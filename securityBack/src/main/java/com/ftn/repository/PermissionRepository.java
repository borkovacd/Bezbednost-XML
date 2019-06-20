package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
