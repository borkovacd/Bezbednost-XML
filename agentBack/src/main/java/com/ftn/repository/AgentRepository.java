package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

}
