package controllers.travelbusiness;

import java.io.File;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import play.Play;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.generic.BitOperations.Int;
import views.html.travelbusiness.hotelBookingInfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.domain.AmenitiesType;
import com.travelportal.domain.CancellationDateDiff;
import com.travelportal.domain.Country;
import com.travelportal.domain.HotelAmenities;
import com.travelportal.domain.HotelBookingDetails;
import com.travelportal.domain.HotelImagesPath;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.InfoWiseImagesPath;
import com.travelportal.domain.InternalContacts;
import com.travelportal.domain.admin.BreakfastMarkup;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.ChildPolicies;
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
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.ChildselectedVM;
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
/*import play.activiti.engine.impl.util.json.JSONArray;
import play.activiti.engine.impl.util.json.JSONException;
import org.activiti.engine.impl.util.json.JSONObject;
*/

public class HotelDetailsController extends Controller {

	final static String rootDir = Play.application().configuration().getString("mail.storage.path");
	
	@Transactional(readOnly = false)
	public static Result getHotelGenImg(long supplierCode,long indexValue) {

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

	/*@Transactional(readOnly = false)
	public static Result getHotelServImg(long supplierCode) {

		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath
				.findById(supplierCode);
		
		File f = null;
		if(infowiseimagesPath != null){
			if(infowiseimagesPath.getAmenitiesServices() != null){
			
		 f = new File(infowiseimagesPath.getAmenitiesServices());
			}else{
				f = new File(rootDir+"/default/logo.jpg");
			}
		}else{
			    f = new File(rootDir+"/default/logo.jpg");
		}
		return ok(f);

	}

	@Transactional(readOnly = false)
	public static Result getHotelRoomImg(long supplierCode) {

		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath
				.findById(supplierCode);
		
		File f = null;
		if(infowiseimagesPath != null){
			if(infowiseimagesPath.getHotelRoom() != null){
			
		 f = new File(infowiseimagesPath.getHotelRoom());
			}else{
				f = new File(rootDir+"/default/logo.jpg");
			}
		}else{
			    f = new File(rootDir+"/default/logo.jpg");
		}
		return ok(f);

	}

	@Transactional(readOnly = false)
	public static Result getHotelLobbyImg(long supplierCode) {

		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath
				.findById(supplierCode);
		File f = null;
		if(infowiseimagesPath != null){
			if(infowiseimagesPath.getHotel_Lobby() != null){
			
		 f = new File(infowiseimagesPath.getHotel_Lobby());
			}else{
				f = new File(rootDir+"/default/logo.jpg");
			}
		}else{
			    f = new File(rootDir+"/default/logo.jpg");
		}
		return ok(f);

	}

	@Transactional(readOnly = false)
	public static Result getHotelLSImg(long supplierCode) {

		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath
				.findById(supplierCode);
		File f = null;
		if(infowiseimagesPath != null){
			if(infowiseimagesPath.getLeisureSports() != null){
			
		 f = new File(infowiseimagesPath.getLeisureSports());
			}else{
				f = new File("C:\\mypath\\default\\logo.jpg");
			}
		}else{
			    f = new File("C:\\mypath\\default\\logo.jpg");
		}
		return ok(f);

	}

	@Transactional(readOnly = false)
	public static Result getHotelMapImg(long supplierCode) {

		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath
				.findById(supplierCode);
		File f = null;
		if(infowiseimagesPath != null){
			if(infowiseimagesPath.getMap_image() != null){
			
		 f = new File(infowiseimagesPath.getMap_image());
			}else{
				f = new File("C:\\mypath\\default\\logo.jpg");
			}
		}else{
			    f = new File("C:\\mypath\\default\\logo.jpg");
		}
		return ok(f);

	}*/
	
	@Transactional(readOnly=true)
	public static Result getAmenities() {
		final List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(1));
		return ok(Json.toJson(hotelamenities));
	}
	
