package com.ftn.micro3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.micro3.dto.MessageDTO;
import com.ftn.micro3.model.Agent;
import com.ftn.micro3.model.Message;
import com.ftn.micro3.model.Reservation;
import com.ftn.micro3.model.User;
import com.ftn.micro3.repository.AgentRepository;
import com.ftn.micro3.repository.MessageRepository;
import com.ftn.micro3.repository.ReservationRepository;

@Service
public class MessageService 
{
	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private ReservationRepository reservationRepository ;
	
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
	
	public void createMessage(MessageDTO messageDTO, User user) throws Exception
	{
		
		System.out.println("*********************************");
		System.out.println(user.getEmail());
		System.out.println(messageDTO.getAgent());
		System.out.println("*********************************");
		Agent agent = agentRepository.findOneByUsername(messageDTO.getAgent());
		System.out.println("Agent: " + agent.getUsername());
		
		Message message = new Message();

		message.setSender(user);
		message.setRecipient(agent);
		message.setText(messageDTO.getText());
		
		messageRepository.save(message);
		
	}

	
	
}
