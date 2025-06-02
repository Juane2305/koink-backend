package com.koinkapp.koink_app.dashboard.service;

import com.koinkapp.koink_app.budget.repository.BudgetRepository;
import com.koinkapp.koink_app.dashboard.dto.BudgetSummaryDTO;
import com.koinkapp.koink_app.dashboard.dto.DashboardResponse;
import com.koinkapp.koink_app.dashboard.dto.RecentTransactionDTO;
import com.koinkapp.koink_app.transaction.model.TransactionType;
import com.koinkapp.koink_app.transaction.repository.TransactionRepository;
import com.koinkapp.koink_app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    public DashboardResponse getDashboard(User user, LocalDate startDate, LocalDate endDate) {

        // Ingresos y egresos del período
        BigDecimal totalIncome = transactionRepository.getTotalAmountByTypeAndUserAndDateRange(
                        TransactionType.INCOME, user.getId(), startDate, endDate)
                .orElse(BigDecimal.ZERO);

        BigDecimal totalExpense = transactionRepository.getTotalAmountByTypeAndUserAndDateRange(
                        TransactionType.EXPENSE, user.getId(), startDate, endDate)
                .orElse(BigDecimal.ZERO);

        BigDecimal balance = totalIncome.subtract(totalExpense);

        // Transacciones recientes del período (limit 5)
        List<RecentTransactionDTO> recentTransactions = transactionRepository
                .findTop5ByUserAndDateBetweenOrderByDateDesc(user, startDate, endDate)
                .stream()
                .map(t -> new RecentTransactionDTO(
                        t.getId(),
                        t.getAmount(),
                        t.getDescription(),
                        t.getDate(),
                        t.getCategory().getName(),
                        t.getType()
                ))
                .collect(Collectors.toList());

        // Presupuestos activos (se mantienen igual, son independientes del período)
        List<BudgetSummaryDTO> activeBudgets = budgetRepository.findByUserAndActiveTrue(user)
                .stream()
                .map(b -> {
                    BigDecimal spent = transactionRepository.getTotalSpentInCategoryAndPeriod(
                            user.getId(),
                            b.getCategory().getId(),
                            b.getStartDate(),
                            b.getEndDate()
                    ).orElse(BigDecimal.ZERO);

                    return new BudgetSummaryDTO(
                            b.getId(),
                            b.getCategory().getName(),
                            b.getPeriod(),
                            b.getLimitAmount(),
                            spent,
                            b.isActive()
                    );
                })
                .collect(Collectors.toList());

        DashboardResponse response = new DashboardResponse();
        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);
        response.setBalance(balance);
        response.setRecentTransactions(recentTransactions);
        response.setActiveBudgets(activeBudgets);

        return response;
    }
}
