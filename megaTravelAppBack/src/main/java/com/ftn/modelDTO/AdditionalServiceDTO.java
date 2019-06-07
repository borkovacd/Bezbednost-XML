package com.ftn.modelDTO;

public class AdditionalServiceDTO 
{
	private String name; 
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
