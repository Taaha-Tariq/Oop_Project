package com.oopsproject.services;

import java.util.List;
import java.util.stream.Collectors;

import com.oopsproject.dto.*;
import com.oopsproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oopsproject.repositories.CarRepository;
import com.oopsproject.repositories.CategoryRepository;
import com.oopsproject.repositories.CompanyRepository;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public CarService(CarRepository carRepository, CategoryRepository categoryRepository, CompanyRepository companyRepository) {
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
        this.carRepository = carRepository;
    }
    
    @Transactional
    public Car convertToEntity(CarSaveDTO carDTO) {
        Car car = new Car();
        car.setCarId(carDTO.getCarId());
        car.setName(carDTO.getName());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setMaintenanceHistory(carDTO.getMaintenanceHistory() != null ? convertToMaintenanceHistory(carDTO.getMaintenanceHistory()) : null);
        car.setMaintenanceReminders(carDTO.getMaintenanceReminders() != null ? convertToMaintenanceReminder(carDTO.getMaintenanceReminders()) : null);
        
        // Handle Category properly
        if (carDTO.getCategory() != null) {
            Category category;
            
            // If category has an ID, try to find it in the database
            if (carDTO.getCategory().getCategoryId() > 0) {
                category = categoryRepository.findById(carDTO.getCategory().getCategoryId())
                    .orElseGet(() -> {
                        // If not found by ID, create and save a new category
                        Category newCategory = new Category();
                        newCategory.setType(carDTO.getCategory().getType());
                        return categoryRepository.save(newCategory);
                    });
            } else {
                // Try to find by type/name
                category = categoryRepository.findByType(carDTO.getCategory().getType());
                if (category == null) {
                    // Create and save new category
                    category = new Category();
                    category.setType(carDTO.getCategory().getType());
                    category = categoryRepository.save(category);
                }
            }
            
            car.setCategory(category);
        }
        
        // Handle Company similarly
        if (carDTO.getCompany() != null) {
            Company company;
            
            if (carDTO.getCompany().getCompanyId() > 0) {
                company = companyRepository.findById(carDTO.getCompany().getCompanyId())
                    .orElseGet(() -> {
                        Company newCompany = new Company();
                        newCompany.setCompanyName(carDTO.getCompany().getCompanyName());
                        return companyRepository.save(newCompany);
                    });
            } else {
                company = companyRepository.findByCompanyName(carDTO.getCompany().getCompanyName());
                if (company == null) {
                    company = new Company();
                    company.setCompanyName(carDTO.getCompany().getCompanyName());
                    company = companyRepository.save(company);
                }
            }
            
            car.setCompany(company);
        }
        
        return car;
    }
    
    @Transactional
    public Car saveCar(Car car) {
        return carRepository.saveAndFlush(car);
    }


    private List<MaintenanceReminder> convertToMaintenanceReminder(List<MaintenanceReminderDTO> maintenanceReminderDTOs) {
        return maintenanceReminderDTOs.stream()
                .map(this::convertToMaintenanceReminder)
                .collect(Collectors.toList());
    }

    private MaintenanceReminder convertToMaintenanceReminder(MaintenanceReminderDTO maintenanceReminderDTO) {
        MaintenanceReminder maintenanceReminder = new MaintenanceReminder();
        maintenanceReminder.setReminderId(maintenanceReminderDTO.getReminderId());
        maintenanceReminder.setDescription(maintenanceReminderDTO.getDescription());
        maintenanceReminder.setReminderDate(maintenanceReminderDTO.getReminderDate());
        maintenanceReminder.setDueMileage(maintenanceReminderDTO.getDueMileage());
        return maintenanceReminder;
    }

    private List<MaintenanceHistory> convertToMaintenanceHistory(List<MaintenanceHistoryDTO> maintenanceHistoryDTOs) {
        return maintenanceHistoryDTOs.stream()
                .map(this::convertToMaintenanceHistory)
                .collect(Collectors.toList());
    }

    private MaintenanceHistory convertToMaintenanceHistory(MaintenanceHistoryDTO maintenanceHistoryDTO) {
        MaintenanceHistory maintenanceHistory = new MaintenanceHistory();
        maintenanceHistory.setDate(maintenanceHistoryDTO.getDate());
        maintenanceHistory.setCar(carRepository.findByName(maintenanceHistoryDTO.getCarName()));
        maintenanceHistory.setDescription(maintenanceHistoryDTO.getDescription());
        maintenanceHistory.setMileage(maintenanceHistoryDTO.getMileage());
        maintenanceHistory.setCost(maintenanceHistoryDTO.getCost());
        maintenanceHistory.setType(maintenanceHistory.getType());
        return maintenanceHistory;
    }

    public Category convertToCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setType(categoryDTO.getType());
        return category;
    }

    public Company convertToCompany(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setCompanyId(companyDTO.getCompanyId());
        company.setCompanyName(companyDTO.getCompanyName());
        return company;
    }

    public CarSaveDTO convertToCarSaveDTO(Car car) {
        CarSaveDTO carSaveDTO = new CarSaveDTO();
        carSaveDTO.setCarId(car.getCarId());
        carSaveDTO.setName(car.getName());
        carSaveDTO.setModel(car.getModel());
        carSaveDTO.setYear(car.getYear());
        carSaveDTO.setCompany(car.getCompany() != null ? convertToCompanyDTO(car.getCompany()) : null);
        carSaveDTO.setCategory(car.getCategory() != null ? convertToCategoryDTO(car.getCategory()) : null);
        carSaveDTO.setMaintenanceHistory(car.getMaintenanceHistory() != null ? convertToMaintenanceHistoryDTOs(car.getMaintenanceHistory()) : null);
        carSaveDTO.setMaintenanceReminders(car.getMaintenanceReminders() != null ? convertToMaintenanceReminderDTOs(car.getMaintenanceReminders()) : null);
        carSaveDTO.setOwnerId(car.getOwner().getUserId());
        return carSaveDTO;
    }

    private MaintenanceReminderDTO convertToMaintenanceReminderDTO(MaintenanceReminder maintenanceReminder) {
        MaintenanceReminderDTO maintenanceReminderDTO = new MaintenanceReminderDTO();
        maintenanceReminderDTO.setReminderId(maintenanceReminder.getReminderId());
        maintenanceReminderDTO.setDescription(maintenanceReminder.getDescription());
        maintenanceReminderDTO.setReminderDate(maintenanceReminder.getReminderDate());
        maintenanceReminderDTO.setDueMileage(maintenanceReminder.getDueMileage());
        return maintenanceReminderDTO;
    }

    private List<MaintenanceReminderDTO> convertToMaintenanceReminderDTOs(List<MaintenanceReminder> maintenanceReminders) {
        return maintenanceReminders.stream()
                .map(this::convertToMaintenanceReminderDTO)
                .collect(Collectors.toList());
    }

    private MaintenanceHistoryDTO convertToMaintenanceHistoryDTO(MaintenanceHistory maintenanceHistory) {
        MaintenanceHistoryDTO maintenanceHistoryDTO = new MaintenanceHistoryDTO();
        maintenanceHistoryDTO.setDate(maintenanceHistory.getDate());
        maintenanceHistoryDTO.setDescription(maintenanceHistory.getDescription());
        maintenanceHistoryDTO.setMileage(maintenanceHistory.getMileage());
        maintenanceHistoryDTO.setCost(maintenanceHistory.getCost());
        String carName = carRepository.findById(maintenanceHistory.getCar().getCarId())
                .map(Car::getName)
                .orElse("Unknown Car");
        maintenanceHistoryDTO.setCarName(carName);
        maintenanceHistoryDTO.setType(maintenanceHistory.getType());
        return maintenanceHistoryDTO;
    }

    private List<MaintenanceHistoryDTO> convertToMaintenanceHistoryDTOs(List<MaintenanceHistory> maintenanceHistory) {
        return maintenanceHistory.stream()
                .map(this::convertToMaintenanceHistoryDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO convertToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setType(category.getType());
        return categoryDTO;
    }

    public CompanyDTO convertToCompanyDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCompanyId(company.getCompanyId());
        companyDTO.setCompanyName(company.getCompanyName());
        return companyDTO;
    }

    // to list the all cars owned by the user
    public List<CarSummaryDTO> getCarSummariesForUser(Long userid) {
        List<Car> cars = carRepository.findCarsByOwnerId(userid);
        return cars.stream()
                .map(car -> new CarSummaryDTO(car.getCarId(), car.getName()))
                .collect(Collectors.toList());
    }

    public Car getCarById(int carId) {
        return carRepository.findById(carId).orElse(null);
    }

    public void deleteCar(Car car) {
        carRepository.delete(car);
    }
}
