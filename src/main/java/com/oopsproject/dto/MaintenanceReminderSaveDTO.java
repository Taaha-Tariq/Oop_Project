package com.oopsproject.dto;

import java.util.Date;

public class MaintenanceReminderSaveDTO {
    private int reminderId;
    private Date reminderDate;
    private String description;
    private int dueMileage;
    private int carId;

    // Getters and Setters
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDueMileage() {
        return dueMileage;
    }

    public void setDueMileage(int dueMileage) {
        this.dueMileage = dueMileage;
    }
}
