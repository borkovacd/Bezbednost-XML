package com.ftn.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dto.AccomodationDTO;
import com.ftn.dto.RoomDTO;
import com.ftn.model.Accomodation;
import com.ftn.model.Room;
import com.ftn.service.RoomService;

@RestController
@RequestMapping(value = "/api/room")
public class RoomControler {
	
	@Autowired
	private RoomService roomService;

	@PostMapping("/createRoom/{idAccomodation}")
	public void createAccomodation(@RequestBody RoomDTO roomDTO, @PathVariable Long idAccomodation) {

		roomService.createRoom(roomDTO, idAccomodation);
	}

	@GetMapping("/getAllRooms/{idAccomodation}")
	public ResponseEntity<ArrayList<Room>> getAllRooms(@PathVariable Long idAccomodation) {

		ArrayList<Room> rooms = roomService.getAllRooms(idAccomodation);

		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}
	@GetMapping("/checkIfReservedRoom/{id}")
	public boolean checkIfReservedRoom(@PathVariable Long id) {
		
		//Ako soba nije rezervisana, taken je FALSE
		//u suprotnom taken ima vrednost TRUE
		boolean taken = roomService.checkIfRoomIsReserved(id);
				
		return taken;
	}
	
	@GetMapping("/getOneRoom/{idAccomodation}/{idRoom}")
	public ResponseEntity<Room> getOneRoom(@PathVariable Long idAccomodation,@PathVariable Long idRoom) {
		//treba da mi vrati bas tu sonu koju trazim
		//znci nadjes bas tu sobu u tom smestaju posto moze u drugom smestaju isto biti ta soba 
		//pa da bi znao koju sobu tacno da vrati

		Room room = roomService.getRoom(idRoom);
		if (room == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(room, HttpStatus.OK);
	}
	
	@PutMapping("/editRoom/{idAccomodation}/{idRoom}")
	public ResponseEntity<Room> editAccomodation(@PathVariable Long idAccomodation,@PathVariable Long idRoom,
			@RequestBody RoomDTO roomDTO) {

		Room room = roomService.editRoom(idAccomodation, idRoom, roomDTO);
		return new ResponseEntity<>(room, HttpStatus.OK);

	}
	
	@DeleteMapping("/deleteRoom/{idAccomodation}/{idRoom}")
	public ResponseEntity<String> deleteRoom(@PathVariable Long idAccomodation, @PathVariable Long idRoom) {

		String response = roomService.deleteRoom(idAccomodation, idRoom);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
