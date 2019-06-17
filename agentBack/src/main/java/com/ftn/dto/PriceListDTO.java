package com.ftn.dto;

import java.util.List;

public class PriceListDTO {

	
	private List<PriceDTO> prices;
	
	public PriceListDTO() {
		// TODO Auto-generated constructor stub
	}

	public PriceListDTO(List<PriceDTO> prices) {
		super();
		this.prices = prices;
	}

	public List<PriceDTO> getPrices() {
		return prices;
	}

	public void setPrices(List<PriceDTO> prices) {
		this.prices = prices;
	}
	
	
}
