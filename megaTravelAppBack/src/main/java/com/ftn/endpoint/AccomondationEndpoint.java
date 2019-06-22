package com.ftn.endpoint;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ftn.enums.ClientStatus;
import com.ftn.model.Accomodation;
import com.ftn.model.AdditionalServices;
import com.ftn.model.Category;
import com.ftn.model.City;
import com.ftn.model.Country;
import com.ftn.model.Price;
import com.ftn.model.Room;
import com.ftn.model.TypeAccomodation;
import com.ftn.model.User;
import com.ftn.repository.AccomondationRepository;
import com.ftn.repository.AdditionalServicesRepository;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.CategoryRepository;
import com.ftn.repository.CityRepository;
import com.ftn.repository.CountryRepository;
import com.ftn.repository.PriceRepository;
import com.ftn.repository.ReservationRepository;
import com.ftn.repository.RoomRepository;
import com.ftn.repository.TypeAccomodationRepository;
import com.ftn.repository.UserRepository;
import com.ftn.webservice.AccomodationSoap;
import com.ftn.webservice.AdditionalServicesSoap;
import com.ftn.webservice.AgentSoap;
import com.ftn.webservice.CategorySoap;
import com.ftn.webservice.CitySoap;
import com.ftn.webservice.CountrySoap;
import com.ftn.webservice.CreatePriceListRequest;
import com.ftn.webservice.CreatePriceListResponse;
import com.ftn.webservice.DeleteAccomodationRequest;
import com.ftn.webservice.DeleteAccomodationResponse;
import com.ftn.webservice.DeleteRoomRequest;
import com.ftn.webservice.DeleteRoomResponse;
import com.ftn.webservice.EditAccomodationRequest;
import com.ftn.webservice.EditAccomodationResponse;
import com.ftn.webservice.EditRoomRequest;
import com.ftn.webservice.EditRoomResponse;
import com.ftn.webservice.GetAccomodationRequest;
import com.ftn.webservice.GetAccomodationResponse;
import com.ftn.webservice.GetAccomodationRoomsRequest;
import com.ftn.webservice.GetAccomodationRoomsResponse;
import com.ftn.webservice.GetAllAccomodationTypesRequest;
import com.ftn.webservice.GetAllAccomodationTypesResponse;
import com.ftn.webservice.GetAllAccomodationsRequest;
import com.ftn.webservice.GetAllAccomodationsResponse;
import com.ftn.webservice.GetAllAdditionalServicesRequest;
import com.ftn.webservice.GetAllAdditionalServicesResponse;
import com.ftn.webservice.GetAllAgentsRequest;
import com.ftn.webservice.GetAllAgentsResponse;
import com.ftn.webservice.GetAllCategoriesRequest;
import com.ftn.webservice.GetAllCategoriesResponse;
import com.ftn.webservice.GetAllCitiesRequest;
import com.ftn.webservice.GetAllCitiesResponse;
import com.ftn.webservice.GetAllCountriesRequest;
import com.ftn.webservice.GetAllCountriesResponse;
import com.ftn.webservice.GetAllReservationsRequest;
import com.ftn.webservice.GetAllReservationsResponse;
import com.ftn.webservice.GetAllUsersRequest;
import com.ftn.webservice.GetAllUsersResponse;
import com.ftn.webservice.GetRoomPricesRequest;
import com.ftn.webservice.GetRoomPricesResponse;
import com.ftn.webservice.GetRoomRequest;
import com.ftn.webservice.GetRoomResponse;
import com.ftn.webservice.PriceSoap;
import com.ftn.webservice.RegisterAccomodationRequest;
import com.ftn.webservice.RegisterAccomodationResponse;
import com.ftn.webservice.RegisterRoomRequest;
import com.ftn.webservice.RegisterRoomResponse;
import com.ftn.webservice.ReservationSoap;
import com.ftn.webservice.RoomSoap;
import com.ftn.webservice.TypeAccomodationSoap;
import com.ftn.webservice.UserSoap;


//@Endpoint registers the class with Spring WS as a potential candidate for processing incoming SOAP messages.
@Endpoint
public class AccomondationEndpoint {
	private static final String NAMESPACE_URI = "http://ftn.com/webservice";
	
