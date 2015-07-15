package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.travelportal.domain.Country;
import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.ApplicableDateOnRate;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RateSpecialDays;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.HotelRegistrationVM;
import com.travelportal.vm.NormalRateVM;
import com.travelportal.vm.RateDetailsVM;
import com.travelportal.vm.RateVM;
import com.travelportal.vm.SpecialDaysRateVM;
import com.travelportal.vm.SpecialRateVM;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class ManageRateController extends Controller {

	@Transactional(readOnly=true)
	public static Result getpaymentWise(){
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<RateVM> list = new ArrayList<>();
		//Map<Long, Long> map = new HashMap<Long, Long>();
		String status = "pending";
		
		List<RateMeta> rateMeta = RateMeta.getpendingRate(status);
		
		fullRateList(rateMeta,list,format);
	/*	for(RateMeta rate:rateMeta) {
			Long object = map.get(rate.getId());
			if (object == null) {
				List<SpecialDaysRateVM>  spDaysRateVMs = new ArrayList<>();
				RateDetails rateDetails = RateDetails.findByRateMetaId(rate.getId());
				List<RateSpecialDays> rateSpecialDays = RateSpecialDays.findByRateMetaId(rate.getId());
				List<PersonRate> personRate = PersonRate.findByRateMetaId(rate.getId());
				List<CancellationPolicy> cancellation = CancellationPolicy.findByRateMetaId(rate.getId());
				AllotmentMarket aMarket = AllotmentMarket.findByRateId(rate.getId());
				
				RateVM rateVM = new RateVM();
				rateVM.setCurrency(rate.getCurrency());
				List<String> aMinDateOnRate = ApplicableDateOnRate.getMinDate(rate.getId());
				rateVM.setFromDate(format.format(aMinDateOnRate.get(0)));
				List<String> aMaxDateOnRate = ApplicableDateOnRate.getMaxDate(rate.getId());
				rateVM.setToDate(format.format(aMaxDateOnRate.get(0)));
				rateVM.setRoomName(rate.getRoomType().getRoomType());
				rateVM.setRoomId(rate.getRoomType().getRoomId());
				rateVM.setRateName(rate.getRateName());
				rateVM.setIsSpecialRate(rateDetails.getIsSpecialRate());
				
				rateVM.setId(rate.getId());
				rateVM.applyToMarket = rateDetails.isApplyToMarket();
				List<String> clist = new ArrayList<>();
				List<RateMeta> rList = RateMeta.getcountryByRate(rate.getId());
				for(RateMeta rMeta:rList){				
					
					for(Country country:rMeta.getCountry()){
					
						System.out.println(country.getCountryName());
						clist.add(country.getCountryName());
					}
					
				}
				
				rateVM.setAllocatedCountry(clist);
				
				
				NormalRateVM normalRateVM = new NormalRateVM();
				SpecialRateVM specialRateVM = new SpecialRateVM();
				AllotmentMarketVM aMarketVM = new AllotmentMarketVM();
				SpecialDaysRateVM sDaysRateVM = new SpecialDaysRateVM();
				List<SpecialDaysRateVM> spList = new ArrayList<>(); 
				
				if(aMarket != null){
				aMarketVM.setAllocation(aMarket.getAllocation());
				aMarketVM.setChoose(aMarket.getChoose());
				aMarketVM.setPeriod(aMarket.getPeriod());
				aMarketVM.setSpecifyAllot(aMarket.getSpecifyAllot());
				aMarketVM.setStopAllocation(aMarket.getStopAllocation());
				aMarketVM.setStopChoose(aMarket.getStopChoose());
				aMarketVM.setStopPeriod(aMarket.getStopPeriod());
				if(aMarket.getFromDate() != null){
				aMarketVM.setFromDate(format.format(aMarket.getFromDate()));
				}
				if(aMarket.getToDate() != null){
				aMarketVM.setToDate(format.format(aMarket.getToDate()));
				}
				
				rateVM.setAllotmentmarket(aMarketVM);
				}
		
				for(PersonRate person:personRate) {
					
					if(person.getIsNormal() == 0){
						RateDetailsVM vm = new RateDetailsVM(person);
						normalRateVM.rateDetails.add(vm);
					}else if(person.getIsNormal() == 1) {
						RateDetailsVM vm = new RateDetailsVM(person);
						specialRateVM.rateDetails.add(vm);
					}						
										
					if(person.getIsNormal() > 2) {
						rateVM.setIsSpecialDaysRate(2);
						for(RateSpecialDays rDays:rateSpecialDays){
							if(rDays.getIsSpecialdaysRate() ==  person.getIsNormal()){
							SpecialDaysRateVM sRateVM= new SpecialDaysRateVM();
							sRateVM.name = rDays.getSpecialdaysName();
							sRateVM.fromspecial = rDays.getFromspecialDate();
							sRateVM.tospecial = rDays.getTospecialDate();
							sRateVM.isspecialdaysrate = rDays.getIsSpecialdaysRate();
						    
							RateDetailsVM vm = new RateDetailsVM(person);
							sRateVM.rateDetails.add(vm);
							for(CancellationPolicy cancel:cancellation) {
								if(cancel.getIsNormal() > 2){
										if(sRateVM.isspecialdaysrate == cancel.getIsNormal()){
									CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
									sRateVM.cancellation.add(vm1);
									sRateVM.non_refund = cancel.isNon_refund();
									}
											
								}
							}	
							spDaysRateVMs.add(sRateVM);
							}
						}
						
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
							if(cancel.getIsNormal() == 0){
								CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
								rateVM.cancellation.add(vm);
								rateVM.non_refund = cancel.isNon_refund();
							}
							if(cancel.getIsNormal() == 1) {
								CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
								specialRateVM.cancellation.add(vm);
								specialRateVM.non_refund = cancel.isNon_refund();
							}
						}
						
					rateVM.setNormalRate(normalRateVM);
					rateVM.setSpecial(specialRateVM);
					rateVM.setSpecialDaysRate(spDaysRateVMs);
				
					list.add(rateVM);
				
			map.put(rate.getId(), Long.parseLong("1"));	
		}
			
		}*/
		
		
		return ok(Json.toJson(list));

		
	}
	
	@Transactional(readOnly=false)
	public static Result getAppPendingRate(long rateId){
		
		RateMeta rateMeta = RateMeta.findById(rateId);
		rateMeta.setStatus("approved");
		rateMeta.merge();
		return ok();
	}
	
	@Transactional(readOnly=true)
	public static Result getSupplierWisePendingRate(long supplierCode){
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<RateVM> list = new ArrayList<>();
		//Map<Long, Long> map = new HashMap<Long, Long>();
		String status = "pending";
		
		
		List<RateMeta> rateMeta = RateMeta.getsupplierWisependingRate(status, supplierCode);
		fullRateList(rateMeta,list,format);
		
		return ok(Json.toJson(list));
	}
	
	public static void fullRateList(List<RateMeta> rateMeta,List<RateVM> list,DateFormat format){
		for(RateMeta rate:rateMeta) {
			/*	Long object = map.get(rate.getId());
				if (object == null) {*/
					List<SpecialDaysRateVM>  spDaysRateVMs = new ArrayList<>();
					RateDetails rateDetails = RateDetails.findByRateMetaId(rate.getId());
					List<RateSpecialDays> rateSpecialDays = RateSpecialDays.findByRateMetaId(rate.getId());
					List<PersonRate> personRate = PersonRate.findByRateMetaId(rate.getId());
					List<CancellationPolicy> cancellation = CancellationPolicy.findByRateMetaId(rate.getId());
					AllotmentMarket aMarket = AllotmentMarket.findByRateId(rate.getId());
					
					RateVM rateVM = new RateVM();
					rateVM.setCurrency(rate.getCurrency());
					List<String> aMinDateOnRate = ApplicableDateOnRate.getMinDate(rate.getId());
					rateVM.setFromDate(format.format(aMinDateOnRate.get(0)));
					List<String> aMaxDateOnRate = ApplicableDateOnRate.getMaxDate(rate.getId());
					rateVM.setToDate(format.format(aMaxDateOnRate.get(0)));
					rateVM.setRoomName(rate.getRoomType().getRoomType());
					rateVM.setRoomId(rate.getRoomType().getRoomId());
					rateVM.setRateName(rate.getRateName());
					rateVM.setIsSpecialRate(rateDetails.getIsSpecialRate());
					
					rateVM.setId(rate.getId());
					rateVM.applyToMarket = rateDetails.isApplyToMarket();
					List<String> clist = new ArrayList<>();
					List<RateMeta> rList = RateMeta.getcountryByRate(rate.getId());
					for(RateMeta rMeta:rList){				
						
						for(Country country:rMeta.getCountry()){
						
							System.out.println(country.getCountryName());
							clist.add(country.getCountryName());
						}
						
					}
					
					rateVM.setAllocatedCountry(clist);
					
					
					NormalRateVM normalRateVM = new NormalRateVM();
					SpecialRateVM specialRateVM = new SpecialRateVM();
					AllotmentMarketVM aMarketVM = new AllotmentMarketVM();
					SpecialDaysRateVM sDaysRateVM = new SpecialDaysRateVM();
					List<SpecialDaysRateVM> spList = new ArrayList<>(); 
					
					if(aMarket != null){
					aMarketVM.setAllocation(aMarket.getAllocation());
					aMarketVM.setChoose(aMarket.getChoose());
					aMarketVM.setPeriod(aMarket.getPeriod());
					aMarketVM.setSpecifyAllot(aMarket.getSpecifyAllot());
					aMarketVM.setStopAllocation(aMarket.getStopAllocation());
					aMarketVM.setStopChoose(aMarket.getStopChoose());
					aMarketVM.setStopPeriod(aMarket.getStopPeriod());
					if(aMarket.getFromDate() != null){
					aMarketVM.setFromDate(format.format(aMarket.getFromDate()));
					}
					if(aMarket.getToDate() != null){
					aMarketVM.setToDate(format.format(aMarket.getToDate()));
					}
					
					rateVM.setAllotmentmarket(aMarketVM);
					}
			
					for(PersonRate person:personRate) {
						
						if(person.getIsNormal() == 0){
							RateDetailsVM vm = new RateDetailsVM(person);
							normalRateVM.rateDetails.add(vm);
						}else if(person.getIsNormal() == 1) {
							RateDetailsVM vm = new RateDetailsVM(person);
							specialRateVM.rateDetails.add(vm);
						}						
											
						if(person.getIsNormal() > 2) {
							rateVM.setIsSpecialDaysRate(2);
							for(RateSpecialDays rDays:rateSpecialDays){
								if(rDays.getIsSpecialdaysRate() ==  person.getIsNormal()){
								SpecialDaysRateVM sRateVM= new SpecialDaysRateVM();
								sRateVM.name = rDays.getSpecialdaysName();
								sRateVM.fromspecial = rDays.getFromspecialDate();
								sRateVM.tospecial = rDays.getTospecialDate();
								sRateVM.isspecialdaysrate = rDays.getIsSpecialdaysRate();
							    
								RateDetailsVM vm = new RateDetailsVM(person);
								sRateVM.rateDetails.add(vm);
								for(CancellationPolicy cancel:cancellation) {
									if(cancel.getIsNormal() > 2){
											if(sRateVM.isspecialdaysrate == cancel.getIsNormal()){
										CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
										sRateVM.cancellation.add(vm1);
										sRateVM.non_refund = cancel.isNon_refund();
										}
												
									}
								}	
								spDaysRateVMs.add(sRateVM);
								}
							}
							
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
								if(cancel.getIsNormal() == 0){
									CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
									rateVM.cancellation.add(vm);
									rateVM.non_refund = cancel.isNon_refund();
								}
								if(cancel.getIsNormal() == 1) {
									CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
									specialRateVM.cancellation.add(vm);
									specialRateVM.non_refund = cancel.isNon_refund();
								}
							}
							
						rateVM.setNormalRate(normalRateVM);
						rateVM.setSpecial(specialRateVM);
						rateVM.setSpecialDaysRate(spDaysRateVMs);
					
						list.add(rateVM);
					
			/*	map.put(rate.getId(), Long.parseLong("1"));	
			}*/
				
			}
	}
	
	@Transactional(readOnly=true)
	public static Result getSupplier(){
			List<HotelRegistration> list = HotelRegistration.getAllApprovedUsers();
			List<HotelRegistrationVM> vm = new ArrayList<>();
			for(HotelRegistration hotel : list) {
				HotelRegistrationVM hotelRegistrationVM = new HotelRegistrationVM(hotel);
				vm.add(hotelRegistrationVM);
			}
			
			return ok(Json.toJson(vm));
	}
	

}



