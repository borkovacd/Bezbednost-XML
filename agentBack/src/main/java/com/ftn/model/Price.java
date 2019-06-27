package com.ftn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity

public class Price {

	@NotNull
	@Id
	private long id;

	@NotNull
	@ManyToOne
	private Room room;

	@NotNull
	private String month;

	@NotNull
	private Double price;

	public Price() {
		// TODO Auto-generated constructor stub
	}

	public Price(long id, Room room, String month, Double price) {
		super();
		this.id = id;
		this.room = room;
		this.month = month;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
