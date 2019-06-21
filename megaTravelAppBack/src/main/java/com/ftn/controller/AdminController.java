package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.enums.ClientStatus;
import com.ftn.model.AdditionalServices;
import com.ftn.model.Agent;
import com.ftn.model.Category;
import com.ftn.model.Client;
import com.ftn.model.TypeAccomodation;
import com.ftn.modelDTO.AdditionalServiceDTO;
import com.ftn.modelDTO.AgentDTO;
import com.ftn.modelDTO.CategoryDTO;
import com.ftn.modelDTO.TypeAccomodationDTO;
import com.ftn.service.AdditionalServicesService;
import com.ftn.service.AgentService;
import com.ftn.service.CategoryService;
import com.ftn.service.ClientService;
import com.ftn.service.TypeAccomodationService;

@RestController
@RequestMapping(value="/api/admin")
public class AdminController {
	
	@Autowired
	private AgentService agentService; 
	
	@Autowired
	private AdditionalServicesService additionalServicesService ;
	
	@Autowired
	private CategoryService categoryService ;
	
	@Autowired
	private TypeAccomodationService typeAccomodationService ;
	
	@Autowired
	private ClientService clientService;
	
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
		
		System.out.println(passwordHashed);
		
		agent.setPassword(passwordHashed);
		
		agentService.saveAgent(agent);
		
	}
	
	
	/*
	@RequestMapping(value="/addAdditionalService",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AdditionalServices addAdditionalService(@RequestBody AdditionalServiceDTO additionalServiceDto) 
	{
		AdditionalServices addServ = new AdditionalServices();
		addServ.setName(additionalServiceDto.getName());
	//	addServ.setPrice(additionalServiceDto.getPrice());
		
		return additionalServicesService.saveAdditionalService(addServ);
	}
	
	*/
	
	
	@RequestMapping(value="/addCategory",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addCategory(@RequestBody CategoryDTO categoryDto) 
	{
		Category category = new Category();
		category.setName(categoryDto.getName());
	
		categoryService.saveCategory(category);
	}
	
	/*
	@RequestMapping(value="/addTypeAccomodation",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTypeAccomodation(@RequestBody TypeAccomodationDTO typeAccomodationDto) 
	{
		TypeAccomodation typeAccomodation = new TypeAccomodation();
		typeAccomodation.setName(typeAccomodationDto.getName());
	
		typeAccomodationService.saveTypeAccomodation(typeAccomodation);
	}
	*/
	
	@RequestMapping(value="/activateUser/{username}", method = RequestMethod.GET)
	public Client activateClient(@PathVariable String username) {
	
		Client client = clientService.findByUsername(username);
		
		client.setStatus(ClientStatus.AKTIVAN);
		
		clientService.saveClient(client);
		
		return client;

	}
	
	@RequestMapping(value="/blockUser/{username}", method = RequestMethod.GET)
	public Client blockClient(@PathVariable String username) {
	
		Client client = clientService.findByUsername(username);
		
		client.setStatus(ClientStatus.BLOKIRAN);
		
		clientService.saveClient(client);
		
		return client;

	}
	
	@RequestMapping(value="/removeUser/{username}", method = RequestMethod.GET)
	public Client removeClient(@PathVariable String username) {
	
		Client client = clientService.findByUsername(username);
		
		client.setStatus(ClientStatus.UKLONJEN);
		
		clientService.saveClient(client);
		
		return client;

	}
	

	@RequestMapping(value = "/communicate/{message}", method = RequestMethod.GET)
	public String communicateMethod(@PathVariable String message) {

		System.out.println(message);
		return "Central module responded! Got message: " + message;
	}
	
	
	
	

}
