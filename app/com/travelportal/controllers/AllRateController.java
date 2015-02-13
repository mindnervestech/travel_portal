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
import scala.math.BigInt;

import com.travelportal.domain.HotelAmenities;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.InfoWiseImagesPath;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RoomAllotedRateWise;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.domain.rooms.Specials;
import com.travelportal.domain.rooms.SpecialsMarket;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.HotelSearch;
import com.travelportal.vm.RoomAmenitiesVm;
import com.travelportal.vm.RoomType;
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
		String[] fromDate ={"10-04-2015"};
		String[] toDate = {"14-04-2015"};
		String[] cId = {"2"};
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
    				
    				for (HotelServices hoServices : hAmenities.getServices()){
    					ServicesVM sVm=new ServicesVM();
    					sVm.setServiceId(hoServices.getServiceId());
    					sVm.setServiceName(hoServices.getServiceName());
    					sList.add(sVm);
    				}
    				hProfileVM.setServices(sList);
    				hProfileVM.setNationality(Integer.parseInt(cId[0]));
    				InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(hAmenities.getSupplier_code());
    				hProfileVM.setImgDescription(infowiseimagesPath.getGeneralDescription());
    				
    				//hProfileVM.setFlag("0");
    				
    				
    				for (int i = 0; i < dayDiff; i++) {
    					
    					List<HotelRoomTypes> roomType = HotelRoomTypes
    							.getHotelRoomDetails(rate1.longValue());
    					int days = c.get(Calendar.DAY_OF_WEEK)-1;
    					SerachedHotelbyDate hotelBydateVM = new SerachedHotelbyDate();
    					hotelBydateVM.setDate(format.format(c.getTime()));
    					Map<Long, Long> mapRm = new HashMap<Long, Long>();
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
    								
    								List<RoomAmenitiesVm> rList = new ArrayList<>();
    								
    								for (RoomAmenities roomAmenitiesVm : room.getAmenities()){
    									RoomAmenitiesVm rooAmenitiesVm = new RoomAmenitiesVm();
    									rooAmenitiesVm.setAmenityId(roomAmenitiesVm.getAmenityId());
    									rooAmenitiesVm.setAmenityNm(roomAmenitiesVm.getAmenityNm());
    									rList.add(rooAmenitiesVm);
    								}
    								
    									roomtyp.setAmenities(rList);
    	
    									
    								RateDetails rateDetails = RateDetails
    										.findByRateMetaId(rate.getId());

    								List<PersonRate> personRate = PersonRate
    										.findByRateMetaId(rate.getId());

    								AllotmentMarket alloMarket = AllotmentMarket
    										.getOneMarket(rate.getId());
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
    										System.out.println(rAllotedRateWise.getRoomCount());
    										
    										aRoom = alloMarket.getChoose() - rAllotedRateWise.getRoomCount();
    										System.out.println("++++++++++++++++++++++");
    										System.out.println(aRoom);
    										if(aRoom < 1){
    											System.out.println("aRoom NOt AAA");
    											flag1 = 1;
    										}
    										
    										}
    									}
    									/*AllotmentMarket allotflag = AllotmentMarket
        										.getnationalitywiseMark(alloMarket.getAllotmentMarketId(),Integer.parseInt(cId[0]));
    									
    									if(allotflag == null){
    										//Allvm.setFlag(1);
    										flag1 =1;
    									}*/
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
    								for (PersonRate person : personRate) {
    									
    									if(rateDetails.isSpecialRate() == true){
    										if(days == 0 && specialRateVM.rateDay0) {
    											if (person.isNormal() == false) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											}
    										}else if(days == 1 && specialRateVM.rateDay1) {
    											if (person.isNormal() == false) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											}
    										}else if(days == 2 && specialRateVM.rateDay2) {
    											if (person.isNormal() == false) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											}
    										}else if(days == 3 && specialRateVM.rateDay3) {
    											if (person.isNormal() == false) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											}
    										}else if(days == 4 && specialRateVM.rateDay4) {
    											if (person.isNormal() == false) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											}
    										}else if(days == 5 && specialRateVM.rateDay5) {
    											if (person.isNormal() == false) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											}
    										}else if(days == 6 && specialRateVM.rateDay6) {
    											if (person.isNormal() == false) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);										
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											}
    										}else{
    											if (person.isNormal() == true) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);
    											vm.rateAvg = person.getRateValue();
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											}
    										}
    										
    									}else{
    																				
    										if (person.isNormal() == true) {
    											SearchRateDetailsVM vm = new SearchRateDetailsVM(
    													person);
    											vm.rateAvg = person.getRateValue(); 
    											if(person.getMealType() != null){
    											vm.mealTypeName = person.getMealType().getMealTypeNm();
    											}
    											vm.adult = person.getNumberOfPersons();
    											rateVM.rateDetails.add(vm);
    											}
    									}
    									
    									
    									if (person.isNormal() == true) {
    										SearchRateDetailsVM vm = new SearchRateDetailsVM(
    												person);
    										vm.rateAvg = person.getRateValue(); 
    										rateVM.rateDetails.add(vm);
    										//normalRateVM.rateDetails.add(vm);
    										if (person.getNumberOfPersons().equals(
    												"1 Adult")) {
    											
    										
    										}
    									}

    									if (person.isNormal() == false) {
    										SearchRateDetailsVM vm = new SearchRateDetailsVM(
    												person);										
    										vm.rateAvg = person.getRateValue();
    										specialRateVM.rateDetails.add(vm);
    										
    									}

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
    						sHotelRoomType.setAmenities(roomTP.getAmenities());
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
    						System.out.println("+++++++++++++++ Count 1 ++++++++++++++++++++++++++++++++");
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
    		/*	for (Entry<Long, Integer> entry : promoMap.entrySet()) {
    			    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
    			}*/
    			
    		}
    		
    		List<Long> hotelCount = new ArrayList<>();
    		Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
    		for(Long no:hotelNo){
    			HotelProfile hProfile = HotelProfile.findAllData(no);
    			hotelCount.add(hProfile.getId());
    			
    		}
    		/*List<Map> hotelAmenities = HotelAmenities.getamenitiesCount(hotelCount);
    		
    		List<Map> hotelLocation = HotelProfile.getlocationCount(hotelCount);
    		
    		List<Map> hotelServices =HotelServices.getservicesCount(hotelCount);   	*/	
    		
    		//return ok(Json.toJson(abc));
    		
    		/*for (HotelSearch hotel : hotellist) {
    		double min=0.0;
			int minStart = 0;
			for(SerachHotelRoomType sHotelRoomType2:hotel.hotelbyRoom){
				
				for (SerachedRoomRateDetail roomRateDetail:sHotelRoomType2.hotelRoomRateDetail) {
					
					for(SearchRateDetailsVM sRD:roomRateDetail.rateDetailsNormal){
						
						if(sRD.adult.equals("1 Adult")){
							if(minStart == 0){
								min = sRD.rateAvg;
								minStart++;
							}
							if(min > sRD.rateAvg){
								min = sRD.rateAvg;
							}
						}
					}
				}
			 }
			hotel.minRate = min;
    		}*/
		
    		//Collections.sort(hotellist,new HotelComparator());
    		
    		//JsonNode personJson = Json.toJson(hotellist);
    		mapObject.put("hotelId", hotelNo);
    		//mapObject.put("hotelAmenities", hotelAmenities);
    		//mapObject.put("hotellocation",hotelLocation);
    		//mapObject.put("HotelServ",hotelServices);
    		mapObject.put("hotellist", hotellist);
    		return ok(Json.toJson(hotellist));
    	//return ok(Json.toJson(hotellist));
    		//return ok(Json.toJson(hotelLocation));
    }
	
	
	
	/*HotelProfile hProfile = HotelProfile.findAllData(no);
	Set<HotelAmenities> hotelamenities = hProfile.getAmenities();
	for (HotelAmenities amenities : hotelamenities) {
		//List<HotelAmenities> amenities2 = new ArrayList<>();
		//amenities2.
		Integer object = map2.get(amenities.getAmenitiesCode());
		if (object == null) {
			map2.put(amenities.getAmenitiesCode(), 1);
		} else {
			map2.put(amenities.getAmenitiesCode(), object + 1);
		}
	}*/

	/*
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

	}*/
	
