package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.model.ReservationAgent;

public interface ReservationAgentRepository extends JpaRepository<ReservationAgent, Long> {

}
