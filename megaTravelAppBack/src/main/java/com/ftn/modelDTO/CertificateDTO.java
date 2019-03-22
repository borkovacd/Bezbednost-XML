package com.ftn.modelDTO;

import java.util.Date;

public class CertificateDTO 
{
	private String softwareID;
	private Date startDate;
	private Date endDate;
	private boolean isRevoked; // da li je povucen ili nije
	private String reasonsForRevokation; // 
	private String password ;
	private String city;
	
	
	public CertificateDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CertificateDTO(String softwareID, Date startDate, Date endDate, boolean isRevoked,
			String reasonsForRevokation, String city, String password) {
		super();
		this.softwareID = softwareID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isRevoked = isRevoked;
		this.reasonsForRevokation = reasonsForRevokation;
		this.password = password ;
	}

	public String getSoftwareID() {
		return softwareID;
	}

	public void setSoftwareID(String softwareID) {
		this.softwareID = softwareID;
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

	public boolean isRevoked() {
		return isRevoked;
	}

	public void setRevoked(boolean isRevoked) {
		this.isRevoked = isRevoked;
	}

	public String getReasonsForRevokation() {
		return reasonsForRevokation;
	}

	public void setReasonsForRevokation(String reasonsForRevokation) {
		this.reasonsForRevokation = reasonsForRevokation;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
}
