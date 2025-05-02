package com.oopsproject.models;
import jakarta.persistence.*;
import java.util.Date;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "MAINTENANCE_REMINDER") // The name of the table
public class MaintenanceReminder {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    @Column(name = "reminder_id")
    private int reminderId;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "car_id", nullable = false)
    private Car car;

    @Column(name = "reminder_date", nullable = false)
    private Date reminderDate;

    @Column(name = "description")
    private String description;

    @Column(name = "due_mileage", nullable = false)
    private int dueMileage;

    // Default constructor
    public MaintenanceReminder() {
    }

    // Parametrized constructor
    public MaintenanceReminder(Car car, Date reminderDate, String description, int dueMileage) {
        this.car = car;
        this.reminderDate = reminderDate;
        this.description = description;
        this.dueMileage = dueMileage;
    }

    // Getters for the field attributes
    public int getReminderId() {
        return reminderId;
    }

    public Car getCar() {
        return car;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public String getDescription() {
        return description;
    }

    public int getDueMileage() {
        return dueMileage;
    }

    // Setters for the field attributes
    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueMileage(int dueMileage) {
        this.dueMileage = dueMileage;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "MaintenanceReminder{" +
                "reminderId=" + reminderId +
                ", car=" + car.getCarId() +
                ", reminderDate=" + reminderDate +
                ", description='" + description + '\'' +
                ", dueMileage=" + dueMileage +
                '}';
    }
}