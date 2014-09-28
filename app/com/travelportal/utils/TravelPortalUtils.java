package com.travelportal.utils;

import java.util.List;

import play.db.jpa.Transactional;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;

public class TravelPortalUtils {
	@Transactional
	public List<Country> getCountries() {
		return null;
	}
	
	public List<City> getCities(int countryCode) {
		return null;
	}
	
}
