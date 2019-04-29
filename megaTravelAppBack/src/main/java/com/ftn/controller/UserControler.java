package com.ftn.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.ftn.model.User;

import com.ftn.modelDTO.UserDTO;
import com.ftn.repository.UserRepository;
import com.ftn.service.RoleService;

import com.ftn.service.UserServiceImpl;

@RestController
@RequestMapping(value = "/api/user")
public class UserControler {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private UserRepository userRep;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthenticationManager authManager;
	
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<UserDTO> registrate(@RequestBody UserDTO userDto) {
		System.out.println("usao da se registruje sa mejlom:  " + userDto.getEmail());

		boolean response = userService.exists(userDto.getEmail());
		if (response == true) {
			System.out.println("vec postoji dati mejl");
			return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

		} else {
			if(userService.checkMail(userDto.getEmail() )== false){
				return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

			}
			if(userService.checkCharacters(userDto.getFirstName())==false){
				return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);

			}
			if(userService.checkCharacters(userDto.getLastName())==false){
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

				return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
			}
		}
		return new ResponseEntity<UserDTO>( HttpStatus.NO_CONTENT);

	}

	
	
	
	

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<User> logIn(@RequestBody User user, HttpServletRequest req) {

		boolean response = userService.logIn(user);

		System.out.println(user.getPassword());

		if (response == true) {
			// ako korisnik postoji u bazi

			User userNew = userService.findByEmail(user.getEmail());

			// proveravam da li se password podudara sa onim u bazi
			if (BCrypt.checkpw(user.getPassword(), userNew.getPassword())) {

				System.out.println("pozzz");

				System.out.println("zzzzzzzzzzzzzzzz");

				UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getEmail(),
						user.getPassword());
				System.out.println("da li je prosao>");
				Authentication auth = authManager.authenticate(authReq);

				SecurityContext sc = SecurityContextHolder.getContext();
				sc.setAuthentication(auth);

				HttpSession session = req.getSession(true);
				session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

				/*
				 * for (GrantedAuthority a : ud.getAuthorities()) { Role s =
				 * (Role) a; System.out.println(s.getName()); }
				 */

				// System.out.println(user1.getEmail());

				// System.out.println(cud.getUsername());
				// System.out.println(cud.getPassword());

				return new ResponseEntity<User>(userNew, HttpStatus.OK);

				// final Authentication authentication = authenticationManager
				// .authenticate(new
				// UsernamePasswordAuthenticationToken(user.getEmail(),
				// user.getPassword()));

				// Ubaci username + password u kontext
				// SecurityContextHolder.getContext().setAuthentication(authentication);

			} else {
				return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
	}

	
	@RequestMapping(value = "/checkIfMailExists/{email}", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public boolean checkCommunication(@PathVariable String email) {

		boolean response = userService.checkMailExistence(email);
		return response;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logOutUser() {

		SecurityContextHolder.clearContext();
	}

}
