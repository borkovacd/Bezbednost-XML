package com.ftn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	private static final Logger log = LoggerFactory.getLogger(ResponseController.class);

	@Autowired
	private ResponseService responseService;
	

	@PreAuthorize("hasAuthority('SEND_MESS')")
	@GetMapping("/getAllResponses/{token}")
	public ResponseEntity<List<Response>> getAllResponse(@PathVariable String token) throws Exception {
		
		List<Response> responses = responseService.getAllResponses(token);
		
		return new ResponseEntity<>(responses, HttpStatus.OK);

	}

}
