package com.ftn.modelDTO;

import javax.persistence.Id;

public class MessageDTO 
{
	
	private String sender; // od klase USER

	private String idRecipient; // od klase AGENT

	private String text;

	public MessageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageDTO(String sender, String idRecipient, String text) {
		super();
		this.sender = sender;
		this.idRecipient = idRecipient;
		this.text = text;
	}



	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}



	public String getIdRecipient() {
		return idRecipient;
	}

	public void setIdRecipient(String idRecipient) {
		this.idRecipient = idRecipient;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
