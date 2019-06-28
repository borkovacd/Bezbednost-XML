package com.ftn.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.ftn.security.SQLInjectionSafe;

public class AgentDTO {

	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String username;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String password;

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
