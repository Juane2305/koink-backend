package com.koinkapp.koink_app.user.controller;

import com.koinkapp.koink_app.user.dto.UserPreferencesDTO;
import com.koinkapp.koink_app.user.model.User;
import com.koinkapp.koink_app.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }


    @GetMapping("/preferences")
    public UserPreferencesDTO getPreferences(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return new UserPreferencesDTO(user.isAlertsByEmail());
    }

    @PutMapping("/preferences")
    public void updatePreferences(@RequestBody UserPreferencesDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user.setAlertsByEmail(dto.alertsByEmail());
        userRepository.save(user);
    }

}
