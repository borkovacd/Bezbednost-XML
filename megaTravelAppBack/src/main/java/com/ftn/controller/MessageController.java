package com.ftn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.service.MessageService;
import com.ftn.service.ResponseService;
import com.ftn.service.UserService;
import com.ftn.model.Message;
import com.ftn.model.Response;
import com.ftn.model.User;
import com.ftn.modelDTO.MessageDTO;
import com.ftn.repository.UserRepository;
import com.ftn.security.TokenUtils; 

@RestController
@RequestMapping(value = "/api/message")
public class MessageController 
{
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ResponseService responseService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository ;
	
	@Autowired
	TokenUtils tokenUtils;
	

	@GetMapping("/getAllMessages/{token}")
	public ResponseEntity<List<Message>> getAllMessages(@PathVariable String token) throws Exception
	{	
		token = token.substring(1, token.length()-1);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User u = userService.findByEmail(email);
		
		List<Message> messages = messageService.getAllMessages(u.getId());
		if (messages != null) 
		{
			return new ResponseEntity<>(messages, HttpStatus.OK);
		}
		else 
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/getAllResponsesFromAgent/{token}")
	public ResponseEntity<List<Response>> getAllResponsesFromAgent(@PathVariable String token) throws Exception
	{	
		token = token.substring(1, token.length()-1);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User u = userService.findByEmail(email);
		
		List<Response> responses = responseService.getAllResponsesFromAgent(u.getId());
		if (responses != null) 
		{
			return new ResponseEntity<>(responses, HttpStatus.OK);
		}
		else 
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@PostMapping("/createMessage/{token}")
	public void createAnswer(@RequestBody MessageDTO messageDTO, @PathVariable String token) throws Exception 
	{
		String username = tokenUtils.getUserSecurity(token).getUsername();
		
		User user = userRepository.findOneByUsername(username);
		if ((messageService.canSendMessage(user.getId())) == true) // trenutni korisnik se nalazi u tabeli rezervacija
		{
			messageService.createMessage(messageDTO, token);
		}

	}
	
	
	
}
