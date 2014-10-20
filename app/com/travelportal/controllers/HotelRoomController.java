package com.travelportal.controllers;

import java.util.List;

import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.MealType;
import com.travelportal.domain.rooms.ChildPolicies;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.domain.rooms.RoomChildPolicies;
import com.travelportal.vm.ChildpoliciVM;
import com.travelportal.vm.HotelmealVM;
import com.travelportal.vm.RoomChildpoliciVM;
import com.travelportal.vm.RoomType;
import com.travelportal.vm.RoomtypeVM;

public class HotelRoomController extends Controller {
	
	@Transactional(readOnly=true)
    public static Result hotelRoomHome() {
        return ok(index.render("Your new application is ready."));
    }
	
	@Transactional(readOnly=true)
    public static Result fetchAllRoomTypesForEdit() {
		//this is used when user clicks on edit room type link.
		//get the supplier code from Query param
		/*Long supplierCode = Long.parseLong(request().getQueryString("supplierCode"));
		List<RoomType> roomTypes = HotelRoomTypes.getAllRoomTypes(supplierCode);
		return ok(Json.toJson(roomTypes));*/
		return ok();
	}
	
	@Transactional(readOnly=true)
    public static Result fetchToEditHotelDetails(final Long roomId) {
		//this method is used to fetch the room details for edit. this method will return json data..
		HotelRoomTypes roomType = null;
		if (roomId != -1) {
			roomType = new HotelRoomTypes();
			return ok(Json.toJson(roomType));
		} else {
			roomType = HotelRoomTypes.getHotelRoomDetails(roomId);
		}
		return ok(Json.toJson(roomType));
	}
	
	@Transactional(readOnly=false)
    public static Result saveOrUpdateHotelRoom() {
			
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, RoomtypeVM.class);
		RoomtypeVM roomtypeVM = Json.fromJson(json, RoomtypeVM.class);
		
		HotelRoomTypes hotelroomTypes = new HotelRoomTypes();
		hotelroomTypes.setExtraBedAllowed(roomtypeVM.isExtraBed());
		hotelroomTypes.setChargesForChildren(roomtypeVM.isChargesforChild());
		hotelroomTypes.setChildAllowedFreeWithAdults(roomtypeVM.getFreeChildOccuWithAdults());
		hotelroomTypes.setMaxAdultOccupancy(roomtypeVM.getMaxAdultsOccupancy());
		hotelroomTypes.setMaxOccupancy(roomtypeVM.getMaxOccupancy());
		hotelroomTypes.setMaxAdultOccSharingWithChildren(roomtypeVM.getMaxAdultsWithChildren());
		hotelroomTypes.setSupplierCode(roomtypeVM.getSupplierCode());
		hotelroomTypes.setAmenities(RoomAmenities.getroomamenities(roomtypeVM.getRoomamenities()));
		/*hotelprofile.setServices(HotelServices.getallhotelservice(hoteldescription.getServices()));*/
		
		for(RoomChildpoliciVM childVM : roomtypeVM.getRoomchildPolicies())
		{
			RoomChildPolicies roomchildPolicies = new RoomChildPolicies();
			roomchildPolicies.setAllowedChildAgeFrom(childVM.getChildForm());
			roomchildPolicies.setAllowedChildAgeTo(childVM.getChildTo());
			roomchildPolicies.setYears(childVM.getChildYear());
			roomchildPolicies.setNetRate(childVM.getChildFree());
			roomchildPolicies.save();
			hotelroomTypes.addchildPolicies(roomchildPolicies);
			
		}
		hotelroomTypes.save();
		return ok();

	}
	
	@Transactional(readOnly=true)
	public static Result getAvailableRoomAmenities() {
		System.out.println("getAvailableRoomAmenities");
		return ok(Json.toJson(RoomAmenities.getRoomAmenities()));
	}
}
