package com.ftn.micro2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.micro2.model.AccomodationType;
import com.ftn.micro2.model.Category;
import com.ftn.micro2.repository.CategoryRepository;

@Service
public class CategoryService 
{	
	@Autowired
	CategoryRepository repository ;
	
	public Category findByName(Category cat)
	{
		return repository.findCategoryByName(cat.getName());
	}
	
	public void save(Category cat)
	{
		repository.save(cat);
	}
	
	public List<Category> getAll()
	{
		return repository.findAll();
	}
	
	// brisanje kategorije iz liste
	public List<Category> deleteByName(String name)
	{	
		Category cat = repository.findCategoryByName(name);
		repository.delete(cat);
		return repository.findAll();
	}
}
