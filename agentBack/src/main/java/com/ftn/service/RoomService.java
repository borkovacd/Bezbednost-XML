package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dto.RoomDTO;
import com.ftn.model.Accomodation;
import com.ftn.model.AdditionalServices;
import com.ftn.model.Agent;
import com.ftn.model.Category;
import com.ftn.model.City;
import com.ftn.model.Price;
import com.ftn.model.Reservation;
import com.ftn.model.ReservationAgent;
import com.ftn.model.Room;
import com.ftn.model.TypeAccomodation;
import com.ftn.repository.AccomodationRepository;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.PriceRepository;
import com.ftn.repository.RatingRepository;
import com.ftn.repository.ReservationAgentRepository;
import com.ftn.repository.ReservationRepository;
import com.ftn.repository.RoomRepository;
import com.ftn.security.TokenUtils;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.AccomodationSoap;
import com.ftn.webservice.files.AdditionalServicesSoap;
import com.ftn.webservice.files.CategorySoap;
import com.ftn.webservice.files.CitySoap;
import com.ftn.webservice.files.DeleteAccomodationRequest;
import com.ftn.webservice.files.DeleteAccomodationResponse;
import com.ftn.webservice.files.DeleteRoomRequest;
import com.ftn.webservice.files.DeleteRoomResponse;
import com.ftn.webservice.files.EditAccomodationRequest;
import com.ftn.webservice.files.EditAccomodationResponse;
import com.ftn.webservice.files.EditRoomRequest;
import com.ftn.webservice.files.EditRoomResponse;
import com.ftn.webservice.files.GetAccomodationRequest;
import com.ftn.webservice.files.GetAccomodationResponse;
import com.ftn.webservice.files.GetAccomodationRoomsRequest;
import com.ftn.webservice.files.GetAccomodationRoomsResponse;
import com.ftn.webservice.files.GetRoomRequest;
import com.ftn.webservice.files.GetRoomResponse;
import com.ftn.webservice.files.RegisterRoomRequest;
import com.ftn.webservice.files.RegisterRoomResponse;
import com.ftn.webservice.files.RoomSoap;
import com.ftn.webservice.files.TypeAccomodationSoap;

@Service
public class RoomService {
	private static final Logger log = LoggerFactory.getLogger(RoomService.class);

	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Autowired
	private AccomodationRepository accomodationRepository;
	@Autowired
	private PriceRepository priceRepository;
	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private SOAPConnector soapConnector;

	public void createRoom(RoomDTO roomDTO, Long idAccomodation,String token) throws Exception {
		String usname = tokenUtils.getUserSecurity(token).getUsername();
		
		Agent ag = agentRepository.findOneByUsername(usname);
		log.info("User id: "+ag.getId()+"  CREAROOM");
		RegisterRoomRequest request = new RegisterRoomRequest();
		String accomodationName = accomodationRepository.getOne(idAccomodation).getName();
		request.setRequest("Agent request: 'Register new room in accomodation '" + accomodationName + "'.");
		request.setAccomodationId(idAccomodation);

		RoomSoap r = new RoomSoap();

	    r.setCapacity(roomDTO.getCapacity());
		r.setActive(false);
		r.setFloor(roomDTO.getFloor());
		r.setReserved(false);
		r.setDay(roomDTO.getDay());
		r.setHasBalcony(roomDTO.isHasBalcony());
		
		request.setRoom(r);

		RegisterRoomResponse response = (RegisterRoomResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);

		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		Room room = new Room();
		
		room.setId(response.getRoomId());
		room.setCapacity(r.getCapacity());
		room.setActive(r.isActive());
		room.setDay(r.getDay());
		room.setFloor(r.getFloor());
		room.setHasBalcony(r.isHasBalcony());
		room.setReserved(r.isReserved());
		Accomodation accomodation = accomodationRepository.getOne(request.getAccomodationId());
		room.setAccomodation(accomodation);
		
		roomRepository.save(room);
		log.info("User id: "+ag.getId()+"  CREAROOMSUCCESS");

		
	}

