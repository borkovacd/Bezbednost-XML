package com.ftn.modelDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.ftn.security.SQLInjectionSafe;

public class AdditionalServiceDTO 
{
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String name; 
	
	private double price ;
	
	
	public AdditionalServiceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public AdditionalServiceDTO(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}
	
	
}
