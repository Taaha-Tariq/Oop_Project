package com.oopsproject.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

// Defining a Rest endpoint point for our application
@RestController
@RequestMapping("/users") // Default endpoint (http://localhost:<port>/users)
public class UsersController {
    @GetMapping("/session-status")
    public ResponseEntity<?> checkSessionStatus(HttpSession session) {
        // Create a response map
        Map<String, Object> response = new HashMap<>();
        
        // Check if session exists and has user attributes
        if (session != null && session.getAttribute("userId") != null) {
            // Session is active
            response.put("active", true);
            response.put("userId", session.getAttribute("userId"));
            response.put("userType", session.getAttribute("userType"));
            response.put("loginTime", session.getAttribute("loginTime"));
            response.put("sessionId", session.getId());
            
            return ResponseEntity.ok(response);
        } else {
            // Session is not active
            response.put("active", false);
            return ResponseEntity.ok(response);
        }
    }
}
