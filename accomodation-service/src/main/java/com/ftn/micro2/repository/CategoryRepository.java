package com.ftn.micro2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.micro2.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>
{
	public Category save(Category cat);
	
	public Category findCategoryByName(String name);
	
	public List<Category> findAll();
	
	public void deleteById(Long id);
	
	public void deleteByName(String name);
}
