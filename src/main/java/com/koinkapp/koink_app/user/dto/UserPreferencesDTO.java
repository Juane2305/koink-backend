package com.koinkapp.koink_app.user.dto;

public record UserPreferencesDTO(
        String name,
        String currency,
        String avatar,
        boolean alertsByEmail
) {}

