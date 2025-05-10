package com.oopsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oopsproject.models.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {
    
}