	private AccomondationRepository accomondationRepository;
	private AdditionalServicesRepository additionalServicesRepository;
	private CityRepository cityRepository;
	private CategoryRepository categoryRepository;
	private CountryRepository countryRepository;
	private RoomRepository roomRepository;
	private AgentRepository agentRepository;
	private TypeAccomodationRepository typeAccomodationRepository;
	private PriceRepository priceRepository;
	private ReservationRepository reservationRepository;
	private UserRepository userRepository;
	
	@Autowired
	public AccomondationEndpoint(AccomondationRepository accomondationRepository, AdditionalServicesRepository additionalServicesRepository, 
			CityRepository cityRepository, CategoryRepository categoryRepository, CountryRepository countryRepository, RoomRepository roomRepository, 
			AgentRepository agentRepository, TypeAccomodationRepository typeAccomodationRepository, PriceRepository priceRepository, 
			ReservationRepository reservationRepository, UserRepository userRepository) {
			
		this.accomondationRepository = accomondationRepository;
		this.additionalServicesRepository = additionalServicesRepository;
		this.cityRepository = cityRepository;
		this.categoryRepository = categoryRepository;
		this.countryRepository = countryRepository;
		this.roomRepository = roomRepository;
		this.agentRepository = agentRepository;
		this.typeAccomodationRepository = typeAccomodationRepository;
		this.priceRepository = priceRepository;
		this.reservationRepository = reservationRepository;
		this.userRepository = userRepository;
	}
	
	
	
