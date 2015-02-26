package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.data.DynamicForm;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Enumeration.Val;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.domain.Country;
import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.InternalContacts;
import com.travelportal.domain.admin.BatchMarkup;
import com.travelportal.domain.admin.SpecificMarkup;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.Specials;
import com.travelportal.domain.rooms.SpecialsMarket;
import com.travelportal.vm.AgentRegistrationVM;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.BatchMarkupVM;
import com.travelportal.vm.BatchViewSupportVM;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.ChildpoliciVM;
import com.travelportal.vm.HotelRegistrationVM;
import com.travelportal.vm.NormalRateVM;
import com.travelportal.vm.RateDetailsVM;
import com.travelportal.vm.RateVM;
import com.travelportal.vm.SpecialRateVM;
import com.travelportal.vm.SpecialsMarketVM;
import com.travelportal.vm.SpecialsVM;
import com.travelportal.vm.SpecificMarkupVM;
import com.travelportal.vm.SpecificViewSupportVM;

public class MarkUpController extends Controller {

	@Transactional(readOnly = true)
	public static Result getSupplier() {
		final List<HotelRegistration> list = HotelRegistration
				.getAllApprovedUsers();
		List<HotelRegistrationVM> vm = new ArrayList<>();
		for (HotelRegistration hotel : list) {
			HotelRegistrationVM hotelRegistrationVM = new HotelRegistrationVM(
					hotel);
			vm.add(hotelRegistrationVM);
		}

		return ok(Json.toJson(vm));

	}
	
	

	@Transactional(readOnly = true)
	public static Result agentCountries() {
		
		List<Country> aList = AgentRegistration.getAllApprovedAgent();
		//return ok(Json.toJson(aList));
		
		//final List<Country> countries = Country.getCountries(); 
		List<Map> country = new ArrayList<>();
 		for(Country c : aList){
 			Map m = new HashMap<>();
 			m.put("countryCode", c.getCountryCode());
 			m.put("countryName", c.getCountryName());
 			country.add(m);
		}
		return ok(Json.toJson(country));
	}
	
	@Transactional(readOnly = true)
	public static Result getAgent() {
		List<Country> aList = AgentRegistration.getAllApprovedAgent();
				
		List<agentDataVM> group = new ArrayList<agentDataVM>();

		for (Country aRegistration : aList) {

			agentDataVM countryvm = new agentDataVM();
			countryvm.countryCode = aRegistration.getCountryCode();
			countryvm.countryName = aRegistration.getCountryName();

			List<AgentRegistrationVM> agentRegistrationVMs = new ArrayList<AgentRegistrationVM>();
			List<AgentRegistration> agList = AgentRegistration
					.getAgentData(aRegistration.getCountryCode());
			for (AgentRegistration _agent : agList) {
				AgentRegistrationVM agRegist = new AgentRegistrationVM(_agent);
				agRegist.id = _agent.getId();
				agRegist.firstName = _agent.getFirstName();
				agRegist.lastName = _agent.getLastName();
				agentRegistrationVMs.add(agRegist);

			}
			countryvm.agentDatavm = agentRegistrationVMs;

			group.add(countryvm);
		}
		return ok(Json.toJson(group));

	}
	
	
	@Transactional(readOnly = true)
	public static Result getSupplerWiseRate(long supplierCode) {
		List<AgentRegistration> rList = BatchMarkup.getAllAgent(supplierCode);
		/*for(AgentRegistration rARegistration : rList){
			
		}*/
		return ok(Json.toJson(rList));
	}
	
	@Transactional(readOnly = true)
	public static Result getSupplerWiseSpecificRate(long supplierCode) {
		List<SpecificMarkup> sRList = SpecificMarkup.getAllSpecific(supplierCode);
		return ok(Json.toJson(sRList));
	}

	@Transactional(readOnly = true)
	public static Result getAgentCountry(long countryCode) {
	
		List<Country> aList = AgentRegistration.getAllApprovedAgentByCountry(countryCode);
		
		List<agentDataVM> group = new ArrayList<agentDataVM>();

		for (Country aRegistration : aList) {

			agentDataVM countryvm = new agentDataVM();
			
			countryvm.countryCode = aRegistration.getCountryCode();
			countryvm.countryName = aRegistration.getCountryName();

			List<AgentRegistrationVM> agentRegistrationVMs = new ArrayList<AgentRegistrationVM>();
			List<AgentRegistration> agList = AgentRegistration
					.getAgentData(aRegistration.getCountryCode());
			for (AgentRegistration _agent : agList) {
				AgentRegistrationVM agRegist = new AgentRegistrationVM(_agent);
				agRegist.id = _agent.getId();
				agRegist.firstName = _agent.getFirstName();
				agRegist.lastName = _agent.getLastName();
				agentRegistrationVMs.add(agRegist);

			}
			countryvm.agentDatavm = agentRegistrationVMs;

			group.add(countryvm);
		}
		return ok(Json.toJson(group));

	}

