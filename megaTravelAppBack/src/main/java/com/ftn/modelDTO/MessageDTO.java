package com.ftn.modelDTO;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.ftn.security.SQLInjectionSafe;

public class MessageDTO 
{
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String agent;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String text;

	public MessageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageDTO(String agent, String text) {
		
		super();
		this.agent = agent;
		this.text = text;
	}


	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
