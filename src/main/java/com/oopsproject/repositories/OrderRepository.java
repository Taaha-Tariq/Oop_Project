package com.oopsproject.repositories;

import com.oopsproject.models.Order;
import com.oopsproject.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Optional: Fetch all orders by a specific user
    List<Order> findByUser(Users user);

    // Optional: Fetch all orders by user ID
    List<Order> findByUserUserId(Long userId);
}
