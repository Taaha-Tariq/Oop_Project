package com.oopsproject.repositories;

import com.oopsproject.models.Car;
import com.oopsproject.models.CarOwner;
import com.oopsproject.models.MaintenanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceHistoryRepository extends JpaRepository<MaintenanceHistory, Integer> {
    List<MaintenanceHistory> findAllByCarOwner(CarOwner carOwner);
    List<MaintenanceHistory> findByCar(Car car);

}
