package com.ftn.micro2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro2.model.AccomodationType;
import com.ftn.micro2.model.Category;
import com.ftn.micro2.service.CategoryService;

@RestController
@RequestMapping("/api/accomodation/category")
public class CategoryController 
{
	@Autowired
	CategoryService service ;
	
	// dodaje novu kategoriju
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/addNewCategory",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addNewCategory(@RequestBody Category cat)
	{
		
		Category category = service.findByName(cat.getName());
		if(category != null)
			return false;
		else 
		{
			category = new Category();
			category.setName(cat.getName());
			service.save(category);
			return true;
		}
	}
	
	// brise postojecu kategoriju
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "/removeCategory", consumes = "application/json")
	public ResponseEntity<List<Category>> removeCategory(@RequestBody Category cat)
	{
		Category category = service.findByName(cat.getName());
		if(category == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else 
		{
			service.deleteByName(cat.getName());
			return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
		}
	}
	
	// vraca sve kategorije
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/getCategories")
	public ResponseEntity<List<Category>> getCategories()
	{
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
}
