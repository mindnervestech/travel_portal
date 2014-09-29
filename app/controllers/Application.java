package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.vm.HotelGeneralInfoVM;

import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.home;
import views.html.hotel_profile;


public class Application extends Controller {
	@Transactional(readOnly=true)
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
	

	
	public static Result hotel_profile() {
		
		
		HotelGeneralInfoVM hotelGeneralInfoVM=new HotelGeneralInfoVM();
	hotelGeneralInfoVM.setSupplierCode(new Long(0));
        return ok(hotel_profile.render("Your new application is ready.",hotelGeneralInfoVM));
    }
}
