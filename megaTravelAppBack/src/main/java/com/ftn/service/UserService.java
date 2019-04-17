package com.ftn.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.User;
import com.ftn.repository.UserRepository;

@Service
public class UserService {
	@Autowired 
	private UserRepository userRepository;
	
	public boolean logIn(User user){
		
		ArrayList<User> list = (ArrayList<User>) userRepository.findAll();
		for(User u : list){
			if(u.getEmail().equals(user.getEmail())){
				if(u.getPassword().equals(user.getPassword()))
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

		public boolean checkMailExistence(String email) {
			
			ArrayList<User> list = (ArrayList<User>) userRepository.findAll();
			
			for(User u : list){
				if(u.getEmail().equals(email)){
					return true;
					
				}
			}
			
			return false;
		}
	
}
