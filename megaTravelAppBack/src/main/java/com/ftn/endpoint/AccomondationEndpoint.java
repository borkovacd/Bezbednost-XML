package com.ftn.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ftn.repository.AccomondationRepository;
import com.ftn.webservice_accomondation.RegisterAccomodationRequest;
import com.ftn.webservice_accomondation.RegisterAccomodationResponse;



//@Endpoint registers the class with Spring WS as a potential candidate for processing incoming SOAP messages.
@Endpoint
public class AccomondationEndpoint {
	private static final String NAMESPACE_URI = "http://ftn.com/webservice-accomondation";
	
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
		response.setResponse(accomondationRepository.findAccomodation(request.getAccomondation().getName()).getName());

		return response;
	}

}

