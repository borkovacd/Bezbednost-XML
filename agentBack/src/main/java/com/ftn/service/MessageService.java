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

@Service
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ResponseRepository responseRepository;
	@Autowired
	private ResponseService responseService;
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private SOAPConnector soapConnector;

	public List<Message> getAllMessages(String token) throws Exception {
		
		responseService.getAllResponses(token);
		
		String username = tokenUtils.getUserSecurity(token).getUsername();
		
		GetAllMessagesRequest request = new GetAllMessagesRequest();
		
		Agent agent = agentRepository.findOneByUsername(username);
		request.setRequest("Agent request: 'Return all reservations by agent '" + username + "'");
		request.setAgentId(agent.getId());
		
		GetAllMessagesResponse response = (GetAllMessagesResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		List<Message> messages = new ArrayList<Message>();
		
		for(int i = 0; i < response.getMessagesList().size(); i++) {
			
			Message m = new Message();
			m.setId(response.getMessagesList().get(i).getId());
			User sender = userRepository.getOne(response.getMessagesList().get(i).getSender().getId());
			m.setSender(sender);
			Agent recipient = agentRepository.getOne(response.getMessagesList().get(i).getRecipient().getId());
			m.setRecipient(recipient);
			Response responseMessage = responseRepository.getOne(response.getMessagesList().get(i).getResponse().getId());
			m.setResponse(responseMessage);
			m.setText(response.getMessagesList().get(i).getText());

			messageRepository.save(m);
			messages.add(m);
			
		}
		

		return (ArrayList<Message>) messages;
	}

}
