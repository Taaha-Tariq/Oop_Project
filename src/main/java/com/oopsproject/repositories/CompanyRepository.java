package com.oopsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oopsproject.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {    
    // Custom query to find company by name
    Company findByCompanyName(String companyName);
}
