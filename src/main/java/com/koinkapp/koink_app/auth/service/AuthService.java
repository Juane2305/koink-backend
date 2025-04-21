package com.koinkapp.koink_app.auth.service;

import com.koinkapp.koink_app.auth.dto.AuthResponse;
import com.koinkapp.koink_app.auth.dto.LoginRequest;
import com.koinkapp.koink_app.auth.dto.RegisterRequest;
import com.koinkapp.koink_app.auth.model.AuthProvider;
import com.koinkapp.koink_app.user.model.User;
import com.koinkapp.koink_app.user.repository.UserRepository;
import com.koinkapp.koink_app.auth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .currency(request.getCurrency())
                .provider(AuthProvider.LOCAL)
                .emailVerified(false)
                .lastLogin(LocalDateTime.now())
                .alertsByEmail(true)
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException(("Invalid credentials"));
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getEmail());

        return new AuthResponse(token, user.getName());
    }

}
