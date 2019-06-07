package com.ftn.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.dto.AccomodationDTO;
import com.ftn.model.Accomodation;
import com.ftn.model.Category;
import com.ftn.model.City;
import com.ftn.model.TypeAccomodation;
import com.ftn.repository.AccomodationRepository;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.RegisterAccomodationRequest;
import com.ftn.webservice.files.RegisterAccomodationResponse;

@Service
public class AccomodationService {
	@Autowired
	private AccomodationRepository accomodationRepository;
	@Autowired
	private SOAPConnector soapConnector;

	public Accomodation create(AccomodationDTO accDTO) {
		RegisterAccomodationRequest request = new RegisterAccomodationRequest();

		Accomodation a = new Accomodation();

		a.setName(accDTO.getName());
		City city = new City();
		city.setName(accDTO.getCity());
		a.setCity(city);
		a.setAddress(accDTO.getAddress());
		TypeAccomodation typeAccomodation = new TypeAccomodation();
		typeAccomodation.setName(accDTO.getType());
		a.setTypeAccomodation(typeAccomodation);
		Category category = new Category();
		category.setName(accDTO.getCategory());
		a.setCategory(category);
		a.setDescription(accDTO.getDescription());
		// a.setCapacity(accDTO.getCapacity());
		a.setPic(accDTO.getImage());

		// OVO SAM ZAKOMENTARISALA
		// request.setAccomondation(a);

		RegisterAccomodationResponse response = (RegisterAccomodationResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);

		System.out.println("Odogovor : " + response.getResponse());
		return a;

	}

	public Accomodation delete(Long id) {
		Accomodation accomodation = accomodationRepository.findOneById(id);

		// Ovde treba da se obrise sve sto je vezano za taj smestaj

		return accomodation;

	}

	public Accomodation edit(Long idAgent, Long id, AccomodationDTO accDTO) {
		Accomodation accomodation = accomodationRepository.findOneById(id);

		return accomodation;

	}

	public ArrayList<Accomodation> getAllAccomodation() {

		return (ArrayList<Accomodation>) accomodationRepository.findAll();
	}

	public Accomodation getAccomodation(Long id) {
		return accomodationRepository.findOneById(id);
	}

}
