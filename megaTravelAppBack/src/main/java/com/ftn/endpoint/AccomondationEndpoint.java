package com.ftn.endpoint;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ftn.model.Accomodation;
import com.ftn.model.Category;
import com.ftn.model.City;
import com.ftn.model.Country;
import com.ftn.model.Room;
import com.ftn.repository.AccomondationRepository;
import com.ftn.repository.AdditionalServicesRepository;
import com.ftn.repository.CategoryRepository;
import com.ftn.repository.CityRepository;
import com.ftn.repository.CountryRepository;
import com.ftn.repository.RoomRepository;
import com.ftn.webservice.AccomodationSoap;
import com.ftn.webservice.AdditionalServicesSoap;
import com.ftn.webservice.CitySoap;
import com.ftn.webservice.CountrySoap;
import com.ftn.webservice.DeleteAccomodationRequest;
import com.ftn.webservice.DeleteAccomodationResponse;
import com.ftn.webservice.EditAccomodationRequest;
import com.ftn.webservice.EditAccomodationResponse;
import com.ftn.webservice.GetAccomodationRequest;
import com.ftn.webservice.GetAccomodationResponse;
import com.ftn.webservice.GetAccomodationRoomsRequest;
import com.ftn.webservice.GetAccomodationRoomsResponse;
import com.ftn.webservice.GetAllAccomodationsRequest;
import com.ftn.webservice.GetAllAccomodationsResponse;
import com.ftn.webservice.GetAllAdditionalServicesRequest;
import com.ftn.webservice.GetAllAdditionalServicesResponse;
import com.ftn.webservice.GetAllCitiesRequest;
import com.ftn.webservice.GetAllCitiesResponse;
import com.ftn.webservice.RegisterAccomodationRequest;
import com.ftn.webservice.RegisterAccomodationResponse;
import com.ftn.webservice.RoomSoap;

import net.bytebuddy.asm.Advice.This;




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
	
	@Autowired
	public AccomondationEndpoint(AccomondationRepository accomondationRepository, AdditionalServicesRepository additionalServicesRepository, 
			CityRepository cityRepository, CategoryRepository categoryRepository, CountryRepository countryRepository, RoomRepository roomRepository) {
		this.accomondationRepository = accomondationRepository;
		this.additionalServicesRepository = additionalServicesRepository;
		this.cityRepository = cityRepository;
		this.categoryRepository = categoryRepository;
		this.countryRepository = countryRepository;
		this.roomRepository = roomRepository;
	}
	
	
	
	//@PayloadRoot is then used by Spring WS to pick the handler method based on the message’s 
	//namespace and localPart.
	//@RequestPayload indicates that the incoming message will be mapped to the method’s request parameter.
	//The @ResponsePayload annotation makes Spring WS map the returned value to the response payload.
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegisterAccomodationRequest")
	@ResponsePayload
	public RegisterAccomodationResponse getAccomondation(@RequestPayload RegisterAccomodationRequest request) {
		
		RegisterAccomodationResponse response = new RegisterAccomodationResponse();
		
		AccomodationSoap a = request.getAccomondation();
		
		Accomodation newAccomodation = new Accomodation();
		
		newAccomodation.setName(a.getName());
		
		City newCity = new City();
		newCity.setName(a.getCity().getName());
		cityRepository.save(newCity);
		newAccomodation.setCity(newCity);
		
		newAccomodation.setAddress(a.getAddress());
		newAccomodation.setDescription(a.getDescription());
		
		Category newCategory = new Category();
		newCategory.setName(a.getCategory().getName());
		categoryRepository.save(newCategory);
		
		accomondationRepository.save(newAccomodation);
		
		
		response.setResponse(newAccomodation.getId());

		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteAccomodationRequest")
	@ResponsePayload
	public DeleteAccomodationResponse deleteAccomodation(@RequestPayload DeleteAccomodationRequest request) {
		
		DeleteAccomodationResponse response = new DeleteAccomodationResponse();
		
		Long id = request.getDeleteAccomodationId();
		
		Accomodation a = accomondationRepository.findOneById(id);
		
		response.setDeletedAccomodationId(a.getId());
		
		accomondationRepository.delete(a);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "EditAccomodationRequest")
	@ResponsePayload
	public EditAccomodationResponse editAccomodation(@RequestPayload EditAccomodationRequest request) {
		
		EditAccomodationResponse response = new EditAccomodationResponse();
		
		Long id = request.getEditAccomodationId();
		AccomodationSoap newAccomodation = request.getEditAccomodationData();
		
		Accomodation a = accomondationRepository.findOneById(id);
		a.setName(newAccomodation.getName());
		a.setAddress(newAccomodation.getAddress());
		
		accomondationRepository.save(a);
	
		newAccomodation.setId(id);
		response.setEditedAccomodation(newAccomodation);
		
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
		
		response.setReturnedAccomodation(a);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllAccomodationsRequest")
	@ResponsePayload
	public GetAllAccomodationsResponse getAllAccomodations(@RequestPayload GetAllAccomodationsRequest request) {
		
		GetAllAccomodationsResponse response = new GetAllAccomodationsResponse();
	
		for(int i = 0; i < accomondationRepository.findAll().size(); i++) {
			
			AccomodationSoap a = new AccomodationSoap();
			a.setId(accomondationRepository.findAll().get(i).getId());
			a.setName(accomondationRepository.findAll().get(i).getName());
			a.setAddress(accomondationRepository.findAll().get(i).getAddress());
			
			response.getAccomodationsList().add(a);
			
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllAdditionalServicesRequest")
	@ResponsePayload
	public GetAllAdditionalServicesResponse getAllAdditionalServices(@RequestPayload GetAllAdditionalServicesRequest request) {
		
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
			
			//Prinudno cuvanje Country
			//dok se ne odradi prosledjivanje sa admina na agenta
			Country newCountry = new Country();
			newCountry.setName(cityRepository.findAll().get(i).getCountry().getName());
			countryRepository.save(newCountry); //!
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
	
		for(int i = 0; i < requestedAccomodation.getRooms().size(); i++) {
			
			RoomSoap rs = new RoomSoap();
			rs.setId(requestedAccomodation.getRooms().get(i).getId());
			rs.setCapacity(requestedAccomodation.getRooms().get(i).getCapacity());
			rs.setFloor(requestedAccomodation.getRooms().get(i).getFloor());
			rs.setHasBalcony(requestedAccomodation.getRooms().get(i).isHasBalcony());
			rs.setActive(requestedAccomodation.getRooms().get(i).isActive());
			
			response.getRoomslist().add(rs);
			
		}
		
		return response;
	}


}

