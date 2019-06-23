package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Agent;
import com.ftn.model.User;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
	Agent findOneByUsername(String username);
	
	//Agent findByEmail(String email);
}
