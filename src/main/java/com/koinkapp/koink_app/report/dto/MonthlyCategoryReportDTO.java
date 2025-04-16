package com.koinkapp.koink_app.report.dto;

import java.math.BigDecimal;

public record MonthlyCategoryReportDTO(
        String categoryName,
        BigDecimal totalSpent
) {}
