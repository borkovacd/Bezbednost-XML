package com.ftn.micro2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdditionalServices 
{
	@Column
	private String name ;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long additionalServiceId;
	
	public AdditionalServices() 
	{
		
	}
	
	

	public AdditionalServices(String name) {
		super();
		this.name = name;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public long getAdditionalServiceId() {
		return additionalServiceId;
	}

	public void setAdditionalServiceId(long additionalServiceId) {
		this.additionalServiceId = additionalServiceId;
	}


}
