/**
 * 
 */
package com.travelportal.controllers;

import java.util.HashMap;
import java.util.Map;

import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.home;

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
import com.travelportal.domain.HotelHealthAndSafety;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.HotelPrivateContacts;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.InternalContacts;
import com.travelportal.domain.Location;
import com.travelportal.domain.MarketPolicyTypes;
import com.travelportal.domain.MealType;
import com.travelportal.domain.NightLife;
import com.travelportal.domain.Salutation;
import com.travelportal.domain.ShoppingFacility;
import com.travelportal.domain.TransportationDirection;
import com.travelportal.domain.rooms.ChildPolicies;
import com.travelportal.vm.AreaAttractionsSuppVM;
import com.travelportal.vm.AreaAttractionsVM;
import com.travelportal.vm.ChildpoliciVM;
import com.travelportal.vm.HotelDescription;
import com.travelportal.vm.HotelHealthAndSafetyVM;
import com.travelportal.vm.HotelamenitiesVM;
import com.travelportal.vm.HotelmealVM;
import com.travelportal.vm.TransportationDirectionsSuppVM;
import com.travelportal.vm.TransportationDirectionsVM;

/**
 * @author 
 *
 */
public class HotelProfileController extends Controller {

/*	public static Result home() {
		//Accept the supplier code as parameter... 
		return ok(home.render("Home Page"));
	} 
*/
	public static Result viewSupplierProfile(final Long supplierCode) {
		//Accept the supplier code as parameter...
		System.out.println("view supplier profile...");
		return ok(home.render("Home Page", supplierCode));
	}
	
