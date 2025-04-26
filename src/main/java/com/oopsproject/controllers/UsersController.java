package com.oopsproject.controllers;

import com.oopsproject.models.Users;
import com.oopsproject.sevices.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Defining a Rest endpoint point for our application
@RestController
@RequestMapping("/users") // Default endpoint (http://localhost:<port>/users)
public class UsersController {
    // Declaring a User service
    private final UsersService usersService;
    // Auto wires the beans together
    @Autowired // Constructor for initializing the usersService
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/user")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users savedUser = usersService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users user = usersService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        usersService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
