package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.Country;
import com.ftn.service.CountryService;

@RestController
@RequestMapping(value = "/api/country")
public class CountryController {
	@Autowired
	private CountryService countryService;
	
	

	@GetMapping("/getAllCountries")
	public ResponseEntity<List<Country>> getAllCountries() {
		
		ArrayList<Country> countries = (ArrayList<Country>) countryService.getAllCountries();
		
		return new ResponseEntity<List<Country>>(countries, HttpStatus.OK);
	}

}
