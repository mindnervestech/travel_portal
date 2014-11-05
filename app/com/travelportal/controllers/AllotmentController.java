package com.travelportal.controllers;


import java.io.IOException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



import play.Play;
import play.data.DynamicForm;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelHealthAndSafety;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.Rate;
import com.travelportal.domain.RatePeriod;
import com.travelportal.domain.allotment.Allotment;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.domain.rooms.RoomChildPolicies;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.AllotmentVM;
import com.travelportal.vm.CurrencyVM;
import com.travelportal.vm.RatePeriodVM;
import com.travelportal.vm.RateVM;
import com.travelportal.vm.RoomChildpoliciVM;
import com.travelportal.vm.RoomtypeVM;




public class AllotmentController extends Controller {
	
	
	
	@Transactional(readOnly=true)
	public static Result getDates(long roomid,int currencyid) {
		final List<RatePeriod> rateperiod = RatePeriod.getDates(roomid,currencyid);
		List<RatePeriodVM> periodVMs = new ArrayList<RatePeriodVM>();
		for(RatePeriod period : rateperiod) {
			RatePeriodVM periodVM = new RatePeriodVM();
			periodVM.setFromPeriod(period.getFromPeriod());
			periodVM.setToPeriod(period.getToPeriod());
			periodVM.setId(period.getId());
			periodVMs.add(periodVM);
		}
		return ok(Json.toJson(periodVMs));
	}
	
	@Transactional(readOnly=true)
	public static Result getRates(int Id) {
		RatePeriod rate = RatePeriod.findById(Id);
		RatePeriodVM periodVMs = new RatePeriodVM();
		periodVMs.setRate(rate.getRate());
		periodVMs.setId(rate.getId());
		
		return ok(Json.toJson(periodVMs));
	
	}
	
	
	@Transactional(readOnly=false)
	public static Result saveAllotment() {
		
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, AllotmentVM.class);
		AllotmentVM allotmentVM = Json.fromJson(json, AllotmentVM.class);
		
		
		
		Allotment allotment = Allotment.findById(allotmentVM.getSupplierCode());
		
		if(allotment == null)
		{
		allotment = new Allotment();
				
		allotment.setSupplierCode(allotmentVM.getSupplierCode());
		allotment.setDatePeriodId(allotmentVM.getDatePeriodId());
		allotment.setCurrencyId(Currency.getCurrencyByCode1(allotmentVM.getCurrencyId()));
		allotment.setRoomId(HotelRoomTypes.getHotelRoomDetailsInfo(allotmentVM.getRoomId()));
		allotment.setRoomId(HotelRoomTypes.getHotelRoomDetailsInfo(allotmentVM.getRoomId()));
		allotment.setRate(Rate.getrateId(allotmentVM.getRate()));
		
		
		for(AllotmentMarketVM allotmentarketVM : allotmentVM.getAllotmentmarket())
		{
			AllotmentMarket allotmentmarket = new AllotmentMarket();
			allotmentmarket.setPeriod(allotmentarketVM.getPeriod());
			allotmentmarket.setSpecifyAllot(allotmentarketVM.getSpecifyAllot());
			allotmentmarket.setAllocation(allotmentarketVM.getAllocation());
			allotmentmarket.setChoose(allotmentarketVM.getChoose());
			
			allotmentmarket.save();
			allotment.addAllotmentmarket(allotmentmarket);
		}
		allotment.save();
		}
		
		return ok();
	}
	
	
}
