package com.ftn.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.ftn.security.SQLInjectionSafe;

public class AnswerDTO {

	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String token;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String receipient;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String text;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String idMessage;

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
