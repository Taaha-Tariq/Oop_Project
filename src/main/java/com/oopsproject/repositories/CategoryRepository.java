package com.oopsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oopsproject.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Custom query to find category by type
    Category findByType(String type);
}
