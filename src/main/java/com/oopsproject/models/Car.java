
package com.oopsproject.models;

import java.util.List;
import jakarta.persistence.*;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "CAR") // The name of the table
public class Car {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    @Column(name = "car_id")
    private int carId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year", nullable = false)
    private int year;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id", nullable = false)
    private CarOwner owner;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<MaintenanceHistory> maintenanceHistory;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<MaintenanceReminder> maintenanceReminders;

    // Default constructor
    public Car() {
    }

    // Parametrized constructor
    public Car(String name, String model, int year, Category category, Company company, CarOwner owner) {
        this.name = name;
        this.model = model;
        this.year = year;
        this.category = category;
        this.company = company;
        this.owner = owner;
    }

    // Getters for the field attributes
    public int getCarId() {
        return carId;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public Category getCategory() {
        return category;
    }

    public Company getCompany() {
        return company;
    }

    public CarOwner getOwner() {
        return owner;
    }

    public List<MaintenanceHistory> getMaintenanceHistory() {
        return maintenanceHistory;
    }

    public List<MaintenanceReminder> getMaintenanceReminders() {
        return maintenanceReminders;
    }

    // Setters for the field attributes
    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setOwner(CarOwner owner) {
        this.owner = owner;
    }

    public void setMaintenanceHistory(List<MaintenanceHistory> maintenanceHistory) {
        this.maintenanceHistory = maintenanceHistory;
    }

    public void setMaintenanceReminders(List<MaintenanceReminder> maintenanceReminders) {
        this.maintenanceReminders = maintenanceReminders;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", category=" + (category != null ? category.getCategoryId() : null) +
                ", company=" + (company != null ? company.getCompanyId() : null) +
                ", owner=" + (owner != null ? owner.getUserId() : null) +
                ", maintenanceHistory=" + (maintenanceHistory != null ? maintenanceHistory.size() : 0) + " entries" +
                ", maintenanceReminders=" + (maintenanceReminders != null ? maintenanceReminders.size() : 0) + " reminders" +
                '}';
    }
}