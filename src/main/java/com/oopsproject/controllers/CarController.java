package com.oopsproject.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsproject.dto.CarSaveDTO;
import com.oopsproject.dto.CarSummaryDTO;
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

    @GetMapping("/")
    public ResponseEntity<List<CarSaveDTO>> getAllCars(HttpSession session) {
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

        List<Car> cars = carService.getAllCarsByOwner(userId);
        List<CarSaveDTO> carDTOs = cars.stream()
                .map(carService::convertToCarSaveDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(carDTOs);
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

    @GetMapping("/mycars")
    public ResponseEntity<?> getUserCars(HttpSession session) {
        // Check if there's an active session
        if (session != null && session.getAttribute("userId") != null) {
            // Retrieve user data from session
            Long userId = (Long) session.getAttribute("userId");
        List<CarSummaryDTO> carSummaries = carService.getCarSummariesForUser(userId);
        return ResponseEntity.ok(carSummaries);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
    }

}
