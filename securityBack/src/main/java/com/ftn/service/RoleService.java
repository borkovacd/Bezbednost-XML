package com.ftn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.enums.NameRole;
import com.ftn.model.Role;
import com.ftn.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	public Role findByName(NameRole string) {
		
		return roleRepository.findByName(string);
		
	}

}
