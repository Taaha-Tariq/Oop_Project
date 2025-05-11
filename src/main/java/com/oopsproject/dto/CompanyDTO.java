package com.oopsproject.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class CompanyDTO {
    private int companyId;
    private String companyName;

    // Default constructor needed by Jackson
    public CompanyDTO() {
    }

    // Constructor with both fields
    public CompanyDTO(int companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    // Properly annotated factory method for string values
    @JsonCreator
    public static CompanyDTO create(String value) {
        CompanyDTO dto = new CompanyDTO();
        dto.setCompanyName(value);
        return dto;
    }

    @JsonValue
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // Getters and Setters
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
