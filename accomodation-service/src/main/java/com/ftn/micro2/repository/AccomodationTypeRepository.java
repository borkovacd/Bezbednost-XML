package com.ftn.micro2.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.micro2.model.AccomodationType;

public interface AccomodationTypeRepository extends JpaRepository<AccomodationType, Long>
{
	public AccomodationType save(AccomodationType acc);
	
	public AccomodationType findAccomodationTypeByName(String name);
	
	public List<AccomodationType> findAll();
	
	public void deleteById(Long id) ;
	
	public void deleteByName(String name);
	
	
	/*
	@Transactional
	@Modifying
	@Query("delete from AccomodationType where id = :id")
	public void delete(@Param("id") long id);
	
	*/
}
