package com.ftn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Message;
import com.ftn.model.Response;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.MessageRepository;
import com.ftn.repository.ResponseRepository;
import com.ftn.repository.UserRepository;

@Service
public class ResponseService 
{
	@Autowired
	private ResponseRepository responseRepository ;
	
	// vraca sve primljene poruke tog User-a
	public List<Response> getAllResponsesFromAgent(Long id)
	{
		return responseRepository.findByRecipientId(id);
	}
}
