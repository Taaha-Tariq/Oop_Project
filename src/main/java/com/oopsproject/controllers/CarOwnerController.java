package com.oopsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsproject.dto.CarOwnerDTO;
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
    public ResponseEntity<CarOwnerDTO> register(@RequestBody CarOwnerDTO dto) {
        CarOwner carOwner = carOwnerService.convertToCarOwnerEntity(dto);
        CarOwner savedCarOwner = carOwnerService.saveCarOwner(carOwner);

        return ResponseEntity.status(HttpStatus.CREATED).body(carOwnerService.convertToCarOwnerDTO(savedCarOwner));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        CarOwner carOwner = carOwnerService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (carOwner != null) {
            // Store user data in session
            session.setAttribute("userId", carOwner.getUserId());
            session.setAttribute("loginTime", System.currentTimeMillis());
            session.setAttribute("userType", "carowner"); // Store user type in session
            
            return ResponseEntity.ok(carOwnerService.convertToCarOwnerDTO(carOwner));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        // Check if there's an active session
        if (session != null) {
            // Invalidate the session
            session.invalidate();
        }
        
        // Return a success response
        return ResponseEntity.ok().body("Successfully logged out");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpSession session) {
        // Check if there's an active session
        if (session != null && session.getAttribute("userId") != null) {
            // Retrieve user data from session
            Long userId = (Long) session.getAttribute("userId");

            CarOwner carOwner = carOwnerService.getCarOwnerById(userId);
            return ResponseEntity.ok(carOwnerService.convertToCarOwnerDTO(carOwner)); 
        }
        
        // Return an error response if no active session or invalid user type
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
    }
}
