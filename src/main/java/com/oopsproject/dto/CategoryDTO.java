package com.oopsproject.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class CategoryDTO {
    private int categoryId;
    private String type;

    // Default constructor needed by Jackson
    public CategoryDTO() {
    }

    // Constructor with both fields
    public CategoryDTO(int categoryId, String type) {
        this.categoryId = categoryId;
        this.type = type;
    }

    // Properly annotated factory method for string values
    @JsonCreator
    public static CategoryDTO create(String value) {
        CategoryDTO dto = new CategoryDTO();
        dto.setType(value);
        return dto;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
