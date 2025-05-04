package com.oopsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oopsproject.models.City;

public interface CityRepository extends JpaRepository<City, Long> {
    City findByCityNameAndCountry_CountryId(String cityName, Long countryId);
}
