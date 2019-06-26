package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.Agent;
import com.ftn.model.User;
import com.ftn.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping(value = "/api/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getUsers() {
		
		ArrayList<User> users = (ArrayList<User>) userService.getAllUsers();
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	

}
