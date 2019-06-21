package com.ftn.micro1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.micro1.enums.NameRole;
import com.ftn.micro1.model.Role;
import com.ftn.micro1.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	public Role findByName(NameRole string) {
		
		return roleRepository.findByName(string);
		
	}

}
