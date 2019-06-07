package com.ftn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.TypeAccomodation;
import com.ftn.service.TypeAccomodationService;

@RestController
@RequestMapping(value = "/api/typeAccomodation")
public class TypeAccomodationController {

	@Autowired
	private TypeAccomodationService typeService;
	
	@GetMapping("/getAllTypes")
	public ResponseEntity<List<TypeAccomodation>> getTypes() {
		List<TypeAccomodation> list = null;
		//istp i ovde
		return new ResponseEntity<List<TypeAccomodation>>(list, HttpStatus.OK);
	}
}
