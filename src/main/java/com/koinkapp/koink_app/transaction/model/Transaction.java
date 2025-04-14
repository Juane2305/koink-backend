package com.koinkapp.koink_app.transaction.model;

import com.koinkapp.koink_app.auth.model.User;
import com.koinkapp.koink_app.category.model.Category;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User user;
    @Column(nullable = false)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    private String description;

    @ManyToOne(optional = false)
    private Category category;
    @Column(nullable = false)
    private LocalDate date;
}
