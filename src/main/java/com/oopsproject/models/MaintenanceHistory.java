package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.Date;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "MAINTENANCE_HISTORY") // The name of the table
public class MaintenanceHistory {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    @Column(name = "maintenance_id")
    private int maintenanceId;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "car_id", nullable = false)
    private Car car;

    @Column(name = "description")
    private String description;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "mileage", nullable = false)
    private int mileage;

    @Column(name = "cost", nullable = false)
    private float cost;

    // Default constructor
    public MaintenanceHistory() {
    }

    // Parametrized constructor
    public MaintenanceHistory(Car car, String description, Date date, int mileage, float cost) {
        this.car = car;
        this.description = description;
        this.date = date;
        this.mileage = mileage;
        this.cost = cost;
    }

    // Getters for the field attributes
    public int getMaintenanceId() {
        return maintenanceId;
    }

    public Car getCar() {
        return car;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public int getMileage() {
        return mileage;
    }

    public float getCost() {
        return cost;
    }

    // Setters for the field attributes
    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "MaintenanceHistory{" +
                "maintenanceId=" + maintenanceId +
                ", car=" + car.getCarId() +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", mileage=" + mileage +
                ", cost=" + cost +
                '}';
    }
}