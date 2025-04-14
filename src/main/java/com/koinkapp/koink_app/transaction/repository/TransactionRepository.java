package com.koinkapp.koink_app.transaction.repository;

import com.koinkapp.koink_app.auth.model.User;
import com.koinkapp.koink_app.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByUser(User user);

    List<Transaction> findAllByUserAndDateBetween(User user, LocalDate start, LocalDate end);

}
