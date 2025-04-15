package com.koinkapp.koink_app.budget.model;

import com.koinkapp.koink_app.user.model.User;
import com.koinkapp.koink_app.budget.enums.BudgetPeriod;
import com.koinkapp.koink_app.category.model.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    private BudgetPeriod period;

    @Column(nullable = false)
    private BigDecimal limitAmount;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private boolean active = true;
}
