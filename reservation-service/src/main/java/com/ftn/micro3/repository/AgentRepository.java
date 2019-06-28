package com.ftn.micro3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.micro3.model.Agent;


@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> 
{
	Agent findOneById(Long id);
	Agent findOneByUsername(String username);
}
