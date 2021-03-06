package com.ftn.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Accomodation {
	
	@Id
	private Long id;

	@NotNull
	@Column
	private String name;

	@NotNull
	@OneToOne
	private City city;

	@NotNull
	@Column
	private String address;

	@NotNull
	@OneToOne
	private TypeAccomodation typeAccomodation;

	@NotNull
	@OneToOne
	private Category category;

	@NotNull
	@Column
	private String description;

	@NotNull
	@Column
	private String pic;

	@ManyToMany
	private List<AdditionalServices> additionalServices;

	@NotNull
	@OneToOne
	private Agent agent;

	public Accomodation() {

	}

	public Accomodation(Long id, @NotNull String name, City city, @NotNull String address,
			TypeAccomodation typeAccomodation, Category category, String description, String pic,
			List<AdditionalServices> additionalServices, Agent agent) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.address = address;
		this.typeAccomodation = typeAccomodation;
		this.category = category;
		this.description = description;
		this.pic = pic;
		this.additionalServices = additionalServices;
		this.agent = agent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public TypeAccomodation getTypeAccomodation() {
		return typeAccomodation;
	}

	public void setTypeAccomodation(TypeAccomodation typeAccomodation) {
		this.typeAccomodation = typeAccomodation;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<AdditionalServices> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(List<AdditionalServices> additionalServices) {
		this.additionalServices = additionalServices;
	}


}
