package com.ftn.modelDTO;

import javax.persistence.Id;

public class MessageDTO 
{
	
	private String agent;
	private String text;

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
