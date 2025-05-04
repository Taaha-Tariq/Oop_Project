package com.oopsproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsproject.models.Users;
import com.oopsproject.repositories.UsersRepository;

// Marks the class as a service provider
@Service
public class UsersService {
    // Declaring the usersRepository
    private final UsersRepository usersRepository;

    // Autowires the beans together
    @Autowired // Initializes the usersRepository
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // Saves the passed user to the repo
    public Users saveUser (Users user) {
        return usersRepository.save(user);
    }
    // Retrieves all the users from the repo
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
    // Retrieves the specified user from the repo
    public Users getUserById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }
    // Deletes the specified user from the repo
    public void deleteUserById(Long id) {
        usersRepository.deleteById(id);
    }
}
