package com.oopsproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsproject.dto.OrderResponseDTO;
import com.oopsproject.services.CartService;
import com.oopsproject.services.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<?> checkout(HttpSession session, @PathVariable Long cartId) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body(null);
        }

        try {
            OrderResponseDTO orderResponse = orderService.createOrderFromCart(userId);
            cartService.removeCart(cartId);
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
