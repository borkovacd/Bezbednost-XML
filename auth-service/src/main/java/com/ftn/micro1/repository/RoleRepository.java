package com.ftn.micro1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.micro1.enums.NameRole;
import com.ftn.micro1.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(NameRole name);

}
