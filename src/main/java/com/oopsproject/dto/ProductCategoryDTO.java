package com.oopsproject.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class ProductCategoryDTO {
    private int productCategoryId;
    private String categoryType;

    // Getters and Setters
    public int getProductCategoryId() {
        return productCategoryId;
    }

    // Default constructor needed by Jackson
    public ProductCategoryDTO() {
    }

    // Constructor with both fields
    public ProductCategoryDTO(int productCategoryId, String categoryType) {
        this.productCategoryId = productCategoryId;
        this.categoryType = categoryType;
    }

    // Properly annotated factory method for string values
    @JsonCreator
    public static ProductCategoryDTO create(String value) {
        ProductCategoryDTO dto = new ProductCategoryDTO();
        dto.setCategoryType(value);
        return dto;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @JsonValue
    public String getCategoryType() {
        return categoryType;
    }
    
    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