/*
@Transactional(readOnly = true)
	public static Result getDatewiseHotel() {

	
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		int flag = 0;

		List<HotelSearch> hotellist = new ArrayList<>();
		Map<String, String[]> map1 = request().queryString();
		Map<Long, Long> map = new HashMap<Long, Long>();
	
		//Map<Long, Long> mapRate = new HashMap<Long, Long>();
		Set<String> mapDate = new HashSet<String>();
		// String[] roomId = map1.get("roomId");
		System.out.println("_+_+_++_");
		System.out.println(map1.get("fromDate"));
		String[] fromDate = map1.get("fromDate");
		String[] toDate = map1.get("toDate");
		String[] cId = map1.get("countryId");
		String[] sId =  map1.get("sId");
		String[] cityId =  map1.get("cId");
       // String[] supplierCode = map1.get("supplierCode");
        //String[] roomcode = map1.get("roomcode");
        
        
       // String[] fromDate = {searchVM.checkIn};
	//	String[] toDate = {searchVM.checkOut};
		//String[] sId = {searchVM.id};
		//String[] cityId = {searchVM.city};
		//String[] cId = {searchVM.nationalityCode};

		Date fmDate = null;
		Date tDate = null;

		int j = 0;
		
		List<RateMeta> rateMeta = RateMeta.getdatecheck(
				Integer.parseInt(cityId[0]), Integer.parseInt(sId[0]),
				Integer.parseInt(cId[0]));
        
        
		Date fmDate = null;
		Date tDate = null;

		int j = 0;

		List<RateMeta> rateMeta = RateMeta.getdatecheckOneSupp(
				Long.parseLong(supplierCode[0]),
				Integer.parseInt(cId[0])); // Long.parseLong(roomId[0]),

		for (RateMeta rate1 : rateMeta) {

			Date formDate = null;
			Date toDates = null;
			try {
				formDate = format.parse(fromDate[0]);
				toDates = format.parse(toDate[0]);
			} catch (ParseException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}

			Calendar c = Calendar.getInstance();
			c.setTime(formDate);
			c.set(Calendar.MILLISECOND, 0);

			Calendar c1 = Calendar.getInstance();
			c1.setTime(toDates);
			c1.set(Calendar.MILLISECOND, 0);

			Calendar fromdata = Calendar.getInstance();
			fromdata.setTime(rate1.getFromDate());
			fromdata.set(Calendar.MILLISECOND, 0);

			Calendar todata = Calendar.getInstance();
			todata.setTime(rate1.getToDate());
			todata.set(Calendar.MILLISECOND, 0);
			long dayDiff;
			if(toDates.getTime() == formDate.getTime()){
				dayDiff = 1;
			}else{
				long diff = toDates.getTime() - formDate.getTime();

				dayDiff = diff / (1000 * 60 * 60 * 24);
			}
				
			

			List<SerachedHotelbyDate> Datelist = new ArrayList<>();
			HotelSearch hProfileVM = new HotelSearch();
			Long object = (Long) map.get(rate1.getSupplierCode());
			HotelProfile hAmenities = HotelProfile.findAllData(rate1
					.getSupplierCode());
			if (object == null) {

				Double total = 0.0;
				Double avg = 0.0;
				int value = 0;

				
				
				hProfileVM.setSupplierCode(hAmenities.getSupplier_code());
				hProfileVM.setHotelNm(hAmenities.getHotelName());
				hProfileVM.setSupplierNm(hAmenities.getSupplierName());
				hProfileVM.setHoteldescription(hAmenities.getHotelProfileDesc());
				
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
				//Set<HotelamenitiesVM> hotelamenitiesVMs;
			//	hProfileVM.setAmenities(hAmenities.getAmenities());
				
				//Set<HotelAmenities> hotelamenities = hAmenities.getAmenities();
				//hotelamenitiesVMs.add(arg0)
				//hProfileVM.setAmenities(amenities);
				for(HotelAmenities amenities : hAmenities.getAmenities())
				{
					HotelamenitiesVM hotelamenitiesVM=new HotelamenitiesVM();
					hotelamenitiesVM.setAmenitiesCode());
					
				}
				
				
				//hAmenities.getAmenities());
				hProfileVM.setHotelAddr(hAmenities.getAddress());
				hProfileVM.setCityCode(hAmenities.getCity().getCityCode());
				//hProfileVM.setServices1(hAmenities.getIntListServices());
				
				
				List<ServicesVM> sList = new ArrayList<>();
				
				for (HotelServices hoServices : hAmenities.getServices()){
					ServicesVM sVm=new ServicesVM();
					sVm.setServiceId(hoServices.getServiceId());
					sVm.setServiceName(hoServices.getServiceName());
					sList.add(sVm);
				}
				hProfileVM.setServices(sList);
				
				hProfileVM.setCheckIn(fromDate[0]);
				hProfileVM.setCheckOut(toDate[0]);
				hProfileVM.setNationality(Integer.parseInt(cId[0]));
				
				
				for (int i = 0; i < dayDiff; i++) {

					System.out.println(rate1.getSupplierCode());
					List<HotelRoomTypes> roomType = HotelRoomTypes
							.getHotelRoomDetails(rate1.getSupplierCode());
					int days = c.get(Calendar.DAY_OF_WEEK)-1;
					SerachedHotelbyDate hotelBydateVM = new SerachedHotelbyDate();
					hotelBydateVM.setFlag("0");
					hotelBydateVM.setDate(format.format(c.getTime()));
					Map<Long, Long> mapRm = new HashMap<Long, Long>();
					Map<Long, Long> mapRate = new HashMap<Long, Long>();
					List<SerachedRoomType> roomlist = new ArrayList<>();
					for (HotelRoomTypes room : roomType) {
						Long objectRm = (Long) mapRm.get(room.getRoomId());
						if (objectRm == null) {

							List<RateMeta> rateMeta1 = RateMeta.getdatecheckRoom1(
									room.getRoomId(),
									Integer.parseInt(cId[0]), c.getTime(),
									hAmenities.getSupplier_code()); // Long.parseLong(roomId[0])

							int ib = 1;
							List<SerachedRoomRateDetail> list = new ArrayList<>();
							SerachedRoomType roomtyp = new SerachedRoomType();
							
							for (RateMeta rate : rateMeta1) {
								//Long objectRate = (Long) mapRate.get(rate.getId());
								//if (objectRate == null) {
								roomtyp.setRoomId(room.getRoomId());
								roomtyp.setRoomName(room.getRoomType());
								List<RoomAmenitiesVm> rList = new ArrayList<>();
								
								for (RoomAmenities roomAmenitiesVm : room.getAmenities()){
									RoomAmenitiesVm rooAmenitiesVm = new RoomAmenitiesVm();
									rooAmenitiesVm.setAmenityId(roomAmenitiesVm.getAmenityId());
									rooAmenitiesVm.setAmenityNm(roomAmenitiesVm.getAmenityNm());
									rList.add(rooAmenitiesVm);
								}
								
									roomtyp.setAmenities(rList);
								//roomtyp.setAmenities(RoomAmenities.getroomamenities(room.getAmenities()));	//room.getAmenities();
								RateDetails rateDetails = RateDetails
										.findByRateMetaId(rate.getId());

								List<PersonRate> personRate = PersonRate
										.findByRateMetaId(rate.getId());

								AllotmentMarket alloMarket = AllotmentMarket
										.getOneMarket(rate.getId());

								SerachedRoomRateDetail rateVM = new SerachedRoomRateDetail();
								rateVM.setAdult_occupancy(room
										.getMaxAdultOccupancy());
								//rateVM.isSpecialRate = rateDetails
								//		.isSpecialRate();
								// rateVM.setRateName(rate.getRateName());
								rateVM.setId(rate.getId());

								//SeaachNormalRateVM normalRateVM = new SeaachNormalRateVM();
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
								System.out.println("&^%^&%$^%$^");
								System.out.println(specialRateVM.rateDay6);
								for (PersonRate person : personRate) {
									
									if(rateDetails.isSpecialRate() == true){
										if(days == 0 && specialRateVM.rateDay0) {
											if (person.isNormal() == false) {
											SearchRateDetailsVM vm = new SearchRateDetailsVM(
													person);										
											vm.rateAvg = person.getRateValue();
											if(person.getMealType() != null){
											vm.mealTypeName = person.getMealType().getMealTypeNm();
											}
											vm.adult = person.getNumberOfPersons();
											rateVM.rateDetails.add(vm);
											}
										}else if(days == 1 && specialRateVM.rateDay1) {
											if (person.isNormal() == false) {
											SearchRateDetailsVM vm = new SearchRateDetailsVM(
													person);										
											vm.rateAvg = person.getRateValue();
											if(person.getMealType() != null){
											vm.mealTypeName = person.getMealType().getMealTypeNm();
											}
											vm.adult = person.getNumberOfPersons();
											rateVM.rateDetails.add(vm);
											}
										}else if(days == 2 && specialRateVM.rateDay2) {
											if (person.isNormal() == false) {
											SearchRateDetailsVM vm = new SearchRateDetailsVM(
													person);										
											vm.rateAvg = person.getRateValue();
											if(person.getMealType() != null){
											vm.mealTypeName = person.getMealType().getMealTypeNm();
											}
											vm.adult = person.getNumberOfPersons();
											rateVM.rateDetails.add(vm);
											}
										}else if(days == 3 && specialRateVM.rateDay3) {
											if (person.isNormal() == false) {
											SearchRateDetailsVM vm = new SearchRateDetailsVM(
													person);										
											vm.rateAvg = person.getRateValue();
											if(person.getMealType() != null){
											vm.mealTypeName = person.getMealType().getMealTypeNm();
											}
											vm.adult = person.getNumberOfPersons();
											rateVM.rateDetails.add(vm);
											}
										}else if(days == 4 && specialRateVM.rateDay4) {
											if (person.isNormal() == false) {
											SearchRateDetailsVM vm = new SearchRateDetailsVM(
													person);										
											vm.rateAvg = person.getRateValue();
											if(person.getMealType() != null){
											vm.mealTypeName = person.getMealType().getMealTypeNm();
											}
											vm.adult = person.getNumberOfPersons();
											rateVM.rateDetails.add(vm);
											}
										}else if(days == 5 && specialRateVM.rateDay5) {
											if (person.isNormal() == false) {
											SearchRateDetailsVM vm = new SearchRateDetailsVM(
													person);										
											vm.rateAvg = person.getRateValue();
											if(person.getMealType() != null){
											vm.mealTypeName = person.getMealType().getMealTypeNm();
											}
											vm.adult = person.getNumberOfPersons();
											rateVM.rateDetails.add(vm);
											}
										}else if(days == 6 && specialRateVM.rateDay6) {
											if (person.isNormal() == false) {
											SearchRateDetailsVM vm = new SearchRateDetailsVM(
													person);										
											vm.rateAvg = person.getRateValue();
											if(person.getMealType() != null){
											vm.mealTypeName = person.getMealType().getMealTypeNm();
											}
											vm.adult = person.getNumberOfPersons();
											rateVM.rateDetails.add(vm);
											}
										}else{
											if (person.isNormal() == true) {
											SearchRateDetailsVM vm = new SearchRateDetailsVM(
													person);
											vm.rateAvg = person.getRateValue();
											if(person.getMealType() != null){
											vm.mealTypeName = person.getMealType().getMealTypeNm();
											}
											vm.adult = person.getNumberOfPersons();
											rateVM.rateDetails.add(vm);
											}
										}
										
									}else{
										
																				
										if (person.isNormal() == true) {
											SearchRateDetailsVM vm = new SearchRateDetailsVM(
													person);
											vm.rateAvg = person.getRateValue(); 
											if(person.getMealType() != null){
											vm.mealTypeName = person.getMealType().getMealTypeNm();
											}
											vm.adult = person.getNumberOfPersons();
											rateVM.rateDetails.add(vm);
											}
									}
									
								
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
					
					if (hotelBydateVM.getRoomType().isEmpty()) {
						hotelBydateVM.setFlag("1");
						//hProfileVM.setFlag("1");
					}
					Datelist.add(hotelBydateVM);
					c.add(Calendar.DATE, 1);
				}

				map.put(rate1.getSupplierCode(), Long.parseLong("1"));

				if (!hProfileVM.getHotelbyDate().isEmpty()) {
					hotellist.add(hProfileVM);
				}
				//return ok(Json.toJson(hProfileVM));
			}
		}

				//return ok(Json.toJson(null));
		List<SerachHotelRoomType> hotelRMlist = new ArrayList<>();
		// List<SerachedRoomRateDetail> hotelRMRatelist = new ArrayList<>();
		Map<Long, SerachedRoomRateDetail> mapRM = new HashMap<Long, SerachedRoomRateDetail>();
		int count=0;
		for (HotelSearch hotel : hotellist) {
			//int newHotel=0; 
			int dataVar = 0;
			int countRoom = count;
			for (SerachedHotelbyDate date : hotel.hotelbyDate) {
				int newHotel=countRoom; 
				dataVar++;
				for (SerachedRoomType roomTP : date.getRoomType()) {
					
					Double total = 0.0;
					Double avg = 0.0;
					SerachHotelRoomType sHotelRoomType = new SerachHotelRoomType();
					sHotelRoomType.hotelRoomRateDetail = new ArrayList<SerachedRoomRateDetail>();

					
					SerachedRoomRateDetail sRateDetail = new SerachedRoomRateDetail();
					// sRateDetail.normalRate = new SeaachNormalRateVM();
					sRateDetail.rateDetailsNormal = new ArrayList<SearchRateDetailsVM>();

					//sRateDetail.setAdult_occupancy(roomTP.g);
					
					List<SerachedRoomRateDetail> serachedRoomRateDetails = new ArrayList<>();
					List<SearchRateDetailsVM> searchRateDetailsVMs = new ArrayList<>();
					SerachedRoomRateDetail objectRM = mapRM.get(roomTP
							.getRoomId());
					if (objectRM == null) {
						
						count++;
						
						sHotelRoomType.setRoomId(roomTP.getRoomId());
						sHotelRoomType.setRoomName(roomTP.getRoomName());
						sHotelRoomType.setAmenities(roomTP.getAmenities());
						
						for (SerachedRoomRateDetail rateObj : roomTP
								.getHotelRoomRateDetail()) {
						
							for (SearchRateDetailsVM detailsVM : rateObj.rateDetails) {
								SearchRateDetailsVM searchRateDetailsVM = new SearchRateDetailsVM();
								
								//searchRateDetailsVM.setRateValue(detailsVM.getRateValue());
								searchRateDetailsVM
										.setRateAvg(detailsVM.getRateValue());
								searchRateDetailsVM.setAdult(detailsVM.getAdult());
								searchRateDetailsVM.setMealTypeName(detailsVM.getMealTypeName());
								searchRateDetailsVMs.add(searchRateDetailsVM);
								sRateDetail.rateDetailsNormal.add(searchRateDetailsVM);
								
							}
							sRateDetail.setAdult_occupancy(rateObj.getAdult_occupancy());
						}
						//roomTP.hotelRoomRateDetail.add(sRateDetail);
						mapRM.put(roomTP.getRoomId(), sRateDetail);

					}else {
						List<Double> totalNo = new ArrayList<Double>();
						int i = 0;
						for (SearchRateDetailsVM ord : objectRM.rateDetailsNormal) {
							totalNo.add(i, ord.rateAvg);
							i++;
						}
						for (SerachedRoomRateDetail rateObj : roomTP
								.getHotelRoomRateDetail()) {
							
							int x=0;
							for (SearchRateDetailsVM detailsVM : rateObj.rateDetails) {
								
								//hotelRMlist.get(newHotel).hotelRoomRateDetail.get(0).rateDetailsNormal.get(x).rateValue = detailsVM.getRateValue();
								
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
						}
						//roomTP.hotelRoomRateDetail.add(sRateDetail);
						mapRM.put(roomTP.getRoomId(), sRateDetail);
						newHotel++;
					}
					sHotelRoomType.hotelRoomRateDetail.add(sRateDetail);

					for(SerachedRoomRateDetail sDetail :sRateDetail){
						
					}
					
					 if(sHotelRoomType.roomId != null){
					hotelRMlist.add(sHotelRoomType);
					
					hotel.hotelbyRoom.add(sHotelRoomType);
					 }
					 
				}
				double min=0.0;
				int minStart = 0;
				for(SerachHotelRoomType sHotelRoomType2:hotelRMlist){
					
					for (SerachedRoomRateDetail roomRateDetail:sHotelRoomType2.hotelRoomRateDetail) {
						
						for(SearchRateDetailsVM sRD:roomRateDetail.rateDetailsNormal){
							
							System.out.println(sRD.adult);
							if(sRD.adult.equals("1 Adult")){
								if(minStart == 0){
									min = sRD.rateAvg;
									minStart++;
								}
								if(min > sRD.rateAvg){
									min = sRD.rateAvg;
								}
							}
						}
					}
				}
				hotel.minRate = min;
								 
			}
			return ok(Json.toJson(hotel));
		}
		return ok(Json.toJson(null));
	}

*/

