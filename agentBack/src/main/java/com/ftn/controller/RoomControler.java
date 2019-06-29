package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ftn.dto.RoomDTO;
import com.ftn.model.Agent;
import com.ftn.model.Rating;
import com.ftn.model.Room;
import com.ftn.repository.AgentRepository;
import com.ftn.security.TokenUtils;
import com.ftn.service.RoomService;

@RestController
@RequestMapping(value = "/api/room")
public class RoomControler {
	private static final Logger log = LoggerFactory.getLogger(RoomControler.class);

	
	@Autowired
	private RoomService roomService;
	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private AgentRepository agentRepository;


	@PreAuthorize("hasAuthority('ADD_ROOM')")
	@PostMapping("/createRoom/{idAccomodation}/{token}")
	public void createAccomodation(@RequestBody RoomDTO roomDTO, @PathVariable Long idAccomodation,@PathVariable String token) throws Exception {

		roomService.createRoom(roomDTO, idAccomodation,token);
	}

	@PreAuthorize("hasAuthority('EDIT_ROOM')")
	@GetMapping("/getAllRooms/{idAccomodation}/{token}")
	public ResponseEntity<ArrayList<Room>> getAllRooms(@PathVariable Long idAccomodation,@PathVariable String token) throws Exception {

		ArrayList<Room> rooms = roomService.getAllRooms(idAccomodation,token);

		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('EDIT_ROOM')")
	@GetMapping("/checkIfReservedRoom/{id}")
	public boolean checkIfReservedRoom(@PathVariable Long id) {
		//fali token
		//Ako soba nije rezervisana, taken je FALSE
		//u suprotnom taken ima vrednost TRUE
		boolean taken = roomService.checkIfRoomIsReserved(id);
				
		return taken;
	}
	

	@PreAuthorize("hasAuthority('EDIT_ROOM')")
	@GetMapping("/getOneRoom/{idAccomodation}/{idRoom}/{token}")
	public ResponseEntity<Room> getOneRoom(@PathVariable Long idAccomodation,@PathVariable Long idRoom,@PathVariable String token) throws Exception {
		//treba da mi vrati bas tu sonu koju trazim
		//znci nadjes bas tu sobu u tom smestaju posto moze u drugom smestaju isto biti ta soba 
		//pa da bi znao koju sobu tacno da vrati

		String usname = tokenUtils.getUserSecurity(token).getUsername();
		
		Agent ag = agentRepository.findOneByUsername(usname);
		
		
		Room room = roomService.getRoom(idRoom,token);
		if (room == null) {
			log.error("User id: "+ag.getId()+"  GET1ROOMFAIL");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		log.info("User id: "+ag.getId()+"  GET1ROOMSUCCESS");


		return new ResponseEntity<>(room, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('EDIT_ROOM')")
	@PutMapping("/editRoom/{idAccomodation}/{idRoom}/{token}")
	public ResponseEntity<Room> editAccomodation(@PathVariable Long idAccomodation,@PathVariable Long idRoom,
			@RequestBody RoomDTO roomDTO,@PathVariable String token) throws Exception {

		Room room = roomService.editRoom(idAccomodation, idRoom, roomDTO,token);
		return new ResponseEntity<>(room, HttpStatus.OK);

	}

	@PreAuthorize("hasAuthority('DEL_ROOM')")
	@DeleteMapping("/deleteRoom/{idAccomodation}/{idRoom}/{token}")
	public boolean deleteRoom(@PathVariable Long idAccomodation, @PathVariable Long idRoom,@PathVariable String token) throws Exception {

		boolean response = roomService.deleteRoom(idAccomodation, idRoom,token);
		return response;
	}
	
	@GetMapping("/getAverageRating/{idRoom}")
	public double getAverageRating(@PathVariable Long idRoom)
	{
		double rezultat = roomService.getAverageRating(idRoom);
		return rezultat ;
	}
	
	@GetMapping("/getListOfRating/{idRoom}")
	public ResponseEntity<List<Rating>> getListOfRating (@PathVariable Long idRoom)
	{
		List<Rating> ratings = new ArrayList<Rating>();
		ratings = roomService.getListOfRating(idRoom);
		
		if (ratings == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<List<Rating>>(ratings, HttpStatus.OK);
		}
		
	}
}
