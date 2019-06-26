package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.controller.AccomodationController;
import com.ftn.dto.AccomodationDTO;
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
import com.ftn.model.User;
import com.ftn.repository.AccomodationRepository;
import com.ftn.repository.AdditionalServicesRepository;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.CategoryRepository;
import com.ftn.repository.CityRepository;
import com.ftn.repository.PriceRepository;
import com.ftn.repository.ReservationAgentRepository;
import com.ftn.repository.ReservationRepository;
import com.ftn.repository.RoomRepository;
import com.ftn.repository.TypeAccomodationRepository;
import com.ftn.repository.UserRepository;
import com.ftn.security.TokenUtils;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.AccomodationSoap;
import com.ftn.webservice.files.AdditionalServicesSoap;
import com.ftn.webservice.files.CategorySoap;
import com.ftn.webservice.files.CitySoap;
import com.ftn.webservice.files.DeleteAccomodationRequest;
import com.ftn.webservice.files.DeleteAccomodationResponse;
import com.ftn.webservice.files.EditAccomodationRequest;
import com.ftn.webservice.files.EditAccomodationResponse;
import com.ftn.webservice.files.GetAccomodationRequest;
import com.ftn.webservice.files.GetAccomodationResponse;
import com.ftn.webservice.files.GetAllAccomodationsRequest;
import com.ftn.webservice.files.GetAllAccomodationsResponse;
import com.ftn.webservice.files.RegisterAccomodationRequest;
import com.ftn.webservice.files.RegisterAccomodationResponse;
import com.ftn.webservice.files.TypeAccomodationSoap;

@Service
public class AccomodationService {
	private static final Logger log = LoggerFactory.getLogger(AccomodationService.class);

	@Autowired
	private AccomodationRepository accomodationRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private TypeAccomodationRepository typeAccomodationRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private AdditionalServicesRepository additionalServicesRepository;
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private PriceRepository priceRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private TokenUtils tokenUtils;
	
	
	@Autowired
	private SOAPConnector soapConnector;

	public Accomodation create(AccomodationDTO accDTO, String token) throws Exception {
		
		String usname = tokenUtils.getUserSecurity(token).getUsername();

		RegisterAccomodationRequest request = new RegisterAccomodationRequest();
		request.setRequest("Agent request: 'Register new accomodation '" + accDTO.getName() + "'");

		AccomodationSoap a = new AccomodationSoap();
		
		//token = token.substring(1,token.length()-1).toString();
		
		
		
		Agent ag = agentRepository.findOneByUsername(usname);
		log.info("User id: "+ag.getId()+"  CREACC");

		a.setName(accDTO.getName());
		CitySoap c = new CitySoap();
		c.setName(accDTO.getCity());
		a.setCity(c);
		a.setAddress(accDTO.getAddress());
		TypeAccomodationSoap ta = new TypeAccomodationSoap();
		ta.setName(accDTO.getType());
		a.setTypeAccomodation(ta);
		CategorySoap ca = new CategorySoap();
		ca.setName(accDTO.getCategory());
		a.setCategory(ca);
		a.setDescription(accDTO.getDescription());
		a.setPic(accDTO.getPic());
		a.setAgent(ag.getId());
		for(int i=0; i<accDTO.getList().size(); i++) {
			AdditionalServicesSoap ass = new AdditionalServicesSoap();
			ass.setName(accDTO.getList().get(i));
			a.getAdditionalServices().add(ass);
		}

		request.setAccomodation(a);

		RegisterAccomodationResponse response = (RegisterAccomodationResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);

		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println("Head back response: 'New accomodation is successfully registered'");
		System.out.println("*****");
		
		Accomodation accomodation = new Accomodation();
		
		accomodation.setId(response.getAccomodationId());
		accomodation.setName(a.getName());
		City city = cityRepository.findByName(a.getCity().getName());
		accomodation.setCity(city);
		accomodation.setAddress(a.getAddress());
		TypeAccomodation typeAccomodation = typeAccomodationRepository.findByName(a.getTypeAccomodation().getName());
		accomodation.setTypeAccomodation(typeAccomodation);
		Category category = categoryRepository.findByName(a.getCategory().getName());
		accomodation.setCategory(category);
		accomodation.setDescription(a.getDescription());
		accomodation.setPic(a.getPic());
		accomodation.setAgent(agentRepository.getOne(a.getAgent()));
		List<AdditionalServices> additionalServices = new ArrayList<AdditionalServices>();
		for(int i=0; i<a.getAdditionalServices().size(); i++) {
			AdditionalServices additionalService = additionalServicesRepository.findByName(a.getAdditionalServices().get(i).getName());
			additionalServices.add(additionalService);
		}
		accomodation.setAdditionalServices(additionalServices);
		
		accomodationRepository.save(accomodation);
		
		log.info("User id: "+ag.getId()+"  CREACCSUCCESS");
		return accomodation;

	}


