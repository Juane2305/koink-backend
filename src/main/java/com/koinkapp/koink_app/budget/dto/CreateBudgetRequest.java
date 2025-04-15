package com.koinkapp.koink_app.budget.dto;

import com.koinkapp.koink_app.budget.enums.BudgetPeriod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateBudgetRequest {

    @NotNull
    private Long categoryId;

    @NotNull
    private BudgetPeriod period;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal limitAmount;

    @NotNull
    private LocalDate startDate;
}
