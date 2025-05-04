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

@RestController
@RequestMapping("/shopowner") // Default endpoint (http://localhost:<port>/ShopOwner)
public class ShopOwnerController {
    private final ShopOwnerService ShopOwnerService;

    @Autowired
    public ShopOwnerController (ShopOwnerService ShopOwnerService) {
         this.ShopOwnerService = ShopOwnerService;
    }

    @PostMapping("/register")
    public ResponseEntity<ShopOwner> register(@RequestBody ShopOwner ShopOwner) {
        ShopOwner savedShopOwner = ShopOwnerService.saveShopOwner(ShopOwner);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedShopOwner);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        ShopOwner ShopOwner = ShopOwnerService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (ShopOwner != null) {
            return ResponseEntity.ok(ShopOwner);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
