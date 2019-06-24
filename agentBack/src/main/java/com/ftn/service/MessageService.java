package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dto.AccomodationDTO;
import com.ftn.dto.AnswerDTO;
import com.ftn.model.Accomodation;
import com.ftn.model.AdditionalServices;
import com.ftn.model.Agent;
import com.ftn.model.Category;
import com.ftn.model.City;
import com.ftn.model.Message;
import com.ftn.model.Response;
import com.ftn.model.TypeAccomodation;
import com.ftn.model.User;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.MessageRepository;
import com.ftn.repository.ResponseRepository;
import com.ftn.repository.UserRepository;
import com.ftn.security.TokenUtils;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.AccomodationSoap;
import com.ftn.webservice.files.AdditionalServicesSoap;
import com.ftn.webservice.files.CategorySoap;
import com.ftn.webservice.files.CitySoap;
import com.ftn.webservice.files.CreateAnswerRequest;
import com.ftn.webservice.files.CreateAnswerResponse;
import com.ftn.webservice.files.GetAllMessagesRequest;
import com.ftn.webservice.files.GetAllMessagesResponse;
import com.ftn.webservice.files.RegisterAccomodationRequest;
import com.ftn.webservice.files.RegisterAccomodationResponse;
import com.ftn.webservice.files.ResponseSoap;
import com.ftn.webservice.files.TypeAccomodationSoap;

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
		
		//responseService.getAllResponses(token);
		
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
			/*if(responseRepository.getOne(response.getMessagesList().get(i).getResponse().getId()) != null) {
				Response responseMessage = responseRepository.getOne(response.getMessagesList().get(i).getResponse().getId());
				m.setResponse(responseMessage);
			}*/
			m.setText(response.getMessagesList().get(i).getText());

			messageRepository.save(m);
			messages.add(m);
			
		}
		

		return (ArrayList<Message>) messages;
	}

	public void createAnswer(AnswerDTO answerDTO, String token) throws Exception {
				
		String username = tokenUtils.getUserSecurity(token).getUsername();
		Agent agent = agentRepository.findOneByUsername(username);
		
		CreateAnswerRequest request = new CreateAnswerRequest();
		
		request.setRequest("Agent request: 'Create answer by '" + username + "'");
		request.setAgentId(agent.getId());
		Long idUser = Long.parseLong(answerDTO.getReceipient());
		request.setUserId(idUser);
		Long idMessage = Long.parseLong(answerDTO.getIdMessage());
		request.setMessageId(idMessage);
		request.setText(answerDTO.getText());
		
		CreateAnswerResponse response = (CreateAnswerResponse) soapConnector
					.callWebService("https://localhost:8443/ws/accomondation", request);

		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Answer by '" + username + "' successfully created!");
		System.out.println("*****");
			
		
		Response responseMessage = new Response();
		ResponseSoap responseMessageSoap = response.getResponseMessage();
		responseMessage.setId(responseMessageSoap.getId());
		User recipient = userRepository.getOne(responseMessageSoap.getRecipient().getId());
		responseMessage.setRecipient(recipient);
		Agent sender = agentRepository.getOne(responseMessageSoap.getSender().getId());
		responseMessage.setSender(sender);
		responseMessage.setText(responseMessageSoap.getText());
		Message message = messageRepository.getOne(response.getMessageId());
		responseMessage.setMessage(message);
		
		responseRepository.save(responseMessage);
		/*Message message = messageRepository.getOne(response.getMessageId());
		message.setResponse(responseMessage);
		messageRepository.save(message);*/
		
	}

}
