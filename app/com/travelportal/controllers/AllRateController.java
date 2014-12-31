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

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.HotelDescription;
import com.travelportal.vm.HotelGeneralInfoVM;
import com.travelportal.vm.HotelProfileVM;
import com.travelportal.vm.NormalRateVM;
import com.travelportal.vm.RateDetailsVM;
import com.travelportal.vm.RateVM;
import com.travelportal.vm.SpecialRateVM;

public class AllRateController extends Controller {

	
	
	@Transactional(readOnly = true)
	public static Result getDatewiseHotel() {
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		List<HotelProfileVM> hotellist = new ArrayList<>();
		Map<String, String[]> map1 = request().queryString();
		Map<Long, Long> map = new HashMap<Long, Long>();
		String[] roomId = map1.get("roomId");
		String[] fromDate = map1.get("fromDate");
		String[] toDate = map1.get("toDate");
		String[] sId = map1.get("sId");
		String[] cityId = map1.get("cityId");
		String[] cId = map1.get("countryId");

		
		Date fmDate = null;
		Date tDate = null;
		/*try {
			fmDate =format.parse(fromDate[0]);
			tDate =format.parse(toDate[0]);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		List<RateMeta> rateMeta = RateMeta.getdatecheck(
				Long.parseLong(roomId[0]),Integer.parseInt(cityId[0]),
				Integer.parseInt(sId[0]),Integer.parseInt(cId[0]));		
			 
				for (RateMeta rate1 : rateMeta) {

					  Date formDate = null; Date toDates = null; try { formDate =
							  format.parse(fromDate[0]); toDates = format.parse(toDate[0]); } catch
							 (ParseException e) { // TODO Auto-generated catch block
							 e.printStackTrace(); }
							  
							  Calendar c = Calendar.getInstance(); c.setTime(formDate);
							  c.set(Calendar.MILLISECOND, 0);
							  
							  Calendar c1 = Calendar.getInstance(); c1.setTime(toDates);
							  c1.set(Calendar.MILLISECOND, 0);
							  
							  
							  Calendar fromdata = Calendar.getInstance();
							  fromdata.setTime(rate1.getFromDate()); fromdata.set(Calendar.MILLISECOND,0);
							  
							  Calendar todata = Calendar.getInstance();
							  todata.setTime(rate1.getToDate()); todata.set(Calendar.MILLISECOND, 0);
							  System.out.println("--------from---------------------");
							  System.out.println(fromdata.getTimeInMillis());
							  System.out.println(c.getTimeInMillis());
							  System.out.println("------------to-----------------");
							  System.out.println(todata.getTimeInMillis());
							  System.out.println(c1.getTimeInMillis());
							  
							  
							  
							 if((fromdata.getTimeInMillis() <= c.getTimeInMillis() &&
							  c.getTimeInMillis() <= todata.getTimeInMillis()) ||
							  (fromdata.getTimeInMillis() <= c1.getTimeInMillis() &&
							  c1.getTimeInMillis() <= todata.getTimeInMillis())){
					
					
					List<RateVM> list = new ArrayList<>();
						HotelProfileVM hProfileVM = new HotelProfileVM();
						Long object = (Long) map.get(rate1.getSupplierCode());
						HotelProfile hAmenities = HotelProfile.findAllData(rate1
								.getSupplierCode());
						if (object == null) {
							
							
							
										hProfileVM.setSupplierCode(hAmenities
												.getSupplier_code());
										hProfileVM
												.setHotelNm(hAmenities.getHotelName());
										hProfileVM.setSupplierNm(hAmenities
												.getSupplierName());
										if (hAmenities.getCountry() != null) {
											hProfileVM.setCountryCode(hAmenities
													.getCountry().getCountryCode());
										}
										if (hAmenities.getCurrency() != null) {
											hProfileVM.setCurrencyCode(hAmenities
													.getCurrency().getCurrencyCode());
										}
										if (hAmenities.getStartRatings() != null) {
											hProfileVM.setStartRating(hAmenities
													.getStartRatings().getId());
										}
									
										List<RateMeta> rateMeta1 = RateMeta.getdatecheck(
												Long.parseLong(roomId[0]),Integer.parseInt(cityId[0]),
												Integer.parseInt(sId[0]),Integer.parseInt(cId[0]));
										
										for (RateMeta rate : rateMeta1) {
											
											RateDetails rateDetails = RateDetails.findByRateMetaId(rate
													.getId());

											List<PersonRate> personRate = PersonRate.findByRateMetaId(rate
													.getId());
											List<CancellationPolicy> cancellation = CancellationPolicy
													.findByRateMetaId(rate.getId());

											AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate
													.getId());

											RateVM rateVM = new RateVM();
											rateVM.setCurrency(rate.getCurrency());
											rateVM.setFromDate(format.format(rate.getFromDate()));
											rateVM.setToDate(format.format(rate.getToDate()));
											rateVM.setRoomId(rate.getRoomType().getRoomId());
											rateVM.setRoomName(rate.getRoomType().getRoomType());
											rateVM.setIsSpecialRate(rateDetails.isSpecialRate());
											rateVM.setRateName(rate.getRateName());
											rateVM.setSupplierCode(rate.getSupplierCode());
											rateVM.setId(rate.getId());
											rateVM.applyToMarket = rateDetails.isApplyToMarket();

											NormalRateVM normalRateVM = new NormalRateVM();
											SpecialRateVM specialRateVM = new SpecialRateVM();
											AllotmentMarketVM Allvm = new AllotmentMarketVM();

											if (alloMarket != null) {
												System.out
														.println("---------------------------------------");
												System.out.println(alloMarket.getAllotmentMarketId());
												Allvm.setAllotmentMarketId(alloMarket
														.getAllotmentMarketId());
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
													CancellationPolicyVM vm = new CancellationPolicyVM(
															cancel);
													rateVM.cancellation.add(vm);
												}
												if (cancel.isNormal() == false) {
													CancellationPolicyVM vm = new CancellationPolicyVM(
															cancel);
													specialRateVM.cancellation.add(vm);
												}
											}

											rateVM.setNormalRate(normalRateVM);
											rateVM.setSpecial(specialRateVM);
											rateVM.setAllotmentmarket(Allvm);
											
											
											
											System.out.println(rate.getSupplierCode());
										
											System.out.println(hAmenities.getSupplier_code());
											
												list.add(rateVM);
												hProfileVM.setRate(list);
											
										}
													
										
							map.put(rate1.getSupplierCode(), Long.parseLong("1"));
						}
						System.out.println("---------");
						System.out.println(list);
						if(!list.isEmpty()){
						hotellist.add(hProfileVM);
						}
				}
		 }
				return ok(Json.toJson(hotellist));		
	}
	
	@Transactional(readOnly = true)
	public static Result getSearchHotel() {
													
													
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	
		List<HotelProfileVM> hotellist = new ArrayList<>();
		Map<String, String[]> map1 = request().queryString();
		Map<Long, Long> map = new HashMap<Long, Long>();
		String[] roomId = map1.get("roomId");
		String[] fromDate = map1.get("fromDate");
		String[] toDate = map1.get("toDate");
		String[] sId = map1.get("sId");
		String[] cityId = map1.get("cityId");
		String[] cId = map1.get("countryId");

		
		Date fmDate = null;
		Date tDate = null;
		try {
			fmDate =format.parse(fromDate[0]);
			tDate =format.parse(toDate[0]);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<RateMeta> rateMeta = RateMeta.getHotels(
				Long.parseLong(roomId[0]),Integer.parseInt(cityId[0]),
				Integer.parseInt(sId[0]),fmDate,tDate,Integer.parseInt(cId[0]));
		
		for (RateMeta rate1 : rateMeta) {

			List<RateVM> list = new ArrayList<>();
				HotelProfileVM hProfileVM = new HotelProfileVM();
				Long object = (Long) map.get(rate1.getSupplierCode());
				HotelProfile hAmenities = HotelProfile.findAllData(rate1
						.getSupplierCode());
				if (object == null) {
					
					
					
								hProfileVM.setSupplierCode(hAmenities
										.getSupplier_code());
								hProfileVM
										.setHotelNm(hAmenities.getHotelName());
								hProfileVM.setSupplierNm(hAmenities
										.getSupplierName());
								if (hAmenities.getCountry() != null) {
									hProfileVM.setCountryCode(hAmenities
											.getCountry().getCountryCode());
								}
								if (hAmenities.getCurrency() != null) {
									hProfileVM.setCurrencyCode(hAmenities
											.getCurrency().getCurrencyCode());
								}
								if (hAmenities.getStartRatings() != null) {
									hProfileVM.setStartRating(hAmenities
											.getStartRatings().getId());
								}
							
								List<RateMeta> rateMeta1 = RateMeta.getHotels1(
										Long.parseLong(roomId[0]),Integer.parseInt(cityId[0]),
										Integer.parseInt(sId[0]),fmDate,tDate,Integer.parseInt(cId[0]),rate1.getSupplierCode());
								
								for (RateMeta rate : rateMeta1) {
									
									RateDetails rateDetails = RateDetails.findByRateMetaId(rate
											.getId());

									List<PersonRate> personRate = PersonRate.findByRateMetaId(rate
											.getId());
									List<CancellationPolicy> cancellation = CancellationPolicy
											.findByRateMetaId(rate.getId());

									AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate
											.getId());

									RateVM rateVM = new RateVM();
									rateVM.setCurrency(rate.getCurrency());
									rateVM.setFromDate(format.format(rate.getFromDate()));
									rateVM.setToDate(format.format(rate.getToDate()));
									rateVM.setRoomId(rate.getRoomType().getRoomId());
									rateVM.setRoomName(rate.getRoomType().getRoomType());
									rateVM.setIsSpecialRate(rateDetails.isSpecialRate());
									rateVM.setRateName(rate.getRateName());
									rateVM.setSupplierCode(rate.getSupplierCode());
									rateVM.setId(rate.getId());
									rateVM.applyToMarket = rateDetails.isApplyToMarket();

									NormalRateVM normalRateVM = new NormalRateVM();
									SpecialRateVM specialRateVM = new SpecialRateVM();
									AllotmentMarketVM Allvm = new AllotmentMarketVM();

									if (alloMarket != null) {
										System.out
												.println("---------------------------------------");
										System.out.println(alloMarket.getAllotmentMarketId());
										Allvm.setAllotmentMarketId(alloMarket
												.getAllotmentMarketId());
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
											CancellationPolicyVM vm = new CancellationPolicyVM(
													cancel);
											rateVM.cancellation.add(vm);
										}
										if (cancel.isNormal() == false) {
											CancellationPolicyVM vm = new CancellationPolicyVM(
													cancel);
											specialRateVM.cancellation.add(vm);
										}
									}

									rateVM.setNormalRate(normalRateVM);
									rateVM.setSpecial(specialRateVM);
									rateVM.setAllotmentmarket(Allvm);
									
									
									
									System.out.println(rate.getSupplierCode());
								
									System.out.println(hAmenities.getSupplier_code());
									
										list.add(rateVM);
										hProfileVM.setRate(list);
									
								}
											
								
					map.put(rate1.getSupplierCode(), Long.parseLong("1"));
				}
				System.out.println("---------");
				System.out.println(list);
				if(!list.isEmpty()){
				hotellist.add(hProfileVM);
				}
		}
		
		return ok(Json.toJson(hotellist));

	}

	
	@Transactional(readOnly = true)
	public static Result getAmenitiesWiseHotel() {
													
													
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<RateVM> list = new ArrayList<>();
		List<HotelProfileVM> hotellist = new ArrayList<>();
		Map<String, String[]> map1 = request().queryString();
		Map<Long, Long> map = new HashMap<Long, Long>();
		String[] roomId = map1.get("roomId");
		String[] fromDate = map1.get("fromDate");
		String[] toDate = map1.get("toDate");
		String[] sId = map1.get("sId");
		String[] cityId = map1.get("cityId");
		String[] aId = map1.get("aId");

		String[] arr = aId[0].split(",");
		Date fmDate = null;
		Date tDate = null;
		try {
			fmDate =format.parse(fromDate[0]);
			tDate =format.parse(toDate[0]);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<RateMeta> rateMeta = RateMeta.getRateAndHotel(
				Long.parseLong(roomId[0]),Integer.parseInt(cityId[0]),
				Integer.parseInt(sId[0]),fmDate,tDate);
		
		for (RateMeta rate : rateMeta) {

				HotelProfileVM hProfileVM = new HotelProfileVM();
				Long object = (Long) map.get(rate.getSupplierCode());
				if (object == null) {
					int flag =0;
					HotelProfile hAmenities = HotelProfile.findAllData(rate
							.getSupplierCode());
					for (Integer integer : hAmenities.getIntListServices()) {
						HotelServices hServices = HotelServices
								.findById(integer);
						for (int i = 0; i < arr.length; i++) {
							System.out.println(i);
							if (hServices.getServiceId() == Integer.parseInt(arr[i])) {
								hProfileVM.setSupplierCode(hAmenities
										.getSupplier_code());
								hProfileVM
										.setHotelNm(hAmenities.getHotelName());
								hProfileVM.setSupplierNm(hAmenities
										.getSupplierName());
								if (hAmenities.getCountry() != null) {
									hProfileVM.setCountryCode(hAmenities
											.getCountry().getCountryCode());
								}
								if (hAmenities.getCurrency() != null) {
									hProfileVM.setCurrencyCode(hAmenities
											.getCurrency().getCurrencyCode());
								}
								if (hAmenities.getStartRatings() != null) {
									hProfileVM.setStartRating(hAmenities
											.getStartRatings().getId());
								}
								hotellist.add(hProfileVM);
								 flag=1;
							}
							if(flag==1){
								i = arr.length; 
							}
						}
					}
					map.put(rate.getSupplierCode(), Long.parseLong("1"));
				}

				RateDetails rateDetails = RateDetails.findByRateMetaId(rate
						.getId());

				List<PersonRate> personRate = PersonRate.findByRateMetaId(rate
						.getId());
				List<CancellationPolicy> cancellation = CancellationPolicy
						.findByRateMetaId(rate.getId());

				AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate
						.getId());

				RateVM rateVM = new RateVM();
				rateVM.setCurrency(rate.getCurrency());
				rateVM.setFromDate(format.format(rate.getFromDate()));
				rateVM.setToDate(format.format(rate.getToDate()));
				rateVM.setRoomId(rate.getRoomType().getRoomId());
				rateVM.setRoomName(rate.getRoomType().getRoomType());
				rateVM.setIsSpecialRate(rateDetails.isSpecialRate());
				rateVM.setRateName(rate.getRateName());
				rateVM.setSupplierCode(rate.getSupplierCode());
				rateVM.setId(rate.getId());
				rateVM.applyToMarket = rateDetails.isApplyToMarket();

				NormalRateVM normalRateVM = new NormalRateVM();
				SpecialRateVM specialRateVM = new SpecialRateVM();
				AllotmentMarketVM Allvm = new AllotmentMarketVM();

				if (alloMarket != null) {
					System.out
							.println("---------------------------------------");
					System.out.println(alloMarket.getAllotmentMarketId());
					Allvm.setAllotmentMarketId(alloMarket
							.getAllotmentMarketId());
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
						CancellationPolicyVM vm = new CancellationPolicyVM(
								cancel);
						rateVM.cancellation.add(vm);
					}
					if (cancel.isNormal() == false) {
						CancellationPolicyVM vm = new CancellationPolicyVM(
								cancel);
						specialRateVM.cancellation.add(vm);
					}
				}

				rateVM.setNormalRate(normalRateVM);
				rateVM.setSpecial(specialRateVM);
				rateVM.setAllotmentmarket(Allvm);

				list.add(rateVM);
		
		}
		
		return ok(Json.toJson(hotellist));

	}

	@Transactional(readOnly = true)
	public static Result getHotelAmenities() {
		List<HotelProfileVM> list = new ArrayList<>();
		List<HotelProfile> amenitiesList = HotelProfile.getHotel(2);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (HotelProfile hProfile : amenitiesList) {
			HotelProfileVM hProfileVM = new HotelProfileVM();
			hProfileVM.setLocation1(hProfile.getlocation1());
			hProfileVM.setLocation2(hProfile.getlocation2());
			hProfileVM.setSupplierCode(hProfile.getSupplier_code());
			hProfileVM.setServices(hProfile.getIntListServices());

			for (Integer integer : hProfile.getIntListServices()) {
				System.out.println(integer);
				HotelServices hServices = HotelServices.findById(integer);
				Integer object = map.get(hServices.getServiceId());
				if (object == null) {
					map.put(hServices.getServiceId(), 1);

				} else {
					map.put(hServices.getServiceId(), object + 1);
				}
			}

			list.add(hProfileVM);
		}
		return ok(Json.toJson(map));

	}

	@Transactional(readOnly = true)
	public static Result ByAllArteByCodition(Long supplierCode, Long roomId,
			String fromDate, String toDate, int sId, int cityId) {

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<RateVM> list = new ArrayList<>();

		List<RateMeta> rateMeta = RateMeta.getRateByCountry(supplierCode,
				roomId, cityId, sId);
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

			if ((fromdata.getTimeInMillis() <= c.getTimeInMillis() && c
					.getTimeInMillis() <= todata.getTimeInMillis())
					&& (fromdata.getTimeInMillis() <= c1.getTimeInMillis() && c1
							.getTimeInMillis() <= todata.getTimeInMillis())) {

				RateDetails rateDetails = RateDetails.findByRateMetaId(rate
						.getId());
				List<PersonRate> personRate = PersonRate.findByRateMetaId(rate
						.getId());
				List<CancellationPolicy> cancellation = CancellationPolicy
						.findByRateMetaId(rate.getId());

				AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate
						.getId());

				RateVM rateVM = new RateVM();
				rateVM.setCurrency(rate.getCurrency());
				rateVM.setFromDate(format.format(rate.getFromDate()));
				rateVM.setToDate(format.format(rate.getToDate()));
				rateVM.setRoomId(rate.getRoomType().getRoomId());
				rateVM.setRoomName(rate.getRoomType().getRoomType());
				rateVM.setIsSpecialRate(rateDetails.isSpecialRate());
				rateVM.setRateName(rate.getRateName());
				rateVM.setSupplierCode(rate.getSupplierCode());
				rateVM.setId(rate.getId());
				rateVM.applyToMarket = rateDetails.isApplyToMarket();

				NormalRateVM normalRateVM = new NormalRateVM();
				SpecialRateVM specialRateVM = new SpecialRateVM();
				AllotmentMarketVM Allvm = new AllotmentMarketVM();

				if (alloMarket != null) {
					System.out
							.println("---------------------------------------");
					System.out.println(alloMarket.getAllotmentMarketId());
					Allvm.setAllotmentMarketId(alloMarket
							.getAllotmentMarketId());
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
						CancellationPolicyVM vm = new CancellationPolicyVM(
								cancel);
						rateVM.cancellation.add(vm);
					}
					if (cancel.isNormal() == false) {
						CancellationPolicyVM vm = new CancellationPolicyVM(
								cancel);
						specialRateVM.cancellation.add(vm);
					}
				}

				rateVM.setNormalRate(normalRateVM);
				rateVM.setSpecial(specialRateVM);
				rateVM.setAllotmentmarket(Allvm);

				list.add(rateVM);
			}
		}
		// }
		return ok(Json.toJson(list));
	}

	@Transactional(readOnly = true)
	public static Result getCountryByMarket(int cityId) {
		List<AllotmentMarket> alloMarket = AllotmentMarket
				.getCityWiseMarket(cityId);
		return ok(Json.toJson(alloMarket));
	}

	/*
	 * @Transactional(readOnly = true) public static Result AllDate(String
	 * fromDate,String toDate) {
	 * 
	 * DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * 
	 * 
	 * List<RateVM> list = new ArrayList<>(); List<RateMeta> rateMeta =
	 * RateMeta.getAllDate(); for (RateMeta rate : rateMeta) {
	 * 
	 * Date formDate = null; Date toDates = null; try { formDate =
	 * format.parse(fromDate); toDates = format.parse(toDate); } catch
	 * (ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * Calendar c = Calendar.getInstance(); c.setTime(formDate);
	 * c.set(Calendar.MILLISECOND, 0);
	 * 
	 * Calendar c1 = Calendar.getInstance(); c1.setTime(toDates);
	 * c1.set(Calendar.MILLISECOND, 0);
	 * 
	 * 
	 * Calendar fromdata = Calendar.getInstance();
	 * fromdata.setTime(rate.getFromDate()); fromdata.set(Calendar.MILLISECOND,
	 * 0);
	 * 
	 * Calendar todata = Calendar.getInstance();
	 * todata.setTime(rate.getToDate()); todata.set(Calendar.MILLISECOND, 0);
	 * System.out.println("--------from---------------------");
	 * System.out.println(fromdata.getTimeInMillis());
	 * System.out.println(c.getTimeInMillis());
	 * System.out.println("------------to-----------------");
	 * System.out.println(todata.getTimeInMillis());
	 * System.out.println(c1.getTimeInMillis());
	 * 
	 * 
	 * 
	 * if((fromdata.getTimeInMillis() <= c.getTimeInMillis() &&
	 * c.getTimeInMillis() <= todata.getTimeInMillis()) &&
	 * (fromdata.getTimeInMillis() <= c1.getTimeInMillis() &&
	 * c1.getTimeInMillis() <= todata.getTimeInMillis())){
	 * 
	 * System.out.println("-----------from-to-----------------"); RateDetails
	 * rateDetails = RateDetails .findByRateMetaId(rate.getId());
	 * List<PersonRate> personRate = PersonRate.findByRateMetaId(rate .getId());
	 * List<CancellationPolicy> cancellation = CancellationPolicy
	 * .findByRateMetaId(rate.getId());
	 * 
	 * AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate.getId());
	 * 
	 * 
	 * RateVM rateVM = new RateVM(); rateVM.setCurrency(rate.getCurrency());
	 * rateVM.setFromDate(format.format(rate.getFromDate()));
	 * rateVM.setToDate(format.format(rate.getToDate()));
	 * rateVM.setRoomId(rate.getRoomType().getRoomId());
	 * rateVM.setRoomName(rate.getRoomType().getRoomType());
	 * rateVM.setIsSpecialRate(rateDetails.isSpecialRate());
	 * rateVM.setRateName(rate.getRateName()); rateVM.setId(rate.getId());
	 * rateVM.applyToMarket = rateDetails.isApplyToMarket();
	 * 
	 * NormalRateVM normalRateVM = new NormalRateVM(); SpecialRateVM
	 * specialRateVM = new SpecialRateVM(); AllotmentMarketVM Allvm = new
	 * AllotmentMarketVM();
	 * 
	 * 
	 * if(alloMarket != null){
	 * System.out.println("---------------------------------------");
	 * System.out.println(alloMarket.getAllotmentMarketId());
	 * Allvm.setAllotmentMarketId(alloMarket.getAllotmentMarketId());
	 * Allvm.setAllocation(alloMarket.getAllocation());
	 * Allvm.setChoose(alloMarket.getChoose());
	 * Allvm.setPeriod(alloMarket.getPeriod());
	 * Allvm.setSpecifyAllot(alloMarket.getSpecifyAllot());
	 * Allvm.setApplyMarket(alloMarket.getApplyMarket()); }
	 * 
	 * for (PersonRate person : personRate) {
	 * 
	 * if (person.isNormal() == true) { RateDetailsVM vm = new
	 * RateDetailsVM(person); normalRateVM.rateDetails.add(vm); }
	 * 
	 * if (person.isNormal() == false) { RateDetailsVM vm = new
	 * RateDetailsVM(person); specialRateVM.rateDetails.add(vm); }
	 * 
	 * }
	 * 
	 * 
	 * if (rateDetails.getSpecialDays() != null) { String week[] =
	 * rateDetails.getSpecialDays().split(","); for (String day : week) {
	 * StringBuilder sb = new StringBuilder(day); if (day.contains("[")) {
	 * sb.deleteCharAt(sb.indexOf("[")); } if (day.contains("]")) {
	 * sb.deleteCharAt(sb.indexOf("]")); } if (day.contains(" ")) {
	 * sb.deleteCharAt(sb.indexOf(" ")); }
	 * specialRateVM.weekDays.add(sb.toString());
	 * System.out.println(sb.toString()); if (sb.toString().equals("Sun")) {
	 * specialRateVM.rateDay0 = true; } if (sb.toString().equals("Mon")) {
	 * specialRateVM.rateDay1 = true; } if (sb.toString().equals("Tue")) {
	 * specialRateVM.rateDay2 = true; } if (sb.toString().equals("Wed")) {
	 * specialRateVM.rateDay3 = true; } if (sb.toString().equals("Thu")) {
	 * specialRateVM.rateDay4 = true; } if (sb.toString().equals("Fri")) {
	 * specialRateVM.rateDay5 = true; } if (sb.toString().equals("Sat")) {
	 * specialRateVM.rateDay6 = true; } } }
	 * 
	 * for (CancellationPolicy cancel : cancellation) { if (cancel.isNormal() ==
	 * true) { CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
	 * rateVM.cancellation.add(vm); } if (cancel.isNormal() == false) {
	 * CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
	 * specialRateVM.cancellation.add(vm); } }
	 * 
	 * rateVM.setNormalRate(normalRateVM); rateVM.setSpecial(specialRateVM);
	 * rateVM.setAllotmentmarket(Allvm);
	 * 
	 * list.add(rateVM); }
	 * 
	 * 
	 * } return ok(Json.toJson(list)); }
	 * 
	 * 
	 * @Transactional(readOnly = true) public static Result ByHotelType(Long
	 * supplierCode) {
	 * 
	 * DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * List<RateVM> list = new ArrayList<>(); List<RateMeta> rateMeta =
	 * RateMeta.getRateSupplier(supplierCode); for (RateMeta rate : rateMeta) {
	 * 
	 * RateDetails rateDetails = RateDetails .findByRateMetaId(rate.getId());
	 * List<PersonRate> personRate = PersonRate.findByRateMetaId(rate .getId());
	 * List<CancellationPolicy> cancellation = CancellationPolicy
	 * .findByRateMetaId(rate.getId());
	 * 
	 * AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate.getId());
	 * 
	 * 
	 * RateVM rateVM = new RateVM(); rateVM.setCurrency(rate.getCurrency());
	 * rateVM.setFromDate(format.format(rate.getFromDate()));
	 * rateVM.setToDate(format.format(rate.getToDate()));
	 * rateVM.setRoomId(rate.getRoomType().getRoomId());
	 * rateVM.setRoomName(rate.getRoomType().getRoomType());
	 * rateVM.setIsSpecialRate(rateDetails.isSpecialRate());
	 * rateVM.setRateName(rate.getRateName()); rateVM.setId(rate.getId());
	 * rateVM.applyToMarket = rateDetails.isApplyToMarket();
	 * 
	 * NormalRateVM normalRateVM = new NormalRateVM(); SpecialRateVM
	 * specialRateVM = new SpecialRateVM(); AllotmentMarketVM Allvm = new
	 * AllotmentMarketVM();
	 * 
	 * 
	 * if(alloMarket != null){
	 * System.out.println("---------------------------------------");
	 * System.out.println(alloMarket.getAllotmentMarketId());
	 * Allvm.setAllotmentMarketId(alloMarket.getAllotmentMarketId());
	 * Allvm.setAllocation(alloMarket.getAllocation());
	 * Allvm.setChoose(alloMarket.getChoose());
	 * Allvm.setPeriod(alloMarket.getPeriod());
	 * Allvm.setSpecifyAllot(alloMarket.getSpecifyAllot());
	 * Allvm.setApplyMarket(alloMarket.getApplyMarket()); }
	 * 
	 * for (PersonRate person : personRate) {
	 * 
	 * if (person.isNormal() == true) { RateDetailsVM vm = new
	 * RateDetailsVM(person); normalRateVM.rateDetails.add(vm); }
	 * 
	 * if (person.isNormal() == false) { RateDetailsVM vm = new
	 * RateDetailsVM(person); specialRateVM.rateDetails.add(vm); }
	 * 
	 * }
	 * 
	 * 
	 * if (rateDetails.getSpecialDays() != null) { String week[] =
	 * rateDetails.getSpecialDays().split(","); for (String day : week) {
	 * StringBuilder sb = new StringBuilder(day); if (day.contains("[")) {
	 * sb.deleteCharAt(sb.indexOf("[")); } if (day.contains("]")) {
	 * sb.deleteCharAt(sb.indexOf("]")); } if (day.contains(" ")) {
	 * sb.deleteCharAt(sb.indexOf(" ")); }
	 * specialRateVM.weekDays.add(sb.toString());
	 * System.out.println(sb.toString()); if (sb.toString().equals("Sun")) {
	 * specialRateVM.rateDay0 = true; } if (sb.toString().equals("Mon")) {
	 * specialRateVM.rateDay1 = true; } if (sb.toString().equals("Tue")) {
	 * specialRateVM.rateDay2 = true; } if (sb.toString().equals("Wed")) {
	 * specialRateVM.rateDay3 = true; } if (sb.toString().equals("Thu")) {
	 * specialRateVM.rateDay4 = true; } if (sb.toString().equals("Fri")) {
	 * specialRateVM.rateDay5 = true; } if (sb.toString().equals("Sat")) {
	 * specialRateVM.rateDay6 = true; } } }
	 * 
	 * for (CancellationPolicy cancel : cancellation) { if (cancel.isNormal() ==
	 * true) { CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
	 * rateVM.cancellation.add(vm); } if (cancel.isNormal() == false) {
	 * CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
	 * specialRateVM.cancellation.add(vm); } }
	 * 
	 * rateVM.setNormalRate(normalRateVM); rateVM.setSpecial(specialRateVM);
	 * rateVM.setAllotmentmarket(Allvm);
	 * 
	 * list.add(rateVM);
	 * 
	 * } return ok(Json.toJson(list)); }
	 * 
	 * 
	 * @Transactional(readOnly = true) public static Result getByRoomType(Long
	 * roomId) {
	 * 
	 * DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * 
	 * 
	 * List<RateVM> list = new ArrayList<>(); List<RateMeta> rateMeta =
	 * RateMeta.getRateByRoom(roomId); for (RateMeta rate : rateMeta) {
	 * 
	 * RateDetails rateDetails = RateDetails .findByRateMetaId(rate.getId());
	 * List<PersonRate> personRate = PersonRate.findByRateMetaId(rate .getId());
	 * List<CancellationPolicy> cancellation = CancellationPolicy
	 * .findByRateMetaId(rate.getId());
	 * 
	 * AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate.getId());
	 * 
	 * 
	 * RateVM rateVM = new RateVM(); rateVM.setCurrency(rate.getCurrency());
	 * rateVM.setFromDate(format.format(rate.getFromDate()));
	 * rateVM.setToDate(format.format(rate.getToDate()));
	 * rateVM.setRoomId(rate.getRoomType().getRoomId());
	 * rateVM.setRoomName(rate.getRoomType().getRoomType());
	 * rateVM.setIsSpecialRate(rateDetails.isSpecialRate());
	 * rateVM.setRateName(rate.getRateName()); rateVM.setId(rate.getId());
	 * rateVM.applyToMarket = rateDetails.isApplyToMarket();
	 * 
	 * NormalRateVM normalRateVM = new NormalRateVM(); SpecialRateVM
	 * specialRateVM = new SpecialRateVM(); AllotmentMarketVM Allvm = new
	 * AllotmentMarketVM();
	 * 
	 * 
	 * if(alloMarket != null){
	 * System.out.println("---------------------------------------");
	 * System.out.println(alloMarket.getAllotmentMarketId());
	 * Allvm.setAllotmentMarketId(alloMarket.getAllotmentMarketId());
	 * Allvm.setAllocation(alloMarket.getAllocation());
	 * Allvm.setChoose(alloMarket.getChoose());
	 * Allvm.setPeriod(alloMarket.getPeriod());
	 * Allvm.setSpecifyAllot(alloMarket.getSpecifyAllot());
	 * Allvm.setApplyMarket(alloMarket.getApplyMarket()); }
	 * 
	 * for (PersonRate person : personRate) {
	 * 
	 * if (person.isNormal() == true) { RateDetailsVM vm = new
	 * RateDetailsVM(person); normalRateVM.rateDetails.add(vm); }
	 * 
	 * if (person.isNormal() == false) { RateDetailsVM vm = new
	 * RateDetailsVM(person); specialRateVM.rateDetails.add(vm); }
	 * 
	 * }
	 * 
	 * 
	 * if (rateDetails.getSpecialDays() != null) { String week[] =
	 * rateDetails.getSpecialDays().split(","); for (String day : week) {
	 * StringBuilder sb = new StringBuilder(day); if (day.contains("[")) {
	 * sb.deleteCharAt(sb.indexOf("[")); } if (day.contains("]")) {
	 * sb.deleteCharAt(sb.indexOf("]")); } if (day.contains(" ")) {
	 * sb.deleteCharAt(sb.indexOf(" ")); }
	 * specialRateVM.weekDays.add(sb.toString());
	 * System.out.println(sb.toString()); if (sb.toString().equals("Sun")) {
	 * specialRateVM.rateDay0 = true; } if (sb.toString().equals("Mon")) {
	 * specialRateVM.rateDay1 = true; } if (sb.toString().equals("Tue")) {
	 * specialRateVM.rateDay2 = true; } if (sb.toString().equals("Wed")) {
	 * specialRateVM.rateDay3 = true; } if (sb.toString().equals("Thu")) {
	 * specialRateVM.rateDay4 = true; } if (sb.toString().equals("Fri")) {
	 * specialRateVM.rateDay5 = true; } if (sb.toString().equals("Sat")) {
	 * specialRateVM.rateDay6 = true; } } }
	 * 
	 * for (CancellationPolicy cancel : cancellation) { if (cancel.isNormal() ==
	 * true) { CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
	 * rateVM.cancellation.add(vm); } if (cancel.isNormal() == false) {
	 * CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
	 * specialRateVM.cancellation.add(vm); } }
	 * 
	 * rateVM.setNormalRate(normalRateVM); rateVM.setSpecial(specialRateVM);
	 * rateVM.setAllotmentmarket(Allvm);
	 * 
	 * list.add(rateVM);
	 * 
	 * } return ok(Json.toJson(list)); }
	 * 
	 * 
	 * @Transactional(readOnly = true) public static Result getCountryByRate(int
	 * cityId) {
	 * 
	 * DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * 
	 * 
	 * List<RateVM> list = new ArrayList<>(); List<RateMeta> rateMeta =
	 * RateMeta.getRateByCountry(cityId); for (RateMeta rate : rateMeta) {
	 * 
	 * RateDetails rateDetails = RateDetails .findByRateMetaId(rate.getId());
	 * List<PersonRate> personRate = PersonRate.findByRateMetaId(rate .getId());
	 * List<CancellationPolicy> cancellation = CancellationPolicy
	 * .findByRateMetaId(rate.getId());
	 * 
	 * AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate.getId());
	 * 
	 * 
	 * RateVM rateVM = new RateVM(); rateVM.setCurrency(rate.getCurrency());
	 * rateVM.setFromDate(format.format(rate.getFromDate()));
	 * rateVM.setToDate(format.format(rate.getToDate()));
	 * rateVM.setRoomId(rate.getRoomType().getRoomId());
	 * rateVM.setRoomName(rate.getRoomType().getRoomType());
	 * rateVM.setIsSpecialRate(rateDetails.isSpecialRate());
	 * rateVM.setRateName(rate.getRateName()); rateVM.setId(rate.getId());
	 * rateVM.applyToMarket = rateDetails.isApplyToMarket();
	 * 
	 * NormalRateVM normalRateVM = new NormalRateVM(); SpecialRateVM
	 * specialRateVM = new SpecialRateVM(); AllotmentMarketVM Allvm = new
	 * AllotmentMarketVM();
	 * 
	 * 
	 * if(alloMarket != null){
	 * System.out.println("---------------------------------------");
	 * System.out.println(alloMarket.getAllotmentMarketId());
	 * Allvm.setAllotmentMarketId(alloMarket.getAllotmentMarketId());
	 * Allvm.setAllocation(alloMarket.getAllocation());
	 * Allvm.setChoose(alloMarket.getChoose());
	 * Allvm.setPeriod(alloMarket.getPeriod());
	 * Allvm.setSpecifyAllot(alloMarket.getSpecifyAllot());
	 * Allvm.setApplyMarket(alloMarket.getApplyMarket()); }
	 * 
	 * for (PersonRate person : personRate) {
	 * 
	 * if (person.isNormal() == true) { RateDetailsVM vm = new
	 * RateDetailsVM(person); normalRateVM.rateDetails.add(vm); }
	 * 
	 * if (person.isNormal() == false) { RateDetailsVM vm = new
	 * RateDetailsVM(person); specialRateVM.rateDetails.add(vm); }
	 * 
	 * }
	 * 
	 * 
	 * if (rateDetails.getSpecialDays() != null) { String week[] =
	 * rateDetails.getSpecialDays().split(","); for (String day : week) {
	 * StringBuilder sb = new StringBuilder(day); if (day.contains("[")) {
	 * sb.deleteCharAt(sb.indexOf("[")); } if (day.contains("]")) {
	 * sb.deleteCharAt(sb.indexOf("]")); } if (day.contains(" ")) {
	 * sb.deleteCharAt(sb.indexOf(" ")); }
	 * specialRateVM.weekDays.add(sb.toString());
	 * System.out.println(sb.toString()); if (sb.toString().equals("Sun")) {
	 * specialRateVM.rateDay0 = true; } if (sb.toString().equals("Mon")) {
	 * specialRateVM.rateDay1 = true; } if (sb.toString().equals("Tue")) {
	 * specialRateVM.rateDay2 = true; } if (sb.toString().equals("Wed")) {
	 * specialRateVM.rateDay3 = true; } if (sb.toString().equals("Thu")) {
	 * specialRateVM.rateDay4 = true; } if (sb.toString().equals("Fri")) {
	 * specialRateVM.rateDay5 = true; } if (sb.toString().equals("Sat")) {
	 * specialRateVM.rateDay6 = true; } } }
	 * 
	 * for (CancellationPolicy cancel : cancellation) { if (cancel.isNormal() ==
	 * true) { CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
	 * rateVM.cancellation.add(vm); } if (cancel.isNormal() == false) {
	 * CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
	 * specialRateVM.cancellation.add(vm); } }
	 * 
	 * rateVM.setNormalRate(normalRateVM); rateVM.setSpecial(specialRateVM);
	 * rateVM.setAllotmentmarket(Allvm);
	 * 
	 * list.add(rateVM);
	 * 
	 * } return ok(Json.toJson(list)); }
	 * 
	 * @Transactional(readOnly = true) public static Result getStarWiseHotel(int
	 * sId){ List<HotelGeneralInfoVM> list = new ArrayList<>();
	 * List<HotelProfile> hoList = HotelProfile.getStarwiseHotel(sId); for
	 * (HotelProfile hoProfile : hoList) { HotelGeneralInfoVM hoInfoVM = new
	 * HotelGeneralInfoVM();
	 * hoInfoVM.setSupplierCode(hoProfile.getSupplier_code());
	 * hoInfoVM.setHotelNm(hoProfile.getHotelName());
	 * hoInfoVM.setSupplierNm(hoProfile.getSupplierName());
	 * hoInfoVM.setHotelAddr(hoProfile.getAddress());
	 * hoInfoVM.setEmail(hoProfile.getHotelEmailAddr());
	 * hoInfoVM.setCountryCode(hoProfile.getCountry().getCountryCode());
	 * hoInfoVM.setCityCode(hoProfile.getCity().getCityCode());
	 * hoInfoVM.setChainHotelCode
	 * (hoProfile.getChainHotel().getChainHotelCode());
	 * hoInfoVM.setHotelPartOfChain(hoProfile.getPartOfChain());
	 * hoInfoVM.setBrandHotelCode(hoProfile.getHoteBrands().getBrandsCode());
	 * hoInfoVM.setCurrencyCode(hoProfile.getCurrency().getCurrencyCode());
	 * hoInfoVM.setMarketSpecificPolicyCode(hoProfile.getMarketPolicyType().
	 * getMarketPolicyTypeId());
	 * hoInfoVM.setStartRating(hoProfile.getStartRatings().getId());
	 * list.add(hoInfoVM);
	 * 
	 * }
	 * 
	 * return ok(Json.toJson(list)); }
	 */

	/*
	 * List<HotelProfileVM> list = new ArrayList<>(); List<HotelProfile>
	 * amenitiesList = HotelProfile.getHotel(countryId);
	 * 
	 * for (HotelProfile hProfile : amenitiesList) { HotelProfileVM hProfileVM =
	 * new HotelProfileVM();
	 * 
	 * for(Integer integer : hProfile.getIntListServices()){
	 * System.out.println(integer); HotelServices hServices =
	 * HotelServices.findById(integer); if(hServices.getServiceId() == id){
	 * hProfileVM.setSupplierCode(hProfile.getSupplier_code());
	 * hProfileVM.setHotelNm(hProfile.getHotelName());
	 * hProfileVM.setSupplierNm(hProfile.getSupplierName());
	 * if(hProfile.getCountry() != null){
	 * hProfileVM.setCountryCode(hProfile.getCountry().getCountryCode()); }
	 * if(hProfile.getCurrency() != null){
	 * hProfileVM.setCurrencyCode(hProfile.getCurrency().getCurrencyCode()); }
	 * if(hProfile.getStartRatings() != null){
	 * hProfileVM.setStartRating(hProfile.getStartRatings().getId()); }
	 * list.add(hProfileVM); } }
	 * 
	 * } return ok(Json.toJson(list));
	 */

}
