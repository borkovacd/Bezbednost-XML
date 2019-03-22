package com.ftn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// prikaz Sertifikata u tabeli, sa njegovim serijskim brojem i statusom - aktivan / neaktivan
@Entity
public class CertificateStatus 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "Serijski_broj")
	private Long serijskiBroj ;
	
	@Column(name = "Status")
	private boolean status ;

	public CertificateStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CertificateStatus(Long serijskiBroj, boolean status) {
		super();
		this.serijskiBroj = serijskiBroj;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSerijskiBroj() {
		return serijskiBroj;
	}

	public void setSerijskiBroj(Long serijskiBroj) {
		this.serijskiBroj = serijskiBroj;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
