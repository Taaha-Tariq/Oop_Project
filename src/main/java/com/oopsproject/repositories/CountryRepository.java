package com.oopsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oopsproject.models.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
    public Country findByCountryName(String countryName);
}
