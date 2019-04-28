package com.ftn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.City;
import com.ftn.repository.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;
	
	public List<City> getAllCities(){
		return cityRepository.findAll();
	}

}
