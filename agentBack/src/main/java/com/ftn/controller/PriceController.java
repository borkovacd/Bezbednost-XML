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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.dto.PriceListDTO;
import com.ftn.model.Price;
import com.ftn.service.PriceService;



@RestController
@RequestMapping(value = "/api/pricelist")
public class PriceController {
	private static final Logger log = LoggerFactory.getLogger(PriceController.class);

	
	@Autowired
	private PriceService priceService;
	


	@PreAuthorize("hasAuthority('ADD_PRICE')")
	@PostMapping("/create-price-createPricelist/{idRoom}/{token}")
	public void createPriceList(@RequestBody PriceListDTO priceListDTO, @PathVariable Long idRoom,@PathVariable String token) throws Exception {
	
		priceService.createPriceList(priceListDTO, idRoom,token);
	}
	
	@PreAuthorize("hasAuthority('ADD_PRICE')")
	@GetMapping("/getPriceForRoom/{idRoom}/{token}")
	public ResponseEntity<ArrayList<Price>> getPriceListForRoom(@PathVariable Long idRoom,@PathVariable String token) throws Exception {
		ArrayList<Price> prices = priceService.getAllPrices(idRoom,token);

		return new ResponseEntity<>(prices, HttpStatus.OK);
	}
	
	
}
