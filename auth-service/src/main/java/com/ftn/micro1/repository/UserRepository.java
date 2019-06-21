package com.ftn.micro1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.micro1.model.User;



public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