/*@Transactional(readOnly = true)
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

	//System.out.println(aId[0]);
	
	String[] arr; 
	arr[0]= 14;
	
	//= aId[0].split(",");
	Date fmDate = null;
	Date tDate = null;
	try {
		fmDate = format.parse(fromDate[0]);
		tDate = format.parse(toDate[0]);
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	List<RateMeta> rateMeta = RateMeta.getRateAndHotel(
			Long.parseLong(roomId[0]), Integer.parseInt(cityId[0]),
			Integer.parseInt(sId[0]), fmDate, tDate);

	for (RateMeta rate : rateMeta) {

		HotelProfileVM hProfileVM = new HotelProfileVM();
		Long object = (Long) map.get(rate.getSupplierCode());
		if (object == null) {
			int flag = 0;
			HotelProfile hAmenities = HotelProfile.findAllData(rate
					.getSupplierCode());
			for (Integer integer : hAmenities.getIntListServices()) {
				HotelServices hServices = HotelServices.findById(integer);
				for (int i = 0; i < arr.length; i++) {
					System.out.println(i);
					if (hServices.getServiceId() == Integer
							.parseInt(arr[i])) {
						hProfileVM.setSupplierCode(hAmenities
								.getSupplier_code());
						hProfileVM.setHotelNm(hAmenities.getHotelName());
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
						flag = 1;
					}
					if (flag == 1) {
						i = arr.length;
					}
				}
			}
			map.put(rate.getSupplierCode(), Long.parseLong("1"));
		}

		RateDetails rateDetails = RateDetails
				.findByRateMetaId(rate.getId());

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

	return ok(Json.toJson(hotellist));

}
*/


