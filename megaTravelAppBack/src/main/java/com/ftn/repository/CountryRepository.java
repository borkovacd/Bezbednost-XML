package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
