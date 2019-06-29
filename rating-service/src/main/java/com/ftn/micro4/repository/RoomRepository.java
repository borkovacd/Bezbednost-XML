package com.ftn.micro4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.micro4.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{

	Room findOneById(Long id);
}

