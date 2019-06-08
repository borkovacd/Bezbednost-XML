package com.ftn.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.Accomodation;
import com.ftn.model.AdditionalServices;
import com.ftn.service.AdditionalServicesService;



@RestController
@RequestMapping(value = "/api/additionalServices")
public class AdditionalServicesController {
	@Autowired
	private AdditionalServicesService additionalService;
	
	@GetMapping("/allAditionalServices")
	public ResponseEntity<?> getAditionalSevices() {
		
		ArrayList<AdditionalServices> services = additionalService.getAllAdditionalServices();
		
		//u tu listu treba sa glavnog back-a ubaciti sve usluge i vratiti na front 
		return new ResponseEntity<>(services, HttpStatus.OK);

		
	}

}
