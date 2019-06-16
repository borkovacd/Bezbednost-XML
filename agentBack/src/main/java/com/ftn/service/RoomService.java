package com.ftn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.repository.RoomRepository;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;
}
