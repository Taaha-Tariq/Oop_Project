package com.oopsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsproject.dto.LoginRequest;
import com.oopsproject.models.ShopOwner;
import com.oopsproject.services.ShopOwnerService;
import com.oopsproject.dto.ShopOwnerDTO;

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
}
