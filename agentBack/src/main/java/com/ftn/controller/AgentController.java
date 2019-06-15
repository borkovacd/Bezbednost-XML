package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ftn.dto.AgentDTO;
import com.ftn.model.Agent;
import com.ftn.model.City;
import com.ftn.service.AgentService;


@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping(value = "/api/agent")

public class AgentController {
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "/ssl-test", method = RequestMethod.GET)
	public String TestSSl() {

		return "SSL WORKS";
	}
	
	@RequestMapping(value = "/communicate/{message}", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4201")
	public String communicateWithCentral(@PathVariable String message) {

		String uri = "https://localhost:8443/api/admin/communicate";
		
		//message = message.substring(1,message.length()-1).toString();
		//System.out.println(message);

		RestTemplate restTemplate = new RestTemplate();
	//	String result = restTemplate.getForObject(uri, String.class);
		
		System.out.println(uri+"/"+message);

		System.out.println("Usao da posalje poruku centralnom modulu");
		
		 ResponseEntity<String> res = restTemplate.exchange(uri+"/"+message, HttpMethod.GET, null, String.class);
		 
		 System.out.println(res);
		 
		 System.out.println(res.getBody());
    
		return res.getBody();
	
	
	}
	
	@GetMapping("/getAllAgents")
	public ResponseEntity<List<Agent>> getAgents() {
		
		ArrayList<Agent> agents = (ArrayList<Agent>) agentService.getAllAgents();
		
		return new ResponseEntity<List<Agent>>(agents, HttpStatus.OK);
	}
	
	
	@PutMapping("/log-in")
	public ResponseEntity<?> logIn(@RequestBody AgentDTO agentDTO) {
		Long idagent = agentService.loginAgent(agentDTO);
		if(idagent == null){
			return new ResponseEntity<Long>(idagent, HttpStatus.NO_CONTENT);
		}
			return new ResponseEntity<Long>(idagent, HttpStatus.OK);

		
	}
}
