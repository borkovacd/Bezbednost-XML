package com.ftn.modelDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.ftn.security.SQLInjectionSafe;

public class UserDTO {

	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String username;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String password;
	
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String rePassword;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String firstName;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String lastName;
	
	@NotNull
	@SafeHtml
	@NotBlank
	private @SQLInjectionSafe
	String email;
	
	private String city;
	

	public UserDTO() {
		
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
	
	

}
