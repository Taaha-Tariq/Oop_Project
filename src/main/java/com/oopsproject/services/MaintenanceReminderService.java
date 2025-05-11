package com.oopsproject.services;

import com.oopsproject.dto.MaintenanceReminderSaveDTO;
import com.oopsproject.models.MaintenanceReminder;
import com.oopsproject.repositories.MaintenanceReminderRepository;

import org.springframework.stereotype.Service; 

@Service
public class MaintenanceReminderService {
    MaintenanceReminderRepository maintenanceReminderRepository;
    CarService carService;  

    public MaintenanceReminderService(MaintenanceReminderRepository maintenanceReminderRepository, CarService carService) {
        this.maintenanceReminderRepository = maintenanceReminderRepository;
        this.carService = carService;
    }

    public MaintenanceReminder convertToEntity(MaintenanceReminderSaveDTO dto) {
        MaintenanceReminder maintenanceReminder = new MaintenanceReminder();
        maintenanceReminder.setReminderId(dto.getReminderId());
        maintenanceReminder.setDescription(dto.getDescription());
        maintenanceReminder.setDueMileage(dto.getDueMileage());
        maintenanceReminder.setCar(carService.getCarById(dto.getCarId()));
        maintenanceReminder.setReminderDate(dto.getReminderDate());
        return maintenanceReminder;
    }

    public MaintenanceReminderSaveDTO convertToMaintenanceReminderSaveDTO(MaintenanceReminder maintenanceReminder) {
        MaintenanceReminderSaveDTO maintenanceReminderSaveDTO = new MaintenanceReminderSaveDTO();
        maintenanceReminderSaveDTO.setReminderId(maintenanceReminder.getReminderId());
        maintenanceReminderSaveDTO.setDescription(maintenanceReminder.getDescription());
        maintenanceReminderSaveDTO.setDueMileage(maintenanceReminder.getDueMileage());
        maintenanceReminderSaveDTO.setCarId(maintenanceReminder.getCar().getCarId());
        maintenanceReminderSaveDTO.setReminderDate(maintenanceReminder.getReminderDate());
        return maintenanceReminderSaveDTO;
    }

    public MaintenanceReminder saveMaintenanceReminder(MaintenanceReminder maintenanceReminder) {
        return maintenanceReminderRepository.save(maintenanceReminder);
    }

    public MaintenanceReminder getMaintenanceReminderById(int reminderId) {
        return maintenanceReminderRepository.findById(reminderId).orElse(null);
    }

    public void deleteMaintenanceReminder(int reminderId) {
        maintenanceReminderRepository.deleteById(reminderId);
    }
}
