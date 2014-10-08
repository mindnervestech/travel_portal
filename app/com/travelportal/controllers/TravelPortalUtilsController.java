package com.travelportal.controllers;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



import play.data.DynamicForm;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.domain.AmenitiesType;
import com.travelportal.domain.BillingInformation;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelAmenities;
import com.travelportal.domain.HotelBrands;
import com.travelportal.domain.HotelChain;
import com.travelportal.domain.HotelPrivateContacts;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.Location;
import com.travelportal.domain.MarketPolicyTypes;
import com.travelportal.domain.NightLife;
import com.travelportal.domain.Salutation;
import com.travelportal.domain.ShoppingFacility;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.views.html.billing_information;
import com.travelportal.vm.HotelDescription;
import com.travelportal.vm.HotelGeneralInfoVM;

public class TravelPortalUtilsController extends Controller {

		
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
	public static Result getamenities() {
		List<HotelAmenities> hotelamenities = HotelAmenities.getamenities();
		return ok(Json.toJson(hotelamenities));
	}
		
	@Transactional
	public static Result getbusiness() {
		
		return ok();
	}
	
		
	@Transactional
	public static Result getleisureSport() {
		
		return ok();
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


	   
	    DynamicForm form = DynamicForm.form().bindFromRequest();
		
	  
		HotelProfile hotelprofile = new HotelProfile();
		//hotelprofile.setSupplier_code(form.get("supplierCode"));
		
		hotelprofile.setHotelName(form.get("companyName"));
		hotelprofile.setAddress(form.get("hotelAddress"));
		hotelprofile.setCountry(Country.getCountryByCode(Integer.parseInt(form.get("countryCode"))));
		hotelprofile.setCity(City.getCityByCode(Integer.parseInt(form.get("cityCode"))));
		hotelprofile.setHotelEmailAddr(form.get("emailAddr"));
		hotelprofile.setHoteBrands(HotelBrands.getHotelBrandsbyCode(Integer.parseInt(form.get("brand"))));
		hotelprofile.setPassword(form.get("password"));
		hotelprofile.setStartRatings(Integer.parseInt(form.get("staterate")));
		hotelprofile.setVerifyPassword(form.get("verify"));
		
		
		hotelprofile.save();
		hotelprofile.getSupplier_code();
		return ok(hotelprofile.getId()+"");
	}
	
	@Transactional
	public static Result getupdateDescription() {

		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelDescription.class);
		HotelDescription hoteldescription = Json.fromJson(json, HotelDescription.class);
				
	
		
