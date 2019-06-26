package com.ftn.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.controller.ReservationController;
import com.ftn.model.Accomodation;
import com.ftn.model.AdditionalServices;
import com.ftn.model.Agent;
import com.ftn.model.Reservation;
import com.ftn.model.Room;
import com.ftn.model.User;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.ReservationRepository;
import com.ftn.repository.RoomRepository;
import com.ftn.repository.UserRepository;
import com.ftn.security.TokenUtils;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.ConfirmReservationRequest;
import com.ftn.webservice.files.ConfirmReservationResponse;
import com.ftn.webservice.files.GetAllAccomodationsRequest;
import com.ftn.webservice.files.GetAllAccomodationsResponse;
import com.ftn.webservice.files.GetAllReservationsRequest;
import com.ftn.webservice.files.GetAllReservationsResponse;
import com.ftn.webservice.files.GetRoomRequest;
import com.ftn.webservice.files.GetRoomResponse;
import com.ftn.webservice.files.RoomSoap;


@Service
public class ReservationService {
	private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

	
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private SOAPConnector soapConnector;

	public List<Reservation> getAllReservations(String token) throws Exception {
		
		//token = token.substring(1,token.length()-1).toString();
		
		String usname = tokenUtils.getUserSecurity(token).getUsername();
		
		GetAllReservationsRequest request = new GetAllReservationsRequest();
		
		Agent ag = agentRepository.findOneByUsername(usname);
		log.info("User id: "+ ag.getId()+" GETALLRES");

		request.setRequest("Agent request: 'Return all reservations by agent '" + usname + "'");
		request.setAgentId(ag.getId());
		
		GetAllReservationsResponse response = (GetAllReservationsResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		List<Reservation> reservations = new ArrayList<Reservation>();
		
		for(int i = 0; i < response.getReservationsList().size(); i++) {
			
			Reservation r = new Reservation();
			r.setId(response.getReservationsList().get(i).getId());
			XMLGregorianCalendar fromDate = response.getReservationsList().get(i).getFromDate();
			r.setFromDate(fromDate.toGregorianCalendar().toZonedDateTime().toLocalDate());
			XMLGregorianCalendar toDate = response.getReservationsList().get(i).getToDate();
			r.setToDate(toDate.toGregorianCalendar().toZonedDateTime().toLocalDate());
			Room room = roomRepository.getOne(response.getReservationsList().get(i).getRoom().getId());
			r.setRoom(room);
			User user = userRepository.getOne(response.getReservationsList().get(i).getUser().getId());
			r.setUser(user);
			Agent agent = agentRepository.getOne(response.getReservationsList().get(i).getAgent().getId());
			r.setAgent(agent);
			r.setPrice(response.getReservationsList().get(i).getPrice());
			r.setConfirmed(response.getReservationsList().get(i).isConfirmed());

			reservationRepository.save(r);
			reservations.add(r);
			
		}
		
		log.info("User id: "+ ag.getId()+" GETALLRESSUCCESS");

		return (ArrayList<Reservation>) reservations;
	}

	public void confirmReservation(Long idReservation) {
		
		ConfirmReservationRequest  request = new ConfirmReservationRequest();
		request.setRequest("Agent request: 'Confirm reservation with id " + idReservation + ".");
		request.setReservationId(idReservation);
		
		ConfirmReservationResponse response = (ConfirmReservationResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		Reservation reservation = reservationRepository.getOne(idReservation);
		reservation.setConfirmed(true);
		
		reservationRepository.save(reservation);

	}
}
