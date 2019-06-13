package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{

	Room findOneById(Long id);
}

