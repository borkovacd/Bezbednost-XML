package com.ftn.modelDTO;

import javax.persistence.Id;

public class MessageDTO 
{
	@Id
	private Long idMessage;
	
	private String sender; // od klase USER

	private Long idRecipient; // od klase AGENT

	private String text;

	public MessageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageDTO(Long idMessage, String sender, Long idRecipient, String text) {
		super();
		this.idMessage = idMessage;
		this.sender = sender;
		this.idRecipient = idRecipient;
		this.text = text;
	}


	public Long getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(Long idMessage) {
		this.idMessage = idMessage;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}



	public Long getIdRecipient() {
		return idRecipient;
	}

	public void setIdRecipient(Long idRecipient) {
		this.idRecipient = idRecipient;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
