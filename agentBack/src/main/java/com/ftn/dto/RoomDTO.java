package com.ftn.dto;

import javax.persistence.Column;

public class RoomDTO {

	private int capacity;

	private int floor;

	private boolean hasBalcony;

	// koliko dana pre moze da otkaze
	private int day;
	
	public RoomDTO(){
		
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public boolean isHasBalcony() {
		return hasBalcony;
	}

	public void setHasBalcony(boolean hasBalcony) {
		this.hasBalcony = hasBalcony;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
}
