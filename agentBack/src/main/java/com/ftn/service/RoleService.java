package com.ftn.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Accomodation;
import com.ftn.model.AdditionalServices;
import com.ftn.model.Permission;
import com.ftn.model.Role;
import com.ftn.repository.PermissionRepository;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.UserRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllAccomodationsRequest;
import com.ftn.webservice.files.GetAllAccomodationsResponse;
import com.ftn.webservice.files.GetAllReservationsRequest;
import com.ftn.webservice.files.GetAllRolesRequest;
import com.ftn.webservice.files.GetAllRolesResponse;
import com.ftn.webservice.files.NameRole;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	public ArrayList<Role> getAllRoles() {
		
		GetAllRolesRequest request = new GetAllRolesRequest();
		request.setRequest("Agent request: 'Return all existing roles. '");
		
		GetAllRolesResponse response = (GetAllRolesResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		List<Role> roles = new ArrayList<Role>();
		
		for(int i = 0; i < response.getRolelist().size(); i++) {
			
			Role r = new Role();
			r.setId(response.getRolelist().get(i).getId());
			NameRole nameRole = response.getRolelist().get(i).getName(); //webservice enum NameRole
			if(nameRole.equals(NameRole.ROLE_ADMIN)) {
				com.ftn.enums.NameRole nameRole2 = com.ftn.enums.NameRole.ROLE_ADMIN;
				r.setName(nameRole2);
			} else if (nameRole.equals(NameRole.ROLE_AGENT)) {
				com.ftn.enums.NameRole nameRole2 = com.ftn.enums.NameRole.ROLE_AGENT;
				r.setName(nameRole2);
			} else {
				com.ftn.enums.NameRole nameRole2 = com.ftn.enums.NameRole.ROLE_USER;
				r.setName(nameRole2);
			}
			List<Permission> permissions = new ArrayList<Permission>();
			for(int j=0; j<response.getRolelist().get(i).getPermissions().size(); j++) {
				String permissionName = response.getRolelist().get(i).getPermissions().get(j).getName();
				Permission permission = permissionRepository.findByName(permissionName);
				permissions.add(permission);
			}
			
			Set<Permission> setPermissions = new HashSet<Permission>(permissions);
			r.setPermissions(setPermissions);

			roleRepository.save(r);
			roles.add(r);
			
		}
		

		return (ArrayList<Role>) roles;
	}
	
	

}
