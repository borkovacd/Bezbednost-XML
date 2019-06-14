package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.City;
import com.ftn.service.CityService;

@RestController
@RequestMapping(value = "/api/city")
public class CityController {
	
	@Autowired
	private CityService cityService;

	@GetMapping("/getAllCities")
	public ResponseEntity<List<City>> getCity() {
		
		ArrayList<City> cities = (ArrayList<City>) cityService.getAllCities();
		
		return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}

}
