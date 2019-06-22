package com.ftn.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Period;
import com.ftn.model.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
