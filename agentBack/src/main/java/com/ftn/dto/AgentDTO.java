package com.ftn.dto;

public class AgentDTO {

	private String username;
	private String password;

	public AgentDTO() {
		super();
	}

	public AgentDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
