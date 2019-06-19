package com.ftn.micro3.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/test")
public class TestController {
	
	@RequestMapping(value="/method", method = RequestMethod.GET)
	public String Testing() 
	{
		
		System.out.println("Usao u metodu Test");
		return "Test working";
		
	}

}
