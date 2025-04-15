package com.koinkapp.koink_app.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class DashboardResponse {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
    private List<RecentTransactionDTO> recentTransactions;
    private List<BudgetSummaryDTO> activeBudgets;
}
