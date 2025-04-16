package com.koinkapp.koink_app.category.service;

import com.koinkapp.koink_app.budget.repository.BudgetRepository;
import com.koinkapp.koink_app.category.dto.CreateCategoryRequest;
import com.koinkapp.koink_app.category.dto.UpdateCategoryRequest;
import com.koinkapp.koink_app.category.model.Category;
import com.koinkapp.koink_app.category.repository.CategoryRepository;
import com.koinkapp.koink_app.transaction.repository.TransactionRepository;
import com.koinkapp.koink_app.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    public List<Category> getAllAvailableCategories(User user) {
        return categoryRepository.findByOwnerOrOwnerIsNull(user);
    }

    public Category createCategory(CreateCategoryRequest request, User user) {
        String trimmedName = request.getName().trim();
        if (categoryRepository.existsByOwnerAndNameIgnoreCase(user, trimmedName)) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setType(request.getType());
        category.setOwner(user);

        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, UpdateCategoryRequest request, User user) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));

        if (category.getOwner() == null || !category.getOwner().getId().equals(user.getId())) {
            throw new SecurityException("No tenés permiso para modificar esta categoría.");
        }

        category.setName(request.getName());
        category.setType(request.getType());
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id, User user) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));
        if (category.getOwner() == null || !user.getId().equals(category.getOwner().getId())) {
            throw new SecurityException("No tenés permiso para eliminar esta categoría.");
        }

        boolean usadaEnTransacciones = transactionRepository.existsByCategory(category);
        boolean usadaEnPresupuestos = budgetRepository.existsByCategory(category);

        if (usadaEnTransacciones || usadaEnPresupuestos) {
            throw new IllegalStateException("No se puede eliminar la categoría porque está en uso.");
        }

        categoryRepository.delete(category);
    }
}
