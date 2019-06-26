package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.service.MessageService;
import com.ftn.service.ResponseService;
import com.ftn.service.UserService;
import com.ftn.model.Agent;
import com.ftn.model.City;
import com.ftn.model.Message;
import com.ftn.model.Reservation;
import com.ftn.model.Response;
import com.ftn.model.User;
import com.ftn.modelDTO.MessageDTO;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.ReservationRepository;
import com.ftn.repository.ResponseRepository;
import com.ftn.repository.UserRepository;
import com.ftn.security.TokenUtils; 

@RestController
@RequestMapping(value = "/api/message")
public class MessageController 
{
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ResponseService responseService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository ;
		
	@Autowired
	private AgentRepository agentRepository ;
	
	@Autowired
	private ReservationRepository reservationRepository ;
	
	@Autowired
	private ResponseRepository responseRepository ;
	
	
	@Autowired
	TokenUtils tokenUtils;
	
	//ovu metodu izmeni da vraca samo poruke koje nemaju odgovor
	//poruke koje imaju odgovor bice ucitane preko getAllResponsesFromAgent
	@GetMapping("/getAllMessages/{token}")
	public ResponseEntity<List<Message>> getAllMessages(@PathVariable String token) throws Exception
	{	
		token = token.substring(1, token.length()-1);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User u = userService.findByEmail(email);
		
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
	
	@GetMapping("/getAllResponsesFromAgent/{token}")
	public ResponseEntity<List<Response>> getAllResponsesFromAgent(@PathVariable String token) throws Exception
	{	
		token = token.substring(1, token.length()-1);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User u = userService.findByEmail(email);
		
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
	@PostMapping("/createMessage/{token}")
	public void createMessage(@RequestBody MessageDTO messageDTO, @PathVariable String token) throws Exception 
	{
		
		token = token.substring(1, token.length()-1);
		
		String username = tokenUtils.getUserSecurity(token).getUsername();
		
		User user = userRepository.findOneByUsername(username);
		
		Agent agent = agentRepository.findOneByUsername(messageDTO.getIdRecipient());
			
		if ((messageService.canSendMessage(user.getId())) == true) // trenutni korisnik se nalazi u tabeli rezervacija
		{
			messageService.createMessage(messageDTO, token);
		}

	}
	
	
	
	@GetMapping("/checkIfHasReservation/{token}")
	public boolean checkIfHasReservation(@PathVariable String token) throws Exception {
		
		token = token.substring(1, token.length()-1);
		
		String username = tokenUtils.getUserSecurity(token).getUsername();
		User user = userRepository.findOneByUsername(username);
		
		//metoda treba da vraca true kada korisnik ima neku prethodno kreiranu rezervaciju
		boolean hasReservation = messageService.canSendMessage(user.getId());
				
		return hasReservation;
	}
	
	@GetMapping("/getAppropriateAgents/{token}") 
	public ResponseEntity<List<Agent>> getAppropriateAgents(@PathVariable String token) throws Exception 
	{
		
		token = token.substring(1, token.length()-1);
		
		String username = tokenUtils.getUserSecurity(token).getUsername();
		User user = userRepository.findOneByUsername(username);
		
		//treba da vratis sve agente kojima user moze da prosledi poruke
		ArrayList<Agent> agents = new ArrayList<Agent>();
		
		List<Reservation> reservations = reservationRepository.findByUserId(user.getId()); // vraca sve rezervacije tog usera
		
		if (reservations.size() == 0) // taj korisnik nema rezervacija, pa nema ni agenata
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
