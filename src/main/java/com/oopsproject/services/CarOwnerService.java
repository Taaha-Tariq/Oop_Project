package com.oopsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsproject.dto.AddressDTO;
import com.oopsproject.dto.CarOwnerDTO;
import com.oopsproject.dto.CityDTO;
import com.oopsproject.dto.CountryDTO;
import com.oopsproject.models.Address;
import com.oopsproject.models.CarOwner;
import com.oopsproject.models.City;
import com.oopsproject.models.Country;
import com.oopsproject.repositories.AddressRepository;
import com.oopsproject.repositories.CarOwnerRepository;
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

    public CarOwner convertToCarOwnerEntity(CarOwnerDTO carOwnerDTO) {
        CarOwner carOwner = new CarOwner();
        carOwner.setUserId(carOwnerDTO.getUserId());
        carOwner.setUsername(carOwnerDTO.getUsername());
        carOwner.setEmail(carOwnerDTO.getEmail());
        carOwner.setPhoneNumber(carOwnerDTO.getPhoneNumber());
        carOwner.setPassword(carOwnerDTO.getPassword());
        carOwner.setAddress(carOwnerDTO.getAddress() != null ? convertToAddressEntity(carOwnerDTO.getAddress()) : null);
        carOwner.setFirstName(carOwnerDTO.getFirstName());
        carOwner.setLastName(carOwnerDTO.getLastName());
        
        carOwner.setRole("CAR_OWNER");
        return carOwner;
    }

    private Address convertToAddressEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setAddressId(addressDTO.getAddressId());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity() != null ? convertToCityEntity(addressDTO.getCity()) : null);
        return address;
    }

    private City convertToCityEntity(CityDTO cityDTO) {
        City city = new City();
        city.setCityId(cityDTO.getCityId());
        city.setCityName(cityDTO.getCityName());
        city.setCountry(cityDTO.getCountry() != null ? convertToCountryEntity(cityDTO.getCountry()) : null);
        return city;
    }

    private Country convertToCountryEntity(CountryDTO countryDTO) {
        Country country = new Country();
        country.setCountryId(countryDTO.getCountryId());
        country.setCountryName(countryDTO.getCountryName());
        return country;
    }

    public CarOwnerDTO convertToCarOwnerDTO(CarOwner carOwner) {
        CarOwnerDTO carOwnerDTO = new CarOwnerDTO();
        carOwnerDTO.setUserId(carOwner.getUserId());
        carOwnerDTO.setUsername(carOwner.getUsername());
        carOwnerDTO.setEmail(carOwner.getEmail());
        carOwnerDTO.setPhoneNumber(carOwner.getPhoneNumber());
        carOwnerDTO.setPassword(carOwner.getPassword());
        carOwnerDTO.setAddress(carOwner.getAddress() != null ? convertToAddressDTO(carOwner.getAddress()) : null);
        carOwnerDTO.setFirstName(carOwner.getFirstName());
        carOwnerDTO.setLastName(carOwner.getLastName());
        return carOwnerDTO;
    }

    private AddressDTO convertToAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity() != null ? convertToCityDTO(address.getCity()) : null);
        return addressDTO;
    }

    private CityDTO convertToCityDTO(City city) {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setCityId(city.getCityId());
        cityDTO.setCityName(city.getCityName());
        cityDTO.setCountry(city.getCountry() != null ? convertToCountryDTO(city.getCountry()) : null);
        return cityDTO;
    }

    private CountryDTO convertToCountryDTO(Country country) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryId(country.getCountryId());
        countryDTO.setCountryName(country.getCountryName());
        return countryDTO;
    }
    
    public CarOwner authenticate(String email, String password) {
        CarOwner carOwner = carOwnerRepository.findByEmail(email);
        if (carOwner != null && passwordService.verifyPassword(password, carOwner.getPassword())) {
            return carOwner;
        }
        return null;
    }
}