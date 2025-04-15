package com.koinkapp.koink_app.dashboard.dto;

import com.koinkapp.koink_app.budget.enums.BudgetPeriod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BudgetSummaryDTO {

    private Long id;
    private String categoryName;
    private BudgetPeriod period;
    private BigDecimal limitAmount;
    private BigDecimal totalSpent;
    private boolean active;
}