	public boolean delete(Long id,String token) throws Exception {
		
		String usname = tokenUtils.getUserSecurity(token).getUsername();
		
		Agent ag = agentRepository.findOneByUsername(usname);
		log.info("User id: "+ag.getId()+"  DELETEACC");

		DeleteAccomodationRequest request = new DeleteAccomodationRequest();
		String accommodationName = accomodationRepository.getOne(id).getName();
		request.setRequest("Agent request: 'Delete accomodation '" + accommodationName + "'");
		request.setDeleteAccomodationId(id);
		
		DeleteAccomodationResponse response = (DeleteAccomodationResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		Accomodation a = accomodationRepository.findOneById(id);
		
		for(Room room : roomRepository.findAll()) {
			if(room.getAccomodation().getId() == id) {
				for(Price price : priceRepository.findAll()) {
					if(price.getRoom().getId() == room.getId()) {
						priceRepository.delete(price);
					}
				}
				roomRepository.delete(room);
			}
		}

		
		response.setDeletedAccomodationId(a.getId());
		response.setResponse("Accommodation '" + a.getName() + "' is successfully deleted!");
		
		for(Accomodation accomodation : accomodationRepository.findAll()) {
			if(accomodation.getId() == id) {
				accomodationRepository.delete(accomodation);
			}
		}
		log.info("User id: "+ag.getId()+"  DELETEACCSUCCESS");

		return true;

	}

	public Accomodation edit(String token, Long id, AccomodationDTO accDTO) throws Exception {
		
		EditAccomodationRequest request = new EditAccomodationRequest();
		request.setRequest("Agent request: 'Edit data of accommodation with id " + id + ".");
		request.setEditAccomodationId(id);
		
		AccomodationSoap a = new AccomodationSoap();
		
	//	token = token.substring(1,token.length()-1).toString();
		
		String usname = tokenUtils.getUserSecurity(token).getUsername();

		Agent ag = agentRepository.findOneByUsername(usname);
		log.info("User id: "+ag.getId()+"  EDITACC");

		a.setName(accDTO.getName());
		CitySoap c = new CitySoap();
		c.setName(accDTO.getCity());
		a.setCity(c);
		a.setAddress(accDTO.getAddress());
		TypeAccomodationSoap ta = new TypeAccomodationSoap();
		ta.setName(accDTO.getType());
		a.setTypeAccomodation(ta);
		CategorySoap ca = new CategorySoap();
		ca.setName(accDTO.getCategory());
		a.setCategory(ca);
		a.setDescription(accDTO.getDescription());
		a.setPic(accDTO.getPic());
		a.setAgent(ag.getId());
		for(int i=0; i<accDTO.getList().size(); i++) {
			AdditionalServicesSoap ass = new AdditionalServicesSoap();
			ass.setName(accDTO.getList().get(i));
			a.getAdditionalServices().add(ass);
		}
		
		request.setEditAccomodationData(a);
		
		
		EditAccomodationResponse response = (EditAccomodationResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		Accomodation accomodation = accomodationRepository.getOne(response.getEditedAccomodation().getId());
		
		accomodation.setId(response.getEditedAccomodation().getId());
		accomodation.setName(a.getName());
		City city = cityRepository.findByName(a.getCity().getName());
		accomodation.setCity(city);
		accomodation.setAddress(a.getAddress());
		TypeAccomodation typeAccomodation = typeAccomodationRepository.findByName(a.getTypeAccomodation().getName());
		accomodation.setTypeAccomodation(typeAccomodation);
		Category category = categoryRepository.findByName(a.getCategory().getName());
		accomodation.setCategory(category);
		accomodation.setDescription(a.getDescription());
		accomodation.setPic(a.getPic());
		accomodation.setAgent(agentRepository.getOne(a.getAgent()));
		List<AdditionalServices> additionalServices = new ArrayList<AdditionalServices>();
		for(int i=0; i<a.getAdditionalServices().size(); i++) {
			AdditionalServices additionalService = additionalServicesRepository.findByName(a.getAdditionalServices().get(i).getName());
			additionalServices.add(additionalService);
		}
		accomodation.setAdditionalServices(additionalServices);
		
		accomodationRepository.save(accomodation);
		log.info("User id: "+ag.getId()+"  EDITACCSUCCESS");

		return accomodation;

	}

	public ArrayList<Accomodation> getAllAccomodation(String token) throws Exception {
		
		GetAllAccomodationsRequest request = new GetAllAccomodationsRequest();
		
		//token = token.substring(1,token.length()-1).toString();
		
		String usname = tokenUtils.getUserSecurity(token).getUsername();
		
		
		Agent ag = agentRepository.findOneByUsername(usname);
		log.info("User id: "+ag.getId()+"  GETAACC");
		request.setRequest("Agent request: 'Return all accomodation added by agent '" + usname + "'");
		request.setAgentId(ag.getId());
		
		GetAllAccomodationsResponse response = (GetAllAccomodationsResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		List<Accomodation> accomodations = new ArrayList<Accomodation>();
		
		for(int i = 0; i < response.getAccomodationsList().size(); i++) {
			
			Accomodation a = new Accomodation();
			a.setId(response.getAccomodationsList().get(i).getId());
			a.setName(response.getAccomodationsList().get(i).getName());
			String cityName = response.getAccomodationsList().get(i).getCity().getName();
			a.setCity(cityRepository.findByName(cityName));
			a.setAddress(response.getAccomodationsList().get(i).getAddress());
			String typeAccomodationName = response.getAccomodationsList().get(i).getTypeAccomodation().getName();
			a.setTypeAccomodation(typeAccomodationRepository.findByName(typeAccomodationName));
			String categoryName = response.getAccomodationsList().get(i).getCategory().getName();
			a.setCategory(categoryRepository.findByName(categoryName));
			a.setDescription(response.getAccomodationsList().get(i).getDescription());
			a.setPic(response.getAccomodationsList().get(i).getPic());
			a.setAgent(ag);
			List<AdditionalServices> as = new ArrayList<AdditionalServices>();
			for(int j=0; j<response.getAccomodationsList().get(i).getAdditionalServices().size(); j++) {
				String additionalServiceName = response.getAccomodationsList().get(i).getAdditionalServices().get(j).getName();
				AdditionalServices additonalService = additionalServicesRepository.findByName(additionalServiceName);
				as.add(additonalService);
			}
			a.setAdditionalServices(as);

			accomodationRepository.save(a);
			accomodations.add(a);
			
		}
		log.info("User id: "+ag.getId()+"  GETAACCSUCCESS");


		return (ArrayList<Accomodation>) accomodations;
	}

	public Accomodation getAccomodation(Long id) {
		//fali token
		GetAccomodationRequest request = new GetAccomodationRequest();
		request.setRequestedAccomodationId(id);
		
		GetAccomodationResponse response = (GetAccomodationResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		
		Accomodation accomodation = accomodationRepository.findOneById(response.getReturnedAccomodation().getId());
		
		return accomodation;
	}


	public boolean checkIfAccommodationIsReserved(Long id) {
		//fali token
		boolean taken = false;
		
		List<Reservation> reservations = reservationRepository.findAll();
		List<ReservationAgent> reservationsAgent = reservationAgentRepository.findAll();
		
		
		for(Room room : roomRepository.findAll()) {
			if(room.getAccomodation().getId() == id) {
				for(Reservation reservation : reservations) {
					if(reservation.getRoom().getId() == room.getId()) {
							taken = true;
					}
				}
			}
		}
		
		for(Room room : roomRepository.findAll()) {
			if(room.getAccomodation().getId() == id) {
				for(ReservationAgent reservationAgent : reservationsAgent) {
					if(reservationAgent.getRoom().getId() == room.getId()) {
							taken = true;
					}
				}
			}
		}
		
		
		return taken;
	}
	


}
