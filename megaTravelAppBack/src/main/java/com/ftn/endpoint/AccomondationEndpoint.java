package com.ftn.endpoint;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ftn.model.Accomodation;
import com.ftn.repository.AccomondationRepository;
import com.ftn.webservice.AccomodationSoap;
import com.ftn.webservice.DeleteAccomodationRequest;
import com.ftn.webservice.DeleteAccomodationResponse;
import com.ftn.webservice.EditAccomodationRequest;
import com.ftn.webservice.EditAccomodationResponse;
import com.ftn.webservice.GetAccomodationRequest;
import com.ftn.webservice.GetAccomodationResponse;
import com.ftn.webservice.GetAllAccomodationsRequest;
import com.ftn.webservice.GetAllAccomodationsResponse;
import com.ftn.webservice.RegisterAccomodationRequest;
import com.ftn.webservice.RegisterAccomodationResponse;




//@Endpoint registers the class with Spring WS as a potential candidate for processing incoming SOAP messages.
@Endpoint
public class AccomondationEndpoint {
	private static final String NAMESPACE_URI = "http://ftn.com/webservice";
	
	private AccomondationRepository accomondationRepository;
	
	@Autowired
	public AccomondationEndpoint(AccomondationRepository accomondationRepository) {
		this.accomondationRepository = accomondationRepository;
	}
	
	//@PayloadRoot is then used by Spring WS to pick the handler method based on the message’s 
	//namespace and localPart.
	//@RequestPayload indicates that the incoming message will be mapped to the method’s request parameter.
	//The @ResponsePayload annotation makes Spring WS map the returned value to the response payload.
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegisterAccomodationRequest")
	@ResponsePayload
	public RegisterAccomodationResponse getAccomondation(@RequestPayload RegisterAccomodationRequest request) {
		
		RegisterAccomodationResponse response = new RegisterAccomodationResponse();
		
		AccomodationSoap a = request.getAccomondation();
		
		Accomodation newAccomodation = new Accomodation();
		
		newAccomodation.setName(a.getName());
		
		/*com.ftn.model.City newCity = new com.ftn.model.City();
		newCity.setName(a.getCity().getName());
		newAccomodation.setCity(newCity);*/
		
		newAccomodation.setAddress(a.getAddress());
		//newAccomodation.setDescription(a.getDescription());
		
		
		accomondationRepository.save(newAccomodation);
		
		
		response.setResponse(newAccomodation.getId());

		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteAccomodationRequest")
	@ResponsePayload
	public DeleteAccomodationResponse deleteAccomodation(@RequestPayload DeleteAccomodationRequest request) {
		
		DeleteAccomodationResponse response = new DeleteAccomodationResponse();
		
		Long id = request.getDeleteAccomodationId();
		
		Accomodation a = accomondationRepository.findOneById(id);
		
		response.setDeletedAccomodationId(a.getId());
		
		accomondationRepository.delete(a);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "EditAccomodationRequest")
	@ResponsePayload
	public EditAccomodationResponse editAccomodation(@RequestPayload EditAccomodationRequest request) {
		
		EditAccomodationResponse response = new EditAccomodationResponse();
		
		Long id = request.getEditAccomodationId();
		AccomodationSoap newAccomodation = request.getEditAccomodationData();
		
		Accomodation a = accomondationRepository.findOneById(id);
		a.setName(newAccomodation.getName());
		a.setAddress(newAccomodation.getAddress());
		
		accomondationRepository.save(a);
	
		newAccomodation.setId(id);
		response.setEditedAccomodation(newAccomodation);
		
		return response;
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAccomodationRequest")
	@ResponsePayload
	public GetAccomodationResponse getAccomodation(@RequestPayload GetAccomodationRequest request) {
		
		GetAccomodationResponse response = new GetAccomodationResponse();
		
		Long id = request.getRequestedAccomodationId();
		
		Accomodation requestedAccomodation = accomondationRepository.findOneById(id);
		
		AccomodationSoap a = new AccomodationSoap();
		a.setId(requestedAccomodation.getId());
		a.setName(requestedAccomodation.getName());
		a.setAddress(requestedAccomodation.getAddress());
		
		response.setReturnedAccomodation(a);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllAccomodationsRequest")
	@ResponsePayload
	public GetAllAccomodationsResponse getAllAccomodations(@RequestPayload GetAllAccomodationsRequest request) {
		
		GetAllAccomodationsResponse response = new GetAllAccomodationsResponse();
	
		//List<AccomodationSoap> accomodations = new ArrayList<AccomodationSoap>();
		
		for(int i = 0; i < accomondationRepository.findAll().size(); i++) {
			
			AccomodationSoap a = new AccomodationSoap();
			a.setId(accomondationRepository.findAll().get(i).getId());
			a.setName(accomondationRepository.findAll().get(i).getName());
			a.setAddress(accomondationRepository.findAll().get(i).getAddress());
			
			//accomodations.add(a);
			response.getAccomodationsList().add(a);
			
		}
		
		//response.setAccomodationsList(accomodations);
		
		return response;
	}

}

