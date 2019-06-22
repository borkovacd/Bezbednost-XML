package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.AdditionalServices;
import com.ftn.model.Permission;
import com.ftn.repository.PermissionRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllAdditionalServicesRequest;
import com.ftn.webservice.files.GetAllAdditionalServicesResponse;
import com.ftn.webservice.files.GetAllPermissionsRequest;
import com.ftn.webservice.files.GetAllPermissionsResponse;

@Service
public class PermissionService {
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	public ArrayList<Permission> getAllPermissions() {
		
		GetAllPermissionsRequest request = new GetAllPermissionsRequest();
		request.setRequest("Agent request: 'Get all permissions'");
		
		GetAllPermissionsResponse response = (GetAllPermissionsResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<Permission> permissions = new ArrayList<Permission>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all permissions'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getPermissionslist().size(); i++) {
			
			Permission p = new Permission();
			p.setId(response.getPermissionslist().get(i).getId());
			p.setName(response.getPermissionslist().get(i).getName());
			
			permissionRepository.save(p);
			permissions.add(p);
			
		}
		

		return (ArrayList<Permission>) permissions;
	}

}
