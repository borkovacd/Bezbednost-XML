package com.ftn.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Accomodation;
import com.ftn.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	ArrayList<Comment> findAllByAccommodation(Accomodation acc);

}
