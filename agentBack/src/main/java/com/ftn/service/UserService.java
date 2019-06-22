package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Agent;
import com.ftn.model.User;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.UserRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.ClientStatus;
import com.ftn.webservice.files.GetAllAgentsRequest;
import com.ftn.webservice.files.GetAllAgentsResponse;
import com.ftn.webservice.files.GetAllUsersRequest;
import com.ftn.webservice.files.GetAllUsersResponse;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	public ArrayList<User> getAllUsers() {
		
		GetAllUsersRequest request = new GetAllUsersRequest();
		request.setRequest("Agent request: 'Get all existing users'");
		
		GetAllUsersResponse response = (GetAllUsersResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<User> users = new ArrayList<User>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all existing users'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getUserslist().size(); i++) {
			
			User u = new User();
			u.setId(response.getUserslist().get(i).getId());
			u.setFirstName(response.getUserslist().get(i).getFirstName());
			u.setLastName(response.getUserslist().get(i).getLastName());
			u.setUsername(response.getUserslist().get(i).getUsername());
			u.setPassword(response.getUserslist().get(i).getPassword());
			u.setEmail(response.getUserslist().get(i).getEmail());
			u.setCity(response.getUserslist().get(i).getCity());
			ClientStatus clientStatus = response.getUserslist().get(i).getClientStatus();
			if(clientStatus.equals("AKTIVAN")) {
				u.setStatus(com.ftn.enums.ClientStatus.AKTIVAN);
			} else if (clientStatus.equals("NEAKTIVAN")) {
				u.setStatus(com.ftn.enums.ClientStatus.NEAKTIVAN);
			} else if (clientStatus.equals("BLOKIRAN")) {
				u.setStatus(com.ftn.enums.ClientStatus.BLOKIRAN); 
			} else {
				u.setStatus(com.ftn.enums.ClientStatus.UKLONJEN);
			}
			u.setEnabled(response.getUserslist().get(i).isEnabled());
			u.setNonLocked(response.getUserslist().get(i).isNonLocked());
			
			userRepository.save(u);
			users.add(u);
			
		}
		

		return (ArrayList<User>) users;
	}

}
