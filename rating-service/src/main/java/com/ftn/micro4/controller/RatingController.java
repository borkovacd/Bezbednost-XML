package com.ftn.micro4.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.micro4.repository.ReservationRepository;
import com.ftn.micro4.repository.UserRepository;
import com.ftn.micro4.security.TokenUtils;
import com.ftn.micro4.service.RatingService;
import com.ftn.micro4.service.UserService;
import com.ftn.micro4.dto.RatingDTO;
import com.ftn.micro4.model.Rating;
import com.ftn.micro4.model.User;

@RestController
@RequestMapping(value = "/api/rating")
public class RatingController 
{	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ReservationRepository reservationRepository ;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private RatingService ratingService ;
	
	// KLIK OCENI SMESTAJ - dobija idRezervacije koju je kliknuo, idSobe
	// `${this.BASE_URL}/checkIfComment/${idRoom}`
	@GetMapping("/checkIfComment/{token}/{idRoom}")
	public boolean checkIfComment(@PathVariable String token, @PathVariable Long idRoom) throws Exception 
	{	
		token = token.substring(1, token.length()-1);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User user = userService.findByEmail(email);
		
		//metoda treba da vraca true kada korisnik ima neku prethodno kreiranu rezervaciju
		boolean canComment = ratingService.canComment(idRoom, user.getId());
				
		return canComment;
	}
	
	// CONFIRM CLICK - DTO i idRoom
	// oceniAcc(id: any, idRoom: any)
	// `${this.BASE_URL}/createRating/${token}/${idRoom}`
	@PostMapping("/createRating/{token}/{idRoom}")
	public void createRating(@RequestBody RatingDTO ratingDTO, @PathVariable String token, @PathVariable Long idRoom) throws Exception 
	{
		token = token.substring(1, token.length()-1);
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		User u = userService.findByEmail(email);
	
		ratingService.createRating(ratingDTO, u, idRoom);		
	}
	
	// OCENE SMESTAJA - RoomRating (VRACA - prosecna ocena, lista ocena i komentara)
	// getAverageRating(idRoom)
	// `${this.BASE_URL}/getAverageRating/${idRoom}`
	@GetMapping("/getAverageRating/{idRoom}")
	public double getAverageRating(@PathVariable Long idRoom)
	{
		double rezultat = ratingService.getAverageRating(idRoom);
		return rezultat ;
	}
	
	// getListOfRating(Lista svih rejtinga, komentara i ocena)
	// getListOfRating(idRoom)
	// (`${this.BASE_URL}/getListOfRating/${idRoom}`
	@GetMapping("/getListOfRating/{idRoom}")
	public ResponseEntity<List<Rating>> getListOfRating (@PathVariable Long idRoom)
	{
		List<Rating> ratings = new ArrayList<Rating>();
		ratings = ratingService.getListOfRating(idRoom);
		
		if (ratings == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<List<Rating>>(ratings, HttpStatus.OK);
		}
		
	}
	// ********* ADMIN ************
	
	@GetMapping("/getAllRatings")
	public ResponseEntity<List<Rating>> getAllRatings()
	{
		List<Rating> ratings = new ArrayList<Rating>();
		ratings = ratingService.getAllRatings();
			
		if (ratings.size() == 0)
		{
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<List<Rating>>(ratings, HttpStatus.OK);
		}
	}
	
	@PostMapping("/confirmRating/{idRating}")
	public void confirmRating(@PathVariable Long idRating) 
	{
		ratingService.confirmRating(idRating);

	}
	
	

}
