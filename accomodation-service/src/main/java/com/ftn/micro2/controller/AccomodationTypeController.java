package com.ftn.micro2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro2.model.AccomodationType;
import com.ftn.micro2.service.AccomodationTypeService;

@RestController
@RequestMapping("/accType")
public class AccomodationTypeController 
{
	@Autowired
	AccomodationTypeService service ;
	
	// dodaje novi tip smestaja
	@PostMapping(value = "/addNewAccomodationType", consumes = "application/json")
	public ResponseEntity<List<AccomodationType>> addNewAccomodationType(@RequestBody AccomodationType acc)
	{
		AccomodationType accType = service.findByName(acc);
		if(accType != null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else 
		{
			service.save(acc);
			return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
		}
	}
	
	// brise postojeci tip smestaja
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
	
	@GetMapping(value = "/getAccomodationTypes")
	public ResponseEntity<List<AccomodationType>> getServices()
	{
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
}
