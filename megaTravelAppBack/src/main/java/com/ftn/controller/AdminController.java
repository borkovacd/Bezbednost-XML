package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.Agent;
import com.ftn.modelDTO.AgentDTO;
import com.ftn.service.AgentService;

@RestController
@RequestMapping(value="/api/admin")
public class AdminController {
	
	@Autowired
	private AgentService agentService; 
	
	
	@RequestMapping(value="/addAgent",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addAgent(@RequestBody AgentDTO agentDto) {
		
		Agent agent = new Agent();
		
		agent.setAddress(agentDto.getAddress());
		agent.setFirstName(agentDto.getFirstName());
		agent.setLastName(agentDto.getLastName());
		agent.setMbr(agentDto.getMbr());
		agent.setUsername(agentDto.getUsername());
		
		String salt = BCrypt.gensalt();
		String passwordHashed = BCrypt.hashpw(agentDto.getPassword(), salt);
		
		agent.setPassword(passwordHashed);
		
		agentService.saveAgent(agent);
		
	}
	
	@RequestMapping(value = "/communicate/{message}", method = RequestMethod.GET)
	public String communicateMethod(@PathVariable String message) {

		System.out.println(message);
		return "Central module responded! Got message: " + message;
	}
	
	

}
