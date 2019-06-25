package com.ftn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.Message;
import com.ftn.model.Response;
import com.ftn.service.MessageService;
import com.ftn.service.ResponseService;

@RestController
@RequestMapping(value = "/api/response")
public class ResponseController {
	@Autowired
	private ResponseService responseService;
	
	@GetMapping("/getAllResponses/{token}")
	public ResponseEntity<List<Response>> getAllResponse(@PathVariable String token) throws Exception {
		
		List<Response> responses = responseService.getAllResponses(token);
		
		return new ResponseEntity<>(responses, HttpStatus.OK);

	}

}
