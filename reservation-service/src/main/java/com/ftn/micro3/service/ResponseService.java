package com.ftn.micro3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.micro3.model.Response;
import com.ftn.micro3.repository.ResponseRepository;


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
