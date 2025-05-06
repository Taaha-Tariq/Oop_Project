package com.oopsproject.dto;

public class CountryDTO {
    private Long countryId;
    private String countryName;

    public CountryDTO() {
    }

    public CountryDTO(String countryName) {
        this.countryName = countryName;
    }
    // Getters and Setters
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
