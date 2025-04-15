package com.koinkapp.koink_app.category.dto;

import com.koinkapp.koink_app.transaction.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {

    @NotBlank
    private String name;

    @NotNull
    private TransactionType type;
}
