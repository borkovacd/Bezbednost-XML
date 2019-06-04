package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.dto.AccomodationDTO;
import com.ftn.service.AccomodationService;

@RestController
@RequestMapping(value = "/api/accomodation")
public class AccomodationController {

	@Autowired
	private AccomodationService accomodationService;
	
	public void createAccomodation(@RequestBody AccomodationDTO accDTO){
		
	}
}
