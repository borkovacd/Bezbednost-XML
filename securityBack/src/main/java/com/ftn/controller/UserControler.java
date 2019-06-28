package com.ftn.controller;

import java.util.Collections;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.enums.NameRole;
import com.ftn.model.User;
import com.ftn.model.UserToken;
import com.ftn.modelDTO.UserDTO;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.UserRepository;
import com.ftn.security.LoggerUtils;
import com.ftn.security.TokenUtils;
import com.ftn.service.RoleService;
import com.ftn.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserControler {

	private static final Logger log = LoggerFactory.getLogger(UserControler.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRep;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	TokenUtils tokenUtils;
	
	private boolean field = false;

	
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<UserDTO> registrate(@RequestBody UserDTO userDto, HttpServletRequest request) {
		log.debug("REG");
		boolean response = userService.exists(userDto.getEmail());
		if (response == true) {
			System.out.println("vec postoji dati mejl");
			log.error("REGFAIL");

			return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

		} else {
			if(userService.checkMail(userDto.getEmail() )== false){
				log.error("REGFAILEMAIL");

				return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

			}
			if(userService.checkCharacters(userDto.getFirstName())==false){
				log.error("REGFAILFN");

				return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

			}
			if(userService.checkCharacters(userDto.getLastName())==false){
				log.error("REGFAILLN");

				return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

			}

			if (userDto.getPassword().equals(userDto.getRePassword())) {
				
				User u = new User();

				u.setCity(userDto.getCity());
				u.setEmail(userDto.getEmail());
				u.setFirstName(userDto.getFirstName());
				u.setLastName(userDto.getLastName());
				u.setUsername(userDto.getUsername());
				u.setPassword(userDto.getPassword());

				String pass = u.getPassword();

				String salt = BCrypt.gensalt();
				String passwordHashed = BCrypt.hashpw(pass, salt);

				u.setPassword(passwordHashed);

				u.setRoles(Collections.singleton(roleRepository.findByName(NameRole.ROLE_USER)));

				userRep.save(u);
				System.out.println("upisao korisnika sa mejlom: "+u.getEmail());
				log.info(LoggerUtils.getSMarker(), "SECURITY_EVENT REGSUCCESS");
				log.debug("REGSUCCESS");
				return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
			}
		}
		return new ResponseEntity<UserDTO>( HttpStatus.NO_CONTENT);

	}

	
	
	
	

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> logIn(@RequestBody UserDTO userDTO, HttpServletRequest req) {
		log.debug("LOGING");

		boolean response = userService.logIn(userDTO);


		if (response == true) {
			// ako korisnik postoji u bazi

			User userNew = userService.findByEmail(userDTO.getEmail());

			// proveravam da li se password podudara sa onim u bazi
			if (BCrypt.checkpw(userDTO.getPassword(), userNew.getPassword())) {

				try{
				UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userDTO.getEmail(),
						userDTO.getPassword());
				
				Authentication auth = authManager.authenticate(authReq);
				
				String email = authReq.getName();
				
				System.out.println("mejl je sledeci" + email);
			
				//SecurityContext sc = SecurityContextHolder.getContext();
				//sc.setAuthentication(auth);
				
				String token = tokenUtils.generateToken(auth);
				
				System.out.println("Token koji sam napravio je " + token);
				
				User us = userService.findByEmail(email);
				
				//User user1 = (User) auth.getPrincipal();
			//	System.out.println(user1.getEmail());
				
				long expiresIn = tokenUtils.getExpiredIn();
				log.info(LoggerUtils.getSMarker(), "SECURITY_EVENT User id:{} LOGSUCCESS ", us.getId());
				log.info(LoggerUtils.getNMarker(), "NEPOR_EVENT User id:{} LOGSUCCESS", us.getId());

				return new ResponseEntity<>(new UserToken(token,expiresIn), HttpStatus.OK);
				}catch (Exception e) {
					log.warn(LoggerUtils.getSMarker(), "SECURITY_EVENT User id:{} LOGFAIL ", userNew.getId());
					e.printStackTrace();
					return new ResponseEntity<>(new UserToken(), HttpStatus.NOT_FOUND);
				}
				
			} else {
				log.error("LOGFAIL");
				log.warn(LoggerUtils.getSMarker(), "SECURITY_EVENT  LOGFAIL ");

				

				return new ResponseEntity<>(new UserToken(), HttpStatus.NOT_FOUND);
			}
		} else {
			log.error("LOGFAIL");
			log.warn(LoggerUtils.getSMarker(), "SECURITY_EVENT  LOGFAIL ");


			return new ResponseEntity<>(new UserToken(), HttpStatus.NOT_FOUND);
		}
	}


	@RequestMapping(value = "/checkIfMailExists/{email}", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public boolean checkCommunication(@PathVariable String email) {

		boolean response = userService.checkMailExistence(email);
		return response;
	}
	
	
	//@PreAuthorize("hasAuthority('CREATE_CERT')")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public void logOutUser() {
		log.debug("LOGOUT");

		System.out.println("usao ovde");
		SecurityContextHolder.clearContext();
		
	}
	
	@RequestMapping(value = "/ssl-test", method = RequestMethod.GET)
	public String TestSSl() {

		return "SSL WORKS";
	}
	
	@RequestMapping(value = "/communicate/{message}", method = RequestMethod.GET)
	public String communicateMethod(@PathVariable String message) {
		log.info("COMMUN");
		System.out.println(message);
		return "Central module responded! Got message: " + message;
	}
	
	@RequestMapping(value = "/loggedUser/{token}",  method = RequestMethod.GET)
	public boolean getLoggedUserEmail(@PathVariable String token) throws Exception {

		
		System.out.println("Token kad ga dobijem: " + token);
		
		token = token.substring(1, token.length()-1);
		
		System.out.println("Token nakon seckanja: " + token);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User u = userService.findByEmail(email);
		
		System.out.println("A imejl je: " + email);
		log.info("User id:{} LOGEDUSER",u.getId());
		
		if(email.equals("MTRoot@gmail.com")) {
			return true;
		} else {
			return false;
		}
		
	}

}
