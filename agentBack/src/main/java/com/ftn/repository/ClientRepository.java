package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	Client findByUsername(String username);
}
