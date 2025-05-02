package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.List;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "address") // The name of the table
public class Address {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    private Long addressId;

    // Defining the attribute street which can't be null
    @Column(nullable = false)
    private String street;

    // Many-to-one relationship with City
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    // One-to-many relationship with ShopOwner
    @OneToMany(mappedBy = "address")
    private List<ShopOwner> shopOwners;

    // One-to-many relationship with CarOwner
    @OneToMany(mappedBy = "address")
    private List<CarOwner> carOwners;

    // Default constructor
    public Address() {
    }

    // Parametrized constructor
    public Address(String street, City city) {
        this.street = street;
        this.city = city;
    }

    // Getters for the field attributes
    public Long getAddressId() {
        return addressId;
    }

    public String getStreet() {
        return street;
    }

    public City getCity() {
        return city;
    }

    public List<ShopOwner> getShopOwners() {
        return shopOwners;
    }

    public List<CarOwner> getCarOwners() {
        return carOwners;
    }

    // Setters for the field attributes
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setShopOwners(List<ShopOwner> shopOwners) {
        this.shopOwners = shopOwners;
    }

    public void setCarOwners(List<CarOwner> carOwners) {
        this.carOwners = carOwners;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "Address{" + "addressId=" + addressId + ", street=" + street + ", city=" + city + '}';
    }
}