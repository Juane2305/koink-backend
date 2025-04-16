package com.koinkapp.koink_app.budget.repository;

import com.koinkapp.koink_app.category.model.Category;
import com.koinkapp.koink_app.user.model.User;
import com.koinkapp.koink_app.budget.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findAllByUser(User user);

    boolean existsByCategory(Category category);

    List<Budget> findByUserAndActiveTrue(User user);

    @Query("SELECT b FROM Budget b WHERE :today BETWEEN b.startDate AND b.endDate")
    List<Budget> findAllActiveBudgets(@Param("today") LocalDate today);

    @Query("""
    SELECT COUNT(b) > 0 FROM Budget b
    WHERE b.user.id = :userId
      AND b.category.id = :categoryId
      AND b.active = true
      AND (
           (b.startDate <= :endDate AND b.endDate >= :startDate)
          )
""")
    boolean existsOverlappingBudget(Long userId, Long categoryId, LocalDate startDate, LocalDate endDate);

}
