package com.ftn.micro3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class AdditionalServices 
{
	@Id
	private Long id;

	@NotNull
	@Column(unique = true)
	private String name;

	public AdditionalServices() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdditionalServices(Long id, @NotNull String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
