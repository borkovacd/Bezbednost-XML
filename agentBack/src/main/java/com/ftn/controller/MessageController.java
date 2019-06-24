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

import com.ftn.dto.AnswerDTO;
import com.ftn.model.Message;
import com.ftn.service.MessageService;
import com.ftn.service.ResponseService;




@RestController
@RequestMapping(value = "/api/message")
public class MessageController {
	
	@Autowired
	private MessageService messageService;

	@GetMapping("/getAllMessages/{token}")
	public ResponseEntity<List<Message>> getAllMessages(@PathVariable String token) throws Exception {
		
		List<Message> message = messageService.getAllMessages(token);
		
		return new ResponseEntity<>(message, HttpStatus.OK);

	}
	
	@PostMapping("/createAnswer/{token}")
	public void createAnswer(@RequestBody AnswerDTO answerDTO, @PathVariable String token) throws Exception {
		
		messageService.createAnswer(answerDTO, token);

	}

}
