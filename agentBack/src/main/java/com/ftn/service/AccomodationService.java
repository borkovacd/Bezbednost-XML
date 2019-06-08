package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dto.AccomodationDTO;
import com.ftn.model.Accomodation;
import com.ftn.repository.AccomodationRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.AccomodationSoap;
import com.ftn.webservice.files.AgentSoap;
import com.ftn.webservice.files.CategorySoap;
import com.ftn.webservice.files.CitySoap;
import com.ftn.webservice.files.DeleteAccomodationRequest;
import com.ftn.webservice.files.DeleteAccomodationResponse;
import com.ftn.webservice.files.GetAllAccomodationsRequest;
import com.ftn.webservice.files.GetAllAccomodationsResponse;
import com.ftn.webservice.files.RegisterAccomodationRequest;
import com.ftn.webservice.files.RegisterAccomodationResponse;
import com.ftn.webservice.files.TypeAccomodationSoap;

@Service
public class AccomodationService {
	
	@Autowired
	private AccomodationRepository accomodationRepository;
	
	@Autowired
	private SOAPConnector soapConnector;

	//Zasto bismo ovde vracali Accomodation? Zasto ne neki String odgovor ili nesto slicno zvanicnije?
	public AccomodationSoap create(AccomodationDTO accDTO) {
		
		RegisterAccomodationRequest request = new RegisterAccomodationRequest();

		AccomodationSoap a = new AccomodationSoap();

		a.setName(accDTO.getName());
		CitySoap city = new CitySoap();
		city.setName(accDTO.getCity());
		a.setCity(city);
		a.setAddress(accDTO.getAddress());
		TypeAccomodationSoap typeAccomodation = new TypeAccomodationSoap();
		typeAccomodation.setName(accDTO.getType());
		a.setTypeAccomodation(typeAccomodation);
		CategorySoap category = new CategorySoap();
		category.setName(accDTO.getCategory());
		a.setCategory(category);
		a.setDescription(accDTO.getDescription());
		// a.setCapacity(accDTO.getCapacity());
		a.setPic(accDTO.getImage());
		AgentSoap agent = new AgentSoap();
		//Zasto DTO nema polje za agenta?
		//agent.setUsername(accDTO.getA);
		//a.setAgent(value);

		
		request.setAccomondation(a);

		RegisterAccomodationResponse response = (RegisterAccomodationResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);

		System.out.println("Odogovor : " + response.getResponse());
		
		Accomodation accomodation = new Accomodation();
		
		
		accomodation.setId(response.getResponse());
		accomodation.setName(a.getName());
		accomodation.setAddress(a.getAddress());
		
		accomodationRepository.save(accomodation);
		
		return a;

	}

	public Accomodation delete(Long id) {
		
		DeleteAccomodationRequest request = new DeleteAccomodationRequest();
		request.setDeleteAccomodationId(id);
		
		DeleteAccomodationResponse response = (DeleteAccomodationResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		System.out.println("Odogovor : " + response.getDeletedAccomodationId());
		
		Accomodation accomodation = accomodationRepository.findOneById(response.getDeletedAccomodationId());
		accomodationRepository.delete(accomodation);
		
		// Ovde treba da se obrise sve sto je vezano za taj smestaj

		return accomodation;

	}

	public Accomodation edit(Long idAgent, Long id, AccomodationDTO accDTO) {
		Accomodation accomodation = accomodationRepository.findOneById(id);

		return accomodation;

	}

	public ArrayList<Accomodation> getAllAccomodation() {
		
		GetAllAccomodationsRequest request = new GetAllAccomodationsRequest();
		request.setGetAccomodationsList("GetAllAccomodations");
		
		GetAllAccomodationsResponse response = (GetAllAccomodationsResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<Accomodation> accomodations = new ArrayList<Accomodation>();
		
		for(int i = 0; i < response.getAccomodationsList().size(); i++) {
			
			Accomodation a = new Accomodation();
			a.setId(response.getAccomodationsList().get(i).getId());
			a.setName(response.getAccomodationsList().get(i).getName());
			a.setAddress(response.getAccomodationsList().get(i).getAddress());
			
			accomodations.add(a);
			
		}
		

		return (ArrayList<Accomodation>) accomodations;
	}

	public Accomodation getAccomodation(Long id) {
		return accomodationRepository.findOneById(id);
	}

}
