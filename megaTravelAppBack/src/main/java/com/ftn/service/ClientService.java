package com.ftn.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Client;
import com.ftn.model.User;
import com.ftn.repository.ClientRepository;

@Service
public class ClientService  {
	
	@Autowired
	private ClientRepository clientRepository;

	public boolean exists(String email) {

		ArrayList<Client> clients = (ArrayList<Client>) clientRepository.findAll();	
		
		for(int i=0; i<clients.size(); i++) {
			
			if(clients.get(i).getEmail().equals(email)){
				
				System.out.println("postoji client");
				return true;
				
			}
		}
		
		return false;
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

	public void saveClient(Client u) {

		clientRepository.save(u);
		
	}

	public Client findByUsername(String username) {

		return clientRepository.findByUsername(username);
	}

}
