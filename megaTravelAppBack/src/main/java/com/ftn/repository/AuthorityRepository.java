package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.*;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String>{

	Authority findOneByName(String string);

}
