package com.oopsproject.controllers;

import com.oopsproject.dto.MaintenanceHistoryDTO;
import com.oopsproject.models.CarOwner;
import com.oopsproject.models.MaintenanceHistory;
import com.oopsproject.services.CarOwnerService;
import com.oopsproject.services.MaintenanceHistoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenancehistory")
public class MaintenanceHistoryController {

    private final MaintenanceHistoryService maintenanceHistoryService;
    private final CarOwnerService carOwnerService;

    public MaintenanceHistoryController(MaintenanceHistoryService maintenanceHistoryService, CarOwnerService carOwnerService) {
        this.maintenanceHistoryService = maintenanceHistoryService;
        this.carOwnerService = carOwnerService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceHistoryDTO> addMaintenanceHistory(@RequestBody MaintenanceHistoryDTO maintenanceHistoryDTO, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CarOwner carOwner = carOwnerService.getCarOwnerById(userId);
        if (carOwner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            MaintenanceHistory history = maintenanceHistoryService.convertToMaintenanceHistory(maintenanceHistoryDTO, carOwner);
            MaintenanceHistory savedHistory = maintenanceHistoryService.saveMaintenanceHistory(history);
            MaintenanceHistoryDTO savedDTO = maintenanceHistoryService.convertToMaintenanceHistoryDTO(savedHistory);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceHistoryDTO>> getAllMaintenanceHistories(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CarOwner carOwner = carOwnerService.getCarOwnerById(userId);
        if (carOwner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<MaintenanceHistoryDTO> historyDTOs = maintenanceHistoryService.getAllByCarOwner(userId);
        return ResponseEntity.ok(historyDTOs);
    }

}
