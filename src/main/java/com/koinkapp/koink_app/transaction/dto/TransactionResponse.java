package com.koinkapp.koink_app.transaction.dto;

import com.koinkapp.koink_app.transaction.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TransactionResponse {

    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private String categoryName;
    private LocalDate date;
}
