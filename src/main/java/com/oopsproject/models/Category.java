package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.List;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "Car_CATEGORY") // The name of the table
public class Category {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "category")
    private List<Car> cars;

    // Default constructor
    public Category() {
    }

    // Parametrized constructor
    public Category(String type) {
        this.type = type;
    }

    // Getters for the field attributes
    public int getCategoryId() {
        return categoryId;
    }

    public String getType() {
        return type;
    }

    public List<Car> getCars() {
        return cars;
    }

    // Setters for the field attributes
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", type='" + type + '\'' +
                ", cars=" + (cars != null ? cars.size() : 0) + " cars" +
                '}';
    }
}