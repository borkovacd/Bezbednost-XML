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

import com.ftn.dto.PriceListDTO;
import com.ftn.model.Accomodation;



@RestController
@RequestMapping(value = "/api/pricelist")
public class PriceContorler {
	
	

	@PostMapping("/create-price-createPricelist/{idRoom}")
	public void createPriceList(@RequestBody PriceListDTO priceListDTO, @PathVariable Long idRoom) {
		//znaci ovde je za kreiranje cenvnika
		//posto sam napravila da on inicijalno unese odjednom sve mesece ,sa fronta ti stize lista objekata
		//svaki objekat ti je PriceDTO koji sadrzi cenu i mesec  na koji se odnosi cena
		//otprilke preko ovog fora mozes da izvlacis taj model cena po mesecima
		//OBAVEZNO : za sobu koju sam poslala preko idRoom SETOVATI polje ACTIVE= TRUE sto znaci da sad postoje cene i da je aktivna za prikazivanje klijentu
		
		/*for (PriceDTO priceDTO : priceListDTO.getPrices()) {

			Price price = new Price();
			price.setAccomodation(accomodation);
			price.setMonth(priceDTO.getMonth());
			price.setSum(priceDTO.getSum());

			priceRepository.save(price);*/
	}
	
	@GetMapping("/getPriceForRoom/{idRoom}")
	public ResponseEntity<?> getPriceListForRoom(@PathVariable Long idRoom) {
		//znaci ovde treba da vrati  cenovnik za tu sobu 
		//znaci nesto kao PriceListDTO posto je to da prikaze u tabele cenu za sve mesece
		return null;
	}
	
	
}
