package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.AdditionalServices;
import com.ftn.model.City;
import com.ftn.model.Country;
import com.ftn.repository.CityRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllAdditionalServicesRequest;
import com.ftn.webservice.files.GetAllAdditionalServicesResponse;
import com.ftn.webservice.files.GetAllCitiesRequest;
import com.ftn.webservice.files.GetAllCitiesResponse;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	public ArrayList<City> getAllCities() {
		
		GetAllCitiesRequest request = new GetAllCitiesRequest();
		request.setRequest("Agent request: 'Get all existing cities'");
		
		GetAllCitiesResponse response = (GetAllCitiesResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<City> cities = new ArrayList<City>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all existing cities'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getCitieslist().size(); i++) {
			
			City c = new City();
			c.setId(response.getCitieslist().get(i).getId());
			c.setName(response.getCitieslist().get(i).getName());
			Country country = new Country();
			country.setId(response.getCitieslist().get(i).getCountry().getId());
			country.setName(response.getCitieslist().get(i).getCountry().getName());
			c.setCountry(country);
			
			cities.add(c);
			
		}
		

		return (ArrayList<City>) cities;
	}
}
