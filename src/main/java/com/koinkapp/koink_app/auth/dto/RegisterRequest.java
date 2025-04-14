package com.koinkapp.koink_app.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {

    private String email;
    private String password;
    private String name;
    private String currency;
}
