package com.ftn.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name="Certificates")
public class CertificateModel 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	private SubjectSoftware issuerSoft;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private SubjectSoftware subSoft;
	
	
	
	public CertificateModel(SubjectSoftware issuerSoft, SubjectSoftware subSoft, Date startDate, Date endDate,
			boolean isCertificateAuthority, boolean isRevoked, String reasonsForRevocation) {
		super();
		this.issuerSoft = issuerSoft;
		this.subSoft = subSoft;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isCertificateAuthority = isCertificateAuthority;
		this.isRevoked = isRevoked;
		this.reasonForRevokation = reasonsForRevocation;
	}

	@Column(name="StartDate")
	private Date startDate;
	
	@Column
	private Integer serialNumber;
	
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

	public CertificateModel() {
		super();

	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public SubjectSoftware getIssuerSoft() {
		return issuerSoft;
	}

	public void setIssuerSoft(SubjectSoftware issuerSoft) {
		this.issuerSoft = issuerSoft;
	}

	public SubjectSoftware getSubSoft() {
		return subSoft;
	}

	public void setSubSoft(SubjectSoftware subSoft) {
		this.subSoft = subSoft;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	
	

}
