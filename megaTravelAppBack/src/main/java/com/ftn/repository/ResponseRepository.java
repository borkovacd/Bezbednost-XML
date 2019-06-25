package com.ftn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> 
{
	List<Response> findBySenderId(Long id);
	List<Response> findByRecipientId(Long id);
}
