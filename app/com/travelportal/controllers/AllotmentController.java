package com.travelportal.controllers;


import java.io.File;
import java.io.IOException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.junit.experimental.theories.internal.AllMembersSupplier;



import play.Play;
import play.data.DynamicForm;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.controllers.HotelRoomController.CityVM;
import com.travelportal.controllers.HotelRoomController.CountryVM;
import com.travelportal.controllers.HotelRoomController.MarketVM;
import com.travelportal.controllers.HotelRoomController.SelectedCityVM;
import com.travelportal.controllers.HotelRoomController.VM;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelAttractions;
import com.travelportal.domain.HotelHealthAndSafety;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.ImgPath;
import com.travelportal.domain.RatePeriod;
import com.travelportal.domain.allotment.Allotment;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.domain.rooms.RoomChildPolicies;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.AllotmentVM;
import com.travelportal.vm.AreaAttractionsVM;
import com.travelportal.vm.CurrencyVM;
import com.travelportal.vm.RatePeriodVM;
import com.travelportal.vm.RateVM;
import com.travelportal.vm.RoomChildpoliciVM;
import com.travelportal.vm.RoomtypeVM;




public class AllotmentController extends Controller {
	
	
	
	@Transactional(readOnly=true)
	public static Result getDates(long roomid,String currencyName) {
	
		List<RateMeta> rateperiod = RateMeta.getDates(roomid, currencyName);
		return ok(Json.toJson(rateperiod));
		//return ok();
		/*final List<RatePeriod> rateperiod = RatePeriod.getDates(roomid,currencyid);
		List<RatePeriodVM> periodVMs = new ArrayList<RatePeriodVM>();
		for(RatePeriod period : rateperiod) {
			RatePeriodVM periodVM = new RatePeriodVM();
			periodVM.setFromPeriod(period.getFromPeriod());
			periodVM.setToPeriod(period.getToPeriod());
			periodVM.setId(period.getId());
			periodVMs.add(periodVM);
		}
		return ok(Json.toJson(periodVMs));*/
	}
	
	@Transactional(readOnly=true)
	public static Result getRates() throws ParseException {
		JsonNode json = request().body().asJson();
		Json.fromJson(json, AllotmentVM.class);
		AllotmentVM allVm = Json.fromJson(json, AllotmentVM.class);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		List<RateMeta> ratemeta = RateMeta.getRateMeta(allVm.getCurrencyName(),format.parse(allVm.getFormPeriod()),format.parse(allVm.getToPeriod()),allVm.getRoomId());
		return ok(Json.toJson(ratemeta));
		
	
	}
	
	@Transactional(readOnly=true)
	public static Result getallmentMarket() throws ParseException {
		JsonNode json = request().body().asJson();
		//DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, AllotmentVM.class);
		AllotmentVM allVm = Json.fromJson(json, AllotmentVM.class);
		
		System.out.println(allVm.getFormPeriod());
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		
		
		Allotment allotment = Allotment.getRateById(allVm.getSupplierCode(),format.parse(allVm.getFormPeriod()),format.parse(allVm.getToPeriod()),allVm.getCurrencyName(),allVm.getRoomId());
		
      // Allotment allotment = Allotment.findById(supplierCode);
		if(allotment==null) {
			return ok("");
		}
		AllotmentVM allotmentVM = new AllotmentVM();
		allotmentVM.setAllotmentId(allotment.getAllotmentId());
		allotmentVM.setSupplierCode(allotment.getSupplierCode());
		allotmentVM.setToPeriod(allotment.getToDate().toString());
		allotmentVM.setFormPeriod(allotment.getFormDate().toString());
		allotmentVM.setCurrencyName(allotment.getCurrencyId().getCurrencyName());
		allotmentVM.setRoomId(allotment.getRoomId().getRoomId());
		
		
		List<AllotmentMarketVM> marketVMs = new ArrayList<>();
		for(AllotmentMarket allMarketVM : allotment.getAllotmentmarket())
		{
			
			AllotmentMarketVM vm = new AllotmentMarketVM();
			vm.setAllocation(allMarketVM.getAllocation());
			vm.setAllotmentMarketId(allMarketVM.getAllotmentMarketId());
			vm.setChoose(allMarketVM.getChoose());
			vm.setPeriod(allMarketVM.getPeriod());
			vm.setSpecifyAllot(allMarketVM.getSpecifyAllot());
			List<Long> listInt = new ArrayList<Long>();
			for(RateMeta rate:allMarketVM.getRate()) {
				listInt.add(Long.parseLong(String.valueOf(rate.getId())));			
			}
			vm.setRate(listInt);	
			
			marketVMs.add(vm);
		
		}
		allotmentVM.setAllotmentmarket(marketVMs);
		
		return ok(Json.toJson(allotmentVM));
	
	}
	
