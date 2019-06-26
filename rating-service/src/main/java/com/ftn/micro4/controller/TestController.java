package com.ftn.micro4.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/test")
public class TestController {

	@GetMapping(value="/method")
	public String Testing() {
		return "Test is working";
	}
	
}
