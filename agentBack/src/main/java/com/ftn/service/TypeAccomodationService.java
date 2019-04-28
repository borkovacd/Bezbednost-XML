package com.ftn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.TypeAccomodation;

import com.ftn.repository.TypeAccomodationRepository;

@Service
public class TypeAccomodationService {
	@Autowired
	private TypeAccomodationRepository typeRepository;

	public List<TypeAccomodation> getAllTypes() {
		return typeRepository.findAll();
	}

}
