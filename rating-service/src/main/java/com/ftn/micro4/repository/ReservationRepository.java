package com.ftn.micro4.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.micro4.model.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> 
{
	List<Reservation> findByUserId(Long id);
	Reservation findOneById(Long id);
	List<Reservation> findByRoomId(Long id);
}
