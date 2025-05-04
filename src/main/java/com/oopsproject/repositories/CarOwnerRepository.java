package com.oopsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oopsproject.models.CarOwner;

public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {
    // Add this method to your repository interface
    CarOwner findByEmail(String email);
}
