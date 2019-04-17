package com.ftn.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name="Name")
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
    private Collection<Privilege> privileges;
	
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

	public Collection<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivilege(Collection<Privilege> privileges) {
		this.privileges = privileges;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}

	
	

}
