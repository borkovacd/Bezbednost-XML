package com.ftn.micro3.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro3.config.TokenUtils;
import com.ftn.micro3.dto.BasicSearchDTO;
import com.ftn.micro3.dto.ReservationDTO;
import com.ftn.micro3.model.Price;
import com.ftn.micro3.model.Reservation;
import com.ftn.micro3.model.Room;
import com.ftn.micro3.model.User;
import com.ftn.micro3.repository.AgentRepository;
import com.ftn.micro3.repository.PriceRepository;
import com.ftn.micro3.repository.ReservationRepository;
import com.ftn.micro3.repository.RoomRepository;
import com.ftn.micro3.repository.UserRepository;
import com.ftn.micro3.service.ReservationService;

@RestController
@RequestMapping(value="api/reservations")
public class ReservationController 
{
	@Autowired
	ReservationService reservationService ;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	RoomRepository roomRepository ;
	
	@Autowired
	UserRepository userRepository ;
	
	@Autowired
	AgentRepository agentRepository ;
	
	@Autowired
	PriceRepository priceRepository ;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@GetMapping(value="/getRoomById/{id}")
	public ResponseEntity<Room> getRoomById(ServletRequest request, @PathVariable Long id) throws Exception
	{
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");

		token = token.substring(1, token.length()-1);
		User u = userRepository.findOneByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		
		
		Room oneRoom = reservationService.getOneRoom(id);
		
		if (oneRoom == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<Room>(oneRoom, HttpStatus.OK);
		}
	}
	
	// findOneByName
	
	@PostMapping(value="/searchFreeRooms")
	public ResponseEntity<List<Room>> searchFreeRooms(/*ServletRequest request,*/@RequestBody BasicSearchDTO dto) throws Exception {
		/*
		 * 
		 * Za pretragu ne mora biti ulogovan, pa je zakomentarisano
		 * 
		 * 
		 * 
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");
		
		token = token.substring(1, token.length()-1);
		User u = userRepository.findOneByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		*/
		
		List<Room> rooms = reservationService.searchFreeRooms(dto.getCity(), dto.getFromDate(), dto.getToDate(), dto.getNumberOfPersons());
		
		boolean checkCity = reservationService.checkCharacters(dto.getCity());
		
		// ukoliko je uneo grad koji sadrzi nesto sem slova i brojeva
		if (checkCity == false)
		{
			return new ResponseEntity<List<Room>>(HttpStatus.NO_CONTENT);
		}
		
		if(rooms != null) 
		{
			return new ResponseEntity<List<Room>>(rooms, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasAuthority('RESERVE')")
	@PostMapping(value="/createReservation")
	public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO res) throws Exception
	
	{
		
		System.out.println(res.getToken());
		
		String token = res.getToken().substring(1, res.getToken().length()-1);
		
		String username = tokenUtils.getUserSecurity(token).getUsername();
		
		User us = userRepository.findOneByEmail(username);
		
		System.out.println("Ovo je user: " + us.getUsername());
		
		Reservation newReservation = new Reservation();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Date parsedDate1 = formatter.parse(res.getFromDate());
		Date parsedDate2 = formatter.parse(res.getToDate());
		
		
		String europeanDatePattern = "yyyy-MM-dd";
		DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
		LocalDate fromDateConverted = LocalDate.parse(res.getFromDate(), europeanDateFormatter);
		LocalDate toDateConverted = LocalDate.parse(res.getToDate(), europeanDateFormatter);
		
		Month a = fromDateConverted.getMonth();
		
		System.out.println("Ovo je mesec " + a.getValue());
		
	//	Date date1 = fromDateConverted.get;
	//	Date toDateConv = Date.parse(res.getToDate(), europeanDateFormatter);
		
		long startDateTime = parsedDate1.getTime();
	    long endDateTime = parsedDate2.getTime();
	    long milPerDay = 1000*60*60*24; 

	    int numOfDays = (int) ((endDateTime - startDateTime) / milPerDay); // 
		System.out.println("Dana odabranih: " + numOfDays);
		
		Room r = roomRepository.findOneById(res.getIdRoom());
		
		ArrayList<Price> pr = priceRepository.findByRoom(r);

		double price = 0;
		for(Price p: pr) {
			if (Integer.parseInt(p.getMonth()) == a.getValue()) {
				price = p.getPrice();
				break;
			}
		}
		
		System.out.println("Ukupna cena: " + price*numOfDays);
		
		if (res != null)
		{
			newReservation.setAgent(agentRepository.findOneById(res.getIdAgent()));
			newReservation.setConfirmed(false);
			newReservation.setFromDate(fromDateConverted); 
			newReservation.setToDate(toDateConverted); 
			newReservation.setId(res.getId());
			newReservation.setUser(userRepository.findOneByEmail(username)); 
			newReservation.setRoom(r); 
			newReservation.setPrice(price*numOfDays);
		 
			
			Reservation newRes = reservationService.createReservation(newReservation);
			
			return new ResponseEntity<Reservation>(newRes, HttpStatus.OK);
			
			
		}
		
		
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PreAuthorize("hasAuthority('RESERVE')")
	@GetMapping(value="/getReservationsByUser/{token}")
	public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable String token) throws Exception 
	{
		
		System.out.println("taj token: " + token);
		token = token.substring(1, token.length()-1);
		
		String username = tokenUtils.getUserSecurity(token).getUsername();
		
		System.out.println("Username je: " + username);
		
		User us = userRepository.findOneByEmail(username);
		
		List <Reservation> reservations = reservationRepository.findAll();
		
		List <Reservation> ress = new ArrayList<Reservation>();
		
		System.out.println("Ukupno ima res: " + reservations.size());
		
		for(Reservation r: reservations) {
			
			if(r.getUser() == null) {
				System.out.println("User je null");
			}

			System.out.println(r.getUser().getId());
			System.out.println(us.getId());
			
			if( (long) r.getUser().getId() == (long) us.getId()) {
				
				if(r.isConfirmed()==true) {
						ress.add(r);
				}
			}
		}
		
		return new ResponseEntity<List<Reservation>>(ress, HttpStatus.OK);
	}
	
	/*
	@PreAuthorize("hasAuthority('DEL_RES')")
	@DeleteMapping(value="/deleteReservation/{id}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long id)
	{
		if (id != null) {
			reservationService.deleteReservation(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	*/

	@PreAuthorize("hasAuthority('DEL_RES')")
	@GetMapping(value="/cancelRes/{id}")
	public boolean cancelRes(ServletRequest request, @PathVariable Long id) throws Exception
	{	

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String token = httpRequest.getHeader("token");
		
		token = token.substring(1, token.length()-1);
		User u = userRepository.findOneByEmail(tokenUtils.getUserSecurity(token).getUsername());
		System.out.println("Ovo je user " + u.getUsername());
		
		
		String a = reservationService.cancelAccepted(id);
		
		if (a.equals("OK"))
		{
			reservationService.cancelReservation(id);
			return true;
		}
		else // a je NO
		{
			return false;
		}
		
		
		/*
		if (id != null) {
			reservationService.cancelReservation(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		*/
	}
}
