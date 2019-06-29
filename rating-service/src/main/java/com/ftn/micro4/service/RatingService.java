package com.ftn.micro4.service;

import java.time.LocalDate;
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
	public boolean canComment(Long idReservation)
	{
		Reservation reservation = reservationRepository.findOneById(idReservation);
		
		if (reservation == null) // ukoliko nije pronasao nijednu rezervaciju za prosledjen idRezervacije
		{
			return false ;
		}
		else // postoji makar jedna rezervacija u listi rezervacija sa tim id-jem
		{

			if (reservation.getToDate().isBefore(LocalDate.now())) // ukoliko je krajnji datum rezervacije pre danasnjeg datuma
			{
				return true ;
			}
			else
			{
				return false ;
			}
			
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
	
}
