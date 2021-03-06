package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

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
import com.travelportal.domain.HotelHealthAndSafety;
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
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.vm.AreaAttractionsVM;
import com.travelportal.vm.HotelBillingInformation;
import com.travelportal.vm.HotelCommunication;
import com.travelportal.vm.HotelContactInformation;
import com.travelportal.vm.HotelDescription;
import com.travelportal.vm.HotelGeneralInfoVM;
import com.travelportal.vm.HotelHealthAndSafetyVM;
import com.travelportal.vm.HotelInternalInformation;
import com.travelportal.vm.TransportationDirectionsVM;

public class TravelPortalUtilsController extends Controller {

	@Transactional(readOnly=true)
	public static Result getCountries() {
		final List<Country> countries = Country.getCountries(); 
		List<Map> country = new ArrayList<>();
 		for(Country c : countries){
 			Map m = new HashMap<>();
 			m.put("countryCode", c.getCountryCode());
 			m.put("countryName", c.getCountryName());
 			country.add(m);
		}
		return ok(Json.toJson(country));
	}

	@Transactional(readOnly=true)
	public static Result getCities(int countryCode) {
		final List<City> cities = City.getCities(countryCode);
		List<Map> city = new ArrayList<>();
 		for(City c : cities){
 			Map m = new HashMap<>();
 			m.put("id", c.getCityCode());
 			m.put("name", c.getCityName());
			city.add(m);
		}
 		
		return ok(Json.toJson(city));
	}

	@Transactional(readOnly=true)
	public static Result getChainHotels() {
		final List<HotelChain> chainHotels = HotelChain.getChainHotels();
		return ok(Json.toJson(chainHotels));
	}

	@Transactional(readOnly=true)
	public static Result getCurrency() {
		final List<Currency> currency = Currency.getCurrency();
		return ok(Json.toJson(currency));
	}

	@Transactional(readOnly=true)
	public static Result getHotelBrand() {
		final List<HotelBrands> hotelbrands = HotelBrands.gethotelbrand();
		return ok(Json.toJson(hotelbrands));
	}

	@Transactional(readOnly=true)
	public static Result getLocations(int cityId) {
		final List<Location> location = Location.getLocation(cityId);
		List<Map> _location  = new ArrayList();
		for(Location l : location){
			Map m = new HashMap<>();
			m.put("id", l.getLocationId());
			m.put("name", l.getLocationNm());
			_location.add(m);
		}
		return ok(Json.toJson(_location));
	}

	@Transactional(readOnly=true)
	public static Result getShoppingFacility() {
		final List<ShoppingFacility> shoppingfacility = ShoppingFacility.gethotelStarratings();
		return ok(Json.toJson(shoppingfacility));
	}

	@Transactional(readOnly=true)
	public static Result getNightLife() {
		final List<NightLife> nightlife = NightLife.getNightLife();
		return ok(Json.toJson(nightlife));
	}

	@Transactional(readOnly=true)
	public static Result getServices() {
		final List<HotelServices> hotelservice = HotelServices.gethotelservice();
		return ok(Json.toJson(hotelservice));
	}

