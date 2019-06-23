package com.ftn.micro2.controller;

import java.util.List;

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

import com.ftn.micro2.model.AccomodationType;
import com.ftn.micro2.service.AccomodationTypeService;

@RestController
@RequestMapping("/api/accomodation/accType")
public class AccomodationTypeController 
{
	@Autowired
	AccomodationTypeService service ;
	
	// dodaje novi tip smestaja
	@PreAuthorize("hasAuthority('ADD_TYPE')")
	@RequestMapping(value="/addNewAccomodationType",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addNewAccomodationType(@RequestBody AccomodationType acc)
	{
		AccomodationType accType = service.findByName(acc);
		if(accType != null)
			return false;
		else 
		{
			accType = new AccomodationType();
			accType.setName(acc.getName());
			service.save(acc);
			return true;
		}
	}
	
	// brise postojeci tip smestaja
	@PreAuthorize("hasAuthority('REMOVE_TYPE')")
	@PostMapping(value = "/removeAccomodationType", consumes = "application/json")
	public ResponseEntity<List<AccomodationType>> removeAccType(@RequestBody AccomodationType acc)
	{
		AccomodationType accType = service.findByName(acc);
		if(accType == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else 
		{
			service.deleteByName(acc.getName());
			return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAuthority('ADD_TYPE')")
	@GetMapping(value = "/getAccomodationTypes")
	public ResponseEntity<List<AccomodationType>> getServices()
	{
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
}
