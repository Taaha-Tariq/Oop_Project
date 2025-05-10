package com.oopsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsproject.dto.LoginRequest;
import com.oopsproject.dto.ShopOwnerDTO;
import com.oopsproject.models.ShopOwner;
import com.oopsproject.services.ShopOwnerService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/shopowner") // Default endpoint (http://localhost:<port>/ShopOwner)
public class ShopOwnerController {
    private final ShopOwnerService shopOwnerService;

    @Autowired
    public ShopOwnerController (ShopOwnerService shopOwnerService) {
         this.shopOwnerService = shopOwnerService;
    }

    @PostMapping("/register")
    public ResponseEntity<ShopOwnerDTO> register(@RequestBody ShopOwnerDTO dto) {
        ShopOwner shopOwner = shopOwnerService.convertToShopOwnerEntity(dto);
        ShopOwner savedShopOwner = shopOwnerService.saveShopOwner(shopOwner);

        return ResponseEntity.status(HttpStatus.CREATED).body(shopOwnerService.convertToShopOwnerDTO(savedShopOwner));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        ShopOwner ShopOwner = shopOwnerService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (ShopOwner != null) {
            // Store user data in session
            session.setAttribute("userId", ShopOwner.getUserId());
            session.setAttribute("loginTime", System.currentTimeMillis());
            session.setAttribute("userType", "shopowner"); // Store user type in session
            
            return ResponseEntity.ok(shopOwnerService.convertToShopOwnerDTO(ShopOwner));
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
        if (session != null) {
            // Retrieve user data from session
            Long userId = (Long) session.getAttribute("userId");
            String userType = (String) session.getAttribute("userType");

            if (userId != null && userType != null) {
                // Fetch the profile based on user type
                if (userType.equals("shopowner")) {
                    ShopOwner shopOwner = shopOwnerService.getShopOwnerById(userId);
                    return ResponseEntity.ok(shopOwnerService.convertToShopOwnerDTO(shopOwner));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
    }

    @PutMapping("/profileupdate")
    public ResponseEntity<?> updateProfile(@RequestBody ShopOwnerDTO dto, HttpSession session) {
        // Check if there's an active session
        if (session != null) {
            // Retrieve user data from session
            Long userId = (Long) session.getAttribute("userId");
            String userType = (String) session.getAttribute("userType");
    
            if (userId != null && userType != null) {
                // Update the profile based on user type
                if (userType.equals("shopowner")) {
                    // First fetch the existing shop owner
                    ShopOwner existingShopOwner = shopOwnerService.getShopOwnerById(userId);
                    if (existingShopOwner != null) {
                        // Update the fields from DTO
                        existingShopOwner.setShopName(dto.getShopName());
                        existingShopOwner.setShopPhoneNumber(dto.getShopPhoneNumber());
                        existingShopOwner.setFirstName(dto.getFirstName());
                        existingShopOwner.setLastName(dto.getLastName());
                        existingShopOwner.setEmail(dto.getEmail());
                        existingShopOwner.setPhoneNumber(dto.getPhoneNumber());
                        
                        // Handle address update if provided in DTO
                        if (dto.getAddress() != null) {
                            existingShopOwner.setAddress(
                                shopOwnerService.convertToAddressEntity(dto.getAddress())
                            );
                        }
                        
                        // Save the updated entity
                        ShopOwner updatedShopOwner = shopOwnerService.updateShopOwner(existingShopOwner);
                        return ResponseEntity.ok(shopOwnerService.convertToShopOwnerDTO(updatedShopOwner));
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
    }
}