	@Transactional(readOnly=true)
	public static Result getAmenities() {
		final List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(1));
		return ok(Json.toJson(hotelamenities));
	}

	@Transactional(readOnly=true)
	public static Result getSalutation() {
		final List<Salutation> salutation = Salutation.getsalutation();
		return ok(Json.toJson(salutation));
	}

	@Transactional(readOnly=true)
	public static Result getBusiness() {
		final List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(2));
		return ok(Json.toJson(hotelamenities));
	}

	@Transactional(readOnly=true)
	public static Result getLeisureSport() {
		List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(3));
		return ok(Json.toJson(hotelamenities));
	}

	@Transactional(readOnly=true)
	public static Result getStarRatings() {
		List<HotelStarRatings> hotelstarratings = HotelStarRatings.gethotelStarratings();
		return ok(Json.toJson(hotelstarratings));
	}

	@Transactional(readOnly=true)
	public static Result getMarketRate() {
		List<MarketPolicyTypes> marketpolicytypes = MarketPolicyTypes.getMarketPolicyTypes();
		return ok(Json.toJson(marketpolicytypes));
	}

	@Transactional(readOnly=true)
	public static Result getMealTypePlan(long supplierCode) {
		List<HotelMealPlan> mealtype = HotelMealPlan.getmealtype(supplierCode);
		return ok(Json.toJson(mealtype));
	}

	@Transactional(readOnly=true)
	public static Result getMealType() {
		List<MealType> mealtypes = MealType.getmealtypes();
		return ok(Json.toJson(mealtypes));
	}

	
	@Transactional(readOnly=true)
	public static Result finddescripData(long supplierCode) {
			
		HotelProfile hotelProfile = HotelProfile.findAllData(supplierCode);
		HotelDescription hoteldescription = new HotelDescription();
		
		hoteldescription.setDescription(hotelProfile.getHotelProfileDesc());
		if(hotelProfile.getLocation() != null)
		{
		hoteldescription.setHotelLocation(hotelProfile.getLocation().getLocationId());
		}
		if(hotelProfile.getShoppingFacility() != null)
		{
		hoteldescription.setShoppingFacilityCode(hotelProfile.getShoppingFacility().getId());
		}
		if(hotelProfile.getNightLife() != null)
		{
		hoteldescription.setNightLifeCode(hotelProfile.getNightLife().getNightLifeCode());
		}
		hoteldescription.setServices(hotelProfile.getIntListServices());
		hoteldescription.setLocation1(hotelProfile.getlocation1());
		hoteldescription.setLocation2(hotelProfile.getlocation2());
		hoteldescription.setLocation3(hotelProfile.getlocation3());
		hoteldescription.setSupplierCode(hotelProfile.getSupplier_code());
	
		
		return ok(Json.toJson(hoteldescription));
		
	}
	
	@Transactional(readOnly=true)
	public static Result findInternalData(long supplierCode) {
	
		HotelProfile hotelProfile = HotelProfile.findAllData(supplierCode);
		InternalContacts internalcontacts = InternalContacts.findById(supplierCode);
		HotelInternalInformation hotelinternalinformation = new HotelInternalInformation();
				
		hotelinternalinformation.setGenMgrName(hotelProfile.getHotelGeneralManager());
		hotelinternalinformation.setGenMgrEmail(hotelProfile.getGeneralMgrEmail());
		hotelinternalinformation.setBuiltYear(hotelProfile.getHotelBuiltYear());
		hotelinternalinformation.setRenovationYear(hotelProfile.getHotelRenovationYear());
		hotelinternalinformation.setWebSiteUrl(hotelProfile.getHotelWebSite());
		hotelinternalinformation.setNoOffloor(hotelProfile.getNoOfFloors());
		hotelinternalinformation.setNoOfRoom(hotelProfile.getNoOfRooms());
		hotelinternalinformation.setSafetyCompliance(hotelProfile.getFireSafetyCompliance());
		
		hotelinternalinformation.setGuestTel(internalcontacts.getGuestTelValue());
		hotelinternalinformation.setGuestTelCode(internalcontacts.getGuestTelCityCode());
		hotelinternalinformation.setGuestFax(internalcontacts.getGuestFaxValue());
		hotelinternalinformation.setGuestFaxCode(internalcontacts.getGuestFaxCityCode());
		hotelinternalinformation.setDirectTelNo(internalcontacts.getDirectTelValue());
		hotelinternalinformation.setDirectTelCode(internalcontacts.getDirectTelCityCode());
		hotelinternalinformation.setDirectFaxNo(internalcontacts.getDirectFaxValue());
		hotelinternalinformation.setDirectFaxCode(internalcontacts.getDirectFaxCityCode());
		hotelinternalinformation.setSupplierCode(internalcontacts.getSupplierCode());
		hotelinternalinformation.setCheckInTime(internalcontacts.getCheckInTime());
		hotelinternalinformation.setCheckOutTime(internalcontacts.getCheckOutTime());
		hotelinternalinformation.setRoomVoltage(internalcontacts.getRoomVoltage());
		hotelinternalinformation.setCheckInType(internalcontacts.getCheckInType());
		hotelinternalinformation.setCheckOutType(internalcontacts.getCheckOutType());
		hotelinternalinformation.setCheckTimePolicy(internalcontacts.getCheckTimePolicy());
				
		return ok(Json.toJson(hotelinternalinformation));
	}
	
	@Transactional(readOnly=true)
	public static Result findContactData(long supplierCode) {
		
		HotelPrivateContacts hotelprivatecontacts = HotelPrivateContacts.findById(supplierCode);
		HotelContactInformation hotelcontactinformation = new HotelContactInformation();
		
		
		hotelcontactinformation.setSupplierCode(hotelprivatecontacts.getSupplierCode());
		if(hotelprivatecontacts.getMainContactPersonName() != null){
		hotelcontactinformation.setcPersonName(hotelprivatecontacts.getMainContactPersonName());
		}
		if(hotelprivatecontacts.getMainContactPersonTitle() != null){
		hotelcontactinformation.setcTitle(hotelprivatecontacts.getMainContactPersonTitle());
		}
		
		hotelcontactinformation.setdTelNo(hotelprivatecontacts.getMainContactTelNo());
		hotelcontactinformation.setdTelCode(hotelprivatecontacts.getMainContactTelCode());
		hotelcontactinformation.setdFaxNo(hotelprivatecontacts.getMainContactFaxNo());
		hotelcontactinformation.setdFaxCode(hotelprivatecontacts.getMainContactFaxCode());
		hotelcontactinformation.setdExtNo(hotelprivatecontacts.getMainContactExt());
		hotelcontactinformation.setdEmailAddr(hotelprivatecontacts.getMainContactEmailAddr());
		hotelcontactinformation.setdTollFreeTelNo(hotelprivatecontacts.getTollFreeNo());
		if(hotelprivatecontacts.getSalutation_salutation_id() != null)
		{
		hotelcontactinformation.setSalutationCode(hotelprivatecontacts.getSalutation_salutation_id().getSalutationId());
		}
		hotelcontactinformation.setmPhoneNo(hotelprivatecontacts.getMainContactPhoneNo());
		hotelcontactinformation.setReservationDetailSame(hotelprivatecontacts.getReservationSameAsMainContact());
		hotelcontactinformation.setrContactName(hotelprivatecontacts.getReservationContactPersonName());
		hotelcontactinformation.setrTitle(hotelprivatecontacts.getReservationContactPersonTitle());
		hotelcontactinformation.setrDeptTelNo(hotelprivatecontacts.getDeptTelNo());
		hotelcontactinformation.setrEmailAddr(hotelprivatecontacts.getReservationContactEmailAddr());
		hotelcontactinformation.setrDeptTelCode(hotelprivatecontacts.getReservationContactTelNo());
		hotelcontactinformation.setrDeptFaxNo(hotelprivatecontacts.getDeptFaxNo());
		hotelcontactinformation.setrDeptFaxCode(hotelprivatecontacts.getDeptFaxCode());
		hotelcontactinformation.setrDirectTelNo(hotelprivatecontacts.getReservationContactTelNo());
		hotelcontactinformation.setrDirectTelCode(hotelprivatecontacts.getReservationContactTelCode());
		hotelcontactinformation.setrExtNo(hotelprivatecontacts.getReservationConstactExt());
		hotelcontactinformation.setDeptExtNo(hotelprivatecontacts.getDeptExtNo());
		//hotelcontactinformation.setrDeptTelNo(rDeptTelNo)(dTelCode);
		return ok(Json.toJson(hotelcontactinformation));
	}
	
	
	@Transactional(readOnly=true)
	public static Result findCommunicationData(long supplierCode) {
		BusinessCommunication businesscommunication = BusinessCommunication.findById(supplierCode);
		HotelCommunication hotelcommuniction = new HotelCommunication();
		hotelcommuniction.setPrimaryEmailAddr(businesscommunication.getPrimaryEmailAddr());
		hotelcommuniction.setCcEmailAddr(businesscommunication.getCcEmailAddr());
		hotelcommuniction.setSupplierCode(businesscommunication.getSupplierCode());
		hotelcommuniction.setBooking(businesscommunication.getbooking());
		
		return ok(Json.toJson(hotelcommuniction));
	}
		
	@Transactional(readOnly=true)
	public static Result findBillData(long supplierCode) {
			
		BillingInformation billinginformation = BillingInformation.findById(supplierCode);
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
		hotelbillinginformation.setBankName(billinginformation.getBankName());
		hotelbillinginformation.setAccountNo(billinginformation.getAccountNo());
		hotelbillinginformation.setBranchName(billinginformation.getBranchName());
		hotelbillinginformation.setAccountType(billinginformation.getAccountType());
		hotelbillinginformation.setSwiftCode(billinginformation.getSwiftCode());
		hotelbillinginformation.setMobileNo(billinginformation.getMainContactPhoneNo());
		if(billinginformation.getSalutationCode() != null){
			hotelbillinginformation.setSalutationCode(billinginformation.getSalutationCode().getSalutationId());
		}
		
		return ok(Json.toJson(hotelbillinginformation));
	}
	
	@Transactional(readOnly=true)
	public static Result findAmenitiesData(long supplierCode) {
		HotelProfile hotelProfile = HotelProfile.findAllData(supplierCode);
		Set<HotelAmenities> hotelamenities = hotelProfile.getAmenities();
		return ok(Json.toJson(hotelamenities));
	}
	

	@Transactional(readOnly=true)
	public static Result findAreaData(long supplierCode) {
		HotelProfile hotelProfile = HotelProfile.findAllData(supplierCode);
		List<HotelAttractions> attractions = hotelProfile.getHotelareaattraction();
		
		List<AreaAttractionsVM> areaattractionsVM = new ArrayList<>();

		for(HotelAttractions hotelattractionvm : attractions)
		{
			AreaAttractionsVM vm = new AreaAttractionsVM();
			vm.setAttraction_code(hotelattractionvm.getAttractionCode());
			vm.setName(hotelattractionvm.getAttractionNm());
			vm.setDistance(hotelattractionvm.getDistance());
			vm.setKm(hotelattractionvm.getDistanceType());
			vm.setMinutes(hotelattractionvm.getTimeRequireInMinutes());		
			areaattractionsVM.add(vm);
		}
		return ok(Json.toJson(areaattractionsVM));
	}
	
	@Transactional(readOnly=true)
	public static Result findTranDirData(long supplierCode) {
		
		HotelProfile hotelProfile = HotelProfile.findAllData(supplierCode);
		List<TransportationDirection> transportationdirections = hotelProfile.getTransportCode();
		List<TransportationDirectionsVM> transportationdirectionsVM = new ArrayList<>();

		for(TransportationDirection transportationDirection: transportationdirections)
		{
			TransportationDirectionsVM vm = new TransportationDirectionsVM();
			vm.setTransportCode(transportationDirection.getTransportCode());
			vm.setLocationName(transportationDirection.getLocationName());
			vm.setLocationAddr(transportationDirection.getLocationAddr());
			transportationdirectionsVM.add(vm);

		}
		return ok(Json.toJson(transportationdirectionsVM));
	}
	
	
	@Transactional(readOnly=true)
	public static Result findHealthAndSafData(long supplierCode) {
		
		HotelHealthAndSafety hAndSafety=HotelHealthAndSafety.findById(supplierCode);
		HotelHealthAndSafetyVM healthAndSafetyVM = new HotelHealthAndSafetyVM();
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		healthAndSafetyVM.setSupplierCode(hAndSafety.getSupplierCode());
		healthAndSafetyVM.setFireRisk(hAndSafety.getFireRisk());
		healthAndSafetyVM.setHaccpCertify(hAndSafety.getHaccpCertify());
		healthAndSafetyVM.setInternalFire(hAndSafety.getInternalFire());
		healthAndSafetyVM.setLocalTourist(hAndSafety.getLocalTourist());
		healthAndSafetyVM.setPublicLiability(hAndSafety.getPublicLiability());
		healthAndSafetyVM.setRecordsForFire(hAndSafety.getRecordsForFire());
		healthAndSafetyVM.setRecordsForHealth(hAndSafety.getRecordsForHealth());
		
		if(hAndSafety.getFireRiskexpiryDate() != null){
		healthAndSafetyVM.fireRiskexpiryDate = format.format(hAndSafety.getFireRiskexpiryDate());
		}
		
		if(hAndSafety.getHaccpCertifyexpiryDate() != null){
		healthAndSafetyVM.haccpCertifyexpiryDate =format.format(hAndSafety.getHaccpCertifyexpiryDate());
		}
		
		if(hAndSafety.getInternalFireexpiryDate() != null){
		healthAndSafetyVM.internalFireexpiryDate=format.format(hAndSafety.getInternalFireexpiryDate());
		}
		
		if(hAndSafety.getLocalTouristexpiryDate() != null){
		healthAndSafetyVM.localTouristexpiryDate=format.format(hAndSafety.getLocalTouristexpiryDate());
		}
		
		if(hAndSafety.getPublicLiabilityexpiryDate() != null){
		healthAndSafetyVM.publicLiabilityexpiryDate=format.format(hAndSafety.getPublicLiabilityexpiryDate());
		}
		
		if(hAndSafety.getRecordsForFireexpiryDate() != null){
		healthAndSafetyVM.recordsForFireexpiryDate = format.format(hAndSafety.getRecordsForFireexpiryDate());
		}
		
		if(hAndSafety.getRecordsForHealthexpiryDate() != null){
		healthAndSafetyVM.recordsForHealthexpiryDate = format.format(hAndSafety.getRecordsForHealthexpiryDate());
		}
		//healthAndSafetyVM.setExpiryDate(format.format(hAndSafety.getExpiryDate()));
		//healthAndSafetyVM.setExpiryDate1(format.format(hAndSafety.getExpiryDate1()));
		/*------------------1.FirePrecaution--------------------*/
		healthAndSafetyVM.setWorkingFireAlarm(hAndSafety.getWorkingFireAlarm());
		healthAndSafetyVM.setSmokeDetectorsInPublicArea(hAndSafety.getSmokeDetectorsInPublicArea());
		healthAndSafetyVM.setSmokeDetectorsInApartment(hAndSafety.getSmokeDetectorsInApartment());
		healthAndSafetyVM.setSmokeDetectorsInGuestBedroom(hAndSafety.getSmokeDetectorsInGuestBedroom());
		healthAndSafetyVM.setSystemAtLeastAnnually(hAndSafety.getSystemAtLeastAnnually());
		healthAndSafetyVM.setInternalFireAlarmTest(hAndSafety.getInternalFireAlarmTest());
		healthAndSafetyVM.setExtinguishersInAllArea(hAndSafety.getExtinguishersInAllArea());
		healthAndSafetyVM.setEmergencyLightingInstall(hAndSafety.getEmergencyLightingInstall());
		healthAndSafetyVM.setLimitedWalkingAbilities(hAndSafety.getLimitedWalkingAbilities());
		/*-----------------------2.Exits and Corridors-----------------------*/
		healthAndSafetyVM.setHowManyExits(hAndSafety.getHowManyExits());
		healthAndSafetyVM.setUnlockedAtAllTime(hAndSafety.getUnlockedAtAllTime());
		healthAndSafetyVM.setExitsClearlySigned(hAndSafety.getExitsClearlySigned());
		healthAndSafetyVM.setRoutesIlluminated(hAndSafety.getRoutesIlluminated());
		healthAndSafetyVM.setSupplierCode(hAndSafety.getSupplierCode());
		healthAndSafetyVM.setUsableStaircaseFromAllFloors(hAndSafety.getUsableStaircaseFromAllFloors());
		/*-----------------------3.Air Conditioning------------------------*/
		healthAndSafetyVM.setCentral(hAndSafety.getCentral());
		healthAndSafetyVM.setIndependentUnits(hAndSafety.getIndependentUnits());		
		/*-------------------------4.Lifts---------------------------------*/
		healthAndSafetyVM.setFloorsAccessible(hAndSafety.getFloorsAccessible());
		healthAndSafetyVM.setInternalClosingDoor(hAndSafety.getInternalClosingDoor());
		healthAndSafetyVM.setRelevantSignageDisplay(hAndSafety.getRelevantSignageDisplay());
		healthAndSafetyVM.setNoSmoking(hAndSafety.getNoSmoking());
		healthAndSafetyVM.setNoUnaccompaniedChildren(hAndSafety.getNoUnaccompaniedChildren());
		healthAndSafetyVM.setEventOfFire(hAndSafety.getEventOfFire());		
		/*-----------------------5.Bedrooms and Balconies--------------------------*/
		healthAndSafetyVM.setFireSafetyInstructionsPosted(hAndSafety.getFireSafetyInstructionsPosted());
		healthAndSafetyVM.setElectricsAutomaticallyDisconnect(hAndSafety.getElectricsAutomaticallyDisconnect());
		healthAndSafetyVM.setRoomsHaveBalconies(hAndSafety.getRoomsHaveBalconies());
		healthAndSafetyVM.setBalconiesAtLeast1m(hAndSafety.getBalconiesAtLeast1m());
		healthAndSafetyVM.setGapsGreaterThan10cm(hAndSafety.getGapsGreaterThan10cm());
		healthAndSafetyVM.setAnyAdjoiningRooms(hAndSafety.getAnyAdjoiningRooms());
		healthAndSafetyVM.setHowMany(hAndSafety.getHowMany());
		/*-------------------------6.Kitchen's and Hygiene---------------------------*/
				
		healthAndSafetyVM.setSelfCateringAccommodation(hAndSafety.getSelfCateringAccommodation());
		healthAndSafetyVM.setSelfCateringAccommodationHaveFull(hAndSafety.getSelfCateringAccommodationHaveFull());
		healthAndSafetyVM.setAllKitchenAppliancesRegularly(hAndSafety.getAllKitchenAppliancesRegularly());
		healthAndSafetyVM.setMainKitchen(hAndSafety.getMainKitchen());
		healthAndSafetyVM.setStagesOfFoodPreparation(hAndSafety.getStagesOfFoodPreparation());
		healthAndSafetyVM.setPremisesAdequatelyProofed(hAndSafety.getPremisesAdequatelyProofed());
	
    //	 --------------------------7.Children's Facilitie--------------------------------------------	
		
		healthAndSafetyVM.setInternalChildrenPlayArea(hAndSafety.getInternalChildrenPlayArea());
		healthAndSafetyVM.setExternalChildrenPlayArea(hAndSafety.getExternalChildrenPlayArea());
		healthAndSafetyVM.setSuperviseTheKidClub(hAndSafety.getSuperviseTheKidClub());
		healthAndSafetyVM.setQualityChecksOfEquipmentAndFurniture(hAndSafety.getQualityChecksOfEquipmentAndFurniture());
		
		if(hAndSafety.getMonthkid() != null){
		String[] monthchild = hAndSafety.getMonthkid().split(",");
		List lchild = Arrays.asList(monthchild);
		healthAndSafetyVM.setMonthkid(lchild);
		}
		/*----------------------------------8.Swimming Pool --------------------------------------------*/
		
		healthAndSafetyVM.setPoolRulesAndRegulation(hAndSafety.getPoolRulesAndRegulation());
		healthAndSafetyVM.setOpeningClosingTimes(hAndSafety.getOpeningClosingTimes());
		healthAndSafetyVM.setDepthInformation(hAndSafety.getDepthInformation());
		healthAndSafetyVM.setNoDiving(hAndSafety.getNoDiving());
		healthAndSafetyVM.setNoUnaccompaniedChildrenPool(hAndSafety.getNoUnaccompaniedChildrenPool());
		healthAndSafetyVM.setLifesavingEquipment(hAndSafety.getLifesavingEquipment());
		healthAndSafetyVM.setGlassAroundThePoolArea(hAndSafety.getGlassAroundThePoolArea());
		healthAndSafetyVM.setLifeguardOrQualifiedPerson(hAndSafety.getLifeguardOrQualifiedPerson());
		healthAndSafetyVM.setThereChildrenPool(hAndSafety.getThereChildrenPool());
		healthAndSafetyVM.setSeparatedFromTheAdultPool(hAndSafety.getSeparatedFromTheAdultPool());
		healthAndSafetyVM.setSufficientDepthMarkings(hAndSafety.getSufficientDepthMarkings());
		healthAndSafetyVM.setThePoolCleanedDaily(hAndSafety.getThePoolCleanedDaily());
		healthAndSafetyVM.setRecordsKept(hAndSafety.getRecordsKept());
		healthAndSafetyVM.setHeatedPoolInTheProperty(hAndSafety.getHeatedPoolInTheProperty());
		
		if(hAndSafety.getMonthOperational() != null){
		String[] monthOperation = hAndSafety.getMonthOperational().split(",");
		List loperation = Arrays.asList(monthOperation);
		healthAndSafetyVM.setMonthOperational(loperation);
		}
		
		/*------------------------9.Gas Water Heaters--------------------------------------------*/
		healthAndSafetyVM.setGasWaterHeaters(hAndSafety.getGasWaterHeaters());
		healthAndSafetyVM.setInternal(hAndSafety.getInternal());
		healthAndSafetyVM.setExternal(hAndSafety.getExternal());
		healthAndSafetyVM.setServicing(hAndSafety.getServicing());
		healthAndSafetyVM.setMaintenance(hAndSafety.getMaintenance());
		/*-------------------------------------------------------------------*/
		if(hAndSafety.getCctvArea() != null){
		String[] area = hAndSafety.getCctvArea().split(",");
		List larea = Arrays.asList(area);
		healthAndSafetyVM.setCctvArea(larea);
		}
		/*-------------------------11. Additional info---------------------------*/
		healthAndSafetyVM.setAdditionalInformationOrComments(hAndSafety.getAdditionalInformationOrComments());
		healthAndSafetyVM.setName(hAndSafety.getName());
		healthAndSafetyVM.setDesignation(hAndSafety.getDesignation());
		
		List<HotelHealthAndSafety> docInfo = HotelHealthAndSafety.getdocumentList(supplierCode);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("healthAndSafetyVM", healthAndSafetyVM);
		map.put("docInfo", docInfo);
		return ok(Json.toJson(map));
		
	}
	
	
	@Transactional(readOnly=true)
	public static Result getAllData(long supplierCode) {
		
		HotelProfile hotelProfile = HotelProfile.findAllData(supplierCode);
		HotelGeneralInfoVM hotelgeneralinfoVM = new HotelGeneralInfoVM();

		hotelgeneralinfoVM.setHotelNm(hotelProfile.getHotelName());
		hotelgeneralinfoVM.setSupplierNm(hotelProfile.getSupplierName());
		hotelgeneralinfoVM.setHotelAddr(hotelProfile.getAddress());
		if(hotelProfile.getCountry() != null)
		{
		hotelgeneralinfoVM.setCountryCode(hotelProfile.getCountry().getCountryCode());
		}
		if(hotelProfile.getCurrency() != null)
		{
		hotelgeneralinfoVM.setCurrencyCode(hotelProfile.getCurrency().getCurrencyCode());
		//hotelgeneralinfoVM.setCurrencyCode(hotelProfile.getCurrency().getCurrencyName());
		}
		hotelgeneralinfoVM.setHotelPartOfChain(hotelProfile.getPartOfChain());
		if(hotelProfile.getChainHotel() != null)
		{
		hotelgeneralinfoVM.setChainHotelCode(hotelProfile.getChainHotel());
		}
		hotelgeneralinfoVM.setEmail(hotelProfile.getHotelEmailAddr());
		hotelgeneralinfoVM.setPrimaryPasswd(hotelProfile.getPassword());
		hotelgeneralinfoVM.setVerifiedPasswd(hotelProfile.getVerifyPassword());
		hotelgeneralinfoVM.setCityCode(hotelProfile.getCity().getCityCode());
		if(hotelProfile.getMarketPolicyType() != null)
		{
		hotelgeneralinfoVM.setMarketSpecificPolicyCode(hotelProfile.getMarketPolicyType().getMarketPolicyTypeId());
		}
		if(hotelProfile.getHoteBrands() != null)
		{
		hotelgeneralinfoVM.setBrandHotelCode(hotelProfile.getHoteBrands());
		}
		
		if(hotelProfile.getStartRatings() != null)
		{
			
		hotelgeneralinfoVM.setStartRating(hotelProfile.getStartRatings().getId());
		}
		hotelgeneralinfoVM.setSupplierCode(hotelProfile.getSupplier_code());

		if(session().get("NAME") != null){
			hotelgeneralinfoVM.isAdmin = "Admin";	
		}else
		{
			hotelgeneralinfoVM.isAdmin = "Supplier";	
		}
			

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hotelgeneralinfo", hotelgeneralinfoVM);
		
		return ok(Json.toJson(map));

	}

	@Transactional(readOnly=true)
	private static Result getHotelRoomAmenities() {
		List<RoomAmenities> rAmenities = RoomAmenities.getRoomAmenities();
		return ok(Json.toJson(rAmenities));
	}

}
