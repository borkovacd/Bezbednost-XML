package com.ftn.repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import com.ftn.model.User;
import com.ftn.webservice_accomondation.Accomodation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AccomondationRepository {
	
	private static final Map<String, Accomodation> accomodations = new HashMap<>();

	@PostConstruct
	public void initData() {
		Accomodation a1 = new Accomodation();
		a1.setName("Hotel Vojvodina");
		a1.setAddress("Zmaj Jovina 1");

		accomodations.put(a1.getName(), a1);

		Accomodation a2 = new Accomodation();
		a2.setName("Hotel Borkovac");
		a2.setAddress("Orloviceva 11");

		accomodations.put(a2.getName(), a2);
	}

	public Accomodation findAccomodation(String name) {
		Assert.notNull(name, "The accomodations's name must not be null");
		return accomodations.get(name);
	}

}

/*@Component
public class CountryRepository {
	private static final Map<String, Country> countries = new HashMap<>();

	@PostConstruct
	public void initData() {
		Country spain = new Country();
		spain.setName("Spain");
		spain.setCapital("Madrid");
		spain.setCurrency(Currency.EUR);
		spain.setPopulation(46704314);

		countries.put(spain.getName(), spain);

		Country poland = new Country();
		poland.setName("Poland");
		poland.setCapital("Warsaw");
		poland.setCurrency(Currency.PLN);
		poland.setPopulation(38186860);

		countries.put(poland.getName(), poland);

		Country uk = new Country();
		uk.setName("United Kingdom");
		uk.setCapital("London");
		uk.setCurrency(Currency.GBP);
		uk.setPopulation(63705000);

		countries.put(uk.getName(), uk);
	}

	public Country findCountry(String name) {
		Assert.notNull(name, "The country's name must not be null");
		return countries.get(name);
	}
}*/