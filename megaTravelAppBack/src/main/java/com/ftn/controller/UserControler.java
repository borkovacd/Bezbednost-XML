package com.ftn.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.ftn.model.User;
import com.ftn.model.UserToken;
import com.ftn.modelDTO.UserDTO;
import com.ftn.repository.UserRepository;
import com.ftn.security.LoggerUtils;
import com.ftn.security.TokenUtils;
import com.ftn.service.RoleService;
import com.ftn.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserControler {

	private static final Logger log = LoggerFactory.getLogger(UserControler.class);
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRep;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	TokenUtils tokenUtils;
	
	private boolean field = false;


	@RequestMapping(value = "/checkIfMailExists/{email}", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public boolean checkCommunication(@PathVariable String email) {

		boolean response = userService.checkMailExistence(email);
		return response;
	}
	
	//@PreAuthorize("hasRole('USER')") 
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logOutUser() {
		log.debug("LOGOUT");

		System.out.println("usao ovde");
		SecurityContextHolder.clearContext();
		
	}
	
	@RequestMapping(value = "/ssl-test", method = RequestMethod.GET)
	public String TestSSl() {

		return "SSL WORKS";
	}
	
	@RequestMapping(value = "/communicate/{message}", method = RequestMethod.GET)
	public String communicateMethod(@PathVariable String message) {

		System.out.println(message);
		return "Central module responded! Got message: " + message;
	}
	
	@RequestMapping(value = "/loggedUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getLoggedUserEmail(@RequestBody String token) {

		String email = null;
		try {
			email = tokenUtils.getUserSecurity(token).getUsername();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User u = userService.findByEmail(email);
		
		System.out.println(email);
		log.info("user id:{} LOGEDUSER",u.getId());
		
		
	}

}
