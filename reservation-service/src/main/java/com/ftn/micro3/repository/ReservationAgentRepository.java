package com.ftn.micro3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.micro3.model.ReservationAgent;

@Repository
public interface ReservationAgentRepository extends JpaRepository<ReservationAgent, Long>
{

}
