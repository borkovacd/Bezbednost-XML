package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.TypeAccomodation;
import com.ftn.service.TypeAccomodationService;

@RestController
@RequestMapping(value = "/api/typeAccomodation")
public class TypeAccomodationController {
	private static final Logger log = LoggerFactory.getLogger(TypeAccomodationController.class);

	@Autowired
	private TypeAccomodationService typeService;
	
	
	@PreAuthorize("hasAuthority('READ_TYPE')")
	@GetMapping("/getAllTypes")
	public ResponseEntity<List<TypeAccomodation>> getTypes() {
		
		ArrayList<TypeAccomodation> accomodationTypes = (ArrayList<TypeAccomodation>) typeService.getAllTypes();

		
		return new ResponseEntity<List<TypeAccomodation>>(accomodationTypes, HttpStatus.OK);
	}
}
