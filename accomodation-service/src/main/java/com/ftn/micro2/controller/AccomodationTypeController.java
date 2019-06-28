package com.ftn.micro2.controller;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro2.config.TokenUtils;
import com.ftn.micro2.model.AccomodationType;
import com.ftn.micro2.model.User;
import com.ftn.micro2.repository.UserRepository;
import com.ftn.micro2.service.AccomodationTypeService;

@RestController
@RequestMapping("/api/accomodation/accType")
public class AccomodationTypeController 
{
	private static final Logger log = LoggerFactory.getLogger(AccomodationTypeController.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AccomodationTypeService service;
	
	@Autowired
	TokenUtils tokenUtils;
	
	// dodaje novi tip smestaja
	@PreAuthorize("hasAuthority('ADD_TYPE')")
	@RequestMapping(value="/addNewAccomodationType",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addNewAccomodationType(ServletRequest request, @RequestBody AccomodationType acc) throws Exception
	{
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		log.info("User id: {} CREAACCTYPE",u.getId());
		System.out.println("Ovo je user " + u.getUsername());
		
		AccomodationType accType = service.findByName(acc);
		if(accType != null){
			log.error("User id: {} CREAACCTYPEERROR",u.getId());
			return false;
		}
		else 
		{
			accType = new AccomodationType();
			accType.setName(acc.getName());
			service.save(acc);
			log.info("User id: {} CREAACCTYPSUCCESS",u.getId());
			return true;
		}
	}
	
	// brise postojeci tip smestaja
	@PreAuthorize("hasAuthority('DEL_TYPE')")
	@PostMapping(value = "/removeAccomodationType", consumes = "application/json")
	public ResponseEntity<List<AccomodationType>> removeAccType(ServletRequest request, @RequestBody AccomodationType acc) throws Exception
	{
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");
		
		token = token.substring(1, token.length()-1);
		
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		log.info("User id: {} DELACCTYPE",u.getId());
		System.out.println("Ovo je user " + u.getUsername());
		
		AccomodationType accType = service.findByName(acc);
		if(accType == null){
			log.error("User id: {} DELACCTYPEERROR",u.getId());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else 
		{
			log.info("User id: {} DELACCTYPESUCCESS",u.getId());

			service.deleteByName(acc.getName());
			return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAuthority('ADD_TYPE')")
	@GetMapping(value = "/getAccomodationTypes")
	public ResponseEntity<List<AccomodationType>> getServices(ServletRequest request) throws Exception
	{
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		log.info("User id: {} GETACCTYPE",u.getId());

		System.out.println("Ovo je user " + u.getUsername());
		log.info("User id: {} GETACCTYPESUCCESS",u.getId());

		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
}
