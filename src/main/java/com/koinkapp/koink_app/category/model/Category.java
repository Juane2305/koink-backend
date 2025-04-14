package com.koinkapp.koink_app.category.model;

import com.koinkapp.koink_app.auth.model.User;
import com.koinkapp.koink_app.transaction.model.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private User owner;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;
}
