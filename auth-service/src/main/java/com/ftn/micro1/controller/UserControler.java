package com.ftn.micro1.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro1.dto.UserDTO;
import com.ftn.micro1.dto.UserLogin;
import com.ftn.micro1.enums.ClientStatus;
import com.ftn.micro1.enums.NameRole;
import com.ftn.micro1.model.User;
import com.ftn.micro1.model.UserToken;
import com.ftn.micro1.repository.PermissionRepository;
import com.ftn.micro1.repository.RoleRepository;
import com.ftn.micro1.repository.UserRepository;
import com.ftn.micro1.security.LoggerUtils;
import com.ftn.micro1.security.TokenUtils;
import com.ftn.micro1.service.UserService;


@RestController
@RequestMapping(value = "/user")
public class UserControler {

	private static final Logger log = LoggerFactory.getLogger(UserControler.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRep;
	
	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	TokenUtils tokenUtils;
	
	private boolean field = false;
	
	
	@RequestMapping(value = "/reg", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> registrate(@RequestBody UserDTO clientDto, HttpServletRequest request) {
		
			System.out.println("ime je" +clientDto.getFirstName());
			
			boolean response = userService.exists(clientDto.getEmail());
			if (response == true) {
				System.out.println("Vec postoji dati mejl");
				log.error("REGFAIL");

				return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

			} else {
				if(userService.checkMail(clientDto.getEmail() )== false){
					log.error("REGFAILEMAIL");

					return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

				}
				if(userService.checkCharacters(clientDto.getFirstName())==false){
					log.error("REGFAILFN");

					return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

				}
				if(userService.checkCharacters(clientDto.getLastName())==false){
					log.error("REGFAILLN");

					return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

				}
				if(userService.checkCharacters(clientDto.getUsername())==false){
					log.error("REGFAILLN");

					return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

				}
				
			}
		
			if (clientDto.getPassword().equals(clientDto.getRePassword())) {
				System.out.println("usao saaaaaasm");
				User u = new User();

				u.setCity(clientDto.getCity());
				u.setEmail(clientDto.getEmail());
				u.setFirstName(clientDto.getFirstName());
				u.setLastName(clientDto.getLastName());
				u.setUsername(clientDto.getUsername());
				u.setPassword(clientDto.getPassword());
				u.setCity("Beograd");
				
				u.setEnabled(true);

				u.setRoles(Collections.singleton(roleRepository.findByName(NameRole.ROLE_USER)));
				
				u.setStatus(ClientStatus.NEAKTIVAN);

				String pass = u.getPassword();

				String salt = BCrypt.gensalt();
				String passwordHashed = BCrypt.hashpw(pass, salt);

				u.setPassword(passwordHashed);
				
				u.setEnabled(true);
				u.setNonLocked(true);

				userService.saveUser(u);
				System.out.println("upisao usera sa mejlom: "+u.getEmail());
				log.info("REGSUCCESS");

				return new ResponseEntity<UserDTO>(clientDto, HttpStatus.OK);
			}
			
			
		return new ResponseEntity<UserDTO>( HttpStatus.NO_CONTENT);

	}

	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> logIn(@RequestBody UserLogin userDTO, HttpServletRequest req) {
		log.debug("LOG");

		boolean response = userService.checkMailExistence(userDTO.getEmail());

		if (response == true) {
			// ako korisnik postoji u bazi

			User userNew = userService.findByEmail(userDTO.getEmail());
			
			if (userNew.getStatus().equals(ClientStatus.NEAKTIVAN)) {
				log.error("User id: {} LOGFAIL",userNew.getId());

				return new ResponseEntity<String>("Vas nalog jos nije aktiviran", HttpStatus.FORBIDDEN);
			} 
			
			if (userNew.getStatus().equals(ClientStatus.BLOKIRAN)) {
				log.error("User id: {} LOGFAIL",userNew.getId());

				return new ResponseEntity<String>("Ne mozete pristupiti", HttpStatus.FORBIDDEN);
			}
			
			
			System.out.println("pass" + userDTO.getPassword());

			// proveravam da li se password podudara sa onim u bazi
			if (BCrypt.checkpw(userDTO.getPassword(), userNew.getPassword())) {

				try{
				UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userDTO.getEmail(),
						userDTO.getPassword());
				
				Authentication auth = authManager.authenticate(authReq);
				
				String email = authReq.getName();
				
				System.out.println("mejl je sledeci" + email);
				
				List<String> authorities = auth.getAuthorities().stream()
	    				.map(GrantedAuthority::getAuthority)
	    				.collect(Collectors.toList());

				//SecurityContext sc = SecurityContextHolder.getContext();
				//sc.setAuthentication(auth);
				
				String token = tokenUtils.generateToken(auth);
				
				User us = userService.findByEmail(email);
				
				//User user1 = (User) auth.getPrincipal();
			//	System.out.println(user1.getEmail());
				
				long expiresIn = tokenUtils.getExpiredIn();
				log.info("User id:{} LOGSUCCESS ", us.getId());

				return new ResponseEntity<>(new UserToken(token,expiresIn), HttpStatus.OK);
				}catch (Exception e) {
					log.warn("User id:{} LOGFAIL ", userNew.getId());
					e.printStackTrace();
					return new ResponseEntity<>(new UserToken(), HttpStatus.NOT_FOUND);
				}
				
			} else {
				log.error("User id:{} LOGFAIL ", userNew.getId());

				return new ResponseEntity<>(new UserToken(), HttpStatus.NOT_FOUND);
			}
		} else {
			log.error("LOGFAIL ");


			return new ResponseEntity<>(new UserToken(), HttpStatus.NOT_FOUND);
		}
	}


	@RequestMapping(value = "/checkIfMailExists/{email}", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public boolean checkCommunication(@PathVariable String email) {

		boolean response = userService.checkMailExistence(email);
		return response;
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4202")
	public ResponseEntity<?> logOutUser() {
		log.debug("LOGOUT");

		System.out.println("usao ovde");
		SecurityContextHolder.clearContext();
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getUsername/{token}", method = RequestMethod.GET)
	public String getUsername(@PathVariable String token) throws Exception {
		
		String username = tokenUtils.getUserSecurity(token).getUsername();
			
		return username;
    	
	}
	
	@RequestMapping(value = "/getPermissions/{token}", method = RequestMethod.GET)
	public String getPermissions(@PathVariable String token) throws Exception {
		
		//List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		
		ArrayList<String> list = new ArrayList<String>();
		
		tokenUtils.getUserSecurity(token).getAuthorities().forEach(e -> {
			
			System.out.println(e.toString());
			list.add(e.toString());
 
		});

		String d = "";
		
		for(int i=0; i<list.size(); i++) {
			d = d + "," + list.get(i);
		}
		
	    return d.substring(1).toString();
		
	}

}
