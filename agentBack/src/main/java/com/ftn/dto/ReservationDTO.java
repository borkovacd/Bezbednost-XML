package com.ftn.dto;

public class ReservationDTO {
	private String checkInDate;
	private String checkOutDate;
	
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
