package com.travelportal.controllers;

import java.util.List;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelBrands;
import com.travelportal.domain.HotelChain;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.Location;
import com.travelportal.domain.MarketPolicyTypes;
import com.travelportal.domain.NightLife;
import com.travelportal.domain.ShoppingFacility;
import com.travelportal.vm.HotelGeneralInfoVM;
import views.html.hotel_profile;

public class TravelPortalUtilsController extends Controller {

	
public static Result hotel_profile() {
		
		
		HotelGeneralInfoVM hotelGeneralInfoVM=new HotelGeneralInfoVM();
	hotelGeneralInfoVM.setSupplierCode(new Long(0));
        return ok(hotel_profile.render("Your new application is ready.",hotelGeneralInfoVM));
    }
	
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

	@Transactional
	public static Result getChainHotels() {
		List<HotelChain> chainHotels = HotelChain.getChainHotels();
		return ok(Json.toJson(chainHotels));
	}

	@Transactional
	public static Result getcurrency() {
		List<Currency> currency = Currency.getCurrency();
		return ok(Json.toJson(currency));
	}
	
	@Transactional
	public static Result gethotelbrand() {
		List<HotelBrands> hotelbrands = HotelBrands.gethotelbrand();
		return ok(Json.toJson(hotelbrands));
	}
   
	
	@Transactional
	public static Result getlocations() {
		List<Location> location = Location.getLocation();
		return ok(Json.toJson(location));
	}
	
	
	@Transactional
	public static Result getshoppingfacility() {
		List<ShoppingFacility> shoppingfacility = ShoppingFacility.gethotelStarratings();
		return ok(Json.toJson(shoppingfacility));
	}
	
	
	@Transactional
	public static Result getnightlife() {
		List<NightLife> nightlife = NightLife.getNightLife();
		return ok(Json.toJson(nightlife));
	}
	
	
	@Transactional
	public static Result getservices() {
		List<HotelServices> hotelservice = HotelServices.gethotelservice();
		return ok(Json.toJson(hotelservice));
	}
	
	@Transactional
	public static Result getstarrating() {
		List<HotelStarRatings> hotelstarratings = HotelStarRatings.gethotelStarratings();
		return ok(Json.toJson(hotelstarratings));
	}
	
	
	@Transactional
	public static Result getmarketrate() {
		List<MarketPolicyTypes> marketpolicytypes = MarketPolicyTypes.getMarketPolicyTypes();
		return ok(Json.toJson(marketpolicytypes));
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
