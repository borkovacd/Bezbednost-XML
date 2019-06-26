package com.ftn.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Reservation {
	
	@Id
	private Long id;

	@Column
	private LocalDate fromDate;

	@Column
	private LocalDate toDate;

	@OneToOne
	private Room room;

	@ManyToOne(fetch = FetchType.EAGER)
	// @JsonIgnore
	private User user;

	@OneToOne(fetch = FetchType.EAGER)
	// @JsonIgnore
	private Agent agent;
	
	@Column(name="Price")
	private double price;

	@Column(name="Confirmed")
	private boolean confirmed = false;

	public Reservation() {

	}

	public Reservation(Long id, LocalDate fromDate, LocalDate toDate, Room room, User user, Agent agent,
			boolean confirmed, float price) {
		super();
		this.id = id;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.room = room;
		this.user = user;
		this.agent = agent;
		this.confirmed = confirmed;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	

}
