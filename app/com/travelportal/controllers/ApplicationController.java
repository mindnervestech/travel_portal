package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.NoResultException;

import com.travelportal.domain.AdminUser;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelBrands;
import com.travelportal.domain.HotelChain;
import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.rooms.RateWrapper;
import com.travelportal.vm.HotelSignUpVM;
import com.travelportal.vm.RoomtypeVM;

import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class ApplicationController extends Controller{

	@Transactional
	public static Result login() {
		
		return ok(views.html.login.render());
	}
	
	@Transactional
	public static Result adminLogin() {
		final String value = session().get("NAME");
        if (value == null) {
        	return ok(views.html.adminLogin.render());
        }
		
        return ok(views.html.adminHome.render());
		
	}
	
	@Transactional
	public static Result doAdminLogin() {
		DynamicForm form = DynamicForm.form().bindFromRequest();
		try {
			AdminUser adminUser = AdminUser.doLogin(form.get("name"),form.get("pass"));
			
			if(adminUser != null) {
				session().put("NAME", adminUser.getUserName());
				return ok(views.html.adminHome.render());
			}
		
		} catch(NoResultException e) { }
		System.out.println("SESSION VALUE   "+session().get("NAME"));
		return ok(views.html.adminLogin.render());
	}
	
	@Transactional
	public static Result adminLogout() {
		System.out.println("SESSION VALUE   "+session().get("NAME"));
		session().clear();
		return ok(views.html.adminLogin.render());
	}	
	
	@Transactional
	public static Result getSignUpForm() {
		
		List<Country> countries = Country.getCountries();
		List<String> countryList = new ArrayList<>();
		for(Country country: countries) {
			countryList.add(country.getCountryName());
		}
		
		List<HotelChain> hotelChains = HotelChain.getChainHotels();
		List<String> chainList = new ArrayList<>();
		for(HotelChain hotel: hotelChains) {
			chainList.add(hotel.getChainHotelName());
		}
		
		List<HotelBrands> brands = HotelBrands.gethotelbrand();
		List<String> brandList = new ArrayList<>();
		for(HotelBrands hotel: brands) {
			brandList.add(hotel.getBrandName());
		}
		
		List<Currency> currencies = Currency.getCurrency();
		List<String> currencyList = new ArrayList<>();
		for(Currency currency : currencies) {
			currencyList.add(currency.getCurrencyName());
		}
		
		return ok(views.html.signup.render(countryList, chainList, brandList, currencyList));
	}
	
	@Transactional
	public static Result getCityOfCountry() {
		DynamicForm bindedForm = DynamicForm.form().bindFromRequest();
		System.out.println(bindedForm.get("country"));
		String countryName = bindedForm.get("country");
		List<String> cities = new ArrayList<>();
		
			Country country = Country.getCountryByName(countryName);
			cities = City.getCitiesByCountry(country.getCountryCode());
			
		return ok(Json.toJson(cities));
	}
	
	@Transactional
	public static Result doRegister() {
		DateFormat format = new SimpleDateFormat("MMddyyyy");
		Form<HotelSignUpVM> HotelForm = Form.form(HotelSignUpVM.class).bindFromRequest();
		HotelSignUpVM hotelSignUpVM = HotelForm.get();

		HotelRegistration register = new HotelRegistration();
		
		register.setCountry(Country.getCountryByName(hotelSignUpVM.country));
		register.setCity(City.getCitiByName(hotelSignUpVM.city));
		register.setCurrency(Currency.getCurrencyByName(hotelSignUpVM.currency));
		register.setHotelAddress(hotelSignUpVM.hotelAddress);
		
		register.setHotelName(hotelSignUpVM.hotelName);
		register.setLaws(Boolean.parseBoolean(hotelSignUpVM.laws));
		register.setPartOfChain(Boolean.parseBoolean(hotelSignUpVM.partOfChain));
		if(hotelSignUpVM.partOfChain.equals("true")) {
			register.setHotelBrand(hotelSignUpVM.hotelBrand);
			register.setChainHotel(hotelSignUpVM.chainHotel);
		}
		register.setPassword(hotelSignUpVM.password);
		register.setPolicy(hotelSignUpVM.policy);
		register.setStarRating(hotelSignUpVM.starRating);
		register.setSupplierName(hotelSignUpVM.supplierName);
		register.setZipcode(hotelSignUpVM.zipcode);
			Date date = new Date();
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(100);
		register.setSupplierCode(format.format(date).concat(Integer.toString(randomInt)));
		register.setStatus("PENDING");
		
		register.save();
		
		return ok();
	}
	
}
