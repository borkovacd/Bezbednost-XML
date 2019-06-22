package com.ftn.micro3.dto;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.ftn.micro3.model.Agent;
import com.ftn.micro3.model.Room;
import com.ftn.micro3.model.User;

public class ReservationDTO 
{

	private Long id ;
	
	private LocalDate fromDate ;
	
	private LocalDate toDate ;
	
	private Room room ;
	
	private User user ;

	private Agent agent ;
	
	private boolean confirmed = false ;

	public ReservationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReservationDTO(Long id, LocalDate fromDate, LocalDate toDate, Room room, User user, Agent agent,
			boolean confirmed) {
		super();
		this.id = id;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.room = room;
		this.user = user;
		this.agent = agent;
		this.confirmed = confirmed;
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
	
	

}
