package com.ftn.controller;

import java.util.HashSet;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.ftn.model.User;

import com.ftn.modelDTO.UserDTO;

import com.ftn.repository.UserRepository;
import com.ftn.service.UserService;


@RestController
@RequestMapping(value = "/api/user")
public class UserControler {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRep;
	
	
	
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
	
	@RequestMapping(value="/registration",	method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<UserDTO> registrate(@RequestBody UserDTO userDto) {
		
		System.out.println(userDto.getEmail());
		System.out.println("usao da registruje");
		boolean response = userService.exists(userDto.getEmail());
		//if(response == true){
			//System.out.println("nasao istog");
			//return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

		//}else{
			
			//if(userDto.getPassword().equals(userDto.getRePassword())) {
			
			User u = new User();
			
			u.setCity(userDto.getCity());
			u.setEmail(userDto.getEmail());
			u.setFirstName(userDto.getFirstName());
			u.setLastName(userDto.getLastName());
			u.setUsername(userDto.getUsername());
			u.setPassword(userDto.getPassword());
			
			String pass = u.getPassword();
			
			String salt = BCrypt.gensalt();
			String passwordHashed = BCrypt.hashpw(pass, salt);
			
			u.setPassword(passwordHashed);
			
		
			
			userRep.save(u);
			System.out.println("upisao");
			
			//} else {

			//	return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
			//}
			
			return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
		}
		
	//}
}
