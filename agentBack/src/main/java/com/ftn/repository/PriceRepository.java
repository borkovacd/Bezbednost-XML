package com.ftn.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.model.Accomodation;
import com.ftn.model.Price;


public interface PriceRepository extends JpaRepository<Price, Long>{ 

	ArrayList<Price> findAllByAccomodation(Accomodation acc);
}
