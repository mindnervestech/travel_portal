package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.controllers.AllotmentController.AllotMarketVM;
import com.travelportal.controllers.AllotmentController.CityVM;
import com.travelportal.controllers.AllotmentController.CountryVM;
import com.travelportal.controllers.AllotmentController.VM;
import com.travelportal.controllers.HotelRoomController.SelectedCityVM;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.FreeStay;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.Specials;
import com.travelportal.domain.rooms.SpecialsMarket;
import com.travelportal.vm.AllocatedCitiesVM;
import com.travelportal.vm.SpecialsMarketVM;
import com.travelportal.vm.SpecialsVM;
import com.travelportal.vm.SpecialsWrapper;

import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class SupplierController extends Controller {

	@Transactional(readOnly=true)
    public static Result getSpecialsObject() {
		SpecialsVM specialsVM = new SpecialsVM();
		SpecialsMarketVM vm = new SpecialsMarketVM();
		specialsVM.markets.add(vm);
		
		return ok(Json.toJson(specialsVM));
	}
	
	
	@Transactional(readOnly=true)
    public static Result getPeriod(long supplierCode) {
		System.out.println("*********Call Ok *****************8");
		List<Specials> list = Specials.getperiod(supplierCode);
		return ok(Json.toJson(list));
		//return ok();
		
	}
	
	@Transactional(readOnly=true)
	 public static Result getfreeStay() {
		System.out.println("*********Call Ok *****************8");
		List<FreeStay> freelist = FreeStay.getFreeStay();
		return ok(Json.toJson(freelist));
				
	}
	
	
	@Transactional(readOnly=false)
    public static Result saveSpecialsObject() throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Form<SpecialsWrapper> specialsForm = Form.form(SpecialsWrapper.class).bindFromRequest();
		List<SpecialsVM> specialsList = specialsForm.get().specialsObject;
		
		for(SpecialsVM spec: specialsList) {
			Specials specials = new Specials();
			specials.setFromDate(format.parse(spec.fromDate));
			specials.setToDate(format.parse(spec.toDate));
			specials.setPromotionName(spec.promotionName);
			specials.setSupplierCode(spec.supplierCode);
			specials.setRoomTypes(HotelRoomTypes.findByListName(spec.roomTypes));
			specials.save();
			
			for(SpecialsMarketVM market: spec.markets) {
				SpecialsMarket specialsMarket = new SpecialsMarket();
				specialsMarket.setStayDays(market.stayDays);
				specialsMarket.setPayDays(market.payDays);
				specialsMarket.setTypeOfStay(market.typeOfStay);
				specialsMarket.setCombined(market.combined);
				specialsMarket.setMultiple(market.multiple);
				specialsMarket.setApplyToMarket(market.applyToMarket);
				specialsMarket.setSpecial(Specials.findSpecial(spec.promotionName, format.parse(spec.fromDate), format.parse(spec.toDate)));
				specialsMarket.save();
				
				//RateMeta rateObject = RateMeta.findRateMeta(rate.rateName,rate.currency,format.parse(rate.fromDate),format.parse(rate.toDate),HotelRoomTypes.findByName(rate.roomType));
				 SpecialsMarket SpecM = SpecialsMarket.findByTopid();
				List<SelectedCityVM> selectedCityVM = new ArrayList<>(); 	
				for(AllocatedCitiesVM vm: market.allocatedCities) {
					if(vm.multiSelectGroup == false && vm.name != null){
						SelectedCityVM cityVM = new SelectedCityVM();
						cityVM.name = vm.name;
						cityVM.ticked = vm.ticked;
						selectedCityVM.add(cityVM);
					}
					
					System.out.println(vm.multiSelectGroup);
				}
				List<City> listCity = new ArrayList<>();
				for(SelectedCityVM cityvm : selectedCityVM){
					City _city = City.getCitiByName(cityvm.name);
					
					if(cityvm.ticked){
						listCity.add(_city);
					}
				}
				SpecM.setCities(listCity);
				
			}
		}
		
		return ok();
	}
	
	
	@Transactional(readOnly=false)
    public static Result deleteMarket(long id){
		SpecialsMarket.deletespecialCity(id);
		SpecialsMarket.deleteMarketSp(id);	
		
		return ok();
	}
	
	@Transactional(readOnly=false)
    public static Result deletePeriod(long id){
		System.out.println("**************");
		System.out.println(id);
		
		List<SpecialsMarket> specialsList = SpecialsMarket.findBySpecialsId(id);
		for(SpecialsMarket special : specialsList) {
			SpecialsMarket.deletespecialCity(special.getId());
			//specials.setFromDate(format.parse(spec.fromDate));
			
		}
		
		
		SpecialsMarket.deleteSp(id);		
		
		
		Specials spe = Specials.findBySpecialsID(id);
		spe.setRoomTypes(null);
		spe.delete();
				
		
		return ok();
	}
	
	@Transactional(readOnly=false)
    public static Result updateSpecials() throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Form<SpecialsWrapper> specialsForm = Form.form(SpecialsWrapper.class).bindFromRequest();
		List<SpecialsVM> specialsList = specialsForm.get().specialsObject;
		
		for(SpecialsVM spec: specialsList) {
			Specials specials = Specials.findSpecial(spec.promotionName, format.parse(spec.fromDate), format.parse(spec.toDate));
			specials.setFromDate(format.parse(spec.fromDate));
			specials.setToDate(format.parse(spec.toDate));
			specials.setPromotionName(spec.promotionName);
			specials.setSupplierCode(spec.supplierCode);
			specials.setRoomTypes(HotelRoomTypes.findByListName(spec.roomTypes));
			specials.merge();
			
			for(SpecialsMarketVM market: spec.markets) {
				if(market.id == 0) {
					SpecialsMarket specialsMarket = new SpecialsMarket();
					specialsMarket.setStayDays(market.stayDays);
					specialsMarket.setPayDays(market.payDays);
					specialsMarket.setTypeOfStay(market.typeOfStay);
					specialsMarket.setCombined(market.combined);
					specialsMarket.setMultiple(market.multiple);
					specialsMarket.setApplyToMarket(market.applyToMarket);
					specialsMarket.setSpecial(Specials.findSpecial(spec.promotionName, format.parse(spec.fromDate), format.parse(spec.toDate)));
					specialsMarket.save();
					
					 SpecialsMarket SpecM = SpecialsMarket.findByTopid();
						List<SelectedCityVM> selectedCityVM = new ArrayList<>(); 	
						for(AllocatedCitiesVM vm: market.allocatedCities) {
							if(vm.multiSelectGroup == false && vm.name != null){
								SelectedCityVM cityVM = new SelectedCityVM();
								cityVM.name = vm.name;
								cityVM.ticked = vm.ticked;
								selectedCityVM.add(cityVM);
							}
							
							System.out.println(vm.multiSelectGroup);
						}
						List<City> listCity = new ArrayList<>();
						for(SelectedCityVM cityvm : selectedCityVM){
							City _city = City.getCitiByName(cityvm.name);
							
							if(cityvm.ticked){
								listCity.add(_city);
							}
						}
						SpecM.setCities(listCity);
				} else {
					SpecialsMarket specialsMarket = SpecialsMarket.findById(market.id);
					specialsMarket.setStayDays(market.stayDays);
					specialsMarket.setPayDays(market.payDays);
					specialsMarket.setTypeOfStay(market.typeOfStay);
					specialsMarket.setCombined(market.combined);
					specialsMarket.setMultiple(market.multiple);
					specialsMarket.merge();
					
					// SpecialsMarket SpecM = SpecialsMarket.findByTopid();
						List<SelectedCityVM> selectedCityVM = new ArrayList<>(); 	
						for(AllocatedCitiesVM vm: market.allocatedCities) {
							if(vm.multiSelectGroup == false && vm.name != null){
								SelectedCityVM cityVM = new SelectedCityVM();
								cityVM.name = vm.name;
								cityVM.ticked = vm.ticked;
								selectedCityVM.add(cityVM);
							}
							
							System.out.println(vm.multiSelectGroup);
						}
						List<City> listCity = new ArrayList<>();
						for(SelectedCityVM cityvm : selectedCityVM){
							City _city = City.getCitiByName(cityvm.name);
							
							if(cityvm.ticked){
								listCity.add(_city);
							}
						}
						specialsMarket.setCities(listCity);
				}
			}
		}
		return ok();
	}
	
	@Transactional(readOnly=true)
    public static Result getSpecialsData(String fromDate,String toDate,String promotionName) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<SpecialsVM> list = new ArrayList<>();
		List<Specials> specialsList = Specials.findSpecialByDate(format.parse(fromDate), format.parse(toDate),promotionName);
		for(Specials special : specialsList) {
			SpecialsVM specialsVM = new SpecialsVM();
			specialsVM.id = special.getId();
			specialsVM.fromDate = format.format(special.getFromDate());
			specialsVM.toDate = format.format(special.getToDate());
			specialsVM.promotionName = special.getPromotionName();
			specialsVM.supplierCode = special.getSupplierCode();
			for(HotelRoomTypes room : special.getRoomTypes()) {
				specialsVM.roomTypes.add(room.getRoomType());
			}
			
			List<SpecialsMarket> marketList = SpecialsMarket.findBySpecialsId(special.getId());
			for(SpecialsMarket market : marketList) {
				SpecialsMarketVM vm = new SpecialsMarketVM();
				vm.combined = market.isCombined();
				vm.multiple = market.isMultiple();
				vm.payDays = market.getPayDays();
				vm.stayDays = market.getStayDays();
				vm.typeOfStay = market.getTypeOfStay();
				vm.applyToMarket = market.getApplyToMarket();
				vm.id = market.getId();
				specialsVM.markets.add(vm);
			}
			
			list.add(specialsVM);
		}
		return ok(Json.toJson(list));
	}	
	
	
	@Transactional(readOnly = true)
	public static Result getMarketGroup(long id) {
		List<Country> country = Country.getCountries();
		List<MarketVM> group = new ArrayList<MarketVM>();
		for (Country c : country) {
			MarketVM marketvm = new MarketVM();
			CountryVM countryvm = new CountryVM();
			countryvm.countryCode = c.getCountryCode();
			countryvm.countryName = c.getCountryName();

			List<CityVM> cityvm = new ArrayList<CityVM>();
			List<City> city = City.getCities(c.getCountryCode());
			for (City _city : city) {
				
				CityVM _cityvm = new CityVM();
				_cityvm.id = _city.getCityCode();
				_cityvm.cityCountryCode = _city.getCountry().getCountryCode();
				_cityvm.cityName = _city.getCityName();
				if(id != 0) {
					SpecialsMarket alotMarket = SpecialsMarket.findByIdCity(id);
					System.out.println("*******************");
					System.out.println(alotMarket);
				for(City cty : alotMarket.getCities()){
					if(cty.getCityCode() == _city.getCityCode()){
						_cityvm.tick = true;
						break;
					}else{
						_cityvm.tick = false;
					}
						
				}
			} else {
				_cityvm.tick = false;
				}
				cityvm.add(_cityvm);
			}
			countryvm.cityvm = cityvm;
			marketvm.country = countryvm;
			group.add(marketvm);
		}
		return ok(Json.toJson(group));
	}
	
	public static class MarketVM {
		public CountryVM country;

	}

	public static class CountryVM {
		public int countryCode;
		public String countryName;
		public List<CityVM> cityvm;
	}

	public static class CityVM {
		public int id;
		public int cityCountryCode;
		public String cityName;
		public boolean tick;

	}
	
	public static class SelectedCityVM {

		public String name;
		public int countryCode;
		public boolean ticked;

	}
	public static class VM {
		public int id;
		public List<SelectedCityVM> city = new ArrayList<SelectedCityVM>();
	}
	

	@Transactional(readOnly = false)
	public static Result setCitySelection() {
		//JsonNode json = request().body().asJson().get("id");
		JsonNode jn = request().body().asJson();
		//System.out.println(json.asInt());
		//jn.as
		
		VM c = Json.fromJson(jn, VM.class);
		int id  = c.id;
		List<SelectedCityVM> city = c.city;
		AllotmentMarket alotMarket = AllotmentMarket.findById(id);
		
		if(alotMarket != null && alotMarket.getCities() != null && !alotMarket.getCities().isEmpty())
		{
			alotMarket.getCities().removeAll(alotMarket.getCities());
			//JPA.em().merge(rates);
		}
		
		List<City> listCity = new ArrayList<>();
		for(SelectedCityVM cityvm : city){
			City _city = City.getCitiByName(cityvm.name);
			
			if(cityvm.ticked){
				listCity.add(_city);
			}
		}
		alotMarket.setCities(listCity);
		return ok();

	}

	

	
}
