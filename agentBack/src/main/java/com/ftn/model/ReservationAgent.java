package com.ftn.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ReservationAgent {
	

	@Id
	private Long id;

	@NotNull
	private LocalDate fromDate;
	
	@NotNull
	private LocalDate toDate;

	@NotNull
	@OneToOne
	private Room room;
	
	@NotNull
	@OneToOne
	private Agent agent;
	
	public ReservationAgent(){
		
	}


	public ReservationAgent(Long id, LocalDate fromDate, LocalDate toDate, Room room, Agent agent) {
		super();
		this.id = id;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.room = room;
		this.agent = agent;
	}
	


	public Agent getAgent() {
		return agent;
	}


	public void setAgent(Agent agent) {
		this.agent = agent;
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
	
	
}
