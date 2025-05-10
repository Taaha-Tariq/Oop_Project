package com.oopsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oopsproject.dto.AddressDTO;
import com.oopsproject.dto.CityDTO;
import com.oopsproject.dto.CountryDTO;
import com.oopsproject.dto.ShopOwnerDTO;
import com.oopsproject.models.Address;
import com.oopsproject.models.City;
import com.oopsproject.models.Country;
import com.oopsproject.models.ShopOwner;
import com.oopsproject.repositories.AddressRepository;
import com.oopsproject.repositories.CityRepository;
import com.oopsproject.repositories.CountryRepository;
import com.oopsproject.repositories.ShopOwnerRepository;

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

    public ShopOwner getShopOwnerById(Long userId) {
        return ShopOwnerRepository.findById(userId).orElse(null);
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

    public Address convertToAddressEntity(AddressDTO addressDTO) {
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

    @Transactional
    public ShopOwner updateShopOwner(ShopOwner shopOwner) {
        if (shopOwner.getUserId() == null) {
            throw new IllegalArgumentException("Shop owner ID cannot be null for update operation");
        }
        
        // Verify the shop owner exists
        ShopOwner existingShopOwner = ShopOwnerRepository.findById(shopOwner.getUserId())
            .orElseThrow(() -> new RuntimeException("Shop owner not found with ID: " + shopOwner.getUserId()));
        
        // Update basic properties
        existingShopOwner.setFirstName(shopOwner.getFirstName());
        existingShopOwner.setLastName(shopOwner.getLastName());
        existingShopOwner.setEmail(shopOwner.getEmail());
        existingShopOwner.setPhoneNumber(shopOwner.getPhoneNumber());
        existingShopOwner.setShopName(shopOwner.getShopName());
        existingShopOwner.setShopPhoneNumber(shopOwner.getShopPhoneNumber());
        
        // Handle address update
        if (shopOwner.getAddress() != null) {
            Address newAddress = shopOwner.getAddress();
            
            // If the address has a city
            if (newAddress.getCity() != null) {
                City city = newAddress.getCity();
                // System.out.println("City: " + city.getCityName());
                
                // If the city has a country
                if (city.getCountry() != null) {
                    Country country = countryRepository.findByCountryName(city.getCountry().getCountryName());
                    Country persistentCountry;
                    if (country != null) {
                        // Country already exists
                        persistentCountry = country;
                    } else {
                        // Save the new country
                        persistentCountry = countryRepository.save(new Country(city.getCountry().getCountryName()));
                    }
                    // System.out.println("Country: " + persistentCountry.getCountryName());
                    // System.out.println("City: " + persistentCountry.getCountryId());
                    // Set the country to the city
                    city.setCountry(persistentCountry);
                }
                
                // Find or create city
                City persistentCity;
                City existingCity = cityRepository.findByCityNameAndCountry_CountryId(
                    city.getCityName(), city.getCountry().getCountryId());
                if (existingCity != null) {
                    // City already exists
                    persistentCity = existingCity;
                } else {
                    // Save the new city
                    persistentCity = cityRepository.save(new City(city.getCityName(), city.getCountry()));
                }
                
                // Set the city to the address
                newAddress.setCity(persistentCity);
            }
            
            // Find or create address
            Address persistentAddress;
            Address existingAddress = addressRepository.findByStreetAndCity_CityId(
                newAddress.getStreet(), newAddress.getCity().getCityId());
            if (existingAddress != null) {
                // Address already exists
                persistentAddress = existingAddress;
            } else {
                // Save the new address
                persistentAddress = addressRepository.save(new Address(newAddress.getStreet(), newAddress.getCity()));
            }
            
            // Set the address to the shop owner
            existingShopOwner.setAddress(persistentAddress);
        }
        
        // Save and return the updated shop owner
        return ShopOwnerRepository.save(existingShopOwner);
    }
}