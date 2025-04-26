package com.oopsproject.models;

import jakarta.persistence.*;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "users") // The name of the table
public class Users {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    private Long id;

    // Defining the attribute username which must be unique and cant be null
    @Column(unique = true, nullable = false)
    private String username;
    // Defining the attribute password which must not be null
    @Column(nullable = false)
    private String password;

    // Default constructor
    public Users() {
    }

    // Parametrized constructor
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }
    // Getters for the field attributes
    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    // Setters for the field attributes
    public void setId(long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", username=" + username + ", password=" + password + '}';
    }
}
