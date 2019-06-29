package com.ftn.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.SubjectSoftware;
import com.ftn.repository.SubjectSoftwareRepository;

@Service
public class SubjectSoftwareService {
	
	@Autowired
	private SubjectSoftwareRepository subSoftRepository;

	
	public ArrayList<SubjectSoftware> getSoftwares(){
		
		return (ArrayList<SubjectSoftware>) subSoftRepository.findAll();
	}
	
	public SubjectSoftware getSoftware(String city) {
		
		return subSoftRepository.findByCity(city);
		
	}
	
	public SubjectSoftware getSoftwareByEmail(String email) {
		
		return subSoftRepository.findByEmail(email);
		
	}
}
