package com.koinkapp.koink_app.budget.dto;

import com.koinkapp.koink_app.budget.enums.BudgetPeriod;
import com.koinkapp.koink_app.budget.model.Budget;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
public class UpdateBudgetRequest {

    private Long categoryId;
    private BigDecimal limitAmount;
    private BudgetPeriod period;
    private LocalDate startDate;
}
