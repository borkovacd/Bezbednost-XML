package com.ftn.micro2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.micro2.model.AccomodationType;
import com.ftn.micro2.model.AdditionalServices;
import com.ftn.micro2.repository.AdditionalServicesRepository;

@Service
public class AdditionalServicesService 
{
	@Autowired
	AdditionalServicesRepository repository;
	
	public AdditionalServices findByName(AdditionalServices add) 
	{
		 return repository.findAdditionalServicesByName(add.getName());
	}
	
	public void save(AdditionalServices service) 
	{
		repository.save(service);
	}
	
	// lista svih dodatnih servisa
	public List<AdditionalServices> getAll() {
		return repository.findAll();
	}
	
	// vraca listu dodatnih servisa, nakon brisanja servisa sa prosledjenim Id-jem
	public List<AdditionalServices> deleteByName(String name)
	{
		AdditionalServices addServ = repository.findAdditionalServicesByName(name);
		repository.delete(addServ);
		return repository.findAll();
	}
}
