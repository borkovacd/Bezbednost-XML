package com.ftn.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.model.Agent;
import com.ftn.model.Permission;
import com.ftn.model.Role;
import com.ftn.model.User;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.UserRepository;
import com.ftn.security.UserSecurity;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.ClientStatus;
import com.ftn.webservice.files.GetAllAgentsRequest;
import com.ftn.webservice.files.GetAllAgentsResponse;
import com.ftn.webservice.files.GetAllUsersRequest;
import com.ftn.webservice.files.GetAllUsersResponse;
import com.ftn.webservice.files.NameRole;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
	       
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
	
	public ArrayList<User> getAllUsers() {
		
		GetAllUsersRequest request = new GetAllUsersRequest();
		request.setRequest("Agent request: 'Get all existing users'");
		
		GetAllUsersResponse response = (GetAllUsersResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<User> users = new ArrayList<User>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all existing users'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getUserslist().size(); i++) {
			
			User u = new User();
			u.setId(response.getUserslist().get(i).getId());
			u.setFirstName(response.getUserslist().get(i).getFirstName());
			u.setLastName(response.getUserslist().get(i).getLastName());
			u.setUsername(response.getUserslist().get(i).getUsername());
			u.setPassword(response.getUserslist().get(i).getPassword());
			u.setEmail(response.getUserslist().get(i).getEmail());
			u.setCity(response.getUserslist().get(i).getCity());
			ClientStatus clientStatus = response.getUserslist().get(i).getClientStatus();
			if(clientStatus.equals(ClientStatus.AKTIVAN)) {
				com.ftn.enums.ClientStatus clientStatus2 = com.ftn.enums.ClientStatus.AKTIVAN;
				u.setStatus(clientStatus2);
			} else if (clientStatus.equals(ClientStatus.BLOKIRAN)) {
				com.ftn.enums.ClientStatus clientStatus2 = com.ftn.enums.ClientStatus.BLOKIRAN;
				u.setStatus(clientStatus2);
			} else if (clientStatus.equals(ClientStatus.NEAKTIVAN)) {
				com.ftn.enums.ClientStatus clientStatus2 = com.ftn.enums.ClientStatus.NEAKTIVAN;
				u.setStatus(clientStatus2);
			} else if (clientStatus.equals(ClientStatus.UKLONJEN)) {
				com.ftn.enums.ClientStatus clientStatus2 = com.ftn.enums.ClientStatus.UKLONJEN;
				u.setStatus(clientStatus2);
			}
			u.setEnabled(response.getUserslist().get(i).isEnabled());
			u.setNonLocked(response.getUserslist().get(i).isNonLocked());
			
			userRepository.save(u);
			users.add(u);
			
		}
	
		return (ArrayList<User>) users;
	}


}