		//hotelgeneralinfo.save();
		//return ok();		   
		
		
	   /* DynamicForm form = DynamicForm.form().bindFromRequest();
		System.out.println("//////////////");
		System.out.println(form.get("service[1]"));
	    */

	
		HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("supplierCode")));
		System.out.println("//////////////////");		
		System.out.println(form.get("supplierCode"));
		hotelprofile.setHotelProfileDesc(hoteldescription.getDescription());
		hotelprofile.setLocation(Location.getlocationIdByCode(hoteldescription.getHotelLocation()));
		hotelprofile.setShoppingFacility(ShoppingFacility.getShoppingFacilityByCode(hoteldescription.getShoppingFacilityCode()));
		hotelprofile.setNightLife(NightLife.getNightLifeByCode(hoteldescription.getNightLifeCode()));
		hotelprofile.setServices(HotelServices.getallhotelservice(hoteldescription.getServices()));
		
		
		//hotelprofile.setHotelProfileDesc(form.get("description"));
		//hotelprofile.setLocation(Location.getlocationIdByCode(Integer.parseInt(form.get("hotelLocation"))));
		//hotelprofile.setShoppingFacility(ShoppingFacility.getShoppingFacilityByCode(Integer.parseInt(form.get("shoppingFacilityCode"))));
		//hotelprofile.setNightLife(NightLife.getNightLifeByCode(Integer.parseInt(form.get("nightLifeCode"))));
		//hotelprofile.setServices(form.get("services"));
		/*hotelprofile.setZipCode(form.get("hydepark"));
		hotelprofile.setHotelWebSite(form.get("piccadilly"));
		hotelprofile.setHotelGeneralManager(form.get("Buckinghampalace"));*/
		
		
		hotelprofile.merge();
		return ok();
	}
	
	
	@Transactional
	public static Result getupdateInternalInfo() {

				   
	    DynamicForm form = DynamicForm.form().bindFromRequest();
		
	    System.out.println("(((((((())))))))))))");
	    System.out.println(form.get("code"));
	    System.out.println("(((((((())))))))))))");
		
	    HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("code")));
        		
		hotelprofile.setHotelGeneralManager(form.get("generalManager"));
		hotelprofile.setGeneralMgrEmail(form.get("GMemail"));
		hotelprofile.setHotelBuiltYear(Integer.parseInt(form.get("Built")));
		hotelprofile.setHotelRenovationYear(Integer.parseInt(form.get("lastRenov")));
		hotelprofile.setHotelWebSite(form.get("websit"));
		hotelprofile.setNoOfFloors(Integer.parseInt(form.get("noFloors")));
		hotelprofile.setNoOfRooms(Integer.parseInt(form.get("room")));
		
		/*-------------------wong entry-----------------*/
		
		hotelprofile.setZipCode(form.get("GuestTelenumber"));
		hotelprofile.setHotelWebSite(form.get("GuestTelenumber"));
		hotelprofile.setHotelProfileDesc(form.get("email"));
		
		hotelprofile.merge();
		return ok();
	}
	
	
	
	@Transactional
	public static Result getupdateContactInfo() {

	
		
		
		
		
	    DynamicForm form = DynamicForm.form().bindFromRequest();
		
	    HotelPrivateContacts hotelprivatecontacts =new HotelPrivateContacts();
        
	   hotelprivatecontacts.setMainContactPersonName(form.get("contactName"));
	 /*   hotelprivatecontacts.setMainContactPersonTitle();
	    hotelprivatecontacts.setMainContactTelNo();
	    hotelprivatecontacts.setMainContactFaxNo();
	    hotelprivatecontacts.setMainContactExt(Integer.parseInt(form.get("Extension")));
	    hotelprivatecontacts.setMainContactEmailAddr();
	    hotelprivatecontacts.setTollFreeNo(form.get("TollfreeTel"));
	    hotelprivatecontacts.setReservationSameAsMainContact();
	    hotelprivatecontacts.setReservationContactPersonName();
	    hotelprivatecontacts.setReservationContactPersonTitle();
	    hotelprivatecontacts.setReservationContactTelNo();
	    hotelprivatecontacts.setDeptTelNo();
	    hotelprivatecontacts.setDeptFaxNo();
	    hotelprivatecontacts.setDeptExtNo();
	    hotelprivatecontacts.setReservationContactExt();*/
	    hotelprivatecontacts.setReservationContactEmailAddr(form.get("RemailAddr"));
	    
		
	
		
		
		hotelprivatecontacts.save();
		return ok();
	}
	
	
	@Transactional
	public static Result getupdatebillingInfo() {

				   
	    DynamicForm form = DynamicForm.form().bindFromRequest();
		//System.out.println(form.get("Name"));
	    
		BillingInformation billinginfo= new BillingInformation();
		//billinginfo.setInvoiceToHotel(form.get("Invoices"));
		billinginfo.setFirstName(form.get("Name"));
		billinginfo.setLastName(form.get("lastName"));
		billinginfo.setTitle(form.get("title"));
	
		billinginfo.setEmailAddr(form.get("email"));
		/*billinginfo.setTelNo(Integer.parseInt(form.get("telephcode"))+Integer.parseInt(form.get("teleNumber")));
		billinginfo.setFaxNo(Integer.parseInt(form.get("dirFaxcode"))+Integer.parseInt(form.get("dirFaxNumber")));*/
		
		
        billinginfo.save();
		return ok();
	}
	
	@Transactional
	public static Result getupdateComunication() {

				   
	    DynamicForm form = DynamicForm.form().bindFromRequest();
		System.out.println(form.get("code"));
	    
		HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("code")));
        		
		/*hotelprofile.setHotelGeneralManager(form.get("DirectFaxNumber"));
		hotelprofile.setGeneralMgrEmail(form.get("email"));
		hotelprofile.setHotelWebSite(form.get("RDirectFaxNumber"));
	
		hotelprofile.setZipCode(form.get("RemailAddr"));
		hotelprofile.setHotelWebSite(form.get("TollfreeTel"));
		hotelprofile.setHotelGeneralManager(form.get("contactName"));*/
		
		
		hotelprofile.merge();
		return ok();
	}
	
	@Transactional(readOnly=true)
	private static Result getHotelRoomAmenities() {
		List<RoomAmenities> rAmenities = RoomAmenities.getRoomAmenities();
		return ok(Json.toJson(rAmenities));
	}
	
}
