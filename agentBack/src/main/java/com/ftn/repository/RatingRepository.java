package com.ftn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{

	List<Rating> findByRoomId(Long idRoom); 

}
