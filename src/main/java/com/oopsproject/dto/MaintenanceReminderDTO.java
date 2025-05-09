package com.oopsproject.dto;

import java.util.Date;

public class MaintenanceReminderDTO {
    private int reminderId;
    private Date reminderDate;
    private String description;
    private int dueMileage;

    // Getters and Setters
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
