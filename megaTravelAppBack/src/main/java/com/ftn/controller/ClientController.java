package com.ftn.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.Client;
import com.ftn.modelDTO.ClientDTO;
import com.ftn.security.LoggerUtils;
import com.ftn.service.ClientService;

@RestController
@RequestMapping(value="/api/client")
public class ClientController {
	
	private static final Logger log = LoggerFactory.getLogger(ClientController.class);
	
	
	@Autowired
	private ClientService clientService;
	
	
	@RequestMapping(value = "/reg", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientDTO> registrate(@RequestBody ClientDTO clientDto, HttpServletRequest request) {
		log.debug("REG");
		boolean response = clientService.exists(clientDto.getEmail());
		if (response == true) {
			System.out.println("vec postoji dati mejl");
			log.error("REG_ERR");

			return new ResponseEntity<ClientDTO>(HttpStatus.NO_CONTENT);

		} else {
			if(clientService.exists(clientDto.getEmail())== true){
				log.error("REG_ERR_EMAIL");

				return new ResponseEntity<ClientDTO>(HttpStatus.NO_CONTENT);

			}
			if(clientService.checkCharacters(clientDto.getFirstName())==false){
				log.error("REG_ERR_FN");

				return new ResponseEntity<ClientDTO>(HttpStatus.NO_CONTENT);

			}
			if(clientService.checkCharacters(clientDto.getLastName())==false){
				log.error("REG_ERR_LN");

				return new ResponseEntity<ClientDTO>(HttpStatus.NO_CONTENT);

			}

			if (clientDto.getPassword().equals(clientDto.getRePassword())) {
				Client u = new Client();

				u.setCity(clientDto.getCity());
				u.setEmail(clientDto.getEmail());
				u.setFirstName(clientDto.getFirstName());
				u.setLastName(clientDto.getLastName());
				u.setUsername(clientDto.getUsername());
				u.setPassword(clientDto.getPassword());

				String pass = u.getPassword();

				String salt = BCrypt.gensalt();
				String passwordHashed = BCrypt.hashpw(pass, salt);

				u.setPassword(passwordHashed);

				clientService.saveClient(u);
				System.out.println("upisao klijenta sa mejlom: "+u.getEmail());
				log.info(LoggerUtils.getSMarker(), "SECURITY_EVENT user id:{} REG_SUC,ip: {}", u.getId(), request.getRemoteAddr());
				log.debug("REG_SUC");
				return new ResponseEntity<ClientDTO>(clientDto, HttpStatus.OK);
			}
		}
		return new ResponseEntity<ClientDTO>( HttpStatus.NO_CONTENT);

	}

}