	@Transactional(readOnly=true)
	public static Result getBusiness() {
		final List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(2));
		return ok(Json.toJson(hotelamenities));
	}

	@Transactional(readOnly=true)
	public static Result getLeisureSport() {
		List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(3));
		return ok(Json.toJson(hotelamenities));
	}
	
	@Transactional(readOnly=true)
	public static Result findAmenitiesData(long supplierCode) {
		HotelProfile hotelProfile = HotelProfile.findAllData(supplierCode);
		Set<HotelAmenities> hotelamenities = hotelProfile.getAmenities();
		return ok(Json.toJson(hotelamenities));
	}
	
	@Transactional(readOnly=false)
	public static Result getHotelRoomImagePath(long roomId) {
		System.out.println("()()()()()()(");
		System.out.println(roomId);
		HotelRoomTypes hRoomTypes = HotelRoomTypes.findById(roomId);
		File f = null;
		if(hRoomTypes != null){
			if(hRoomTypes.getRoomPic() != null){
				f = new File(hRoomTypes.getRoomPic());
			}else{
				f = new File(rootDir+"/default/logo.jpg");
			}
		}else{
			f = new File(rootDir+"/default/logo.jpg");
		}
	    return ok(f);		
		
	}
	
	
	/* public static Result hotelBooking() {
  //  	PageScope.scope("number", 1);
    	return ok(hotelBookingInfo.render());
    }*/
	 