/*
@Transactional(readOnly = true)
public static Result getDatewiseHotel() {


	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	int flag = 0;

	List<HotelSearch> hotellist = new ArrayList<>();
	Map<String, String[]> map1 = request().queryString();
	Map<Long, Long> map = new HashMap<Long, Long>();

	//Map<Long, Long> mapRate = new HashMap<Long, Long>();
	Set<String> mapDate = new HashSet<String>();
	// String[] roomId = map1.get("roomId");
	String[] fromDate = map1.get("fromDate");
	String[] toDate = map1.get("toDate");
	String[] sId = map1.get("sId");
	String[] cityId = map1.get("cityId");
	String[] cId = map1.get("countryId");

	Date fmDate = null;
	Date tDate = null;

	int j = 0;

	List<RateMeta> rateMeta = RateMeta.getdatecheck(
			Integer.parseInt(cityId[0]), Integer.parseInt(sId[0]),
			Integer.parseInt(cId[0])); // Long.parseLong(roomId[0]),

	for (RateMeta rate1 : rateMeta) {

		Date formDate = null;
		Date toDates = null;
		try {
			formDate = format.parse(fromDate[0]);
			toDates = format.parse(toDate[0]);
		} catch (ParseException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar c = Calendar.getInstance();
		c.setTime(formDate);
		c.set(Calendar.MILLISECOND, 0);

		Calendar c1 = Calendar.getInstance();
		c1.setTime(toDates);
		c1.set(Calendar.MILLISECOND, 0);

		Calendar fromdata = Calendar.getInstance();
		fromdata.setTime(rate1.getFromDate());
		fromdata.set(Calendar.MILLISECOND, 0);

		Calendar todata = Calendar.getInstance();
		todata.setTime(rate1.getToDate());
		todata.set(Calendar.MILLISECOND, 0);

		long diff = toDates.getTime() - formDate.getTime();

		long dayDiff = diff / (1000 * 60 * 60 * 24);

		List<SerachedHotelbyDate> Datelist = new ArrayList<>();
		HotelSearch hProfileVM = new HotelSearch();
		Long object = (Long) map.get(rate1.getSupplierCode());
		HotelProfile hAmenities = HotelProfile.findAllData(rate1
				.getSupplierCode());
		if (object == null) {

			Double total = 0.0;
			Double avg = 0.0;
			int value = 0;

			
	       
	        
	        File f = new File(infowiseimagesPath.getHotel_Lobby());
	        return ok(f);
			
			hProfileVM.setSupplierCode(hAmenities.getSupplier_code());
			hProfileVM.setHotelNm(hAmenities.getHotelName());
			hProfileVM.setSupplierNm(hAmenities.getSupplierName());
			//if (hAmenities.getCountry() != null) {
		//		hProfileVM.setCountryCode(hAmenities.getCountry()
		//				.getCountryCode());
			//}

			if (hAmenities.getStartRatings() != null) {
				hProfileVM.setStartRating(hAmenities.getStartRatings()
						.getId());
			}
			hProfileVM.setHotelAddr(hAmenities.getAddress());
			hProfileVM.setCityCode(hAmenities.getCity().getCityCode());
			hProfileVM.setServices(hAmenities.getIntListServices());
			hProfileVM.setCheckIn(fromDate[0]);
			hProfileVM.setCheckOut(toDate[0]);
			hProfileVM.setNationality(Integer.parseInt(cId[0]));
			
			
			hProfileVM.setFlag("0");
			for (int i = 0; i < dayDiff; i++) {

				System.out.println(rate1.getSupplierCode());
				List<HotelRoomTypes> roomType = HotelRoomTypes
						.getHotelRoomDetails(rate1.getSupplierCode());
				int days = c.get(Calendar.DAY_OF_WEEK)-1;
				SerachedHotelbyDate hotelBydateVM = new SerachedHotelbyDate();
				hotelBydateVM.setDate(format.format(c.getTime()));
				Map<Long, Long> mapRm = new HashMap<Long, Long>();
				Map<Long, Long> mapRate = new HashMap<Long, Long>();
				List<SerachedRoomType> roomlist = new ArrayList<>();
				for (HotelRoomTypes room : roomType) {
					Long objectRm = (Long) mapRm.get(room.getRoomId());
					if (objectRm == null) {

						List<RateMeta> rateMeta1 = RateMeta.getdatecheck1(
								room.getRoomId(),
								Integer.parseInt(cityId[0]),
								Integer.parseInt(sId[0]),
								Integer.parseInt(cId[0]), c.getTime(),
								hAmenities.getSupplier_code()); // Long.parseLong(roomId[0])

						//if (rateMeta1.isEmpty()) {
						//	hProfileVM.setFlag("1");
						//}
						int ib = 1;
						List<SerachedRoomRateDetail> list = new ArrayList<>();
						SerachedRoomType roomtyp = new SerachedRoomType();
						
						for (RateMeta rate : rateMeta1) {
							//Long objectRate = (Long) mapRate.get(rate.getId());
							//if (objectRate == null) {
							roomtyp.setRoomId(room.getRoomId());
							roomtyp.setRoomName(room.getRoomType());
								
							RateDetails rateDetails = RateDetails
									.findByRateMetaId(rate.getId());

							List<PersonRate> personRate = PersonRate
									.findByRateMetaId(rate.getId());

							AllotmentMarket alloMarket = AllotmentMarket
									.getOneMarket(rate.getId());

							SerachedRoomRateDetail rateVM = new SerachedRoomRateDetail();
							rateVM.setAdult_occupancy(room
									.getMaxAdultOccupancy());
							//rateVM.isSpecialRate = rateDetails
							//		.isSpecialRate();
							// rateVM.setRateName(rate.getRateName());
							rateVM.setId(rate.getId());

							//SeaachNormalRateVM normalRateVM = new SeaachNormalRateVM();
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
							System.out.println("&^%^&%$^%$^");
							System.out.println(specialRateVM.rateDay6);
							for (PersonRate person : personRate) {
								
								if(rateDetails.isSpecialRate() == true){
									if(days == 0 && specialRateVM.rateDay0) {
										if (person.isNormal() == false) {
										SearchRateDetailsVM vm = new SearchRateDetailsVM(
												person);										
										vm.rateAvg = person.getRateValue();
										if(person.getMealType() != null){
										vm.mealTypeName = person.getMealType().getMealTypeNm();
										}
										vm.adult = person.getNumberOfPersons();
										rateVM.rateDetails.add(vm);
										}
									}else if(days == 1 && specialRateVM.rateDay1) {
										if (person.isNormal() == false) {
										SearchRateDetailsVM vm = new SearchRateDetailsVM(
												person);										
										vm.rateAvg = person.getRateValue();
										if(person.getMealType() != null){
										vm.mealTypeName = person.getMealType().getMealTypeNm();
										}
										vm.adult = person.getNumberOfPersons();
										rateVM.rateDetails.add(vm);
										}
									}else if(days == 2 && specialRateVM.rateDay2) {
										if (person.isNormal() == false) {
										SearchRateDetailsVM vm = new SearchRateDetailsVM(
												person);										
										vm.rateAvg = person.getRateValue();
										if(person.getMealType() != null){
										vm.mealTypeName = person.getMealType().getMealTypeNm();
										}
										vm.adult = person.getNumberOfPersons();
										rateVM.rateDetails.add(vm);
										}
									}else if(days == 3 && specialRateVM.rateDay3) {
										if (person.isNormal() == false) {
										SearchRateDetailsVM vm = new SearchRateDetailsVM(
												person);										
										vm.rateAvg = person.getRateValue();
										if(person.getMealType() != null){
										vm.mealTypeName = person.getMealType().getMealTypeNm();
										}
										vm.adult = person.getNumberOfPersons();
										rateVM.rateDetails.add(vm);
										}
									}else if(days == 4 && specialRateVM.rateDay4) {
										if (person.isNormal() == false) {
										SearchRateDetailsVM vm = new SearchRateDetailsVM(
												person);										
										vm.rateAvg = person.getRateValue();
										if(person.getMealType() != null){
										vm.mealTypeName = person.getMealType().getMealTypeNm();
										}
										vm.adult = person.getNumberOfPersons();
										rateVM.rateDetails.add(vm);
										}
									}else if(days == 5 && specialRateVM.rateDay5) {
										if (person.isNormal() == false) {
										SearchRateDetailsVM vm = new SearchRateDetailsVM(
												person);										
										vm.rateAvg = person.getRateValue();
										if(person.getMealType() != null){
										vm.mealTypeName = person.getMealType().getMealTypeNm();
										}
										vm.adult = person.getNumberOfPersons();
										rateVM.rateDetails.add(vm);
										}
									}else if(days == 6 && specialRateVM.rateDay6) {
										if (person.isNormal() == false) {
										SearchRateDetailsVM vm = new SearchRateDetailsVM(
												person);										
										vm.rateAvg = person.getRateValue();
										if(person.getMealType() != null){
										vm.mealTypeName = person.getMealType().getMealTypeNm();
										}
										vm.adult = person.getNumberOfPersons();
										rateVM.rateDetails.add(vm);
										}
									}else{
										if (person.isNormal() == true) {
										SearchRateDetailsVM vm = new SearchRateDetailsVM(
												person);
										vm.rateAvg = person.getRateValue();
										if(person.getMealType() != null){
										vm.mealTypeName = person.getMealType().getMealTypeNm();
										}
										vm.adult = person.getNumberOfPersons();
										rateVM.rateDetails.add(vm);
										}
									}
									
								}else{
									
									//ratePerson();
																			
									if (person.isNormal() == true) {
										SearchRateDetailsVM vm = new SearchRateDetailsVM(
												person);
										vm.rateAvg = person.getRateValue(); 
										if(person.getMealType() != null){
										vm.mealTypeName = person.getMealType().getMealTypeNm();
										}
										vm.adult = person.getNumberOfPersons();
										rateVM.rateDetails.add(vm);
										}
								}
								
								
								if (person.isNormal() == true) {
									SearchRateDetailsVM vm = new SearchRateDetailsVM(
											person);
									vm.rateAvg = person.getRateValue(); 
									rateVM.rateDetails.add(vm);
									//normalRateVM.rateDetails.add(vm);
									if (person.getNumberOfPersons().equals(
											"1 Adult")) {
										
										value++;
									}
								}

								if (person.isNormal() == false) {
									SearchRateDetailsVM vm = new SearchRateDetailsVM(
											person);										
									vm.rateAvg = person.getRateValue();
									specialRateVM.rateDetails.add(vm);
									
								}

							}

							

							

							

							//rateVM.setSpecial(specialRateVM);
							//rateVM.setNormalRate(normalRateVM);

							list.add(rateVM);
							

						


						//mapRate.put(rate.getId(), Long.parseLong("1"));
						//}
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
					hProfileVM.setFlag("1");
				}

				c.add(Calendar.DATE, 1);
			}

			map.put(rate1.getSupplierCode(), Long.parseLong("1"));

			if (!hProfileVM.getHotelbyDate().isEmpty()) {
				hotellist.add(hProfileVM);
			}
		}
	}

	List<SerachHotelRoomType> hotelRMlist = new ArrayList<>();
	// List<SerachedRoomRateDetail> hotelRMRatelist = new ArrayList<>();
	Map<Long, SerachedRoomRateDetail> mapRM = new HashMap<Long, SerachedRoomRateDetail>();
	int count=0;
	for (HotelSearch hotel : hotellist) {
		//int newHotel=0; 
		int dataVar = 0;
		int countRoom = count;
		for (SerachedHotelbyDate date : hotel.hotelbyDate) {
			int newHotel=countRoom; 
			dataVar++;
			for (SerachedRoomType roomTP : date.getRoomType()) {
				
				Double total = 0.0;
				Double avg = 0.0;
				SerachHotelRoomType sHotelRoomType = new SerachHotelRoomType();
				sHotelRoomType.hotelRoomRateDetail = new ArrayList<SerachedRoomRateDetail>();

				
				SerachedRoomRateDetail sRateDetail = new SerachedRoomRateDetail();
				// sRateDetail.normalRate = new SeaachNormalRateVM();
				sRateDetail.rateDetailsNormal = new ArrayList<SearchRateDetailsVM>();

				//sRateDetail.setAdult_occupancy(roomTP.g);
				
				List<SerachedRoomRateDetail> serachedRoomRateDetails = new ArrayList<>();
				List<SearchRateDetailsVM> searchRateDetailsVMs = new ArrayList<>();
				SerachedRoomRateDetail objectRM = mapRM.get(roomTP
						.getRoomId());
				if (objectRM == null) {
					
					count++;
					
					sHotelRoomType.setRoomId(roomTP.getRoomId());
					sHotelRoomType.setRoomName(roomTP.getRoomName());
					for (SerachedRoomRateDetail rateObj : roomTP
							.getHotelRoomRateDetail()) {
					
						for (SearchRateDetailsVM detailsVM : rateObj.rateDetails) {
							SearchRateDetailsVM searchRateDetailsVM = new SearchRateDetailsVM();
							
							//searchRateDetailsVM.setRateValue(detailsVM.getRateValue());
							searchRateDetailsVM
									.setRateAvg(detailsVM.getRateValue());
							searchRateDetailsVM.setAdult(detailsVM.getAdult());
							searchRateDetailsVM.setMealTypeName(detailsVM.getMealTypeName());
							searchRateDetailsVMs.add(searchRateDetailsVM);
							sRateDetail.rateDetailsNormal
									.add(searchRateDetailsVM);

						}
						sRateDetail.setAdult_occupancy(rateObj.getAdult_occupancy());
					}
					//roomTP.hotelRoomRateDetail.add(sRateDetail);
					mapRM.put(roomTP.getRoomId(), sRateDetail);

				}else {
					List<Double> totalNo = new ArrayList<Double>();
					int i = 0;
					for (SearchRateDetailsVM ord : objectRM.rateDetailsNormal) {
						totalNo.add(i, ord.rateAvg);
						i++;
					}
					for (SerachedRoomRateDetail rateObj : roomTP
							.getHotelRoomRateDetail()) {
						
						int x=0;
						for (SearchRateDetailsVM detailsVM : rateObj.rateDetails) {
							
							//hotelRMlist.get(newHotel).hotelRoomRateDetail.get(0).rateDetailsNormal.get(x).rateValue = detailsVM.getRateValue();
							
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
					}
					//roomTP.hotelRoomRateDetail.add(sRateDetail);
					mapRM.put(roomTP.getRoomId(), sRateDetail);
					newHotel++;
				}
				sHotelRoomType.hotelRoomRateDetail.add(sRateDetail);
				System.out.println("99");
				System.out.println("+-----+");
				System.out.println(avg);

				 if(sHotelRoomType.roomId != null){
				hotelRMlist.add(sHotelRoomType);
				
				hotel.hotelbyRoom.add(sHotelRoomType);
				 }
				 
			}

							 
		}
		
	}
	//return ok(Json.toJson(hotelRMlist));
	
	return ok(Json.toJson(hotellist));
}*/




	/*@Transactional(readOnly = true)
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
			fmDate = format.parse(fromDate[0]);
			tDate = format.parse(toDate[0]);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<RateMeta> rateMeta = RateMeta.getHotels(Long.parseLong(roomId[0]),
				Integer.parseInt(cityId[0]), Integer.parseInt(sId[0]), fmDate,
				tDate, Integer.parseInt(cId[0]));

		for (RateMeta rate1 : rateMeta) {

			List<RateVM> list = new ArrayList<>();
			HotelProfileVM hProfileVM = new HotelProfileVM();
			Long object = (Long) map.get(rate1.getSupplierCode());
			HotelProfile hAmenities = HotelProfile.findAllData(rate1
					.getSupplierCode());
			if (object == null) {

				hProfileVM.setSupplierCode(hAmenities.getSupplier_code());
				hProfileVM.setHotelNm(hAmenities.getHotelName());
				hProfileVM.setSupplierNm(hAmenities.getSupplierName());
				if (hAmenities.getCountry() != null) {
					hProfileVM.setCountryCode(hAmenities.getCountry()
							.getCountryCode());
				}
				if (hAmenities.getCurrency() != null) {
					hProfileVM.setCurrencyCode(hAmenities.getCurrency()
							.getCurrencyCode());
				}
				if (hAmenities.getStartRatings() != null) {
					hProfileVM.setStartRating(hAmenities.getStartRatings()
							.getId());
				}

				List<RateMeta> rateMeta1 = RateMeta.getHotels1(
						Long.parseLong(roomId[0]), Integer.parseInt(cityId[0]),
						Integer.parseInt(sId[0]), fmDate, tDate,
						Integer.parseInt(cId[0]), rate1.getSupplierCode());

				for (RateMeta rate : rateMeta1) {

					RateDetails rateDetails = RateDetails.findByRateMetaId(rate
							.getId());

					List<PersonRate> personRate = PersonRate
							.findByRateMetaId(rate.getId());
					List<CancellationPolicy> cancellation = CancellationPolicy
							.findByRateMetaId(rate.getId());

					AllotmentMarket alloMarket = AllotmentMarket
							.getOneMarket(rate.getId());

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
						// System.out
						// .println("---------------------------------------");
						// System.out.println(alloMarket.getAllotmentMarketId());
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
							// System.out.println(sb.toString());
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

					// System.out.println(rate.getSupplierCode());

					// System.out.println(hAmenities.getSupplier_code());

					list.add(rateVM);
					hProfileVM.setRate(list);

				}

				map.put(rate1.getSupplierCode(), Long.parseLong("1"));
			}
			// System.out.println("---------");
			// System.out.println(list);
			if (!list.isEmpty()) {
				hotellist.add(hProfileVM);
			}
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
*/
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
