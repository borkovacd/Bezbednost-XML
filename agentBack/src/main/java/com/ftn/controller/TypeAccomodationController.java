package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.service.TypeAccomodationService;

@RestController
@RequestMapping(value = "/api/typeAccomodation")
public class TypeAccomodationController {

	@Autowired
	private TypeAccomodationService typeService;
}
