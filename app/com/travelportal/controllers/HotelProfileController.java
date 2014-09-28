/**
 * 
 */
package com.travelportal.controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import com.travelportal.domain.HotelProfile;
import views.html.*;

/**
 * @author 
 *
 */
public class HotelProfileController extends Controller {
	
	@Transactional(readOnly=true)
	public static Result hotelProfile(Integer profileId) {
		HotelProfile profile = new HotelProfile();
		if (profileId == null || profileId == -1) {
			//showing blank profile for admin to create new
		} else {
			//fetch the information from db and return object...
			HotelProfile.findById(profileId);
		}
        return ok(hotel_profile.render("Hotel Profile", profile));
    }
	
}
