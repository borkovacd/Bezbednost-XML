package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.service.CityService;

@RestController
@RequestMapping(value = "/api/city")
public class CityController {
	@Autowired
	private CityService cityService;

}
