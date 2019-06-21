package com.ftn.micro1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.micro1.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
