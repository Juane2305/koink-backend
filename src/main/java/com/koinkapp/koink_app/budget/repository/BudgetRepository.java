package com.koinkapp.koink_app.budget.repository;

import com.koinkapp.koink_app.category.model.Category;
import com.koinkapp.koink_app.user.model.User;
import com.koinkapp.koink_app.budget.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findAllByUser(User user);

    boolean existsByCategory(Category category);

    List<Budget> findByUserAndActiveTrue(User user);
}
