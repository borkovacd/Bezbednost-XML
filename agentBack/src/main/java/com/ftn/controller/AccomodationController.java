package com.ftn.controller;

import java.util.ArrayList;

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

import com.ftn.dto.AccomodationDTO;
import com.ftn.model.Accomodation;
import com.ftn.model.Room;
import com.ftn.service.AccomodationService;

@RestController
@RequestMapping(value = "/api/accomodation")
public class AccomodationController {

	@Autowired
	private AccomodationService accomodationService;
	
	
	@PreAuthorize("hasAuthority('ADD_ACC')")
	@PostMapping("/createAccomodation/{token}")
	public void createAccomodation(@RequestBody AccomodationDTO accDTO, @PathVariable String token) throws Exception {

		accomodationService.create(accDTO, token);

	}
	

	@PreAuthorize("hasAuthority('DEL_ACC')")
	@DeleteMapping("/deleteAccomodation/{id}")
	public boolean deleteAccomodation(@PathVariable Long id) {

		boolean response = accomodationService.delete(id);
		return response;
	}


	@PreAuthorize("hasAuthority('EDIT_ACC')")
	@PutMapping("/editAccomodation/{token}/{id}")
	public ResponseEntity<Accomodation> editAccomodation(@PathVariable String token, @PathVariable Long id,
			@RequestBody AccomodationDTO accomodationDTO) throws Exception {

		Accomodation accomodation = accomodationService.edit(token, id, accomodationDTO);
		return new ResponseEntity<>(accomodation, HttpStatus.OK);

	}


	@PreAuthorize("hasAuthority('EDIT_ACC')")
	@GetMapping("/getAllAccomodations/{token}")
	public ResponseEntity<?> getAccomodations(@PathVariable String token) throws Exception {
		
		//token = token.substring(1,token.length()-1).toString();
		
		System.out.println("tokencic je: " + token);
		ArrayList<Accomodation> accomodations = accomodationService.getAllAccomodation(token);

		return new ResponseEntity<>(accomodations, HttpStatus.OK);
	}

	// Treba da proverim

	@PreAuthorize("hasAuthority('EDIT_ACC')")
	@GetMapping("/getAccomodation/{id}")
	public ResponseEntity<Accomodation> getAccomodation(@PathVariable Long id) {
		Accomodation accomodation = accomodationService.getAccomodation(id);
		if (accomodation == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(accomodation, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('EDIT_ACC')")
	@GetMapping("/checkIfReservedAccomodation/{id}")
	public boolean checkIfReservedAccomodation(@PathVariable Long id) {
		
		//Ako smestaj ne poseduje rezervisane sobe, taken je FALSE
		//u suprotnom taken ima vrednost TRUE
		boolean taken = accomodationService.checkIfAccommodationIsReserved(id);
		
		return taken;
	}

}
