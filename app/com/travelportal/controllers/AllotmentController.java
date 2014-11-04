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
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.RatePeriod;
import com.travelportal.vm.CurrencyVM;
import com.travelportal.vm.RatePeriodVM;
import com.travelportal.vm.RateVM;




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
		
		
		//return ok(Json.toJson(periodVMs));
		
		/**/
	}
	
}
