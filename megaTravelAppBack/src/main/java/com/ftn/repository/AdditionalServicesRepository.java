package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.AdditionalServices;

@Repository
public interface AdditionalServicesRepository extends JpaRepository<AdditionalServices, Long>{

	AdditionalServices findByName(String name);

}
