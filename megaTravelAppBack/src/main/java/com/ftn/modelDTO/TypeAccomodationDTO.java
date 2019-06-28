package com.ftn.modelDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.ftn.security.SQLInjectionSafe;

public class TypeAccomodationDTO 
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

	public TypeAccomodationDTO(String name) {
		super();
		this.name = name;
	}

	public TypeAccomodationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
