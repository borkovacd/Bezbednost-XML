package com.ftn.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ftn.model.User;

@Service
public interface UserService extends UserDetailsService  {
	
	boolean logIn(User user);
	void saveUser(User user);
	User findByEmail(String email);
	boolean checkMailExistence(String email);
	boolean exists(String email);
	
}
