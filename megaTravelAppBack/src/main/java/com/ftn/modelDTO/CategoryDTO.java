package com.ftn.modelDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.ftn.security.SQLInjectionSafe;

public class CategoryDTO 
{
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String name ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CategoryDTO(String name) {
		super();
		this.name = name;
	}

	public CategoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
