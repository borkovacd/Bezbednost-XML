package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.Reservation;
import com.ftn.model.Room;
import com.ftn.service.ReservationService;

@RestController
@RequestMapping(value = "/api/reservation")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	//Vraca sve rezervacije vezane za agenta ciji je ID prosledjen 
	@GetMapping("/getAllReservations/{idAgent}")
	public ResponseEntity<List<Reservation>> getAllReservations(@PathVariable Long idAgent) {
		
		List<Reservation> reservations = reservationService.getAllReservations(idAgent);
		
		return new ResponseEntity<>(reservations, HttpStatus.OK);
		
	}
	
	@GetMapping("/confirmReservation/{idReservation}")
	public void confirmReservation(@PathVariable Long idReservation) {
		
		//da nadje tu rezervaciju preko idReservation i da setujes polje confirmed =true;
		reservationService.confirmReservation(idReservation);

	}
}
