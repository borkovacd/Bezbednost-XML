package com.ftn.dto;

public class PriceDTO {

	private String month;
	
	private String sum;
	
	public PriceDTO() {
		// TODO Auto-generated constructor stub
	}

	public PriceDTO(String month, String sum) {
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

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}
	
	
	
	
}
