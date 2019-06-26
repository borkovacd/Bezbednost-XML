package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ftn.dto.AgentDTO;
import com.ftn.model.Agent;
import com.ftn.model.UserToken;
import com.ftn.repository.AgentRepository;
import com.ftn.security.TokenUtils;
import com.ftn.service.AgentService;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping(value = "/api/agent")
public class AgentController {
	private static final Logger log = LoggerFactory.getLogger(AgentController.class);
	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private AgentService agentService;

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AgentRepository agentRepository;

	@RequestMapping(value = "/ssl-test", method = RequestMethod.GET)
	public String TestSSl() {

		return "SSL WORKS";
	}

	@RequestMapping(value = "/communicate/{message}", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4201")
	public String communicateWithCentral(@PathVariable String message) {
		log.info("COMMUN");
		String uri = "https://localhost:8443/api/admin/communicate";
		log.debug("COMMUNA");
		// message = message.substring(1,message.length()-1).toString();
		// System.out.println(message);

		RestTemplate restTemplate = new RestTemplate();
		// String result = restTemplate.getForObject(uri, String.class);

		System.out.println(uri + "/" + message);

		System.out.println("Usao da posalje poruku centralnom modulu");

		ResponseEntity<String> res = restTemplate.exchange(uri + "/" + message, HttpMethod.GET, null, String.class);

		System.out.println(res);

		System.out.println(res.getBody());

		return res.getBody();

	}

	@GetMapping("/getAllAgents")
	public ResponseEntity<List<Agent>> getAgents() {

		ArrayList<Agent> agents = (ArrayList<Agent>) agentService.getAllAgents();

		return new ResponseEntity<List<Agent>>(agents, HttpStatus.OK);
	}

	@RequestMapping(value = "/log-in", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> logIn(@RequestBody AgentDTO agentDTO) throws Exception {
		Agent agent = agentRepository.findOneByUsername(agentDTO.getUsername());

		UserToken ut = agentService.loginAgent(agentDTO);
		if (ut == null) {
			log.warn("User id: " + agent.getId() + " LOGNOCONTENT");

			return new ResponseEntity<UserToken>(ut, HttpStatus.NO_CONTENT);
		}
		log.warn("User id: " + agent.getId() + " LOGSUCCESS");

		return new ResponseEntity<UserToken>(ut, HttpStatus.OK);

	}

	@PreAuthorize("hasAuthority('ADD_ACC')")
	@RequestMapping(value = "/log-out/{token}", method = RequestMethod.GET)
	public void logOutUser(@PathVariable String token) throws Exception {
		
        String usname = tokenUtils.getUserSecurity(token).getUsername();
		
		Agent ag = agentRepository.findOneByUsername(usname);
		log.info("User id: "+ag.getId()+"  LOGOUT");

		System.out.println("usao da birse");
		SecurityContextHolder.clearContext();

	}

	/*
	 * @RequestMapping("/log-in") public ResponseEntity<?> logIn(@RequestBody
	 * AgentDTO agentDTO) { UserToken ut = agentService.loginAgent(agentDTO);
	 * if(ut == null){ return new ResponseEntity<UserToken>(ut,
	 * HttpStatus.NO_CONTENT); } return new ResponseEntity<UserToken>(ut,
	 * HttpStatus.OK);
	 * 
	 * 
	 * }
	 */
}