	@Transactional(readOnly=false)
	public static Result saveAllotment() throws ParseException {
		
		JsonNode json = request().body().asJson();
		Json.fromJson(json, AllotmentVM.class);
		AllotmentVM allotmentVM = Json.fromJson(json, AllotmentVM.class);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Allotment allotment = Allotment.getRateById(allotmentVM.getSupplierCode(),format.parse(allotmentVM.getFormPeriod()),format.parse(allotmentVM.getToPeriod()),allotmentVM.getCurrencyName(),allotmentVM.getRoomId());
		System.out.println("&&&&&&&&&&&&&&&&&");
		System.out.println(allotment);
		if(allotment == null)
		{

		allotment = new Allotment();
				
		allotment.setSupplierCode(allotmentVM.getSupplierCode());
		allotment.setCurrencyId(Currency.getCurrencyByCode1(allotmentVM.getCurrencyName()));
		allotment.setRoomId(HotelRoomTypes.getHotelRoomDetailsInfo(allotmentVM.getRoomId()));
		allotment.setFormDate(format.parse(allotmentVM.getFormPeriod()));
		allotment.setToDate(format.parse(allotmentVM.getToPeriod()));
		/*allotmentVM.setToPeriod(allotment.getToDate());
		allotmentVM.setFormPeriod(allotment.getFormDate());*/
		//allotment.setRate(Rate.getrateId(allotmentVM.getRate()));
		
		
		for(AllotmentMarketVM allotmentarketVM : allotmentVM.getAllotmentmarket())
		{
			AllotmentMarket allotmentmarket = new AllotmentMarket();
			allotmentmarket.setPeriod(allotmentarketVM.getPeriod());
			allotmentmarket.setSpecifyAllot(allotmentarketVM.getSpecifyAllot());
			allotmentmarket.setAllocation(allotmentarketVM.getAllocation());
			allotmentmarket.setChoose(allotmentarketVM.getChoose());
			allotmentmarket.setRate(RateMeta.getrateId(allotmentarketVM.getRate()));
			
			allotmentmarket.save();
			allotment.addAllotmentmarket(allotmentmarket);
		}
		allotment.save();
		}
		else
		{
			
			allotment.setCurrencyId(Currency.getCurrencyByCode1(allotmentVM.getCurrencyName()));
			allotment.setRoomId(HotelRoomTypes.getHotelRoomDetailsInfo(allotmentVM.getRoomId()));
			//allotment.setRate(Rate.getrateId(allotmentVM.getRate()));
			
			
			for(AllotmentMarketVM allotmentarketVM : allotmentVM.getAllotmentmarket())
			{
				System.out.println("$$$$$$$");
				System.out.println(allotmentarketVM.getAllotmentMarketId());
				
				if(allotmentarketVM.getAllotmentMarketId() == 0)
				{
					
					AllotmentMarket allotmentmarket = new AllotmentMarket();
					
					allotmentmarket.setPeriod(allotmentarketVM.getPeriod());
					allotmentmarket.setSpecifyAllot(allotmentarketVM.getSpecifyAllot());
					allotmentmarket.setAllocation(allotmentarketVM.getAllocation());
					allotmentmarket.setChoose(allotmentarketVM.getChoose());
					allotmentmarket.setRate(RateMeta.getrateId(allotmentarketVM.getRate()));
					
					allotmentmarket.save();
					allotment.addAllotmentmarket(allotmentmarket);
				}
				else
				{
					AllotmentMarket allotmentmarket = AllotmentMarket.findById(allotmentarketVM.getAllotmentMarketId());
					
					allotmentmarket.setPeriod(allotmentarketVM.getPeriod());
					allotmentmarket.setSpecifyAllot(allotmentarketVM.getSpecifyAllot());
					allotmentmarket.setAllocation(allotmentarketVM.getAllocation());
					allotmentmarket.setChoose(allotmentarketVM.getChoose());
					allotmentmarket.setRate(RateMeta.getrateId(allotmentarketVM.getRate()));
					
					allotmentmarket.merge();
					allotment.addAllotmentmarket(allotmentmarket);
				}
			
			}
			allotment.merge();
			
		}
		
		return ok();
	}
	
