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
import com.travelportal.controllers.SupplierController.CountryVM;
import com.travelportal.controllers.SupplierController.MarketVM;
import com.travelportal.controllers.SupplierController.SelectedCountryVM;
import com.travelportal.controllers.SupplierController.martektsVM;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.Markets;
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
	public static Result getDates(long roomid,String currencyName,Long supplierCode) {
	
		List<RateMeta> rateperiod = RateMeta.getDates(roomid, currencyName,supplierCode);
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
		allotmentVM.setToPeriod(format.format(allotment.getToDate()));
		allotmentVM.setFormPeriod(format.format(allotment.getFormDate()));
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
		System.out.println(format.parse(allotmentVM.getToPeriod()));
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
			List<SelectedCountryVM> selectedCountryVM = new ArrayList<>(); 	
			for(AllocatedCitiesVM vm: allotmentarketVM.allocatedCities) {
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
			allotM.setCountry(listCity);
			
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
				System.out.println(allotmentarketVM.getFromDate());
				System.out.println(allotmentarketVM.getAllocatedCities().toString());
				
				if(allotmentarketVM.getAllotmentMarketId() == 0)
				{
					//allotment = Allotment.getRateById(allotmentVM.getSupplierCode(),format.parse(allotmentVM.getFormPeriod()),format.parse(allotmentVM.getToPeriod()),allotmentVM.getCurrencyName(),allotmentVM.getRoomId());
					
					//if(allotment == null)
				//	{
					
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
					List<SelectedCountryVM> selectedCountryVM = new ArrayList<>(); 	
					for(AllocatedCitiesVM vm: allotmentarketVM.allocatedCities) {
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
					allotM.setCountry(listCity);
					//}
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
					List<SelectedCountryVM> selectedCountryVM = new ArrayList<>(); 	
					for(AllocatedCitiesVM vm: allotmentarketVM.allocatedCities) {
						if(vm.multiSelectGroup == false && vm.name != null){
							SelectedCountryVM cityVM = new SelectedCountryVM();
							cityVM.name = vm.name;
							cityVM.ticked = vm.ticked;
							selectedCountryVM.add(cityVM);
						}
						
						System.out.println(vm.multiSelectGroup);
					}
						if(allotmentmarket != null && allotmentmarket.getCountry() != null && !allotmentmarket.getCountry().isEmpty())
						{
							allotmentmarket.getCountry().removeAll(allotmentmarket.getCountry());
							//JPA.em().merge(rates);
						}
						List<Country> listCity = new ArrayList<>();
						for(SelectedCountryVM cityvm : selectedCountryVM){
							Country _city = Country.getCountryByName(cityvm.name);
							
							if(cityvm.ticked){
								listCity.add(_city);
							}
						}
						allotmentmarket.setCountry(listCity);
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
	
		System.out.println(allotid);
		Allotment allotment = Allotment.allotmentfindById(allotid);
		AllotmentMarket deleteallotent = null;
		/*	
		for(AllotmentMarket market : allotment.getAllotmentmarket())
		{
			System.out.println(market.getAllotmentMarketId());
			if(market.getAllotmentMarketId() == allotMarkid){
			//	deleteallotent = market;
				market.setRate(null);
				market.setCountry(null);
				allotment.deleteMarketAllotRel(allotMarkid);
				allotment.deleteMarketAllot(allotMarkid);
			}			
			
		}
		
		//allotment.getAllotmentmarket().remove(deleteallotent);
		//deleteallotent.delete();
	//	allotment.merge();
		return ok();*/
		
		
		for(AllotmentMarket market : allotment.getAllotmentmarket())
		{
			if(market.getAllotmentMarketId() == allotMarkid){
				deleteallotent = market;
			}			
			
		}
		
		allotment.getAllotmentmarket().remove(deleteallotent);
		deleteallotent.country.removeAll(deleteallotent.getCountry());
		deleteallotent.getRate().removeAll(deleteallotent.getRate());
		deleteallotent.merge();
		allotment.merge();
		deleteallotent.delete();
		return ok();
	}
	
	@Transactional(readOnly = true)
	public static Result getMarketGroup(int id) {
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
				AllotmentMarket alotMarket = AllotmentMarket.findById(id);
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
		
		if(alotMarket != null && alotMarket.getCountry() != null && !alotMarket.getCountry().isEmpty())
		{
			alotMarket.getCountry().removeAll(alotMarket.getCountry());
			//JPA.em().merge(rates);
		}
		
		List<Country> listCity = new ArrayList<>();
		for(SelectedCountryVM cityvm : city){
			Country _city = Country.getCountryByName(cityvm.name);
			
			if(cityvm.ticked){
				listCity.add(_city);
			}
		}
		alotMarket.setCountry(listCity);
		return ok();

	}

	

	
}
