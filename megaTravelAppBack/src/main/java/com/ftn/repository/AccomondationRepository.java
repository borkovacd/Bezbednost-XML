package com.ftn.repository;

import com.ftn.model.Accomodation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccomondationRepository extends JpaRepository<Accomodation, Long>{


	Accomodation findOneById(Long id);

}

