package com.ftn.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ftn.enums.ClientStatus;

@Entity(name="Clients")
@NamedEntityGraph(name = "Client.Roles.Permissions", 
attributeNodes = @NamedAttributeNode(value = "roles", subgraph = "permissions"), 
subgraphs = @NamedSubgraph(name = "permissions", attributeNodes = @NamedAttributeNode("permissions")))
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="FirstName", nullable=false)
	private String firstName;
	
	@Column(name="LastName", nullable=false)
	private String lastName;
	
	@Column(name="Username")
	private String username;
	
	@NotNull
	@Size(min=8, max = 99)
	@Column(name="Password")
	private String password;

	@NotNull
	@Column(name="Email", nullable=false)
	private String email;
	
	@Column(name="City", nullable=false)
	private String city;
	
	@Enumerated(EnumType.STRING)
	private ClientStatus status;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "clients_roles", joinColumns = @JoinColumn(name = "clients_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private Set<Role> roles;

	
	public Client() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public ClientStatus getStatus() {
		return status;
	}

	public void setStatus(ClientStatus status) {
		this.status = status;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	
	
	
}
