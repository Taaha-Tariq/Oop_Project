package com.oopsproject.dto;

import java.util.List;


public class CarOwnerDTO {
    private Long userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private AddressDTO address; // Assuming you have an AddressDTO class
    private String firstName;
    private String lastName;
    private List<CarDTO> car; // Assuming you have a CarDTO class
    private List<OrderDTO> orders; // Assuming you have an OrderDTO class
    private CartDTO cart; // Assuming you have a CartDTO class

    // Getters and Setters
    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    public List<CarDTO> getCar() {
        return car;
    }

    public void setCar(List<CarDTO> car) {
        this.car = car;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
