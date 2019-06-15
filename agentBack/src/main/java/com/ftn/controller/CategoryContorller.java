package com.ftn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.Category;
import com.ftn.service.CategoryService;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryContorller {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/getAllCategories")
	public ResponseEntity<List<Category>> getCategory() {
		
		ArrayList<Category> categories = (ArrayList<Category>) categoryService.getAllCategories();
		
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);

	}

}
