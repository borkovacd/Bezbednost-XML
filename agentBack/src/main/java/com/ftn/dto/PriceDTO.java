package com.ftn.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.ftn.security.SQLInjectionSafe;

public class PriceDTO {

	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String month;
	
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
