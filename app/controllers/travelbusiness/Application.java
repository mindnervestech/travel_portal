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
import java.util.Properties;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import play.Play;
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
import com.travelportal.domain.CancellationDateDiff;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelAmenities;
import com.travelportal.domain.HotelBookingDetails;
import com.travelportal.domain.HotelImagesPath;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.InfoWiseImagesPath;
import com.travelportal.domain.InternalContacts;
import com.travelportal.domain.RoomAndDateWiseRate;
import com.travelportal.domain.RoomRegiterBy;
import com.travelportal.domain.RoomRegiterByChild;
import com.travelportal.domain.admin.BatchMarkup;
import com.travelportal.domain.admin.BreakfastMarkup;
import com.travelportal.domain.admin.CurrencyExchangeRate;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RateSpecialDays;
import com.travelportal.domain.rooms.RoomAllotedRateWise;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.domain.rooms.RoomChildPolicies;
import com.travelportal.domain.rooms.Specials;
import com.travelportal.domain.rooms.SpecialsMarket;
import com.travelportal.vm.AgentRegistrationVM;
import com.travelportal.vm.BatchMarkupInfoVM;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.ChildselectedVM;
import com.travelportal.vm.CurrencyExchangeVM;
import com.travelportal.vm.HotelBookingDetailsVM;
import com.travelportal.vm.HotelSearch;
import com.travelportal.vm.PassengerBookingInfoVM;
import com.travelportal.vm.RateDatedetailVM;
import com.travelportal.vm.RoomAmenitiesVm;
import com.travelportal.vm.RoomChildpoliciVM;
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
//import com.gargoylesoftware.htmlunit.javascript.host.Console;
public class Application extends Controller {

	final static String rootDir = Play.application().configuration().getString("mail.storage.path");
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
   
		if(agent != null) {
			System.out.println("SESSION VALUE   "+session().get("agent"));
			AgentRegistrationVM aVm=new AgentRegistrationVM(agent);
			session().put("agent", agent.getAgentCode());
			return ok(Json.toJson(aVm));
		}
		
