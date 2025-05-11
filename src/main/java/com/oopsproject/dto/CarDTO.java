package com.oopsproject.dto;

import java.util.List;

public class CarDTO {
    private int carId;
    private String name;
    private String model;
    private int year;
    private CompanyDTO company; // Assuming you have a CompanyDTO class
    private CategoryDTO category; // Assuming you have a CatalogDTO class
    private List<MaintenanceHistoryDTO> maintenanceHistory; // Assuming you have a MaintenanceHistoryDTO class
    private List<MaintenanceReminderDTO> maintenanceReminders; // Assuming you have a MaintenanceReminderDTO class

    // Getters and Setters
    public List<MaintenanceHistoryDTO> getMaintenanceHistory() {
        return maintenanceHistory;
    }

    public void setMaintenanceHistory(List<MaintenanceHistoryDTO> maintenanceHistory) {
        this.maintenanceHistory = maintenanceHistory;
    }

    public List<MaintenanceReminderDTO> getMaintenanceReminders() {
        return maintenanceReminders;
    }

    public void setMaintenanceReminders(List<MaintenanceReminderDTO> maintenanceReminders) {
        this.maintenanceReminders = maintenanceReminders;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
