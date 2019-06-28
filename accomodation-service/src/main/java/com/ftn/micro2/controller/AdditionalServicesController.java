package com.ftn.micro2.controller;

import java.util.ArrayList;
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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro2.config.TokenUtils;
import com.ftn.micro2.model.AdditionalServices;
import com.ftn.micro2.model.User;
import com.ftn.micro2.repository.UserRepository;
import com.ftn.micro2.service.AdditionalServicesService;

@RestController
@RequestMapping("/api/accomodation/addServ")
public class AdditionalServicesController 
{
	private static final Logger log = LoggerFactory.getLogger(AdditionalServicesController.class);

	@Autowired
	AdditionalServicesService addServ ;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	UserRepository userRepository;
	
	
	// METODA kojom se DODAJE novi Dodatni servis
	@PreAuthorize("hasAuthority('ADD_SERVICE')")
	@RequestMapping(value="/addNewAdditionalService",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addNewAdditionalService(ServletRequest request, @RequestBody AdditionalServices add) throws Exception
	{
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		log.info("User id: {} CREAADDSERV",u.getId());

		
		AdditionalServices addService = addServ.findByName(add.getName());
		
		// ukoliko servis sa tim imenom vec postoji, ne mozes ga sacuvati
		if (addService != null)
		{
			log.error("User id: {} CREAADDSERVERROR",u.getId());

			return false;
		}
		else
		{
			addService = new AdditionalServices();
			addService.setName(add.getName());
			addServ.save(add);
			log.info("User id: {} CREAADDSERVSUCCESS",u.getId());

			return true;
		}
	}
	
	// METODA kojom se UKLANJA postojeci Dodatni servis
	@PreAuthorize("hasAuthority('DEL_SERVICE')")
	@PostMapping(value = "/removeAdditionalService", consumes = "application/json")
	public ResponseEntity<List<AdditionalServices>> removeAdditionalService(ServletRequest request, @RequestBody AdditionalServices add) throws Exception
	{
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		log.info("User id: {} DELADDSERV",u.getId());

		
		
		AdditionalServices addService = addServ.findByName(add.getName());
		
		// ukoliko servis sa tim imenom ne postoji, ne mozes ga obrisati
		if (addService == null)
		{
			log.error("User id: {} DELADDSERVERROR",u.getId());

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			addServ.deleteByName(add.getName());
			log.info("User id: {} DELADDSERVSUCCESS",u.getId());

			return new ResponseEntity<>(addServ.getAll(), HttpStatus.OK);
		}
	}
	
	// METODA - lista svih dodatnih servisa
	@PreAuthorize("hasAuthority('ADD_SERVICE')")
	@RequestMapping(value = "/getAdditionalServices",method = RequestMethod.GET)
	public ResponseEntity<List<AdditionalServices>> getAdditionalServices(ServletRequest request) throws Exception
	{
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		log.info("User id: {} GETADDSER",u.getId());

		
		
		return new ResponseEntity<>(addServ.getAll(), HttpStatus.OK);
	}
	
}
