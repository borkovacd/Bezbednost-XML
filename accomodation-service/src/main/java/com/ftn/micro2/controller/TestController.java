package com.ftn.micro2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/accomodation/test")
public class TestController 
{
	@RequestMapping(value="/method1", method = RequestMethod.GET)
	public String Testing() 
	{
		
		System.out.println("Usao u metodu Test");
		return "Test working";
		
	}
}
