package com.ftn.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dto.AccomodationDTO;
import com.ftn.model.Room;

@RestController
@RequestMapping(value = "/api/room")
public class RoomControler {

	@PostMapping("/createRoom/{idAccommodation}")
	public void createAccomodation(@RequestBody AccomodationDTO accDTO, @PathVariable Long idAccommodation) {

		// ovde treba da doda sobu u smestaj koji je poslat preko id sa fronta
		// inicijalno stavis da je oba active=false posto nema cenu
		// i da jos uvek nije rezervisana reserved=false

	}

	@GetMapping("/getAllRooms/{idAccommodation}")
	public ResponseEntity<ArrayList<Room>> getAllRooms(@PathVariable Long idAccommodation) {

		// treba da vrati sve sobe za taj smestaj
		return null;
	}

}
