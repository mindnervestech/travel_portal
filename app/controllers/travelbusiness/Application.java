package controllers.travelbusiness;

import java.io.File;
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
import java.util.Set;

import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.travelbusiness.home;
import views.html.travelbusiness.searchHotel;
import views.html.travelbusiness.hotelDetails;
import com.fasterxml.jackson.databind.JsonNode;
import com.mnt.travelbusiness.helper.PageScope;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.InfoWiseImagesPath;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.vm.HotelSearch;
import com.travelportal.vm.HotelSignUpVM;
import com.travelportal.vm.HotelamenitiesVM;
import com.travelportal.vm.RoomAmenitiesVm;
import com.travelportal.vm.SearchHotelValueVM;
import com.travelportal.vm.SearchRateDetailsVM;
import com.travelportal.vm.SearchSpecialRateVM;
import com.travelportal.vm.SerachHotelRoomType;
import com.travelportal.vm.SerachedHotelbyDate;
import com.travelportal.vm.SerachedRoomRateDetail;
import com.travelportal.vm.SerachedRoomType;
import com.travelportal.vm.ServicesVM;
public class Application extends Controller {

    public static Result index() {
    	PageScope.scope("number", 1);
    	return ok(home.render());
    }
    
    
        
    @Transactional(readOnly=true)
	public static Result searchCountries() {
		final List<Country> countries = Country.getCountries(); 
		List<Map> country = new ArrayList<>();
 		for(Country c : countries){
 			Map m = new HashMap<>();
 			m.put("countryCode", c.getCountryCode());
 			m.put("countryName", c.getCountryName());
 			country.add(m);
		}
		return ok(Json.toJson(country));
	}
    
    
    @Transactional(readOnly=true)
	public static Result searchCities(int countryId) {
		final List<City> cities = City.getCities(countryId);
		List<Map> city = new ArrayList<>();
 		for(City c : cities){
 			Map m = new HashMap<>();
 			m.put("id", c.getCityCode());
 			m.put("name", c.getCityName());
			city.add(m);
		}
 		
		return ok(Json.toJson(city));
	}
    
    @Transactional(readOnly=true)
   	public static Result searchNationality() {
   		final List<Country> countries = Country.getCountries(); 
   		List<Map> country = new ArrayList<>();
    		for(Country c : countries){
    			Map m = new HashMap<>();
    			m.put("nationalityCode", c.getCountryCode());
    			m.put("nationality", c.getNationality());
    			country.add(m);
   		}
   		return ok(Json.toJson(country));
   	}
       
