package com.travelportal.controllers;

import java.util.ArrayList;
import java.util.List;

import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.travelportal.domain.Currency;
import com.travelportal.domain.admin.CurrencyExchangeRate;
import com.travelportal.vm.CurrencyExchangeVM;
import com.travelportal.vm.CurrencyVM;

public class ManageExchangeRateController extends Controller {

	
	@Transactional(readOnly=true)
	public static Result exchangeCurrency() {
		 List<CurrencyVM> cList = new ArrayList<>();
		final List<Currency> currency = Currency.getCurrency();
		for(Currency cc:currency){
			if(cc.getCurrencyCode() == 62 || cc.getCurrencyCode() == 26){
				CurrencyVM cVm = new CurrencyVM();
				cVm.currencyCode = cc.getCurrencyCode();
				cVm.currencyName = cc.getCurrencyName();
				cVm.id = cc.getId();
				cList.add(cVm);
			}
		}
		return ok(Json.toJson(cList));
	}
	
	
	@Transactional(readOnly=false)
	public static Result saveCurrencyExchangeRate() {

		DynamicForm form = DynamicForm.form().bindFromRequest();

		List<CurrencyExchangeRate> cList = CurrencyExchangeRate.findCurrencyRate(Integer.parseInt(form.get("currencySelect")));
		
			if(cList != null){
				for(CurrencyExchangeRate cRate: cList){
					cRate.delete();
				}
			}
		
			CurrencyExchangeRate cRateThb = new CurrencyExchangeRate();
			cRateThb.setCurrId(Currency.getCurrencyByCode(Integer.parseInt(form.get("currencySelect"))));
			cRateThb.setCurrencyName("THB");
			cRateThb.setCurrencyRate(Double.parseDouble(form.get("curr_THB")));
			cRateThb.save();
		
			CurrencyExchangeRate cRateSgd = new CurrencyExchangeRate();
			cRateSgd.setCurrId(Currency.getCurrencyByCode(Integer.parseInt(form.get("currencySelect"))));
			cRateSgd.setCurrencyName("SGD");
			cRateSgd.setCurrencyRate(Double.parseDouble(form.get("curr_SGD")));
			cRateSgd.save();
			
			CurrencyExchangeRate cRateMyr = new CurrencyExchangeRate();
			cRateMyr.setCurrId(Currency.getCurrencyByCode(Integer.parseInt(form.get("currencySelect"))));
			cRateMyr.setCurrencyName("MYR");
			cRateMyr.setCurrencyRate(Double.parseDouble(form.get("curr_MYR")));
			cRateMyr.save();
			
			CurrencyExchangeRate cRateInr = new CurrencyExchangeRate();
			cRateInr.setCurrId(Currency.getCurrencyByCode(Integer.parseInt(form.get("currencySelect"))));
			cRateInr.setCurrencyName("INR");
			cRateInr.setCurrencyRate(Double.parseDouble(form.get("curr_INR")));
			cRateInr.save();
		
				
		return ok();
	}	
	
	@Transactional(readOnly=false)
	public static Result getExchangeRate(Integer currId) {
		
		List<CurrencyExchangeRate> cList = CurrencyExchangeRate.findCurrencyRate(currId);
		CurrencyExchangeVM cVm = new CurrencyExchangeVM();
		for(CurrencyExchangeRate cExchangeRate:cList){
			cVm.currencySelect = cExchangeRate.getCurrId().getId();
			if(cExchangeRate.getCurrencyName().equals("INR")){
				cVm.curr_INR = cExchangeRate.getCurrencyRate();
			}
			if(cExchangeRate.getCurrencyName().equals("THB")){
				cVm.curr_THB = cExchangeRate.getCurrencyRate();
			}
			if(cExchangeRate.getCurrencyName().equals("SGD")){
				cVm.curr_SGD = cExchangeRate.getCurrencyRate();
			}
			if(cExchangeRate.getCurrencyName().equals("MYR")){
				cVm.curr_MYR = cExchangeRate.getCurrencyRate();
			}
		}
		
		return ok(Json.toJson(cVm));
		
	}
	

}



