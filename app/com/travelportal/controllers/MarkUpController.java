package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.SpecialsMarket;
import com.travelportal.vm.AgentRegistrationVM;
import com.travelportal.vm.BatchMarkupVM;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.ChildpoliciVM;
import com.travelportal.vm.HotelRegistrationVM;
import com.travelportal.vm.NormalRateVM;
import com.travelportal.vm.RateDetailsVM;
import com.travelportal.vm.RateVM;
import com.travelportal.vm.SpecialRateVM;
import com.travelportal.vm.SpecialsMarketVM;
import com.travelportal.vm.SpecificMarkupVM;

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

	public static class agentDataVM {
		public long countryCode;
		public String countryName;
		public List<AgentRegistrationVM> agentDatavm;

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
			rateVM.setFromDate(format.format(rate.getFromDate()));
			rateVM.setToDate(format.format(rate.getToDate()));
			rateVM.setRoomId(rate.getRoomType().getRoomId());
			rateVM.setRoomName(rate.getRoomType().getRoomType());
			rateVM.setIsSpecialRate(rateDetails.isSpecialRate());
			rateVM.setRateName(rate.getRateName());
			rateVM.setId(rate.getId());
			rateVM.applyToMarket = rateDetails.isApplyToMarket();

			NormalRateVM normalRateVM = new NormalRateVM();
			SpecialRateVM specialRateVM = new SpecialRateVM();

			for (PersonRate person : personRate) {

				if (person.isNormal() == true) {
					RateDetailsVM vm = new RateDetailsVM(person);
					normalRateVM.rateDetails.add(vm);
				}

				if (person.isNormal() == false) {
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
				if (cancel.isNormal() == true) {
					CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
					rateVM.cancellation.add(vm);
				}
				if (cancel.isNormal() == false) {
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
	public static Result savespecificMarkup() {
		
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, SpecificMarkupVM.class);
		SpecificMarkupVM specificMarkupVM = Json.fromJson(json, SpecificMarkupVM.class);

		System.out.println("&^&&^&&^&^&^&^&&&^");
		System.out.println(specificMarkupVM.getSpecificPercent());
		System.out.println("&^&&^&&^&^&^&^&&&^");
		
for(long agentvm : specificMarkupVM.getAgentSpecific()){
			
			for(long vm : specificMarkupVM.getRateSelected()){
				System.out.println(vm);
				
				SpecificMarkup specificMarkup = SpecificMarkup.findRateSupplier(AgentRegistration.getallAgentCode(agentvm),vm,specificMarkupVM.getCode());

				if(specificMarkup == null)
				{
	 specificMarkup = new SpecificMarkup();
	specificMarkup.setSpecificFlat(specificMarkupVM.getSpecificFlat());
	specificMarkup.setSpecificPercent(specificMarkupVM.getSpecificPercent());
	specificMarkup.setSpecificSelected(specificMarkupVM.getSpecificSelected());
	specificMarkup.setSupplierCode(specificMarkupVM.getCode());
	specificMarkup.setAgentSpecific(AgentRegistration.getallAgentCode(agentvm));
	specificMarkup.setRateSelected(RateMeta.getallRateCode(vm));
		
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
				BatchMarkup batchmarkup = BatchMarkup.findAgentSupplier(AgentRegistration.getallAgentCode(agentvm), vm);

				if(batchmarkup == null)
				{
				 batchmarkup = new BatchMarkup();
		batchmarkup.setFlat(batMarkupVM.getFlat());
		batchmarkup.setPercent(batMarkupVM.getPercent());
		batchmarkup.setSelected(batMarkupVM.getSelected());
		//batchmarkup.setSupplier(HotelRegistration.getallSupplierCode(vm));
		batchmarkup.setSupplier(vm);
		batchmarkup.setAgent(AgentRegistration.getallAgentCode(agentvm));
		
		batchmarkup.save();
				}
		
			}
	
	}
		return ok();
	}


}
