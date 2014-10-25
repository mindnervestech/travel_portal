package com.travelportal.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.data.DynamicForm;
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
import com.travelportal.domain.rooms.RoomAmenities;
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

	@Transactional(readOnly=true)
	public static Result getCountries() {
		final List<Country> countries = Country.getCountries(); 
		return ok(Json.toJson(countries));
	}

	@Transactional(readOnly=true)
	public static Result getCities(int countryCode) {
		final List<City> cities = City.getCities(countryCode);
		return ok(Json.toJson(cities));
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
	public static Result getLocations() {
		final List<Location> location = Location.getLocation();
		return ok(Json.toJson(location));
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
	public static Result getMealTypePlan() {
		List<HotelMealPlan> mealtype = HotelMealPlan.getmealtype();
		return ok(Json.toJson(mealtype));
	}

	@Transactional(readOnly=true)
	public static Result getMealType() {
		List<MealType> mealtypes = MealType.getmealtypes();
		return ok(Json.toJson(mealtypes));
	}

	@Transactional(readOnly=true)
	public static Result getAllData(long supplierCode) {
		
		System.out.print(">>>>>>sss.. suppler code " + supplierCode);
		HotelProfile hotelProfile = HotelProfile.findAllData(supplierCode);
		System.out.print("hotel name " + hotelProfile.getHotelName());
		HotelGeneralInfoVM hotelgeneralinfoVM = new HotelGeneralInfoVM();

		hotelgeneralinfoVM.setHotelNm(hotelProfile.getHotelName());
		hotelgeneralinfoVM.setHotelAddr(hotelProfile.getAddress());
		hotelgeneralinfoVM.setCountryCode(hotelProfile.getCountry().getCountryCode());
		hotelgeneralinfoVM.setCurrencyCode(hotelProfile.getCurrency().getCurrencyCode());
		hotelgeneralinfoVM.setHotelPartOfChain(hotelProfile.isPartOfChain());
		hotelgeneralinfoVM.setChainHotelCode(hotelProfile.getChainHotel().getChainHotelCode());
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
		hotelcontactinformation.setSalutationCode(hotelprivatecontacts.getSalutation_salutation_id().getSalutationId());
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

		List<HotelAttractions> attractions = hotelProfile.getHotelareaattraction();

		//AreaAttractionsSuppVM areaattractionssuppVM = new AreaAttractionsSuppVM();
		List<AreaAttractionsVM> areaattractionsVM = new ArrayList<>();

		for(HotelAttractions hotelattractionvm : attractions)
		{
			AreaAttractionsVM vm = new AreaAttractionsVM();
			vm.setAttraction_code(hotelattractionvm.getAttractionCode());
			vm.setname(hotelattractionvm.getAttractionNm());
			vm.setDistance(hotelattractionvm.getDistance());
			vm.setKm(hotelattractionvm.getDistanceType());
			vm.setMinutes(hotelattractionvm.getTimeRequireInMinutes());		
			areaattractionsVM.add(vm);
		}

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

		Set<HotelAmenities> hotelamenities = hotelProfile.getAmenities();
		/*	List<Integer> hotelamenitiesVM = new ArrayList<Integer>();
		for(HotelAmenities hAmenities : hotelamenities)
		{
			//hotelamenitiesVM.add();
		    // vm.setSupplierCode(hAmenities.getAmenitiesNm());
		}*/




		System.out.println(supplierCode);
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


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hotelgeneralinfo", hotelgeneralinfoVM);
		map.put("hoteldescription", hoteldescription);
		map.put("hotelinternalinformation", hotelinternalinformation);
		map.put("hotelcontactinformation", hotelcontactinformation);
		map.put("hotelcommuniction", hotelcommuniction);
		map.put("areaattractionsVM", areaattractionsVM);
		map.put("hotelbillinginformation", hotelbillinginformation);
		map.put("transportationdirectionsVM", transportationdirectionsVM);
		map.put("hotelamenities", hotelamenities);

		return ok(Json.toJson(map));

	}

	@Transactional(readOnly=true)
	private static Result getHotelRoomAmenities() {
		List<RoomAmenities> rAmenities = RoomAmenities.getRoomAmenities();
		return ok(Json.toJson(rAmenities));
	}

}
