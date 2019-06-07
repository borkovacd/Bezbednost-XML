package com.ftn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity

public class Price {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
   
	@ManyToOne
    private Accomodation accomodation;

    private String month;
    
    
    private String sum;
    
    
    
    
    public Price() {
		// TODO Auto-generated constructor stub
	}


	


	public Price(long id, Accomodation accomodation, String month, String sum) {
		super();
		this.id = id;
		this.accomodation = accomodation;
		this.month = month;
		this.sum = sum;
	}





	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Accomodation getAccomodation() {
		return accomodation;
	}


	public void setAccomodation(Accomodation accomodation) {
		this.accomodation = accomodation;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}





	public String getSum() {
		return sum;
	}





	public void setSum(String sum) {
		this.sum = sum;
	}
	
	
	
	
}
