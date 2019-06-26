package com.ftn.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.dto.PriceDTO;
import com.ftn.dto.PriceListDTO;
import com.ftn.model.Agent;
import com.ftn.model.Price;
import com.ftn.model.Room;
import com.ftn.repository.AgentRepository;
import com.ftn.repository.PriceRepository;
import com.ftn.repository.RoomRepository;
import com.ftn.security.TokenUtils;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.CreatePriceListRequest;
import com.ftn.webservice.files.CreatePriceListResponse;
import com.ftn.webservice.files.GetRoomPricesRequest;
import com.ftn.webservice.files.GetRoomPricesResponse;
import com.ftn.webservice.files.PriceSoap;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
	private static final Logger log = LoggerFactory.getLogger(PriceService.class);

	@Autowired
	private PriceRepository priceRepository;
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	TokenUtils tokenUtils;
	

	public void createPriceList(PriceListDTO priceListDTO, Long idRoom,String token) throws Exception {
		  String usname = tokenUtils.getUserSecurity(token).getUsername();
			
			Agent ag = agentRepository.findOneByUsername(usname);
			log.info("User id: "+ag.getId()+"  CREAPRICL");
		CreatePriceListRequest request = new CreatePriceListRequest();
		request.setRequest("Agent request: 'Create price list.'");
		request.setRoomId(idRoom);
		
		Room room = roomRepository.getOne(idRoom);
		
		for(PriceDTO priceDTO : priceListDTO.getPrices()) {
			
			PriceSoap p = new PriceSoap();
			p.setMonth(priceDTO.getMonth());
			p.setPrice(priceDTO.getSum());
			
			request.getPriceList().add(p);
		}
			

		CreatePriceListResponse response = (CreatePriceListResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);

		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		
		for(PriceSoap priceSoap : response.getPriceListWithId()) {
			
			Price price = new Price();
			price.setId(priceSoap.getId());
			price.setMonth(priceSoap.getMonth());
			price.setPrice(priceSoap.getPrice());
			price.setRoom(room);
			
			priceRepository.save(price);
		}
		
		room.setActive(true);
		roomRepository.save(room); 
		log.info("User id: "+ag.getId()+"  CREAPRICLSCCESS");

			
	}


	public ArrayList<Price> getAllPrices(Long idRoom,String token) throws Exception {
		  String usname = tokenUtils.getUserSecurity(token).getUsername();
			
			Agent ag = agentRepository.findOneByUsername(usname);
			log.info("User id: "+ag.getId()+"  GETAPRICL");
		GetRoomPricesRequest request = new GetRoomPricesRequest();
		request.setRequest("Agent request: 'Get all prices for requested room.'");
		request.setRoomId(idRoom);
		
		GetRoomPricesResponse response = (GetRoomPricesResponse) soapConnector
				.callWebService("https://localhost:8443/ws/accomondation", request);
		
		List<Price> prices = new ArrayList<Price>();
		
		//Response poruka sa glavnog back-a
		System.out.println("*****");
		System.out.println(response.getResponse());
		System.out.println("*****");
		
		
		Room room = roomRepository.getOne(idRoom);
		
		for(int i = 0; i < response.getPriceslist().size(); i++) {
			
			Price p = new Price();
			p.setId(response.getPriceslist().get(i).getId());
			p.setMonth(response.getPriceslist().get(i).getMonth());
			p.setPrice(response.getPriceslist().get(i).getPrice());
			p.setRoom(room);
			
			prices.add(p);
			priceRepository.save(p);
			
		}
		log.info("User id: " + ag.getId() + "  GETAPRICLSUCCESS");


		return (ArrayList<Price>) prices;
	}



}
