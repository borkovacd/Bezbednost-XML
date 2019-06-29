package com.ftn.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.model.Permission;
import com.ftn.model.Role;
import com.ftn.model.User;
import com.ftn.modelDTO.UserDTO;
import com.ftn.repository.UserRepository;
import com.ftn.security.UserSecurity;

@Service
public class UserService implements UserDetailsService  {

	
	@Autowired 
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(mail);
	       
        return getUserSecurity(user);
		
	}
	
	private UserSecurity getUserSecurity(User user) {
		
		Set<Role> roles = user.getRoles();
		
		Set<String> perm = new HashSet<String>();
		
		for(Role r: roles) {
			
			for(Permission p: r.getPermissions()) {
				
				
				perm.add(p.getName());
			}
		}
		
		List<GrantedAuthority> authorites = new ArrayList<GrantedAuthority>();
		for(String s: perm) {
		
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(s);
			authorites.add(authority);
			
		}
		
		return new UserSecurity(user.getId(), user.getPassword(), user.getEmail(), user.isEnabled(), authorites, user.isNonLocked());
	}
	
	public void activateUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}
/*
	public List<String> getUserAuthorities(User user) {
		Stream<String> roles = user.getRoles().stream().map(Role::getName).map(Enum::name);

		Stream<String> permissions = user.getRoles().stream().map(Role::getPermissions).flatMap(Collection::stream).map(Permission::getName);

		List<GrantedAuthority> authorities = Stream.concat(roles, permissions).distinct().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		List<String> auts = new ArrayList<String>();
		
		for (GrantedAuthority auth : authorities) {
			auts.add(auth.getAuthority());
		}

		return auts;
	}
	
	*/
	public boolean logIn(UserDTO user){
		
		ArrayList<User> list = (ArrayList<User>) userRepository.findAll();
		for(User u : list){
			if(u.getEmail().equals(user.getEmail())){
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
