package com.oopsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsproject.models.CarOwner;
import com.oopsproject.models.City;
import com.oopsproject.models.Country;
import com.oopsproject.repositories.CarOwnerRepository;
import com.oopsproject.repositories.AddressRepository;
import com.oopsproject.models.Address;
import com.oopsproject.repositories.CityRepository;
import com.oopsproject.repositories.CountryRepository;

@Service
public class CarOwnerService {
    private final CarOwnerRepository carOwnerRepository;
    private final PasswordService passwordService;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository; 

    @Autowired
    public CarOwnerService(CarOwnerRepository carOwnerRepository, PasswordService passwordService, AddressRepository addressRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        this.carOwnerRepository = carOwnerRepository;
        this.passwordService = passwordService;
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
    }

    public CarOwner saveCarOwner(CarOwner carOwner) {
        // Save the address first if it's new
        if (carOwner.getAddress() != null && carOwner.getAddress().getAddressId() == null) {
            // Check if the address has a city
            if (carOwner.getAddress().getCity() == null) {
                throw new IllegalArgumentException("Address must have a city");
            } else {
                // If city is provided but not saved yet (transient)
                City city = carOwner.getAddress().getCity();
                if (city.getCityId() == null) {
                    // Check if the city has a country
                    if (city.getCountry() == null) {
                        throw new IllegalArgumentException("City must have a country");
                    } else {
                        // If country is provided but not saved yet (transient)
                        Country country = city.getCountry();
                        if (country.getCountryId() == null) {
                            // Check if country already exists with the same name
                            Country existingCountry = countryRepository.findByCountryName(country.getCountryName());
                            if (existingCountry != null) {
                                city.setCountry(existingCountry);
                            } else {
                                // Save the country first if it doesn't exist
                                Country savedCountry = countryRepository.save(country);
                                city.setCountry(savedCountry);
                            }
                        }
                    }
                    
                    // Check if city already exists with the same name in the same country
                    City existingCity = cityRepository.findByCityNameAndCountry_CountryId(
                        city.getCityName(), city.getCountry().getCountryId());
                        
                    if (existingCity != null) {
                        carOwner.getAddress().setCity(existingCity);
                    } else {
                        // Save the city after country is handled
                        City savedCity = cityRepository.save(city);
                        carOwner.getAddress().setCity(savedCity);
                    }
                }

                Address existingAddress = addressRepository.findByStreetAndCity_CityId(
                    carOwner.getAddress().getStreet(), carOwner.getAddress().getCity().getCityId());

                if (existingAddress != null) {
                    carOwner.setAddress(existingAddress);
                } else {
                    // Save the address after city is handled
                    Address savedAddress = addressRepository.save(carOwner.getAddress());
                    carOwner.setAddress(savedAddress);
                }
            }
        
        // Save the address after city is handled
        Address savedAddress = addressRepository.save(carOwner.getAddress());
        carOwner.setAddress(savedAddress);
        }
    
        // Hash the password before saving
        String hashedPassword = passwordService.hashPassword(carOwner.getPassword());
        carOwner.setPassword(hashedPassword);
        
        return carOwnerRepository.save(carOwner);
    }
    
    
    public CarOwner authenticate(String email, String password) {
        CarOwner carOwner = carOwnerRepository.findByEmail(email);
        if (carOwner != null && passwordService.verifyPassword(password, carOwner.getPassword())) {
            return carOwner;
        }
        return null;
    }
}