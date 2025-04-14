package com.koinkapp.koink_app.transaction.dto;

import com.koinkapp.koink_app.transaction.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateTransactionRequest {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private TransactionType type;

    private String description;

    @NotNull
    private Long categoryId;

    @NotNull
    private LocalDate date;
}
