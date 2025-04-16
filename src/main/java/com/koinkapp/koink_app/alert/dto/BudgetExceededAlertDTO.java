package com.koinkapp.koink_app.alert.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BudgetExceededAlertDTO(
    String categoryName,
    LocalDate startDate,
    LocalDate endDate,
    BigDecimal limitAmount,
    BigDecimal totalSpent
) { }
