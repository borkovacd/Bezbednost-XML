package com.ftn.repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ftn.model.Accomodation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccomondationRepository extends JpaRepository<Accomodation, Long>{


	/*private static final Map<String, Accomodation> accomodations = new HashMap<>();

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
	}*/

}

