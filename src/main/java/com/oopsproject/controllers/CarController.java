package com.oopsproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsproject.dto.CarSaveDTO;
import com.oopsproject.models.Car;
import com.oopsproject.models.CarOwner;
import com.oopsproject.services.CarOwnerService;
import com.oopsproject.services.CarService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final CarOwnerService carOwnerService;

    public CarController(CarService carService, CarOwnerService carOwnerService) {
        this.carOwnerService = carOwnerService;
        this.carService = carService;
    }

    // Add your endpoints here
    @PostMapping("/add")
    public ResponseEntity<CarSaveDTO> addCar(@RequestBody CarSaveDTO carSaveDTO, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        // Check if userId is null (not logged in)
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Check if the car owner exists
        CarOwner carOwner = carOwnerService.getCarOwnerById(userId);
        if (carOwner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Car car = carService.convertToEntity(carSaveDTO);
        car.setOwner(carOwner);
        Car savedCar = carService.saveCar(car);

        CarSaveDTO savedCarDTO = carService.convertToCarSaveDTO(savedCar);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCarDTO); 
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable int carId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        // Check if userId is null (not logged in)
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Check if the car owner exists
        CarOwner carOwner = carOwnerService.getCarOwnerById(userId);
        if (carOwner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Check if the car exists
        Car car = carService.getCarById(carId);
        if (car == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Check if the car belongs to the logged-in user
        if (!car.getOwner().getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        carService.deleteCar(car);
        return ResponseEntity.noContent().build();
    }
}
