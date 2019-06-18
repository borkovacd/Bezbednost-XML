package com.ftn.micro2.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.micro2.model.AccomodationType;
import com.ftn.micro2.model.AdditionalServices;

public interface AdditionalServicesRepository extends JpaRepository<AdditionalServices, Long> 
{
	public AdditionalServices save(AdditionalServices addServ);
	
	public AdditionalServices findAdditionalServicesByName(String name);
	
	public List<AdditionalServices> findAll();
	
	public void deleteById(Long id) ;
	
	public List<AccomodationType> deleteByName(String name);
	
	/*
	@Transactional
	@Modifying
	@Query("delete from AccomodationService where id = :id")
	public void delete(@Param("id") long id);
	*/
}
