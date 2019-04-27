package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.service.CategoryService;

@RestController
@RequestMapping( value = "/api/category")
public class CategoryContorller {
	@Autowired
	private CategoryService categoryService;
	

}
