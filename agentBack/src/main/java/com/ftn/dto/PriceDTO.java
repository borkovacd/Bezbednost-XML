package com.ftn.dto;

public class PriceDTO {

	private String month;
	
	private Double sum;
	
	public PriceDTO() {
		// TODO Auto-generated constructor stub
	}

	public PriceDTO(String month, Double sum) {
		super();
		this.month = month;
		this.sum = sum;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}
	
	
	
	
}
