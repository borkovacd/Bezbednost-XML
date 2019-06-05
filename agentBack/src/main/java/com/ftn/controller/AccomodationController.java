package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.dto.AccomodationDTO;
import com.ftn.service.AccomodationService;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.Accomodation;
import com.ftn.webservice.files.City;
import com.ftn.webservice.files.Category;
import com.ftn.webservice.files.RegisterAccomodationRequest;
import com.ftn.webservice.files.RegisterAccomodationResponse;
import com.ftn.webservice.files.TypeAccomodation;

@RestController
@RequestMapping(value = "/api/accomodation")
public class AccomodationController {

	@Autowired
	private AccomodationService accomodationService;
	
	@Autowired 
	private SOAPConnector soapConnector;
	
	@PostMapping("/createAccomodation")
	public void createAccomodation(@RequestBody AccomodationDTO accDTO){
		
		RegisterAccomodationRequest request = new RegisterAccomodationRequest();
		
        Accomodation a = new Accomodation();
        
        a.setName(accDTO.getName());
        City city = new City();
        city.setName(accDTO.getCity());
        a.setCity(city);
        a.setAddress(accDTO.getAddress());
        TypeAccomodation typeAccomodation = new TypeAccomodation();
        typeAccomodation.setName(accDTO.getType());
        a.setTypeAccomodation(typeAccomodation);
        Category category = new Category();
        category.setName(accDTO.getCategory());
        a.setCategory(category);
        a.setDescription(accDTO.getDescription());
        //a.setCapacity(accDTO.getCapacity());
        a.setPic(accDTO.getImage());
        
        request.setAccomondation(a);
        
        
        RegisterAccomodationResponse response =(RegisterAccomodationResponse) soapConnector.callWebService("https://localhost:8443/ws/accomondation", request);
        
        
        System.out.println("Response : "+response.getResponse());
	}
}
