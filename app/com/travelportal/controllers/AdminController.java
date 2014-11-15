package com.travelportal.controllers;

import java.util.ArrayList;
import java.util.List;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelBrands;
import com.travelportal.domain.HotelChain;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.MarketPolicyTypes;
import com.travelportal.vm.HotelRegistrationVM;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class AdminController extends Controller {

	@Transactional
	public static Result getPendingUsers() {
		List<HotelRegistration> list = HotelRegistration.getAllPendingUsers();
		List<HotelRegistrationVM> vm = new ArrayList<>();
		for(HotelRegistration hotel : list) {
			HotelRegistrationVM hotelRegistrationVM = new HotelRegistrationVM(hotel);
			vm.add(hotelRegistrationVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	@Transactional
	public static Result getApprovedUsers() {
		List<HotelRegistration> list = HotelRegistration.getAllApprovedUsers();
		List<HotelRegistrationVM> vm = new ArrayList<>();
		for(HotelRegistration hotel : list) {
			HotelRegistrationVM hotelRegistrationVM = new HotelRegistrationVM(hotel);
			vm.add(hotelRegistrationVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	@Transactional
	public static Result getRejectedUsers() {
		List<HotelRegistration> list = HotelRegistration.getAllRejectedUsers();
		List<HotelRegistrationVM> vm = new ArrayList<>();
		for(HotelRegistration hotel : list) {
			HotelRegistrationVM hotelRegistrationVM = new HotelRegistrationVM(hotel);
			vm.add(hotelRegistrationVM);
		}
		
		return ok(Json.toJson(vm));
	}
	
	@Transactional
	public static Result approveUser(Long id) {
		HotelRegistration register = HotelRegistration.findById(id);
		register.setStatus("APPROVED");
		register.merge();
		HotelProfile hotelProfile = new HotelProfile();
		
		hotelProfile.setSupplier_code(Long.parseLong(register.getSupplierCode()));
		hotelProfile.setHotelName(register.getHotelName());
		hotelProfile.setSupplierName(register.getSupplierName());
		hotelProfile.setAddress(register.getHotelAddress());
		hotelProfile.setCountry(register.getCountry());
		hotelProfile.setCurrency(register.getCurrency());
		hotelProfile.setCity(register.getCity());
		if(register.isPartOfChain()) {
			hotelProfile.setPartOfChain("true");
			hotelProfile.setHoteBrands(HotelBrands.getHotelBrandsByName(register.getHotelBrand()));
			hotelProfile.setChainHotel(HotelChain.getHotelChainByName(register.getChainHotel()));
		} else {
			hotelProfile.setPartOfChain("false");
		}
		
		hotelProfile.setHotelEmailAddr(register.getEmail());
		hotelProfile.setMarketPolicyType(MarketPolicyTypes.getMarketPolicyByName(register.getPolicy()));
		hotelProfile.setPassword(register.getPassword());
		hotelProfile.setStartRatings(HotelStarRatings.getHotelRatingsByName(register.getStarRating()));
		hotelProfile.setLaws(register.isLaws());
		hotelProfile.setZipCode(register.getZipcode());
		hotelProfile.save();
		return ok();
	}
	
	@Transactional
	public static Result rejectUser(Long id) {
		HotelRegistration register = HotelRegistration.findById(id);
		register.setStatus("REJECTED");
		register.merge();
		return ok();
	}
	
	@Transactional
	public static Result pendingUser(Long id) {
		HotelRegistration register = HotelRegistration.findById(id);
		register.setStatus("PENDING");
		register.merge();
		return ok();
	}
	
}
