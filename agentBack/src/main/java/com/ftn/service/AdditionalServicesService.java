package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.AdditionalServices;
import com.ftn.repository.AdditionalServicesRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllAdditionalServicesRequest;
import com.ftn.webservice.files.GetAllAdditionalServicesResponse;

@Service
public class AdditionalServicesService {
	
	@Autowired
	private AdditionalServicesRepository additionalServicesRepository;
	
	@Autowired
	private SOAPConnector soapConnector;

	public ArrayList<AdditionalServices> getAllAdditionalServices() {
		
		GetAllAdditionalServicesRequest request = new GetAllAdditionalServicesRequest();
		request.setRequest("Agent request: 'Get all existing additional services'");
		
		GetAllAdditionalServicesResponse response = (GetAllAdditionalServicesResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<AdditionalServices> additionalServices = new ArrayList<AdditionalServices>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all existing additional services'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getAdditionalServicesList().size(); i++) {
			
			AdditionalServices as = new AdditionalServices();
			as.setId(response.getAdditionalServicesList().get(i).getId());
			as.setName(response.getAdditionalServicesList().get(i).getName());
			
			additionalServicesRepository.save(as);
			additionalServices.add(as);
			
		}
		

		return (ArrayList<AdditionalServices>) additionalServices;
	}

}
