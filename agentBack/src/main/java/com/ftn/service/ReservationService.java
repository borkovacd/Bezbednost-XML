package com.ftn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.repository.ReservationRepository;


@Service
public class ReservationService {
	@Autowired
	private ReservationRepository reservationRepository;
}
