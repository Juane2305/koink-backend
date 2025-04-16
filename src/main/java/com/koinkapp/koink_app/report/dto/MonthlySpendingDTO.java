package com.koinkapp.koink_app.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonthlySpendingDTO {
    private int month;
    private BigDecimal totalSpent;
}
