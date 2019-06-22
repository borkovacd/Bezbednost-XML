package com.ftn.micro3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.micro3.model.Agent;
import com.ftn.micro3.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	User findOneById(Long id);
}
