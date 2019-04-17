package com.ftn.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.model.Role;
import com.ftn.model.User;
import com.ftn.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	

	@Autowired 
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(mail);
	       
        return user;
	}
	

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

}
