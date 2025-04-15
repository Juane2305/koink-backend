package com.koinkapp.koink_app.user.controller;

import com.koinkapp.koink_app.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
