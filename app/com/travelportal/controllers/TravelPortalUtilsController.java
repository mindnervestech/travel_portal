package com.travelportal.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
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
import com.travelportal.domain.BusinessCommunication;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelAmenities;
import com.travelportal.domain.HotelAttractions;
import com.travelportal.domain.HotelBrands;
import com.travelportal.domain.HotelChain;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.HotelPrivateContacts;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.InternalContacts;
import com.travelportal.domain.Location;
import com.travelportal.domain.MarketPolicyTypes;
import com.travelportal.domain.MealType;
import com.travelportal.domain.NightLife;
import com.travelportal.domain.Salutation;
import com.travelportal.domain.ShoppingFacility;
import com.travelportal.domain.TransportationDirection;
import com.travelportal.domain.rooms.ChildPolicies;
/*import com.travelportal.domain.SupplierCode;*/
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.views.html.billing_information;
import com.travelportal.vm.AreaAttractionsSuppVM;
import com.travelportal.vm.AreaAttractionsVM;
import com.travelportal.vm.ChildpoliciVM;
import com.travelportal.vm.HotelDescription;
import com.travelportal.vm.HotelGeneralInfoVM;
import com.travelportal.vm.HotelamenitiesVM;
import com.travelportal.vm.HotelmealVM;
import com.travelportal.vm.TransportationDirectionsSuppVM;
import com.travelportal.vm.TransportationDirectionsVM;


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
		int amenitiescode=1;
		
		List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(amenitiescode));
		return ok(Json.toJson(hotelamenities));
	}
	
	
	@Transactional
	public static Result getsalutation() {
		List<Salutation> salutation = Salutation.getsalutation();
		return ok(Json.toJson(salutation));
	}
	
		
	@Transactional
	public static Result getbusiness() {
		
		int businesseVal=2;
	
		List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(businesseVal));
		return ok(Json.toJson(hotelamenities));

	}
	
		
	@Transactional
	public static Result getleisureSport() {
		
		int leisureSportVal=3;
	
		List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(leisureSportVal));
		return ok(Json.toJson(hotelamenities));
		
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
	public static Result getMealtypeplan() {
		List<HotelMealPlan> mealtype = HotelMealPlan.getmealtype();
		System.out.println("/////////////////////");
		//System.out.println(mealtype.get(11).get());
		
		return ok(Json.toJson(mealtype));
	}
	
	@Transactional
	public static Result getMealtype() {
		List<MealType> mealtypes = MealType.getmealtypes();
		return ok(Json.toJson(mealtypes));
	}
	
	
	
	@Transactional
	public static Result saveGeneralInfo() {
   
	    DynamicForm form = DynamicForm.form().bindFromRequest();
  
		HotelProfile hotelprofile = new HotelProfile();
		//hotelprofile.setSupplier_code(form.get("supplierCode"));
		
		
		hotelprofile.setHotelName(form.get("companyName"));
		hotelprofile.setSupplierName(form.get("companyName"));
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
		
    	Map<String,Object> map  = new HashMap<String,Object>();
    	map.put("ID", hotelprofile.getId());
    	map.put("NAME", hotelprofile.getHotelName());
		return ok(Json.toJson(map));
	}
	
	
	@Transactional
	public static Result getsaveamenities() {

		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelamenitiesVM.class);
		HotelamenitiesVM hotelamenitiesVM = Json.fromJson(json, HotelamenitiesVM.class);
		
		System.out.println("////////////@@@@@//////////");
		System.out.println(form.get("supplierCode"));
		System.out.println("//////////@@@@@////////////");
		
		HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("supplierCode")));
		hotelprofile.setAmenities(HotelAmenities.getallhotelamenities(hotelamenitiesVM.getamenities()));
		
		return ok();
	}
	
	
	@Transactional
	public static Result getupdatemealpolicy() {
		
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelmealVM.class);
		HotelmealVM hotelmealvm = Json.fromJson(json, HotelmealVM.class);
		
		HotelMealPlan hotelmealplan = HotelMealPlan.findById(Integer.parseInt(form.get("id")));
		hotelmealplan.setFromPeriod(hotelmealvm.getFromPeriod());
		hotelmealplan.setToPeriod(hotelmealvm.getToPeriod());
		hotelmealplan.setMealPlanNm(hotelmealvm.getMealPlanNm());
		hotelmealplan.setRate(hotelmealvm.getRate());
		hotelmealplan.setSupplierCode(hotelmealvm.getsupplierCode());
		hotelmealplan.setTaxIncluded(hotelmealvm.gettaxIncluded());
		hotelmealplan.setTaxvalue(hotelmealvm.getTaxvalue());
		hotelmealplan.setTaxtype(hotelmealvm.getTaxtype());
		hotelmealplan.setMealType(MealType.getMealTypeIdByCode(hotelmealvm.getMealType()));
		
		//hotelmealplan.merge();
		
		
		for(ChildpoliciVM vm : hotelmealvm.getchild()){
			ChildPolicies childPolicies = ChildPolicies.findById(vm.getChildPolicyId());
			childPolicies.setAllowedChildAgeFrom(vm.getAllowedChildAgeFrom());
			childPolicies.setAllowedChildAgeTo(vm.getAllowedChildAgeTo());
			childPolicies.setCharge(vm.getCharge());
			childPolicies.setChargeType(vm.getChargeType());
			childPolicies.setChildtaxvalue(vm.getChildtaxvalue());
			childPolicies.setChildtaxtype(vm.getChildtaxtype());
			
			//childPolicies.setMeal_plan_id(HotelMealPlan.getHotelMealPlanIdByCode(hotelmealplan.getId()));
			childPolicies.merge();
			hotelmealplan.addChild(childPolicies);
		//	hotelmealplan.addChild(childPolicies);
			
		}
		
		hotelmealplan.merge();
	
		return ok();
	}
	
	
	@Transactional
	public static Result getsavemealpolicy() {
	
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelmealVM.class);
		HotelmealVM hotelmealvm = Json.fromJson(json, HotelmealVM.class);
		
		HotelMealPlan hotelmealplan = new HotelMealPlan();
		hotelmealplan.setFromPeriod(hotelmealvm.getFromPeriod());
		hotelmealplan.setToPeriod(hotelmealvm.getToPeriod());
		hotelmealplan.setMealPlanNm(hotelmealvm.getMealPlanNm());
		hotelmealplan.setRate(hotelmealvm.getRate());
		hotelmealplan.setSupplierCode(hotelmealvm.getsupplierCode());
		hotelmealplan.setTaxIncluded(hotelmealvm.gettaxIncluded());
		hotelmealplan.setTaxvalue(hotelmealvm.getTaxvalue());
		hotelmealplan.setTaxtype(hotelmealvm.getTaxtype());
		hotelmealplan.setMealType(MealType.getMealTypeIdByCode(hotelmealvm.getMealType()));
		
		for(ChildpoliciVM vm : hotelmealvm.getchild()){
			ChildPolicies childPolicies = new ChildPolicies();
			childPolicies.setAllowedChildAgeFrom(vm.getAllowedChildAgeFrom());
			childPolicies.setAllowedChildAgeTo(vm.getAllowedChildAgeTo());
			childPolicies.setCharge(vm.getCharge());
			childPolicies.setChargeType(vm.getChargeType());
			childPolicies.setChildtaxvalue(vm.getChildtaxvalue());
			childPolicies.setChildtaxtype(vm.getChildtaxtype());
//			childPolicies.setMeal_plan_id(HotelMealPlan.getHotelMealPlanIdByCode(hotelmealplan.getId()));
			childPolicies.save();
			hotelmealplan.addChild(childPolicies);
			
		}
		hotelmealplan.save();
		
		//hotelmealplan.merge();
	
		return ok();
	}
	
	
	@Transactional
	public static Result getdeletemealpolicy(int id) {
		
				
		HotelMealPlan hotelmealplan = HotelMealPlan.findById(id);
		for(ChildPolicies policies : hotelmealplan.getChild()){
			policies.delete();
		}
		
		hotelmealplan.delete();
		return ok();
	}
	
		
	@Transactional
	public static Result getsaveattraction() {

		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, AreaAttractionsSuppVM.class);
		AreaAttractionsSuppVM areaattractionssuppVM = Json.fromJson(json, AreaAttractionsSuppVM.class);
				
		
		HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("supplierCode")));
		
	
		for(AreaAttractionsVM vm : areaattractionssuppVM.getAreaInfo()){
		 if(vm.getname() != "")
		 {
			HotelAttractions hotelattractions = new HotelAttractions();
			
			hotelattractions.setDistanceType(vm.getKm());
			hotelattractions.setDistance(vm.getDistance());
			hotelattractions.setTimeRequireInMinutes(vm.getMinutes());
			hotelattractions.setAttractionNm(vm.getname());
		
			
			hotelattractions.save();
			hotelprofile.addHotelareaattraction(hotelattractions);
			}
					
		}
		
		
		return ok();
	}
	
	
	@Transactional
	public static Result getsavetransportDir() {

		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, TransportationDirectionsSuppVM.class);
		TransportationDirectionsSuppVM transportationdirectionsSuppVM = Json.fromJson(json, TransportationDirectionsSuppVM.class);
		
		//HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("supplierCode")));
		
	
		for(TransportationDirectionsVM vm : transportationdirectionsSuppVM.getTransportInfo()){
		// if(vm.getname() != "")
		// {
			
			TransportationDirection transportationDirection=new  TransportationDirection();
			transportationDirection.setAirportNm(vm.getAirportName());
			transportationDirection.setAirportdirections(vm.getAirportdirections());
			transportationDirection.setAirportdistance(vm.getAirportdistance());
			transportationDirection.setAirportdistanceType(vm.getAirportkm());
			transportationDirection.setAirporttimeRequireInMinutes(vm.getAirportminutes());
			
			transportationDirection.setRailStationNm(vm.getRailName());
			transportationDirection.setRailStationdistance(vm.getRaildistance());
			transportationDirection.setRailStationdirections(vm.getRaildirections());
			transportationDirection.setRailStationdistanceType(vm.getRailkm());
			transportationDirection.setRailStationtimeRequireInMinutes(vm.getRailminutes());
			
			transportationDirection.setSubwayNm(vm.getSubwayName());
			transportationDirection.setSubwaydirections(vm.getSubwaydirections());
			transportationDirection.setSubwaydistance(vm.getSubwaydistance());
			transportationDirection.setSubwaydistanceType(vm.getSubwaykm());
			transportationDirection.setSubwaytimeRequireInMinutes(vm.getSubwayminutes());
			
			transportationDirection.setCruiseNm(vm.getCruiseName());
			transportationDirection.setCruisedirections(vm.getCruisedirections());
			transportationDirection.setCruisedistance(vm.getCruisedistance());
			transportationDirection.setCruisedistanceType(vm.getCruisekm());
			transportationDirection.setCruisetimeRequireInMinutes(vm.getCruiseminutes());
			
			transportationDirection.save();
					
		}
		
		
		return ok();
	}
	
	
	@Transactional
	public static Result getupdateDescription() {

		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelDescription.class);
		HotelDescription hoteldescription = Json.fromJson(json, HotelDescription.class);
		
	
		HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("supplierCode")));
		
		hotelprofile.setHotelProfileDesc(hoteldescription.getDescription());
		hotelprofile.setLocation(Location.getlocationIdByCode(hoteldescription.getHotelLocation()));
		hotelprofile.setShoppingFacility(ShoppingFacility.getShoppingFacilityByCode(hoteldescription.getShoppingFacilityCode()));
		hotelprofile.setNightLife(NightLife.getNightLifeByCode(hoteldescription.getNightLifeCode()));
		hotelprofile.setServices(HotelServices.getallhotelservice(hoteldescription.getServices()));
		hotelprofile.setlocation1(hoteldescription.getlocation1());
		hotelprofile.setlocation2(hoteldescription.getlocation2());
		hotelprofile.setlocation3(hoteldescription.getlocation3());
		hotelprofile.setSupplier_code(Long.parseLong(form.get("supplierCode")));
		
		
		hotelprofile.merge();
		return ok();
	}
	
	
	@Transactional
	public static Result getupdateInternalInfo() {
				   
	    DynamicForm form = DynamicForm.form().bindFromRequest();
	    HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("code")));
        		
		hotelprofile.setHotelGeneralManager(form.get("generalManager"));
		hotelprofile.setGeneralMgrEmail(form.get("GMemail"));
		hotelprofile.setHotelBuiltYear(Integer.parseInt(form.get("Built")));
		hotelprofile.setHotelRenovationYear(Integer.parseInt(form.get("lastRenov")));
		hotelprofile.setHotelWebSite(form.get("websit"));
		hotelprofile.setNoOfFloors(Integer.parseInt(form.get("noFloors")));
		hotelprofile.setNoOfRooms(Integer.parseInt(form.get("room")));
		
		hotelprofile.merge();
		
		InternalContacts internalcontact=new InternalContacts();
		
		internalcontact.setGuestTelCityCode(Integer.parseInt(form.get("GuestTele")));
		internalcontact.setGuestTelValue(Integer.parseInt(form.get("GuestTelenumber")));
		internalcontact.setGuestFaxCityCode(Integer.parseInt(form.get("GuestfaxCode")));
		internalcontact.setGuestFaxValue(Integer.parseInt(form.get("Guestfaxnumber")));
		internalcontact.setDirectTelCityCode(Integer.parseInt(form.get("DirectteleCode")));
		internalcontact.setDirectTelValue(Integer.parseInt(form.get("Directnumber")));
		internalcontact.setDirectFaxCityCode(Integer.parseInt(form.get("dirFaxCode")));
		internalcontact.setDirectFaxValue(Integer.parseInt(form.get("dirFaxnumber")));
		internalcontact.setSupplierCode(Long.parseLong(form.get("code")));
		
		internalcontact.save();
		
		return ok();
	}
	
	
	
	@Transactional
	public static Result getupdateContactInfo() {
		
	    DynamicForm form = DynamicForm.form().bindFromRequest();
		
	    HotelPrivateContacts hotelprivatecontacts =new HotelPrivateContacts();
        
	   hotelprivatecontacts.setMainContactPersonName(form.get("contactName"));
	    hotelprivatecontacts.setMainContactPersonTitle(form.get("title"));
	    hotelprivatecontacts.setMainContactTelNo(form.get("teleCode")+" "+form.get("teleNumber"));
	    hotelprivatecontacts.setMainContactFaxNo(form.get("DirectFaxCode")+" "+form.get("DirectFaxNumber"));
	    hotelprivatecontacts.setMainContactExt(Integer.parseInt(form.get("Extension")));
	    hotelprivatecontacts.setMainContactEmailAddr(form.get("emailAddr"));
	    hotelprivatecontacts.setTollFreeNo(form.get("TollfreeTel"));
	    hotelprivatecontacts.setReservationSameAsMainContact(Boolean.parseBoolean(form.get("value1")));
	    hotelprivatecontacts.setReservationContactPersonName(form.get("RcontactName"));
	    hotelprivatecontacts.setReservationContactPersonTitle(form.get("Rtitle"));
	    hotelprivatecontacts.setReservationContactTelNo(form.get("RteleCode")+" "+form.get("RteleNumber"));
	    hotelprivatecontacts.setDeptTelNo(form.get("RDirecttelCode")+" "+form.get("RDirecttelNumber"));
	    hotelprivatecontacts.setDeptFaxNo(form.get("RDirectFaxCode")+" "+form.get("RDirectFaxNumber"));
	    hotelprivatecontacts.setDeptExtNo(form.get("RExtension"));
	    hotelprivatecontacts.setReservationContactExt(Integer.parseInt(form.get("RExtension2")));
	    hotelprivatecontacts.setReservationContactEmailAddr(form.get("RemailAddr"));
	    hotelprivatecontacts.setSalutation_salutation_id(Salutation.getsalutationIdIdByCode(Integer.parseInt(form.get("salutation"))));
	 	
		hotelprivatecontacts.save();
		return ok();
	}
	
	
	@Transactional
	public static Result getupdatebillingInfo() {

				   
	    DynamicForm form = DynamicForm.form().bindFromRequest();
		//System.out.println(form.get("Name"));
	    
		BillingInformation billinginfo= new BillingInformation();
		billinginfo.setInvoiceToHotel(Boolean.parseBoolean(form.get("Invoices")));
		billinginfo.setFirstName(form.get("Name"));
		billinginfo.setLastName(form.get("lastName"));
		billinginfo.setTitle(form.get("title"));
	
		billinginfo.setEmailAddr(form.get("email"));
		billinginfo.setTelNo(Integer.parseInt(form.get("telephcode")+form.get("teleNumber")));
		billinginfo.setFaxNo(Integer.parseInt(form.get("dirFaxcode")+form.get("dirFaxNumber")));
		billinginfo.setSupplierCode(Long.parseLong(form.get("code")));
		billinginfo.setExt(Integer.parseInt(form.get("Extension")));
		billinginfo.setbankservice(Boolean.parseBoolean(form.get("service")));
		
        billinginfo.save();
		return ok();
	}
	
	@Transactional
	public static Result getupdateComunication() {
		
				
	    DynamicForm form = DynamicForm.form().bindFromRequest();
		//System.out.println(form.get("code"));
	           		
		BusinessCommunication businesscommunication=new BusinessCommunication(); 
		businesscommunication.setPrimaryEmailAddr(form.get("email"));
		businesscommunication.setCcEmailAddr(form.get("ccmail"));
		businesscommunication.setbooking(form.get("booking"));
		businesscommunication.setSupplierCode(Long.parseLong(form.get("code")));
		
		
		businesscommunication.save();
		return ok();
	}
	
	@Transactional(readOnly=true)
	private static Result getHotelRoomAmenities() {
		List<RoomAmenities> rAmenities = RoomAmenities.getRoomAmenities();
		return ok(Json.toJson(rAmenities));
	}
	
}
