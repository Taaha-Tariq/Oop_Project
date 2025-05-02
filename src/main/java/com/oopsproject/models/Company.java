package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.List;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "COMPANY") // The name of the table
public class Company {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    @Column(name = "company_id")
    private int companyId;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @OneToMany(mappedBy = "company")
    private List<Car> cars;

    // Default constructor
    public Company() {
    }

    // Parametrized constructor
    public Company(String companyName) {
        this.companyName = companyName;
    }

    // Getters for the field attributes
    public int getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Car> getCars() {
        return cars;
    }

    // Setters for the field attributes
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", cars=" + (cars != null ? cars.size() : 0) + " cars" +
                '}';
    }
}