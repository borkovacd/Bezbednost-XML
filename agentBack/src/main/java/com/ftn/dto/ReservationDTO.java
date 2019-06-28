package com.ftn.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.ftn.security.SQLInjectionSafe;

public class ReservationDTO {
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String checkInDate;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String checkOutDate;
	
	public ReservationDTO(){
		
	}

	public ReservationDTO(String checkInDate, String checkOutDate) {
		super();
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	

}
