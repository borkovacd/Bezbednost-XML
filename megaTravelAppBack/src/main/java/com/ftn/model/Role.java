package com.ftn.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role {
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	@ManyToMany()
	private Set<Privilege> privilege = new HashSet<Privilege>();
	public void Role(){
		
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

	public Set<Privilege> getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Set<Privilege> privilege) {
		this.privilege = privilege;
	}

	
	

}
