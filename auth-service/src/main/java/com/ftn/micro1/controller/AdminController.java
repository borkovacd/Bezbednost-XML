package com.ftn.micro1.controller;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro1.model.Agent;
import com.ftn.micro1.model.User;
import com.ftn.micro1.repository.RoleRepository;
import com.ftn.micro1.repository.UserRepository;
import com.ftn.micro1.security.TokenUtils;
import com.ftn.micro1.service.AgentService;
import com.ftn.micro1.service.UserService;
import com.ftn.micro1.enums.ClientStatus;
import com.ftn.micro1.dto.AgentDTO;
import com.ftn.micro1.enums.NameRole;

@RestController
@RequestMapping(value="/admin")
public class AdminController {
	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AgentService agentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@PreAuthorize("hasAuthority('ADD_AGENT')")
	@RequestMapping(value="/addAgent",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addAgent(ServletRequest request, @RequestBody AgentDTO agentDto) throws Exception {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		log.info("User id: {} ADDAG",u.getId());

		
		
		
		Agent agent = new Agent();
		
		System.out.println("dodaje agenta");
		
		agent.setAddress(agentDto.getAddress());
		agent.setFirstName(agentDto.getFirstName());
		agent.setLastName(agentDto.getLastName());
		agent.setMbr(agentDto.getMbr());
		agent.setUsername(agentDto.getUsername());
		
		
		String salt = BCrypt.gensalt();
		String passwordHashed = BCrypt.hashpw(agentDto.getPassword(), salt);
		
		agent.setPassword(passwordHashed);
		
		System.out.println("sifra je" + passwordHashed);
		
		agent.setRoles(Collections.singleton(roleRepository.findByName(NameRole.ROLE_AGENT)));

		agentService.saveAgent(agent);
		log.info("User id: {} ADDAGSUCCESS",u.getId());

		
	}
	
	@PreAuthorize("hasAuthority('ADD_AGENT')")
	@RequestMapping(value="/agents",method = RequestMethod.GET)
	public ArrayList<Agent> getAgents(ServletRequest request) throws Exception {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		log.info("User id: {} GETAG",u.getId());

		

			return agentService.getAgents();
		
	}
	
	@PreAuthorize("hasAuthority('SEE_USERS')")
	@RequestMapping(value="/users",method = RequestMethod.GET)
	public ArrayList<User> getUsers(ServletRequest request) throws Exception {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		log.info("User id: {} GETUS",u.getId());

		

			return userService.getUsers();
		
	}
	

	@PreAuthorize("hasAuthority('ACT_USER')")
	@RequestMapping(value="/activateUser/{email}", method = RequestMethod.GET)
	public User activateClient(@PathVariable String email) {
	
		User user = userService.findByEmail(email);
		log.info("User id: {} ACTIUS",user.getId());


		user.setStatus(ClientStatus.AKTIVAN);
		
		userService.saveUser(user);
		
		return user;

	}
	

	@PreAuthorize("hasAuthority('BLOCK_USER')")
	@RequestMapping(value="/blockUser/{email}", method = RequestMethod.GET)
	public User blockClient(@PathVariable String email) {
	
		User user = userService.findByEmail(email);
		
		user.setStatus(ClientStatus.BLOKIRAN);
		log.info("User id: {} BLOUS",user.getId());

		userService.saveUser(user);
		
		return user;

	}
	

	@PreAuthorize("hasAuthority('DEL_USER')")
	@RequestMapping(value="/removeUser/{email}", method = RequestMethod.GET)
	public User removeUser(@PathVariable String email) {
	
		User user = userService.findByEmail(email);
		log.info("User id: {} DELUS",user.getId());

		userService.remove(user);
		
		return user;

	}
	
	

}
