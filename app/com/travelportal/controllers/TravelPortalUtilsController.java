package com.travelportal.controllers;

import java.util.List;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.HotelChain;

public class TravelPortalUtilsController extends Controller {
	
	@Transactional
	public static Result getCountries() {
		List<Country> countries = Country.getCountries(); 
		return ok(Json.toJson(countries));
	}
	
	public static Result getCities(int countryCode) {
		List<City> cities = City.getCities(countryCode);
		return ok(Json.toJson(cities));
	}
	
	public static Result getChainHotels() {
		List<HotelChain> cities = HotelChain.getChainHotels();
		return ok(Json.toJson(cities));
	}
	
	
	
}
