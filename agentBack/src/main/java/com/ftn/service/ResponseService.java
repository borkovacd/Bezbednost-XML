package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Agent;
import com.ftn.model.Message;
import com.ftn.model.Response;
import com.ftn.model.User;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.MessageRepository;
import com.ftn.repository.ResponseRepository;
import com.ftn.repository.UserRepository;
import com.ftn.security.TokenUtils;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllMessagesRequest;
import com.ftn.webservice.files.GetAllMessagesResponse;
import com.ftn.webservice.files.GetAllResponsesRequest;
import com.ftn.webservice.files.GetAllResponsesResponse;

@Service
public class ResponseService {
	
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ResponseRepository responseRepository;
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private SOAPConnector soapConnector;

	public List<Response> getAllResponses(String token) throws Exception {
		
		String username = tokenUtils.getUserSecurity(token).getUsername();
		
		GetAllResponsesRequest request = new GetAllResponsesRequest();
		
		Agent agent = agentRepository.findOneByUsername(username);
		request.setRequest("Agent request: 'Return all responses for agent '" + username + "'");
		request.setAgentId(agent.getId());
		
		GetAllResponsesResponse response = (GetAllResponsesResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		List<Response> responses = new ArrayList<Response>();
		
		for(int i = 0; i < response.getResponsesList().size(); i++) {
			
			Response r = new Response();
			r.setId(response.getResponsesList().get(i).getId());
			User recipient = userRepository.getOne(response.getResponsesList().get(i).getRecipient().getId());
			r.setRecipient(recipient);
			Agent sender = agentRepository.getOne(response.getResponsesList().get(i).getSender().getId());
			r.setSender(sender);
			r.setText(response.getResponsesList().get(i).getText());
			Message message = messageRepository.getOne(response.getResponsesList().get(i).getMessage().getId());
			r.setMessage(message);

			responseRepository.save(r);
			responses.add(r);
			
		}
		

		return (ArrayList<Response>) responses;
	}

}
