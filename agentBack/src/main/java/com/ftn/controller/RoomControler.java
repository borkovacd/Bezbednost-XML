package com.ftn.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dto.RoomDTO;
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

}
