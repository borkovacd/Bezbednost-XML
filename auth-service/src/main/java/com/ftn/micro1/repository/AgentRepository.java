package com.ftn.micro1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.micro1.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

}
