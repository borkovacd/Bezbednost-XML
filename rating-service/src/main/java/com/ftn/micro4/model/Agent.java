package com.ftn.micro4.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity(name="Agents")
public class Agent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name="Username")
	private String username;

	@NotNull
	@Column(name="Password")
	private String password;

	@NotNull
	@Column(name="FirstName")
	private String firstName;

	@NotNull
	@Column(name="LastName")
	private String lastName;

	@NotNull
	@Column(name="Address")
	private String address;

	@NotNull
	@Column(name="Mbr")
	private String mbr;
	
	@Column(name = "Enabled")
	private boolean enabled;
	
	@Column(name="Nonlocked")
	private boolean nonLocked;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "agents_roles", joinColumns = @JoinColumn(name = "agents_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private Set<Role> roles;


	public Agent() {

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isNonLocked() {
		return nonLocked;
	}

	public void setNonLocked(boolean nonLocked) {
		this.nonLocked = nonLocked;
	}
	
	
	
	

}
