package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.enums.NameRole;
import com.ftn.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(NameRole name);

}
