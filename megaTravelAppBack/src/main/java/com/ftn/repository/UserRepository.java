package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.model.User;



public interface UserRepository extends JpaRepository<User, Long> {

}
