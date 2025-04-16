package com.koinkapp.koink_app.budget.service;

import com.koinkapp.koink_app.budget.dto.UpdateBudgetRequest;
import com.koinkapp.koink_app.budget.enums.BudgetPeriod;
import com.koinkapp.koink_app.budget.model.Budget;
import com.koinkapp.koink_app.budget.repository.BudgetRepository;
import com.koinkapp.koink_app.category.model.Category;
import com.koinkapp.koink_app.category.repository.CategoryRepository;
import com.koinkapp.koink_app.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    private LocalDate calculateEndDate(LocalDate startDate, BudgetPeriod period) {
        return switch (period) {
            case DAILY -> startDate;
            case WEEKLY -> startDate.plusDays(6);
            case MONTHLY -> startDate.plusMonths(1).minusDays(1);
            case ANNUAL -> startDate.plusYears(1).minusDays(1);
        };
    }


    @Transactional
    public Budget createBudget(User user, Long categoryId, BudgetPeriod period, BigDecimal limitAmount, LocalDate startDate) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));

        if (category.getOwner() != null && !category.getOwner().getId().equals(user.getId())) {
            throw new SecurityException("No tenés permiso para usar esta categoría.");
        }

        if (limitAmount == null || limitAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto límite debe ser mayor a cero.");
        }

        LocalDate endDate = switch (period) {
            case DAILY -> startDate;
            case WEEKLY -> startDate.plusDays(6);
            case MONTHLY -> startDate.plusMonths(1).minusDays(1);
            case ANNUAL -> startDate.plusYears(1).minusDays(1);
        };

        if (budgetRepository.existsOverlappingBudget(user.getId(), categoryId, startDate, endDate)) {
            throw new IllegalArgumentException("Ya existe un presupuesto para esa categoría en el rango especificado.");
        }

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setCategory(category);
        budget.setPeriod(period);
        budget.setLimitAmount(limitAmount);
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setActive(true);


        return budgetRepository.save(budget);
    }

    public List<Budget> getBudgetsByUser(User user) {
        return budgetRepository.findAllByUser(user);
    }

    @Transactional
    public void updateBudget(Long budgetId, UpdateBudgetRequest request, User user) {

        System.out.println("🔍 Iniciando actualización de presupuesto");
        System.out.println("➡️ Usuario autenticado: " + user.getId() + " - " + user.getEmail());
        System.out.println("➡️ Budget ID recibido: " + budgetId);

        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> {
                    System.out.println("❌ Presupuesto no encontrado con ID: " + budgetId);
                    return new IllegalArgumentException("Presupuesto no encontrado.");
                });

        System.out.println("✅ Presupuesto encontrado, pertenece al usuario ID: " + budget.getUser().getId());

        if (!budget.getUser().getId().equals(user.getId())) {
            System.out.println("❌ Usuario no autorizado para modificar este presupuesto");
            throw new IllegalArgumentException("No tenés permiso para modificar este presupuesto.");
        }

        System.out.println("➡️ Category ID recibido: " + request.getCategoryId());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> {
                    System.out.println("❌ Categoría no encontrada con ID: " + request.getCategoryId());
                    return new IllegalArgumentException("Categoría no encontrada.");
                });

        if (category.getOwner() != null && !category.getOwner().getId().equals(user.getId())) {
            System.out.println("❌ Usuario no autorizado para usar esta categoría");
            throw new IllegalArgumentException("No tenés acceso a esta categoría.");
        }

        System.out.println("✅ Categoría validada correctamente");
        System.out.println("➡️ Período: " + request.getPeriod());
        System.out.println("➡️ Fecha inicio: " + request.getStartDate());

        LocalDate endDate = calculateEndDate(request.getStartDate(), request.getPeriod());

        System.out.println("📆 Fecha fin calculada: " + endDate);

        budget.setCategory(category);
        budget.setLimitAmount(request.getLimitAmount());
        budget.setPeriod(request.getPeriod());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(endDate);

        System.out.println("✅ Presupuesto actualizado exitosamente");
    }

    @Transactional
    public void deleteBudget(Long budgetId, User user) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new IllegalArgumentException("Presupuesto no encontrado."));

        if (!budget.getUser().getId().equals(user.getId())) {
            throw new SecurityException("No tenés permiso para eliminar este presupuesto.");
        }

        budgetRepository.delete(budget);
    }

}
