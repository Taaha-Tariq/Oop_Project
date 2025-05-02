package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.List;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "city") // The name of the table
public class City {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    private Long cityId;

    // Defining the attribute cityName which can't be null
    @Column(nullable = false)
    private String cityName;

    // Many-to-one relationship with Country
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    // One-to-many relationship with Address
    @OneToMany(mappedBy = "city")
    private List<Address> addresses;

    // Default constructor
    public City() {
    }

    // Parametrized constructor
    public City(String cityName, Country country) {
        this.cityName = cityName;
        this.country = country;
    }

    // Getters for the field attributes
    public Long getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public Country getCountry() {
        return country;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    // Setters for the field attributes
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "City{" + "cityId=" + cityId + ", cityName=" + cityName + ", country=" + country + '}';
    }
}