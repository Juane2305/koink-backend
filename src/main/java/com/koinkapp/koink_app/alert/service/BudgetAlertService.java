package com.koinkapp.koink_app.alert.service;

import com.koinkapp.koink_app.alert.dto.BudgetExceededAlertDTO;
import com.koinkapp.koink_app.budget.model.Budget;
import com.koinkapp.koink_app.budget.repository.BudgetRepository;
import com.koinkapp.koink_app.transaction.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BudgetAlertService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final EmailService emailService;


    @Scheduled(cron = "0 0 * * * *")
    public void checkExceededBudgets() {
        List<Budget> activeBudgets = budgetRepository.findAllActiveBudgets(LocalDate.now());

        for (Budget budget : activeBudgets) {
            BigDecimal totalSpent = transactionRepository.sumAmountByCategoryAndPeriod(
                    budget.getUser().getId(),
                    budget.getCategory().getId(),
                    budget.getStartDate(),
                    budget.getEndDate()
            );

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String formattedMessage = "Se superó el presupuesto de la categoría \"" +
                    budget.getCategory().getName() + "\" del " +
                    budget.getStartDate().format(formatter) + " al " +
                    budget.getEndDate().format(formatter) +
                    ". Total gastado: $" + totalSpent;

            if (totalSpent != null && totalSpent.compareTo(budget.getLimitAmount()) > 0) {
                if (budget.getUser().isAlertsByEmail()) {
                    emailService.sendBudgetExceededAlert(budget, totalSpent);
                }
            }
        }
    }

    public List<BudgetExceededAlertDTO> getExceededBudgetsForUser(Long userId) {
        List<Budget> budgets = budgetRepository.findAllActiveBudgets(LocalDate.now());

        return budgets.stream()
                .filter(b -> b.getUser().getId().equals(userId))
                .map(b -> {
                    BigDecimal spent = transactionRepository.sumAmountByCategoryAndPeriod(
                            userId,
                            b.getCategory().getId(),
                            b.getStartDate(),
                            b.getEndDate()
                    );

                    if (spent != null && spent.compareTo(b.getLimitAmount()) > 0) {
                        return new BudgetExceededAlertDTO(
                                b.getCategory().getName(),
                                b.getStartDate(),
                                b.getEndDate(),
                                b.getLimitAmount(),
                                spent
                        );
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

}
