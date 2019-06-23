package com.ftn.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.dto.PriceListDTO;
import com.ftn.model.Price;
import com.ftn.service.PriceService;



@RestController
@RequestMapping(value = "/api/pricelist")
public class PriceController {
	
	@Autowired
	private PriceService priceService;
	


	@PreAuthorize("hasAuthority('ADD_PRICE')")
	@PostMapping("/create-price-createPricelist/{idRoom}")
	public void createPriceList(@RequestBody PriceListDTO priceListDTO, @PathVariable Long idRoom) {
		
		priceService.createPriceList(priceListDTO, idRoom);
	}
	
	@PreAuthorize("hasAuthority('ADD_PRICE')")
	@GetMapping("/getPriceForRoom/{idRoom}")
	public ResponseEntity<ArrayList<Price>> getPriceListForRoom(@PathVariable Long idRoom) {
		
		ArrayList<Price> prices = priceService.getAllPrices(idRoom);

		return new ResponseEntity<>(prices, HttpStatus.OK);
	}
	
	
}
