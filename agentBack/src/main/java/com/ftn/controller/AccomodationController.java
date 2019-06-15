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
import com.ftn.model.Accomodation;
import com.ftn.model.Room;
import com.ftn.service.AccomodationService;

@RestController
@RequestMapping(value = "/api/accomodation")
public class AccomodationController {

	@Autowired
	private AccomodationService accomodationService;

	@PostMapping("/createAccomodation/{idAgent}")
	public void createAccomodation(@RequestBody AccomodationDTO accDTO,@PathVariable Long idAgent) {

		accomodationService.create(accDTO, idAgent);

	}

	//Treba da proverim
	@DeleteMapping("/deleteAccomodation/{id}")
	public ResponseEntity<String> deleteAccomodation(@PathVariable Long id) {

		String response = accomodationService.delete(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//Treba da proverim
	@PutMapping("/editAccomodation/{idAgent}/{id}")
	public ResponseEntity<Accomodation> editAccomodation(@PathVariable Long idAgent, @PathVariable Long id,
			@RequestBody AccomodationDTO accomodationDTO) {

		Accomodation accomodation = accomodationService.edit(idAgent, id, accomodationDTO);
		return new ResponseEntity<>(accomodation, HttpStatus.OK);

	}

	@GetMapping("/getAllAccomodations/{idAgent}")
	public ResponseEntity<?> getAccomodations(@PathVariable Long idAgent) {
		ArrayList<Accomodation> accomodations = accomodationService.getAllAccomodation(idAgent);

		return new ResponseEntity<>(accomodations, HttpStatus.OK);
	}

	//Treba da proverim
	@GetMapping("/getAccomodation/{id}")
	public ResponseEntity<Accomodation> getAccomodation(@PathVariable Long id) {

		Accomodation accomodation = accomodationService.getAccomodation(id);
		if (accomodation == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(accomodation, HttpStatus.OK);
	}
	
	//Nije zavrseno for now
	@GetMapping("/getAccomodationRooms/{id}")
	public ResponseEntity<?> getAccomodationRooms(@PathVariable Long id) {

		ArrayList<Room> rooms = accomodationService.getAllAccomodationRooms(id);

		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}

}
