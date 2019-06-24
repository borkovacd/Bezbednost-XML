package com.ftn.micro1.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.micro1.model.Agent;
import com.ftn.micro1.model.User;
import com.ftn.micro1.repository.AgentRepository;

@Service
public class AgentService {
	
	@Autowired
	private AgentRepository agentRepository;
	
	public void saveAgent(Agent agent) {
		agentRepository.save(agent);
	}

	public ArrayList<Agent> getAgents() {


			return (ArrayList<Agent>) agentRepository.findAll();
	}

	

}
