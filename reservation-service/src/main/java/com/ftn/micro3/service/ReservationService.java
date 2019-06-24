package com.ftn.micro3.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.micro3.model.Accomodation;
import com.ftn.micro3.model.Reservation;
import com.ftn.micro3.model.ReservationAgent;
import com.ftn.micro3.model.Room;
import com.ftn.micro3.model.User;
import com.ftn.micro3.repository.AccomodationRepository;
import com.ftn.micro3.repository.ReservationAgentRepository;
import com.ftn.micro3.repository.ReservationRepository;
import com.ftn.micro3.repository.RoomRepository;

@Service
public class ReservationService 
{
	@Autowired
	ReservationRepository reservationRepository ;
	
	@Autowired
	RoomRepository roomRepository ;
	
	@Autowired
	AccomodationRepository accomodationRepository ;
	
	@Autowired
	ReservationAgentRepository reservationAgentRepository ;
	
	public List<Reservation> findReservationsByUserId(Long id)
	{
		
		return reservationRepository.findByUserId(id);
	}
	
	public List<Reservation> getAll()
	{
		return reservationRepository.findAll();
	}
	
	public List<Room> searchFreeRooms(String city, String fromDate, String toDate, int numberOfPersons)
	{
		List<Room> allRooms = roomRepository.findAll();
		List <Room> matchingRoomsAccomodation = new ArrayList<Room>() ;
		List<Room> matchingRooms = new ArrayList<Room>();

		
		System.out.println("city je " + city);
		String europeanDatePattern = "yyyy-MM-dd";
		DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
		LocalDate fromDateConverted = LocalDate.parse(fromDate, europeanDateFormatter);
		LocalDate toDateConverted = LocalDate.parse(toDate, europeanDateFormatter);
		
		
		// lista postojecih rezervacija
		List<Reservation> reservations = reservationRepository.findAll();
		List<ReservationAgent> reservationsAgent = reservationAgentRepository.findAll();
		
		System.out.println("Soba ima: " + allRooms.size());
		
		for (Room r : allRooms)
		{
			System.out.println("usao u for");
			if (r.getAccomodation().getCity().getName().equals(city))
			{
				System.out.println("nasao da su jednaki");
				matchingRoomsAccomodation.add(r);
			}
		}
		
		List<Room> matchingRoomsAccomodationCopy = new ArrayList<Room>(matchingRoomsAccomodation);
		
		for (Room r : matchingRoomsAccomodation)
		{
			if (r.getCapacity() != numberOfPersons)
			{
				matchingRoomsAccomodationCopy.remove(r);
			}
		}
		
		for (Reservation res : reservations) // prolazak kroz sve rezervacije
		{
			if (matchingRoomsAccomodationCopy.contains(res.getRoom())) // ukoliko se soba iz rezervacije nalazi medju odgovarajucim sobama po smestaju
			{
				// ukoliko su pocetni datumi jednaki, ili je prosledjen pocetni veci od onog na rezervaciji
				// ukoliko je pocetni datum rezervacije pre krajnjeg datuma rezervacije
				
				// ukoliko je pocetni datum pre kraja rezervacije
				// ukoliko je krajnji datum pre kraja rezervacije
				if((res.getFromDate().compareTo(fromDateConverted) >= 0 && res.getFromDate().compareTo(toDateConverted) <= 0 && res.isConfirmed() == true) || (res.getToDate().compareTo(fromDateConverted) >= 0 && res.getToDate().compareTo(toDateConverted) <= 0 && res.isConfirmed() == false)) 
				{
					matchingRoomsAccomodationCopy.remove(res.getRoom());
				}
			}		
			
		}
		
		List<Room> matchingRoomsAccomodationFinal = new ArrayList<Room>(matchingRoomsAccomodationCopy);

		for (ReservationAgent resAgent : reservationsAgent) // prolazak kroz sve rezervacije agenta
		{
			if (matchingRoomsAccomodationFinal.contains(resAgent.getRoom())) // ukoliko se soba iz agentove rezervacije nalazi medju odgovarajucim sobama po smestaju
			{
				// ukoliko su pocetni datumi jednaki, ili je prosledjen pocetni veci od onog na rezervaciji
				// ukoliko je pocetni datum rezervacije pre krajnjeg datuma rezervacije
				
				// ukoliko je pocetni datum pre kraja rezervacije
				// ukoliko je krajnji datum pre kraja rezervacije
				if((resAgent.getFromDate().compareTo(fromDateConverted) >= 0 && resAgent.getFromDate().compareTo(toDateConverted) <= 0) || (resAgent.getToDate().compareTo(fromDateConverted) >= 0 && resAgent.getToDate().compareTo(toDateConverted) <= 0)) 
				{
					matchingRoomsAccomodationFinal.remove(resAgent.getRoom());
				}
			}		
			
		}
		
		return matchingRoomsAccomodationFinal;
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
