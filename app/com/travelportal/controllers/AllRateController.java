package com.travelportal.controllers;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RateSpecialDays;
import com.travelportal.domain.rooms.RoomAllotedRateWise;
import com.travelportal.domain.rooms.Specials;
import com.travelportal.domain.rooms.SpecialsMarket;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.HotelSearch;
import com.travelportal.vm.SearchAllotmentMarketVM;
import com.travelportal.vm.SearchRateDetailsVM;
import com.travelportal.vm.SearchSpecialRateVM;
import com.travelportal.vm.SerachHotelRoomType;
import com.travelportal.vm.SerachedHotelbyDate;
import com.travelportal.vm.SerachedRoomRateDetail;
import com.travelportal.vm.SerachedRoomType;
import com.travelportal.vm.ServicesVM;
import com.travelportal.vm.SpecialsMarketVM;
import com.travelportal.vm.SpecialsVM;

public class AllRateController extends Controller {

	@Transactional(readOnly = true)
    public static Result getDatewiseHotel() {
    	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    		
    	int flag = 0;

    	long diffInpromo = 0;
    	
    	Map<String, Object> mapObject = new HashMap<String, Object>();
    	List<HotelSearch> hotellist = new ArrayList<>();
		Map<String, String[]> map1 = request().queryString();
		Map<Long, Long> map = new HashMap<Long, Long>();
	
		Set<String> mapDate = new HashSet<String>();
		String[] fromDate ={"11-04-2015"};
		String[] toDate = {"14-04-2015"};
		String[] cId = {"5"};
		//String[] sId =  {"1"};
		String[] cityId = {"1"};

		
		Date formDate = null;
		Date toDates = null;
		try {
			formDate = format.parse(fromDate[0]);
			toDates = format.parse(toDate[0]);
		} catch (ParseException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		long dayDiff;
		if(toDates.getTime() == formDate.getTime()){
			dayDiff = 1;
			diffInpromo = dayDiff;
		}else{
			long diff = toDates.getTime() - formDate.getTime();

			dayDiff = diff / (1000 * 60 * 60 * 24);
			diffInpromo = dayDiff;
		}
		
    	    		
    		List<BigInteger> rateMeta = RateMeta.getsupplierId(
    				Integer.parseInt(cityId[0]),
    				Integer.parseInt(cId[0])); // Long.parseLong(roomId[0]),  Integer.parseInt(sId[0]),

    		for (BigInteger rate1 : rateMeta) {
    			

    			Calendar c = Calendar.getInstance();
    			c.setTime(formDate);
    			c.set(Calendar.MILLISECOND, 0);

    			
    			List<SerachedHotelbyDate> Datelist = new ArrayList<>();
    			HotelSearch hProfileVM = new HotelSearch();
    			Long object = map.get(rate1.longValue());
    			HotelProfile hAmenities = HotelProfile.findAllData(rate1.longValue());
    			if (object == null) {


    				hProfileVM.setSupplierCode(hAmenities.getSupplier_code());
    				hProfileVM.setHotelNm(hAmenities.getHotelName());
    				hProfileVM.setSupplierNm(hAmenities.getSupplierName());

    				if (hAmenities.getStartRatings() != null) {
    					hProfileVM.setStartRating(hAmenities.getStartRatings()
    							.getId());
    				}
    				hProfileVM.currencyId = hAmenities.getCurrency().getCurrencyCode();
    				hProfileVM.currencyName = hAmenities.getCurrency().getCurrencyName();
    				String currency = hAmenities.getCurrency().getCurrencyName();
    				String[] currencySplit;
    				 currencySplit = currency.split(" - ");
    				hProfileVM.currencyShort = currencySplit[0];
    				hProfileVM.setHotelAddr(hAmenities.getAddress());
    				hProfileVM.setCityCode(hAmenities.getCity().getCityCode());
    				hProfileVM.setCheckIn(fromDate[0]);
    				hProfileVM.setCheckOut(toDate[0]);
    				List<ServicesVM> sList = new ArrayList<>();
    				
    				/*for (HotelServices hoServices : hAmenities.getServices()){
    					ServicesVM sVm=new ServicesVM();
    					sVm.setServiceId(hoServices.getServiceId());
    					sVm.setServiceName(hoServices.getServiceName());
    					sList.add(sVm);
    				}*/
    				hProfileVM.setServices(sList);
    				hProfileVM.setNationality(Integer.parseInt(cId[0]));
    				/*InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(hAmenities.getSupplier_code());
    				if(infowiseimagesPath.getGeneralDescription() != null){
    				hProfileVM.setImgDescription(infowiseimagesPath.getGeneralDescription());
    				}*/
    				for (int i = 0; i < dayDiff; i++) {
    					
    					List<HotelRoomTypes> roomType = HotelRoomTypes
    							.getHotelRoomDetails(rate1.longValue());
    					int days = c.get(Calendar.DAY_OF_WEEK)-1;
    					SerachedHotelbyDate hotelBydateVM = new SerachedHotelbyDate();
    					hotelBydateVM.setDate(format.format(c.getTime()));
    					Map<Long, Long> mapRm = new HashMap<Long, Long>();
    					Map<Long, Long> mapcancel = new HashMap<Long, Long>();
    					Map<Long, Long> mapRate = new HashMap<Long, Long>();
    					List<SerachedRoomType> roomlist = new ArrayList<>();
    					for (HotelRoomTypes room : roomType) {
    						
    						int count=0;
    						Long objectRm = (Long) mapRm.get(room.getRoomId());
    						//
    						if (objectRm == null) {

    							List<RateMeta> rateMeta1 = RateMeta.getdatecheck(
    									room.getRoomId(),
    									//Integer.parseInt(sId[0]),
    									Integer.parseInt(cId[0]), c.getTime(),
    									hAmenities.getSupplier_code()); // Long.parseLong(roomId[0])

    							int ib = 1;
    							List<SerachedRoomRateDetail> list = new ArrayList<>();
    							SerachedRoomType roomtyp = new SerachedRoomType();
    							
    							List<SpecialsVM> listsp = new ArrayList<>();
    							List<Specials> specialsList = Specials.findSpecialByDateandroom(c.getTime(),room.getRoomId());
    							for(Specials special : specialsList) {
    								SpecialsVM specialsVM = new SpecialsVM();
    								specialsVM.id = special.getId();
    								specialsVM.fromDate = format.format(special.getFromDate());
    								specialsVM.toDate = format.format(special.getToDate());
    								specialsVM.promotionName = special.getPromotionName();
    								specialsVM.supplierCode = special.getSupplierCode();
    								    								
    							List<SpecialsMarket> marketList = SpecialsMarket.findBySpecialsIdnationality(special.getId(),Integer.parseInt(cId[0]));
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
    								if(!specialsVM.markets.isEmpty()){
    								listsp.add(specialsVM);
    								}
    								
    							}
    							roomtyp.setSpecials(listsp);
    							 
    							if(!roomtyp.getSpecials().isEmpty()){
    								count++;
    								roomtyp.setPcount(count);
    	    					}
    							
    							for (RateMeta rate : rateMeta1) {
    								roomtyp.setRoomId(room.getRoomId());
    								roomtyp.setRoomName(room.getRoomType());
    								roomtyp.setRoomSize(room.getRoomSize());
    								/*List<RoomAmenitiesVm> rList = new ArrayList<>();
    								
    								for (RoomAmenities roomAmenitiesVm : room.getAmenities()){
    									RoomAmenitiesVm rooAmenitiesVm = new RoomAmenitiesVm();
    									rooAmenitiesVm.setAmenityId(roomAmenitiesVm.getAmenityId());
    									rooAmenitiesVm.setAmenityNm(roomAmenitiesVm.getAmenityNm());
    									rList.add(rooAmenitiesVm);
    								}
    								
    									roomtyp.setAmenities(rList);*/
    	
    									
    								RateDetails rateDetails = RateDetails
    										.findByRateMetaId(rate.getId());
    								
    								List<RateSpecialDays> reDays = RateSpecialDays.findByRateMetaId(rate.getId());

    								List<PersonRate> personRate = PersonRate
    										.findByRateMetaId(rate.getId());

    								AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate.getId());
    								int flag1=0;
    								int aRoom = 0;
    								SearchAllotmentMarketVM Allvm = new SearchAllotmentMarketVM();

    								
    								if (alloMarket != null) {
    									
    									Allvm.setAllotmentMarketId(alloMarket.getAllotmentMarketId());
    									Allvm.setAllocation(alloMarket.getAllocation());
    									Allvm.setChoose(alloMarket.getChoose());
    									Allvm.setPeriod(alloMarket.getPeriod());
    									Allvm.setSpecifyAllot(alloMarket.getSpecifyAllot());
    									Allvm.setApplyMarket(alloMarket.getApplyMarket());
    									Allvm.setStopAllocation(alloMarket.getStopAllocation());
    									Allvm.setStopPeriod(alloMarket.getStopPeriod());
    									if(alloMarket.getFromDate() != null){
    									Allvm.setFromDate(format.format(alloMarket.getFromDate()));
    									}
    									if(alloMarket.getToDate() != null){
    									Allvm.setToDate(format.format(alloMarket.getToDate()));
    									}
    									if(alloMarket.getAllocation() == 2){
    										//Allvm.setFlag(1);
    										flag1 =1;
    									}
    									if(alloMarket.getAllocation() == 3){
    										RoomAllotedRateWise rAllotedRateWise= RoomAllotedRateWise.findByRateIdandDate(rate.getId(), c.getTime());
    									
    										if(rAllotedRateWise != null){
    										
    										aRoom = alloMarket.getChoose() - rAllotedRateWise.getRoomCount();
    										if(aRoom < 1){
    											flag1 = 1;
    										}
    										
    										}
    									}
    									AllotmentMarket allotflag = AllotmentMarket
        										.getnationalitywiseMark(alloMarket.getAllotmentMarketId(),Integer.parseInt(cId[0]));
    									
    									if(allotflag == null){
    										//Allvm.setFlag(1);
    										flag1 =1;
    									}
    								}
    								
    								SerachedRoomRateDetail rateVM = new SerachedRoomRateDetail();
    								rateVM.setAdult_occupancy(room
    										.getMaxAdultOccupancy());
    								rateVM.setId(rate.getId());
    								rateVM.setFlag(flag1);
    								if(flag1 != 1){
    									rateVM.setAvailableRoom(aRoom);
    								}
    								rateVM.setAllotmentmarket(Allvm);
    								    								
    								SearchSpecialRateVM specialRateVM = new SearchSpecialRateVM();
    								if (rateDetails.getSpecialDays() != null) {
    									String week[] = rateDetails
    											.getSpecialDays().split(",");
    									for (String day : week) {
    										StringBuilder sb = new StringBuilder(
    												day);
    										if (day.contains("[")) {
    											sb.deleteCharAt(sb.indexOf("["));
    										}
    										if (day.contains("]")) {
    											sb.deleteCharAt(sb.indexOf("]"));
    										}
    										if (day.contains(" ")) {
    											sb.deleteCharAt(sb.indexOf(" "));
    										}
    										specialRateVM.weekDays.add(sb
    												.toString());
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
    								int findrate = 0;
    								Long objectcancel;
    								for (PersonRate person : personRate) {
    									List<CancellationPolicy> cancellation = CancellationPolicy.findByRateMetaId(person.getRate().getId());
    									if (person.getIsNormal() > 2) {
    										
    										for(RateSpecialDays rDays:reDays){
    											
    											if(rDays.getIsSpecialdaysRate() > 2){
    												Date sformDate = null;
        											Date stoDates = null;
        											try {
        												sformDate = format.parse(rDays.getFromspecialDate());
        												stoDates = format.parse(rDays.getTospecialDate());
        											} catch (ParseException e) { // TODO Auto-generated catch block
        												e.printStackTrace();
        											}
        											Double value = rDays.getIsSpecialdaysRate();
        											long sdayDiff;
        											if(stoDates.getTime() == sformDate.getTime()){
        												sdayDiff = 1;
        												//diffInpromo = sdayDiff;
        											}else{
        												long sdiff = stoDates.getTime() - sformDate.getTime();

        												sdayDiff = sdiff / (1000 * 60 * 60 * 24);
        												//diffInpromo = sdayDiff;
        											}

        									    	Calendar specialfromDate = Calendar.getInstance();
        									    	specialfromDate.setTime(sformDate);
        									    	specialfromDate.set(Calendar.MILLISECOND, 0);

        											for (int j = 0; j < sdayDiff; j++) {
        						    					
        												if(c.getTime().equals(specialfromDate.getTime())){
        													if (person.getIsNormal() == rDays.getIsSpecialdaysRate()) {
        		    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
        		    													person);
        		    											vm.rateAvg = person.getRateValue(); 
        		    											if(person.getMealType() != null){
        		    											vm.mealTypeName = person.getMealType().getMealTypeNm();
        		    											}
        		    											vm.adult = person.getNumberOfPersons();
        		    											rateVM.rateDetails.add(vm);
        		    											}
        													findrate = 1;
        													
        													for(CancellationPolicy cancel:cancellation) {
        														objectcancel = (Long) mapcancel.get(cancel.getId());
            						    						
            						    						if (objectcancel == null) {
            						    							
            													if(cancel.getIsNormal() > 2){
            														if(person.getIsNormal() == cancel.getIsNormal()){
            														CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
            														rateVM.cancellation.add(vm);
            														}
            													}
            													mapcancel.put(cancel.getId(), Long.parseLong("1"));
            						    						}
            													
            												}
        												}
        												
        												
        						    					specialfromDate.add(Calendar.DATE, 1);
        											}
    												
    											}
    											
    										}
    										
    										
    									}
    									
    									if(rateDetails.getIsSpecialRate() == 1.0 && findrate == 0){
    										if(days == 0 && specialRateVM.rateDay0) {
    											if (person.getIsNormal() == 1) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											
    											for(CancellationPolicy cancel:cancellation) {
													objectcancel = (Long) mapcancel.get(cancel.getId());
						    						
						    						if (objectcancel == null) {
        											if(cancel.getIsNormal() == 1) {
        												CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
        												rateVM.cancellation.add(vm1);
        											}
        											mapcancel.put(cancel.getId(), Long.parseLong("1"));
						    						}
    											 }
    											}
    										}else if(days == 1 && specialRateVM.rateDay1) {
    											if (person.getIsNormal() == 1) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											for(CancellationPolicy cancel:cancellation) {
													objectcancel = (Long) mapcancel.get(cancel.getId());
						    						
						    						if (objectcancel == null) {
        											if(cancel.getIsNormal() == 1) {
        												CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
        												rateVM.cancellation.add(vm1);
        											}
        											mapcancel.put(cancel.getId(), Long.parseLong("1"));
						    						}
    											 }
    											}
    										}else if(days == 2 && specialRateVM.rateDay2) {
    											if (person.getIsNormal() == 1) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											for(CancellationPolicy cancel:cancellation) {
													objectcancel = (Long) mapcancel.get(cancel.getId());
						    						
						    						if (objectcancel == null) {
        											if(cancel.getIsNormal() == 1) {
        												CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
        												rateVM.cancellation.add(vm1);
        											}
        											mapcancel.put(cancel.getId(), Long.parseLong("1"));
						    						}
    											 }
    											}
    										}else if(days == 3 && specialRateVM.rateDay3) {
    											if (person.getIsNormal() == 1) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											for(CancellationPolicy cancel:cancellation) {
													objectcancel = (Long) mapcancel.get(cancel.getId());
						    						
						    						if (objectcancel == null) {
        											if(cancel.getIsNormal() == 1) {
        												CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
        												rateVM.cancellation.add(vm1);
        											}
        											mapcancel.put(cancel.getId(), Long.parseLong("1"));
						    						}
    											 }
    											}
    										}else if(days == 4 && specialRateVM.rateDay4) {
    											if (person.getIsNormal() == 1) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											for(CancellationPolicy cancel:cancellation) {
													objectcancel = (Long) mapcancel.get(cancel.getId());
						    						
						    						if (objectcancel == null) {
        											if(cancel.getIsNormal() == 1) {
        												CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
        												rateVM.cancellation.add(vm1);
        											}
        											mapcancel.put(cancel.getId(), Long.parseLong("1"));
						    						}
    											 }
    											}
    										}else if(days == 5 && specialRateVM.rateDay5) {
    											if (person.getIsNormal() == 1) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											for(CancellationPolicy cancel:cancellation) {
													objectcancel = (Long) mapcancel.get(cancel.getId());
						    						
						    						if (objectcancel == null) {
        											if(cancel.getIsNormal() == 1) {
        												CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
        												rateVM.cancellation.add(vm1);
        											}
        											mapcancel.put(cancel.getId(), Long.parseLong("1"));
						    						}
    											 }
    											}
    										}else if(days == 6 && specialRateVM.rateDay6) {
    											if (person.getIsNormal() == 1) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											for(CancellationPolicy cancel:cancellation) {
													objectcancel = (Long) mapcancel.get(cancel.getId());
						    						
						    						if (objectcancel == null) {
        											if(cancel.getIsNormal() == 1) {
        												CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
        												rateVM.cancellation.add(vm1);
        											}
        											mapcancel.put(cancel.getId(), Long.parseLong("1"));
						    						}
    											 }
    											}
    										}else{
    											if (person.getIsNormal() == 0) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											for(CancellationPolicy cancel:cancellation) {
													objectcancel = (Long) mapcancel.get(cancel.getId());
						    						
						    						if (objectcancel == null) {
        											if(cancel.getIsNormal() == 0) {
        												CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
        												rateVM.cancellation.add(vm1);
        											}
        											mapcancel.put(cancel.getId(), Long.parseLong("1"));
						    						}
    											 }
    											}
    										}
    										
    									}else{
    																				
    										if (person.getIsNormal() == 0 && findrate == 0) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);
    											vm.rateAvg = person.getRateValue(); 
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											for(CancellationPolicy cancel:cancellation) {
													objectcancel = (Long) mapcancel.get(cancel.getId());
						    						
						    						if (objectcancel == null) {
        											if(cancel.getIsNormal() == 0) {
        												CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
        												rateVM.cancellation.add(vm1);
        											}
        											mapcancel.put(cancel.getId(), Long.parseLong("1"));
						    						}
    											 }
    											}
    										
    									}
    									
    									
    									/*if (person.getIsNormal() == 0) {
    										SearchRateDetailsVM vm = new SearchRateDetailsVM(
    												person);
    										vm.rateAvg = person.getRateValue(); 
    										rateVM.rateDetails.add(vm);
    										//normalRateVM.rateDetails.add(vm);
    										if (person.getNumberOfPersons().equals(
    												"1 Adult")) {
    											
    										
    										}
    									}*/

    									/*if (person.getIsNormal() == 1) {
    										SearchRateDetailsVM vm = new SearchRateDetailsVM(
    												person);										
    										vm.rateAvg = person.getRateValue();
    										specialRateVM.rateDetails.add(vm);
    										
    									}*/
    									
    								}

    								list.add(rateVM);
    							
    							}
    							mapRm.put(room.getRoomId(), Long.parseLong("1"));
    							
    							roomtyp.setHotelRoomRateDetail(list);
    							if(!roomtyp.hotelRoomRateDetail.isEmpty()){
    							roomlist.add(roomtyp);
    							}
    						}
    						
    						hProfileVM.setHotelbyDate(Datelist);
    					}
    					
    					hotelBydateVM.setRoomType(roomlist);
    					
    					if (!hotelBydateVM.getRoomType().isEmpty()) {
    						Datelist.add(hotelBydateVM);
    					}else{
    						//hProfileVM.setFlag("1");
    					}

    					c.add(Calendar.DATE, 1);
    				}
    				
    				map.put(rate1.longValue(), Long.parseLong("1"));

    				if (!hProfileVM.getHotelbyDate().isEmpty()) {
    					hotellist.add(hProfileVM);
    				}
    			}
    		}
    		Map<Long, Integer> promoMap = new HashMap<Long, Integer>();
    		
    		//List<SpecialsVM> listsp = new ArrayList<>();
    		List<Long> hotelNo = new ArrayList<>();
    		List<SerachHotelRoomType> hotelRMlist = new ArrayList<>();
    		Map<Long, SerachedRoomRateDetail> mapRM = new HashMap<Long, SerachedRoomRateDetail>();
    		Map<Long, List<SpecialsVM>> mapSpecials = new HashMap<Long, List<SpecialsVM>>();
    		int count=0;
    		for (HotelSearch hotel : hotellist) {
    			
    			hotelNo.add(hotel.supplierCode);
    			
    			//int newHotel=0; 
    			int dataVar = 0;
    			int countRoom = count;
    			List<Integer> arrayCount = new ArrayList<Integer>();
    			for (SerachedHotelbyDate date : hotel.hotelbyDate) {
    				int newHotel=countRoom; 
    				dataVar++;
    				int aCount = 0;
    			//	int arrayCount[] = {0};
    				
    				for (SerachedRoomType roomTP : date.getRoomType()) {
    					
    					Double total = 0.0;
    					Double avg = 0.0;
    					
    					SerachHotelRoomType sHotelRoomType = new SerachHotelRoomType();
    					sHotelRoomType.hotelRoomRateDetail = new ArrayList<SerachedRoomRateDetail>();

    					
    					SerachedRoomRateDetail sRateDetail = new SerachedRoomRateDetail();
    					sRateDetail.rateDetailsNormal = new ArrayList<SearchRateDetailsVM>();

    					List<SerachedRoomRateDetail> serachedRoomRateDetails = new ArrayList<>();
    					List<SearchRateDetailsVM> searchRateDetailsVMs = new ArrayList<>();
    					SerachedRoomRateDetail objectRM = mapRM.get(roomTP.getRoomId());
    					//Integer objectRateforP = promoMap.get(roomTP.getRoomId());
    				//	List<SpecialsVM> objectSpecials = mapSpecials.get(roomTP.getRoomId());
    					 if (objectRM == null) {
    						
    						count++;
    					
    						sHotelRoomType.setRoomId(roomTP.getRoomId());
    						sHotelRoomType.setRoomName(roomTP.getRoomName());
    						//sHotelRoomType.setAmenities(roomTP.getAmenities());
    						sHotelRoomType.setSpecials(roomTP.getSpecials());
    					
    						arrayCount.add(aCount, roomTP.getPcount());
    					
    						for (SerachedRoomRateDetail rateObj : roomTP
    								.getHotelRoomRateDetail()) {
    						
    							for (SearchRateDetailsVM detailsVM : rateObj.rateDetails) {
    								SearchRateDetailsVM searchRateDetailsVM = new SearchRateDetailsVM();
    								searchRateDetailsVM
    										.setRateAvg(detailsVM.getRateValue());
    								searchRateDetailsVM.setAdult(detailsVM.getAdult());
    								searchRateDetailsVM.setMealTypeName(detailsVM.mealTypeName);
    								searchRateDetailsVMs.add(searchRateDetailsVM);
    								sRateDetail.rateDetailsNormal
    										.add(searchRateDetailsVM);

    							}
    							sRateDetail.setAdult_occupancy(rateObj.getAdult_occupancy());
    							//if(){
    							sRateDetail.setCancellation(rateObj.getCancellation());
    							//}
    							
    						}
    						mapRM.put(roomTP.getRoomId(), sRateDetail);
    						promoMap.put(roomTP.getRoomId(),roomTP.getPcount());
    						if(!roomTP.getSpecials().isEmpty()){
    						mapSpecials.put(roomTP.getRoomId(), (List<SpecialsVM>) roomTP.getSpecials());
    						}

    					}else {
    						List<Double> totalNo = new ArrayList<Double>();
    						int i = 0;
    						for (SearchRateDetailsVM ord : objectRM.rateDetailsNormal) {
    							totalNo.add(i, ord.rateAvg);
    							i++;
    						}
    						int ptotal = 0;
    						
    						ptotal = arrayCount.get(aCount) + roomTP.getPcount();
    						
    						for (SpecialsVM specialObj : roomTP.specials){
    							for (SpecialsMarketVM marketObj : specialObj.markets){
    								if(diffInpromo < Integer.parseInt(marketObj.stayDays)){
    									ptotal = 1;
    								}
    							}
    						}
    						
    						arrayCount.set(aCount, ptotal);
    						
    						for (SerachedRoomRateDetail rateObj : roomTP
    								.getHotelRoomRateDetail()) {
    							
    							int x=0;
    							for (SearchRateDetailsVM detailsVM : rateObj.rateDetails) {
    								
    								if(detailsVM.getRateValue() > totalNo.get(x)){
    									total= totalNo.get(x);
    								}else{
    									total= detailsVM.getRateValue();
    								}
    							
    								hotelRMlist.get(newHotel).hotelRoomRateDetail.get(0).rateDetailsNormal.get(x).setRateAvg(total);
    							
    								sRateDetail.rateDetailsNormal
    										.add(hotelRMlist.get(newHotel).hotelRoomRateDetail.get(0).rateDetailsNormal.get(x));
    								x++;
    							}
    							sRateDetail.setCancellation(rateObj.getCancellation());
    						}
    						mapRM.put(roomTP.getRoomId(), sRateDetail);
    						promoMap.put(roomTP.getRoomId(),arrayCount.get(aCount));
    						if(!roomTP.getSpecials().isEmpty()){
        						mapSpecials.put(roomTP.getRoomId(), (List<SpecialsVM>) roomTP.getSpecials());
        						}
    						
    						newHotel++;
    					}
    					
    					
    					sHotelRoomType.hotelRoomRateDetail.add(sRateDetail);
    				

    					if(sHotelRoomType.roomId != null){	
    						hotelRMlist.add(sHotelRoomType);
    						hotel.hotelbyRoom.add(sHotelRoomType);
    					}
    					aCount++;
    				}
    				
    								 
    			}
    			int diffProm;
    			for(SerachHotelRoomType room:hotel.hotelbyRoom){
    				for (Entry<Long, Integer> entry : promoMap.entrySet()) {
    				if(room.getRoomId() == entry.getKey()){
    					room.setPcount(entry.getValue());
    					diffProm = (int) (diffInpromo/2);
        				if(entry.getValue() >= diffProm){
        					room.setApplyPromotion(1);
        				}else{
        					room.setApplyPromotion(0);
        				}
    				}
    			  }
    			}
    			for(SerachHotelRoomType room:hotel.hotelbyRoom){
    				for (Entry<Long, List<SpecialsVM>> entry : mapSpecials.entrySet()) {
    				if(room.getRoomId() == entry.getKey()){
    					room.setSpecials(entry.getValue());
    					
    				}
    			  }
    			}
    		
    			
    		}
    		
    		List<Long> hotelCount = new ArrayList<>();
    		Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
    		for(Long no:hotelNo){
    			HotelProfile hProfile = HotelProfile.findAllData(no);
    			hotelCount.add(hProfile.getId());
    			
    		}
    	
    		mapObject.put("hotelId", hotelNo);
    		//mapObject.put("hotelAmenities", hotelAmenities);
    		//mapObject.put("hotellocation",hotelLocation);
    		//mapObject.put("HotelServ",hotelServices);
    		mapObject.put("hotellist", hotellist);
    		return ok(Json.toJson(hotellist));
    	//return ok(Json.toJson(hotellist));
    		//return ok(Json.toJson(hotelLocation));
    }
	
}
