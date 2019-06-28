package com.ftn.micro2.controller;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro2.config.TokenUtils;
import com.ftn.micro2.model.Category;
import com.ftn.micro2.model.User;
import com.ftn.micro2.repository.UserRepository;
import com.ftn.micro2.service.CategoryService;

@RestController
@RequestMapping("/api/accomodation/category")
public class CategoryController 
{
	private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	CategoryService service ;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	UserRepository userRepository;
	
	// dodaje novu kategoriju
	@PreAuthorize("hasAuthority('ADD_CAT')")
	@RequestMapping(value="/addNewCategory",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addNewCategory(ServletRequest request, @RequestBody Category cat) throws Exception
	{
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		log.info("User id: {} CREACATE",u.getId());

		
		Category category = service.findByName(cat.getName());
		if(category != null){
			log.error("User id: {} CREACATEERROR",u.getId());

			return false;

		}
		else 
		{
			category = new Category();
			category.setName(cat.getName());
			service.save(category);
			log.info("User id: {} CREACATESUCCESS",u.getId());

			return true;
		}
	}
	
	// brise postojecu kategoriju
	@PreAuthorize("hasAuthority('DEL_CAT')")
	@PostMapping(value = "/removeCategory", consumes = "application/json")
	public ResponseEntity<List<Category>> removeCategory(ServletRequest request, @RequestBody Category cat) throws Exception
	{
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		log.info("User id: {} DELCAT",u.getId());

		
		Category category = service.findByName(cat.getName());
		if(category == null){
			log.error("User id: {} DELCATERROR",u.getId());

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else 
		{
			service.deleteByName(cat.getName());
			log.info("User id: {} DELCATSUCCESS",u.getId());

			return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
		}
	}
	
	// vraca sve kategorije
	@PreAuthorize("hasAuthority('ADD_CAT')")
	@GetMapping(value = "/getCategories")
	public ResponseEntity<List<Category>> getCategories(ServletRequest request) throws Exception
	{
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		
		
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
}