    @Transactional(readOnly=true)
	public static Result getStarRatings() {
		List<HotelStarRatings> hotelstarratings = HotelStarRatings.gethotelStarratings();
		return ok(Json.toJson(hotelstarratings));
	}

    
    @Transactional(readOnly = true)
    public static Result searchHotelInfo() {
    	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    	
    	Form<SearchHotelValueVM> HotelForm = Form.form(SearchHotelValueVM.class).bindFromRequest();
    	SearchHotelValueVM searchVM = HotelForm.get();
    		
    		int flag = 0;

    		List<HotelSearch> hotellist = new ArrayList<>();
    		Map<Long, Long> map = new HashMap<Long, Long>();
    		Set<String> mapDate = new HashSet<String>();
    		
    		
    		String[] fromDate = {searchVM.checkIn};
    		String[] toDate = {searchVM.checkOut};
    		String[] sId = {searchVM.id};
    		String[] cityId = {searchVM.city};
    		String[] cId = {searchVM.nationalityCode};

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

    							int ib = 1;
    							List<SerachedRoomRateDetail> list = new ArrayList<>();
    							SerachedRoomType roomtyp = new SerachedRoomType();
    							
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

    								SerachedRoomRateDetail rateVM = new SerachedRoomRateDetail();
    								rateVM.setAdult_occupancy(room
    										.getMaxAdultOccupancy());
    								rateVM.setId(rate.getId());

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
    					sRateDetail.rateDetailsNormal = new ArrayList<SearchRateDetailsVM>();

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
    		
    		//return ok(Json.toJson(hotellist));
    		JsonNode personJson = Json.toJson(hotellist);
    
    	return ok(searchHotel.render(personJson));
    }
    

@Transactional(readOnly=false)
public static Result getHotelImagePath(long supplierCode) {
	
	InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(supplierCode);
	File f = new File(infowiseimagesPath.getGeneralPicture());
    return ok(f);		
	
}


@Transactional(readOnly=true)
public static Result hoteldetailpage() {
	
	
	
	Form<SearchHotelValueVM> HotelForm = Form.form(SearchHotelValueVM.class).bindFromRequest();
	SearchHotelValueVM searchVM = HotelForm.get();
	
	    	
	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	int flag = 0;

	List<HotelSearch> hotellist = new ArrayList<>();
	Map<String, String[]> map1 = request().queryString();
	Map<Long, Long> map = new HashMap<Long, Long>();

	Set<String> mapDate = new HashSet<String>();
	
	
	String[] fromDate = {searchVM.checkIn};
	String[] toDate = {searchVM.checkOut};
	String[] cId = {searchVM.nationalityCode};
    String[] supplierCode = {searchVM.supplierCode};
       
	Date fmDate = null;
	Date tDate = null;

	int j = 0;

	
	List<RateMeta> rateMeta = RateMeta.getdatecheckOneSupp(
			Long.parseLong(supplierCode[0]),
			Integer.parseInt(cId[0])); 

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
			hProfileVM.setHotelAddr(hAmenities.getAddress());
			hProfileVM.setCityCode(hAmenities.getCity().getCityCode());
			
			
			List<ServicesVM> sList = new ArrayList<>();
			
			for (HotelServices hoServices : hAmenities.getServices()){
				ServicesVM sVm=new ServicesVM();
				sVm.setServiceId(hoServices.getServiceId());
				sVm.setServiceName(hoServices.getServiceName());
				sList.add(sVm);
			}
			hProfileVM.setServices(sList);
			hProfileVM.currencyId = hAmenities.getCurrency().getCurrencyCode();
			hProfileVM.currencyName = hAmenities.getCurrency().getCurrencyName();
			String currency = hAmenities.getCurrency().getCurrencyName();
			String[] currencySplit;
			 currencySplit = currency.split(" - ");
			hProfileVM.currencyShort = currencySplit[0];
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

							SerachedRoomRateDetail rateVM = new SerachedRoomRateDetail();
							rateVM.setAdult_occupancy(room
									.getMaxAdultOccupancy());
							rateVM.setId(rate.getId());

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
				}
				Datelist.add(hotelBydateVM);
				c.add(Calendar.DATE, 1);
			}

			map.put(rate1.getSupplierCode(), Long.parseLong("1"));

			if (!hProfileVM.getHotelbyDate().isEmpty()) {
				hotellist.add(hProfileVM);
			}
		}
	}

	List<SerachHotelRoomType> hotelRMlist = new ArrayList<>();
	Map<Long, SerachedRoomRateDetail> mapRM = new HashMap<Long, SerachedRoomRateDetail>();
	int count=0;
	for (HotelSearch hotel : hotellist) {
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
				sRateDetail.rateDetailsNormal = new ArrayList<SearchRateDetailsVM>();

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
		JsonNode onehotelJson = Json.toJson(hotel);
		return ok(hotelDetails.render(onehotelJson));
	}
	return ok(Json.toJson(null));
	
	
	//return ok(hotelDetails.render());
}





/*
@Transactional(readOnly = true)
	public static Result getDatewiseHotelRoom(String checkIn,String checkOut,String nationality,String supplierCode1,String roomCode) {

	
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		int flag = 0;

		List<HotelSearch> hotellist = new ArrayList<>();
		Map<String, String[]> map1 = request().queryString();
		Map<Long, Long> map = new HashMap<Long, Long>();
	
		Set<String> mapDate = new HashSet<String>();
		
		 System.out.println("_+_+_+_+_++_+_+_");
	        System.out.println(checkIn);
	        System.out.println(supplierCode1);
	        System.out.println(checkOut);
	        System.out.println(nationality);
	        System.out.println(roomCode);
		
		String[] fromDate = {checkIn};
		String[] toDate = {checkOut};
		String[] cId = {nationality};
        String[] supplierCode = {supplierCode1};
        String[] roomcode = {roomCode};
        
		Date fmDate = null;
		Date tDate = null;

		int j = 0;

		List<RateMeta> rateMeta = RateMeta.getdatecheckRoom(
				Long.parseLong(supplierCode[0]),Long.parseLong(roomcode[0]),
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

				
				
				hProfileVM.setSupplierCode(hAmenities.getSupplier_code());
				hProfileVM.setHotelNm(hAmenities.getHotelName());
				hProfileVM.setSupplierNm(hAmenities.getSupplierName());

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
				
				
				
				for (int i = 0; i < dayDiff; i++) {

					System.out.println(rate1.getSupplierCode());
					List<HotelRoomTypes> roomType = HotelRoomTypes
							.getHotelRoomDetailsByIds(rate1.getSupplierCode(),Long.parseLong(roomcode[0]));
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
				return ok(Json.toJson(hProfileVM));
			}
		}

				
		return ok(Json.toJson(null));
	}

*/