		return ok(Json.toJson("0"));
    	
    }
  
        
    @Transactional(readOnly=true)
	public static Result searchCountries() {
		final List<Country> countries = Country.getCountries(); 
		List<Map> country = new ArrayList<>();
 		for(Country c : countries){
 			if(c.getCountryCode() == 4 ||c.getCountryCode() == 1){
 			Map m = new HashMap<>();
 			m.put("countryCode", c.getCountryCode());
 			m.put("countryName", c.getCountryName());
 			country.add(m);
 			}
		}
		return ok(Json.toJson(country));
	}
    
    
    @Transactional(readOnly=true)
	public static Result searchCities(int countryId) {
		final List<City> cities = City.getCities(countryId);
		
		List<Integer> cityValue = HotelProfile.getCityId();
		
		List<Map> city = new ArrayList<>();
 		for(City c : cities){
 			for (Integer cityV : cityValue) {
 				if(c.getCityCode() == cityV.longValue()){
 					Map m = new HashMap<>();
 	 	 			m.put("id", c.getCityCode());
 	 	 			m.put("name", c.getCityName());
 	 				city.add(m);
 				}
 				
 			}
 			
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

    
    @Transactional(readOnly=true)
	public static Result getAgentpassword(String email){
    	
    	AgentRegistration aRegistration= AgentRegistration.findByemail(email);
    	//HotelProfile hProfile = HotelProfile.findByEmail(email);
		int flag = 0;
		if(aRegistration != null){

 		flag= 0;
 		
		}else{
			flag=1;
		}
		return ok(Json.toJson(flag));
    }
    
    
    @Transactional(readOnly = true)
    public static Result searchAgainHotelInfo() {
    	
    	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    	//Long.parseLong(session().get("agent"))
    	
    	Form<SearchHotelValueVM> HotelForm = Form.form(SearchHotelValueVM.class).bindFromRequest();
    	SearchHotelValueVM searchVM = HotelForm.get();
    		
    		Map<String, Object> mapObject = new HashMap<String, Object>();
    		List<HotelSearch> hotellist = new ArrayList<>();
    		Map<Long, Long> map = new HashMap<Long, Long>();
    		
    		String fromDate = searchVM.checkIn;
    		String toDate = searchVM.checkOut;
    		//String[] sId = {searchVM.id};
    		String cityId = searchVM.cityCode;
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
    		boolean refundValue = false;
    		fillRoomsInHotelInfo(hotellist,hotelRMlist,hotelNo,diffInpromo,refundValue); /* fill data in room in hotel object.... function*/
    		
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
		
    		AgentRegistration aRegistration = AgentRegistration.getAgentCode(session().get("agent"));
    		AgentRegistrationVM aVm = new AgentRegistrationVM(aRegistration);
    		
    		CurrencyExchangeVM cVm = new CurrencyExchangeVM();
    		currencyExchangeRateDate(aVm, cVm);
    		
    		
    		if(!hotelCount.isEmpty()){
    			mapObject.put("hotelAmenities", hotelAmenities);
    			mapObject.put("hotelServices", hotelServices);
    			mapObject.put("hotellocation",hotelLocation);
    		}
    		
    		mapObject.put("currencyExchangeRate", cVm);
    		mapObject.put("agentInfo", aVm);
    		mapObject.put("hotelId", hotelNo);
    		mapObject.put("hotellist", hotellist);
    		mapObject.put("totalHotel",totalHotel);
    		
    		JsonNode personJson = Json.toJson(mapObject);
    
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
    		String cityId = searchVM.cityCode;
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
    		boolean refundValue = false;
    		fillRoomsInHotelInfo(hotellist,hotelRMlist,hotelNo,diffInpromo,refundValue); /* fill data in room in hotel object.... function*/
    		
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
		
    		AgentRegistration aRegistration = AgentRegistration.getAgentCode(session().get("agent"));
    		AgentRegistrationVM aVm = new AgentRegistrationVM(aRegistration);
    		
    		CurrencyExchangeVM cVm = new CurrencyExchangeVM();
    		currencyExchangeRateDate(aVm, cVm);
    		
    		if(!hotelCount.isEmpty()){
    			mapObject.put("hotelAmenities", hotelAmenities);
    			mapObject.put("hotelServices", hotelServices);
    			mapObject.put("hotellocation",hotelLocation);
    		}
    		
    		mapObject.put("currencyExchangeRate", cVm);
    		mapObject.put("agentInfo", aVm);
    		mapObject.put("hotelId", hotelNo);
    		mapObject.put("hotellist", hotellist);
    		mapObject.put("totalHotel",totalHotel);
    		
    		JsonNode personJson = Json.toJson(mapObject);
    
    	return ok(searchHotel.render(personJson));
    }
    
    public static void currencyExchangeRateDate(AgentRegistrationVM aVm, CurrencyExchangeVM cVm){
    	Currency currency = Currency.getCurrencyByName(aVm.currency);
		List<CurrencyExchangeRate> cList = CurrencyExchangeRate.findCurrencyRate(currency.getId());
		
		for(CurrencyExchangeRate cExchangeRate:cList){
			cVm.currencySelect = cExchangeRate.getCurrId().getId();
			if(cExchangeRate.getCurrencyName().equals("INR")){
				cVm.curr_INR = cExchangeRate.getCurrencyRate();
			}
			if(cExchangeRate.getCurrencyName().equals("THB")){
				cVm.curr_THB = cExchangeRate.getCurrencyRate();
			}
			if(cExchangeRate.getCurrencyName().equals("SGD")){
				cVm.curr_SGD = cExchangeRate.getCurrencyRate();
			}
			if(cExchangeRate.getCurrencyName().equals("MYR")){
				cVm.curr_MYR = cExchangeRate.getCurrencyRate();
			}
		}
    }

@Transactional(readOnly=false)
public static Result getHotelImagePath(long supplierCode,long indexValue) {
	
	HotelImagesPath hImagesPath = HotelImagesPath.findByIdAndIndex(supplierCode,indexValue);
	File f;
	if(hImagesPath != null){
	 
		if(hImagesPath.getPicturePath() != null){
			 f = new File(hImagesPath.getPicturePath());
		}else{
			f = new File(rootDir+"/default/logo.jpg");
		}
	}
	else{
		f = new File(rootDir+"/default/logo.jpg");
	}
			
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
			
			List<BatchMarkup> batchMarkup = BatchMarkup.findMarkupAgentSupplier(AgentRegistration.findByIdOnCode(session().get("agent")), supplierid.longValue());
			
			for(BatchMarkup bm:batchMarkup){
				BatchMarkupInfoVM baInfoVM = new BatchMarkupInfoVM();
				
				if(bm.getFlat() != null){
					baInfoVM.flat = bm.getFlat();
				}
				if(bm.getPercent() != null){
					baInfoVM.percent = bm.getPercent();
				}
				baInfoVM.selected = bm.getSelected();
				baInfoVM.supplier = bm.getSupplier();
				
				hProfileVM.batchMarkup = baInfoVM;
			}
			
			BreakfastMarkup bMarkup = BreakfastMarkup.findById(1L);
			if(bMarkup != null){
				hProfileVM.breakfackRate = bMarkup.getBreakfastRate();
			}
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
							
							allotmentmarketInfo(alloMarket,rateVM,nationalityId,rate.getId(),checkInDate.getTime());
							
							
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
			vm.breakfast = market.isBreakfast();
			vm.adultRate = market.getAdultRate();
			vm.childRate = market.getChildRate();
			
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

public static void fillRoomsInHotelInfo(List<HotelSearch> hotellist,List<SerachHotelRoomType> hotelRMlist,List<Long> hotelNo,Long diffInpromo, Boolean refundValue){
	
	Map<Long, List<SpecialsVM>> mapSpecials = new HashMap<Long, List<SpecialsVM>>();
	Map<Long, Integer> promoMap = new HashMap<Long, Integer>();
	Map<Long, SerachedRoomRateDetail> mapRM = new HashMap<Long, SerachedRoomRateDetail>();
	Map<Long, Boolean> nonrefundRoom = new HashMap<Long, Boolean>();
	
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
				SerachHotelRoomType sHotelRoomType = new SerachHotelRoomType();
				sHotelRoomType.hotelRoomRateDetail = new ArrayList<SerachedRoomRateDetail>();
				
				SerachedRoomRateDetail sRateDetail = new SerachedRoomRateDetail();
				sRateDetail.rateDetailsNormal = new ArrayList<SearchRateDetailsVM>();

				List<SearchRateDetailsVM> searchRateDetailsVMs = new ArrayList<>();
				SerachedRoomRateDetail objectRM = mapRM.get(roomTP.getRoomId());
				if (objectRM == null) {
					
					count++;
					
					sHotelRoomType.setRoomId(roomTP.getRoomId());
					sHotelRoomType.setRoomName(roomTP.getRoomName());
					sHotelRoomType.setRoomSize(roomTP.getRoomSize());
					sHotelRoomType.setDescription(roomTP.getDescription());
					sHotelRoomType.setMaxAdultsWithchild(roomTP.getMaxAdultsWithchild());
					sHotelRoomType.setRoomchildPolicies(roomTP.getRoomchildPolicies());
					sHotelRoomType.setChildAllowedFreeWithAdults(roomTP.getChildAllowedFreeWithAdults());
					sHotelRoomType.setExtraBedAllowed(roomTP.getExtraBedAllowed());
					sHotelRoomType.setRoomSuiteType(roomTP.getRoomSuiteType());
					sHotelRoomType.setBreakfastInclude(roomTP.getBreakfastInclude());
					sHotelRoomType.setBreakfastRate(roomTP.getBreakfastRate());
					sHotelRoomType.setChildAge(roomTP.getChildAge());
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
						sRateDetail.setCancellation(rateObj.getCancellation());
						if(rateObj.non_refund == true){
							refundValue = true;
						}
						nonrefundRoom.put(roomTP.getRoomId(), rateObj.non_refund);
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
						if(rateObj.non_refund == true){
							refundValue = true;
							nonrefundRoom.put(roomTP.getRoomId(), rateObj.non_refund);
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
		
		System.out.println(nonrefundRoom);
		int diffProm;
		for(SerachHotelRoomType room:hotel.hotelbyRoom){
			for (Entry<Long, Boolean> entry : nonrefundRoom.entrySet()) {
				if(room.getRoomId() == entry.getKey()){
					room.nonRefund = entry.getValue();
				}
			}
			for (Entry<Long, Integer> entry : promoMap.entrySet()) {
			if(room.getRoomId() == entry.getKey()){
				room.setPcount(entry.getValue());
				diffProm = diffInpromo.intValue();
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

public static void allotmentmarketInfo(AllotmentMarket alloMarket,SerachedRoomRateDetail rateVM,String nationalityId,Long rateid,Date CurrDate){
	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
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
	roomtyp.setMaxAdultsWithchild(room.getMaxAdultOccSharingWithChildren());
	roomtyp.setExtraBedAllowed(room.getExtraBedAllowed());
	roomtyp.setRoomSuiteType(room.getRoomSuiteType());
	roomtyp.setRoomSize(room.getRoomSize());
	roomtyp.setBreakfastInclude(room.getBreakfastInclude());
	if(room.getBreakfastRate() != null){
	roomtyp.setBreakfastRate(room.getBreakfastRate().intValue());
	}else{
		roomtyp.setBreakfastRate(0);
	}
	roomtyp.setChildAge(room.getChildAge());
	//roomtyp.setExtraBedRate(String.valueOf(room.getExtraBedRate()));
	
	List<RoomChildpoliciVM> roomChildList = new ArrayList<>();
	
	for(RoomChildPolicies rooVm:room.getRoomchildPolicies()){
		RoomChildpoliciVM rooChildpoliciVM = new RoomChildpoliciVM();
		rooChildpoliciVM.setAllowedChildAgeFrom(rooVm.getAllowedChildAgeFrom());
		rooChildpoliciVM.setAllowedChildAgeTo(rooVm.getAllowedChildAgeTo());
		rooChildpoliciVM.setExtraChildRate(String.valueOf(rooVm.getExtraChildRate()));
	//	rooChildpoliciVM.setNetRate(rooVm.getNetRate());
		rooChildpoliciVM.setRoomchildPolicyId(rooVm.getRoomchildPolicyId());
		//rooChildpoliciVM.setYears(rooVm.getYears());
		roomChildList.add(rooChildpoliciVM);
	}
	
	roomtyp.setRoomchildPolicies(roomChildList);
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
	hProfileVM.setPerferhotel(hAmenities.getPerfer());
	hProfileVM.setSupplierNm(hAmenities.getSupplierName());

	if (hAmenities.getStartRatings() != null) {
		hProfileVM.setStartRating(hAmenities.getStartRatings()
				.getId());
		hProfileVM.setStars(hAmenities.getStartRatings().getStarRating());
	}
	
	CancellationDateDiff can = CancellationDateDiff.getById(1);
	
	hProfileVM.cancellation_date_diff = can.getDateDiff();
	hProfileVM.currencyId = hAmenities.getCurrency().getCurrencyCode();
	hProfileVM.currencyName = hAmenities.getCurrency().getCurrencyName();
	String currency = hAmenities.getCurrency().getCurrencyName();
	String[] currencySplit;
	 currencySplit = currency.split(" - ");
	hProfileVM.currencyShort = currencySplit[0];
	hProfileVM.setHotelAddr(hAmenities.getAddress());
	if(hAmenities.getCountry() != null){
	hProfileVM.setCountryCode(hAmenities.getCountry().getCountryCode());
	hProfileVM.setCountryName(hAmenities.getCountry().getCountryName());
	}
	hProfileVM.setCityCode(hAmenities.getCity().getCityCode());
	hProfileVM.setCityName(hAmenities.getCity().getCityName());
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
	Country country = Country.getCountryByCode(Integer.parseInt(nationality));
	hProfileVM.setNationalityName(country.getNationality());
	
	InternalContacts internalCont = InternalContacts.findById(hAmenities.getSupplier_code());
	if(internalCont != null){
		hProfileVM.setCheckInTime(internalCont.getCheckInTime());
		hProfileVM.setCheckOutTime(internalCont.getCheckOutTime());
		hProfileVM.setRoomVoltage(internalCont.getRoomVoltage());
		hProfileVM.setHotelBuiltYear(String.valueOf(hAmenities.getHotelBuiltYear()));
	}
	
	/*InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(hAmenities.getSupplier_code());
	if(infowiseimagesPath != null){
	if(infowiseimagesPath.getGeneralDescription() != null){
	hProfileVM.setImgDescription(infowiseimagesPath.getGeneralDescription());
	}
	}*/
	List<HotelMealPlan> mealtype = HotelMealPlan.getmealtype(hAmenities.getSupplier_code());
	hProfileVM.setMealPlan(mealtype);
	
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
	Long objectcancel;
	Map<Long, Long> mapcancel = new HashMap<Long, Long>();
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
							for(CancellationPolicy cancel:cancellation) {
								objectcancel = (Long) mapcancel.get(cancel.getId());
	    						
	    						if (objectcancel == null) {
	    							
								System.out.println(cancel.getIsNormal());
								if(cancel.getIsNormal() > 2){
									if(person.getIsNormal() == cancel.getIsNormal()){
									CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
									rateVM.cancellation.add(vm);
									rateVM.non_refund = cancel.isNon_refund();
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
					rateVM.non_refund = cancel.isNon_refund();
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
					rateVM.non_refund = cancel.isNon_refund();
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
					rateVM.non_refund = cancel.isNon_refund();
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
					rateVM.non_refund = cancel.isNon_refund();
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
					rateVM.non_refund = cancel.isNon_refund();
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
					rateVM.non_refund = cancel.isNon_refund();
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
					rateVM.non_refund = cancel.isNon_refund();
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
					rateVM.non_refund = cancel.isNon_refund();
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
					rateVM.non_refund = cancel.isNon_refund();
				}
				mapcancel.put(cancel.getId(), Long.parseLong("1"));
				}
			 }
			}
		
	}
	
	 
	}
	
}

public static void hotelBookingAllInfo(HotelSearch hProfileVM,String BookingId){
	HotelBookingDetailsVM hbooking = new HotelBookingDetailsVM(); 
	List<PassengerBookingInfoVM> pBookingInfoVM = new ArrayList<>();
	HotelBookingDetails hDetails = HotelBookingDetails.findBookingById(Long.parseLong(BookingId));
	List<RoomRegiterBy> roBy = RoomRegiterBy.getRoomInfoByBookingId(Long.parseLong(BookingId));
	if(roBy != null){
		//hProfileVM.hotelBookingDetails.passengerInfo =
		for(RoomRegiterBy rBy:roBy){
			PassengerBookingInfoVM paInfoVM = new PassengerBookingInfoVM();
			paInfoVM.adult = rBy.getAdult();
			paInfoVM.noOfchild =String.valueOf(rBy.getNoOfchild());
			paInfoVM.regiterBy = rBy.getRegiterBy();
			paInfoVM.total = rBy.getTotal();
			List<ChildselectedVM> chList = new ArrayList<>();
			List<RoomRegiterByChild> rByChild = RoomRegiterByChild.getRoomChildInfoByRoomId(rBy.getId());
			if(rByChild != null){
			
			for(RoomRegiterByChild rChild:rByChild){
				ChildselectedVM cVm = new ChildselectedVM();
				cVm.age = String.valueOf(rChild.getAge());
				cVm.breakfast = rChild.getBreakfast();
				cVm.childRate = String.valueOf(rChild.getChild_rate());
				cVm.freeChild = rChild.getFree_child();
				chList.add(cVm);
			}
			paInfoVM.childselected = chList;
			}
			
			List<RateDatedetailVM> rList = new ArrayList<>();
			List<RoomAndDateWiseRate> rByrate = RoomAndDateWiseRate.getRoomRateInfoByRoomId(rBy.getId());
			if(rByrate != null){
			
			for(RoomAndDateWiseRate rRate:rByrate){
				RateDatedetailVM rVm = new RateDatedetailVM();
				rVm.currency = rRate.getCurrency();
				rVm.date = rRate.getDate();
				rVm.day = rRate.getDay();
				rVm.fulldate = rRate.getFulldate();
				rVm.meal = rRate.getMeal();
				rVm.month = rRate.getMonth();
				rVm.rate = String.valueOf(rRate.getRate());
				rList.add(rVm);
			}
			paInfoVM.rateDatedetail = rList;
			}
			
			pBookingInfoVM.add(paInfoVM);
			
		}
		hbooking.passengerInfo = pBookingInfoVM;
		hbooking.travelleraddress = hDetails.getTravelleraddress();
		hbooking.travellercountry = String.valueOf(hDetails.getTravellercountry().getCountryCode());
		hbooking.travelleremail = hDetails.getTravelleremail();
		hbooking.travellerfirstname = hDetails.getTravellerfirstname();
		hbooking.travellerlastname = hDetails.getTravellerlastname();
		hbooking.travellermiddlename = hDetails.getTravellermiddlename();
		hbooking.travellerpassportNo = hDetails.getTravellerpassportNo();
		hbooking.travellerphnaumber = hDetails.getTravellerphnaumber();
		hbooking.travellersalutation = String.valueOf(hDetails.getTravellersalutation().getSalutationId());
		hProfileVM.hotelBookingDetails = hbooking;
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
		String cityId = searchHotelValueVM.getCityCode();
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
		List<BigInteger> supplierId;
		if(searchHotelValueVM.getHotelname() == null || searchHotelValueVM.getHotelname() == ""){
			supplierId = RateMeta.getsupplierId(
				Integer.parseInt(cityId),
				Integer.parseInt(nationalityId));
		}else{
			supplierId = RateMeta.getsupplierByName(searchHotelValueVM.getHotelname());
		}

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
						,searchHotelValueVM.getAmenitiesCheck(),searchHotelValueVM.getServicesCheck(),searchHotelValueVM.getLocationCheck(),searchHotelValueVM.getStarCheck());  
				
				List<BatchMarkup> batchMarkup = BatchMarkup.findMarkupAgentSupplier(AgentRegistration.findById(Long.parseLong(session().get("agent"))), supplierid.longValue());
				
				for(BatchMarkup bm:batchMarkup){
					BatchMarkupInfoVM baInfoVM = new BatchMarkupInfoVM();
					
					if(bm.getFlat() != null){
						baInfoVM.flat = bm.getFlat();
					}
					if(bm.getPercent() != null){
						baInfoVM.percent = bm.getPercent();
					}
					baInfoVM.selected = bm.getSelected();
					baInfoVM.supplier = bm.getSupplier();
					
					hProfileVM.batchMarkup = baInfoVM;
				}
				
				BreakfastMarkup bMarkup = BreakfastMarkup.findById(1L);
				if(bMarkup != null){
					hProfileVM.breakfackRate = bMarkup.getBreakfastRate();
				}
				
				//hProfileVM.currencyExchangeRate = searchHotelValueVM.currencyExchangeRate;
				
				for(HotelProfile hAmenities:hAmenities1){
					
					hProfileVM.agentCurrency = searchHotelValueVM.agentCurrency;
					List<Currency> currencyList = Currency.getCurrency();
					for(Currency curr:currencyList){
						String[] currencySplit;
						 currencySplit = curr.getCurrencyName().split(" - ");
						if(currencySplit[0].equals(searchHotelValueVM.agentCurrency)){
							List<CurrencyExchangeRate> cExchangeRate = CurrencyExchangeRate.findCurrencyRate(curr.getId());
							
							String[] currencySplit1;
							 currencySplit1 = hAmenities.getCurrency().getCurrencyName().split(" - ");
							for(CurrencyExchangeRate cExchange:cExchangeRate){
								if(currencySplit1[0].equals(cExchange.getCurrencyName())){
									hProfileVM.currencyExchangeRate = cExchange.getCurrencyRate();
								}
							}
							
						}
					}
					
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
								allotmentmarketInfo(alloMarket,rateVM,nationalityId,rate.getId(),checkInDate.getTime());
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
		boolean refundValue = false;
		fillRoomsInHotelInfo(hotellist,hotelRMlist,hotelNo,diffInpromo, refundValue); /* fill data in room in hotel object.... function*/
		
		List<Long> hotelCount = new ArrayList<>();
		int totalHotel = 0;
		for(Long no:hotelNo){
			totalHotel++;
			HotelProfile hProfile = HotelProfile.findAllData(no);
			hotelCount.add(hProfile.getId());
			
		}
		
		findMinRateInHotel(hotellist);  /* find min Rate in par Hotel function*/
		
		if(searchHotelValueVM.getNoSort().equals("1")){
		if(searchHotelValueVM.getSortData().equals("1")){
			Collections.sort(hotellist,new HotelComparatorByRateAsc());
		}else{
			Collections.sort(hotellist,new HotelComparatorByRateDes());
		}
		}
		
		if(searchHotelValueVM.getNoSort().equals("0")){
		if(searchHotelValueVM.getSortByRating().equals("1")){
			Collections.sort(hotellist,new HotelComparatorByRatingAsc());
		}else{
			Collections.sort(hotellist,new HotelComparatorByRatingDes());
		}
		}
	
		mapObject.put("hotellist", hotellist);
		mapObject.put("totalHotel",totalHotel);
		
	
	return ok(Json.toJson(mapObject));
	
}

public static double currencyExchange(HotelSearch arg){
	
	return  (1/arg.currencyExchangeRate * arg.minRate);
}

public static class HotelComparatorByRateAsc implements Comparator<HotelSearch> {

	@Override
	public int compare(HotelSearch arg0, HotelSearch arg1) {
		return currencyExchange(arg0) > currencyExchange(arg1) ? -1 : currencyExchange(arg0) < currencyExchange(arg1)?1:0;
	}
}

public static class HotelComparatorByRateDes implements Comparator<HotelSearch> {

	@Override
	public int compare(HotelSearch arg0, HotelSearch arg1) {
		return currencyExchange(arg0) < currencyExchange(arg1) ? -1 : currencyExchange(arg0) > currencyExchange(arg1)?1:0;
	}
}

/*public static class HotelComparatorByRateAsc implements Comparator<HotelSearch> {

	@Override
	public int compare(HotelSearch arg0, HotelSearch arg1) {
		return arg0.minRate.doubleValue() > arg1.minRate.doubleValue() ? -1 : arg0.minRate.doubleValue() < arg1.minRate.doubleValue()?1:0;
	}
}*/
/*public static class HotelComparatorByRateDes implements Comparator<HotelSearch> {

	@Override
	public int compare(HotelSearch arg0, HotelSearch arg1) {
		return arg0.minRate.doubleValue() < arg1.minRate.doubleValue() ? -1 : arg0.minRate.doubleValue() > arg1.minRate.doubleValue()?1:0;
	}
}*/
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
		if(searchVM.bookingId != null)
		{
			hProfileVM.bookingId = searchVM.bookingId;
			hotelBookingAllInfo(hProfileVM,searchVM.bookingId);
			/*HotelBookingDetailsVM hbooking = new HotelBookingDetailsVM(); 
			List<PassengerBookingInfoVM> pBookingInfoVM = new ArrayList<>();
			List<RoomRegiterBy> roBy = RoomRegiterBy.getRoomInfoByBookingId(Long.parseLong(searchVM.bookingId));
			if(roBy != null){
				//hProfileVM.hotelBookingDetails.passengerInfo =
				for(RoomRegiterBy rBy:roBy){
					PassengerBookingInfoVM paInfoVM = new PassengerBookingInfoVM();
					paInfoVM.adult = rBy.getAdult();
					paInfoVM.noOfchild =String.valueOf(rBy.getNoOfchild());
					paInfoVM.regiterBy = rBy.getRegiterBy();
					paInfoVM.total = rBy.getTotal();
					List<ChildselectedVM> chList = new ArrayList<>();
					List<RoomRegiterByChild> rByChild = RoomRegiterByChild.getRoomChildInfoByRoomId(rBy.getId());
					if(rByChild != null){
					
					for(RoomRegiterByChild rChild:rByChild){
						ChildselectedVM cVm = new ChildselectedVM();
						cVm.age = String.valueOf(rChild.getAge());
						cVm.breakfast = rChild.getBreakfast();
						cVm.childRate = String.valueOf(rChild.getChild_rate());
						cVm.freeChild = rChild.getFree_child();
						chList.add(cVm);
					}
					paInfoVM.childselected = chList;
					}
					
					List<RateDatedetailVM> rList = new ArrayList<>();
					List<RoomAndDateWiseRate> rByrate = RoomAndDateWiseRate.getRoomRateInfoByRoomId(rBy.getId());
					if(rByrate != null){
					
					for(RoomAndDateWiseRate rRate:rByrate){
						RateDatedetailVM rVm = new RateDatedetailVM();
						rVm.currency = rRate.getCurrency();
						rVm.date = rRate.getDate();
						rVm.day = rRate.getDay();
						rVm.fulldate = rRate.getFulldate();
						rVm.meal = rRate.getMeal();
						rVm.month = rRate.getMonth();
						rVm.rate = String.valueOf(rRate.getRate());
						rList.add(rVm);
					}
					paInfoVM.rateDatedetail = rList;
					}
					
					pBookingInfoVM.add(paInfoVM);
					
				}
				hbooking.passengerInfo = pBookingInfoVM;
				hProfileVM.hotelBookingDetails = hbooking;
			}*/
			
		}
		
		hProfileVM.agentCurrency = searchVM.agentCurrency;
		hProfileVM.currencyExchangeRate = searchVM.currencyExchangeRate;
		Long object = (Long) map.get(supplierid.longValue());
		
		if (object == null) {
			HotelProfile hAmenities = HotelProfile.findAllData(supplierid.longValue());
			
			List<BatchMarkup> batchMarkup = BatchMarkup.findMarkupAgentSupplier(AgentRegistration.findById(Long.parseLong(session().get("agent"))), supplierid.longValue());
			
			for(BatchMarkup bm:batchMarkup){
				BatchMarkupInfoVM baInfoVM = new BatchMarkupInfoVM();
				
				if(bm.getFlat() != null){
					baInfoVM.flat = bm.getFlat();
				}
				if(bm.getPercent() != null){
					baInfoVM.percent = bm.getPercent();
				}
				baInfoVM.selected = bm.getSelected();
				baInfoVM.supplier = bm.getSupplier();
				
				hProfileVM.batchMarkup = baInfoVM;
			}
			
			BreakfastMarkup bMarkup = BreakfastMarkup.findById(1L);
			if(bMarkup != null){
				hProfileVM.breakfackRate = bMarkup.getBreakfastRate();
			}
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
							allotmentmarketInfo(alloMarket,rateVM,nationalityId,rate.getId(),checkInDate.getTime());
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
	boolean refundValue = false;
	for (HotelSearch hotel : hotellist) {
		fillRoomsInHotelInfo1(hotel, hotelRMlist, count, mapRM ,diffInpromo,refundValue); /* fill data in room in hotel object.... function*/
				
		findMinRateInHotel(hotellist);
				
		JsonNode onehotelJson = Json.toJson(hotel);
		return ok(hotelDetails.render(onehotelJson));
	}
	return ok(Json.toJson(null));
	
}


@Transactional(readOnly = true)
	public static Result getDatewiseHotelRoom(String checkIn,String checkOut,String nationality,String supplierCode1,String roomCode,String bookingId) {
	
		//
	
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
			
			
			if(!bookingId.equals("null"))
			{
				hProfileVM.bookingId = bookingId;
				hotelBookingAllInfo(hProfileVM,bookingId);
				
			/*	HotelBookingDetailsVM hbooking = new HotelBookingDetailsVM(); 
				List<PassengerBookingInfoVM> pBookingInfoVM = new ArrayList<>();
				List<RoomRegiterBy> roBy = RoomRegiterBy.getRoomInfoByBookingId(Long.parseLong(bookingId));
				if(roBy != null){
					//hProfileVM.hotelBookingDetails.passengerInfo =
					for(RoomRegiterBy rBy:roBy){
						PassengerBookingInfoVM paInfoVM = new PassengerBookingInfoVM();
						paInfoVM.adult = rBy.getAdult();
						paInfoVM.noOfchild =String.valueOf(rBy.getNoOfchild());
						paInfoVM.total = rBy.getTotal();
						List<ChildselectedVM> chList = new ArrayList<>();
						List<RoomRegiterByChild> rByChild = RoomRegiterByChild.getRoomChildInfoByRoomId(rBy.getId());
						if(rByChild != null){
						
						for(RoomRegiterByChild rChild:rByChild){
							ChildselectedVM cVm = new ChildselectedVM();
							cVm.age = String.valueOf(rChild.getAge());
							cVm.breakfast = rChild.getBreakfast();
							cVm.childRate = String.valueOf(rChild.getChild_rate());
							cVm.freeChild = rChild.getFree_child();
							chList.add(cVm);
						}
						paInfoVM.childselected = chList;
						}
						
						List<RateDatedetailVM> rList = new ArrayList<>();
						List<RoomAndDateWiseRate> rByrate = RoomAndDateWiseRate.getRoomRateInfoByRoomId(rBy.getId());
						if(rByrate != null){
						
						for(RoomAndDateWiseRate rRate:rByrate){
							RateDatedetailVM rVm = new RateDatedetailVM();
							rVm.currency = rRate.getCurrency();
							rVm.date = rRate.getDate();
							rVm.day = rRate.getDay();
							rVm.fulldate = rRate.getFulldate();
							rVm.meal = rRate.getMeal();
							rVm.month = rRate.getMonth();
							rVm.rate = String.valueOf(rRate.getRate());
							rList.add(rVm);
						}
						paInfoVM.rateDatedetail = rList;
						}
						
						pBookingInfoVM.add(paInfoVM);
						
					}
					hbooking.passengerInfo = pBookingInfoVM;
					hProfileVM.hotelBookingDetails = hbooking;
				}*/
				
			}
			
			
			
			Long object = (Long) map.get(supplierid.longValue());
			
			if (object == null) {
				HotelProfile hAmenities = HotelProfile.findAllData(supplierid.longValue());	
				
				List<BatchMarkup> batchMarkup = BatchMarkup.findMarkupAgentSupplier(AgentRegistration.findById(Long.parseLong(session().get("agent"))), Long.parseLong(supplierCode));
				
				for(BatchMarkup bm:batchMarkup){
					BatchMarkupInfoVM baInfoVM = new BatchMarkupInfoVM();
					
					if(bm.getFlat() != null){
						baInfoVM.flat = bm.getFlat();
					}
					if(bm.getPercent() != null){
						baInfoVM.percent = bm.getPercent();
					}
					baInfoVM.selected = bm.getSelected();
					baInfoVM.supplier = bm.getSupplier();
					
					hProfileVM.batchMarkup = baInfoVM;
				}
				
				BreakfastMarkup bMarkup = BreakfastMarkup.findById(1L);
				if(bMarkup != null){
					hProfileVM.breakfackRate = bMarkup.getBreakfastRate();
				}
				
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
								allotmentmarketInfo(alloMarket,rateVM,nationalityId,rate.getId(),checkInDate.getTime());
								
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
		boolean refundValue = false;
		for (HotelSearch hotel : hotellist) {
			
			fillRoomsInHotelInfo1(hotel, hotelRMlist, count, mapRM , diffInpromo, refundValue); /* fill data in room in hotel object.... function*/
		
			return ok(Json.toJson(hotel));
		}
		return ok(Json.toJson(null));
	}


public static void fillRoomsInHotelInfo1(HotelSearch hotel, List<SerachHotelRoomType> hotelRMlist,int count, Map<Long, SerachedRoomRateDetail> mapRM,Long diffInpromo,boolean refundValue){
	
	Map<Long, List<SpecialsVM>> mapSpecials = new HashMap<Long, List<SpecialsVM>>();
	Map<Long, Integer> promoMap = new HashMap<Long, Integer>();
	Map<Long, Boolean> nonrefundRoom = new HashMap<Long, Boolean>();
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
					sHotelRoomType.setRoomSize(roomTP.getRoomSize());
					//sHotelRoomType.setExtraBedRate(roomTP.getExtraBedRate());
					sHotelRoomType.setDescription(roomTP.getDescription());
					sHotelRoomType.setMaxAdultsWithchild(roomTP.getMaxAdultsWithchild());
					sHotelRoomType.setRoomchildPolicies(roomTP.getRoomchildPolicies());
					sHotelRoomType.setChildAllowedFreeWithAdults(roomTP.getChildAllowedFreeWithAdults());
					sHotelRoomType.setExtraBedAllowed(roomTP.getExtraBedAllowed());
					sHotelRoomType.setBreakfastInclude(roomTP.getBreakfastInclude());
					sHotelRoomType.setBreakfastRate(roomTP.getBreakfastRate());
					sHotelRoomType.setChildAge(roomTP.getChildAge());
					
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
						sRateDetail.setCancellation(rateObj.getCancellation());
						if(rateObj.non_refund == true){
							refundValue = true;
						}
						nonrefundRoom.put(roomTP.getRoomId(), rateObj.non_refund);
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
							if(rateObj.non_refund == true){
								refundValue =true;
								nonrefundRoom.put(roomTP.getRoomId(), rateObj.non_refund);
							}
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
		
		System.out.println(nonrefundRoom.toString());
		
		int diffProm;
		for(SerachHotelRoomType room:hotel.hotelbyRoom){
			for (Entry<Long, Boolean> entry : nonrefundRoom.entrySet()) {
				if(room.getRoomId() == entry.getKey()){
					room.nonRefund = entry.getValue();
				}
			}
			for (Entry<Long, Integer> entry : promoMap.entrySet()) {
			if(room.getRoomId() == entry.getKey()){
				room.setPcount(entry.getValue());
				diffProm =diffInpromo.intValue();
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
