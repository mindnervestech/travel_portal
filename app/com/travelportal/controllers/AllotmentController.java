package com.travelportal.controllers;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.allotment.Allotment;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.vm.AllocatedCitiesVM;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.AllotmentVM;
//import org.junit.experimental.theories.internal.AllMembersSupplier;
//import views.html.index;
//import com.sun.glass.ui.Pixels.Format;
//import com.travelportal.domain.RatePeriod;
//import com.travelportal.vm.RatePeriodVM;




public class AllotmentController extends Controller {
	
	
	
	@Transactional(readOnly=true)
	public static Result getDates(long roomid,String currencyName) {
	
		List<RateMeta> rateperiod = RateMeta.getDates(roomid, currencyName);
		System.out.println("Print data");
		System.out.println(rateperiod);
	
		return ok(Json.toJson(rateperiod));
	
	}
	
	@Transactional(readOnly=true)
	public static Result getRates() throws ParseException {
		JsonNode json = request().body().asJson();
		Json.fromJson(json, AllotmentVM.class);
		AllotmentVM allVm = Json.fromJson(json, AllotmentVM.class);
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		List<RateMeta> ratemeta = RateMeta.getRateMeta(allVm.getCurrencyName(),format.parse(allVm.getFormPeriod()),format.parse(allVm.getToPeriod()),allVm.getRoomId());
			
		List<Map> _rate  = new ArrayList();
		for(RateMeta l : ratemeta){
			Map m = new HashMap<>();
			m.put("id", l.getId());
			m.put("rateName", l.getRateName());
			_rate.add(m);
		}
		
		return ok(Json.toJson(_rate));
		
	
	}
	
	@Transactional(readOnly=true)
	public static Result getallmentMarket() throws ParseException {
		JsonNode json = request().body().asJson();
		//DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, AllotmentVM.class);
		AllotmentVM allVm = Json.fromJson(json, AllotmentVM.class);
		
		System.out.println(allVm.getFormPeriod());
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		Allotment allotment = Allotment.getRateById(allVm.getSupplierCode(),format.parse(allVm.getFormPeriod()),format.parse(allVm.getToPeriod()),allVm.getCurrencyName(),allVm.getRoomId());
		
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
			vm.setApplyMarket(allMarketVM.getApplyMarket());
			vm.setStopAllocation(allMarketVM.getStopAllocation());
			vm.setStopChoose(allMarketVM.getStopChoose());
			vm.setStopPeriod(allMarketVM.getStopPeriod());
			if(allMarketVM.getFromDate() != null){
			vm.setFromDate(format.format(allMarketVM.getFromDate()));
			}
			if(allMarketVM.getToDate() != null){
			vm.setToDate(format.format(allMarketVM.getToDate()));
			}
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
		Form<AllotmentVM> allotmentVMForm = Form.form(AllotmentVM.class).bindFromRequest();
		AllotmentVM allotmentVM = allotmentVMForm.get();
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		
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
			allotmentmarket.setApplyMarket(allotmentarketVM.getApplyMarket());
			allotmentmarket.setStopAllocation(allotmentarketVM.getStopAllocation());
			allotmentmarket.setStopChoose(allotmentarketVM.getStopChoose());
			allotmentmarket.setStopPeriod(allotmentarketVM.getStopPeriod());
			if(allotmentarketVM.getFromDate() != null ){
			allotmentmarket.setFromDate(format.parse(allotmentarketVM.getFromDate()));
			}
			if(allotmentarketVM.getToDate() != null){
			allotmentmarket.setToDate(format.parse(allotmentarketVM.getToDate()));
			}
			
			allotmentmarket.save();
			
		    allotment.addAllotmentmarket(allotmentmarket);
		    
		    
		    AllotmentMarket allotM = AllotmentMarket.findByTopid();
			System.out.println("-------------------------");
			System.out.println(allotM.getAllotmentMarketId());
			  List<SelectedCityVM> selectedCityVM = new ArrayList<>(); 	
				for(AllocatedCitiesVM vm: allotmentarketVM.allocatedCities) {
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
				allotM.setCities(listCity);
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
				System.out.println(allotmentarketVM.getAllocatedCities().toString());
				
				if(allotmentarketVM.getAllotmentMarketId() == 0)
				{
					allotment = Allotment.getRateById(allotmentVM.getSupplierCode(),format.parse(allotmentVM.getFormPeriod()),format.parse(allotmentVM.getToPeriod()),allotmentVM.getCurrencyName(),allotmentVM.getRoomId());
					
					if(allotment == null)
					{
					
					AllotmentMarket allotmentmarket = new AllotmentMarket();
					//System.out.println(allotmentmarket.get);
					
					allotmentmarket.setPeriod(allotmentarketVM.getPeriod());
					allotmentmarket.setSpecifyAllot(allotmentarketVM.getSpecifyAllot());
					allotmentmarket.setAllocation(allotmentarketVM.getAllocation());
					allotmentmarket.setChoose(allotmentarketVM.getChoose());
					allotmentmarket.setRate(RateMeta.getrateId(allotmentarketVM.getRate()));
					allotmentmarket.setApplyMarket(allotmentarketVM.getApplyMarket());
					allotmentmarket.setStopAllocation(allotmentarketVM.getStopAllocation());
					allotmentmarket.setStopChoose(allotmentarketVM.getStopChoose());
					allotmentmarket.setStopPeriod(allotmentarketVM.getStopPeriod());
					if(allotmentarketVM.getFromDate() != null ){
					allotmentmarket.setFromDate(format.parse(allotmentarketVM.getFromDate()));
					}
					if(allotmentarketVM.getToDate() != null ){
					allotmentmarket.setToDate(format.parse(allotmentarketVM.getToDate()));
					}
					
					allotmentmarket.save();
					allotment.addAllotmentmarket(allotmentmarket);
					System.out.println("AddNew");
					
					AllotmentMarket allotM = AllotmentMarket.findByTopid();
					System.out.println("-------------------------");
					System.out.println(allotM.getAllotmentMarketId());
					  List<SelectedCityVM> selectedCityVM = new ArrayList<>(); 	
						for(AllocatedCitiesVM vm: allotmentarketVM.allocatedCities) {
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
						allotM.setCities(listCity);
					}
				}
				else
				{
					AllotmentMarket allotmentmarket = AllotmentMarket.findById(allotmentarketVM.getAllotmentMarketId());
					
					allotmentmarket.setPeriod(allotmentarketVM.getPeriod());
					allotmentmarket.setSpecifyAllot(allotmentarketVM.getSpecifyAllot());
					allotmentmarket.setAllocation(allotmentarketVM.getAllocation());
					allotmentmarket.setChoose(allotmentarketVM.getChoose());
					allotmentmarket.setRate(RateMeta.getrateId(allotmentarketVM.getRate()));
					allotmentmarket.setApplyMarket(allotmentarketVM.getApplyMarket());
					allotmentmarket.setStopAllocation(allotmentarketVM.getStopAllocation());
					allotmentmarket.setStopChoose(allotmentarketVM.getStopChoose());
					allotmentmarket.setStopPeriod(allotmentarketVM.getStopPeriod());
					if(allotmentarketVM.getFromDate() != null ){
					allotmentmarket.setFromDate(format.parse(allotmentarketVM.getFromDate()));
					}
					if(allotmentarketVM.getToDate() != null ){
					allotmentmarket.setToDate(format.parse(allotmentarketVM.getToDate()));
					}
					
					allotmentmarket.merge();
					allotment.addAllotmentmarket(allotmentmarket);
					System.out.println("update");
					//AllotmentMarket allotM = AllotmentMarket.findByTopid();
					//System.out.println("-------------------------");
					//System.out.println(allotM.getAllotmentMarketId());
					  List<SelectedCityVM> selectedCityVM = new ArrayList<>(); 	
						for(AllocatedCitiesVM vm: allotmentarketVM.allocatedCities) {
							if(vm.multiSelectGroup == false && vm.name != null){
								SelectedCityVM cityVM = new SelectedCityVM();
								cityVM.name = vm.name;
								cityVM.ticked = vm.ticked;
								selectedCityVM.add(cityVM);
							}
							
							System.out.println(vm.multiSelectGroup);
						}
						/*if(allotmentmarket != null && allotmentmarket.getCities() != null && !allotmentmarket.getCities().isEmpty())
						{
							allotmentmarket.getCities().removeAll(allotmentmarket.getCities());
							//JPA.em().merge(rates);
						}*/
						List<City> listCity = new ArrayList<>();
						for(SelectedCityVM cityvm : selectedCityVM){
							City _city = City.getCitiByName(cityvm.name);
							
							if(cityvm.ticked){
								listCity.add(_city);
							}
						}
						allotmentmarket.setCities(listCity);
				}
			
			}
			allotment.merge();
			
		}
		
		return ok();
	}
	
