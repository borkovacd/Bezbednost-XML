package com.ftn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CommunicationRelationship {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private CertificateModel certificateCommunication1;
	
	@ManyToOne
	private CertificateModel certificateCommunication2;
	
	public CommunicationRelationship() {
		
	}
	
	public CommunicationRelationship(CertificateModel certificateCommunication1,
			CertificateModel certificateCommunication2) {
		super();
		this.certificateCommunication1 = certificateCommunication1;
		this.certificateCommunication2 = certificateCommunication2;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CertificateModel getCertificateCommunication1() {
		return certificateCommunication1;
	}

	public void setCertificateCommunication1(CertificateModel certificateCommunication1) {
		this.certificateCommunication1 = certificateCommunication1;
	}

	public CertificateModel getCertificateCommunication2() {
		return certificateCommunication2;
	}

	public void setCertificateCommunication2(CertificateModel certificateCommunication2) {
		this.certificateCommunication2 = certificateCommunication2;
	}
	
	



}
