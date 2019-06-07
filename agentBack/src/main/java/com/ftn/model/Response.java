package com.ftn.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table

public class Response implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
	@OneToOne
	private Agent sender;
	
	@OneToOne
	private User recipient;
	
	private String text;

	
	public Response() {
		// TODO Auto-generated constructor stub
	}

	public Response(Long id, Agent sender, User recipient, String text) {
		super();
		this.id = id;
		this.sender = sender;
		this.recipient = recipient;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Agent getSender() {
		return sender;
	}

	public void setSender(Agent sender) {
		this.sender = sender;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	
	
	
	

}