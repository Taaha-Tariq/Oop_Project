package com.oopsproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oopsproject.models.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {
    // Custom query to find cars by owner ID
    @Query("SELECT c FROM Car c WHERE c.owner.userId = :ownerId")
    List<Car> findCarsByOwnerId(@Param("ownerId") Long ownerId);

    // Custom query to find cars by car ID
    @Query("SELECT c FROM Car c WHERE c.carId = :carId")
    List<Car> findByOwner(@Param("carId") Long carId);
    
}
