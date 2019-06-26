package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.Category;
import com.ftn.service.CategoryService;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryContorller {
	private static final Logger log = LoggerFactory.getLogger(CategoryContorller.class);

	@Autowired
	private CategoryService categoryService;

	

	@PreAuthorize("hasAuthority('READ_CAT')")
	@GetMapping("/getAllCategories")
	public ResponseEntity<List<Category>> getCategory() {
		log.info("GETCAT");

		ArrayList<Category> categories = (ArrayList<Category>) categoryService.getAllCategories();
		
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);

	}

}
