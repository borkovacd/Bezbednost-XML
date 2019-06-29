package com.ftn.micro4.service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.micro4.model.Role;
import com.ftn.micro4.model.User;
import com.ftn.micro4.repository.UserRepository;

@Service
public class UserService implements UserDetailsService  {
	
	@Autowired 
	private UserRepository userRepository;
	
	public boolean logIn(User user){
		
		ArrayList<User> list = (ArrayList<User>) userRepository.findAll();
		for(User u : list){
			if(u.getEmail().equals(user.getEmail())){
				if(u.getPassword().equals(user.getPassword()))
					for(Role r : u.getRoles()) {
						System.out.println(r.getName());
					}
					return true;
			}
		}
		return false;
		
	}
	
	public boolean exists(String email) {
		
		ArrayList<User> list = (ArrayList<User>) userRepository.findAll();
		
		for(User u : list){
			if(u.getEmail().equals(email)){
				return false;
				
			}
		}
		
		return true;
		
	}
	
		public void saveUser(User user) {

			userRepository.save(user);
		
	}

		public User findByEmail(String email) {
			return userRepository.findByEmail(email);
		}
		
		public boolean checkMailExistence(String email) {
			
			ArrayList<User> list = (ArrayList<User>) userRepository.findAll();
			
			for(User u : list){
				if(u.getEmail().equals(email)){
					return true;
					
				}
			}
			
			return false;
		}
	

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(mail);
	       
        return (UserDetails) user;
		
	}
	public boolean checkMail(String mail) {
		if(mail.isEmpty()) {
			return false;
		}
		if(mail.contains(";")) {
			return false;
		}
		
		if(mail.contains(",")) {
			return false;
		}
		for(Character c:mail.toCharArray()) {
			if(Character.isWhitespace(c)) {
				return false;
			
			}
				
		}
		return true;
	}
	public boolean checkCharacters(String data) {
		if(data.isEmpty()) {
			return false;
		}
		for(Character c :data.toCharArray()) {
			if(Character.isWhitespace(c)== false && Character.isLetterOrDigit(c) == false) {
				return false;
			}
		}
		
		return true;
	}
	
}
