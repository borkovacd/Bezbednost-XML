package com.ftn.micro3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Agent 
{
	@Id
	private Long id;

	@NotNull
	@Column
	private String username;

	@NotNull
	@Column
	private String password;

	@NotNull
	@Column
	private String firstName;

	@NotNull
	@Column
	private String lastName;

	@NotNull
	@Column
	private String address;

	@NotNull
	@Column
	private String mbr;

	public Agent() {

	}

	public Agent(Long id, @NotNull String username, @NotNull String password, @NotNull String firstName,
			@NotNull String lastName, @NotNull String address, @NotNull String mbr) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.mbr = mbr;
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
