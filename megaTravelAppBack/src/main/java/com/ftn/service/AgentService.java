package com.ftn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Agent;
import com.ftn.repository.AgentRepository;

@Service
public class AgentService {
	
	@Autowired
	private AgentRepository agentRepository;
	
	public void saveAgent(Agent agent) {
		agentRepository.save(agent);
	}

}
