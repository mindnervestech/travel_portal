package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.HotelGeneralInfoVM;
import com.travelportal.vm.NormalRateVM;
import com.travelportal.vm.RateDetailsVM;
import com.travelportal.vm.RateVM;
import com.travelportal.vm.SpecialRateVM;

public class AllRateController extends Controller {
	
	@Transactional(readOnly = true)
	public static Result ByAllArteByCodition(Long supplierCode,Long roomId,String fromDate,String toDate,int sId,int cityId) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<RateVM> list = new ArrayList<>();
		//List<HotelProfile> hoList = HotelProfile.getStarwiseHotel(sId);
		//for (HotelProfile hoProfile : hoList) {
		
		List<RateMeta> rateMeta = RateMeta.getRateByCountry(supplierCode,roomId,cityId,sId);
		for (RateMeta rate : rateMeta) {
			
			Date formDate = null;
			Date toDates = null;
			try {
				formDate = format.parse(fromDate);
				toDates = format.parse(toDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			Calendar c = Calendar.getInstance();
			c.setTime(formDate);
			c.set(Calendar.MILLISECOND, 0);
			
			Calendar c1 = Calendar.getInstance();
			c1.setTime(toDates);
			c1.set(Calendar.MILLISECOND, 0);
			
			
			Calendar fromdata = Calendar.getInstance();
			fromdata.setTime(rate.getFromDate());
			fromdata.set(Calendar.MILLISECOND, 0);
			
			Calendar todata = Calendar.getInstance();
			todata.setTime(rate.getToDate());
			todata.set(Calendar.MILLISECOND, 0);
			
			if((fromdata.getTimeInMillis() <= c.getTimeInMillis() && c.getTimeInMillis() <= todata.getTimeInMillis()) && (fromdata.getTimeInMillis() <= c1.getTimeInMillis() && c1.getTimeInMillis() <= todata.getTimeInMillis())){
			
			RateDetails rateDetails = RateDetails
					.findByRateMetaId(rate.getId());
			List<PersonRate> personRate = PersonRate.findByRateMetaId(rate
					.getId());
			List<CancellationPolicy> cancellation = CancellationPolicy
					.findByRateMetaId(rate.getId());
			
			AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate.getId());
			

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
			AllotmentMarketVM Allvm = new AllotmentMarketVM();
			
			
			if(alloMarket != null){
				System.out.println("---------------------------------------");
				System.out.println(alloMarket.getAllotmentMarketId());
				Allvm.setAllotmentMarketId(alloMarket.getAllotmentMarketId());
				Allvm.setAllocation(alloMarket.getAllocation());
				Allvm.setChoose(alloMarket.getChoose());
				Allvm.setPeriod(alloMarket.getPeriod());
				Allvm.setSpecifyAllot(alloMarket.getSpecifyAllot());
				Allvm.setApplyMarket(alloMarket.getApplyMarket());
			}
			
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
			rateVM.setAllotmentmarket(Allvm);

			list.add(rateVM);
		}
		}
		//}
		return ok(Json.toJson(list));
	}
	
	
	@Transactional(readOnly = true)
	public static Result getCountryByMarket(int cityId) {
		List<AllotmentMarket> alloMarket = AllotmentMarket.getCityWiseMarket(cityId);
		return ok(Json.toJson(alloMarket));
	}
	
	
	/*
	@Transactional(readOnly = true)
	public static Result AllDate(String fromDate,String toDate) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
			
		
		List<RateVM> list = new ArrayList<>();
		List<RateMeta> rateMeta = RateMeta.getAllDate();
		for (RateMeta rate : rateMeta) {
			
			Date formDate = null;
			Date toDates = null;
			try {
				formDate = format.parse(fromDate);
				toDates = format.parse(toDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			Calendar c = Calendar.getInstance();
			c.setTime(formDate);
			c.set(Calendar.MILLISECOND, 0);
			
			Calendar c1 = Calendar.getInstance();
			c1.setTime(toDates);
			c1.set(Calendar.MILLISECOND, 0);
			
			
			Calendar fromdata = Calendar.getInstance();
			fromdata.setTime(rate.getFromDate());
			fromdata.set(Calendar.MILLISECOND, 0);
			
			Calendar todata = Calendar.getInstance();
			todata.setTime(rate.getToDate());
			todata.set(Calendar.MILLISECOND, 0);
			System.out.println("--------from---------------------");
			System.out.println(fromdata.getTimeInMillis());
			System.out.println(c.getTimeInMillis());
			System.out.println("------------to-----------------");
			System.out.println(todata.getTimeInMillis());
			System.out.println(c1.getTimeInMillis());
			
			
			
			if((fromdata.getTimeInMillis() <= c.getTimeInMillis() && c.getTimeInMillis() <= todata.getTimeInMillis()) && (fromdata.getTimeInMillis() <= c1.getTimeInMillis() && c1.getTimeInMillis() <= todata.getTimeInMillis())){ 
			
				System.out.println("-----------from-to-----------------");
				RateDetails rateDetails = RateDetails
					.findByRateMetaId(rate.getId());
			List<PersonRate> personRate = PersonRate.findByRateMetaId(rate
					.getId());
			List<CancellationPolicy> cancellation = CancellationPolicy
					.findByRateMetaId(rate.getId());
			
			AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate.getId());
			

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
			AllotmentMarketVM Allvm = new AllotmentMarketVM();
			
			
			if(alloMarket != null){
				System.out.println("---------------------------------------");
				System.out.println(alloMarket.getAllotmentMarketId());
			Allvm.setAllotmentMarketId(alloMarket.getAllotmentMarketId());
			Allvm.setAllocation(alloMarket.getAllocation());
			Allvm.setChoose(alloMarket.getChoose());
			Allvm.setPeriod(alloMarket.getPeriod());
			Allvm.setSpecifyAllot(alloMarket.getSpecifyAllot());
			Allvm.setApplyMarket(alloMarket.getApplyMarket());
			}
			
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
			rateVM.setAllotmentmarket(Allvm);

			list.add(rateVM);
		}

	
		}
		return ok(Json.toJson(list));
	}
	
	
	@Transactional(readOnly = true)
	public static Result ByHotelType(Long supplierCode) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		List<RateVM> list = new ArrayList<>();
		List<RateMeta> rateMeta = RateMeta.getRateSupplier(supplierCode);
		for (RateMeta rate : rateMeta) {
			
			RateDetails rateDetails = RateDetails
					.findByRateMetaId(rate.getId());
			List<PersonRate> personRate = PersonRate.findByRateMetaId(rate
					.getId());
			List<CancellationPolicy> cancellation = CancellationPolicy
					.findByRateMetaId(rate.getId());
			
			AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate.getId());
			

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
			AllotmentMarketVM Allvm = new AllotmentMarketVM();
			
			
			if(alloMarket != null){
				System.out.println("---------------------------------------");
				System.out.println(alloMarket.getAllotmentMarketId());
				Allvm.setAllotmentMarketId(alloMarket.getAllotmentMarketId());
				Allvm.setAllocation(alloMarket.getAllocation());
				Allvm.setChoose(alloMarket.getChoose());
				Allvm.setPeriod(alloMarket.getPeriod());
				Allvm.setSpecifyAllot(alloMarket.getSpecifyAllot());
				Allvm.setApplyMarket(alloMarket.getApplyMarket());
			}
			
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
			rateVM.setAllotmentmarket(Allvm);

			list.add(rateVM);
			
		}
		return ok(Json.toJson(list));
	}
	
	
	@Transactional(readOnly = true)
	public static Result getByRoomType(Long roomId) {
		
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
			
		
		List<RateVM> list = new ArrayList<>();
		List<RateMeta> rateMeta = RateMeta.getRateByRoom(roomId);
		for (RateMeta rate : rateMeta) {
			
			RateDetails rateDetails = RateDetails
					.findByRateMetaId(rate.getId());
			List<PersonRate> personRate = PersonRate.findByRateMetaId(rate
					.getId());
			List<CancellationPolicy> cancellation = CancellationPolicy
					.findByRateMetaId(rate.getId());
			
			AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate.getId());
			

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
			AllotmentMarketVM Allvm = new AllotmentMarketVM();
			
			
			if(alloMarket != null){
				System.out.println("---------------------------------------");
				System.out.println(alloMarket.getAllotmentMarketId());
				Allvm.setAllotmentMarketId(alloMarket.getAllotmentMarketId());
				Allvm.setAllocation(alloMarket.getAllocation());
				Allvm.setChoose(alloMarket.getChoose());
				Allvm.setPeriod(alloMarket.getPeriod());
				Allvm.setSpecifyAllot(alloMarket.getSpecifyAllot());
				Allvm.setApplyMarket(alloMarket.getApplyMarket());
			}
			
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
			rateVM.setAllotmentmarket(Allvm);

			list.add(rateVM);
			
		}
		return ok(Json.toJson(list));
	}
	
	
	@Transactional(readOnly = true)
	public static Result getCountryByRate(int cityId) {
		
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
			
		
		List<RateVM> list = new ArrayList<>();
		List<RateMeta> rateMeta = RateMeta.getRateByCountry(cityId);
		for (RateMeta rate : rateMeta) {
			
			RateDetails rateDetails = RateDetails
					.findByRateMetaId(rate.getId());
			List<PersonRate> personRate = PersonRate.findByRateMetaId(rate
					.getId());
			List<CancellationPolicy> cancellation = CancellationPolicy
					.findByRateMetaId(rate.getId());
			
			AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate.getId());
			

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
			AllotmentMarketVM Allvm = new AllotmentMarketVM();
			
			
			if(alloMarket != null){
				System.out.println("---------------------------------------");
				System.out.println(alloMarket.getAllotmentMarketId());
				Allvm.setAllotmentMarketId(alloMarket.getAllotmentMarketId());
				Allvm.setAllocation(alloMarket.getAllocation());
				Allvm.setChoose(alloMarket.getChoose());
				Allvm.setPeriod(alloMarket.getPeriod());
				Allvm.setSpecifyAllot(alloMarket.getSpecifyAllot());
				Allvm.setApplyMarket(alloMarket.getApplyMarket());
			}
			
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
			rateVM.setAllotmentmarket(Allvm);

			list.add(rateVM);
			
		}
		return ok(Json.toJson(list));
	}
	
	@Transactional(readOnly = true)
	public static Result getStarWiseHotel(int sId){
		List<HotelGeneralInfoVM> list = new ArrayList<>();
		List<HotelProfile> hoList = HotelProfile.getStarwiseHotel(sId);
		for (HotelProfile hoProfile : hoList) {
			HotelGeneralInfoVM hoInfoVM = new HotelGeneralInfoVM();
			hoInfoVM.setSupplierCode(hoProfile.getSupplier_code());
			hoInfoVM.setHotelNm(hoProfile.getHotelName());
			hoInfoVM.setSupplierNm(hoProfile.getSupplierName());
			hoInfoVM.setHotelAddr(hoProfile.getAddress());
			hoInfoVM.setEmail(hoProfile.getHotelEmailAddr());
			hoInfoVM.setCountryCode(hoProfile.getCountry().getCountryCode());
			hoInfoVM.setCityCode(hoProfile.getCity().getCityCode());
			hoInfoVM.setChainHotelCode(hoProfile.getChainHotel().getChainHotelCode());
			hoInfoVM.setHotelPartOfChain(hoProfile.getPartOfChain());
			hoInfoVM.setBrandHotelCode(hoProfile.getHoteBrands().getBrandsCode());
			hoInfoVM.setCurrencyCode(hoProfile.getCurrency().getCurrencyCode());
			hoInfoVM.setMarketSpecificPolicyCode(hoProfile.getMarketPolicyType().getMarketPolicyTypeId());
			hoInfoVM.setStartRating(hoProfile.getStartRatings().getId());
			list.add(hoInfoVM);		
			
		}
	
		return ok(Json.toJson(list));
	}
	*/
	
}
