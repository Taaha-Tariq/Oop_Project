package com.oopsproject.services;

import com.oopsproject.dto.MaintenanceHistoryDTO;
import com.oopsproject.models.Car;
import com.oopsproject.models.CarOwner;
import com.oopsproject.models.MaintenanceHistory;
import com.oopsproject.repositories.CarRepository;
import com.oopsproject.repositories.MaintenanceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceHistoryService {

    private final MaintenanceHistoryRepository maintenanceHistoryRepository;
    private final CarService carService;
    private final CarRepository carRepository;

    public MaintenanceHistoryService(MaintenanceHistoryRepository maintenanceHistoryRepository,CarRepository carRepository, CarService carService) {
        this.maintenanceHistoryRepository = maintenanceHistoryRepository;
        this.carService = carService;
        this.carRepository=carRepository;
    }

    public MaintenanceHistory convertToMaintenanceHistory(MaintenanceHistoryDTO dto, CarOwner carOwner) {
        Car car = carRepository.findById(dto.getCarId())
                .filter(c -> c.getOwner().getOwnerId() == carOwner.getOwnerId()) // Validate ownership
                .orElseThrow(() -> new IllegalArgumentException("Car does not belong to this user"));

        MaintenanceHistory history = new MaintenanceHistory();
        history.setCar(car);
        history.setDate(dto.getDate());
        history.setDescription(dto.getDescription());
        history.setMileage(dto.getMileage());
        history.setCost(dto.getCost());
        history.setType(dto.getType());

        return history;
    }

    public MaintenanceHistoryDTO convertToMaintenanceHistoryDTO(MaintenanceHistory maintenanceHistory) {
        MaintenanceHistoryDTO maintenanceHistoryDTO = new MaintenanceHistoryDTO();
        maintenanceHistoryDTO.setDate(maintenanceHistory.getDate());
        maintenanceHistoryDTO.setDescription(maintenanceHistory.getDescription());
        maintenanceHistoryDTO.setMileage(maintenanceHistory.getMileage());
        maintenanceHistoryDTO.setCost(maintenanceHistory.getCost());
        String carName = carRepository.findById(maintenanceHistory.getCar().getCarId())
                .map(Car::getName)
                .orElse("Unknown Car");
        maintenanceHistoryDTO.setCarName(carName);
        maintenanceHistoryDTO.setCarId(maintenanceHistory.getCar().getCarId());
        maintenanceHistoryDTO.setType(maintenanceHistory.getType());
        return maintenanceHistoryDTO;
    }

    public MaintenanceHistory saveMaintenanceHistory(MaintenanceHistory history) {
        return maintenanceHistoryRepository.save(history);
    }

    public List<MaintenanceHistoryDTO> getAllByCarOwner(Long userId) {
        List<Car> userCars = carRepository.findByOwnerUserId(userId);
        List<MaintenanceHistory> allHistories = new ArrayList<>();
        for (Car car : userCars) {
            allHistories.addAll(maintenanceHistoryRepository.findByCar(car));
        }
        return allHistories.stream()
                .map(this::convertToMaintenanceHistoryDTO)
                .collect(Collectors.toList());
    }

}
