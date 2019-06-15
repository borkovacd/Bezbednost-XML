package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dto.AgentDTO;
import com.ftn.model.Agent;
import com.ftn.repository.AgentRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllAgentsRequest;
import com.ftn.webservice.files.GetAllAgentsResponse;
import com.ftn.webservice.files.GetAllCitiesRequest;
import com.ftn.webservice.files.GetAllCitiesResponse;

@Service
public class AgentService {

	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	public ArrayList<Agent> getAllAgents() {
		
		GetAllAgentsRequest request = new GetAllAgentsRequest();
		request.setRequest("Agent request: 'Get all existing agents'");
		
		GetAllAgentsResponse response = (GetAllAgentsResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<Agent> agents = new ArrayList<Agent>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all existing agents'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getAgentslist().size(); i++) {
			
			Agent a = new Agent();
			a.setId(response.getAgentslist().get(i).getId());
			a.setUsername(response.getAgentslist().get(i).getUsername());
			a.setPassword(response.getAgentslist().get(i).getPassword());
			a.setFirstName(response.getAgentslist().get(i).getFirstName());
			a.setLastName(response.getAgentslist().get(i).getLastName());
			a.setAddress(response.getAgentslist().get(i).getAddress());
			a.setMbr(response.getAgentslist().get(i).getMbr());
			
			agentRepository.save(a);
			agents.add(a);
			
		}
		

		return (ArrayList<Agent>) agents;
	}
	
	public Agent loginAgent(AgentDTO agentDTO){
		Agent agent = agentRepository.findOneByUsername(agentDTO.getUsername());
		if (agent == null) {
			throw new IllegalArgumentException("Agent not found!");
		}

		if (agent.getPassword().equals(agentDTO.getPassword())) {
			//OVDE IDE SINHRONIZACIJA
			return agent;
		}else{
			return null;
		}
			
		
	}

}
