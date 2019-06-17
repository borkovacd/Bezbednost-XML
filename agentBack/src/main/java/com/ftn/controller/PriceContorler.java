package com.ftn.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.dto.PriceListDTO;
import com.ftn.service.PriceService;



@RestController
@RequestMapping(value = "/api/pricelist")
public class PriceContorler {
	
	@Autowired
	private PriceService priceService;
	

	@PostMapping("/create-price-createPricelist/{idRoom}")
	public void createPriceList(@RequestBody PriceListDTO priceListDTO, @PathVariable Long idRoom) {
		
		priceService.createPriceList(priceListDTO, idRoom);
	}
	
	@GetMapping("/getPriceForRoom/{idRoom}")
	public ResponseEntity<?> getPriceListForRoom(@PathVariable Long idRoom) {
		//znaci ovde treba da vrati  cenovnik za tu sobu 
		//znaci nesto kao PriceListDTO posto je to da prikaze u tabele cenu za sve mesece
		return null;
	}
	
	
}
