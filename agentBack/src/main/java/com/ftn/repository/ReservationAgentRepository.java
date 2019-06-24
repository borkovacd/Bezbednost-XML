package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.ReservationAgent;
@Repository
public interface ReservationAgentRepository extends JpaRepository<ReservationAgent, Long> {

}
