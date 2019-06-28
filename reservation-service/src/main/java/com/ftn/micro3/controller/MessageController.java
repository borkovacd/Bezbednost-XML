package com.ftn.micro3.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro3.config.TokenUtils;
import com.ftn.micro3.dto.MessageDTO;
import com.ftn.micro3.model.Agent;
import com.ftn.micro3.model.Message;
import com.ftn.micro3.model.Reservation;
import com.ftn.micro3.model.Response;
import com.ftn.micro3.model.User;
import com.ftn.micro3.repository.AgentRepository;
import com.ftn.micro3.repository.ReservationRepository;
import com.ftn.micro3.repository.ResponseRepository;
import com.ftn.micro3.repository.UserRepository;
import com.ftn.micro3.service.MessageService;
import com.ftn.micro3.service.ResponseService;

@RestController
@RequestMapping(value = "/api/message")
public class MessageController 
{
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ResponseService responseService;
	
	@Autowired
	private UserRepository userRepository ;
		
	@Autowired
	private AgentRepository agentRepository ;
	
	@Autowired
	private ReservationRepository reservationRepository ;
	
	@Autowired
	private ResponseRepository responseRepository ;
	
	
	@Autowired
	private TokenUtils tokenUtils;
	
	//ovu metodu izmeni da vraca samo poruke koje nemaju odgovor
	//poruke koje imaju odgovor bice ucitane preko getAllResponsesFromAgent
	@PreAuthorize("hasAuthority('SEND_MESS')")
	@GetMapping("/getAllMessages/{token}")
	public ResponseEntity<List<Message>> getAllMessages(@PathVariable String token) throws Exception
	{	
		token = token.substring(1, token.length()-1);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User u = userRepository.findOneByEmail(email);
		
		List<Message> messages = messageService.getAllMessages(u.getId());
		List<Message> goodMessages = new ArrayList<Message>();
		
		List<Message> unansweredMessages = new ArrayList<Message>();
		boolean unanswered = true;
		
		for(Message message : messages) 
		{
			for(Response responseMessage : responseRepository.findAll()) 
			{
				if(responseMessage.getMessage().getId() == message.getId()) 
				{
					unanswered = false;
				}
			}
			
			
			if(unanswered == true) 
			{
				unansweredMessages.add(message);
			}
			
			else 
			{
				unanswered = true;
			}
		}
			
		if (unansweredMessages != null) 
		{
			return new ResponseEntity<List<Message>>(unansweredMessages, HttpStatus.OK);
		}
		else 
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@PreAuthorize("hasAuthority('SEND_MESS')")
	@GetMapping("/getAllResponsesFromAgent/{token}")
	public ResponseEntity<List<Response>> getAllResponsesFromAgent(@PathVariable String token) throws Exception
	{	
		token = token.substring(1, token.length()-1);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User u = userRepository.findOneByEmail(email);
		
		List<Response> responses = responseService.getAllResponsesFromAgent(u.getId());
		if (responses != null) 
		{
			return new ResponseEntity<>(responses, HttpStatus.OK);
		}
		else 
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	//MessageDTO ima dva polja koja se salju:
	//1. string koji predstavlja username agenta kome se poruka salje
	//2. string koji predstavlja tekst poruke koji se salje
	//preko tokena preuzimas usera koji salje poruku
	//i to je to sto ti tre
	@PreAuthorize("hasAuthority('SEND_MESS')")
	@PostMapping("/createMessage/{token}")
	public void createMessage(@RequestBody MessageDTO messageDTO, @PathVariable String token) throws Exception 
	{
		
		token = token.substring(1, token.length()-1);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User u = userRepository.findOneByEmail(email);

		messageService.createMessage(messageDTO, u);
		

	}
	
	
	@PreAuthorize("hasAuthority('RESERVE')")
	@GetMapping("/checkIfHasReservation/{token}")
	public boolean checkIfHasReservation(@PathVariable String token) throws Exception {
		
		token = token.substring(1, token.length()-1);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User user = userRepository.findOneByEmail(email);
		
		//metoda treba da vraca true kada korisnik ima neku prethodno kreiranu rezervaciju
		boolean hasReservation = messageService.canSendMessage(user.getId());
				
		return hasReservation;
	}

	@PreAuthorize("hasAuthority('SEND_MESS')")
	@GetMapping("/getAppropriateAgents/{token}") 
	public ResponseEntity<List<Agent>> getAppropriateAgents(@PathVariable String token) throws Exception 
	{
		
		token = token.substring(1, token.length()-1);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User user = userRepository.findOneByEmail(email);
		
		//treba da vratis sve agente kojima user moze da prosledi poruke
		ArrayList<Agent> agents = new ArrayList<Agent>();
		
		List<Reservation> reservations = reservationRepository.findByUserId(user.getId()); // vraca sve rezervacije tog usera
		
		if (reservations.size() == 0) // taj korisnik nema rezervacija, pa nema ni agenata
		{
			return new ResponseEntity<List<Agent>>(agents, HttpStatus.OK);
		}
		else
		{
			for (Reservation r: reservations) // prolazi kroz sve rezervacije i iz svake rezervacije pokupi agenta
			{
				if (!agents.contains(r.getAgent())) // ukoliko je taj agent vec dodat u listu (jedan User vise puta rezervisao kod istog agenta)
				{
					agents.add(r.getAgent());
				}
				
			}
			
			return new ResponseEntity<List<Agent>>(agents, HttpStatus.OK);
		}
		
	} 
	
	
	
}
