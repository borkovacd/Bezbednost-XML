package com.ftn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.AdditionalServices;
import com.ftn.model.Agent;
import com.ftn.repository.AdditionalServicesRepository;
import com.ftn.repository.AgentRepository;

@Service
public class AdditionalServicesService 
{
	@Autowired
	private AdditionalServicesRepository addServRepository;
	
	public AdditionalServices saveAdditionalService(AdditionalServices addServ) {
		return addServRepository.save(addServ);
	}
}
