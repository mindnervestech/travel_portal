package com.travelportal.controllers;

import java.util.HashMap;
import java.util.List;

import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.home;

import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.Permissions;
import com.travelportal.domain.agent.AgentRegistration;

public class SupplierLoginthroughAdminController extends Controller {

	/*@Transactional
	public static Result getsupplierLogin(String supplierCode,String supplierName) {
		System.out.println("%%%%%%%%%%%%");
		System.out.println(supplierCode);
		System.out.println(supplierName);
		
		HotelRegistration user = HotelRegistration.findSupplier(supplierCode,supplierName);
		System.out.println(user.getSupplierCode());
		if(user != null) {
			System.out.println("Home login");
			//session().put("SUPPLIER", user.getSupplierCode());
			long code = Long.parseLong(user.getSupplierCode());
			return ok(home.render("Home Page", code));
		}
		return ok(views.html.adminHome.render());
				
		
		
		}*/
	
	@Transactional
	public static Result getsupplierLogin() {
	DynamicForm form = DynamicForm.form().bindFromRequest();
	
	HotelRegistration user = HotelRegistration.findSupplier(form.get("supplierCode"),form.get("hotelNm"));
	//System.out.println(user.getSupplierCode());
	long supplierpending = HotelRegistration.pendingData();
	long agentpending = AgentRegistration.pendingData();
	System.out.println(supplierpending);
	System.out.println("SESSION VALUE   "+session().get("NAME"));
	if(user != null) {
		//session().put("SUPPLIER", user.getSupplierCode());
		long code = Long.parseLong(user.getSupplierCode());
		
		HashMap<String , String> permission = new HashMap<>();
        List<Permissions> permissions = Permissions.getPermission();
    
        for(Permissions permissions2 : permissions){
        	permission.put(permissions2.getName() , String.valueOf(user.getPermissions().contains(permissions2)));
        }
		return ok(home.render("Home Page", code,Json.stringify(Json.toJson(permission))));
		
	}
	
	
	//return ok();
	return ok(views.html.adminHome.render(String.valueOf(supplierpending), String .valueOf(agentpending)));
}
	
	
	
	@Transactional
	public static Result getfindSupplier(String SupplierCode,String SupplierName) {
		String successdata="";
		HotelRegistration hotelRegistration = HotelRegistration.findSupplier(SupplierCode,SupplierName);
		if(hotelRegistration != null)
		{
			successdata = "true";
		}
		else
		{
			successdata = "false";
		}
		return ok(successdata);
		
	}
			
}
