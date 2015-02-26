package controllers.travelbusiness;

import java.io.File;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.travelbusiness.home;
import views.html.travelbusiness.hotelDetails;
import views.html.travelbusiness.searchHotel;

import com.fasterxml.jackson.databind.JsonNode;
import com.mnt.travelbusiness.helper.PageScope;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.HotelAmenities;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.InfoWiseImagesPath;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RateSpecialDays;
import com.travelportal.domain.rooms.RoomAllotedRateWise;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.domain.rooms.Specials;
import com.travelportal.domain.rooms.SpecialsMarket;
import com.travelportal.vm.AgentRegistrationVM;
import com.travelportal.vm.HotelSearch;
import com.travelportal.vm.RoomAmenitiesVm;
import com.travelportal.vm.SearchAllotmentMarketVM;
import com.travelportal.vm.SearchHotelValueVM;
import com.travelportal.vm.SearchRateDetailsVM;
import com.travelportal.vm.SearchSpecialRateVM;
import com.travelportal.vm.SerachHotelRoomType;
import com.travelportal.vm.SerachedHotelbyDate;
import com.travelportal.vm.SerachedRoomRateDetail;
import com.travelportal.vm.SerachedRoomType;
import com.travelportal.vm.ServicesVM;
import com.travelportal.vm.SpecialsMarketVM;
import com.travelportal.vm.SpecialsVM;
public class Application extends Controller {

    public static Result index() {
    	
    	/*Form<SearchHotelValueVM> HotelForm = Form.form(SearchHotelValueVM.class).bindFromRequest();
    	SearchHotelValueVM searchVM = HotelForm.get();
    	
    	System.out.println(searchVM.password);*/
    	
    	PageScope.scope("number", 1);
    	return ok(home.render());
    }
    