	@Transactional(readOnly=false)
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
		System.out.println(form.get("currencyCode"));
		System.out.print("-- " + Currency.getCurrencyByCode(Integer.parseInt(form.get("currencyCode"))));
		hotelprofile.setCurrency(Currency.getCurrencyByCode(Integer.parseInt(form.get("currencyCode"))));
		hotelprofile.setCity(City.getCityByCode(Integer.parseInt(form.get("cityCode"))));
		hotelprofile.setPartOfChain(form.get("hotelPartOfChain"));
		hotelprofile.setHotelEmailAddr(form.get("email"));
		hotelprofile.setMarketPolicyType(MarketPolicyTypes.getMarketPolicyTypesIdByCode(Integer.parseInt(form.get("marketSpecificPolicyCode"))));
		hotelprofile.setHoteBrands(HotelBrands.getHotelBrandsbyCode(Integer.parseInt(form.get("brandHotelCode"))));
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
		map.put("ID", hotelprofile.getSupplier_code());
		map.put("NAME", hotelprofile.getHotelName());
		map.put("ADDR",hotelprofile.getAddress());
		return ok(Json.toJson(map));


	}


	@Transactional(readOnly=false)
	public static Result saveAmenities() {

		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelamenitiesVM.class);
		HotelamenitiesVM hotelamenitiesVM = Json.fromJson(json, HotelamenitiesVM.class);

		HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("supplierCode")));
		hotelprofile.setAmenities(HotelAmenities.getallhotelamenities(hotelamenitiesVM.getAmenities()));
		
		return ok();
				
	}
			
	@Transactional(readOnly=false)
	public static Result saveUpdateHealthSafety() {
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelHealthAndSafetyVM.class);
		HotelHealthAndSafetyVM healthAndSafetyVM = Json.fromJson(json, HotelHealthAndSafetyVM.class);
		
		HotelHealthAndSafety hAndSafety=HotelHealthAndSafety.findById(healthAndSafetyVM.getSupplierCode());
		
		if(hAndSafety == null)
		{
			
			hAndSafety=new HotelHealthAndSafety();
		
			hAndSafety.setFireRisk(healthAndSafetyVM.getFireRisk());
			hAndSafety.setHaccpCertify(healthAndSafetyVM.getHaccpCertify());
			hAndSafety.setInternalFire(healthAndSafetyVM.getInternalFire());
			hAndSafety.setLocalTourist(healthAndSafetyVM.getLocalTourist());
			hAndSafety.setPublicLiability(healthAndSafetyVM.getPublicLiability());
			hAndSafety.setRecordsForFire(healthAndSafetyVM.getRecordsForFire());
			hAndSafety.setRecordsForHealth(healthAndSafetyVM.getRecordsForHealth());
			hAndSafety.setExpiryDate(healthAndSafetyVM.getExpiryDate());
			hAndSafety.setExpiryDate1(healthAndSafetyVM.getExpiryDate1());
			hAndSafety.setSupplierCode(healthAndSafetyVM.getSupplierCode());
		
			hAndSafety.save();
		}
		else
		{
			hAndSafety.setFireRisk(healthAndSafetyVM.getFireRisk());
			hAndSafety.setHaccpCertify(healthAndSafetyVM.getHaccpCertify());
			hAndSafety.setInternalFire(healthAndSafetyVM.getInternalFire());
			hAndSafety.setLocalTourist(healthAndSafetyVM.getLocalTourist());
			hAndSafety.setPublicLiability(healthAndSafetyVM.getPublicLiability());
			hAndSafety.setRecordsForFire(healthAndSafetyVM.getRecordsForFire());
			hAndSafety.setRecordsForHealth(healthAndSafetyVM.getRecordsForHealth());
			hAndSafety.setExpiryDate(healthAndSafetyVM.getExpiryDate());
			hAndSafety.setExpiryDate1(healthAndSafetyVM.getExpiryDate1());
			
			hAndSafety.merge();
		}
		
		return ok();
	}
	

	
	@Transactional(readOnly=false)
	public static Result saveUpdateFirePrecaution() {
	
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelHealthAndSafetyVM.class);
		HotelHealthAndSafetyVM healthAndSafetyVM = Json.fromJson(json, HotelHealthAndSafetyVM.class);
		
		HotelHealthAndSafety hAndSafety=HotelHealthAndSafety.findById(healthAndSafetyVM.getSupplierCode());
		
		if(hAndSafety == null)
		{
			
			hAndSafety=new HotelHealthAndSafety();
		
			hAndSafety.setWorkingFireAlarm(healthAndSafetyVM.getWorkingFireAlarm());
			hAndSafety.setSmokeDetectorsInPublicArea(healthAndSafetyVM.getSmokeDetectorsInPublicArea());
			hAndSafety.setSmokeDetectorsInApartment(healthAndSafetyVM.getSmokeDetectorsInApartment());
			hAndSafety.setSmokeDetectorsInGuestBedroom(healthAndSafetyVM.getSmokeDetectorsInGuestBedroom());
			hAndSafety.setSystemAtLeastAnnually(healthAndSafetyVM.getSystemAtLeastAnnually());
			hAndSafety.setInternalFireAlarmTest(healthAndSafetyVM.getInternalFireAlarmTest());
			hAndSafety.setExtinguishersInAllArea(healthAndSafetyVM.getExtinguishersInAllArea());
			hAndSafety.setEmergencyLightingInstall(healthAndSafetyVM.getEmergencyLightingInstall());
			hAndSafety.setLimitedWalkingAbilities(healthAndSafetyVM.getLimitedWalkingAbilities());
			hAndSafety.setSupplierCode(healthAndSafetyVM.getSupplierCode());
		
			hAndSafety.save();
		}
		else
		{
			hAndSafety.setWorkingFireAlarm(healthAndSafetyVM.getWorkingFireAlarm());
			hAndSafety.setSmokeDetectorsInPublicArea(healthAndSafetyVM.getSmokeDetectorsInPublicArea());
			hAndSafety.setSmokeDetectorsInApartment(healthAndSafetyVM.getSmokeDetectorsInApartment());
			hAndSafety.setSmokeDetectorsInGuestBedroom(healthAndSafetyVM.getSmokeDetectorsInGuestBedroom());
			hAndSafety.setSystemAtLeastAnnually(healthAndSafetyVM.getSystemAtLeastAnnually());
			hAndSafety.setInternalFireAlarmTest(healthAndSafetyVM.getInternalFireAlarmTest());
			hAndSafety.setExtinguishersInAllArea(healthAndSafetyVM.getExtinguishersInAllArea());
			hAndSafety.setEmergencyLightingInstall(healthAndSafetyVM.getEmergencyLightingInstall());
			hAndSafety.setLimitedWalkingAbilities(healthAndSafetyVM.getLimitedWalkingAbilities());
			
			hAndSafety.merge();
		}
		
		return ok();
	}
	
		
	@Transactional(readOnly=false)
	public static Result saveUpdateExitsAndCorridor() {
	
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelHealthAndSafetyVM.class);
		HotelHealthAndSafetyVM healthAndSafetyVM = Json.fromJson(json, HotelHealthAndSafetyVM.class);
		
		HotelHealthAndSafety hAndSafety=HotelHealthAndSafety.findById(healthAndSafetyVM.getSupplierCode());
		
		if(hAndSafety == null)
		{
			
			hAndSafety=new HotelHealthAndSafety();
		
			hAndSafety.setHowManyExits(healthAndSafetyVM.getHowManyExits());
			hAndSafety.setUnlockedAtAllTime(healthAndSafetyVM.getUnlockedAtAllTime());
			hAndSafety.setExitsClearlySigned(healthAndSafetyVM.getExitsClearlySigned());
			hAndSafety.setRoutesIlluminated(healthAndSafetyVM.getRoutesIlluminated());
			hAndSafety.setSupplierCode(healthAndSafetyVM.getSupplierCode());
			hAndSafety.setUsableStaircaseFromAllFloors(healthAndSafetyVM.getUsableStaircaseFromAllFloors());
			hAndSafety.setSupplierCode(healthAndSafetyVM.getSupplierCode());
		
			hAndSafety.save();
		}
		else
		{
			hAndSafety.setHowManyExits(healthAndSafetyVM.getHowManyExits());
			hAndSafety.setUnlockedAtAllTime(healthAndSafetyVM.getUnlockedAtAllTime());
			hAndSafety.setExitsClearlySigned(healthAndSafetyVM.getExitsClearlySigned());
			hAndSafety.setRoutesIlluminated(healthAndSafetyVM.getRoutesIlluminated());
			hAndSafety.setSupplierCode(healthAndSafetyVM.getSupplierCode());
			hAndSafety.setUsableStaircaseFromAllFloors(healthAndSafetyVM.getUsableStaircaseFromAllFloors());
			
			hAndSafety.merge();
		}
		
		return ok();
		
	}
	
	@Transactional(readOnly=false)
	public static Result saveUpdateAirCondition() {
	
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelHealthAndSafetyVM.class);
		HotelHealthAndSafetyVM healthAndSafetyVM = Json.fromJson(json, HotelHealthAndSafetyVM.class);
		
		HotelHealthAndSafety hAndSafety=HotelHealthAndSafety.findById(healthAndSafetyVM.getSupplierCode());
		
		if(hAndSafety == null)
		{
			
			hAndSafety=new HotelHealthAndSafety();
		
			hAndSafety.setCentral(healthAndSafetyVM.getCentral());
			hAndSafety.setIndependentUnits(healthAndSafetyVM.getIndependentUnits());			
			hAndSafety.setSupplierCode(healthAndSafetyVM.getSupplierCode());
		
			hAndSafety.save();
		}
		else
		{
			hAndSafety.setCentral(healthAndSafetyVM.getCentral());
			hAndSafety.setIndependentUnits(healthAndSafetyVM.getIndependentUnits());
			
			hAndSafety.merge();
		}
		
		return ok();
	}
	
	@Transactional(readOnly=false)
	public static Result saveUpdateLifts() {
	
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelHealthAndSafetyVM.class);
		HotelHealthAndSafetyVM healthAndSafetyVM = Json.fromJson(json, HotelHealthAndSafetyVM.class);
			
		HotelHealthAndSafety hAndSafety=HotelHealthAndSafety.findById(healthAndSafetyVM.getSupplierCode());
		
		if(hAndSafety == null)
		{
			
			hAndSafety=new HotelHealthAndSafety();
		
			hAndSafety.setFloorsAccessible(healthAndSafetyVM.getFloorsAccessible());
			hAndSafety.setInternalClosingDoor(healthAndSafetyVM.getInternalClosingDoor());
			hAndSafety.setRelevantSignageDisplay(healthAndSafetyVM.getRelevantSignageDisplay());
			hAndSafety.setNoSmoking(healthAndSafetyVM.getNoSmoking());
			hAndSafety.setNoUnaccompaniedChildren(healthAndSafetyVM.getNoUnaccompaniedChildren());
			hAndSafety.setEventOfFire(healthAndSafetyVM.getEventOfFire());						
			hAndSafety.setSupplierCode(healthAndSafetyVM.getSupplierCode());
		
			hAndSafety.save();
		}
		else
		{
			hAndSafety.setFloorsAccessible(healthAndSafetyVM.getFloorsAccessible());
			hAndSafety.setInternalClosingDoor(healthAndSafetyVM.getInternalClosingDoor());
			hAndSafety.setRelevantSignageDisplay(healthAndSafetyVM.getRelevantSignageDisplay());
			hAndSafety.setNoSmoking(healthAndSafetyVM.getNoSmoking());
			hAndSafety.setNoUnaccompaniedChildren(healthAndSafetyVM.getNoUnaccompaniedChildren());
			hAndSafety.setEventOfFire(healthAndSafetyVM.getEventOfFire());	
			
			hAndSafety.merge();
		}
		
		return ok();
	}
	
	@Transactional(readOnly=false)
	public static Result saveUpdateBedroomsAsndBalconies() {
	
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelHealthAndSafetyVM.class);
		HotelHealthAndSafetyVM healthAndSafetyVM = Json.fromJson(json, HotelHealthAndSafetyVM.class);
			
		HotelHealthAndSafety hAndSafety=HotelHealthAndSafety.findById(healthAndSafetyVM.getSupplierCode());
		
		if(hAndSafety == null)
		{
			
			hAndSafety=new HotelHealthAndSafety();
			
			hAndSafety.setFireSafetyInstructionsPosted(healthAndSafetyVM.getFireSafetyInstructionsPosted());
			hAndSafety.setElectricsAutomaticallyDisconnect(healthAndSafetyVM.getElectricsAutomaticallyDisconnect());
			hAndSafety.setRoomsHaveBalconies(healthAndSafetyVM.getRoomsHaveBalconies());
			hAndSafety.setBalconiesAtLeast1m(healthAndSafetyVM.getBalconiesAtLeast1m());
			hAndSafety.setGapsGreaterThan10cm(healthAndSafetyVM.getGapsGreaterThan10cm());
			hAndSafety.setAnyAdjoiningRooms(healthAndSafetyVM.getAnyAdjoiningRooms());
			hAndSafety.setHowMany(healthAndSafetyVM.getHowMany());
			hAndSafety.setSupplierCode(healthAndSafetyVM.getSupplierCode());
		
			hAndSafety.save();
		}
		else
		{
			hAndSafety.setFireSafetyInstructionsPosted(healthAndSafetyVM.getFireSafetyInstructionsPosted());
			hAndSafety.setElectricsAutomaticallyDisconnect(healthAndSafetyVM.getElectricsAutomaticallyDisconnect());
			hAndSafety.setRoomsHaveBalconies(healthAndSafetyVM.getRoomsHaveBalconies());
			hAndSafety.setBalconiesAtLeast1m(healthAndSafetyVM.getBalconiesAtLeast1m());
			hAndSafety.setGapsGreaterThan10cm(healthAndSafetyVM.getGapsGreaterThan10cm());
			hAndSafety.setAnyAdjoiningRooms(healthAndSafetyVM.getAnyAdjoiningRooms());
			hAndSafety.setHowMany(healthAndSafetyVM.getHowMany());			
			hAndSafety.merge();
		}
		
		return ok();
	}
	
	@Transactional(readOnly=false)
	public static Result saveUpdateKitchenAndHygiene() {
	
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelHealthAndSafetyVM.class);
		HotelHealthAndSafetyVM healthAndSafetyVM = Json.fromJson(json, HotelHealthAndSafetyVM.class);
			
		HotelHealthAndSafety hAndSafety=HotelHealthAndSafety.findById(healthAndSafetyVM.getSupplierCode());
		
		if(hAndSafety == null)
		{
			
			hAndSafety=new HotelHealthAndSafety();
						
			hAndSafety.setSelfCateringAccommodation(healthAndSafetyVM.getSelfCateringAccommodation());
			hAndSafety.setSelfCateringAccommodationHaveFull(healthAndSafetyVM.getSelfCateringAccommodationHaveFull());
			hAndSafety.setAllKitchenAppliancesRegularly(healthAndSafetyVM.getAllKitchenAppliancesRegularly());
			hAndSafety.setMainKitchen(healthAndSafetyVM.getMainKitchen());
			hAndSafety.setStagesOfFoodPreparation(healthAndSafetyVM.getStagesOfFoodPreparation());
			hAndSafety.setPremisesAdequatelyProofed(healthAndSafetyVM.getPremisesAdequatelyProofed());
			hAndSafety.setSupplierCode(healthAndSafetyVM.getSupplierCode());
		
			hAndSafety.save();
		}
		else
		{
			hAndSafety.setSelfCateringAccommodation(healthAndSafetyVM.getSelfCateringAccommodation());
			hAndSafety.setSelfCateringAccommodationHaveFull(healthAndSafetyVM.getSelfCateringAccommodationHaveFull());
			hAndSafety.setAllKitchenAppliancesRegularly(healthAndSafetyVM.getAllKitchenAppliancesRegularly());
			hAndSafety.setMainKitchen(healthAndSafetyVM.getMainKitchen());
			hAndSafety.setStagesOfFoodPreparation(healthAndSafetyVM.getStagesOfFoodPreparation());
			hAndSafety.setPremisesAdequatelyProofed(healthAndSafetyVM.getPremisesAdequatelyProofed());
			hAndSafety.merge();
		}
		
		return ok();
	}
	
	@Transactional(readOnly=false)
	public static Result saveUpdateAdditionalInfo() {
	
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelHealthAndSafetyVM.class);
		HotelHealthAndSafetyVM healthAndSafetyVM = Json.fromJson(json, HotelHealthAndSafetyVM.class);
			
		HotelHealthAndSafety hAndSafety=HotelHealthAndSafety.findById(healthAndSafetyVM.getSupplierCode());
		
		if(hAndSafety == null)
		{
			
			hAndSafety=new HotelHealthAndSafety();

			hAndSafety.setAdditionalInformationOrComments(healthAndSafetyVM.getAdditionalInformationOrComments());
			hAndSafety.setName(healthAndSafetyVM.getName());
			hAndSafety.setDesignation(healthAndSafetyVM.getDesignation());
			hAndSafety.setSupplierCode(healthAndSafetyVM.getSupplierCode());
		
			hAndSafety.save();
		}
		else
		{
			hAndSafety.setAdditionalInformationOrComments(healthAndSafetyVM.getAdditionalInformationOrComments());
			hAndSafety.setName(healthAndSafetyVM.getName());
			hAndSafety.setDesignation(healthAndSafetyVM.getDesignation());
			hAndSafety.merge();
		}
		
		return ok();
	}
	
	@Transactional(readOnly=false)
	public static Result saveUpdateGaswaterHeaters() {
	
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelHealthAndSafetyVM.class);
		HotelHealthAndSafetyVM healthAndSafetyVM = Json.fromJson(json, HotelHealthAndSafetyVM.class);
			
		HotelHealthAndSafety hAndSafety=HotelHealthAndSafety.findById(healthAndSafetyVM.getSupplierCode());
		
		if(hAndSafety == null)
		{
			
			hAndSafety=new HotelHealthAndSafety();
			
			hAndSafety.setGasWaterHeaters(healthAndSafetyVM.getGasWaterHeaters());
			hAndSafety.setInternal(healthAndSafetyVM.getInternal());
			hAndSafety.setExternal(healthAndSafetyVM.getExternal());
			hAndSafety.setServicing(healthAndSafetyVM.getServicing());
			hAndSafety.setMaintenance(healthAndSafetyVM.getMaintenance());			
			hAndSafety.setSupplierCode(healthAndSafetyVM.getSupplierCode());
		
			hAndSafety.save();
		}
		else
		{
			hAndSafety.setGasWaterHeaters(healthAndSafetyVM.getGasWaterHeaters());
			hAndSafety.setInternal(healthAndSafetyVM.getInternal());
			hAndSafety.setExternal(healthAndSafetyVM.getExternal());
			hAndSafety.setServicing(healthAndSafetyVM.getServicing());
			hAndSafety.setMaintenance(healthAndSafetyVM.getMaintenance());
			hAndSafety.merge();
		}
		
		return ok();
	}
	
	@Transactional(readOnly=false)
	public static Result updateMealPolicy() {

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


	@Transactional(readOnly=false)
	public static Result saveMealPolicy() {

		JsonNode json = request().body().asJson();
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




	@Transactional(readOnly=false)
	public static Result deleteChile(int id) {

		HotelMealPlan hotelmealplan = HotelMealPlan.findById(id);
		for(ChildPolicies policies : hotelmealplan.getChild()){
			policies.delete();
		}
		hotelmealplan.setChild(null);
		hotelmealplan.merge();

		return ok();
	}

	@Transactional(readOnly=false)
	public static Result deleteMealPolicy(int id) {

		HotelMealPlan hotelmealplan = HotelMealPlan.findById(id);
		for(ChildPolicies policies : hotelmealplan.getChild()){
			policies.delete();
		}
		hotelmealplan.delete();
		return ok();
	}


	@Transactional(readOnly=false)
	public static Result saveAttraction() {

		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, AreaAttractionsSuppVM.class);
		AreaAttractionsSuppVM areaattractionssuppVM = Json.fromJson(json, AreaAttractionsSuppVM.class);


		HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("supplierCode")));

		String attractionsuccess="";
		//hotelprofile.setHotelareaattraction(null);

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
					// hotelprofile.addHotelareaattraction(hotelattractions.attractionrepeat(vm.getname()));
					attractionsuccess = "no";
				}
			}

		}

		return ok(attractionsuccess);
	}


	@Transactional(readOnly=false)
	public static Result saveTransportDir() {

		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, TransportationDirectionsSuppVM.class);
		TransportationDirectionsSuppVM transportationdirectionsSuppVM = Json.fromJson(json, TransportationDirectionsSuppVM.class);

		HotelProfile hotelprofile = HotelProfile.findById(Long.parseLong(form.get("supplierCode")));
		String successdata="";

		hotelprofile.setTransportCode(null);

		for(TransportationDirectionsVM vm : transportationdirectionsSuppVM.getFindLocation()){

			TransportationDirection transportationDirection=new  TransportationDirection();

			if(transportationDirection.checklocationexe(vm.getLocationName()) == null) {	

				transportationDirection.setLocationName(vm.getLocationName());
				transportationDirection.setLocationAddr(vm.getLocationAddr());

				transportationDirection.save();

				hotelprofile.addTransportCode(transportationDirection);
				successdata="yes";
			} else {
				hotelprofile.addTransportCode(transportationDirection.checklocationexe(vm.getLocationName()));
				successdata="no";
			}

		}


		return ok(successdata);
	}


	@Transactional(readOnly=false)
	public static Result updateDescription() {

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


	@Transactional(readOnly=false)
	public static Result updateInternalInfo() {

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



	@Transactional(readOnly=false)
	public static Result updateContactInfo() {

		DynamicForm form = DynamicForm.form().bindFromRequest();

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
			hotelprivatecontacts.setReservationSameAsMainContact(form.get("reservationDetailSame"));
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
			hotelprivatecontacts.setReservationSameAsMainContact(form.get("reservationDetailSame"));
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


	@Transactional(readOnly=false)
	public static Result updateBillingInfo() {

		 DynamicForm form = DynamicForm.form().bindFromRequest();
			//System.out.println(form.get("Name"));
		    
		    BillingInformation billinginfo = BillingInformation.findById(Long.parseLong(form.get("supplierCode")));
		    
		    if(billinginfo == null)
		    {
		    	billinginfo =new BillingInformation();
		    	
		    	billinginfo.setInvoiceToHotel(form.get("invoiceToHotel"));
				billinginfo.setFirstName(form.get("aFirstName"));
				billinginfo.setLastName(form.get("aLastName"));
				billinginfo.setTitle(form.get("title"));
					
				billinginfo.setEmailAddr(form.get("dEmailAddr"));
				billinginfo.setTelNo(Integer.parseInt(form.get("dTelNo")));
				billinginfo.setTelNoCode(Integer.parseInt(form.get("dTelCode")));
				billinginfo.setFaxNo(Integer.parseInt(form.get("dFaxNo")));
				billinginfo.setFaxNoCode(Integer.parseInt(form.get("dFaxCode")));
				billinginfo.setExt(Integer.parseInt(form.get("dExtNo")));;
				billinginfo.setSupplierCode(Long.parseLong(form.get("supplierCode")));
				billinginfo.setBankservice(form.get("bankToBankTransfer"));
				
		        billinginfo.save();
		    	
		    }
		    else
		    {
		    
		    	billinginfo.setInvoiceToHotel(form.get("invoiceToHotel"));
				billinginfo.setFirstName(form.get("aFirstName"));
				billinginfo.setLastName(form.get("aLastName"));
				billinginfo.setTitle(form.get("title"));
					
				billinginfo.setEmailAddr(form.get("dEmailAddr"));
				billinginfo.setTelNo(Integer.parseInt(form.get("dTelNo")));
				billinginfo.setTelNoCode(Integer.parseInt(form.get("dTelCode")));
				billinginfo.setFaxNo(Integer.parseInt(form.get("dFaxNo")));
				billinginfo.setFaxNoCode(Integer.parseInt(form.get("dFaxCode")));
				billinginfo.setExt(Integer.parseInt(form.get("dExtNo")));
				billinginfo.setBankservice(form.get("bankToBankTransfer"));
			
	        billinginfo.merge();
		    }
			return ok();
	}

	@Transactional(readOnly=false)
	public static Result updateComunication() {


		DynamicForm form = DynamicForm.form().bindFromRequest();
		//System.out.println(form.get("code"));

		BusinessCommunication businesscommunication = BusinessCommunication.findById(Long.parseLong(form.get("supplierCode")));

		if(businesscommunication == null)
		{
			businesscommunication=new BusinessCommunication(); 
			businesscommunication.setPrimaryEmailAddr(form.get("primaryEmailAddr"));
			businesscommunication.setCcEmailAddr(form.get("ccEmailAddr"));
			businesscommunication.setbooking(form.get("booking"));
			businesscommunication.setSupplierCode(Long.parseLong(form.get("supplierCode")));

			businesscommunication.save();
		}
		else
		{			
			businesscommunication.setPrimaryEmailAddr(form.get("primaryEmailAddr"));
			businesscommunication.setCcEmailAddr(form.get("ccEmailAddr"));
			businesscommunication.setbooking(form.get("booking"));

			businesscommunication.merge();

		}
		return ok();
	}


}
