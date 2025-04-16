package com.koinkapp.koink_app.category.repository;

import com.koinkapp.koink_app.user.model.User;
import com.koinkapp.koink_app.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByOwnerOrOwnerIsNull(User owner);

    boolean existsByOwnerAndNameIgnoreCase(User owner, String name);

}
