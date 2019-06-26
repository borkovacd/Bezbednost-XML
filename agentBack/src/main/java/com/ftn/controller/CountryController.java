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

import com.ftn.model.Country;
import com.ftn.service.CountryService;

@RestController
@RequestMapping(value = "/api/country")
public class CountryController {
	private static final Logger log = LoggerFactory.getLogger(CountryController.class);

	@Autowired
	private CountryService countryService;
	
	

	@PreAuthorize("hasAuthority('READ_COUNTRY')")
	@GetMapping("/getAllCountries")
	public ResponseEntity<List<Country>> getAllCountries() {
		log.info("GETCOUNT");

		ArrayList<Country> countries = (ArrayList<Country>) countryService.getAllCountries();
		
		return new ResponseEntity<List<Country>>(countries, HttpStatus.OK);
	}

}
