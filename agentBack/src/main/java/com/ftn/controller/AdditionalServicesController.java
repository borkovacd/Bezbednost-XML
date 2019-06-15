package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.AdditionalServices;
import com.ftn.service.AdditionalServicesService;



@RestController
@RequestMapping(value = "/api/additionalServices")
public class AdditionalServicesController {
	@Autowired
	private AdditionalServicesService additionalService;
	
	@GetMapping("/allAditionalServices")
	public ResponseEntity<List<AdditionalServices>> getAllAdditionalServices() {
		
		ArrayList<AdditionalServices> additionalServices = (ArrayList<AdditionalServices>) additionalService.getAllAdditionalServices();
		
		return new ResponseEntity<List<AdditionalServices>>(additionalServices, HttpStatus.OK);
	}

}
