package com.koinkapp.koink_app.auth.service;

import com.koinkapp.koink_app.auth.model.RefreshToken;
import com.koinkapp.koink_app.auth.repository.RefreshTokenRepository;
import com.koinkapp.koink_app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.jwt.refreshTokenExpirationMs:604800000}") // 7 días por default
    private Long refreshTokenDurationMs;

    public RefreshToken createRefreshToken(User user) {
        // Siempre eliminar el anterior si existe
        refreshTokenRepository.deleteByUser(user);

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        return refreshTokenRepository.save(token);
    }



    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("El refresh token expiró. Por favor iniciá sesión nuevamente.");
        }
        return token;
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}

