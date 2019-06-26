package com.ftn.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dto.ReservationDTO;
import com.ftn.dto.SearchRoomDTO;
import com.ftn.model.Agent;
import com.ftn.model.ReservationAgent;
import com.ftn.model.Room;
import com.ftn.repository.AgentRepository;
import com.ftn.security.TokenUtils;
import com.ftn.service.ReservationAgentService;

@RestController
@RequestMapping(value = "/api/reservationAgent")
public class ReservationAgentController {
	private static final Logger log = LoggerFactory.getLogger(ReservationAgentController.class);
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private ReservationAgentService reservationAgentService;
	@Autowired
	private TokenUtils tokenUtils;

	@GetMapping("/getAllReservationsAgent/{token}")
	public ResponseEntity<List<ReservationAgent>> getAllReservations(@PathVariable String token) throws Exception {
		
		List<ReservationAgent> reservations = reservationAgentService.getAllReservations(token);

		return new ResponseEntity<>(reservations, HttpStatus.OK);

	}

	@PostMapping("/searchRoom/{token}")
	public ResponseEntity<List<Room>> searchRoom(@RequestBody SearchRoomDTO searchRoomDTO,@PathVariable String token) {
		
		
		String europeanDatePattern = "yyyy-MM-dd";
		DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
		LocalDate d1 = LocalDate.parse(searchRoomDTO.getCheckInDate(), europeanDateFormatter);
		LocalDate d2 = LocalDate.parse(searchRoomDTO.getCheckOutDate(), europeanDateFormatter);
		
		//sad treba da se iz tabele rezervacija proveri koja je soba slobodna u datom periodu
		//i lista tih slobodnih soba treba da se vrati


		List<Room> rooms = reservationAgentService.searchFreeRooms(d1, d2);
		if(rooms != null) {
			

			return new ResponseEntity<List<Room>>(rooms, HttpStatus.OK);
		}
		log.warn("SEAROOMNO");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/createReservation/{idRoom}/{token}")
	public void createReservation(@RequestBody ReservationDTO reservationDTO,@PathVariable Long idRoom,@PathVariable String token) throws Exception {
		
		reservationAgentService.createReservation(reservationDTO, idRoom, token);

	}

}
