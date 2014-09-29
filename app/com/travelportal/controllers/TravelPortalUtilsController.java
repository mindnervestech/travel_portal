package com.travelportal.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Splitter;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.HotelChain;
import com.travelportal.vm.HotelGeneralInfoVM;

public class TravelPortalUtilsController extends Controller {
	
	@Transactional
	public static Result getCountries() {
		List<Country> countries = Country.getCountries(); 
		return ok(Json.toJson(countries));
	}
	
	@Transactional
	public static Result getCities(int countryCode) {
		List<City> cities = City.getCities(countryCode);
		return ok(Json.toJson(cities));
	}
	
	public static Result getChainHotels() {
		List<HotelChain> cities = HotelChain.getChainHotels();
		return ok(Json.toJson(cities));
	}
	   
   
   @Transactional
   public static Result getCityname(int countryCode){
   	List<City> listcity = City.getCities(countryCode); 
   	List<Map> list = new ArrayList<>();
   	
   		for(City city : listcity){
   			Map<String,Object> map = new HashMap<String, Object>();
   			map.put("cityname", city);
   			list.add(map);
   		}
   		return ok(Json.toJson(list));
      
   }
	
   
   @Transactional
	public static Result saveGeneralInfo() {
	   
	   
	 /*  System.out.println("//////////8888////////");
	   System.out.println("hiiiiiiiiiiiiiiii");
	   System.out.println("//////////////////");
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		System.out.println(form);
		Json.fromJson(json, HotelGeneralInfoVM.class);
		HotelGeneralInfoVM hotelGeneralInfoVM = Json.fromJson(json, HotelGeneralInfoVM.class);
				
		
		newspaperdetails.save();*/
		return ok();
	}
	
	
}
