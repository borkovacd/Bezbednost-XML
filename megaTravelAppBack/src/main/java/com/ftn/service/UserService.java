package com.ftn.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.model.User;

@Service
@Transactional
public interface UserService extends UserDetailsService {
	
	boolean logIn(User user);
	boolean exists(String email);
	void saveUser(User user);
	User findByEmail(String email);
	boolean checkMailExistence(String mail);
	
	
	
}
