package com.koinkapp.koink_app.category.controller;

import com.koinkapp.koink_app.category.dto.CreateCategoryRequest;
import com.koinkapp.koink_app.category.dto.UpdateCategoryRequest;
import com.koinkapp.koink_app.category.model.Category;
import com.koinkapp.koink_app.category.repository.CategoryRepository;
import com.koinkapp.koink_app.category.service.CategoryService;
import com.koinkapp.koink_app.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAll(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Category> categories = categoryService.getAllAvailableCategories(user);
        return ResponseEntity.ok(categories);
    }


    @PostMapping
    public ResponseEntity<Category> create(@RequestBody @Valid CreateCategoryRequest request,
                                           Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Category created = categoryService.createCategory(request, user);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id,
                                           @RequestBody @Valid UpdateCategoryRequest request,
                                           Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Category updated = categoryService.updateCategory(id, request, user);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,
                                         Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        categoryService.deleteCategory(id, user);
        return ResponseEntity.ok("Categoría eliminada con éxito.");
    }
}
