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
	
	private String fromDate ;
	
	private String toDate ;
	
	private Long idRoom ;
	
	private String token;

	private Long idAgent ;
	
	private boolean confirmed = false ;

	public ReservationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReservationDTO(String fromDate, String toDate, Long idRoom, String token, Long idAgent) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.idRoom = idRoom;
		this.token = token;
		this.idAgent = idAgent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Long getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(Long idRoom) {
		this.idRoom = idRoom;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Long idAgent) {
		this.idAgent = idAgent;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	

}
