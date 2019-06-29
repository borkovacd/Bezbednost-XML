package com.ftn.micro3.dto;

import java.util.ArrayList;
import java.util.List;

import com.ftn.micro3.model.Room;

public class AdvancedSearchDTO 
{
	public boolean tipHotel; 
	public boolean tipApartman ;
	public boolean tipBadAndBreakfast ;
	
	public boolean kategorija1 ;
	public boolean kategorija2 ;
	public boolean kategorija3 ;
	public boolean kategorija4 ;
	public boolean kategorija5 ;
	public boolean nekategorisan ;
	
	public boolean parking ;
	public boolean wifi ;
	public boolean dorucak ;
	public boolean poluPansion ;
	public boolean pansion ;
	public boolean allInclusive ;
	public boolean petFriendly ;
	public boolean tv ;
	public boolean miniKuhinja ;
	public boolean kupatilo ;
	public boolean bespaltnoOtkazivanje ;
	
	public List<Room> rooms = new ArrayList<Room>();

	public AdvancedSearchDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdvancedSearchDTO(boolean tipHotel, boolean tipApartman, boolean tipBadAndBreakfast, boolean kategorija1,
			boolean kategorija2, boolean kategorija3, boolean kategorija4, boolean kategorija5, boolean nekategorisan,
			boolean parking, boolean wifi, boolean dorucak, boolean poluPansion, boolean pansion, boolean allInclusive,
			boolean petFriendly, boolean tv, boolean miniKuhinja, boolean kupatilo, boolean bespaltnoOtkazivanje) {
		super();
		this.tipHotel = tipHotel;
		this.tipApartman = tipApartman;
		this.tipBadAndBreakfast = tipBadAndBreakfast;
		this.kategorija1 = kategorija1;
		this.kategorija2 = kategorija2;
		this.kategorija3 = kategorija3;
		this.kategorija4 = kategorija4;
		this.kategorija5 = kategorija5;
		this.nekategorisan = nekategorisan;
		this.parking = parking;
		this.wifi = wifi;
		this.dorucak = dorucak;
		this.poluPansion = poluPansion;
		this.pansion = pansion;
		this.allInclusive = allInclusive;
		this.petFriendly = petFriendly;
		this.tv = tv;
		this.miniKuhinja = miniKuhinja;
		this.kupatilo = kupatilo;
		this.bespaltnoOtkazivanje = bespaltnoOtkazivanje;
	}

	public boolean isTipHotel() {
		return tipHotel;
	}

	public void setTipHotel(boolean tipHotel) {
		this.tipHotel = tipHotel;
	}

	public boolean isTipApartman() {
		return tipApartman;
	}

	public void setTipApartman(boolean tipApartman) {
		this.tipApartman = tipApartman;
	}

	public boolean isTipBadAndBreakfast() {
		return tipBadAndBreakfast;
	}

	public void setTipBadAndBreakfast(boolean tipBadAndBreakfast) {
		this.tipBadAndBreakfast = tipBadAndBreakfast;
	}

	public boolean isKategorija1() {
		return kategorija1;
	}

	public void setKategorija1(boolean kategorija1) {
		this.kategorija1 = kategorija1;
	}

	public boolean isKategorija2() {
		return kategorija2;
	}

	public void setKategorija2(boolean kategorija2) {
		this.kategorija2 = kategorija2;
	}

	public boolean isKategorija3() {
		return kategorija3;
	}

	public void setKategorija3(boolean kategorija3) {
		this.kategorija3 = kategorija3;
	}

	public boolean isKategorija4() {
		return kategorija4;
	}

	public void setKategorija4(boolean kategorija4) {
		this.kategorija4 = kategorija4;
	}

	public boolean isKategorija5() {
		return kategorija5;
	}

	public void setKategorija5(boolean kategorija5) {
		this.kategorija5 = kategorija5;
	}

	public boolean isNekategorisan() {
		return nekategorisan;
	}

	public void setNekategorisan(boolean nekategorisan) {
		this.nekategorisan = nekategorisan;
	}

	public boolean isParking() {
		return parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public boolean isDorucak() {
		return dorucak;
	}

	public void setDorucak(boolean dorucak) {
		this.dorucak = dorucak;
	}

	public boolean isPoluPansion() {
		return poluPansion;
	}

	public void setPoluPansion(boolean poluPansion) {
		this.poluPansion = poluPansion;
	}

	public boolean isPansion() {
		return pansion;
	}

	public void setPansion(boolean pansion) {
		this.pansion = pansion;
	}

	public boolean isAllInclusive() {
		return allInclusive;
	}

	public void setAllInclusive(boolean allInclusive) {
		this.allInclusive = allInclusive;
	}

	public boolean isPetFriendly() {
		return petFriendly;
	}

	public void setPetFriendly(boolean petFriendly) {
		this.petFriendly = petFriendly;
	}

	public boolean isTv() {
		return tv;
	}

	public void setTv(boolean tv) {
		this.tv = tv;
	}

	public boolean isMiniKuhinja() {
		return miniKuhinja;
	}

	public void setMiniKuhinja(boolean miniKuhinja) {
		this.miniKuhinja = miniKuhinja;
	}

	public boolean isKupatilo() {
		return kupatilo;
	}

	public void setKupatilo(boolean kupatilo) {
		this.kupatilo = kupatilo;
	}

	public boolean isBespaltnoOtkazivanje() {
		return bespaltnoOtkazivanje;
	}

	public void setBespaltnoOtkazivanje(boolean bespaltnoOtkazivanje) {
		this.bespaltnoOtkazivanje = bespaltnoOtkazivanje;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	
}
