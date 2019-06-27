package com.ftn.micro2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Category 
{
	@NotNull
	@Column(unique = true)
	private String name ;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	public Category(String name) {
		super();
		this.name = name;
	}

	public Category() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
