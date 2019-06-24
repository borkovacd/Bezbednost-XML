package com.ftn.dto;

public class AnswerDTO {

	private String token;
	private String receipient;
	private String text;
	private String idMessage;

	public AnswerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(String idMessage) {
		this.idMessage = idMessage;
	}

	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getReceipient() {
		return receipient;
	}

	public void setReceipient(String receipient) {
		this.receipient = receipient;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public AnswerDTO(String token, String receipient, String text) {
		super();
		this.token = token;
		this.receipient = receipient;
		this.text = text;
	}

}
