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
import com.ftn.micro3.model.AdditionalServices;
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
		List<Room> allRooms = rooms;
		
		List<Room> typeMatchingRooms =  new ArrayList<Room>();
		
		// TypeAccomodation
		for (Room r : allRooms)
		{
			
			if (r.getAccomodation().getTypeAccomodation().getName().equals("hotel") && tipHotel == true)
			{
				typeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getTypeAccomodation().getName().equals("apartman") && tipApartman == true)
			{
				typeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getTypeAccomodation().getName().equals("bed&breakfast") && tipBadAndBreakfast == true)
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
			if (r.getAccomodation().getCategory().getName().equals("jedna") && kategorija1 == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getCategory().getName().equals("dve") && kategorija2 == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getCategory().getName().equals("tri") && kategorija3 == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getCategory().getName().equals("cetri") && kategorija4 == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getCategory().getName().equals("pet") && kategorija5 == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (r.getAccomodation().getCategory().getName().equals("nekategorisan") && nekategorisan == true)
			{
				categoryTypeMatchingRooms.add(r);
			}
			if (kategorija1 == false && kategorija2 == false && kategorija3 == false && kategorija4 == false && kategorija5 == false && nekategorisan == false)
			{
				categoryTypeMatchingRooms.add(r);
			}
		}
		
		
		List<Room> additionalCatTypeMatching = new ArrayList<Room>();
		
		for (Room r : categoryTypeMatchingRooms)
		{
			for (AdditionalServices ser : r.getAccomodation().getAdditionalServices())
			{
				if (ser.getName().equals("Parking") && parking == true)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
				
				if (ser.getName().equals("Wifi") && wifi == true && parking == false)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
				
				if (ser.getName().equals("Dorucak") && dorucak == true && wifi == false && parking == false)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
				
				if (ser.getName().equals("Polupansion") && poluPansion == true && wifi == false && parking == false && dorucak == false)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
				
				if (ser.getName().equals("Pansion") && pansion == true && poluPansion == false && wifi == false && parking == false && dorucak == false)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
				
				if (ser.getName().equals("All inclusive") && allInclusive == true && pansion == false && poluPansion == false && wifi == false && parking == false && dorucak == false)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
				
				if (ser.getName().equals("Kucni ljubimci") &&  petFriendly == true && allInclusive == false && pansion == false && poluPansion == false && wifi == false && parking == false && dorucak == false)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
				
				if (ser.getName().equals("Tv") && tv == true &&  petFriendly == false && allInclusive == false && pansion == false && poluPansion == false && wifi == false && parking == false && dorucak == false)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
				
				if (ser.getName().equals("Minikuhinja/kuhinja") && miniKuhinja == true && tv == false &&  petFriendly == false && allInclusive == false && pansion == false && poluPansion == false && wifi == false && parking == false && dorucak == false)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
				
				if (ser.getName().equals("Privatno kupatilo") && kupatilo == true && miniKuhinja == false && tv == false &&  petFriendly == false && allInclusive == false && pansion == false && poluPansion == false && wifi == false && parking == false && dorucak == false)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
				
				if (ser.getName().equals("Otkazivanje") && bespaltnoOtkazivanje == true  && kupatilo == false && miniKuhinja == false && tv == false &&  petFriendly == false && allInclusive == false && pansion == false && poluPansion == false && wifi == false && parking == false && dorucak == false)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
				
				if (parking == false && wifi == false && dorucak == false && poluPansion == false && pansion == false && allInclusive == false && petFriendly == false && tv == false && miniKuhinja == false && kupatilo == false && bespaltnoOtkazivanje == false)
				{
					if(!additionalCatTypeMatching.contains(r))
						additionalCatTypeMatching.add(r);
				}
			}
		}
	
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
		
		
		if (fromDateConverted.isBefore(LocalDate.now()) || toDateConverted.isBefore(LocalDate.now()))
		{
			return matchingRooms ;
		}
		
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
	
	public String cancelAccepted(Long id) throws ParseException
	{
		Reservation res = reservationRepository.findOneById(id);
		
		LocalDate now = LocalDate.now() ;
		LocalDate startOfReservation = res.getFromDate();
		
		System.out.println("Now: " + now);
		System.out.println("startOfReservation: " + startOfReservation);
		
		
		LocalDate nowPlusDays = now.plusDays(res.getRoom().getDay());
		System.out.println("nowPlusDays: " + nowPlusDays);
		
		if (now.isAfter(startOfReservation))
		{
			return "NO" ;
		}
		
		else if (startOfReservation.isBefore(nowPlusDays))
		{
			return "NO" ;
		}
		
		else 
		{
			return "OK" ;
		}
	}
	

	public void deleteReservation(Long id)
	{
		reservationRepository.deleteById(id);
	}
	
	public void cancelReservation(Long idReservation) 
	{
		reservationRepository.deleteById(idReservation);
	}


	
	// Metode za validaciju
	
	public boolean checkStringField(String field) 
	{
		if(field.isEmpty()) // prazno polje
		{
			return false;
		}
		if(field.contains(";")) // tacka-zarez
		{
			return false;
		}
		
		if(field.contains(",")) // zarez
		{
			return false;
		}
		
		for(Character c : field.toCharArray()) // razmaci
		{
			if(Character.isWhitespace(c)) 
			{
				return false;
			
			}
				
		}
		
		return true;
	}
	
	// sme samo slovo ili broj
	public boolean checkCharacters(String data) 
	{
		if(data.isEmpty()) 
		{
			return false;
		}
		for(Character c :data.toCharArray()) // ukoliko je razmak ili ukoliko nije slovo ili broj
		{
			if(Character.isWhitespace(c)== false && Character.isLetterOrDigit(c) == false) 
			{
				return false;
			}
		}
		
		return true;
	}
	
	// provera za brojeve
	public boolean checkNumberFields(String data) 
	{
		if(data.isEmpty()) 
		{
			return false;
		}
		for(Character c : data.toCharArray()) // ukoliko je razmak ili ukoliko broj
		{
			if(Character.isWhitespace(c)== false && Character.isDigit(c) == false) 
			{
				return false;
			}
		}
		
		return true;
	}
	
	public List<Room> sortAllRooms(List<Room> rooms)
	{
		//List<Room> rooms = roomRepository.findAll();
		ArrayList<String> roomNames = new ArrayList<>();
		
		for (Room r : rooms)
		{
			roomNames.add(r.getAccomodation().getCategory().getName());
		}
		
		Room temp = new Room();
			for (int x = 0 ; x < rooms.size(); x++)
			{
				for (int i = 0; i < rooms.size() - x - 1; i++)
				{
					if (rooms.get(i).getAccomodation().getCategory().getName().compareTo(rooms.get(i+1).getAccomodation().getCategory().getName()) > 0)
					{
						temp = rooms.get(i);
						rooms.set(i, rooms.get(i + 1));
						rooms.set(i+1, temp);
						
					}
				}
			}
		/*
		Collections.sort(rooms, roomNames);
		*/
		
		return rooms ;
	}

}
