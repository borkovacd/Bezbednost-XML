package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dto.RoomDTO;
import com.ftn.model.Accomodation;
import com.ftn.model.AdditionalServices;
import com.ftn.model.Category;
import com.ftn.model.City;
import com.ftn.model.Price;
import com.ftn.model.Room;
import com.ftn.model.TypeAccomodation;
import com.ftn.repository.AccomodationRepository;
import com.ftn.repository.PriceRepository;
import com.ftn.repository.RoomRepository;
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
	
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private AccomodationRepository accomodationRepository;
	@Autowired
	private PriceRepository priceRepository;
	
	@Autowired
	private SOAPConnector soapConnector;

	public void createRoom(RoomDTO roomDTO, Long idAccomodation) {
		
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
		
		roomRepository.save(room);
		Accomodation accomodation = accomodationRepository.getOne(request.getAccomodationId());
		List<Room> rooms = accomodation.getRooms();
		rooms.add(room);
		accomodation.setRooms(rooms);
		accomodationRepository.save(accomodation);
		
	}

	public ArrayList<Room> getAllRooms(Long idAccomodation) {
		
		GetAccomodationRoomsRequest request = new GetAccomodationRoomsRequest();
		request.setRequest("Agent request: 'Get all rooms in accomodation '" + accomodationRepository.getOne(idAccomodation).getName() + "'");
		request.setAccomodationId(idAccomodation);
		
		GetAccomodationRoomsResponse response = (GetAccomodationRoomsResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		Accomodation accomodation = accomodationRepository.getOne(idAccomodation);
		
		List<Room> rooms = new ArrayList<Room>();
		List<Room> accRooms;
		
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
			
			rooms.add(r);
			roomRepository.save(r);
			/*if(accomodation.getRooms() == null) {
				accRooms = new ArrayList<Room>();
				accRooms.add(r);
				accomodation.setRooms(accRooms);
			} else {
				if(!accomodation.getRooms().contains(r)) {
					accomodation.getRooms().add(r);
				}
			}*/
			
			
			
		}
		
		//accomodationRepository.save(accomodation);
		return (ArrayList<Room>) rooms;
	}
	
	
	public boolean deleteRoom(Long idAccomodation, Long idRoom) {
		
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
		
		Accomodation a = accomodationRepository.findOneById(idAccomodation);
		for(int i=0; i<a.getRooms().size(); i++) {
			if(a.getRooms().get(i).getId() == idRoom) {
				for(Price price : priceRepository.findAll()) {
					if(price.getRoom().getId() == idRoom) {
						priceRepository.delete(price);
					}
				}
				
				a.getRooms().remove(i);
				accomodationRepository.save(a);
				Room room = roomRepository.getOne(idRoom);
				roomRepository.delete(room);
			
			}
		}
		
		
		
		return true;
	}

	public boolean checkIfRoomIsReserved(Long id) {
		
		boolean taken = false;
		
		Room room = roomRepository.getOne(id);
		if(room.isReserved() == true) {
			taken = true;
		} else {
			taken = false;
		}
		
		return taken;
	}

	public Room editRoom(Long idAccomodation, Long idRoom, RoomDTO roomDTO) {
		
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
		
		roomRepository.save(room);
		
		return room;
	}

	public Room getRoom(Long idRoom) {
		
		GetRoomRequest request = new GetRoomRequest();
		request.setRequestedRoomId(idRoom);
		
		GetRoomResponse response = (GetRoomResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		
		Room room = roomRepository.findOneById(response.getReturnedRoom().getId());
		
		return room;
	}
}
