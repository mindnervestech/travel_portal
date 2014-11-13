package com.travelportal.controllers;

import java.util.ArrayList;
import java.util.List;

import com.travelportal.domain.HotelRegistration;
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
