/**
 * 
 */
package com.travelportal.controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.hotel_profile;
import views.html.home;

import com.travelportal.domain.HotelProfile;
import com.travelportal.vm.HotelGeneralInfoVM;

/**
 * @author 
 *
 */
public class HotelProfileController extends Controller {
	
	@Transactional(readOnly=true)
	public static Result hotelProfile(Integer profileId) {
		HotelProfile profile = null;
		HotelGeneralInfoVM gInfo = new HotelGeneralInfoVM();
		
		if (profileId == null || profileId == -1) {
			//showing blank profile for admin to create new
			//create new supplier code.. 
			gInfo.setSupplierCode(Long.valueOf(123456));
		} else {
			//fetch the information from db and return object...
			profile = HotelProfile.findById(profileId);
		}
		//
		if (profile != null) {
			//pupulate HoteGeneralInfo with details...
		}
        return ok(hotel_profile.render("Hotel Profile", gInfo));
    }

	public static Result home() {
		return ok(home.render("Home Page"));
	} 
}
