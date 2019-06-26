package com.ftn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.Reservation;
import com.ftn.service.ReservationService;

@RestController
@RequestMapping(value = "/api/reservation")
public class ReservationController {
	private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

	@Autowired
	private ReservationService reservationService;
	
	//Vraca sve rezervacije vezane za agenta ciji je ID prosledjen 

	@PreAuthorize("hasAuthority('ADD_RES')")
	@GetMapping("/getAllReservations/{token}")
	public ResponseEntity<List<Reservation>> getAllReservations(@PathVariable String token) throws Exception {
		
	//	token = token.substring(1,token.length()-1).toString();
		
		List<Reservation> reservations = reservationService.getAllReservations(token);
		
		return new ResponseEntity<>(reservations, HttpStatus.OK);
		
	}

	@PreAuthorize("hasAuthority('ADD_RES')")
	@GetMapping("/confirmReservation/{idReservation}")
	public void confirmReservation(@PathVariable Long idReservation) {
		//fali token
		//da nadje tu rezervaciju preko idReservation i da setujes polje confirmed =true;
		reservationService.confirmReservation(idReservation);

	}
}
