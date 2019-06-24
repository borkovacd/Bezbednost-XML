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
public class MessageService 
{
	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private UserRepository userRepository;
	

	
	// vraca sve poslate poruke to User-a
	public List<Message> getAllMessages(Long id)
	{
		return messageRepository.findBySenderId(id);
	}

	
	
}
