package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.service.CountryService;

@RestController
@RequestMapping(value = "/api/country")
public class CountryController {
	@Autowired
	private CountryService countryService;

}
