package com.ftn.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {
	
	@Id
	private Long id;

	@Column
	private int capacity;

	@Column
	private int floor;

	@Column
	private boolean hasBalcony;

	@Column
	private boolean active = false;

	public Room() {

	}

	public Room(Long id, int capacity, int floor, boolean hasBalcony, boolean active) {
		super();
		this.id = id;
		this.capacity = capacity;
		this.floor = floor;
		this.hasBalcony = hasBalcony;
		this.active = active;
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

}
