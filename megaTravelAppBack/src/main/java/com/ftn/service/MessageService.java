package com.ftn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Agent;
import com.ftn.model.Message;
import com.ftn.model.Response;
import com.ftn.model.User;
import com.ftn.modelDTO.MessageDTO;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.MessageRepository;
import com.ftn.repository.ReservationRepository;
import com.ftn.repository.ResponseRepository;
import com.ftn.repository.UserRepository;
import com.ftn.security.TokenUtils;

@Service
public class MessageService 
{
	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReservationRepository reservationRepository ;
	
	@Autowired
	private TokenUtils tokenUtils;

	
	// vraca sve poslate poruke to User-a
	public List<Message> getAllMessages(Long id)
	{
		return messageRepository.findBySenderId(id);
	}
	
	// Korisnik moze da salje poruku agentu jedino ukoliko vec ima kreiranu rezervaciju
	public boolean canSendMessage(Long idUser)
	{
		if (reservationRepository.findByUserId(idUser) != null) // u tabeli rezervacija je pronasao User-a sa tim Id-jem
		{
			return true ;
		}	
		else
		{
			return false ;
		}
	}
	
	public void createMessage(MessageDTO messageDTO, String token) throws Exception
	{
		String username = tokenUtils.getUserSecurity(token).getUsername();
		
		User user = userRepository.findOneByUsername(username);
		Agent agent = agentRepository.findOneById(messageDTO.getIdRecipient());
		
		Message message = new Message();
		
		message.setId(messageDTO.getIdMessage());
		message.setSender(user);
		message.setRecipient(agent);
		message.setText(messageDTO.getText());
		
		messageRepository.save(message);
		
	}

	
	
}
