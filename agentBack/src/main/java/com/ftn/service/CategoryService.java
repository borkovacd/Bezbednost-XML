package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Category;
import com.ftn.repository.CategoryRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllCategoriesRequest;
import com.ftn.webservice.files.GetAllCategoriesResponse;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SOAPConnector soapConnector;

	public List<Category> getAllCategories() {
		
		GetAllCategoriesRequest request = new GetAllCategoriesRequest();
		request.setRequest("Agent request: 'Get all existing categories'");
		
		GetAllCategoriesResponse response = (GetAllCategoriesResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<Category> categories = new ArrayList<Category>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all existing categories'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getCategoriesList().size(); i++) {
			
			Category c = new Category();
			c.setId(response.getCategoriesList().get(i).getId());
			c.setName(response.getCategoriesList().get(i).getName());
			
			categoryRepository.save(c);
			categories.add(c);
			
		}
		

		return (ArrayList<Category>) categories;
	}

}
