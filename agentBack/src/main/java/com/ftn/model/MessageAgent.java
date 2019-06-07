package com.ftn.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;



@Entity

public class MessageAgent implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne	
	private Message message;
	
	@OneToOne
	private Agent agent;
	
	
	public MessageAgent() {
		// TODO Auto-generated constructor stub
	}
	


	public MessageAgent(Long id, Message message, Agent agent) {
		super();
		this.id = id;
		this.message = message;
		this.agent = agent;
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Message getMessage() {
		return message;
	}


	public void setMessage(Message message) {
		this.message = message;
	}


	public Agent getAgent() {
		return agent;
	}


	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	
	
	
}
