package com.ftn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="SubjectSoftware")
public class SubjectSoftware {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="State")
	private String state;
	
	@Column(name="City")
	private String city;

	@Column(name="Email")
	private String email;

	@Column(name="HasCertificate")
	private boolean hasCert;
	
	public SubjectSoftware() {
		super();
		hasCert = false;
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isHasCert() {
		return hasCert;
	}

	public void setHasCert(boolean hasCert) {
		this.hasCert = hasCert;
	}
	
	
}
