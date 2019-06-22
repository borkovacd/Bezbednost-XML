package com.ftn.micro3.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.micro3.model.Accomodation;
import com.ftn.micro3.model.Reservation;
import com.ftn.micro3.model.Room;
import com.ftn.micro3.model.User;
import com.ftn.micro3.repository.ReservationRepository;
import com.ftn.micro3.repository.RoomRepository;

@Service
public class ReservationService 
{
	@Autowired
	ReservationRepository reservationRepository ;
	
	@Autowired
	RoomRepository roomRepository ;
	
	public List<Reservation> findReservationByUserId(Long id)
	{
		
		return reservationRepository.findByUser(id);
	}
	
	public List<Reservation> getAll()
	{
		return reservationRepository.findAll();
	}
	
	public List<Room> getAvailableRooms(Accomodation accomodation, LocalDate fromDate, LocalDate toDate)
	{
		List<Room> allRooms = roomRepository.findAll();
		List <Room> matchingRoomsAccomodation = new ArrayList<Room>() ;
		List<Room> matchingRooms = new ArrayList<Room>();
		
		// lista postojecih rezervacija
		List<Reservation> reservations = reservationRepository.findAll();
		
		for (Room r : allRooms)
		{
			if (r.getAccomodation().getId().equals(accomodation.getId()))
			{
				matchingRoomsAccomodation.add(r);
			}
		}
		
		for (Reservation res : reservations) // prolazak kroz sve rezervacije
		{
			if (matchingRoomsAccomodation.contains(res.getRoom())) // ukoliko se soba iz rezervacije nalazi medju odgovarajucim sobama po smestaju
			{
				// ukoliko su pocetni datumi jednaki, ili je prosledjen pocetni veci od onog na rezervaciji
				// ukoliko je pocetni datum rezervacije pre krajnjeg datuma rezervacije
				
				// ukoliko je pocetni datum pre kraja rezervacije
				// ukoliko je krajnji datum pre kraja rezervacije
				if((res.getFromDate().compareTo(fromDate) >= 0 && res.getFromDate().compareTo(toDate) <= 0) || (res.getToDate().compareTo(fromDate) >= 0 && res.getToDate().compareTo(toDate) <= 0)) 
				{
					matchingRoomsAccomodation.remove(res.getRoom());
				}
			}
			
		
		}
		
		return matchingRoomsAccomodation;
	}
	
	public Room getOneRoom(Long id)
	{
		return roomRepository.findOneById(id);
	}
	
	public Reservation createReservation(Reservation reservation)
	{
		return reservationRepository.save(reservation);
	}
	

	public void deleteReservation(Long id)
	{
		reservationRepository.deleteById(id);
	}



}
