package com.ftn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Agent;
import com.ftn.model.Message;
import com.ftn.model.Reservation;
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
		List<Reservation> reservations = reservationRepository.findByUserId(idUser);
		
		if (reservations.size() == 0) // ukoliko nije pronasao nijednu rezervaciju za prosledjen idUser-a
		{
			return false ;
		}
		else // postoji makar jedna rezervacija u listi rezervacija koju je taj User napravio
		{
			return true ;
		}
	}
	
	public void createMessage(MessageDTO messageDTO, String token) throws Exception
	{
		String username = tokenUtils.getUserSecurity(token).getUsername();
		
		User user = userRepository.findOneByUsername(username);
		Agent agent = agentRepository.findOneByUsername(messageDTO.getIdRecipient());
		
		Message message = new Message();

		message.setSender(user);
		message.setRecipient(agent);
		message.setText(messageDTO.getText());
		
		messageRepository.save(message);
		
	}

	
	
}
