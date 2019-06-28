package com.ftn.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.ftn.security.SQLInjectionSafe;

public class AccomodationDTO {
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String name;

	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String city;

	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
    String address;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String type;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String category;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String description;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String pic;

	
	private ArrayList<String> list;

	public AccomodationDTO() {

	}

	

	public String getPic() {
		return pic;
	}



	public void setPic(String pic) {
		this.pic = pic;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}
	

}
