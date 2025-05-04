package com.oopsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oopsproject.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    public Address findByStreetAndCity_CityId(String street, Long cityId);
    
}
