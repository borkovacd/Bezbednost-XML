package com.ftn.micro4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.micro4.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> 
{
	List<Rating> findByRoomId(Long id) ; // vrati listu ratinga za tu sobu
	List<Rating> findByUserId(Long id) ; // vrati listu ratinga tog user-a
}