	public ArrayList<Room> getAllRooms(Long idAccomodation,String token) throws Exception {
		String usname = tokenUtils.getUserSecurity(token).getUsername();
		
		Agent ag = agentRepository.findOneByUsername(usname);
		log.info("User id: "+ag.getId()+"  GETAROOM");
		
		GetAccomodationRoomsRequest request = new GetAccomodationRoomsRequest();
		request.setRequest("Agent request: 'Get all rooms in accomodation '" + accomodationRepository.getOne(idAccomodation).getName() + "'");
		request.setAccomodationId(idAccomodation);
		
		GetAccomodationRoomsResponse response = (GetAccomodationRoomsResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		Accomodation accomodation = accomodationRepository.getOne(idAccomodation);
		
		List<Room> rooms = new ArrayList<Room>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Successfully sent list of all rooms in requested accomodation'");
		System.out.println("*****");
		
		for(int i = 0; i < response.getRoomslist().size(); i++) {
			
			Room r = new Room();
			r.setId(response.getRoomslist().get(i).getId());
			r.setCapacity(response.getRoomslist().get(i).getCapacity());
			r.setFloor(response.getRoomslist().get(i).getFloor());
			r.setHasBalcony(response.getRoomslist().get(i).isHasBalcony());
			r.setActive(response.getRoomslist().get(i).isActive());
			r.setDay(response.getRoomslist().get(i).getDay());
			r.setReserved(response.getRoomslist().get(i).isReserved());
			r.setAccomodation(accomodation);
			
			rooms.add(r);
			roomRepository.save(r);
			
		}
		log.info("User id: "+ag.getId()+"  GETAROOMSUCCESS");

		
		return (ArrayList<Room>) rooms;
	}
	
	
	public boolean deleteRoom(Long idAccomodation, Long idRoom,String token) throws Exception {
		String usname = tokenUtils.getUserSecurity(token).getUsername();
		
		Agent ag = agentRepository.findOneByUsername(usname);
		log.info("User id: "+ag.getId()+"  DELROOM");
		DeleteRoomRequest request = new DeleteRoomRequest();
		String accommodationName = accomodationRepository.getOne(idAccomodation).getName();
		request.setRequest("Agent request: 'Delete room in accomodation '" + accommodationName + "'.");
		request.setAccomodationId(idAccomodation);
		request.setRoomId(idRoom);
		
		DeleteRoomResponse response = (DeleteRoomResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		for(Room room : roomRepository.findAll()) {
			if(room.getAccomodation().getId() == idAccomodation) {
				if(room.getId() == idRoom) {
					for(Price price : priceRepository.findAll()) {
						if(price.getRoom().getId() == room.getId()) {
							priceRepository.delete(price);
						}
					}	
					roomRepository.delete(room);
				}
				
			}
		}
		log.info("User id: "+ag.getId()+"  DELROOMSUCCESS");

		
		return true;
	}

	public boolean checkIfRoomIsReserved(Long id) {
		
		boolean taken = false;
	
		List<Reservation> reservations = reservationRepository.findAll();
		List<ReservationAgent> reservationsAgent = reservationAgentRepository.findAll();
		
		for(Reservation reservation : reservations) {
			if(reservation.getRoom().getId() == id) {
				taken = true;
			}
		}
		
		for(ReservationAgent reservationAgent : reservationsAgent) {
			if(reservationAgent.getRoom().getId() == id) {
				taken = true;
			}
		}
		
		return taken;
	}

	public Room editRoom(Long idAccomodation, Long idRoom, RoomDTO roomDTO,String token) throws Exception {
		String usname = tokenUtils.getUserSecurity(token).getUsername();
		
		Agent ag = agentRepository.findOneByUsername(usname);
		log.info("User id: "+ag.getId()+"  EDITROOM");

		
		EditRoomRequest request = new EditRoomRequest();
		request.setRequest("Agent request: 'Edit data of room with id " + idRoom + ".");
		request.setAccomodationId(idAccomodation);
		request.setRoomId(idRoom);
		
		RoomSoap r = new RoomSoap();

	    r.setCapacity(roomDTO.getCapacity());
		r.setActive(false);
		r.setFloor(roomDTO.getFloor());
		r.setReserved(false);
		r.setDay(roomDTO.getDay());
		r.setHasBalcony(roomDTO.isHasBalcony());
		
		request.setEditRoomData(r);

		EditRoomResponse response = (EditRoomResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		Room room = roomRepository.getOne(response.getEditedRoom().getId());
		
		room.setId(response.getEditedRoom().getId());
		room.setCapacity(response.getEditedRoom().getCapacity());
		room.setFloor(response.getEditedRoom().getFloor());
		room.setReserved(response.getEditedRoom().isReserved());
		room.setDay(response.getEditedRoom().getDay());
		room.setHasBalcony(response.getEditedRoom().isHasBalcony());
		room.setActive(response.getEditedRoom().isActive());
		Accomodation accomodation = accomodationRepository.getOne(idAccomodation);
		room.setAccomodation(accomodation);
		
		roomRepository.save(room);
		log.info("User id: "+ag.getId()+"  EDITROOMSUCCESS");

		
		return room;
	}

	public Room getRoom(Long idRoom,String token) throws Exception {
      String usname = tokenUtils.getUserSecurity(token).getUsername();
		
		Agent ag = agentRepository.findOneByUsername(usname);
		log.info("User id: "+ag.getId()+"  GET1AROOM");
		GetRoomRequest request = new GetRoomRequest();
		request.setRequestedRoomId(idRoom);
		
		GetRoomResponse response = (GetRoomResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		
		Room room = roomRepository.findOneById(response.getReturnedRoom().getId());
		
		return room;
	}

	public String getRoomRating(Long idRoom) {
		
		String comment = null;
		
		for(int i=0; i<ratingRepository.findAll().size(); i++) {
			if(ratingRepository.findAll().get(i).getRoom().getId() == idRoom) {
				comment = ratingRepository.findAll().get(i).getComment();
				return comment;
			}
		}
		
		return comment;
	}
}
