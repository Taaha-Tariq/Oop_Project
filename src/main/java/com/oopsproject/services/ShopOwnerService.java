package com.oopsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsproject.models.ShopOwner;
import com.oopsproject.models.City;
import com.oopsproject.models.Country;
import com.oopsproject.repositories.ShopOwnerRepository;
import com.oopsproject.repositories.AddressRepository;
import com.oopsproject.dto.AddressDTO;
import com.oopsproject.dto.ShopOwnerDTO;
import com.oopsproject.dto.CityDTO;
import com.oopsproject.dto.CountryDTO;
import com.oopsproject.models.Address;
import com.oopsproject.models.ShopOwner;
import com.oopsproject.repositories.CityRepository;
import com.oopsproject.repositories.CountryRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopOwnerService {
    private final ShopOwnerRepository ShopOwnerRepository;
    private final PasswordService passwordService;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository; 

    @Autowired
    public ShopOwnerService(ShopOwnerRepository ShopOwnerRepository, PasswordService passwordService, AddressRepository addressRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        this.ShopOwnerRepository = ShopOwnerRepository;
        this.passwordService = passwordService;
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
    }

    @Transactional
    public ShopOwner saveShopOwner(ShopOwner ShopOwner) {
        // Save the address first if it's new
        if (ShopOwner.getAddress() != null && ShopOwner.getAddress().getAddressId() == null) {
            // Check if the address has a city
            if (ShopOwner.getAddress().getCity() == null) {
                throw new IllegalArgumentException("Address must have a city");
            } else {
                // If city is provided but not saved yet (transient)
                City city = ShopOwner.getAddress().getCity();
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
                        ShopOwner.getAddress().setCity(existingCity);
                    } else {
                        // Save the city after country is handled
                        City savedCity = cityRepository.save(city);
                        ShopOwner.getAddress().setCity(savedCity);
                    }
                }

                Address existingAddress = addressRepository.findByStreetAndCity_CityId(
                    ShopOwner.getAddress().getStreet(), ShopOwner.getAddress().getCity().getCityId());

                if (existingAddress != null) {
                    ShopOwner.setAddress(existingAddress);
                } else {
                    // Save the address after city is handled
                    Address savedAddress = addressRepository.save(ShopOwner.getAddress());
                    ShopOwner.setAddress(savedAddress);
                }
            }
        }
    
        // Hash the password before saving
        String hashedPassword = passwordService.hashPassword(ShopOwner.getPassword());
        ShopOwner.setPassword(hashedPassword);
        
        return ShopOwnerRepository.save(ShopOwner);
    }
    
    public ShopOwner convertToShopOwnerEntity(ShopOwnerDTO ShopOwnerDTO) {
        ShopOwner ShopOwner = new ShopOwner();
        ShopOwner.setUserId(ShopOwnerDTO.getUserId());
        ShopOwner.setUsername(ShopOwnerDTO.getUsername());
        ShopOwner.setEmail(ShopOwnerDTO.getEmail());
        ShopOwner.setPhoneNumber(ShopOwnerDTO.getPhoneNumber());
        ShopOwner.setPassword(ShopOwnerDTO.getPassword());
        ShopOwner.setAddress(ShopOwnerDTO.getAddress() != null ? convertToAddressEntity(ShopOwnerDTO.getAddress()) : null);
        ShopOwner.setFirstName(ShopOwnerDTO.getFirstName());
        ShopOwner.setLastName(ShopOwnerDTO.getLastName());
        ShopOwner.setShopName(ShopOwnerDTO.getShopName());
        ShopOwner.setShopPhoneNumber(ShopOwnerDTO.getShopPhoneNumber());
        ShopOwner.setShopId(ShopOwnerDTO.getShopId());

        ShopOwner.setRole("SHOP_OWNER");
        return ShopOwner;
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

    public ShopOwnerDTO convertToShopOwnerDTO(ShopOwner ShopOwner) {
        ShopOwnerDTO ShopOwnerDTO = new ShopOwnerDTO();
        ShopOwnerDTO.setUserId(ShopOwner.getUserId());
        ShopOwnerDTO.setUsername(ShopOwner.getUsername());
        ShopOwnerDTO.setEmail(ShopOwner.getEmail());
        ShopOwnerDTO.setPhoneNumber(ShopOwner.getPhoneNumber());
        ShopOwnerDTO.setPassword(ShopOwner.getPassword());
        ShopOwnerDTO.setAddress(ShopOwner.getAddress() != null ? convertToAddressDTO(ShopOwner.getAddress()) : null);
        ShopOwnerDTO.setFirstName(ShopOwner.getFirstName());
        ShopOwnerDTO.setLastName(ShopOwner.getLastName());
        ShopOwnerDTO.setShopName(ShopOwner.getShopName());
        ShopOwnerDTO.setShopPhoneNumber(ShopOwner.getShopPhoneNumber());
        ShopOwnerDTO.setShopId(ShopOwner.getShopId());
        return ShopOwnerDTO;
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
    
    public ShopOwner authenticate(String email, String password) {
        ShopOwner ShopOwner = ShopOwnerRepository.findByEmail(email);
        if (ShopOwner != null && passwordService.verifyPassword(password, ShopOwner.getPassword())) {
            return ShopOwner;
        }
        return null;
    }
}