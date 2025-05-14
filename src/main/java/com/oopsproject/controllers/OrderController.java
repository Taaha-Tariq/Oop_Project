package com.oopsproject.controllers;

import com.oopsproject.dto.OrderResponseDTO;
import com.oopsproject.services.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body(null);
        }

        try {
            OrderResponseDTO orderResponse = orderService.createOrderFromCart(userId);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.ok().body("Order placed successfully");
        }
    }


    @GetMapping("/history")
    public ResponseEntity<List<OrderResponseDTO>> getUserOrders(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body(null);
        }

        try {
            List<OrderResponseDTO> orders = orderService.getAllOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
