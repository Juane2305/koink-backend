package com.koinkapp.koink_app.budget.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ActiveBudgetResponse {
    private Long id;
    private String categoryName;
    private BigDecimal limitAmount;
    private BigDecimal spentAmount;
}
