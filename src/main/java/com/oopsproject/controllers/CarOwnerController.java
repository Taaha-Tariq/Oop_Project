package com.oopsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsproject.dto.LoginRequest;
import com.oopsproject.models.CarOwner;
import com.oopsproject.services.CarOwnerService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/carowner") // Default endpoint (http://localhost:<port>/carowner)
public class CarOwnerController {
    private final CarOwnerService carOwnerService;

    @Autowired
    public CarOwnerController (CarOwnerService carOwnerService) {
         this.carOwnerService = carOwnerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CarOwner> register(@RequestBody CarOwner carOwner) {
        CarOwner savedCarOwner = carOwnerService.saveCarOwner(carOwner);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCarOwner);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        CarOwner carOwner = carOwnerService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (carOwner != null) {
            // Store user data in session
            session.setAttribute("userId", carOwner.getUserId());
            session.setAttribute("loginTime", System.currentTimeMillis());
            session.setAttribute("userType", "carowner"); // Store user type in session
            
            return ResponseEntity.ok(carOwner);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
