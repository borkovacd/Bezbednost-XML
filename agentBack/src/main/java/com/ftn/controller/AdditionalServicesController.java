package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.service.AdditionalServicesService;

@RestController
@RequestMapping(value = "/api/additionalServices")
public class AdditionalServicesController {
	@Autowired
	private AdditionalServicesService additionalService;

}
