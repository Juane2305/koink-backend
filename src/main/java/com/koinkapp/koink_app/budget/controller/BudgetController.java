package com.koinkapp.koink_app.budget.controller;

import com.koinkapp.koink_app.budget.dto.ActiveBudgetResponse;
import com.koinkapp.koink_app.budget.dto.BudgetResponse;
import com.koinkapp.koink_app.budget.dto.CreateBudgetRequest;
import com.koinkapp.koink_app.budget.dto.UpdateBudgetRequest;
import com.koinkapp.koink_app.budget.model.Budget;
import com.koinkapp.koink_app.budget.service.BudgetService;
import com.koinkapp.koink_app.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<Budget> create(@Valid @RequestBody CreateBudgetRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Budget created = budgetService.createBudget(
                user,
                request.getCategoryId(),
                request.getPeriod(),
                request.getLimitAmount(),
                request.getStartDate()
        );

        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getAll(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<BudgetResponse> budgets = budgetService.getBudgetsWithSpending(user);
        return ResponseEntity.ok(budgets);
    }



    @GetMapping("/active")
    public ResponseEntity<List<ActiveBudgetResponse>> getActiveWithSpending(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<ActiveBudgetResponse> response = budgetService.getActiveBudgetsWithSpending(user);
        return ResponseEntity.ok(response);
    }



    @PutMapping("/{id}")
    public ResponseEntity<String> updateBudget(@PathVariable Long id,
                                               @RequestBody @Valid UpdateBudgetRequest request,
                                               Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        budgetService.updateBudget(id, request, user);
        return ResponseEntity.ok("Presupuesto actualizado con éxito.");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBudget(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        budgetService.deleteBudget(id, user);
        return ResponseEntity.ok("Presupuesto eliminado con éxito.");
    }
}
