package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.controllers.AllotmentController.AllotMarketVM;
import com.travelportal.controllers.AllotmentController.CityVM;
import com.travelportal.controllers.AllotmentController.CountryVM;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.Location;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.vm.AgentRegistrationVM;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.HotelRegistrationVM;
import com.travelportal.vm.HotelSignUpVM;
import com.travelportal.vm.NormalRateVM;
import com.travelportal.vm.RateDetailsVM;
import com.travelportal.vm.RateVM;
import com.travelportal.vm.RoomType;
import com.travelportal.vm.SpecialRateVM;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class MarkUpController extends Controller {

	
	@Transactional(readOnly=true)
	public static Result getSupplier() {
		final List<HotelRegistration> list = HotelRegistration.getAllApprovedUsers();
		List<HotelRegistrationVM> vm = new ArrayList<>();
		for(HotelRegistration hotel : list) {
			HotelRegistrationVM hotelRegistrationVM = new HotelRegistrationVM(hotel);
			vm.add(hotelRegistrationVM);
		}
		
		return ok(Json.toJson(vm));
		
	}
	
	
	@Transactional(readOnly=true)
	public static Result getAgent() {
		List<Country> aList = AgentRegistration.getAllApprovedAgent();
		List<agentfullDataVM> group = new ArrayList<agentfullDataVM>();
		
		for(Country aRegistration : aList){
			agentfullDataVM marketvm = new agentfullDataVM();
			agentDataVM countryvm = new agentDataVM();
			countryvm.countryCode = aRegistration.getCountryCode();
			countryvm.countryName = aRegistration.getCountryName();
			
			List<AgentRegistrationVM> agentRegistrationVMs= new ArrayList<AgentRegistrationVM>();
			List<AgentRegistration> agList = AgentRegistration.getAgentData(aRegistration.getCountryCode());
			for (AgentRegistration _agent : agList) {
				AgentRegistrationVM agRegist = new AgentRegistrationVM(_agent);
				agRegist.id = _agent.getId();
				agRegist.firstName = _agent.getFirstName();
				agRegist.lastName = _agent.getLastName();
				agentRegistrationVMs.add(agRegist);
				
			}
			countryvm.agentDatavm = agentRegistrationVMs;
			marketvm.agentfullData = countryvm;
			group.add(marketvm);
		}
		return ok(Json.toJson(group));
		
	}
	
	public static class agentfullDataVM {
		public agentDataVM agentfullData;

	}
	public static class agentDataVM {
		public long countryCode;
		public String countryName;
		public List<AgentRegistrationVM> agentDatavm;

	}
	
	

	
	@Transactional(readOnly=true)
	public static Result getSupplierRate(Long code) {
		System.out.println(code);
		//List<RateMeta> rateMetas = RateMeta.getRateSupplier(code);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<RateVM> list = new ArrayList<>();
		List<RateMeta> rateMeta = RateMeta.getRateSupplier(code);
		for(RateMeta rate:rateMeta) {
			RateDetails rateDetails = RateDetails.findByRateMetaId(rate.getId());
			List<PersonRate> personRate = PersonRate.findByRateMetaId(rate.getId());
			List<CancellationPolicy> cancellation = CancellationPolicy.findByRateMetaId(rate.getId());
			
			RateVM rateVM = new RateVM();
			rateVM.setCurrency(rate.getCurrency());
			rateVM.setFromDate(format.format(rate.getFromDate()));
			rateVM.setToDate(format.format(rate.getToDate()));
			rateVM.setRoomId(rate.getRoomType().getRoomId());
			rateVM.setIsSpecialRate(rateDetails.isSpecialRate());
			rateVM.setRateName(rate.getRateName());
			rateVM.setId(rate.getId());
			rateVM.applyToMarket = rateDetails.isApplyToMarket();
			
			NormalRateVM normalRateVM = new NormalRateVM();
			SpecialRateVM specialRateVM = new SpecialRateVM();
			
			for(PersonRate person:personRate) {
				
				if(person.isNormal() == true){
					RateDetailsVM vm = new RateDetailsVM(person);
					normalRateVM.rateDetails.add(vm);
				}
				
				if(person.isNormal() == false) {
					RateDetailsVM vm = new RateDetailsVM(person);
					specialRateVM.rateDetails.add(vm);
				}
				
			}
				if(rateDetails.getSpecialDays() != null) {
					String week[] = rateDetails.getSpecialDays().split(",");
						for(String day:week) {
							StringBuilder sb = new StringBuilder(day);
							if(day.contains("[")) {
								sb.deleteCharAt(sb.indexOf("["));
							}
							if(day.contains("]")) {
								sb.deleteCharAt(sb.indexOf("]"));
							}
							if(day.contains(" ")) {
								sb.deleteCharAt(sb.indexOf(" "));
							}
							specialRateVM.weekDays.add(sb.toString());
							System.out.println(sb.toString());
							if(sb.toString().equals("Sun")) {
								specialRateVM.rateDay0 = true;
							}
							if(sb.toString().equals("Mon")) {
								specialRateVM.rateDay1 = true;
							}
							if(sb.toString().equals("Tue")) {
								specialRateVM.rateDay2 = true;
							}
							if(sb.toString().equals("Wed")) {
								specialRateVM.rateDay3 = true;
							}
							if(sb.toString().equals("Thu")) {
								specialRateVM.rateDay4 = true;
							}
							if(sb.toString().equals("Fri")) {
								specialRateVM.rateDay5 = true;
							}
							if(sb.toString().equals("Sat")) {
								specialRateVM.rateDay6 = true;
							}
						}
				}
				
					for(CancellationPolicy cancel:cancellation) {
						if(cancel.isNormal() == true){
							CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
							rateVM.cancellation.add(vm);
						}
						if(cancel.isNormal() == false) {
							CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
							specialRateVM.cancellation.add(vm);
						}
					}
					
				rateVM.setNormalRate(normalRateVM);
				rateVM.setSpecial(specialRateVM);
			
				list.add(rateVM);
		}
		
		return ok(Json.toJson(list));
		
	}
	
	
}