	@Transactional(readOnly=true)
	public static Result getallotmentAllData(long supplierCode) {
		
		Allotment allotment = Allotment.findById(supplierCode);
		DateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
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
			vm.setApplyMarket(allMarketVM.getApplyMarket());
			vm.setStopAllocation(allMarketVM.getStopAllocation());
			vm.setStopChoose(allMarketVM.getStopChoose());
			vm.setStopPeriod(allMarketVM.getStopPeriod());
			if(allMarketVM.getFromDate() != null){
			vm.setFromDate(format1.format(allMarketVM.getFromDate()));
			}
			if(allMarketVM.getToDate() != null){
			vm.setToDate(format1.format(allMarketVM.getToDate()));
			}
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
	
		Allotment allotment = Allotment.allotmentfindById(allotid);
		AllotmentMarket deleteallotent = null;
		for(AllotmentMarket market : allotment.getAllotmentmarket())
		{
			if(market.getAllotmentMarketId() == allotMarkid){
				deleteallotent = market;
				market.setRate(null);
				market.setCities(null);
			}			
			
		}
		
		allotment.getAllotmentmarket().remove(deleteallotent);
		deleteallotent.delete();
		allotment.merge();
		return ok();
		
		/*
		for(AllotmentMarket market : allotment.getAllotmentmarket())
		{
			if(market.getAllotmentMarketId() == allotMarkid){
				deleteallotent = market;
			}			
			
		}
		
		allotment.getAllotmentmarket().remove(deleteallotent);
		deleteallotent.cities.removeAll(deleteallotent.getCities());
		deleteallotent.getRate().removeAll(deleteallotent.getRate());
		deleteallotent.merge();
		allotment.merge();
		deleteallotent.delete();
		return ok();*/
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
				if(id != 0) {
				AllotmentMarket alotMarket = AllotmentMarket.findById(id);
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
