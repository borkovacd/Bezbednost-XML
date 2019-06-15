package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.TypeAccomodation;

@Repository
public interface TypeAccomodationRepository extends JpaRepository<TypeAccomodation, Long> {

	TypeAccomodation findByName(String name);

}
