package com.ftn.micro3.dto;

import java.time.LocalDate;

import com.ftn.micro3.model.City;

public class BasicSearchDTO 
{
	private Long id ;
	private String fromDate ;
	private String toDate ;
	private String city ;
	private int numberOfPersons ;
	
	
	public BasicSearchDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BasicSearchDTO(String fromDate, String toDate, String city, int numberOfPersons) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.city = city;
		this.numberOfPersons = numberOfPersons;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFromDate() {
		return fromDate;
	}


	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}


	public String getToDate() {
		return toDate;
	}


	public void setToDate(String toDate) {
		this.toDate = toDate;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public int getNumberOfPersons() {
		return numberOfPersons;
	}


	public void setNumberOfPersons(int numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}



	
	
}
