package com.koinkapp.koink_app.transaction.service;

import com.koinkapp.koink_app.auth.model.User;

import com.koinkapp.koink_app.category.model.Category;
import com.koinkapp.koink_app.category.repository.CategoryRepository;
import com.koinkapp.koink_app.transaction.dto.CreateTransactionRequest;
import com.koinkapp.koink_app.transaction.dto.TransactionResponse;
import com.koinkapp.koink_app.transaction.dto.UpdateTransactionRequest;
import com.koinkapp.koink_app.transaction.model.Transaction;
import com.koinkapp.koink_app.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createTransaction(CreateTransactionRequest request, User user) {
        if (request.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura.");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));

        if (category.getOwner() != null && !category.getOwner().getId().equals(user.getId())) {
            throw new IllegalArgumentException("No tenés acceso a esta categoría.");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setCategory(category);
        transaction.setUser(user);

        transactionRepository.save(transaction);
    }

    public List<TransactionResponse> getUserTransactions(User user) {
        return transactionRepository.findAllByUser(user).stream()
                .map(tx -> new TransactionResponse(
                        tx.getId(),
                        tx.getAmount(),
                        tx.getType(),
                        tx.getDescription(),
                        tx.getCategory().getName(),
                        tx.getDate()
                ))
                .toList();
    }


    public TransactionResponse getTransactionById(Long id, User user) {
        Transaction tx = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transacción no encontrada."));

        if (!tx.getUser().getId().equals(user.getId())) {
            throw new SecurityException("No tenés permiso para ver esta transacción.");
        }

        return new TransactionResponse(
                tx.getId(),
                tx.getAmount(),
                tx.getType(),
                tx.getDescription(),
                tx.getCategory().getName(),
                tx.getDate()
        );
    }


    public void updateTransaction(Long id, UpdateTransactionRequest request, User user) {
        Transaction tx = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transacción no encontrada"));

        if (!tx.getUser().getId().equals(user.getId())){
            throw new SecurityException("No tenés permiso para modificar esta transacción.");
        }

        if (request.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura.");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));

        if (category.getOwner() != null && !category.getOwner().getId().equals(user.getId())) {
            throw new SecurityException("No tenés acceso a esta categoría.");
        }

        tx.setAmount(request.getAmount());
        tx.setType(request.getType());
        tx.setDescription(request.getDescription());
        tx.setCategory(category);
        tx.setDate(request.getDate());

        transactionRepository.save(tx);
    }


    public void deleteTransaction(Long id, User user) {
        Transaction tx = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transacción no encontrada."));

        if (!tx.getUser().getId().equals(user.getId())) {
            throw new SecurityException("No tenés permiso para eliminar esta transacción");
        }

        transactionRepository.delete(tx);
    }
}
