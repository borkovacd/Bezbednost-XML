package com.ftn.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Response implements Serializable {


	@Id
	private Long id;

	@NotNull
	@OneToOne
	private Agent sender;

	@NotNull
	@OneToOne
	private User recipient;
	
	@NotNull
	@OneToOne
	private Message message;
	
	@NotNull
	private String text;

	public Response() {
	}

	public Response(Long id, Agent sender, User recipient, String text, Message message) {
		super();
		this.id = id;
		this.sender = sender;
		this.recipient = recipient;
		this.text = text;
		this.message = message;
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

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	

}