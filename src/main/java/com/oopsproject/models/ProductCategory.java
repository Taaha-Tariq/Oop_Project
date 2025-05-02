package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.List;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "PRODUCT_CATEGORY") // The name of the table
public class ProductCategory {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    @Column(name = "category_id")
    private int productCategoryId;

    @Column(name = "category_type", nullable = false)
    private String categoryType;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    // Default constructor
    public ProductCategory() {
    }

    // Parametrized constructor
    public ProductCategory(String categoryType, List<Product> products) {
        this.categoryType = categoryType;
        this.products = products;
    }

    // Getters for the field attributes
    public int getProductCategoryId() {
        return productCategoryId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public List<Product> getProducts() {
        return products;
    }

    // Setters for the field attributes
    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + productCategoryId +
                ", categoryType='" + categoryType + '\'' +
                ", products=" + (products != null ? products.size() : 0) + " products" +
                '}';
    }
}
