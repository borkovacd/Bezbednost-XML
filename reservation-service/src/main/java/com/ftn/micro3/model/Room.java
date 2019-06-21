package com.ftn.micro3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Room {

	@Id
	private Long id;
	
	@ManyToOne
	private Accomodation accomodation;

	@Column
	private int capacity;

	@Column
	private int floor;

	@Column
	private boolean hasBalcony;

	// koliko dana pre moze da otkaze
	@Column
	private int day;

	@Column
	private boolean reserved = false;
	@Column
	private boolean active = false;

	public Room() {

	}

	public Room(Long id, Accomodation accomodation, int capacity, int floor, boolean hasBalcony, int day, boolean reserved, boolean active) {
		super();
		this.id = id;
		this.accomodation = accomodation;
		this.capacity = capacity;
		this.floor = floor;
		this.hasBalcony = hasBalcony;
		this.day = day;
		this.reserved = reserved;
		this.active = active;
	}

	public Accomodation getAccomodation() {
		return accomodation;
	}

	public void setAccomodation(Accomodation accomodation) {
		this.accomodation = accomodation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
}
