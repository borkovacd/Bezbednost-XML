package com.ftn.micro4.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.micro4.repository.RatingRepository;
import com.ftn.micro4.repository.ReservationRepository;
import com.ftn.micro4.repository.RoomRepository;
import com.ftn.micro4.repository.UserRepository;
import com.ftn.micro4.model.User;
import com.ftn.micro4.dto.RatingDTO;
import com.ftn.micro4.model.Rating;
/*
import com.ftn.service.UserService;
*/
import com.ftn.micro4.model.Reservation;
import com.ftn.micro4.model.Room;

@Service
public class RatingService 
{
	
	@Autowired
	private UserRepository userRepository;
	
	/*
	@Autowired
	private UserService userService;
	*/
	
	@Autowired
	private ReservationRepository reservationRepository ;
	
	@Autowired
	private RatingRepository ratingRepository ;
	
	@Autowired
	private RoomRepository roomRepository ;
	
	// Korisnik moze da salje poruku agentu jedino ukoliko vec ima kreiranu rezervaciju
	public boolean canComment(Long idRoom, Long idUser)
	{
		System.out.println("Room Id : " + idRoom);
		List<Reservation> reservations = reservationRepository.findByRoomId(idRoom);
		List<Reservation> myReservations = new ArrayList<Reservation>();
		
		if (reservations.size() == 0)
		{
			return false ;
		}
		
		for (Reservation res : reservations)
		{
			if ((res.getUser().getId()) == idUser)
			{
				myReservations.add(res);
			}
		}
		
		if (myReservations.size() == 0) // ukoliko nije pronasao nijednu rezervaciju za prosledjen idRoom, kod datog korisnika
		{
			System.out.println("NEMA REZERVACIJA ");
			return false ;
		}
		else // postoji makar jedna soba u listi rezervacija sa tim id-jem, za tog User-a
		{
			
			for (Reservation r : myReservations)
			{
				System.out.println("DATUM DATUM");
				if (r.getToDate().isBefore(LocalDate.now())) // krajnji datum rezervacije je pre danasnjeg datuma
				{	
					return true ;
				}
			}
			
			return false ;
			
		}
	}
	
	public void createRating(RatingDTO ratingDTO, User user, Long idRoom) throws Exception
	{
		
		Room room = roomRepository.findOneById(idRoom);
		
		Rating rating = new Rating();
		
		int ratingMark = Integer.parseInt(ratingDTO.getRatingMark());

		rating.setApproved(false);
		rating.setComment(ratingDTO.getComment());
		rating.setRatingMark(ratingMark);
		rating.setUser(user);
		rating.setRoom(room);
		
		ratingRepository.save(rating);
		
	}
	
	public double getAverageRating(Long idRoom)
	{
		double average = 0;
		double ukupno = 0;
		int brojac = 0;
		
		List<Rating> ratings = new ArrayList<Rating>();
		ratings = ratingRepository.findByRoomId(idRoom); // vrati sve rejtinge te sobe
		
		if (ratings.size() == 0)
		{
			System.out.println("RATINGS JE NULL");
			return average;
		}
		else
		{
			for (Rating r : ratings)
			{
				brojac += 1 ;
				ukupno += r.getRatingMark();
			}
			
			average = ukupno / brojac ;
			
			return average ;
		}
		
	}
	
	public List<Rating> getListOfRating(Long idRoom)
	{
		List<Rating> ratings = ratingRepository.findByRoomId(idRoom);
		List<Rating> goodRatings = new ArrayList<Rating>();
		
		for (Rating r : ratings)
		{
			if (r.isApproved() == true)
			{
				goodRatings.add(r);
			}
		}
		return goodRatings ;
	}
	
	// ****************** ADMIN ************************ 
	
	// vraca listu svih ratinga
	public List<Rating> getAllRatings()
	{
			
		List<Rating> allRatings = ratingRepository.findAll();
		List<Rating> goodRatings = new ArrayList<Rating>() ;
			
		for (Rating r : allRatings)
		{
			if (r.isApproved() == false)
			{
				goodRatings.add(r);
			}
		}
			
		return goodRatings ;
	}
	
	// potvrdjuje rating
	public void confirmRating(Long idRating)
	{
		Rating rating = ratingRepository.findOneById(idRating) ;
		rating.setApproved(true);
		
		ratingRepository.save(rating) ;
	}
}
