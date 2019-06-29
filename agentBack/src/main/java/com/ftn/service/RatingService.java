package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.City;
import com.ftn.model.Country;
import com.ftn.model.Rating;
import com.ftn.model.Room;
import com.ftn.model.User;
import com.ftn.repository.RatingRepository;
import com.ftn.repository.RoomRepository;
import com.ftn.repository.UserRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.GetAllCitiesRequest;
import com.ftn.webservice.files.GetAllCitiesResponse;
import com.ftn.webservice.files.GetAllRatingsRequest;
import com.ftn.webservice.files.GetAllRatingsResponse;

@Service
public class RatingService {
	
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private UserRepository userRespository;
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	public ArrayList<Rating> getAllRatings() {
		
		GetAllRatingsRequest request = new GetAllRatingsRequest();
		request.setRequest("Agent request: 'Get all user ratings in application'");
		
		GetAllRatingsResponse response = (GetAllRatingsResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<Rating> ratings = new ArrayList<Rating>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all existing ratings in application'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getRatingsList().size(); i++) {
			
			Rating r = new Rating();
			r.setId(response.getRatingsList().get(i).getId());
			r.setComment(response.getRatingsList().get(i).getComment());
			r.setRatingMark(response.getRatingsList().get(i).getRatingMark());
			r.setApproved(response.getRatingsList().get(i).isApproved());
			Room room = roomRepository.getOne(response.getRatingsList().get(i).getRoom().getId());
			r.setRoom(room);
			User user = userRespository.getOne(response.getRatingsList().get(i).getUser().getId());
			r.setUser(user);
			
			ratingRepository.save(r);
			ratings.add(r);
			
		}
		

		return (ArrayList<Rating>) ratings;
	}

}
