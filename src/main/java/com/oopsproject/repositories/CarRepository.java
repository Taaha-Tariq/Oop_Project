package com.oopsproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oopsproject.models.Car;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    // Custom query to find cars by owner ID
    @Query("SELECT c FROM Car c WHERE c.owner.userId = :ownerId")
    List<Car> findCarsByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT c FROM Car c WHERE c.owner.userId = :userId")
    List<Car> findByOwnerUserId(@Param("userId") Long userId);

    Car findByName(String name);
}