	//@PayloadRoot is then used by Spring WS to pick the handler method based on the message’s 
	//namespace and localPart.
	//@RequestPayload indicates that the incoming message will be mapped to the method’s request parameter.
	//The @ResponsePayload annotation makes Spring WS map the returned value to the response payload.
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegisterAccomodationRequest")
	@ResponsePayload
	public RegisterAccomodationResponse getAccomondation(@RequestPayload RegisterAccomodationRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		RegisterAccomodationResponse response = new RegisterAccomodationResponse();
		
		AccomodationSoap a = request.getAccomodation();
		
		Accomodation accomodation = new Accomodation();
		
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
		
		accomondationRepository.save(accomodation);
		
		response.setAccomodationId(accomodation.getId());

		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteAccomodationRequest")
	@ResponsePayload
	public DeleteAccomodationResponse deleteAccomodation(@RequestPayload DeleteAccomodationRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		DeleteAccomodationResponse response = new DeleteAccomodationResponse();
		
		Long id = request.getDeleteAccomodationId();
		
		Accomodation a = accomondationRepository.findOneById(id);
		
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
		
		for(Accomodation accomodation : accomondationRepository.findAll()) {
			if(accomodation.getId() == id) {
				accomondationRepository.delete(accomodation);
			}
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "EditAccomodationRequest")
	@ResponsePayload
	public EditAccomodationResponse editAccomodation(@RequestPayload EditAccomodationRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
				
		EditAccomodationResponse response = new EditAccomodationResponse();
		
		Long id = request.getEditAccomodationId();
		AccomodationSoap a = request.getEditAccomodationData();
		
		Accomodation accomodation = accomondationRepository.findOneById(id);
		
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
		
		accomondationRepository.save(accomodation);
	
		a.setId(id);
		response.setEditedAccomodation(a);
		
		return response;
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAccomodationRequest")
	@ResponsePayload
	public GetAccomodationResponse getAccomodation(@RequestPayload GetAccomodationRequest request) {
		
		GetAccomodationResponse response = new GetAccomodationResponse();
		
		Long id = request.getRequestedAccomodationId();
		
		Accomodation requestedAccomodation = accomondationRepository.findOneById(id);
		
		AccomodationSoap a = new AccomodationSoap();
		a.setId(requestedAccomodation.getId());
		a.setName(requestedAccomodation.getName());
		a.setAddress(requestedAccomodation.getAddress());
		CitySoap c = new CitySoap();
		c.setName(requestedAccomodation.getCity().getName());
		a.setCity(c);
		TypeAccomodationSoap ta = new TypeAccomodationSoap();
		ta.setName(requestedAccomodation.getTypeAccomodation().getName());
		a.setTypeAccomodation(ta);
		CategorySoap ca = new CategorySoap();
		ca.setName(requestedAccomodation.getCategory().getName());
		a.setCategory(ca);
		a.setDescription(requestedAccomodation.getDescription());
		a.setPic(requestedAccomodation.getPic());
		a.setAgent(requestedAccomodation.getAgent().getId());
		for(int j=0; j<requestedAccomodation.getAdditionalServices().size(); j++) {
			AdditionalServicesSoap ass = new AdditionalServicesSoap();
			ass.setName(requestedAccomodation.getAdditionalServices().get(j).getName());
			a.getAdditionalServices().add(ass);
		}
		
		response.setReturnedAccomodation(a);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllAccomodationsRequest")
	@ResponsePayload
	public GetAllAccomodationsResponse getAllAccomodations(@RequestPayload GetAllAccomodationsRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		GetAllAccomodationsResponse response = new GetAllAccomodationsResponse();
		String agentUsername = agentRepository.getOne(request.getAgentId()).getUsername();
		response.setResponse("Head back response: 'Accomodations added by agent " + agentUsername + " successfully sent");

	
		for(int i = 0; i < accomondationRepository.findAll().size(); i++) {
			
			if(accomondationRepository.findAll().get(i).getAgent().getId() == request.getAgentId()) {
				
				AccomodationSoap a = new AccomodationSoap();

				a.setId(accomondationRepository.findAll().get(i).getId());
				a.setName(accomondationRepository.findAll().get(i).getName());
				CitySoap c = new CitySoap();
				c.setName(accomondationRepository.findAll().get(i).getCity().getName());
				a.setCity(c);
				a.setAddress(accomondationRepository.findAll().get(i).getAddress());
				TypeAccomodationSoap ta = new TypeAccomodationSoap();
				ta.setName(accomondationRepository.findAll().get(i).getTypeAccomodation().getName());
				a.setTypeAccomodation(ta);
				CategorySoap ca = new CategorySoap();
				ca.setName(accomondationRepository.findAll().get(i).getCategory().getName());
				a.setCategory(ca);
				a.setDescription(accomondationRepository.findAll().get(i).getDescription());
				a.setPic(accomondationRepository.findAll().get(i).getPic());
				a.setAgent(accomondationRepository.findAll().get(i).getAgent().getId());
				for(int j=0; j<accomondationRepository.findAll().get(i).getAdditionalServices().size(); j++) {
					AdditionalServicesSoap ass = new AdditionalServicesSoap();
					ass.setName(accomondationRepository.findAll().get(i).getAdditionalServices().get(j).getName());
					a.getAdditionalServices().add(ass);
				}
				
				response.getAccomodationsList().add(a);
				
			}

			
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllAdditionalServicesRequest")
	@ResponsePayload
	public GetAllAdditionalServicesResponse getAllAdditionalServices(@RequestPayload GetAllAdditionalServicesRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		GetAllAdditionalServicesResponse response = new GetAllAdditionalServicesResponse();
	
		
		for(int i = 0; i < additionalServicesRepository.findAll().size(); i++) {
			
			AdditionalServicesSoap a = new AdditionalServicesSoap();
			a.setId(additionalServicesRepository.findAll().get(i).getId());
			a.setName(additionalServicesRepository.findAll().get(i).getName());
			
			response.getAdditionalServicesList().add(a);
			
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllCitiesRequest")
	@ResponsePayload
	public GetAllCitiesResponse getAllCities(@RequestPayload GetAllCitiesRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		GetAllCitiesResponse response = new GetAllCitiesResponse();
	
		for(int i = 0; i < cityRepository.findAll().size(); i++) {
			
			CitySoap c = new CitySoap();
			c.setId(cityRepository.findAll().get(i).getId());
			c.setName(cityRepository.findAll().get(i).getName());
			Country newCountry = countryRepository.getOne(cityRepository.findAll().get(i).getCountry().getId());
			CountrySoap cs = new CountrySoap();
			cs.setName(newCountry.getName());
			cs.setId(newCountry.getId());
			c.setCountry(cs);
			
			response.getCitieslist().add(c);
			
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAccomodationRoomsRequest")
	@ResponsePayload
	public GetAccomodationRoomsResponse getAccomodationRooms(@RequestPayload GetAccomodationRoomsRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		GetAccomodationRoomsResponse response = new GetAccomodationRoomsResponse();
		
		Accomodation requestedAccomodation = accomondationRepository.getOne(request.getAccomodationId());
		
		System.out.println("Accomodation : " + requestedAccomodation.getName());
	
		for(int i = 0; i < roomRepository.findAll().size(); i++) {
			
			if(roomRepository.findAll().get(i).getAccomodation().getId() == request.getAccomodationId()) {
				
				RoomSoap rs = new RoomSoap();
				rs.setId(roomRepository.findAll().get(i).getId());
				rs.setCapacity(roomRepository.findAll().get(i).getCapacity());
				rs.setFloor(roomRepository.findAll().get(i).getFloor());
				rs.setHasBalcony(roomRepository.findAll().get(i).isHasBalcony());
				rs.setActive(roomRepository.findAll().get(i).isActive());
				rs.setReserved(roomRepository.findAll().get(i).isReserved());
				rs.setDay(roomRepository.findAll().get(i).getDay());
				
				response.getRoomslist().add(rs);
			}
			
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllAgentsRequest")
	@ResponsePayload
	public GetAllAgentsResponse getAllAgents(@RequestPayload GetAllAgentsRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		GetAllAgentsResponse response = new GetAllAgentsResponse();
	
		for(int i = 0; i < agentRepository.findAll().size(); i++) {
			
			AgentSoap as = new AgentSoap();
			as.setId(agentRepository.findAll().get(i).getId());
			as.setUsername(agentRepository.findAll().get(i).getUsername());
			as.setPassword(agentRepository.findAll().get(i).getPassword());
			as.setFirstName(agentRepository.findAll().get(i).getFirstName());
			as.setLastName(agentRepository.findAll().get(i).getLastName());
			as.setAddress(agentRepository.findAll().get(i).getAddress());
			as.setMbr(agentRepository.findAll().get(i).getMbr());
			
			response.getAgentslist().add(as);
			
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllCountriesRequest")
	@ResponsePayload
	public GetAllCountriesResponse getAllCountries(@RequestPayload GetAllCountriesRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		GetAllCountriesResponse response = new GetAllCountriesResponse();
	
		for(int i = 0; i < countryRepository.findAll().size(); i++) {
			
			CountrySoap c = new CountrySoap();
			c.setId(countryRepository.findAll().get(i).getId());
			c.setName(countryRepository.findAll().get(i).getName());
			
			response.getCountrieslist().add(c);
			
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllCategoriesRequest")
	@ResponsePayload
	public GetAllCategoriesResponse getAllCategories(@RequestPayload GetAllCategoriesRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		GetAllCategoriesResponse response = new GetAllCategoriesResponse();
	
		for(int i = 0; i < categoryRepository.findAll().size(); i++) {
			
			CategorySoap c = new CategorySoap();
			c.setId(categoryRepository.findAll().get(i).getId());
			c.setName(categoryRepository.findAll().get(i).getName());
			
			response.getCategoriesList().add(c);
			
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllAccomodationTypesRequest")
	@ResponsePayload
	public GetAllAccomodationTypesResponse getAllAccomodationTypes(@RequestPayload GetAllAccomodationTypesRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		GetAllAccomodationTypesResponse response = new GetAllAccomodationTypesResponse();
	
		for(int i = 0; i < typeAccomodationRepository.findAll().size(); i++) {
			
			TypeAccomodationSoap ta = new TypeAccomodationSoap();
			ta.setId(typeAccomodationRepository.findAll().get(i).getId());
			ta.setName(typeAccomodationRepository.findAll().get(i).getName());
			
			
			response.getAccomodationTypesList().add(ta);
			
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegisterRoomRequest")
	@ResponsePayload
	public RegisterRoomResponse registerRoom(@RequestPayload RegisterRoomRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		RegisterRoomResponse response = new RegisterRoomResponse();
		
		RoomSoap r = request.getRoom();
		String accomodationName = accomondationRepository.getOne(request.getAccomodationId()).getName();
		
		Room room = new Room();
		
		room.setCapacity(r.getCapacity());
		room.setFloor(r.getFloor());
		room.setActive(r.isActive());
		room.setHasBalcony(r.isHasBalcony());
		room.setDay(r.getDay());
		room.setReserved(r.isReserved());
		Accomodation accomodation = accomondationRepository.getOne(request.getAccomodationId());
		room.setAccomodation(accomodation);
		
		roomRepository.save(room);
		
		
		response.setRoomId(room.getId());
		response.setResponse("Head back response: 'New room successfully added in accomodation '" + accomodationName + "'.");

		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreatePriceListRequest")
	@ResponsePayload
	public CreatePriceListResponse createPriceList(@RequestPayload CreatePriceListRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		CreatePriceListResponse response = new CreatePriceListResponse();
		
		Long roomId = request.getRoomId();
		Room room = roomRepository.getOne(roomId);
		
		for(PriceSoap ps : request.getPriceList()) {
			
			Price price =  new Price();
			price.setMonth(ps.getMonth());
			price.setPrice(ps.getPrice());
			price.setRoom(room);
			
			priceRepository.save(price);
			
			PriceSoap priceSoapWithId = new PriceSoap();
			priceSoapWithId.setId(price.getId());
			priceSoapWithId.setMonth(price.getMonth());
			priceSoapWithId.setPrice(price.getPrice());
			
			response.getPriceListWithId().add(priceSoapWithId);

		}
		
		room.setActive(true);
		roomRepository.save(room); 
		
		response.setResponse("Head back response: 'Price List is successfully created!'");

		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetRoomPricesRequest")
	@ResponsePayload
	public GetRoomPricesResponse getRoomPrices(@RequestPayload GetRoomPricesRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		GetRoomPricesResponse response = new GetRoomPricesResponse();
		
		Room requestedRoom = roomRepository.getOne(request.getRoomId());
	
		for(int i = 0; i < priceRepository.findAll().size(); i++) {
			
			if(priceRepository.findAll().get(i).getRoom().getId() == request.getRoomId()) {
				
				PriceSoap ps = new PriceSoap();
				ps.setId(priceRepository.findAll().get(i).getId());
				ps.setMonth(priceRepository.findAll().get(i).getMonth());
				ps.setPrice(priceRepository.findAll().get(i).getPrice());
				
				response.getPriceslist().add(ps);
				
			}
			
		}
		
		response.setResponse("Head back response: 'Price list of requested room successfully sent!'");
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteRoomRequest")
	@ResponsePayload
	public DeleteRoomResponse deleteRoom(@RequestPayload DeleteRoomRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		DeleteRoomResponse response = new DeleteRoomResponse();
		
		Long idAccomodation = request.getAccomodationId();
		Long idRoom = request.getRoomId();
		
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
		
		
		
		response.setAccomodationId(idAccomodation);
		response.setDeletedRoomId(idRoom);
		response.setResponse("Head back response: 'Room is successfully deleted!'");
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "EditRoomRequest")
	@ResponsePayload
	public EditRoomResponse editRoom(@RequestPayload EditRoomRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
				
		EditRoomResponse response = new EditRoomResponse();
		
		Long idAccomodation = request.getAccomodationId();
		Long idRoom = request.getRoomId();
		RoomSoap r = request.getEditRoomData();
		
		Room room = roomRepository.getOne(idRoom);
		
		room.setCapacity(r.getCapacity());
		room.setFloor(r.getFloor());
		room.setActive(r.isActive());
		room.setHasBalcony(r.isHasBalcony());
		room.setDay(r.getDay());
		room.setReserved(r.isReserved());
		
		roomRepository.save(room);
	
		r.setId(idRoom);
		response.setAccomodationId(idAccomodation);
		response.setEditedRoom(r);
		response.setResponse("Head back response: 'Room data is successfully edited!'");
		
		return response;
	}
	

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetRoomRequest")
	@ResponsePayload
	public GetRoomResponse getRoom(@RequestPayload GetRoomRequest request) {
		
		GetRoomResponse response = new GetRoomResponse();
		
		Long id = request.getRequestedRoomId();
		
		Room requestedRoom = roomRepository.findOneById(id);
		
		RoomSoap r = new RoomSoap();
		r.setId(requestedRoom.getId());
		r.setCapacity(requestedRoom.getCapacity());
		r.setDay(requestedRoom.getDay());
		r.setFloor(requestedRoom.getFloor());
		r.setActive(requestedRoom.isActive());
		r.setReserved(requestedRoom.isReserved());
		r.setHasBalcony(requestedRoom.isHasBalcony());
		
		response.setReturnedRoom(r);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllReservationsRequest")
	@ResponsePayload
	public GetAllReservationsResponse getAllReservations(@RequestPayload GetAllReservationsRequest request) throws DatatypeConfigurationException {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		GetAllReservationsResponse response = new GetAllReservationsResponse();
		String agentUsername = agentRepository.getOne(request.getAgentId()).getUsername();
		response.setResponse("Head back response: 'Reservations by agent " + agentUsername + " successfully sent");

	
		for(int i = 0; i < reservationRepository.findAll().size(); i++) {
			
			if(reservationRepository.findAll().get(i).getAgent().getId() == request.getAgentId()) {
				
				ReservationSoap r = new ReservationSoap();

				r.setId(reservationRepository.findAll().get(i).getId());
				LocalDate fromDate = reservationRepository.findAll().get(i).getFromDate();
				GregorianCalendar gcalFromDate = GregorianCalendar.from(fromDate.atStartOfDay(ZoneId.systemDefault()));
				XMLGregorianCalendar xcalFromDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalFromDate);
				r.setFromDate(xcalFromDate);
				LocalDate toDate = reservationRepository.findAll().get(i).getToDate();
				GregorianCalendar gcalToDate = GregorianCalendar.from(toDate.atStartOfDay(ZoneId.systemDefault()));
				XMLGregorianCalendar xcalToDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalToDate);
				r.setToDate(xcalToDate);
				RoomSoap roomSoap = new RoomSoap();
				roomSoap.setId(reservationRepository.findAll().get(i).getRoom().getId());
				r.setRoom(roomSoap);
				UserSoap userSoap = new UserSoap();
				userSoap.setId(reservationRepository.findAll().get(i).getUser().getId());
				r.setUser(userSoap);
				AgentSoap agentSoap = new AgentSoap();
				agentSoap.setId(reservationRepository.findAll().get(i).getAgent().getId());
				r.setAgent(agentSoap);
				r.setConfirmed(reservationRepository.findAll().get(i).isConfirmed());
				
				response.getReservationsList().add(r);
				
			}

			
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllUsersRequest")
	@ResponsePayload
	public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest request) {
		
		//Request poruka sa agentskog back-a
		System.out.println("*****");
		System.out.println(request.getRequest());
		System.out.println("*****");
		
		GetAllUsersResponse response = new GetAllUsersResponse();
	
		for(int i = 0; i < userRepository.findAll().size(); i++) {
			
			UserSoap us = new UserSoap();
			us.setId(userRepository.findAll().get(i).getId());
			us.setFirstName(userRepository.findAll().get(i).getFirstName());
			us.setLastName(userRepository.findAll().get(i).getLastName());
			us.setUsername(userRepository.findAll().get(i).getUsername());
			us.setPassword(userRepository.findAll().get(i).getPassword());
			us.setEmail(userRepository.findAll().get(i).getEmail());
			us.setCity(userRepository.findAll().get(i).getCity());
			ClientStatus clientStatus = userRepository.findAll().get(i).getStatus();
			if(clientStatus.equals("AKTIVAN")) {
				us.setClientStatus(com.ftn.webservice.ClientStatus.AKTIVAN);
			} else if (clientStatus.equals("NEAKTIVAN")) {
				us.setClientStatus(com.ftn.webservice.ClientStatus.NEAKTIVAN);
			} else if (clientStatus.equals("BLOKIRAN")) {
				us.setClientStatus(com.ftn.webservice.ClientStatus.BLOKIRAN); 
			} else {
				us.setClientStatus(com.ftn.webservice.ClientStatus.UKLONJEN);
			}
			us.setEnabled(userRepository.findAll().get(i).isEnabled());
			us.setNonLocked(userRepository.findAll().get(i).isNonLocked());
			
			response.getUserslist().add(us);
			
		}
		
		return response;
	}
	
	


}

