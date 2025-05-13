package com.oopsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oopsproject.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Custom query to find a cart by user ID
    @Query("SELECT c FROM Cart c WHERE c.user.userId = :userId")
    Cart findByUserId(@Param("userId") Long userId);
     
}
