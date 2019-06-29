package com.ftn.micro4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.micro4.model.User;



public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	User findOneByUsername(String username);

}
