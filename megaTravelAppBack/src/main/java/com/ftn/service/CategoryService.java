package com.ftn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.AdditionalServices;
import com.ftn.model.Category;
import com.ftn.repository.AdditionalServicesRepository;
import com.ftn.repository.CategoryRepository;

@Service
public class CategoryService 
{
	@Autowired
	private CategoryRepository categoryRepository;
	
	public void saveCategory(Category cat) {
		categoryRepository.save(cat);
	}
}
