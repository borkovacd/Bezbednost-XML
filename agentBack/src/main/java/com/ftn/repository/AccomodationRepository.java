package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Accomodation;

@Repository
public interface AccomodationRepository extends JpaRepository<Accomodation, Long>{

	Accomodation findOneById(Long id);
}
