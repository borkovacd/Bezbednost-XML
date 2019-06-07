package com.ftn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.AdditionalServices;
import com.ftn.model.TypeAccomodation;
import com.ftn.repository.AdditionalServicesRepository;
import com.ftn.repository.TypeAccomodationRepository;

@Service
public class TypeAccomodationService {

	@Autowired
	private TypeAccomodationRepository typeAccRepository;
	
	public void saveTypeAccomodation(TypeAccomodation typeAcc) {
		typeAccRepository.save(typeAcc);
	}
}
