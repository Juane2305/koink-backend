package com.koinkapp.koink_app.budget.dto;

import com.koinkapp.koink_app.budget.enums.BudgetPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ActiveBudgetResponse {
    private Long id;
    private String categoryName;
    private BigDecimal limitAmount;
    private BigDecimal spentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private BudgetPeriod period;
}
