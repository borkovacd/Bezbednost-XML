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
				return true;
				
			}
		}
		return false;
		
	}
	
}
