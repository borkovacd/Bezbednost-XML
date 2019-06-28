package com.ftn.modelDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.ftn.security.SQLInjectionSafe;

public class AgentDTO {
	
	private Long id;
	
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
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String rePassword;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String firstName;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String lastName;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String address;
	
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String mbr;

	public AgentDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMbr() {
		return mbr;
	}

	public void setMbr(String mbr) {
		this.mbr = mbr;
	}
	
	

}
