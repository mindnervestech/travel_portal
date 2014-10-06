package com.travelportal.controllers;

import java.util.List;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.vm.RoomType;

public class HotelRoomController extends Controller {
	
	@Transactional(readOnly=true)
    public static Result hotelRoomHome() {
        return ok(index.render("Your new application is ready."));
    }
	
	@Transactional(readOnly=true)
    public static Result fetchAllRoomTypesForEdit() {
		//this is used when user clicks on edit room type link.
		//get the supplier code from Query param
		Long supplierCode = Long.parseLong(request().getQueryString("supplierCode"));
		List<RoomType> roomTypes = HotelRoomTypes.getAllRoomTypes(supplierCode);
		return ok(Json.toJson(roomTypes));
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
		request().body().asJson();
		HotelRoomTypes roomType = request().body().as(HotelRoomTypes.class);
		roomType.saveOrUpdateHotelRoomDetails();
		return ok();
	}
	
	@Transactional(readOnly=true)
	public static Result getAvailableRoomAmenities() {
		System.out.println("getAvailableRoomAmenities");
		return ok(Json.toJson(RoomAmenities.getRoomAmenities()));
	}
}
