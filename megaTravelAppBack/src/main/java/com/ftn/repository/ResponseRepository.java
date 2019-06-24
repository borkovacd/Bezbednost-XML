package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

}