    @Transactional(readOnly=false)
    public static Result checkAgentinfo(String loginID,String password,String agentId){
    	
    	AgentRegistration agent = AgentRegistration.findagentinfo(loginID, password, agentId);
    	session().put("agent", agent.getAgentCode());
    	System.out.println("--------8**************8------------");
    	System.out.println(agent);
    	System.out.println("--------8**************8------------");
		if(agent != null) {
			System.out.println("SESSION VALUE   "+session().get("agent"));
			AgentRegistrationVM aVm=new AgentRegistrationVM(agent);
			return ok(Json.toJson(aVm));
		}
		
		return ok(Json.toJson("0"));
    	
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
    public static Result searchAgainHotelInfo() {
    	
    	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    	
    	/*JsonNode json = request().body().asJson();
    	DynamicForm form = DynamicForm.form().bindFromRequest();
    	Json.fromJson(json, SearchHotelValueVM.class);
    	SearchHotelValueVM searchHotelValueVM = Json.fromJson(json, SearchHotelValueVM.class);
    		
    		int flag = 0;
    		
    		
    		Map<String, Object> mapObject = new HashMap<String, Object>();
    		List<HotelSearch> hotellist = new ArrayList<>();
    		Map<Long, Long> map = new HashMap<Long, Long>();
    		Set<String> mapDate = new HashSet<String>();
    		
    		
    		String fromDate = searchHotelValueVM.getCheckIn();
    		String toDate = searchHotelValueVM.getCheckOut();
    		//String[] sId = {searchVM.id};
    		String cityId = searchHotelValueVM.getCity();
    		String nationalityId = searchHotelValueVM.getNationalityCode();*/
    	
    	Form<SearchHotelValueVM> HotelForm = Form.form(SearchHotelValueVM.class).bindFromRequest();
    	SearchHotelValueVM searchVM = HotelForm.get();
    		
    		Map<String, Object> mapObject = new HashMap<String, Object>();
    		List<HotelSearch> hotellist = new ArrayList<>();
    		Map<Long, Long> map = new HashMap<Long, Long>();
    		
    		String fromDate = searchVM.checkIn;
    		String toDate = searchVM.checkOut;
    		//String[] sId = {searchVM.id};
    		String cityId = searchVM.city;
    		String nationalityId = searchVM.nationalityCode;
    	
    		Date formDate = null;
			Date toDates = null;
			try {
				formDate = format.parse(fromDate);
				toDates = format.parse(toDate);
			} catch (ParseException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			long diffInpromo = 0;
			long dayDiff = 0;
			
			dayDiff = findDateDiff(toDates,formDate,dayDiff);
			diffInpromo = dayDiff;
			
			DateWiseSortFunction(hotellist,toDate,fromDate,dayDiff,cityId,nationalityId,map,format,formDate);
    		
    	 		List<Long> hotelNo = new ArrayList<>();
    		List<SerachHotelRoomType> hotelRMlist = new ArrayList<>();
    		fillRoomsInHotelInfo(hotellist,hotelRMlist,hotelNo,diffInpromo); /* fill data in room in hotel object.... function*/
    		
    		List<Long> hotelCount = new ArrayList<>();
    		int totalHotel = 0;
    		for(Long no:hotelNo){
    			totalHotel++;
    			HotelProfile hProfile = HotelProfile.findAllData(no);
    			hotelCount.add(hProfile.getId());
    			
    		}
    		
    		List<Map> hotelAmenities = null;
    		List<Map> hotelLocation = null;
    		List<Map> hotelServices =null;
    		if(!hotelCount.isEmpty()){
    		 hotelAmenities = HotelAmenities.getamenitiesCount(hotelCount);
    		 hotelLocation = HotelProfile.getlocationCount(hotelCount);
    		 hotelServices =HotelServices.getservicesCount(hotelCount);   
    		}
    		
    		findMinRateInHotel(hotellist);  /* find min Rate in par Hotel function*/
		
    		
    		if(!hotelCount.isEmpty()){
    			mapObject.put("hotelAmenities", hotelAmenities);
    			mapObject.put("hotelServices", hotelServices);
    			mapObject.put("hotellocation",hotelLocation);
    		}
    		mapObject.put("hotelId", hotelNo);
    		mapObject.put("hotellist", hotellist);
    		mapObject.put("totalHotel",totalHotel);
    		
    		JsonNode personJson = Json.toJson(mapObject);
    
    	//return ok(Json.toJson(mapObject));
    		return ok(searchHotel.render(personJson));
    }
    
    
    
    @Transactional(readOnly = true)
    public static Result searchHotelInfo() {
    	
    	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    	
    	Form<SearchHotelValueVM> HotelForm = Form.form(SearchHotelValueVM.class).bindFromRequest();
    	SearchHotelValueVM searchVM = HotelForm.get();
    		
    		Map<String, Object> mapObject = new HashMap<String, Object>();
    		List<HotelSearch> hotellist = new ArrayList<>();
    		Map<Long, Long> map = new HashMap<Long, Long>();
    		
    		String fromDate = searchVM.checkIn;
    		String toDate = searchVM.checkOut;
    		String cityId = searchVM.city;
    		String nationalityId = searchVM.nationalityCode;
    		
    		Date formDate = null;
			Date toDates = null;
			try {
				formDate = format.parse(fromDate);
				toDates = format.parse(toDate);
			} catch (ParseException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			long diffInpromo = 0;
			long dayDiff = 0;
			
			dayDiff = findDateDiff(toDates,formDate,dayDiff);
			diffInpromo = dayDiff;
			    		
			DateWiseSortFunction(hotellist,toDate,fromDate,dayDiff,cityId,nationalityId,map,format,formDate);

    		List<Long> hotelNo = new ArrayList<>();
    		List<SerachHotelRoomType> hotelRMlist = new ArrayList<>();
    		
    		fillRoomsInHotelInfo(hotellist,hotelRMlist,hotelNo,diffInpromo); /* fill data in room in hotel object.... function*/
    		
    		List<Long> hotelCount = new ArrayList<>();
    		int totalHotel = 0;
    		for(Long no:hotelNo){
    			totalHotel++;
    			HotelProfile hProfile = HotelProfile.findAllData(no);
    			hotelCount.add(hProfile.getId());
    			
    		}
    		List<Map> hotelAmenities = null;
    		List<Map> hotelLocation = null;
    		List<Map> hotelServices =null;
    		if(!hotelCount.isEmpty()){
    		hotelAmenities = HotelAmenities.getamenitiesCount(hotelCount);
    		hotelLocation = HotelProfile.getlocationCount(hotelCount);
    		hotelServices =HotelServices.getservicesCount(hotelCount);
    		}
    		
    		findMinRateInHotel(hotellist);  /* find min Rate in par Hotel function*/
		
    		if(!hotelCount.isEmpty()){
    			mapObject.put("hotelAmenities", hotelAmenities);
    			mapObject.put("hotelServices", hotelServices);
    			mapObject.put("hotellocation",hotelLocation);
    		}
    		mapObject.put("hotelId", hotelNo);
    		mapObject.put("hotellist", hotellist);
    		mapObject.put("totalHotel",totalHotel);
    		
    		JsonNode personJson = Json.toJson(mapObject);
    
    	return ok(searchHotel.render(personJson));
    }
    

@Transactional(readOnly=false)
public static Result getHotelImagePath(long supplierCode) {
	
	InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(supplierCode);
	File f = new File(infowiseimagesPath.getGeneralPicture());
    return ok(f);		
	
}


public static long findDateDiff(Date toDates, Date formDate, long dayDiff){
	if(toDates.getTime() == formDate.getTime()){
	return dayDiff = 1;
	}else{
		long diff = toDates.getTime() - formDate.getTime();

		return dayDiff = diff / (1000 * 60 * 60 * 24);
	}
}

public static void DateWiseSortFunction(List<HotelSearch> hotellist,String toDate,String fromDate,long dayDiff,String cityId,String nationalityId,Map<Long, Long> map,DateFormat format,Date formDate){
    
	List<BigInteger> supplierId = RateMeta.getsupplierId(
			Integer.parseInt(cityId), 
			Integer.parseInt(nationalityId));  

	for (BigInteger supplierid : supplierId) {

		Calendar checkInDate = Calendar.getInstance();
		checkInDate.setTime(formDate);
		checkInDate.set(Calendar.MILLISECOND, 0);

		List<SerachedHotelbyDate> Datelist = new ArrayList<>();
		HotelSearch hProfileVM = new HotelSearch();
		
		Long object = map.get(supplierid.longValue());
		if (object == null) {

			HotelProfile hAmenities = HotelProfile.findAllData(supplierid.longValue());
			fillHotelInfo(hAmenities,hProfileVM,fromDate,toDate,nationalityId,dayDiff);   /* Fill Hotel info function*/
			
			for (int i = 0; i < dayDiff; i++) {

				List<HotelRoomTypes> roomType = HotelRoomTypes
						.getHotelRoomDetails(supplierid.longValue());
				int days = checkInDate.get(Calendar.DAY_OF_WEEK)-1;
				SerachedHotelbyDate hotelBydateVM = new SerachedHotelbyDate();
				hotelBydateVM.setDate(format.format(checkInDate.getTime()));
				Map<Long, Long> mapRm = new HashMap<Long, Long>();
				List<SerachedRoomType> roomlist = new ArrayList<>();
				
				for (HotelRoomTypes room : roomType) {
					Long objectRm = (Long) mapRm.get(room.getRoomId());
					if (objectRm == null) {

						
						List<RateMeta> rateMeta1 = RateMeta.getdatecheck(
								room.getRoomId(),
								Integer.parseInt(nationalityId), checkInDate.getTime(),
								hAmenities.getSupplier_code()); 

						List<SerachedRoomRateDetail> list = new ArrayList<>();
						SerachedRoomType roomtyp = new SerachedRoomType();
						
						fillRoomInfo(room,roomtyp);  /*fill room info function*/
						
						specialsPromotion(roomtyp,format,Integer.parseInt(nationalityId),room.getRoomId(),checkInDate.getTime());
						
						for (RateMeta rate : rateMeta1) {
								
							RateDetails rateDetails = RateDetails
									.findByRateMetaId(rate.getId());
							
							List<RateSpecialDays> reDays = RateSpecialDays.findByRateMetaId(rate.getId());
							
							List<PersonRate> personRate = PersonRate
									.findByRateMetaId(rate.getId());

							AllotmentMarket alloMarket = AllotmentMarket.getOneMarket(rate.getId());

							SerachedRoomRateDetail rateVM = new SerachedRoomRateDetail();
							rateVM.setAdult_occupancy(room
									.getMaxAdultOccupancy());
							rateVM.setId(rate.getId());
							
							allotmentmarketInfo(alloMarket,rateVM,format,nationalityId,rate.getId(),checkInDate.getTime());
							
							
							SearchSpecialRateVM specialRateVM = new SearchSpecialRateVM();
							
							SpecialRateReturn(specialRateVM,rateDetails);/* Special Rate Return function*/
							
							personRatereturn(personRate,specialRateVM,rateDetails,days,rateVM, reDays, format, checkInDate); /* person Rate return function*/
							
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

				checkInDate.add(Calendar.DATE, 1);
			}

			map.put(supplierid.longValue(), Long.parseLong("1"));

			if (!hProfileVM.getHotelbyDate().isEmpty()) {
				hotellist.add(hProfileVM);
			}
		}
	}
	
}

public static void specialsPromotion(SerachedRoomType roomtyp, DateFormat format, int nationalityId, Long roomId, Date checkInD) {
	int count = 0;
	List<SpecialsVM> listsp = new ArrayList<>();
	List<Specials> specialsList = Specials.findSpecialByDateandroom(checkInD,roomId);
	for(Specials special : specialsList) {
		SpecialsVM specialsVM = new SpecialsVM();
		specialsVM.id = special.getId();
		specialsVM.fromDate = format.format(special.getFromDate());
		specialsVM.toDate = format.format(special.getToDate());
		specialsVM.promotionName = special.getPromotionName();
		specialsVM.supplierCode = special.getSupplierCode();
		    								
	List<SpecialsMarket> marketList = SpecialsMarket.findBySpecialsIdnationality(special.getId(),nationalityId);
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
}

public static void fillRoomsInHotelInfo(List<HotelSearch> hotellist,List<SerachHotelRoomType> hotelRMlist,List<Long> hotelNo,Long diffInpromo){
	
	Map<Long, List<SpecialsVM>> mapSpecials = new HashMap<Long, List<SpecialsVM>>();
	Map<Long, Integer> promoMap = new HashMap<Long, Integer>();
	Map<Long, SerachedRoomRateDetail> mapRM = new HashMap<Long, SerachedRoomRateDetail>();
	int count=0;
	
	for (HotelSearch hotel : hotellist) {
		hotelNo.add(hotel.supplierCode);
		int dataVar = 0;
		int countRoom = count;
		List<Integer> arrayCount = new ArrayList<Integer>();
		for (SerachedHotelbyDate date : hotel.hotelbyDate) {
			int newHotel=countRoom; 
			dataVar++;
			int aCount = 0;
			for (SerachedRoomType roomTP : date.getRoomType()) {
				
				Double total = 0.0;
				//Double avg = 0.0;
				SerachHotelRoomType sHotelRoomType = new SerachHotelRoomType();
				sHotelRoomType.hotelRoomRateDetail = new ArrayList<SerachedRoomRateDetail>();
				
				SerachedRoomRateDetail sRateDetail = new SerachedRoomRateDetail();
				sRateDetail.rateDetailsNormal = new ArrayList<SearchRateDetailsVM>();

				//List<SerachedRoomRateDetail> serachedRoomRateDetails = new ArrayList<>();
				List<SearchRateDetailsVM> searchRateDetailsVMs = new ArrayList<>();
				SerachedRoomRateDetail objectRM = mapRM.get(roomTP.getRoomId());
				if (objectRM == null) {
					
					count++;
					
					sHotelRoomType.setRoomId(roomTP.getRoomId());
					sHotelRoomType.setRoomName(roomTP.getRoomName());
					sHotelRoomType.setDescription(roomTP.getDescription());
					sHotelRoomType.setChildAllowedFreeWithAdults(roomTP.getChildAllowedFreeWithAdults());
					sHotelRoomType.setExtraBedAllowed(roomTP.getExtraBedAllowed());
					sHotelRoomType.setRoomSuiteType(roomTP.getRoomSuiteType());
					arrayCount.add(aCount, roomTP.getPcount());
					//sHotelRoomType.setSpecials(roomTP.getSpecials());
					
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
				if(entry.getValue() == 0 && diffProm == 0){
					room.setApplyPromotion(0);
				}else if(entry.getValue() >= diffProm){
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
	
}

public static void allotmentmarketInfo(AllotmentMarket alloMarket,SerachedRoomRateDetail rateVM,DateFormat format,String nationalityId,Long rateid,Date CurrDate){
	
	int flag=0;
	int aRoom=0;
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
			flag = 1;
		}
		if(alloMarket.getAllocation() == 3){
			RoomAllotedRateWise rAllotedRateWise= RoomAllotedRateWise.findByRateIdandDate(rateid, CurrDate);
			System.out.println("++++++++++++++++++++++");
			if(rAllotedRateWise != null){
				
				aRoom = alloMarket.getChoose() - rAllotedRateWise.getRoomCount();
				if(aRoom < 1){
					flag = 1;
				}
				
		   }else{
			   aRoom = alloMarket.getChoose();
		   }
		}
		
	}
	rateVM.setFlag(flag);
	//if(flag != 1){
		rateVM.setAvailableRoom(aRoom);
	//}
	rateVM.setAllotmentmarket(Allvm);
}

public static void fillRoomInfo(HotelRoomTypes room,SerachedRoomType roomtyp){
	roomtyp.setRoomId(room.getRoomId());
	roomtyp.setRoomName(room.getRoomType());
	roomtyp.setDescription(room.getDescription());
	roomtyp.setChildAllowedFreeWithAdults(room.getChildAllowedFreeWithAdults());
	roomtyp.setExtraBedAllowed(room.getExtraBedAllowed());
	roomtyp.setRoomSuiteType(room.getRoomSuiteType());
	
	List<RoomAmenitiesVm> rList = new ArrayList<>();
	
	for (RoomAmenities roomAmenitiesVm : room.getAmenities()){
		RoomAmenitiesVm rooAmenitiesVm = new RoomAmenitiesVm();
		rooAmenitiesVm.setAmenityId(roomAmenitiesVm.getAmenityId());
		rooAmenitiesVm.setAmenityNm(roomAmenitiesVm.getAmenityNm());
		rooAmenitiesVm.setAmenitiesicon(roomAmenitiesVm.getAmenitiesicon());
		rList.add(rooAmenitiesVm);
	}
	
	
		roomtyp.setAmenities(rList);
}

public static void fillHotelInfo(HotelProfile hAmenities,HotelSearch hProfileVM,String fDate,String tDate,String nationality,long dayDiff){
	
	hProfileVM.setSupplierCode(hAmenities.getSupplier_code());
	hProfileVM.setHotelNm(hAmenities.getHotelName());
	hProfileVM.setSupplierNm(hAmenities.getSupplierName());

	if (hAmenities.getStartRatings() != null) {
		hProfileVM.setStartRating(hAmenities.getStartRatings()
				.getId());
		hProfileVM.setStars(hAmenities.getStartRatings().getStarRating());
	}
	hProfileVM.currencyId = hAmenities.getCurrency().getCurrencyCode();
	hProfileVM.currencyName = hAmenities.getCurrency().getCurrencyName();
	String currency = hAmenities.getCurrency().getCurrencyName();
	String[] currencySplit;
	 currencySplit = currency.split(" - ");
	hProfileVM.currencyShort = currencySplit[0];
	hProfileVM.setHotelAddr(hAmenities.getAddress());
	hProfileVM.setCityCode(hAmenities.getCity().getCityCode());
	hProfileVM.setHoteldescription(hAmenities.getHotelProfileDesc());
	hProfileVM.setCheckIn(fDate);
	hProfileVM.setCheckOut(tDate);
	hProfileVM.setDatediff(dayDiff);
	List<ServicesVM> sList = new ArrayList<>();
	
	for (HotelServices hoServices : hAmenities.getServices()){
		ServicesVM sVm=new ServicesVM();
		sVm.setServiceId(hoServices.getServiceId());
		sVm.setServiceName(hoServices.getServiceName());
		sList.add(sVm);
	}
	hProfileVM.setServices(sList);
	hProfileVM.setNationality(Integer.parseInt(nationality));
	InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(hAmenities.getSupplier_code());
	hProfileVM.setImgDescription(infowiseimagesPath.getGeneralDescription());
	
	//hProfileVM.setFlag("0");
}

public static void SpecialRateReturn(SearchSpecialRateVM specialRateVM,RateDetails rateDetails){
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
}

public static void personRatereturn(List<PersonRate> personRate,SearchSpecialRateVM specialRateVM,RateDetails rateDetails,int days,SerachedRoomRateDetail rateVM, List<RateSpecialDays> reDays, DateFormat format,Calendar checkInDate){
	int findrate = 0;
	for (PersonRate person : personRate) {
		
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
    					
						if(checkInDate.getTime().equals(specialfromDate.getTime())){
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
			}
	}
	}
}
public static void findMinRateInHotel(List<HotelSearch> hotellist){
	for (HotelSearch hotel : hotellist) {
		
		

	double min=0.0;
	int minStart = 0;
	for(SerachHotelRoomType sHotelRoomType2:hotel.hotelbyRoom){
		
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
}


@Transactional(readOnly=true)
public static Result findHotelByData() {
	
DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	
	JsonNode json = request().body().asJson();
	DynamicForm form = DynamicForm.form().bindFromRequest();
	Json.fromJson(json, SearchHotelValueVM.class);
	SearchHotelValueVM searchHotelValueVM = Json.fromJson(json, SearchHotelValueVM.class);

	
		
		Map<String, Object> mapObject = new HashMap<String, Object>();
		List<HotelSearch> hotellist = new ArrayList<>();
		Map<Long, Long> map = new HashMap<Long, Long>();
		Set<String> mapDate = new HashSet<String>();
		
		String fromDate = searchHotelValueVM.getCheckIn();
		String toDate = searchHotelValueVM.getCheckOut();
		String cityId = searchHotelValueVM.getCity();
		String nationalityId = searchHotelValueVM.getNationalityCode();
		
		Date formDate = null;
		Date toDates = null;
		try {
			formDate = format.parse(fromDate);
			toDates = format.parse(toDate);
		} catch (ParseException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		long diffInpromo = 0;
		long dayDiff = 0;
		
		dayDiff = findDateDiff(toDates,formDate,dayDiff);
		diffInpromo = dayDiff;
			
		List<BigInteger> supplierId = RateMeta.getsupplierId(
				Integer.parseInt(cityId),
				Integer.parseInt(nationalityId));

		for (BigInteger supplierid : supplierId) {


			Calendar checkInDate = Calendar.getInstance();
			checkInDate.setTime(formDate);
			checkInDate.set(Calendar.MILLISECOND, 0);
			
			List<SerachedHotelbyDate> Datelist = new ArrayList<>();
			HotelSearch hProfileVM = new HotelSearch();
			Long object = (Long) map.get(supplierid.longValue());
			
			HotelProfile hProfile = HotelProfile.findAllData(supplierid.longValue());
			
			
			if (object == null) {
				
				List<HotelProfile> hAmenities1 = HotelProfile.findAllDataforamenities(hProfile.getId()
						,searchHotelValueVM.getAmenitiesCheck(),searchHotelValueVM.getServicesCheck(),searchHotelValueVM.getLocationCheck());  
				

				for(HotelProfile hAmenities:hAmenities1){
					fillHotelInfo(hAmenities,hProfileVM,fromDate,toDate,nationalityId,dayDiff);   /* Fill Hotel info function*/				for (int i = 0; i < dayDiff; i++) {

					List<HotelRoomTypes> roomType = HotelRoomTypes
							.getHotelRoomDetails(supplierid.longValue());
					int days = checkInDate.get(Calendar.DAY_OF_WEEK)-1;
					SerachedHotelbyDate hotelBydateVM = new SerachedHotelbyDate();
					hotelBydateVM.setDate(format.format(checkInDate.getTime()));
					Map<Long, Long> mapRm = new HashMap<Long, Long>();
					Map<Long, Long> mapRate = new HashMap<Long, Long>();
					List<SerachedRoomType> roomlist = new ArrayList<>();
					for (HotelRoomTypes room : roomType) {
						Long objectRm = (Long) mapRm.get(room.getRoomId());
						if (objectRm == null) {

							List<RateMeta> rateMeta1 = RateMeta.getdatecheck(
									room.getRoomId(),
									//Integer.parseInt(sId[0]),
									Integer.parseInt(nationalityId), checkInDate.getTime(),
									hAmenities.getSupplier_code()); // Long.parseLong(roomId[0])

							int ib = 1;
							List<SerachedRoomRateDetail> list = new ArrayList<>();
							SerachedRoomType roomtyp = new SerachedRoomType();
							fillRoomInfo(room,roomtyp);  /*fill room info function*/
							specialsPromotion(roomtyp,format,Integer.parseInt(nationalityId),room.getRoomId(),checkInDate.getTime());
							
							for (RateMeta rate : rateMeta1) {
								
								RateDetails rateDetails = RateDetails
										.findByRateMetaId(rate.getId());

								List<RateSpecialDays> reDays = RateSpecialDays.findByRateMetaId(rate.getId());
								
								List<PersonRate> personRate = PersonRate
										.findByRateMetaId(rate.getId());

								AllotmentMarket alloMarket = AllotmentMarket
										.getOneMarket(rate.getId());

								SerachedRoomRateDetail rateVM = new SerachedRoomRateDetail();
								rateVM.setAdult_occupancy(room
										.getMaxAdultOccupancy());
								rateVM.setId(rate.getId());
								allotmentmarketInfo(alloMarket,rateVM,format,nationalityId,rate.getId(),checkInDate.getTime());
								SearchSpecialRateVM specialRateVM = new SearchSpecialRateVM();
								
								SpecialRateReturn(specialRateVM,rateDetails);/* Special Rate Return function*/
								
								personRatereturn(personRate,specialRateVM,rateDetails,days,rateVM, reDays, format, checkInDate); /* person Rate return function*/
								
							
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
					//	hProfileVM.setFlag("1");
					}

					checkInDate.add(Calendar.DATE, 1);
				}

				map.put(supplierid.longValue(), Long.parseLong("1"));

				if (!hProfileVM.getHotelbyDate().isEmpty()) {
					hotellist.add(hProfileVM);
				}
			}
		}
		}

		List<Long> hotelNo = new ArrayList<>();
		List<SerachHotelRoomType> hotelRMlist = new ArrayList<>();
		fillRoomsInHotelInfo(hotellist,hotelRMlist,hotelNo,diffInpromo); /* fill data in room in hotel object.... function*/
		
		List<Long> hotelCount = new ArrayList<>();
		int totalHotel = 0;
		for(Long no:hotelNo){
			totalHotel++;
			HotelProfile hProfile = HotelProfile.findAllData(no);
			hotelCount.add(hProfile.getId());
			
		}
		
		findMinRateInHotel(hotellist);  /* find min Rate in par Hotel function*/
	
		if(searchHotelValueVM.getSortData().equals("1")){
			Collections.sort(hotellist,new HotelComparatorByRateAsc());
		}else{
			Collections.sort(hotellist,new HotelComparatorByRateDes());
		}
		
		if(searchHotelValueVM.getSortByRating().equals("1")){
			Collections.sort(hotellist,new HotelComparatorByRatingAsc());
		}else{
			Collections.sort(hotellist,new HotelComparatorByRatingDes());
		}
		
	
		mapObject.put("hotellist", hotellist);
		mapObject.put("totalHotel",totalHotel);
		
	
	return ok(Json.toJson(mapObject));
	
}


public static class HotelComparatorByRateAsc implements Comparator<HotelSearch> {

	@Override
	public int compare(HotelSearch arg0, HotelSearch arg1) {
		return arg0.minRate.doubleValue() > arg1.minRate.doubleValue() ? -1 : arg0.minRate.doubleValue() < arg1.minRate.doubleValue()?1:0;
	}
}
public static class HotelComparatorByRateDes implements Comparator<HotelSearch> {

	@Override
	public int compare(HotelSearch arg0, HotelSearch arg1) {
		return arg0.minRate.doubleValue() < arg1.minRate.doubleValue() ? -1 : arg0.minRate.doubleValue() > arg1.minRate.doubleValue()?1:0;
	}
}
public static class HotelComparatorByRatingAsc implements Comparator<HotelSearch> {

	@Override
	public int compare(HotelSearch arg0, HotelSearch arg1) {
		return arg0.startRating > arg1.startRating ? -1 : arg0.startRating < arg1.startRating?1:0;
	}
}
public static class HotelComparatorByRatingDes implements Comparator<HotelSearch> {

	@Override
	public int compare(HotelSearch arg0, HotelSearch arg1) {
		return arg0.startRating < arg1.startRating ? -1 : arg0.startRating > arg1.startRating?1:0;
	}
}

@Transactional(readOnly=true)
public static Result hoteldetailpage() {
	
	Form<SearchHotelValueVM> HotelForm = Form.form(SearchHotelValueVM.class).bindFromRequest();
	SearchHotelValueVM searchVM = HotelForm.get();
	    	
	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

	List<HotelSearch> hotellist = new ArrayList<>();
	Map<String, String[]> map1 = request().queryString();
	Map<Long, Long> map = new HashMap<Long, Long>();
	
	String fromDate = searchVM.checkIn;
	String toDate = searchVM.checkOut;
	String nationalityId = searchVM.nationalityCode;
    String supplierCode = searchVM.supplierCode;
       
    Date formDate = null;
	Date toDates = null;
	try {
		formDate = format.parse(fromDate);
		toDates = format.parse(toDate);
	} catch (ParseException e) { // TODO Auto-generated catch block
		e.printStackTrace();
	}

	long diffInpromo = 0;
	long dayDiff = 0;
	
	dayDiff = findDateDiff(toDates,formDate,dayDiff);
	diffInpromo = dayDiff;
	
	List<BigInteger> supplierId = RateMeta.getOneSupplierId(
			Long.parseLong(supplierCode),
			Integer.parseInt(nationalityId)); 

	for (BigInteger supplierid : supplierId) {

		Calendar checkInDate = Calendar.getInstance();
		checkInDate.setTime(formDate);
		checkInDate.set(Calendar.MILLISECOND, 0);

		List<SerachedHotelbyDate> Datelist = new ArrayList<>();
		HotelSearch hProfileVM = new HotelSearch();
		Long object = (Long) map.get(supplierid.longValue());
		
		if (object == null) {
			HotelProfile hAmenities = HotelProfile.findAllData(supplierid.longValue());
			
			fillHotelInfo(hAmenities,hProfileVM,fromDate,toDate,nationalityId,dayDiff);
			
			for (int i = 0; i < dayDiff; i++) {

				System.out.println(supplierid.longValue());
				List<HotelRoomTypes> roomType = HotelRoomTypes
						.getHotelRoomDetails(supplierid.longValue());
				int days = checkInDate.get(Calendar.DAY_OF_WEEK)-1;
				SerachedHotelbyDate hotelBydateVM = new SerachedHotelbyDate();
				hotelBydateVM.setFlag("0");
				hotelBydateVM.setDate(format.format(checkInDate.getTime()));
				Map<Long, Long> mapRm = new HashMap<Long, Long>();
				Map<Long, Long> mapRate = new HashMap<Long, Long>();
				List<SerachedRoomType> roomlist = new ArrayList<>();
				for (HotelRoomTypes room : roomType) {
					Long objectRm = (Long) mapRm.get(room.getRoomId());
					if (objectRm == null) {

						List<RateMeta> rateMeta1 = RateMeta.getdatecheck(
								room.getRoomId(),
								Integer.parseInt(nationalityId), checkInDate.getTime(),
								hAmenities.getSupplier_code()); // Long.parseLong(roomId[0])

						int ib = 1;
						List<SerachedRoomRateDetail> list = new ArrayList<>();
						SerachedRoomType roomtyp = new SerachedRoomType();
						fillRoomInfo(room,roomtyp);  /*fill room info function*/
						specialsPromotion(roomtyp,format,Integer.parseInt(nationalityId),room.getRoomId(),checkInDate.getTime());
						
						for (RateMeta rate : rateMeta1) {
							
							
							RateDetails rateDetails = RateDetails
									.findByRateMetaId(rate.getId());

							List<RateSpecialDays> reDays = RateSpecialDays.findByRateMetaId(rate.getId());
							
							List<PersonRate> personRate = PersonRate
									.findByRateMetaId(rate.getId());

							AllotmentMarket alloMarket = AllotmentMarket
									.getOneMarket(rate.getId());

							SerachedRoomRateDetail rateVM = new SerachedRoomRateDetail();
							rateVM.setAdult_occupancy(room
									.getMaxAdultOccupancy());
							rateVM.setId(rate.getId());
							allotmentmarketInfo(alloMarket,rateVM,format,nationalityId,rate.getId(),checkInDate.getTime());
							SearchSpecialRateVM specialRateVM = new SearchSpecialRateVM();
							
							SpecialRateReturn(specialRateVM,rateDetails);/* Special Rate Return function*/
							
							personRatereturn(personRate,specialRateVM,rateDetails,days,rateVM, reDays, format, checkInDate); /* person Rate return function*/

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
				checkInDate.add(Calendar.DATE, 1);
			}

			map.put(supplierid.longValue(), Long.parseLong("1"));

			if (!hProfileVM.getHotelbyDate().isEmpty()) {
				hotellist.add(hProfileVM);
			}
		}
	}

	List<SerachHotelRoomType> hotelRMlist = new ArrayList<>();
	Map<Long, SerachedRoomRateDetail> mapRM = new HashMap<Long, SerachedRoomRateDetail>();
	int count=0;
	for (HotelSearch hotel : hotellist) {
		fillRoomsInHotelInfo1(hotel, hotelRMlist, count, mapRM ,diffInpromo); /* fill data in room in hotel object.... function*/
				
		findMinRateInHotel(hotellist);
				
		JsonNode onehotelJson = Json.toJson(hotel);
		return ok(hotelDetails.render(onehotelJson));
	}
	return ok(Json.toJson(null));
	
}


@Transactional(readOnly = true)
	public static Result getDatewiseHotelRoom(String checkIn,String checkOut,String nationality,String supplierCode1,String roomCode) {
	
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		List<HotelSearch> hotellist = new ArrayList<>();
		Map<String, String[]> map1 = request().queryString();
		Map<Long, Long> map = new HashMap<Long, Long>();
		
		String fromDate = checkIn;
		String toDate = checkOut;
		String nationalityId = nationality;
        String supplierCode = supplierCode1;
        String roomcode = roomCode;
        
        Date formDate = null;
		Date toDates = null;
		try {
			formDate = format.parse(fromDate);
			toDates = format.parse(toDate);
		} catch (ParseException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		long diffInpromo = 0;
		long dayDiff = 0;
		
		dayDiff = findDateDiff(toDates,formDate,dayDiff);
		diffInpromo = dayDiff;
		
		List<BigInteger> supplierId = RateMeta.getsupplierIdwiseRoom(
				Long.parseLong(supplierCode),Long.parseLong(roomcode),
				Integer.parseInt(nationalityId)); // Long.parseLong(roomId[0]),

		for (BigInteger supplierid : supplierId) {

			Calendar checkInDate = Calendar.getInstance();
			checkInDate.setTime(formDate);
			checkInDate.set(Calendar.MILLISECOND, 0);
			
			List<SerachedHotelbyDate> Datelist = new ArrayList<>();
			HotelSearch hProfileVM = new HotelSearch();
			Long object = (Long) map.get(supplierid.longValue());
			
			if (object == null) {
				HotelProfile hAmenities = HotelProfile.findAllData(supplierid.longValue());	
				
				fillHotelInfo(hAmenities,hProfileVM,fromDate,toDate,nationalityId,dayDiff);
				
				for (int i = 0; i < dayDiff; i++) {

					System.out.println(supplierid.longValue());
					List<HotelRoomTypes> roomType = HotelRoomTypes
							.getHotelRoomDetailsByIds(supplierid.longValue(),Long.parseLong(roomcode));
					int days = checkInDate.get(Calendar.DAY_OF_WEEK)-1;
					SerachedHotelbyDate hotelBydateVM = new SerachedHotelbyDate();
					hotelBydateVM.setFlag("0");
					hotelBydateVM.setDate(format.format(checkInDate.getTime()));
					Map<Long, Long> mapRm = new HashMap<Long, Long>();
					Map<Long, Long> mapRate = new HashMap<Long, Long>();
					List<SerachedRoomType> roomlist = new ArrayList<>();
					for (HotelRoomTypes room : roomType) {
						Long objectRm = (Long) mapRm.get(room.getRoomId());
						if (objectRm == null) {

							List<RateMeta> rateMeta1 = RateMeta.getdatecheck(
									room.getRoomId(),
									Integer.parseInt(nationalityId), checkInDate.getTime(),
									hAmenities.getSupplier_code()); // Long.parseLong(roomId[0])

							int ib = 1;
							List<SerachedRoomRateDetail> list = new ArrayList<>();
							SerachedRoomType roomtyp = new SerachedRoomType();
							fillRoomInfo(room,roomtyp);  /*fill room info function*/
							specialsPromotion(roomtyp,format,Integer.parseInt(nationalityId),room.getRoomId(),checkInDate.getTime());
							
							for (RateMeta rate : rateMeta1) {
								
								RateDetails rateDetails = RateDetails
										.findByRateMetaId(rate.getId());
								
								List<RateSpecialDays> reDays = RateSpecialDays.findByRateMetaId(rate.getId());

								List<PersonRate> personRate = PersonRate
										.findByRateMetaId(rate.getId());

								AllotmentMarket alloMarket = AllotmentMarket
										.getOneMarket(rate.getId());

								SerachedRoomRateDetail rateVM = new SerachedRoomRateDetail();
								rateVM.setAdult_occupancy(room
										.getMaxAdultOccupancy());
								rateVM.setId(rate.getId());
								allotmentmarketInfo(alloMarket,rateVM,format,nationalityId,rate.getId(),checkInDate.getTime());
								
								SearchSpecialRateVM specialRateVM = new SearchSpecialRateVM();
								
								SpecialRateReturn(specialRateVM,rateDetails);/* Special Rate Return function*/
								
								personRatereturn(personRate,specialRateVM,rateDetails,days,rateVM, reDays, format, checkInDate); /* person Rate return function*/
								
								
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
					checkInDate.add(Calendar.DATE, 1);
				}

				map.put(supplierid.longValue(), Long.parseLong("1"));

				if (!hProfileVM.getHotelbyDate().isEmpty()) {
					hotellist.add(hProfileVM);
				}
			}
		}

		List<SerachHotelRoomType> hotelRMlist = new ArrayList<>();
		
		Map<Long, SerachedRoomRateDetail> mapRM = new HashMap<Long, SerachedRoomRateDetail>();
		int count=0;
		for (HotelSearch hotel : hotellist) {
			
			fillRoomsInHotelInfo1(hotel, hotelRMlist, count, mapRM , diffInpromo); /* fill data in room in hotel object.... function*/
		
			return ok(Json.toJson(hotel));
		}
		return ok(Json.toJson(null));
	}


public static void fillRoomsInHotelInfo1(HotelSearch hotel, List<SerachHotelRoomType> hotelRMlist,int count, Map<Long, SerachedRoomRateDetail> mapRM,Long diffInpromo){
	
	Map<Long, List<SpecialsVM>> mapSpecials = new HashMap<Long, List<SpecialsVM>>();
	Map<Long, Integer> promoMap = new HashMap<Long, Integer>();
		int dataVar = 0;
		int countRoom = count;
		List<Integer> arrayCount = new ArrayList<Integer>();
		for (SerachedHotelbyDate date : hotel.hotelbyDate) {
			int newHotel=countRoom; 
			dataVar++;
			int aCount = 0;
			for (SerachedRoomType roomTP : date.getRoomType()) {
				
				Double total = 0.0;
				//Double avg = 0.0;
				SerachHotelRoomType sHotelRoomType = new SerachHotelRoomType();
				sHotelRoomType.hotelRoomRateDetail = new ArrayList<SerachedRoomRateDetail>();
				
				SerachedRoomRateDetail sRateDetail = new SerachedRoomRateDetail();
				sRateDetail.rateDetailsNormal = new ArrayList<SearchRateDetailsVM>();

				//List<SerachedRoomRateDetail> serachedRoomRateDetails = new ArrayList<>();
				List<SearchRateDetailsVM> searchRateDetailsVMs = new ArrayList<>();
				SerachedRoomRateDetail objectRM = mapRM.get(roomTP
						.getRoomId());
				if (objectRM == null) {
					
					count++;
					
					sHotelRoomType.setRoomId(roomTP.getRoomId());
					sHotelRoomType.setRoomName(roomTP.getRoomName());
					sHotelRoomType.setDescription(roomTP.getDescription());
					sHotelRoomType.setChildAllowedFreeWithAdults(roomTP.getChildAllowedFreeWithAdults());
					sHotelRoomType.setExtraBedAllowed(roomTP.getExtraBedAllowed());
					sHotelRoomType.setRoomSuiteType(roomTP.getRoomSuiteType());
					arrayCount.add(aCount, roomTP.getPcount());
					
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
					promoMap.put(roomTP.getRoomId(),roomTP.getPcount());
					if(roomTP.getSpecials() != null){
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
					}
					mapRM.put(roomTP.getRoomId(), sRateDetail);
					promoMap.put(roomTP.getRoomId(),arrayCount.get(aCount));
					if(roomTP.getSpecials()!= null){
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
				if(entry.getValue() == 0 && diffProm == 0){
					room.setApplyPromotion(0);
				}else if(entry.getValue() >= diffProm){
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


}
