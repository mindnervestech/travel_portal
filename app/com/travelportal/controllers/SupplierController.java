package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.domain.Country;
import com.travelportal.domain.Markets;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.FreeStay;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.Specials;
import com.travelportal.domain.rooms.SpecialsMarket;
import com.travelportal.vm.AllocatedCitiesVM;
import com.travelportal.vm.RoomType;
import com.travelportal.vm.SpecialsMarketVM;
import com.travelportal.vm.SpecialsVM;
import com.travelportal.vm.SpecialsWrapper;
//import com.travelportal.controllers.HotelRoomController.SelectedCityVM;

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
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Form<SpecialsWrapper> specialsForm = Form.form(SpecialsWrapper.class).bindFromRequest();
		List<SpecialsVM> specialsList = specialsForm.get().specialsObject;
		
		for(SpecialsVM spec: specialsList) {
			Specials specials = new Specials();
			specials.setFromDate(format.parse(spec.fromDate));
			specials.setToDate(format.parse(spec.toDate));
			specials.setPromotionName(spec.promotionName);
			specials.setSupplierCode(spec.supplierCode);
			System.out.println("+++++++++++++");
			System.out.println(spec.roomTypes);
			System.out.println("+++++++++++++");
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
				List<SelectedCountryVM> selectedCountryVM = new ArrayList<>(); 	
				for(AllocatedCitiesVM vm: market.allocatedCities) {
					if(vm.multiSelectGroup == false && vm.name != null){
						SelectedCountryVM cityVM = new SelectedCountryVM();
						cityVM.name = vm.name;
						cityVM.ticked = vm.ticked;
						selectedCountryVM.add(cityVM);
					}
					
					System.out.println(vm.multiSelectGroup);
				}
				List<Country> listCity = new ArrayList<>();
				for(SelectedCountryVM cityvm : selectedCountryVM){
					Country _city = Country.getCountryByName(cityvm.name);
					
					if(cityvm.ticked){
						listCity.add(_city);
					}
				}
				SpecM.setCountry(listCity);
				
			}
		}
		
		return ok();
	}
	
	
	@Transactional(readOnly=false)
    public static Result deleteMarket(long id){
		SpecialsMarket.deletespecialCountry(id);
		SpecialsMarket.deleteMarketSp(id);	
		
		return ok();
	}
	
	@Transactional(readOnly=false)
    public static Result deletePeriod(long id){
		System.out.println("**************");
		System.out.println(id);
		
		List<SpecialsMarket> specialsList = SpecialsMarket.findBySpecialsId(id);
		for(SpecialsMarket special : specialsList) {
			SpecialsMarket.deletespecialCountry(special.getId());
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
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
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
					System.out.println("-------------");
					System.out.println(market.applyToMarket);
					System.out.println(market.payDays);
					System.out.println("-------------");
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
						List<SelectedCountryVM> selectedCountryVM = new ArrayList<>(); 	
						for(AllocatedCitiesVM vm: market.allocatedCities) {
							if(vm.multiSelectGroup == false && vm.name != null){
								SelectedCountryVM cityVM = new SelectedCountryVM();
								cityVM.name = vm.name;
								cityVM.ticked = vm.ticked;
								selectedCountryVM.add(cityVM);
							}
							
							System.out.println(vm.multiSelectGroup);
						}
						List<Country> listCity = new ArrayList<>();
						for(SelectedCountryVM cityvm : selectedCountryVM){
							Country _city = Country.getCountryByName(cityvm.name);
							
							if(cityvm.ticked){
								listCity.add(_city);
							}
						}
						SpecM.setCountry(listCity);
				} else {
					SpecialsMarket specialsMarket = SpecialsMarket.findById(market.id);
					specialsMarket.setStayDays(market.stayDays);
					specialsMarket.setPayDays(market.payDays);
					specialsMarket.setTypeOfStay(market.typeOfStay);
					specialsMarket.setCombined(market.combined);
					specialsMarket.setMultiple(market.multiple);
					specialsMarket.setApplyToMarket(market.applyToMarket);
					specialsMarket.merge();
					
					// SpecialsMarket SpecM = SpecialsMarket.findByTopid();
					List<SelectedCountryVM> selectedCountryVM = new ArrayList<>(); 	
					for(AllocatedCitiesVM vm: market.allocatedCities) {
						if(vm.multiSelectGroup == false && vm.name != null){
							SelectedCountryVM cityVM = new SelectedCountryVM();
							cityVM.name = vm.name;
							cityVM.ticked = vm.ticked;
							selectedCountryVM.add(cityVM);
						}
						
						System.out.println(vm.multiSelectGroup);
					}
					List<Country> listCity = new ArrayList<>();
					for(SelectedCountryVM cityvm : selectedCountryVM){
						Country _city = Country.getCountryByName(cityvm.name);
						
						if(cityvm.ticked){
							listCity.add(_city);
						}
					}
					specialsMarket.setCountry(listCity);
				}
			}
		}
		return ok();
	}
	
	@Transactional(readOnly=true)
    public static Result getSpecialsData(String fromDate,String toDate,String promotionName) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
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
				RoomType type = new RoomType();
				type.roomType = room.getRoomType();
				type.roomId = room.getRoomId();
				specialsVM.roomallInfo.add(type);
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
		List<Markets> arMarkets = Markets.getMarkets();
		List<MarketVM> group = new ArrayList<MarketVM>();
		for (Markets c : arMarkets) {
			MarketVM marketvm = new MarketVM();
			martektsVM marketsvm = new martektsVM();
			marketsvm.marketCode = c.getMarketCode();
			marketsvm.marketName = c.getMarketName();

			List<CountryVM> conutryvm = new ArrayList<CountryVM>();
			List<Country> conutry = Country.getCountry(c.getMarketCode());
			for (Country _conutry : conutry) {
				
				CountryVM _conutryvm = new CountryVM();
				_conutryvm.id = _conutry.getCountryCode();
				_conutryvm.countryMarketCode = _conutry.getMarket().getMarketCode();
				_conutryvm.countryName = _conutry.getCountryName();
				if(id != 0) {
					SpecialsMarket alotMarket = SpecialsMarket.findByIdCity(id);
					System.out.println("*******************");
					System.out.println(alotMarket);
					for(Country cty : alotMarket.getCountry()){
						if(cty.getCountryCode() == _conutry.getCountryCode()){
							_conutryvm.tick = true;
							break;
						}else{
							_conutryvm.tick = false;
						}
							
					}
				} else {
					_conutryvm.tick = false;
				}
				
				conutryvm.add(_conutryvm);
			}
			marketsvm.conutryvm = conutryvm;
			marketvm.country = marketsvm;
			group.add(marketvm);
		}
		return ok(Json.toJson(group));
	}
	
	public static class MarketVM {
		public martektsVM country;

	}

	public static class martektsVM {
		public int marketCode;
		public String marketName;
		public List<CountryVM> conutryvm;
	}

	public static class CountryVM {
		public int id;
		public int countryMarketCode;
		public String countryName;
		public boolean tick;

	}
	
	public static class SelectedCountryVM {

		public String name;
		public int marketCode;
		public boolean ticked;

	}
	public static class VM {
		public int id;
		public List<SelectedCountryVM> city = new ArrayList<SelectedCountryVM>();
	}
	

	@Transactional(readOnly = false)
	public static Result setCitySelection() {
		//JsonNode json = request().body().asJson().get("id");
		JsonNode jn = request().body().asJson();
		//System.out.println(json.asInt());
		//jn.as
		
		VM c = Json.fromJson(jn, VM.class);
		int id  = c.id;
		List<SelectedCountryVM> city = c.city;
		
		AllotmentMarket alotMarket = AllotmentMarket.findById(id);
		
		/*if(alotMarket != null && alotMarket.getCountry() != null && !alotMarket.getCountry().isEmpty())
		{
			alotMarket.getCountry().removeAll(alotMarket.getCountry());
			//JPA.em().merge(rates);
		}*/
		
		List<Country> listCity = new ArrayList<>();
		for(SelectedCountryVM cityvm : city){
			Country _city = Country.getCountryByName(cityvm.name);
			
			if(cityvm.ticked){
				listCity.add(_city);
			}
		}

		//alotMarket.setCountry(listCity);
		return ok();

	}

	

	
}
