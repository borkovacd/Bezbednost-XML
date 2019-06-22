package com.ftn.micro3.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.micro3.model.Reservation;
import com.ftn.micro3.model.Room;
import com.ftn.micro3.model.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>
{
	List<Reservation> findByUserId(Long id);
	List<Reservation> findAll();
	void deleteById(Long id);
}
