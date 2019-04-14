package com.ftn.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Certificate 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Long idIssuer;
	
	@Column
	private Long idSubject;
	
	@Column
	private Date startDate;
	
	@Column
	private Date endDate;
	
	@Column
	private boolean isCertificateAuthority;
	
	@Column
	private boolean isRevoked;
	
	@Column
	private String reasonForRevokation;
	
	@Column
	private Long idCertificateIssuer;

	public Certificate() {

	}
	
	public Certificate(Long idIssuer,
			Long idSubject, Date startDate, Date endDate, boolean certificateAuthority, boolean isRevoked, String reasonForRevokation) {
		super();
	
		this.idIssuer = idIssuer;
		this.idSubject = idSubject;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isCertificateAuthority = certificateAuthority;
		this.isRevoked = isRevoked;
		this.reasonForRevokation = reasonForRevokation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdIssuer() {
		return idIssuer;
	}

	public void setIdIssuer(Long idIssuer) {
		this.idIssuer = idIssuer;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isCertificateAuthority() {
		return isCertificateAuthority;
	}

	public void setCertificateAuthority(boolean isCertificateAuthority) {
		this.isCertificateAuthority = isCertificateAuthority;
	}

	public boolean isRevoked() {
		return isRevoked;
	}

	public void setRevoked(boolean isRevoked) {
		this.isRevoked = isRevoked;
	}

	public String getReasonForRevokation() {
		return reasonForRevokation;
	}

	public void setReasonForRevokation(String reasonForRevokation) {
		this.reasonForRevokation = reasonForRevokation;
	}

	public Long getIdCertificateIssuer() {
		return idCertificateIssuer;
	}

	public void setIdCertificateIssuer(Long idCertificateIssuer) {
		this.idCertificateIssuer = idCertificateIssuer;
	}
	
	

}
