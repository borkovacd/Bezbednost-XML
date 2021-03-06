package com.ftn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dto.ReservationDTO;
import com.ftn.model.Accomodation;
import com.ftn.model.AdditionalServices;
import com.ftn.model.Agent;
import com.ftn.model.Category;
import com.ftn.model.City;
import com.ftn.model.Reservation;
import com.ftn.model.ReservationAgent;
import com.ftn.model.Room;
import com.ftn.model.TypeAccomodation;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.ReservationAgentRepository;
import com.ftn.repository.ReservationRepository;
import com.ftn.repository.RoomRepository;
import com.ftn.security.TokenUtils;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.AccomodationSoap;
import com.ftn.webservice.files.AdditionalServicesSoap;
import com.ftn.webservice.files.AgentSoap;
import com.ftn.webservice.files.CategorySoap;
import com.ftn.webservice.files.CitySoap;
import com.ftn.webservice.files.CreateReservationAgentRequest;
import com.ftn.webservice.files.CreateReservationAgentResponse;
import com.ftn.webservice.files.GetAllReservationsAgentRequest;
import com.ftn.webservice.files.GetAllReservationsAgentResponse;
import com.ftn.webservice.files.RegisterAccomodationRequest;
import com.ftn.webservice.files.RegisterAccomodationResponse;
import com.ftn.webservice.files.ReservationAgentSoap;
import com.ftn.webservice.files.RoomSoap;
import com.ftn.webservice.files.TypeAccomodationSoap;

@Service
public class ReservationAgentService {
	private static final Logger log = LoggerFactory.getLogger(ReservationAgentService.class);

	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private SOAPConnector soapConnector;

	public List<ReservationAgent> getAllReservations(String token) throws Exception {
		
		String username = tokenUtils.getUserSecurity(token).getUsername();
		
		GetAllReservationsAgentRequest request = new GetAllReservationsAgentRequest();
		
		Agent ag = agentRepository.findOneByUsername(username);
		log.info("User id: "+ ag.getId()+" GETARES");

		request.setRequest("Agent request: 'Return all agent reservations by agent '" + username + "'");
		request.setAgentId(ag.getId());
		
		GetAllReservationsAgentResponse response = (GetAllReservationsAgentResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		List<ReservationAgent> reservations = new ArrayList<ReservationAgent>();
		
		for(int i = 0; i < response.getReservationsList().size(); i++) {
			
			ReservationAgent r = new ReservationAgent();
			r.setId(response.getReservationsList().get(i).getId());
			XMLGregorianCalendar fromDate = response.getReservationsList().get(i).getFromDate();
			r.setFromDate(fromDate.toGregorianCalendar().toZonedDateTime().toLocalDate());
			XMLGregorianCalendar toDate = response.getReservationsList().get(i).getToDate();
			r.setToDate(toDate.toGregorianCalendar().toZonedDateTime().toLocalDate());
			Room room = roomRepository.getOne(response.getReservationsList().get(i).getRoom().getId());
			r.setRoom(room);
			Agent agent = agentRepository.getOne(response.getReservationsList().get(i).getAgent().getId());
			r.setAgent(agent);

			reservationAgentRepository.save(r);
			reservations.add(r);
			log.info("User id: "+ ag.getId()+" GETARESSUCCESS");

			
		}
		

		return (ArrayList<ReservationAgent>) reservations;
	}

	public void createReservation(ReservationDTO reservationDTO, Long idRoom, String token) throws Exception {
		
		CreateReservationAgentRequest request = new CreateReservationAgentRequest();
		
		String username = tokenUtils.getUserSecurity(token).getUsername();
		Agent agent = agentRepository.findOneByUsername(username);
		log.info("User id: "+ agent.getId()+" CREARESAGENT");

		request.setRequest("Agent request: 'Create new agent reservation by agent '" + username + "'");
		request.setAgentId(agent.getId());
		request.setRoomId(idRoom);
	
		ReservationAgentSoap r = new ReservationAgentSoap();
		
		String europeanDatePattern = "yyyy-MM-dd";
		DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
		LocalDate d1 = LocalDate.parse(reservationDTO.getCheckInDate(), europeanDateFormatter);
		LocalDate d2 = LocalDate.parse(reservationDTO.getCheckOutDate(), europeanDateFormatter);
		
		LocalDate fromDate = d1;
		GregorianCalendar gcalFromDate = GregorianCalendar.from(fromDate.atStartOfDay(ZoneId.systemDefault()));
		XMLGregorianCalendar xcalFromDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalFromDate);
		r.setFromDate(xcalFromDate);
		LocalDate toDate = d2;
		GregorianCalendar gcalToDate = GregorianCalendar.from(toDate.atStartOfDay(ZoneId.systemDefault()));
		XMLGregorianCalendar xcalToDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalToDate);
		r.setToDate(xcalToDate);
		
		RoomSoap rs = new RoomSoap();
		rs.setId(roomRepository.getOne(idRoom).getId());
		r.setRoom(rs);
		
		AgentSoap as = new AgentSoap();
		as.setId(agentRepository.getOne(agent.getId()).getId());
		r.setAgent(as);

		request.setReservationAgent(r);

		CreateReservationAgentResponse response = (CreateReservationAgentResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);

		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'Agent reservation is successfully created'");
		System.out.println("*****");
		
		
		ReservationAgent reservationAgent = new ReservationAgent();
		reservationAgent.setId(response.getReservationAgentId());
		reservationAgent.setFromDate(d1);
		reservationAgent.setToDate(d2);
		reservationAgent.setRoom(roomRepository.getOne(idRoom));
		reservationAgent.setAgent(agentRepository.getOne(agent.getId()));
		
		reservationAgentRepository.save(reservationAgent);
		log.info("User id: "+ agent.getId()+" CREARESAGENTSUCCESS");

		
	}

	public List<Room> searchFreeRooms(LocalDate d1, LocalDate d2) throws ParseException {
		log.debug("SEARROOM");
		
		String europeanDatePattern = "yyyy-MM-dd";
		DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
		String formattedString = d1.format(europeanDateFormatter);
	    
	    if (new SimpleDateFormat("yyyy-MM-dd").parse(formattedString).before(new Date())) {
	    	List<Room> emptyRoomsList = new ArrayList<Room>();
	    	return emptyRoomsList;
	      }
	    
		
		List<Room> allRooms = roomRepository.findAll();

		List<Reservation> reservations = reservationRepository.findAll();
		List<ReservationAgent> reservationsAgent = reservationAgentRepository.findAll();
		
		for (Reservation res : reservations) 
		{
			if(res.isConfirmed() == true) {
				if (allRooms.contains(res.getRoom()))
				{
				
					if((res.getFromDate().compareTo(d1) >= 0 && res.getFromDate().compareTo(d2) <= 0) || (res.getToDate().compareTo(d1) >= 0 && res.getToDate().compareTo(d2) <= 0)) 
					{
						allRooms.remove(res.getRoom());
					}
				}
			}
		}
		
		for (ReservationAgent res : reservationsAgent) 
		{
			if (allRooms.contains(res.getRoom()))
			{
			
				if((res.getFromDate().compareTo(d1) >= 0 && res.getFromDate().compareTo(d2) <= 0) || (res.getToDate().compareTo(d1) >= 0 && res.getToDate().compareTo(d2) <= 0)) 
				{
					allRooms.remove(res.getRoom());
				}
			}
		}
		
		return allRooms;
	}

}
