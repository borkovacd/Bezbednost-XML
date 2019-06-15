package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Country;
import com.ftn.repository.CountryRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllCountriesRequest;
import com.ftn.webservice.files.GetAllCountriesResponse;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	public ArrayList<Country> getAllCountries() {
		
		GetAllCountriesRequest request = new GetAllCountriesRequest();
		request.setRequest("Agent request: 'Get all existing countries'");
		
		GetAllCountriesResponse response = (GetAllCountriesResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<Country> countries = new ArrayList<Country>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all existing countries'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getCountrieslist().size(); i++) {
			
			Country c = new Country();
			c.setId(response.getCountrieslist().get(i).getId());
			c.setName(response.getCountrieslist().get(i).getName());
			
			countryRepository.save(c);
			countries.add(c);
			
		}
		

		return (ArrayList<Country>) countries;
	}

}
