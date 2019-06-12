package com.ftn.micro1.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@RequestMapping(value="/testString", method = RequestMethod.GET)
	public String Testing() {
		
		System.out.println("usao u metodu");
		return "Test working";
		
	}

}
