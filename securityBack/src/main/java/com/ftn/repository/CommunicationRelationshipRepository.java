package com.ftn.repository;

import com.ftn.model.CommunicationRelationship;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationRelationshipRepository extends JpaRepository<CommunicationRelationship, Long>{

    CommunicationRelationship save(CommunicationRelationship communicationRelationship);
	
	List<CommunicationRelationship> findAll();
}
