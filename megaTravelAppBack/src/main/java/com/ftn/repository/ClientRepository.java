package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	Client findByUsername(String username);

}
