package com.koinkapp.koink_app.dashboard.dto;

import com.koinkapp.koink_app.transaction.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class RecentTransactionDTO {

    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private String categoryName;
    private TransactionType type;
}
