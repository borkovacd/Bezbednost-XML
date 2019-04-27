package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.service.AgentService;

@RestController
@RequestMapping(value = "/api/agent")
public class AgentController {
	@Autowired
	private AgentService agentService;

}
