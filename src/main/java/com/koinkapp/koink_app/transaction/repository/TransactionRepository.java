package com.koinkapp.koink_app.transaction.repository;

import com.koinkapp.koink_app.category.model.Category;
import com.koinkapp.koink_app.report.dto.MonthlyCategoryReportDTO;
import com.koinkapp.koink_app.report.dto.MonthlySpendingDTO;
import com.koinkapp.koink_app.transaction.model.TransactionType;
import com.koinkapp.koink_app.user.model.User;
import com.koinkapp.koink_app.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByUser(User user);

    List<Transaction> findAllByUserAndDateBetween(User user, LocalDate start, LocalDate end);

    boolean existsByCategory(Category category);

    List<Transaction> findTop5ByUserOrderByDateDesc(User user);

    // 🔹 Total por tipo (INCOME o EXPENSE)
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = :type")
    Optional<BigDecimal> getTotalAmountByTypeAndUser(TransactionType type, Long userId);

    // 🔹 Gasto total en una categoría dentro de un período
    @Query("""
        SELECT SUM(t.amount)
        FROM Transaction t
        WHERE t.user.id = :userId
        AND t.category.id = :categoryId
        AND t.date BETWEEN :startDate AND :endDate
        AND t.type = 'EXPENSE'
        """)
    Optional<BigDecimal> getTotalSpentInCategoryAndPeriod(Long userId, Long categoryId,
                                                          LocalDate startDate, LocalDate endDate);

    @Query("""
    SELECT SUM(t.amount)
    FROM Transaction t
    WHERE t.user.id = :userId
      AND t.category.id = :categoryId
      AND t.date BETWEEN :startDate AND :endDate
      AND t.type = 'EXPENSE'
""")
    BigDecimal sumAmountByCategoryAndPeriod(
            @Param("userId") Long userId,
            @Param("categoryId") Long categoryId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
            );
}
