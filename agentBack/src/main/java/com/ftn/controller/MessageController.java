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
	
	@PostMapping("/createAnswer")
	public void createAnswer(@RequestBody AnswerDTO answerDTO) {
		//ovde ti u answerDTO stize token to ti je agent koga tako dobijes
		//trbea da vratis odgovor u klasi RESPONSE
		//token tj agent je sender
		//recipient = idUser znaci onaj koji prima poruku
		//text ti je text tj odgovor koji agent salje klijentu
		
		
		Long idMessage = Long.parseLong(answerDTO.getIdMessage());
		//ovaj idMessage ti treba da pronadjes tu poruku u bazi i setujes njen response
		//znaci prvo pravis objekat kalse Response to vracas korisniku 
		//a u modelu Message imas objekat response koji setujes 
		
		Long idUser = Long.parseLong(answerDTO.getReceipient());


	}

}
