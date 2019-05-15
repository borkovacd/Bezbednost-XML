package com.ftn.controller;

import java.util.Arrays;

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

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.ftn.model.User;
import com.ftn.model.UserToken;
import com.ftn.modelDTO.UserDTO;
import com.ftn.repository.UserRepository;
import com.ftn.security.LoggerUtils;
import com.ftn.security.TokenUtils;
import com.ftn.service.RoleService;

import com.ftn.service.UserServiceImpl;

@RestController
@RequestMapping(value = "/api/user")
public class UserControler {

	private static final Logger log = LoggerFactory.getLogger(UserControler.class);
	
	@Autowired
	private UserServiceImpl userService;

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
			log.error("REG_ERR");

			return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

		} else {
			if(userService.checkMail(userDto.getEmail() )== false){
				log.error("REG_ERR_EMAIL");

				return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

			}
			if(userService.checkCharacters(userDto.getFirstName())==false){
				log.error("REG_ERR_FN");

				return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

			}
			if(userService.checkCharacters(userDto.getLastName())==false){
				log.error("REG_ERR_LN");

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

				u.setRoles(Arrays.asList(roleService.findByName("ROLE_USER")));

				userRep.save(u);
				System.out.println("upisao korisnika sa mejlom: "+u.getEmail());
				log.info(LoggerUtils.getSMarker(), "SECURITY_EVENT user id:{} REG_SUC,ip: {}", u.getId(), request.getRemoteAddr());
				log.debug("REG_SUC");
				return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
			}
		}
		return new ResponseEntity<UserDTO>( HttpStatus.NO_CONTENT);

	}

	
	
	
	

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> logIn(@RequestBody User user, HttpServletRequest req) {
		log.debug("LOG");

		boolean response = userService.logIn(user);


		if (response == true) {
			// ako korisnik postoji u bazi

			User userNew = userService.findByEmail(user.getEmail());

			// proveravam da li se password podudara sa onim u bazi
			if (BCrypt.checkpw(user.getPassword(), userNew.getPassword())) {

				try{
				UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getEmail(),
						user.getPassword());
				Authentication auth = authManager.authenticate(authReq);

				SecurityContext sc = SecurityContextHolder.getContext();
				sc.setAuthentication(auth);
				User user1 = (User) auth.getPrincipal();
				System.out.println(user1.getEmail());
				String token = tokenUtils.generateToken(user1.getEmail());
				long expiresIn = tokenUtils.getExpiredIn();
				log.info(LoggerUtils.getSMarker(), "SECURITY_EVENT user id:{} LOG_SUC ,ip {}", userNew.getId(),req.getRemoteAddr());
				log.info(LoggerUtils.getNMarker(), "NEPOR_EVENT user id:{} LOG_SUC, ip {}", userNew.getId(),req.getRemoteAddr());

				return new ResponseEntity<>(new UserToken(token,expiresIn), HttpStatus.OK);
				}catch (Exception e) {
					log.warn(LoggerUtils.getSMarker(), "SECURITY_EVENT user id:{} LOG_FAIL ", userNew.getId());

					return new ResponseEntity<>(new UserToken(), HttpStatus.NOT_FOUND);
				}
				
			} else {
				log.error("LOG_ERR");
				log.warn(LoggerUtils.getSMarker(), "SECURITY_EVENT user id:{} LOG_FAIL ", userNew.getId());

				

				return new ResponseEntity<>(new UserToken(), HttpStatus.NOT_FOUND);
			}
		} else {
			log.error("LOG_ERR");
			log.warn(LoggerUtils.getSMarker(), "SECURITY_EVENT user id:{} LOG_FAIL ", user.getId());


			return new ResponseEntity<>(new UserToken(), HttpStatus.NOT_FOUND);
		}
	}


	@RequestMapping(value = "/checkIfMailExists/{email}", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public boolean checkCommunication(@PathVariable String email) {

		boolean response = userService.checkMailExistence(email);
		return response;
	}
	
	//@PreAuthorize("hasRole('USER')") 
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
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

		System.out.println(message);
		return "Central module responded! Got message: " + message;
	}
	
	@RequestMapping(value = "/loggedUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getLoggedUserEmail(@RequestBody String token) {

		String email = tokenUtils.getUsernameFromToken(token);
		User u = userService.findByEmail(email);
		
		System.out.println(email);
		log.info("user id:{} LOGEDUSER",u.getId());
		
		
	}

}
