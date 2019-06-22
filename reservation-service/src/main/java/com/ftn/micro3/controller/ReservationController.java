package com.ftn.micro3.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro3.dto.ReservationDTO;
import com.ftn.micro3.model.Reservation;
import com.ftn.micro3.model.Room;
import com.ftn.micro3.repository.RoomRepository;
import com.ftn.micro3.repository.UserRepository;
import com.ftn.micro3.service.ReservationService;

@RestController
@RequestMapping("api/reservations")
public class ReservationController 
{
	@Autowired
	ReservationService reservationService ;
	
	@Autowired
	RoomRepository roomRepository ;
	
	@Autowired
	UserRepository userRepository ;
	
	@GetMapping("/getRoomById/{id}")
	public ResponseEntity<Room> getRoomById(@PathVariable Long id)
	{
		Room oneRoom = reservationService.getOneRoom(id);
		
		if (oneRoom == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<Room>(oneRoom, HttpStatus.OK);
		}
	}
	
	/*
	public ResponseEntity<List<Room>> getFreeRooms(@PathVariable)
	*/
	
	
	@PostMapping("/createReservation")
	public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO res)
	{
		Reservation newReservation = new Reservation();
		
		if (res != null)
		{
			newReservation.setAgent(res.getAgent());
			newReservation.setConfirmed(false);
			newReservation.setFromDate(res.getFromDate());
			newReservation.setToDate(res.getToDate());
			newReservation.setId(res.getId());
			newReservation.setUser(res.getUser()); // userRepository.getOne(res.getId));
			newReservation.setRoom(res.getRoom());  // roomRepository.getOne(res.getId))
		 
			
			reservationService.createReservation(newReservation);
			
			return new ResponseEntity<Reservation>(newReservation, HttpStatus.OK);
			
			
		}
		
		
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getReservationsByUser/{id}")
	public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long id) 
	{
		
		List <Reservation> reservationsById = reservationService.findReservationByUserId(id);
		List <Reservation> reservations = new ArrayList<>();
		
		if (reservationsById != null)
		{
			for (Reservation r :  reservationsById) {
				reservations.add(r);
			}
			
			return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
		}
		
		else 
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/deleteReservation/{id}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long id)
	{
		if (id != null) {
			reservationService.deleteReservation(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
}
