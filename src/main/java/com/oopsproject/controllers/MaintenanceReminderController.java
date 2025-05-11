package com.oopsproject.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpSession;
import com.oopsproject.dto.MaintenanceReminderSaveDTO;
import com.oopsproject.models.CarOwner;
import com.oopsproject.models.MaintenanceReminder;
import com.oopsproject.services.MaintenanceReminderService;
import com.oopsproject.services.CarOwnerService;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceReminderController {
    private final MaintenanceReminderService maintenanceReminderService;
    private final CarOwnerService carOwnerService;

    @Autowired
    public MaintenanceReminderController(MaintenanceReminderService maintenanceReminderService,  CarOwnerService carOwnerService) {
        this.maintenanceReminderService = maintenanceReminderService;
        this.carOwnerService = carOwnerService;
    }

    @PostMapping("/reminder")
    public ResponseEntity<MaintenanceReminderSaveDTO> addMaintenanceReminder(@RequestBody MaintenanceReminderSaveDTO maintenanceReminderSaveDTO, HttpSession session) {
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

        MaintenanceReminder maintenanceReminder = maintenanceReminderService.convertToEntity(maintenanceReminderSaveDTO);
        MaintenanceReminder savedMaintenanceReminder = maintenanceReminderService.saveMaintenanceReminder(maintenanceReminder);
        MaintenanceReminderSaveDTO savedMaintenanceReminderDTO = maintenanceReminderService.convertToMaintenanceReminderSaveDTO(savedMaintenanceReminder);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMaintenanceReminderDTO);
    }

    @DeleteMapping("/{reminderId}")
    public ResponseEntity<Void> deleteMaintenanceReminder(@PathVariable int reminderId, HttpSession session) {
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

        // Check if the maintenance reminder exists
        MaintenanceReminder maintenanceReminder = maintenanceReminderService.getMaintenanceReminderById(reminderId);
        if (maintenanceReminder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Delete the maintenance reminder
        maintenanceReminderService.deleteMaintenanceReminder(reminderId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
