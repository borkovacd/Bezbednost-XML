package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Accomodation;
import com.ftn.model.AdditionalServices;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllAccomodationsRequest;
import com.ftn.webservice.files.GetAllAccomodationsResponse;
import com.ftn.webservice.files.GetAllAdditionalServicesRequest;
import com.ftn.webservice.files.GetAllAdditionalServicesResponse;

@Service
public class AdditionalServicesService {
	
	@Autowired
	private SOAPConnector soapConnector;

	public ArrayList<AdditionalServices> getAllAdditionalServices() {
		
		GetAllAdditionalServicesRequest request = new GetAllAdditionalServicesRequest();
		request.setGetAdditionalServicesList("GetAllAdditionalServices");
		
		GetAllAdditionalServicesResponse response = (GetAllAdditionalServicesResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<AdditionalServices> as = new ArrayList<AdditionalServices>();
		
		for(int i = 0; i < response.getAdditionalServicesList().size(); i++) {
			
			AdditionalServices a = new AdditionalServices();
			a.setId(response.getAdditionalServicesList().get(i).getId());
			a.setName(response.getAdditionalServicesList().get(i).getName());
			
			as.add(a);
			
		}
		

		return (ArrayList<AdditionalServices>) as;
	}

}
