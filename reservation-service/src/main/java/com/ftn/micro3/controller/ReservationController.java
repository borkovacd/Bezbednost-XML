package com.ftn.micro3.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.ftn.micro3.dto.BasicSearchDTO;
import com.ftn.micro3.dto.ReservationDTO;
import com.ftn.micro3.model.Reservation;
import com.ftn.micro3.model.Room;
import com.ftn.micro3.repository.AgentRepository;
import com.ftn.micro3.repository.RoomRepository;
import com.ftn.micro3.repository.UserRepository;
import com.ftn.micro3.service.ReservationService;

@RestController
@RequestMapping(value="api/reservations")
public class ReservationController 
{
	@Autowired
	ReservationService reservationService ;
	
	@Autowired
	RoomRepository roomRepository ;
	
	@Autowired
	UserRepository userRepository ;
	
	@Autowired
	AgentRepository agentRepository ;
	
	@GetMapping(value="/getRoomById/{id}")
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
	
	// findOneByName
	
	@PostMapping(value="/searchFreeRooms")
	public ResponseEntity<List<Room>> searchFreeRooms(@RequestBody BasicSearchDTO dto) {
		List<Room> rooms = reservationService.searchFreeRooms(dto.getCity(), dto.getFromDate(), dto.getToDate(), dto.getNumberOfPersons());
		if(rooms != null) {
			return new ResponseEntity<List<Room>>(rooms, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@PostMapping(value="/createReservation")
	public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO res)
	{
		Reservation newReservation = new Reservation();
		
		String europeanDatePattern = "yyyy-MM-dd";
		DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
		LocalDate fromDateConverted = LocalDate.parse(res.getFromDate(), europeanDateFormatter);
		LocalDate toDateConverted = LocalDate.parse(res.getToDate(), europeanDateFormatter);
		
		
		
		if (res != null)
		{
			newReservation.setAgent(agentRepository.getOne(res.getIdAgent()));
			newReservation.setConfirmed(false);
			newReservation.setFromDate(fromDateConverted); 
			newReservation.setToDate(toDateConverted); 
			newReservation.setId(res.getId());
			newReservation.setUser(userRepository.getOne(res.getIdUser())); 
			newReservation.setRoom(roomRepository.getOne(res.getIdRoom())); 
		 
			
			reservationService.createReservation(newReservation);
			
			return new ResponseEntity<Reservation>(newReservation, HttpStatus.OK);
			
			
		}
		
		
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value="/getReservationsByUser/{id}")
	public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long id) 
	{
		
		List <Reservation> reservationsById = reservationService.findReservationsByUserId(id);
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
	
	@DeleteMapping(value="/deleteReservation/{id}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long id)
	{
		if (id != null) {
			reservationService.deleteReservation(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
}
