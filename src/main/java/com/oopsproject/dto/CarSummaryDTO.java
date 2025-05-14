package com.oopsproject.dto;

public class CarSummaryDTO {
    private int carId;
    private String name;

    // Constructors
    public CarSummaryDTO() {}

    public CarSummaryDTO(int carId, String name) {
        this.carId = carId;
        this.name = name;
    }

    // Getters and Setters
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
}
