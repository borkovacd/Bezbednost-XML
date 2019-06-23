package com.ftn.dto;

public class SearchRoomDTO {
	private String checkInDate;
	private String checkOutDate;
	
	public SearchRoomDTO(){
		
	}

	public SearchRoomDTO(String checkInDate, String checkOutDate) {
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
