package com.ftn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.service.MessageService;
import com.ftn.service.ResponseService;
import com.ftn.service.UserService;
import com.ftn.model.Message;
import com.ftn.model.Response;
import com.ftn.model.User;
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
	TokenUtils tokenUtils;
	

	@GetMapping("/getAllMessages/{token}")
	public ResponseEntity<List<Message>> getAllMessages(@PathVariable String token) throws Exception
	{	
		
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
	
	@GetMapping("/getAllResponsesFromAgent/{id}")
	public ResponseEntity<List<Response>> getAllResponsesFromAgent(@PathVariable Long id)
	{	
		List<Response> responses = responseService.getAllResponsesFromAgent(id);
		if (responses != null) 
		{
			return new ResponseEntity<>(responses, HttpStatus.OK);
		}
		else 
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	
	
}
