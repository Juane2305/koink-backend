package com.koinkapp.koink_app.auth.repository;

import com.koinkapp.koink_app.auth.model.RefreshToken;
import com.koinkapp.koink_app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}

