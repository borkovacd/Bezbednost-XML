package com.ftn.controller;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.Role;
import com.ftn.model.CertificateModel;
import com.ftn.model.SubjectSoftware;
import com.ftn.model.User;

import com.ftn.modelDTO.UserDTO;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.UserRepository;
import com.ftn.service.RoleService;
import com.ftn.service.UserService;


@RestController
@RequestMapping(value = "/api/user")
public class UserControler {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRep;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@RequestMapping(value="/login",	method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<User> logIn(@RequestBody User user) {
		
		boolean response = userService.logIn(user);
		
		System.out.println(user.getPassword());
		
		if(response == true){
			
		User userNew = userService.findByEmail(user.getEmail());
		
		if(BCrypt.checkpw(user.getPassword(), userNew.getPassword())) {
		
	//		Authentication authentication = authManager
	//				.authenticate(new UsernamePasswordAuthenticationToken(userNew, user.getPassword()));
			


	//		SecurityContextHolder.getContext().setAuthentication(authentication);
			
			
			/*
			for (GrantedAuthority a : ud.getAuthorities()) {
				Role s = (Role) a;
				System.out.println(s.getName());
			}
			*/
			
	//		 System.out.println(user1.getEmail());
			 
		//	 System.out.println(cud.getUsername());
			// System.out.println(cud.getPassword());
			
			
			return new ResponseEntity<User>(userNew, HttpStatus.OK);

		//final Authentication authentication = authenticationManager
		//		.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

		// Ubaci username + password u kontext
		//SecurityContextHolder.getContext().setAuthentication(authentication);
			
			
		
	} else {
		return new ResponseEntity<User>( HttpStatus.NO_CONTENT);
	}
		} else {
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
			
			u.setRoles(Arrays.asList(roleService.findByName("ROLE_USER")));
			
			userRep.save(u);
			System.out.println("upisao");
			
			return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
		}
		
	//}
	
	@RequestMapping(value="/checkIfMailExists/{email}",	method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public boolean checkCommunication(@PathVariable String email) {
		
		boolean response = userService.checkMailExistence(email);
		return response;
	}
}
