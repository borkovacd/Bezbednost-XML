package com.ftn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Accomodation;
import com.ftn.model.Period;



@Repository
public interface PeriodRepository extends JpaRepository<Period, Long>{

	List<Period> findByAccomodation(Accomodation acc);
}
