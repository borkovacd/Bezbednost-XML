package com.ftn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

// prikaz Sertifikata u tabeli, sa njegovim serijskim brojem i statusom - aktivan / neaktivan
@Entity
public class CertificateStatus 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "Serijski_broj")
	private Integer serijskiBroj ;
	
	@Column(name = "Status")
	private boolean status ;
	
	@Column(name = "Message")
	private String message ;

	public CertificateStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CertificateStatus(Integer serijskiBroj, boolean status,String message) {
		super();
		this.serijskiBroj = serijskiBroj;
		this.status = status;
		this.message=message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSerijskiBroj() {
		return serijskiBroj;
	}

	public void setSerijskiBroj(Integer serijskiBroj) {
		this.serijskiBroj = serijskiBroj;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
