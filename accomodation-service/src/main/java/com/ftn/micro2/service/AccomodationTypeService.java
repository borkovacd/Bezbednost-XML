package com.ftn.micro2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.micro2.model.AccomodationType;
import com.ftn.micro2.repository.AccomodationTypeRepository;

@Service
public class AccomodationTypeService 
{
	
	@Autowired
	AccomodationTypeRepository repository ;
	
	public AccomodationType findByName(AccomodationType acc)
	{
		return repository.findAccomodationTypeByName(acc.getName());
	}
	
	public void save(AccomodationType acc)
	{
		repository.save(acc);
	}
	
	// metoda kojom se vracaju svi tipovi smestaja
	public List<AccomodationType> getAll()
	{
		return repository.findAll();
	}
	
	// brisanje tipa smestaja iz liste
	public List<AccomodationType> deleteByName(String name)
	{	
		AccomodationType accType = repository.findAccomodationTypeByName(name);
		repository.delete(accType);
		return repository.findAll();
	}
	
	
}
