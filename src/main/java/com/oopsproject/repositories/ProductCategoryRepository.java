package com.oopsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oopsproject.models.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    ProductCategory findByCategoryType(String categoryType);
    ProductCategory findByProductCategoryId(int categoryId);
}
