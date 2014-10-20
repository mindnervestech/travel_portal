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
import com.travelportal.vm.HotelBillingInformation;
import com.travelportal.vm.HotelCommunication;
import com.travelportal.vm.HotelContactInformation;
import com.travelportal.vm.HotelDescription;
import com.travelportal.vm.HotelGeneralInfoVM;
import com.travelportal.vm.HotelInternalInformation;
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
	public static Result getAllData(long supplierCode)
	{
		HotelProfile hotelProfile = HotelProfile.findAllData(supplierCode);
		
		HotelGeneralInfoVM hotelgeneralinfoVM = new HotelGeneralInfoVM();
		
		hotelgeneralinfoVM.setHotelNm(hotelProfile.getHotelName());
		hotelgeneralinfoVM.setHotelAddr(hotelProfile.getAddress());
		hotelgeneralinfoVM.setCountryCode(hotelProfile.getCountry().getCountryCode());
		hotelgeneralinfoVM.setHotelPartOfChain(hotelProfile.isPartOfChain());
		hotelgeneralinfoVM.setEmail(hotelProfile.getHotelEmailAddr());
		hotelgeneralinfoVM.setPrimaryPasswd(hotelProfile.getPassword());
		hotelgeneralinfoVM.setVerifiedPasswd(hotelProfile.getVerifyPassword());
		hotelgeneralinfoVM.setCityCode(hotelProfile.getCity().getCityCode());
		hotelgeneralinfoVM.setMarketSpecificPolicyCode(hotelProfile.getMarketPolicyType().getMarketPolicyTypeId());
		hotelgeneralinfoVM.setBrandHotelCode(hotelProfile.getHoteBrands().getBrandsCode());
		hotelgeneralinfoVM.setStartRating(String.valueOf(hotelProfile.getStartRatings()));
		hotelgeneralinfoVM.setSupplierCode(hotelProfile.getSupplier_code());
		
		HotelDescription hoteldescription = new HotelDescription();
		hoteldescription.setDescription(hotelProfile.getHotelProfileDesc());
		hoteldescription.setHotelLocation(hotelProfile.getLocation().getLocationId());
		hoteldescription.setShoppingFacilityCode(hotelProfile.getShoppingFacility().getId());
		hoteldescription.setNightLifeCode(hotelProfile.getNightLife().getNightLifeCode());
		hoteldescription.setServices(hotelProfile.getIntListServices());
		
		hoteldescription.setlocation1(hotelProfile.getlocation1());
		hoteldescription.setlocation2(hotelProfile.getlocation2());
		hoteldescription.setlocation3(hotelProfile.getlocation3());
		hoteldescription.setSupplierCode(hotelProfile.getSupplier_code());
		
		InternalContacts internalcontacts = InternalContacts.findById(supplierCode);
		HotelInternalInformation hotelinternalinformation = new HotelInternalInformation();
		
		hotelinternalinformation.setGenMgrName(hotelProfile.getHotelGeneralManager());
		hotelinternalinformation.setGenMgrEmail(hotelProfile.getGeneralMgrEmail());
		hotelinternalinformation.setBuiltYear(hotelProfile.getHotelBuiltYear());
		hotelinternalinformation.setRenovationYear(hotelProfile.getHotelRenovationYear());
		hotelinternalinformation.setWebSiteUrl(hotelProfile.getHotelWebSite());
		hotelinternalinformation.setNoOffloor(hotelProfile.getNoOfFloors());
		hotelinternalinformation.setNoOfRoom(hotelProfile.getNoOfRooms());
		hotelinternalinformation.setSafetyCompliance(hotelProfile.isFireSafetyCompliance());
		hotelinternalinformation.setGuestTel(internalcontacts.getGuestTelValue());
		hotelinternalinformation.setGuestTelCode(internalcontacts.getGuestTelCityCode());
		hotelinternalinformation.setGuestFax(internalcontacts.getGuestFaxValue());
		hotelinternalinformation.setGuestFaxCode(internalcontacts.getGuestFaxCityCode());
		hotelinternalinformation.setDirectTelNo(internalcontacts.getDirectTelValue());
		hotelinternalinformation.setDirectTelCode(internalcontacts.getDirectTelCityCode());
		hotelinternalinformation.setDirectFaxNo(internalcontacts.getDirectFaxValue());
		hotelinternalinformation.setDirectFaxCode(internalcontacts.getDirectFaxCityCode());
		hotelinternalinformation.setSupplierCode(internalcontacts.getSupplierCode());
		
		HotelPrivateContacts hotelprivatecontacts = HotelPrivateContacts.findById(supplierCode);
		HotelContactInformation hotelcontactinformation = new HotelContactInformation();
		
		hotelcontactinformation.setSupplierCode(hotelprivatecontacts.getSupplierCode());
		hotelcontactinformation.setcPersonName(hotelprivatecontacts.getMainContactPersonName());
		hotelcontactinformation.setcTitle(hotelprivatecontacts.getMainContactPersonTitle());
		hotelcontactinformation.setdTelNo(hotelprivatecontacts.getMainContactTelNo());
		hotelcontactinformation.setdTelCode(hotelprivatecontacts.getMainContactTelCode());
		hotelcontactinformation.setdFaxNo(hotelprivatecontacts.getMainContactFaxNo());
		hotelcontactinformation.setdFaxCode(hotelprivatecontacts.getMainContactFaxCode());
		hotelcontactinformation.setDeptExtNo(hotelprivatecontacts.getMainContactExt());
		hotelcontactinformation.setdEmailAddr(hotelprivatecontacts.getMainContactEmailAddr());
		hotelcontactinformation.setdTollFreeTelNo(hotelprivatecontacts.getTollFreeNo());
		hotelcontactinformation.setReservationDetailSame(hotelprivatecontacts.isReservationSameAsMainContact());
		hotelcontactinformation.setrContactName(hotelprivatecontacts.getReservationContactPersonName());
		hotelcontactinformation.setrTitle(hotelprivatecontacts.getReservationContactPersonTitle());
		hotelcontactinformation.setrDeptTelNo(hotelprivatecontacts.getDeptTelNo());
		hotelcontactinformation.setrEmailAddr(hotelprivatecontacts.getReservationContactEmailAddr());
		hotelcontactinformation.setrDeptTelCode(hotelprivatecontacts.getReservationContactTelNo());
		hotelcontactinformation.setrDeptFaxNo(hotelprivatecontacts.getDeptFaxNo());
		hotelcontactinformation.setrDeptFaxCode(hotelprivatecontacts.getDeptFaxCode());
		hotelcontactinformation.setrDirectTelCode(hotelprivatecontacts.getReservationContactTelNo());
		hotelcontactinformation.setrDirectTelCode(hotelprivatecontacts.getReservationContactTelCode());
		
		BusinessCommunication businesscommunication = BusinessCommunication.findById(supplierCode);
		HotelCommunication hotelcommuniction = new HotelCommunication();
		hotelcommuniction.setPrimaryEmailAddr(businesscommunication.getPrimaryEmailAddr());
		hotelcommuniction.setCcEmailAddr(businesscommunication.getCcEmailAddr());
		hotelcommuniction.setSupplierCode(businesscommunication.getSupplierCode());
		hotelcommuniction.setBooking(businesscommunication.getbooking());
		
	/*	System.out.println(supplierCode);
		BillingInformation billinginformation = BillingInformation.findById(supplierCode);
		System.out.println(billinginformation);
		HotelBillingInformation hotelbillinginformation = new HotelBillingInformation();
		
		hotelbillinginformation.setInvoiceToHotel(billinginformation.getInvoiceToHotel());
		hotelbillinginformation.setaFirstName(billinginformation.getFirstName());
		hotelbillinginformation.setaLastName(billinginformation.getLastName());
		hotelbillinginformation.setTitle(billinginformation.getTitle());
		hotelbillinginformation.setdEmailAddr(billinginformation.getEmailAddr());
		hotelbillinginformation.setdTelNo(billinginformation.getTelNo());
		hotelbillinginformation.setdTelCode(billinginformation.getTelNoCode());
		hotelbillinginformation.setdFaxNo(billinginformation.getFaxNo());
		hotelbillinginformation.setdFaxCode(billinginformation.getFaxNoCode());
		hotelbillinginformation.setSupplierCode(billinginformation.getSupplierCode());
		hotelbillinginformation.setdExtNo(billinginformation.getExt());
		hotelbillinginformation.setBankToBankTransfer(billinginformation.getBankservice());
		*/
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hotelgeneralinfo", hotelgeneralinfoVM);
		map.put("hoteldescription", hoteldescription);
		map.put("hotelinternalinformation", hotelinternalinformation);
		map.put("hotelcontactinformation", hotelcontactinformation);
		map.put("hotelcommuniction", hotelcommuniction);
		//map.put("hotelbillinginformation", hotelbillinginformation);
		
		
		return ok(Json.toJson(map));
		
	}
	
	
	@Transactional
	public static Result saveGeneralInfo() {
   
	    DynamicForm form = DynamicForm.form().bindFromRequest();
  
	    HotelProfile hotelprofile;
	    if(form.get("supplierCode") == null || form.get("supplierCode") == "")
	    {
	    	 hotelprofile = new HotelProfile();	    	
       }
	    else
	    {
	    	 hotelprofile = HotelProfile.findById(Long.parseLong(form.get("supplierCode")));	    	 
	    }
	    
	    hotelprofile.setHotelName(form.get("hotelNm"));
  		hotelprofile.setSupplierName(form.get("hotelNm"));
  		hotelprofile.setAddress(form.get("hotelAddr"));
  		hotelprofile.setCountry(Country.getCountryByCode(Integer.parseInt(form.get("countryCode"))));
  		hotelprofile.setCity(City.getCityByCode(Integer.parseInt(form.get("cityCode"))));
  		hotelprofile.setPartOfChain(form.get("isHotelPartOfChain"));
  		hotelprofile.setHotelEmailAddr(form.get("email"));
  		hotelprofile.setMarketPolicyType(MarketPolicyTypes.getMarketPolicyTypesIdByCode(Integer.parseInt(form.get("marketSpecificPolicyCode"))));
  		hotelprofile.setHoteBrands(HotelBrands.getHotelBrandsbyCode(Integer.parseInt(form.get("BrandHotelCode"))));
  		hotelprofile.setPassword(form.get("primaryPasswd"));
  		hotelprofile.setStartRatings(Integer.parseInt(form.get("startRating")));
  		hotelprofile.setVerifyPassword(form.get("verifiedPasswd"));
  		
  		if(form.get("supplierCode") == null || form.get("supplierCode") == "")
	    {
  			hotelprofile.save();	    	
       }
	    else
	    {
	    	hotelprofile.merge();	    	 
	    }
  		
      	Map<String,Object> map  = new HashMap<String,Object>();
      	map.put("ID", hotelprofile.getId());
      	map.put("NAME", hotelprofile.getHotelName());
      	map.put("ADDR",hotelprofile.getAddress());
  		return ok(Json.toJson(map));

			  
	}
	
	
	@Transactional
	public static Result getsaveamenities() {

		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelamenitiesVM.class);
		HotelamenitiesVM hotelamenitiesVM = Json.fromJson(json, HotelamenitiesVM.class);
	
		
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
		
		
		
		for(ChildpoliciVM vm : hotelmealvm.getchild()){
			ChildPolicies childPolicies = ChildPolicies.findById(vm.getChildPolicyId());
			childPolicies.setAllowedChildAgeFrom(vm.getAllowedChildAgeFrom());
			childPolicies.setAllowedChildAgeTo(vm.getAllowedChildAgeTo());
			childPolicies.setCharge(vm.getCharge());
			childPolicies.setChargeType(vm.getChargeType());
			childPolicies.setChildtaxvalue(vm.getChildtaxvalue());
			childPolicies.setChildtaxtype(vm.getChildtaxtype());
			
			childPolicies.merge();
			hotelmealplan.addChild(childPolicies);
					
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

			childPolicies.save();
			hotelmealplan.addChild(childPolicies);
			
		}
		hotelmealplan.save();
		
		

		return ok();
	}
	
	
	
	
	@Transactional
	public static Result getdeletechile(int id) {
	
		HotelMealPlan hotelmealplan = HotelMealPlan.findById(id);
		for(ChildPolicies policies : hotelmealplan.getChild()){
			policies.delete();
		}
		hotelmealplan.setChild(null);
		hotelmealplan.merge();
		
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
		
		String attractionsuccess="";
		for(AreaAttractionsVM vm : areaattractionssuppVM.getAreaInfo()){
		 if(vm.getname() != "")
		 {
			 
			HotelAttractions hotelattractions = new HotelAttractions();
			
			if(hotelattractions.attractionrepeat(vm.getname()) == null)
			{
			
			hotelattractions.setDistanceType(vm.getKm());
			hotelattractions.setDistance(vm.getDistance());
			hotelattractions.setTimeRequireInMinutes(vm.getMinutes());
			hotelattractions.setAttractionNm(vm.getname());
		
			
			hotelattractions.save();
			hotelprofile.addHotelareaattraction(hotelattractions);
			
			attractionsuccess = "yes";
			}
			  else
			    {
				  attractionsuccess = "no";
			    }
			}
					
		}
		
		return ok(attractionsuccess);
	}
	
	
	@Transactional
	public static Result getsavetransportDir() {

		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, TransportationDirectionsSuppVM.class);
		TransportationDirectionsSuppVM transportationdirectionsSuppVM = Json.fromJson(json, TransportationDirectionsSuppVM.class);
		
		//HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("supplierCode")));
		String successdata="";
		
		for(TransportationDirectionsVM vm : transportationdirectionsSuppVM.getFindLocation()){
						
			TransportationDirection transportationDirection=new  TransportationDirection();
			
		    if(transportationDirection.checklocationexe(vm.getLocationName()) == null)
		    {	
		 
			transportationDirection.setLocationName(vm.getLocationName());
			transportationDirection.setLocationAddr(vm.getLocationAddr());
			
			transportationDirection.save();
			//hotelprofile.addTransportCode(transportationDirection);
			successdata="yes";
		    }
		    else
		    {
		    	successdata="no";
		    }
								
		}
		
		
		return ok(successdata);
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
	    HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("supplierCode")));
        		
		hotelprofile.setHotelGeneralManager(form.get("genMgrName"));
		hotelprofile.setGeneralMgrEmail(form.get("genMgrEmail"));
		hotelprofile.setHotelBuiltYear(Integer.parseInt(form.get("builtYear")));
		hotelprofile.setHotelRenovationYear(Integer.parseInt(form.get("renovationYear")));
		hotelprofile.setHotelWebSite(form.get("webSiteUrl"));
		hotelprofile.setNoOfFloors(Integer.parseInt(form.get("noOffloor")));
		hotelprofile.setNoOfRooms(Integer.parseInt(form.get("noOfRoom")));
		hotelprofile.setFireSafetyCompliance(form.get("safetyCompliance"));
		
		hotelprofile.merge();
				
		InternalContacts internalcontact = InternalContacts.findById(Long.parseLong(form.get("supplierCode")));
		
		if(internalcontact == null)
		{
			 internalcontact=new InternalContacts();
			 
			 internalcontact.setGuestTelCityCode(Integer.parseInt(form.get("guestTelCode")));
				internalcontact.setGuestTelValue(Integer.parseInt(form.get("guestTel")));
				internalcontact.setGuestFaxCityCode(Integer.parseInt(form.get("guestFaxCode")));
				internalcontact.setGuestFaxValue(Integer.parseInt(form.get("guestFax")));
				internalcontact.setDirectTelCityCode(Integer.parseInt(form.get("directTelCode")));
				internalcontact.setDirectTelValue(Integer.parseInt(form.get("directTelNo")));
				internalcontact.setDirectFaxCityCode(Integer.parseInt(form.get("directFaxCode")));
				internalcontact.setDirectFaxValue(Integer.parseInt(form.get("directFaxNo")));
				internalcontact.setSupplierCode(Long.parseLong(form.get("supplierCode")));
				
				internalcontact.save();
		}
		else
		{
		
				internalcontact.setGuestTelCityCode(Integer.parseInt(form.get("guestTelCode")));
				internalcontact.setGuestTelValue(Integer.parseInt(form.get("guestTel")));
				internalcontact.setGuestFaxCityCode(Integer.parseInt(form.get("guestFaxCode")));
				internalcontact.setGuestFaxValue(Integer.parseInt(form.get("guestFax")));
				internalcontact.setDirectTelCityCode(Integer.parseInt(form.get("directTelCode")));
				internalcontact.setDirectTelValue(Integer.parseInt(form.get("directTelNo")));
				internalcontact.setDirectFaxCityCode(Integer.parseInt(form.get("directFaxCode")));
				internalcontact.setDirectFaxValue(Integer.parseInt(form.get("directFaxNo")));
				
		internalcontact.merge();
		}
		return ok();
	}
	
	
	
	@Transactional
	public static Result getupdateContactInfo() {
		
	    DynamicForm form = DynamicForm.form().bindFromRequest();
		
	    //HotelPrivateContacts hotelprivatecontacts =new HotelPrivateContacts();
        
	    HotelPrivateContacts hotelprivatecontacts = HotelPrivateContacts.findById(Long.parseLong(form.get("supplierCode")));
	    
	    if(hotelprivatecontacts == null)
	    {
	    	hotelprivatecontacts =new HotelPrivateContacts();
	    	hotelprivatecontacts.setSupplierCode(Long.parseLong(form.get("supplierCode")));
	 	   hotelprivatecontacts.setMainContactPersonName(form.get("cPersonName"));
	 	    hotelprivatecontacts.setMainContactPersonTitle(form.get("cTitle"));
	 	    hotelprivatecontacts.setMainContactTelNo(Integer.parseInt(form.get("dTelNo")));
	 	    hotelprivatecontacts.setMainContactTelCode(Integer.parseInt(form.get("dTelCode")));
	 	    hotelprivatecontacts.setMainContactFaxNo(Integer.parseInt(form.get("dFaxNo")));
	 	    hotelprivatecontacts.setMainContactFaxCode(Integer.parseInt(form.get("dFaxCode")));
	 	    hotelprivatecontacts.setMainContactExt(Integer.parseInt(form.get("dExtNo")));
	 	    hotelprivatecontacts.setMainContactEmailAddr(form.get("dEmailAddr"));
	 	    hotelprivatecontacts.setTollFreeNo(form.get("dTollFreeTelNo"));
	 	    hotelprivatecontacts.setReservationSameAsMainContact(Boolean.parseBoolean(form.get("value1")));
	 	    hotelprivatecontacts.setReservationContactPersonName(form.get("rContactName"));
	 	    hotelprivatecontacts.setReservationContactPersonTitle(form.get("rTitle"));
	 	    hotelprivatecontacts.setReservationContactTelNo(Integer.parseInt(form.get("rDirectTelNo")));
	 	    hotelprivatecontacts.setReservationContactTelCode(Integer.parseInt(form.get("rDirectTelCode")));
	 	    hotelprivatecontacts.setDeptTelNo(Integer.parseInt(form.get("rDeptTelNo")));
	 	    hotelprivatecontacts.setDeptTelCode(Integer.parseInt(form.get("rDeptTelCode")));
	 	    hotelprivatecontacts.setDeptFaxNo(Integer.parseInt(form.get("rDeptFaxNo")));
	 	    hotelprivatecontacts.setDeptFaxCode(Integer.parseInt(form.get("rDeptFaxCode")));
	 	    hotelprivatecontacts.setDeptExtNo(Integer.parseInt(form.get("rExtNo")));
	 	    hotelprivatecontacts.setReservationContactExt(Integer.parseInt(form.get("deptExtNo")));
	 	    hotelprivatecontacts.setReservationContactEmailAddr(form.get("rEmailAddr"));
	 	    hotelprivatecontacts.setSalutation_salutation_id(Salutation.getsalutationIdIdByCode(Integer.parseInt(form.get("salutationCode"))));
	 	 	
	 		hotelprivatecontacts.save();
	 		
	    }
	    else
	    {
	    	
	    	 hotelprivatecontacts.setMainContactPersonName(form.get("cPersonName"));
		 	    hotelprivatecontacts.setMainContactPersonTitle(form.get("cTitle"));
		 	    hotelprivatecontacts.setMainContactTelNo(Integer.parseInt(form.get("dTelNo")));
		 	    hotelprivatecontacts.setMainContactTelCode(Integer.parseInt(form.get("dTelCode")));
		 	    hotelprivatecontacts.setMainContactFaxNo(Integer.parseInt(form.get("dFaxNo")));
		 	    hotelprivatecontacts.setMainContactFaxCode(Integer.parseInt(form.get("dFaxCode")));
		 	    hotelprivatecontacts.setMainContactExt(Integer.parseInt(form.get("dExtNo")));
		 	    hotelprivatecontacts.setMainContactEmailAddr(form.get("dEmailAddr"));
		 	    hotelprivatecontacts.setTollFreeNo(form.get("dTollFreeTelNo"));
		 	    hotelprivatecontacts.setReservationSameAsMainContact(Boolean.parseBoolean(form.get("value1")));
		 	    hotelprivatecontacts.setReservationContactPersonName(form.get("rContactName"));
		 	    hotelprivatecontacts.setReservationContactPersonTitle(form.get("rTitle"));
		 	    hotelprivatecontacts.setReservationContactTelNo(Integer.parseInt(form.get("rDirectTelNo")));
		 	    hotelprivatecontacts.setReservationContactTelCode(Integer.parseInt(form.get("rDirectTelCode")));
		 	    hotelprivatecontacts.setDeptTelNo(Integer.parseInt(form.get("rDeptTelNo")));
		 	    hotelprivatecontacts.setDeptTelCode(Integer.parseInt(form.get("rDeptTelCode")));
		 	    hotelprivatecontacts.setDeptFaxNo(Integer.parseInt(form.get("rDeptFaxNo")));
		 	    hotelprivatecontacts.setDeptFaxCode(Integer.parseInt(form.get("rDeptFaxCode")));
		 	    hotelprivatecontacts.setDeptExtNo(Integer.parseInt(form.get("rExtNo")));
		 	    hotelprivatecontacts.setReservationContactExt(Integer.parseInt(form.get("deptExtNo")));
		 	    hotelprivatecontacts.setReservationContactEmailAddr(form.get("rEmailAddr"));
		 	    hotelprivatecontacts.setSalutation_salutation_id(Salutation.getsalutationIdIdByCode(Integer.parseInt(form.get("salutationCode"))));
	 		hotelprivatecontacts.merge();
	 		
	    	
	    }
	    	
		return ok();
	}
	
	
	@Transactional
	public static Result getupdatebillingInfo() {

				   
	    DynamicForm form = DynamicForm.form().bindFromRequest();
		//System.out.println(form.get("Name"));
	    
	    BillingInformation billinginfo = BillingInformation.findById(Long.parseLong(form.get("supplierCode")));
	    
	    if(billinginfo == null)
	    {
	    	billinginfo =new BillingInformation();
	    	
	    	billinginfo.setInvoiceToHotel(form.get("Invoices"));
			billinginfo.setFirstName(form.get("firstName"));
			billinginfo.setLastName(form.get("lastName"));
			billinginfo.setTitle(form.get("title"));
				
			billinginfo.setEmailAddr(form.get("email"));
			billinginfo.setTelNo(Integer.parseInt(form.get("teleNumber")));
			billinginfo.setTelNoCode(Integer.parseInt(form.get("telephcode")));
			billinginfo.setFaxNo(Integer.parseInt(form.get("dirFaxNumber")));
			billinginfo.setFaxNoCode(Integer.parseInt(form.get("dirFaxcode")));
			billinginfo.setSupplierCode(Long.parseLong(form.get("supplierCode")));
			billinginfo.setExt(Integer.parseInt(form.get("Extension")));
			billinginfo.setBankservice(form.get("service"));
			
	        billinginfo.save();
	    	
	    }
	    else
	    {
	    
		billinginfo.setInvoiceToHotel(form.get("Invoices"));
		billinginfo.setFirstName(form.get("firstName"));
		billinginfo.setLastName(form.get("lastName"));
		billinginfo.setTitle(form.get("title"));
			
		billinginfo.setEmailAddr(form.get("email"));
		billinginfo.setTelNo(Integer.parseInt(form.get("telephcode")+form.get("teleNumber")));
		billinginfo.setFaxNo(Integer.parseInt(form.get("dirFaxcode")+form.get("dirFaxNumber")));
		billinginfo.setExt(Integer.parseInt(form.get("Extension")));
		billinginfo.setBankservice(form.get("service"));
		
        billinginfo.merge();
	    }
		return ok();
	}
	
	@Transactional
	public static Result getupdateComunication() {
		
				
	    DynamicForm form = DynamicForm.form().bindFromRequest();
		//System.out.println(form.get("code"));
	     
	    BusinessCommunication businesscommunication = BusinessCommunication.findById(Long.parseLong(form.get("supplierCode")));
	    
	    if(businesscommunication == null)
	    {
		businesscommunication=new BusinessCommunication(); 
		businesscommunication.setPrimaryEmailAddr(form.get("email"));
		businesscommunication.setCcEmailAddr(form.get("ccmail"));
		businesscommunication.setbooking(form.get("booking"));
		businesscommunication.setSupplierCode(Long.parseLong(form.get("supplierCode")));
		
		businesscommunication.save();
	    }
	    else
	    {
	    	businesscommunication.setPrimaryEmailAddr(form.get("email"));
			businesscommunication.setCcEmailAddr(form.get("ccmail"));
			businesscommunication.setbooking(form.get("booking"));
			
			businesscommunication.merge();
	    	
	    }
		return ok();
	}
	
	@Transactional(readOnly=true)
	private static Result getHotelRoomAmenities() {
		List<RoomAmenities> rAmenities = RoomAmenities.getRoomAmenities();
		return ok(Json.toJson(rAmenities));
	}
	
}
