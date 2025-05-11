package com.oopsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oopsproject.models.ShopOwner;

public interface ShopOwnerRepository extends JpaRepository<ShopOwner, Long> {
    // Add this method to your repository interface
    ShopOwner findByEmail(String email);   
    @Query("SELECT MAX(s.shopId) FROM ShopOwner s")
    Long findMaxShopId(); 
}
