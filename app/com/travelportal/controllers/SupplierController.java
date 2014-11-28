package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.travelportal.domain.Currency;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.Specials;
import com.travelportal.domain.rooms.SpecialsMarket;
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
				specialsMarket.setSpecial(Specials.findSpecial(spec.promotionName, format.parse(spec.fromDate), format.parse(spec.toDate)));
				specialsMarket.save();
			}
		}
		
		return ok();
	}
	
	
	@Transactional(readOnly=false)
    public static Result deleteMarket(long id){
		SpecialsMarket.deleteSp(id);	
		return ok();
	}
	
	@Transactional(readOnly=false)
    public static Result deletePeriod(long id){
		System.out.println("**************");
		System.out.println(id);
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
					specialsMarket.setSpecial(Specials.findSpecial(spec.promotionName, format.parse(spec.fromDate), format.parse(spec.toDate)));
					specialsMarket.save();
				} else {
					SpecialsMarket specialsMarket = SpecialsMarket.findById(market.id);
					specialsMarket.setStayDays(market.stayDays);
					specialsMarket.setPayDays(market.payDays);
					specialsMarket.setTypeOfStay(market.typeOfStay);
					specialsMarket.setCombined(market.combined);
					specialsMarket.setMultiple(market.multiple);
					specialsMarket.merge();
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
				vm.id = market.getId();
				specialsVM.markets.add(vm);
			}
			
			list.add(specialsVM);
		}
		return ok(Json.toJson(list));
	}	
	
}
