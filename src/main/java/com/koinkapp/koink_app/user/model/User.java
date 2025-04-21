package com.koinkapp.koink_app.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koinkapp.koink_app.auth.model.AuthProvider;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;
    @JsonIgnore
    private String password;
    @Column(nullable = false)
    private String name;
    private String avatar;
    private String currency;
    private LocalDateTime lastLogin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonIgnore
    private AuthProvider provider;
    private boolean emailVerified;
    @Column(nullable = false)
    private boolean alertsByEmail = true;
}
