package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.City;
import com.ftn.model.Client;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	
	City findByName(String name);

}
