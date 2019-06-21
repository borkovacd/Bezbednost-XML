package com.ftn.micro2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro2.model.AdditionalServices;
import com.ftn.micro2.service.AdditionalServicesService;

@RestController
@RequestMapping("/api/accomodation/addServ")
public class AdditionalServicesController 
{
	@Autowired
	AdditionalServicesService addServ ;
	
	
	// METODA kojom se DODAJE novi Dodatni servis
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/addNewAdditionalService",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addNewAdditionalService(@RequestBody AdditionalServices add)
	{
		AdditionalServices addService = addServ.findByName(add);
		
		// ukoliko servis sa tim imenom vec postoji, ne mozes ga sacuvati
		if (addService != null)
		{
			return false;
		}
		else
		{
			addService = new AdditionalServices();
			addService.setName(add.getName());
			addServ.save(add);
			return true;
		}
	}
	
	// METODA kojom se UKLANJA postojeci Dodatni servis
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "/removeAdditionalService", consumes = "application/json")
	public ResponseEntity<List<AdditionalServices>> removeAdditionalService(@RequestBody AdditionalServices add)
	{
		AdditionalServices addService = addServ.findByName(add);
		
		// ukoliko servis sa tim imenom ne postoji, ne mozes ga obrisati
		if (addService == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			addServ.deleteByName(add.getName());
			return new ResponseEntity<>(addServ.getAll(), HttpStatus.OK);
		}
	}
	
	// METODA - lista svih dodatnih servisa
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/getAdditionalServices")
	public ResponseEntity<List<AdditionalServices>> getAdditionalServices()
	{
		return new ResponseEntity<>(addServ.getAll(), HttpStatus.OK);
	}
	
}
