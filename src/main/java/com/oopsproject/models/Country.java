package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.List;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "country") // The name of the table
public class Country {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // It will have auto generated (auto incremented values)
    private Long countryId;

    // Defining the attribute countryName which can't be null
    @Column(nullable = false)
    private String countryName;

    // One-to-many relationship with City
    @OneToMany(mappedBy = "country")
    private List<City> cities;

    // Default constructor
    public Country() {
    }

    // Parametrized constructor
    public Country(String countryName) {
        this.countryName = countryName;
    }

    // Getters for the field attributes
    public Long getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public List<City> getCities() {
        return cities;
    }

    // Setters for the field attributes
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "Country{" + "countryId=" + countryId + ", countryName=" + countryName + '}';
    }
}