	public static class agentDataVM {
		public long countryCode;
		public String countryName;
		public List<AgentRegistrationVM> agentDatavm;

	}
	
	@Transactional(readOnly = true)
	public static Result getSpecialRate(Long code) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<SpecialsVM> list = new ArrayList<>();
		List<Specials> specialsList = Specials.findSpecialBySupplierCode(code);
		for(Specials special : specialsList) {
			SpecialsVM specialsVM = new SpecialsVM();
			specialsVM.id = special.getId();
			specialsVM.fromDate = format.format(special.getFromDate());
			specialsVM.toDate = format.format(special.getToDate());
			specialsVM.promotionName = special.getPromotionName();
			specialsVM.supplierCode = special.getSupplierCode();
			for(HotelRoomTypes room : special.getRoomTypes()) {
				specialsVM.roomTypes.add(Long.parseLong(room.getRoomType()));
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
	public static Result getSupplierRate(Long code) {
		System.out.println(code);
		// List<RateMeta> rateMetas = RateMeta.getRateSupplier(code);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<RateVM> list = new ArrayList<>();
		List<RateMeta> rateMeta = RateMeta.getRateSupplier(code);
		for (RateMeta rate : rateMeta) {
			RateDetails rateDetails = RateDetails
					.findByRateMetaId(rate.getId());
			List<PersonRate> personRate = PersonRate.findByRateMetaId(rate
					.getId());
			List<CancellationPolicy> cancellation = CancellationPolicy
					.findByRateMetaId(rate.getId());

			RateVM rateVM = new RateVM();
			rateVM.setCurrency(rate.getCurrency());
			/*rateVM.setFromDate(format.format(rate.getFromDate()));
			rateVM.setToDate(format.format(rate.getToDate()));*/
			rateVM.setRoomId(rate.getRoomType().getRoomId());
			rateVM.setRoomName(rate.getRoomType().getRoomType());
			rateVM.setIsSpecialRate(rateDetails.getIsSpecialRate());
			rateVM.setRateName(rate.getRateName());
			rateVM.setId(rate.getId());
			rateVM.applyToMarket = rateDetails.isApplyToMarket();

			NormalRateVM normalRateVM = new NormalRateVM();
			SpecialRateVM specialRateVM = new SpecialRateVM();

			for (PersonRate person : personRate) {

				if (person.getIsNormal() == 0) {
					RateDetailsVM vm = new RateDetailsVM(person);
					normalRateVM.rateDetails.add(vm);
				}

				if (person.getIsNormal() == 1) {
					RateDetailsVM vm = new RateDetailsVM(person);
					specialRateVM.rateDetails.add(vm);
				}

			}
			if (rateDetails.getSpecialDays() != null) {
				String week[] = rateDetails.getSpecialDays().split(",");
				for (String day : week) {
					StringBuilder sb = new StringBuilder(day);
					if (day.contains("[")) {
						sb.deleteCharAt(sb.indexOf("["));
					}
					if (day.contains("]")) {
						sb.deleteCharAt(sb.indexOf("]"));
					}
					if (day.contains(" ")) {
						sb.deleteCharAt(sb.indexOf(" "));
					}
					specialRateVM.weekDays.add(sb.toString());
					System.out.println(sb.toString());
					if (sb.toString().equals("Sun")) {
						specialRateVM.rateDay0 = true;
					}
					if (sb.toString().equals("Mon")) {
						specialRateVM.rateDay1 = true;
					}
					if (sb.toString().equals("Tue")) {
						specialRateVM.rateDay2 = true;
					}
					if (sb.toString().equals("Wed")) {
						specialRateVM.rateDay3 = true;
					}
					if (sb.toString().equals("Thu")) {
						specialRateVM.rateDay4 = true;
					}
					if (sb.toString().equals("Fri")) {
						specialRateVM.rateDay5 = true;
					}
					if (sb.toString().equals("Sat")) {
						specialRateVM.rateDay6 = true;
					}
				}
			}

			for (CancellationPolicy cancel : cancellation) {
				if (cancel.getIsNormal() == 0) {
					CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
					rateVM.cancellation.add(vm);
				}
				if (cancel.getIsNormal() == 1) {
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
	
	
	@Transactional(readOnly = false)
	public static Result UpdateSpecificMarkup() {
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json,SpecificViewSupportVM.class);
		SpecificViewSupportVM specSupportVM = Json.fromJson(json, SpecificViewSupportVM.class);
		SpecificMarkup speMarkup = SpecificMarkup.findBySpecificId(specSupportVM.getSpecificMarkupId());
		speMarkup.setSpecificFlat(specSupportVM.getSpecificFlat());
		speMarkup.setSpecificPercent(specSupportVM.getSpecificPercent());
		speMarkup.setSpecificSelected(specSupportVM.getSpecificSelected());
		speMarkup.merge();
		return ok();
	}
	
	@Transactional(readOnly = false)
	public static Result UpdateBatchMarkup() {
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, BatchViewSupportVM.class);
		BatchViewSupportVM batSupportVM = Json.fromJson(json, BatchViewSupportVM.class);
		
		BatchMarkup batchmarkup = BatchMarkup.findByBatchId(batSupportVM.getBatchMarkupId());
		batchmarkup.setFlat(batSupportVM.getFlat());
		batchmarkup.setPercent(batSupportVM.getPercent());
		batchmarkup.setSelected(batSupportVM.getSelected());
		batchmarkup.merge();
		return ok();
		
	}
	
	@Transactional(readOnly = false)
	public static Result savespecificMarkup() {
		
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, SpecificMarkupVM.class);
		SpecificMarkupVM specificMarkupVM = Json.fromJson(json, SpecificMarkupVM.class);

		System.out.println("&^&&^&&^&^&^&^&&&^");
		System.out.println(specificMarkupVM.getRateSelected());
		System.out.println("&^&&^&&^&^&^&^&&&^");
		
for(long agentvm : specificMarkupVM.getAgentSpecific()){
	
	SpecificMarkup specificMarkup = SpecificMarkup.findRateSupplier(AgentRegistration.getAgentCode(String.valueOf(agentvm)),specificMarkupVM.getCode());
	
		if(specificMarkupVM.getRateSelected().size() != 0){
			
				for(long vm : specificMarkupVM.getRateSelected()){
				
				System.out.println(vm);
		
				if(specificMarkup == null)
				{
	 specificMarkup = new SpecificMarkup();
	specificMarkup.setSpecificFlat(specificMarkupVM.getSpecificFlat());
	specificMarkup.setSpecificPercent(specificMarkupVM.getSpecificPercent());
	specificMarkup.setSpecificSelected(specificMarkupVM.getSpecificSelected());
	specificMarkup.setSupplierCode(specificMarkupVM.getCode());
	specificMarkup.setAgentSpecific(AgentRegistration.getAgentCode(String.valueOf(agentvm)));
	specificMarkup.setRateSelected(RateMeta.getallRateCode(vm));
		
	specificMarkup.save();
				}
		
			}
		}else{
			if(specificMarkup == null)
			{
 specificMarkup = new SpecificMarkup();
specificMarkup.setSpecificFlat(specificMarkupVM.getSpecificFlat());
specificMarkup.setSpecificPercent(specificMarkupVM.getSpecificPercent());
specificMarkup.setSpecificSelected(specificMarkupVM.getSpecificSelected());
specificMarkup.setSupplierCode(specificMarkupVM.getCode());
specificMarkup.setAgentSpecific(AgentRegistration.getAgentCode(String.valueOf(agentvm)));
//specificMarkup.setRateSelected(RateMeta.getallRateCode(null));
	
specificMarkup.save();
			}
		}
			
	
	}
		
		return ok();
		
		
	}

	@Transactional(readOnly = false)
	public static Result savebatchMarkup() {

		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, BatchMarkupVM.class);
		BatchMarkupVM batMarkupVM = Json.fromJson(json, BatchMarkupVM.class);

		for(long vm : batMarkupVM.getSupplier()){
			
			for(long agentvm : batMarkupVM.getAgent()){
				System.out.println(vm);
				BatchMarkup batchmarkup = BatchMarkup.findAgentSupplier(AgentRegistration.getAgentCode(String.valueOf(agentvm)), vm);

				if(batchmarkup == null)
				{
				 batchmarkup = new BatchMarkup();
		batchmarkup.setFlat(batMarkupVM.getFlat());
		batchmarkup.setPercent(batMarkupVM.getPercent());
		batchmarkup.setSelected(batMarkupVM.getSelected());
		//batchmarkup.setSupplier(HotelRegistration.getallSupplierCode(vm));
		batchmarkup.setSupplier(vm);
		batchmarkup.setAgent(AgentRegistration.getAgentCode(String.valueOf(agentvm)));
		
		batchmarkup.save();
				}
		
			}
	
	}
		return ok();
	}

	
}