	@Transactional(readOnly=true)
	public static Result getallotmentAllData(long supplierCode) {
		
		Allotment allotment = Allotment.findById(supplierCode);
		
		AllotmentVM allotmentVM = new AllotmentVM();
		
		allotmentVM.setAllotmentId(allotment.getAllotmentId());
		allotmentVM.setSupplierCode(allotment.getSupplierCode());
		allotmentVM.setCurrencyName(allotment.getCurrencyId().getCurrencyName());
		allotmentVM.setRoomId(allotment.getRoomId().getRoomId());
		/*List<Integer> listInt = new ArrayList<Integer>();
		for(Rate rate:allotment.getRate()) {
			listInt.add(rate.getId());			
		}
		allotmentVM.setRate(listInt);*/
		
		List<AllotmentMarketVM> marketVMs = new ArrayList<>();
		for(AllotmentMarket allMarketVM : allotment.getAllotmentmarket())
		{
			
			AllotmentMarketVM vm = new AllotmentMarketVM();
			vm.setAllocation(allMarketVM.getAllocation());
			vm.setAllotmentMarketId(allMarketVM.getAllotmentMarketId());
			vm.setChoose(allMarketVM.getChoose());
			vm.setPeriod(allMarketVM.getPeriod());
			vm.setSpecifyAllot(allMarketVM.getSpecifyAllot());
			List<Long> listInt = new ArrayList<Long>();
			for(RateMeta rate:allMarketVM.getRate()) {
				listInt.add(Long.parseLong(String.valueOf(rate.getId())));			
			}
			vm.setRate(listInt);	
			
			marketVMs.add(vm);
		
		}
		allotmentVM.setAllotmentmarket(marketVMs);
		
		return ok(Json.toJson(allotmentVM));
	}
	
	
	
	@Transactional(readOnly=false)
	public static Result deleteAllotmentMarket(int allotMarkid,int allotid) {
		System.out.println(allotMarkid);
		System.out.println(allotid);
		Allotment allotment = Allotment.allotmentfindById(allotid);
		AllotmentMarket deleteallotent = null;
		for(AllotmentMarket market : allotment.getAllotmentmarket())
		{
			if(market.getAllotmentMarketId() == allotMarkid){
				deleteallotent = market;
				market.setRate(null);
			}
		}
		allotment.getAllotmentmarket().remove(deleteallotent);
		deleteallotent.delete();
		allotment.merge();
		return ok();
	}
	
	/*@Transactional(readOnly=false)
	public static Result deleteDocument(int docId,int id) {

		System.out.println(docId);
		System.out.println(id);
		HotelHealthAndSafety healthAndSafety =HotelHealthAndSafety.HealthSafetyfindById(docId);
		ImgPath deletePath = null;
		for(ImgPath path : healthAndSafety.getImgpath()){
			if(path.getImgpathId() == id);
			deletePath = path;
		}
		healthAndSafety.getImgpath().remove(deletePath);
		File currentFile = new File(deletePath.getImgpath());
		currentFile.delete();
		deletePath.delete();
		
		//healthAndSafety.setImgpath(null);
		healthAndSafety.merge();
		return ok();
	}*/
	
///////
	@Transactional(readOnly = true)
	public static Result getMarketGroup(int id) {
		List<Country> country = Country.getCountries();
		List<AllotMarketVM> group = new ArrayList<AllotMarketVM>();
		for (Country c : country) {
			AllotMarketVM marketvm = new AllotMarketVM();
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
				
				AllotmentMarket alotMarket = AllotmentMarket.findById(id);
				for(City cty : alotMarket.getCities()){
					if(cty.getCityCode() == _city.getCityCode()){
						_cityvm.tick = true;
						break;
					}else{
						_cityvm.tick = false;
					}
						
				}
				cityvm.add(_cityvm);
			}
			countryvm.cityvm = cityvm;
			marketvm.country = countryvm;
			group.add(marketvm);
		}
		return ok(Json.toJson(group));
	}
	
	public static class AllotMarketVM {
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