@Transactional(readOnly=true)
public static Result hotelBookingpage() {
	
	Form<SearchHotelValueVM> HotelForm = Form.form(SearchHotelValueVM.class).bindFromRequest();
	SearchHotelValueVM searchVM = HotelForm.get();
	
	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

	List<HotelSearch> hotellist = new ArrayList<>();
	Map<String, String[]> map1 = request().queryString();
	Map<Long, Long> map = new HashMap<Long, Long>();
	
	System.out.println("-=-=-=-=-=-=-");
	System.out.println(searchVM.checkIn);
	System.out.println(searchVM.checkOut);
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
		hProfileVM.bookingId = searchVM.bookingId;
		hProfileVM.agentCurrency = searchVM.agentCurrency;
		hProfileVM.currencyExchangeRate = searchVM.currencyExchangeRate;
		
		Long object = (Long) map.get(supplierid.longValue());
		
		if (object == null) {
			HotelProfile hAmenities = HotelProfile.findAllData(supplierid.longValue());
			BreakfastMarkup bMarkup = BreakfastMarkup.findById(1L);
			if(bMarkup != null){
				hProfileVM.breakfackRate = bMarkup.getBreakfastRate();
			}
			fillHotelInfo(hAmenities,hProfileVM,fromDate,toDate,nationalityId,dayDiff);
			
            //List<hotelBookingDetails> hotelBookingDetails=new ArrayList<hotelBookingDetails>();
			
			HotelBookingDetailsVM hotelBookings = new HotelBookingDetailsVM();
			
			if(searchVM.bookingId != null && searchVM.bookingId != ""){
				
				HotelBookingDetails hDetails = HotelBookingDetails.findBookingById(Long.parseLong(searchVM.bookingId));
				
				hotelBookings.travelleraddress = hDetails.getTravelleraddress();
				hotelBookings.travellercountry = String.valueOf(hDetails.getTravellercountry().getCountryCode());
				hotelBookings.travelleremail = hDetails.getTravelleremail();
				hotelBookings.travellerfirstname = hDetails.getTravellerfirstname();
				hotelBookings.travellerlastname = hDetails.getTravellerlastname();
				hotelBookings.travellermiddlename = hDetails.getTravellermiddlename();
				hotelBookings.travellerpassportNo = hDetails.getTravellerpassportNo();
				hotelBookings.travellerphnaumber = hDetails.getTravellerphnaumber();
				hotelBookings.travellersalutation = String.valueOf(hDetails.getTravellersalutation().getSalutationId());
			}
			//hotelBookings.setAdult(adult)
			
			if(searchVM.adult == ""){
				hotelBookings.setAdult("1 Adult");
			}else{
				hotelBookings.setAdult(searchVM.adult);
			}
			if(searchVM.noOfroom == ""){
				hotelBookings.setNoOfroom("1");
			}else{
				hotelBookings.setNoOfroom(searchVM.noOfroom);
		    }
			System.out.println(searchVM.noOfchild);
			if(searchVM.noOfchild == ""){
				hotelBookings.setNoOfchild("1");
			}else if(searchVM.noOfchild == null){
				hotelBookings.setNoOfchild("0");
			}else if(searchVM.noOfchild == "NoChild"){
				hotelBookings.setNoOfchild("0");
			}else{
				hotelBookings.setNoOfchild(searchVM.noOfchild);
		    }
			hotelBookings.setTotal(searchVM.total);
			hotelBookings.setApplyPromo(searchVM.applyPromo);
			
			hotelBookings.setTotalParPerson(searchVM.totalParPerson);
			
			List<PassengerBookingInfoVM> pList = new ArrayList<>();
			 JSONArray array = null;
			 JSONArray childArray = null;
			 JSONArray rateArray = null;
				try {
					array = new JSONArray(searchVM.getFinalTotalDetails());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 for(int i=0; i<array.length(); i++){
					 try {
						 PassengerBookingInfoVM pInfoVM = new PassengerBookingInfoVM();
						 org.json.JSONObject jsonObj  = array.getJSONObject(i);
					
						 pInfoVM.adult = jsonObj.getString("adult");
						 pInfoVM.total = String.valueOf(jsonObj.getLong("total"));
						 try{
						 pInfoVM.noOfchild = jsonObj.getString("noOfchild");
						 pInfoVM.regiterBy = jsonObj.getString("regiterBy");
						 } catch(Exception e){
							 System.out.println("Child Not Found");
						 }
						 
						 try{
						 childArray = jsonObj.getJSONArray("childselected");
						 List<ChildselectedVM> chList= new ArrayList<>();
						 for(int j=0; j<childArray.length(); j++){
							 org.json.JSONObject jsonObjChild  = childArray.getJSONObject(j);

							 ChildselectedVM chVm = new ChildselectedVM();
							 chVm.age = jsonObjChild.getString("age");
							 chVm.breakfast = jsonObjChild.getString("breakfast");
							 chVm.childRate = jsonObjChild.getString("childRate");
							 chVm.freeChild = jsonObjChild.getString("freeChild");
							 chList.add(chVm);
						 }
						 
						 pInfoVM.childselected = chList;
						 } catch(JSONException e){
							 System.out.println("No Child");
						 }
						 rateArray = jsonObj.getJSONArray("rateDatedetail");
						 List<RateDatedetailVM> rdList= new ArrayList<>();
						 for(int j=0; j<rateArray.length(); j++){
							 org.json.JSONObject jsonObjRate  = rateArray.getJSONObject(j);

							 RateDatedetailVM rdVm = new RateDatedetailVM();
							 System.out.println(jsonObjRate.getString("fulldate"));
							// rdVm.currency = jsonObjRate.getString("currency");
							 rdVm.date = jsonObjRate.getString("date");
							 rdVm.day = jsonObjRate.getString("day");
							 rdVm.fulldate = jsonObjRate.getString("fulldate");
							// rdVm.meal = jsonObjRate.getString("meal");
							 rdVm.month = jsonObjRate.getString("month");
							 rdVm.rate = String.valueOf(jsonObjRate.getDouble("rate"));
							 rdList.add(rdVm);
						 }
						 
						 pInfoVM.rateDatedetail = rdList;
						 
						 
						// pInfoVM.total = jsonObj.getString("total").toString();
						 pList.add(pInfoVM);
						 
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				      
				 }   
				 hotelBookings.setPassengerInfo(pList);
				 DateFormat formatRenew = new SimpleDateFormat("yyyy-MM-dd");
				// List<String> hh= new ArrayList<>();
				 List<Date> newYear = new ArrayList<>();
				 int newDate = 0;
				 int newYearValue = 2015;
				 String[] searchVMcheckIn = searchVM.checkIn.split("-");
				 String searchCheckIn = searchVMcheckIn[2]+"-"+searchVMcheckIn[1]+"-"+searchVMcheckIn[0];
				 String[] searchVMcheckOut = searchVM.checkOut.split("-");
				 String searchCheckOut = searchVMcheckOut[2]+"-"+searchVMcheckOut[1]+"-"+searchVMcheckOut[0];
				 
				/* Date renewDate = null;
				 String dateconcat = newYearValue+"-12-31";
				 try {
						renewDate = formatRenew.parse(dateconcat);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 
				 newYear.add(renewDate);*/
				 for(newDate=0;newDate<3;newDate++){
					 String dateconcat = newYearValue+"-12-31";
					// hh.add(dateconcat);
					 Date renewDate = null;
					try {
						renewDate = formatRenew.parse(dateconcat);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						if(formatRenew.parse(searchCheckIn).before(renewDate) && formatRenew.parse(searchCheckOut).after(renewDate)){
							 newYear.add(renewDate);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					 newYearValue++;
				 }
				 
				 double totalChild =0d;
				 double totalAdult =0d;
				 double totalMealAdd = 0d;
				 List<HotelMealPlan> mealtype = HotelMealPlan.getmealtype(hAmenities.getSupplier_code());
				 for(HotelMealPlan hMealPlan:mealtype){
					 for(Date d:newYear){
						 int hpPlan = HotelMealPlan.getHotelMealCompulsory(hMealPlan.getId(),d);
						if(hpPlan != 0){
							HotelMealPlan hPlan = HotelMealPlan.findById(hpPlan);
							for(PassengerBookingInfoVM p:pList){
								double cal = 0d;
								
								String[] value = p.adult.split(" ");
								cal = Double.valueOf(value[0]) * hPlan.getRate();
								totalAdult = totalAdult + cal;
								for(ChildPolicies child:hPlan.getChild()){
									if(p.childselected != null){
										
									for(ChildselectedVM selectChild:p.childselected){
										if(Integer.parseInt(selectChild.age) > child.getAllowedChildAgeFrom() && Integer.parseInt(selectChild.age) < child.getAllowedChildAgeTo()){
											totalChild = totalChild + child.getCharge();
										}
									}
									}
								}
							
							}
							 System.out.println(totalAdult);
							 System.out.println(totalChild);
							 totalMealAdd = totalMealAdd + (totalAdult + totalChild);
						}
						
					 }
					
					 
				 }
			
				 hProfileVM.setMealCompulsory(totalMealAdd);
			hProfileVM.setHotelBookingDetails(hotelBookings);
			
			//HotelBookingDetailsVM
			
			for (int i = 0; i < dayDiff; i++) {

				System.out.println(supplierid.longValue());
				List<HotelRoomTypes> roomType = HotelRoomTypes
						.getHotelRoomDetailsByIds(supplierid.longValue(),Long.parseLong(searchVM.roomId));
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
						specialsPromotion(roomtyp,format,Integer.parseInt(nationalityId),room.getRoomId(),checkInDate.getTime(),formDate);
						
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
							rateVM.setMinNight(rate.getMinNight());
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
				
	//	findMinRateInHotel(hotellist);
				
		JsonNode onehotelJson = Json.toJson(hotel);
		return ok(hotelBookingInfo.render(onehotelJson));
	}
	return ok(Json.toJson(null));
	
}

public static long findDateDiff(Date toDates, Date formDate, long dayDiff){
	if(toDates.getTime() == formDate.getTime()){
	return dayDiff = 1;
	}else{
		long diff = toDates.getTime() - formDate.getTime();

		return dayDiff = diff / (1000 * 60 * 60 * 24);
	}
}

public static void fillRoomInfo(HotelRoomTypes room,SerachedRoomType roomtyp){
	roomtyp.setRoomId(room.getRoomId());
	roomtyp.setRoomName(room.getRoomType());
	roomtyp.setRoomBed(room.getRoomBed());
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
		//rooChildpoliciVM.setNetRate(rooVm.getNetRate());
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
	hProfileVM.setHotel_email(hAmenities.getHotelEmailAddr());
	
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
	hProfileVM.setCountryCode(hAmenities.getCountry().getCountryCode());
	hProfileVM.setCountryName(hAmenities.getCountry().getCountryName());
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
		hProfileVM.setCheckInTimeType(internalCont.getCheckInType());
		hProfileVM.setCheckOutTimeType(internalCont.getCheckOutType());
		hProfileVM.setCheckOutTime(internalCont.getCheckOutTime());
		hProfileVM.setRoomVoltage(internalCont.getRoomVoltage());
		hProfileVM.setHotelBuiltYear(String.valueOf(hAmenities.getHotelBuiltYear()));
	}
	
	InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(hAmenities.getSupplier_code());
	if(infowiseimagesPath != null){
		if(infowiseimagesPath.getGeneralDescription() != null){
	hProfileVM.setImgDescription(infowiseimagesPath.getGeneralDescription());
		}
	}
	
	AgentRegistration aRegistration= AgentRegistration.getAgentCode(session().get("agent"));
	hProfileVM.setAvailableLimit(aRegistration.getAvailableLimit());
	hProfileVM.setPaymentType(aRegistration.getPaymentMethod());
	hProfileVM.setAgentName(aRegistration.getFirstName()+" "+aRegistration.getLastName());
	
	
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

public static void fillRoomsInHotelInfo1(HotelSearch hotel, List<SerachHotelRoomType> hotelRMlist,int count, Map<Long, SerachedRoomRateDetail> mapRM,Long diffInpromo){
	
	Map<Long, List<SpecialsVM>> mapSpecials = new HashMap<Long, List<SpecialsVM>>();
	Map<Long, Integer> promoMap = new HashMap<Long, Integer>();
	Map<Long, Integer> promoFlatMap = new HashMap<Long, Integer>();
	Map<Long, Integer> promoBirdMap = new HashMap<Long, Integer>();
	Map<Long, Boolean> nonrefundRoom = new HashMap<Long, Boolean>();
	
		int dataVar = 0;
		int countRoom = count;
		boolean refundValue = false;
		List<Integer> arrayCount = new ArrayList<Integer>();
		List<Integer> arrayCountFlat = new ArrayList<Integer>();
		List<Integer> arrayCountBird = new ArrayList<Integer>();
		for (SerachedHotelbyDate date : hotel.hotelbyDate) {
			int newHotel=countRoom; 
			dataVar++;
			int aCount = 0;
			int aCountF = 0;
			int aCountB = 0;
			int fcount =0;
			int bcount =0;
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
					sHotelRoomType.setRoomBed(roomTP.getRoomBed());
					sHotelRoomType.setRoomName(roomTP.getRoomName());
					sHotelRoomType.setDescription(roomTP.getDescription());
					sHotelRoomType.setRoomSize(roomTP.getRoomSize());
					//sHotelRoomType.setExtraBedRate(roomTP.getExtraBedRate());
					sHotelRoomType.setRoomchildPolicies(roomTP.getRoomchildPolicies());
					sHotelRoomType.setMaxAdultsWithchild(roomTP.getMaxAdultsWithchild());
					sHotelRoomType.setChildAllowedFreeWithAdults(roomTP.getChildAllowedFreeWithAdults());
					sHotelRoomType.setExtraBedAllowed(roomTP.getExtraBedAllowed());
					sHotelRoomType.setRoomSuiteType(roomTP.getRoomSuiteType());
					sHotelRoomType.setBreakfastInclude(roomTP.getBreakfastInclude());
					sHotelRoomType.setBreakfastRate(roomTP.getBreakfastRate());
					sHotelRoomType.setChildAge(roomTP.getChildAge());
					for (SpecialsVM specialObj : roomTP.specials){
						
						if(specialObj.promotionType.equals("flatPromotion")){
								fcount = 1;
						}
						
						if(specialObj.promotionType.equals("birdPromotion")){
							bcount = 1;
						}
					
				}

				
		    	arrayCount.add(aCount, roomTP.getPcount());
				arrayCountFlat.add(aCountF, fcount);
				arrayCountBird.add(aCountB, bcount);
					
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
						sRateDetail.setMinNight(rateObj.getMinNight());
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
					int ptotalf = 0;
					int ptotalb = 0;
					ptotal = arrayCount.get(aCount) + roomTP.getPcount();
					for (SpecialsVM specialObj : roomTP.specials){
						if(specialObj.promotionType.equals("nightPromotion")){
						for (SpecialsMarketVM marketObj : specialObj.markets){
							if(diffInpromo < Integer.parseInt(marketObj.stayDays)){
								ptotal = 1;
							}
						 }
						}
						
						if(specialObj.promotionType.equals("flatPromotion")){
							ptotalf = arrayCountFlat.get(aCountF) + roomTP.getPcount();
						}
						
						if(specialObj.promotionType.equals("birdPromotion")){
							ptotalb = arrayCountBird.get(aCountB) + roomTP.getPcount();
						}
						
					}
					arrayCount.set(aCount, ptotal);
					arrayCountFlat.set(aCountF, ptotalf);
					arrayCountBird.set(aCountB, ptotalb);
					
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
								refundValue = true;
								nonrefundRoom.put(roomTP.getRoomId(), rateObj.non_refund);
							}
						}
					}
					mapRM.put(roomTP.getRoomId(), sRateDetail);
					promoMap.put(roomTP.getRoomId(),arrayCount.get(aCount));
					promoFlatMap.put(roomTP.getRoomId(),arrayCountFlat.get(aCountF));
					promoBirdMap.put(roomTP.getRoomId(),arrayCountBird.get(aCountB));
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
				 aCountF++;
				 aCountB++;
				 aCount++;
			}
			
							 
		}
		
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
				diffProm = (int) (diffInpromo/2);
				if(entry.getValue() >= diffProm){
					room.setApplyPromotion(1);
				}else{
					room.setApplyPromotion(0);
				}
			}
		  }
			
			for (Entry<Long, Integer> entry : promoBirdMap.entrySet()) {
				if(room.getRoomId() == entry.getKey()){
					room.setPcount(entry.getValue());
					diffProm = diffInpromo.intValue();
					if(entry.getValue() == 0 && diffProm == 0){
						room.setApplybirdPromotion(0);
					}else if(entry.getValue() >= diffProm){
						room.setApplybirdPromotion(1);
						}else{
							room.setApplybirdPromotion(0);
						}
				}
			  }
			
			for (Entry<Long, Integer> entry : promoFlatMap.entrySet()) {
				if(room.getRoomId() == entry.getKey()){
					room.setPcount(entry.getValue());
					diffProm = diffInpromo.intValue();
					if(entry.getValue() == 0 && diffProm == 0){
						room.setApplyFlatPromotion(0);
					}else if(entry.getValue() >= diffProm){
						room.setApplyFlatPromotion(1);
						}else{
							room.setApplyFlatPromotion(0);
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
public static void allotmentmarketInfo(AllotmentMarket alloMarket,SerachedRoomRateDetail rateVM,DateFormat format,String nationalityId,Long rateid,Date CurrDate){
	int aRoom= 0;
	int flag=0;
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
	rateVM.setAllotmentmarket(Allvm);
}


public static void specialsPromotion(SerachedRoomType roomtyp,DateFormat format,int nationalityId,Long roomId,Date checkInD,Date fromDate) {
	Date date = new Date();
	long dayDiff = 0;
	int count = 0;
	
	List<SpecialsVM> listsp = new ArrayList<>();
	List<Specials> specialsList = Specials.findSpecialByDateandroom(checkInD,roomId);
	for(Specials special : specialsList) {
		
		Date changeDate = fromDate;
		
		SpecialsVM specialsVM = new SpecialsVM();
		specialsVM.id = special.getId();
		specialsVM.fromDate = format.format(special.getFromDate());
		specialsVM.toDate = format.format(special.getToDate());
		specialsVM.promotionName = special.getPromotionName();
		specialsVM.supplierCode = special.getSupplierCode();
		specialsVM.promotionType = special.getPromotionType();
		
		    								
	List<SpecialsMarket> marketList = SpecialsMarket.findBySpecialsIdnationality(special.getId(),nationalityId);
		for(SpecialsMarket market : marketList) {
			SpecialsMarketVM vm = new SpecialsMarketVM();
			vm.combined = market.isCombined();
			vm.multiple = market.isMultiple();
			vm.payDays = market.getPayDays();
			vm.stayDays = market.getStayDays();
			vm.typeOfStay = market.getTypeOfStay();
			vm.earlyBird = market.getEarlyBird();
			vm.earlyBirdDisount = market.getEarlyBirdDisount();
			vm.earlyBirdRateCalculat = market.getEarlyBirdRateCalculat();
			vm.applyToMarket = market.getApplyToMarket();
			vm.breakfast = market.isBreakfast();
			vm.adultRate = market.getAdultRate();
			vm.flatRate = market.getFlatRate();
			vm.childRate = market.getChildRate();
			vm.apply = "1"; 
			
			vm.id = market.getId();
			
				if(special.getPromotionType().equals("birdPromotion")){
					Calendar c = Calendar.getInstance(); 
					c.setTime(changeDate); 
					c.add(Calendar.DATE, Integer.parseInt(market.getEarlyBird()) * -1);
					changeDate = c.getTime();
					if(date.getTime() < changeDate.getTime()){
						specialsVM.markets.add(vm);
					}
				}else{
					specialsVM.markets.add(vm);
				}
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
	 
	 
}
