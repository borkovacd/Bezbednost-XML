package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.ftn.model.User;
import com.ftn.service.UserService;



@RestController
@RequestMapping(value = "/api/user")
public class UserControler {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login",	method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<User> logIn(@RequestBody User user) {
		boolean response = userService.logIn(user);
		if(response == true){
			return new ResponseEntity<User>(user, HttpStatus.OK);

		}else{
			return new ResponseEntity<User>( HttpStatus.NO_CONTENT);
		}
		
	}
}