@Transactional(readOnly = true)
	public static Result getDatewiseHotelRoom(String checkIn,String checkOut,String nationality,String supplierCode1,String roomCode) {

	
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		int flag = 0;

		List<HotelSearch> hotellist = new ArrayList<>();
		Map<String, String[]> map1 = request().queryString();
		Map<Long, Long> map = new HashMap<Long, Long>();
	
		Set<String> mapDate = new HashSet<String>();
		
		 System.out.println("_+_+_+_+_++_+_+_");
	        System.out.println(checkIn);
	        System.out.println(supplierCode1);
	        System.out.println(checkOut);
	        System.out.println(nationality);
	        System.out.println(roomCode);
		
		String[] fromDate = {checkIn};
		String[] toDate = {checkOut};
		String[] cId = {nationality};
        String[] supplierCode = {supplierCode1};
        String[] roomcode = {roomCode};
        
		Date fmDate = null;
		Date tDate = null;

		int j = 0;

		List<RateMeta> rateMeta = RateMeta.getdatecheckRoom(
				Long.parseLong(supplierCode[0]),Long.parseLong(roomcode[0]),
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

				if (hAmenities.getStartRatings() != null) {
					hProfileVM.setStartRating(hAmenities.getStartRatings()
							.getId());
				}
				hProfileVM.setHotelAddr(hAmenities.getAddress());
				hProfileVM.setCityCode(hAmenities.getCity().getCityCode());
				//hProfileVM.setServices(hAmenities.getIntListServices());
				List<ServicesVM> sList = new ArrayList<>();
				for (HotelServices hoServices : hAmenities.getServices()){
					ServicesVM sVm=new ServicesVM();
					sVm.setServiceId(hoServices.getServiceId());
					sVm.setServiceName(hoServices.getServiceName());
					sList.add(sVm);
				}
				hProfileVM.setServices(sList);
				hProfileVM.currencyId = hAmenities.getCurrency().getCurrencyCode();
				hProfileVM.currencyName = hAmenities.getCurrency().getCurrencyName();
				String currency = hAmenities.getCurrency().getCurrencyName();
				String[] currencySplit;
				 currencySplit = currency.split(" - ");
				hProfileVM.currencyShort = currencySplit[0];
				
				hProfileVM.setCheckIn(fromDate[0]);
				hProfileVM.setCheckOut(toDate[0]);
				hProfileVM.setNationality(Integer.parseInt(cId[0]));
				
				for (int i = 0; i < dayDiff; i++) {

					System.out.println(rate1.getSupplierCode());
					List<HotelRoomTypes> roomType = HotelRoomTypes
							.getHotelRoomDetailsByIds(rate1.getSupplierCode(),Long.parseLong(roomcode[0]));
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

								SerachedRoomRateDetail rateVM = new SerachedRoomRateDetail();
								rateVM.setAdult_occupancy(room
										.getMaxAdultOccupancy());
								rateVM.setId(rate.getId());
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
					}
					Datelist.add(hotelBydateVM);
					c.add(Calendar.DATE, 1);
				}

				map.put(rate1.getSupplierCode(), Long.parseLong("1"));

				if (!hProfileVM.getHotelbyDate().isEmpty()) {
					hotellist.add(hProfileVM);
				}
			}
		}

		List<SerachHotelRoomType> hotelRMlist = new ArrayList<>();
		Map<Long, SerachedRoomRateDetail> mapRM = new HashMap<Long, SerachedRoomRateDetail>();
		int count=0;
		for (HotelSearch hotel : hotellist) {
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
					sRateDetail.rateDetailsNormal = new ArrayList<SearchRateDetailsVM>();

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
			return ok(Json.toJson(hotel));
		}
		return ok(Json.toJson(null));
	}



}
