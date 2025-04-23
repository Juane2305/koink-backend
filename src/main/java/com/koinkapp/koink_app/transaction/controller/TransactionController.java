package com.koinkapp.koink_app.transaction.controller;

import com.koinkapp.koink_app.report.dto.MonthlyCategoryReportDTO;
import com.koinkapp.koink_app.report.dto.MonthlySpendingDTO;
import com.koinkapp.koink_app.transaction.repository.TransactionRepository;
import com.koinkapp.koink_app.user.model.User;
import com.koinkapp.koink_app.transaction.dto.CreateTransactionRequest;
import com.koinkapp.koink_app.transaction.dto.TransactionResponse;
import com.koinkapp.koink_app.transaction.dto.UpdateTransactionRequest;
import com.koinkapp.koink_app.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody CreateTransactionRequest request, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        transactionService.createTransaction(request, user);
        return ResponseEntity.status(201).body("Transacción registrada con éxito.");
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAll(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<TransactionResponse> transactions = transactionService.getUserTransactions(user);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getOne(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        TransactionResponse response = transactionService.getTransactionById(id, user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update (@PathVariable Long id,
                                          @Valid @RequestBody UpdateTransactionRequest request,
                                          Authentication authentication){
        User user = (User) authentication.getPrincipal();
        transactionService.updateTransaction(id, request, user);
        return ResponseEntity.ok("Transacción actualizada con éxito.");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        transactionService.deleteTransaction(id, user);
        return ResponseEntity.ok("Transacción eliminada con éxito.");
    }

}
