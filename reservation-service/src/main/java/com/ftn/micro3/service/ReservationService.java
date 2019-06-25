package com.ftn.micro3.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ftn.micro3.dto.ReservationDTO;
import com.ftn.micro3.model.Accomodation;
import com.ftn.micro3.model.Reservation;
import com.ftn.micro3.model.ReservationAgent;
import com.ftn.micro3.model.Room;
import com.ftn.micro3.model.User;
import com.ftn.micro3.repository.AccomodationRepository;
import com.ftn.micro3.repository.PriceRepository;
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
	PriceRepository priceRepository;
	
	@Autowired
	AccomodationRepository accomodationRepository ;
	
	@Autowired
	ReservationAgentRepository reservationAgentRepository ;
	
	
	public List<Reservation> findReservationsByUserId(Long id)
	{
		
		return reservationRepository.findByUserId(id);
	}
	
	public String getUser() {
		String tmp = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		return tmp;
	}
	
	
	public List<Reservation> getAll()
	{
		return reservationRepository.findAll();
	}
	
	public List<Room> advancedSearchFreeRooms(boolean tipHotel, boolean tipApartman, boolean tipBadAndBreakfast, boolean kategorija1, boolean kategorija2, boolean kategorija3, boolean kategorija4, boolean kategorija5, boolean nekategorisan, boolean parking, boolean wifi, boolean dorucak, boolean poluPansion, boolean pansion, boolean allInclusive, boolean petFriendly, boolean tv, boolean miniKuhinja, boolean kupatilo, boolean bespaltnoOtkazivanje, List<Room> rooms)
	{
		List<Room> allRooms = new ArrayList<Room>(rooms);
		List<Room> typeMatchingRooms =  new ArrayList<Room>();
		
		// TypeAccomodation
		for (Room r : allRooms)
		{
			
			if (r.getAccomodation().getTypeAccomodation().getName() == "hotel" && tipHotel == true)
			{
				typeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getTypeAccomodation().getName() == "apartman" && tipApartman == true)
			{
				typeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getTypeAccomodation().getName() == "bad&breakfast" && tipBadAndBreakfast == true)
			{
				typeMatchingRooms.add(r);
			}
			if (tipHotel == false && tipApartman == false && tipBadAndBreakfast == false)
			{
				typeMatchingRooms.add(r);
			}
		}
		
		List<Room> categoryTypeMatchingRooms = new ArrayList<Room>();
		
		// Category	
		for (Room r : typeMatchingRooms)
		{
			if (r.getAccomodation().getCategory().getName() == "jedna" && kategorija1 == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getCategory().getName() == "dve" && kategorija2 == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getCategory().getName() == "tri" && kategorija3 == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getCategory().getName() == "cetri" && kategorija4 == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getCategory().getName() == "pet" && kategorija5 == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getCategory().getName() == "nekategorisan" && nekategorisan == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (kategorija1 == false && kategorija2 == false && kategorija3 == false && kategorija4 == false && kategorija5 == false && nekategorisan == false)
			{
				categoryTypeMatchingRooms.add(r);
			}
		}
		
		List<Room> additionalCatTypeMatching = new ArrayList<Room>();
	
		return additionalCatTypeMatching ;
	
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
				if(r.isActive()==true) {
				matchingRoomsAccomodation.add(r);
				}
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
	
	public boolean cancelAccepted(Long id) throws ParseException
	{
		Reservation res = reservationRepository.findOneById(id);
		
		// Danasnji datum
		Date today = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
        String strToday = dateFormat.format(today);  // danasnji datum je oblika: 2019-46-25
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
        // PARSIRAN
		Date parsedDateToday = formatter.parse(strToday);
		
		String europeanDatePattern = "yyyy-MM-dd";
		DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
		
		LocalDate dateTodayLocal = LocalDate.parse(strToday, europeanDateFormatter);
       
		
        // Datum polaska
        LocalDate startDateLocal = res.getFromDate();
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-mm-dd");  
        String strStart = dateFormat2.format(startDateLocal); 
        
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        
        // PARSIRAN
        Date parsedDateStart = formatter.parse(strStart);
        
        // nastavak
        
        Month a = dateTodayLocal.getMonth();
        System.out.println("Ovo je mesec " + a.getValue());
        
        long todayDateTime = parsedDateToday.getTime();
	    long startDateTime = parsedDateStart.getTime();
	    long milPerDay = 1000*60*60*24; 
	    
	    int numOfDays = (int) ((startDateTime - todayDateTime) / milPerDay); // 
	    System.out.println("Dana odabranih: " + numOfDays);
	    
	    if (res.getRoom().getDay() >= numOfDays)
	    {
	    	return true ;
	    }
	    else
	    {
	    	return false ;
	    }
       
	}
	

	public void deleteReservation(Long id)
	{
		reservationRepository.deleteById(id);
	}
	
	public void cancelReservation(long idReservation) 
	{
		reservationRepository.deleteById(idReservation);
	}



}
