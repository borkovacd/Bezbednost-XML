package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.TypeAccomodation;

import com.ftn.repository.TypeAccomodationRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllAccomodationTypesRequest;
import com.ftn.webservice.files.GetAllAccomodationTypesResponse;

@Service
public class TypeAccomodationService {
	
	@Autowired
	private TypeAccomodationRepository typeRepository;
	
	@Autowired
	private SOAPConnector soapConnector;

	public List<TypeAccomodation> getAllTypes() {
		
		GetAllAccomodationTypesRequest request = new GetAllAccomodationTypesRequest();
		request.setRequest("Agent request: 'Get all existing accomodation types'");
		
		GetAllAccomodationTypesResponse response = (GetAllAccomodationTypesResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<TypeAccomodation> accomodationTypes = new ArrayList<TypeAccomodation>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all existing accomodation types'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getAccomodationTypesList().size(); i++) {
			
			TypeAccomodation ta = new TypeAccomodation();
			ta.setId(response.getAccomodationTypesList().get(i).getId());
			ta.setName(response.getAccomodationTypesList().get(i).getName());
			
			typeRepository.save(ta);
			accomodationTypes.add(ta);
			
		}
		

		return (ArrayList<TypeAccomodation>) accomodationTypes;
	}

}
