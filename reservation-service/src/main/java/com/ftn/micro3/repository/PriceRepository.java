package com.ftn.micro3.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.micro3.model.Price;
import com.ftn.micro3.model.Room;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

	ArrayList<Price> findByRoom(Room r);

}
