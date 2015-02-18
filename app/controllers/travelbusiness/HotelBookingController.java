package controllers.travelbusiness;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelBookingDates;
import com.travelportal.domain.HotelBookingDetails;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RoomAllotedRateWise;
import com.travelportal.vm.AgentRegistrationVM;
import com.travelportal.vm.HotelSearch;
import com.travelportal.vm.SearchHotelValueVM;
import com.travelportal.vm.SearchRateDetailsVM;
import com.travelportal.vm.SerachHotelRoomType;
import com.travelportal.vm.SerachedHotelbyDate;
import com.travelportal.vm.SerachedRoomRateDetail;
import com.travelportal.vm.SerachedRoomType;
import com.travelportal.vm.SpecialsMarketVM;
import com.travelportal.vm.SpecialsVM;

import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class HotelBookingController extends Controller {

	
	@Transactional(readOnly=false)
	public static Result saveHotelBookingInfo() {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		/*JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, HotelSearch.class);
		HotelSearch hSearch = Json.fromJson(json, HotelSearch.class);*/
		
		Form<HotelSearch> HotelForm = Form.form(HotelSearch.class).bindFromRequest();
		HotelSearch searchVM = HotelForm.get();
		System.out.println("_+_+_+_+_+_+_+_+_+_+_+_");
		System.out.println("SESSION VALUE   "+session().get("agent"));
		System.out.println("_+_+_+_+_+_+_+_+_+_+_+_");
		HotelBookingDetails hBookingDetails=new HotelBookingDetails();
		hBookingDetails.setHotelNm(searchVM.getHotelNm());
		hBookingDetails.setHotelAddr(searchVM.getHotelAddr());
		hBookingDetails.setSupplierCode(searchVM.getSupplierCode());
		hBookingDetails.setAgentId(Long.parseLong(session().get("agent")));
		hBookingDetails.setSupplierNm(searchVM.getSupplierNm());
		hBookingDetails.setTotalNightStay(searchVM.getDatediff());
		try {
			hBookingDetails.setCheckIn(format.parse(searchVM.getCheckIn()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			hBookingDetails.setCheckOut(format.parse(searchVM.getCheckOut()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hBookingDetails.setNationality(Country.getCountryByCode(searchVM.getNationality()));
		
		//hBookingDetails.setCountry(Country.getCountryByCode(searchVM.getCountryCode()));
		hBookingDetails.setCityCode(City.getCityByCode(searchVM.getCityCode()));
		
		hBookingDetails.setStartRating(HotelStarRatings.getHotelRatingsById(searchVM.getStartRating()));
		hBookingDetails.setCurrencyId(Currency.getCurrencyByCode(searchVM.currencyId));
		
		
		hBookingDetails.setAdult(searchVM.hotelBookingDetails.getAdult());
		hBookingDetails.setNoOfroom(Integer.parseInt(searchVM.hotelBookingDetails.getNoOfroom()));
		hBookingDetails.setTotal(Double.parseDouble(searchVM.hotelBookingDetails.getTotal()));
		hBookingDetails.setTravellerfirstname(searchVM.hotelBookingDetails.getTravellerfirstname());
		hBookingDetails.setTravelleremail(searchVM.hotelBookingDetails.getTravelleremail());
		
		hBookingDetails.setTravellerlastname(searchVM.hotelBookingDetails.getTravellerlastname());
		hBookingDetails.setTravelleraddress(searchVM.hotelBookingDetails.getTravelleraddress());

		hBookingDetails.setTravellercountry(Country.getCountryByCode(Integer.parseInt(searchVM.hotelBookingDetails.getTravellercountry())));
		hBookingDetails.setTravellerphnaumber(searchVM.hotelBookingDetails.getTravellerphnaumber());
		
		/*AgentRegistration agent = AgentRegistration.findagentinfo(loginID, password, agentId);
    	session().put("agent", agent.getAgentCode());
		if(agent != null) {
			System.out.println("SESSION VALUE   "+session().get("agent"));
			AgentRegistrationVM aVm=new AgentRegistrationVM(agent);
		}*/
		//(searchVM.hotelBookingDetails.getTravellerphnaumber()));
		
		for(SerachHotelRoomType byRoom:searchVM.hotelbyRoom){
			hBookingDetails.setRoomId(byRoom.roomId);
			hBookingDetails.setRoomName(byRoom.roomName);
			if(byRoom.specials != null){
			for(SpecialsVM speci:byRoom.specials){
				hBookingDetails.setPromotionname(speci.promotionName);
				for(SpecialsMarketVM smarket:speci.markets){
					hBookingDetails.setPayDays_inpromotion(Integer.parseInt(smarket.payDays));
					hBookingDetails.setStayDays_inpromotion(Integer.parseInt(smarket.stayDays));
					hBookingDetails.setTypeOfStay_inpromotion(smarket.typeOfStay);
				}
			}
		  }
		}
		int flag = 0;
		for(SerachedHotelbyDate date:searchVM.hotelbyDate){
			if(date.roomType != null){
			for(SerachedRoomType roomTP:date.roomType){
				for(SerachedRoomRateDetail rateObj:roomTP.hotelRoomRateDetail){
					if(rateObj.flag == 1){
						flag = 1;
					}
					
				}
			}
		  }	
			
		}
		if(flag == 1){
			hBookingDetails.setRoom_status("on request");
		}else{
			for(SerachedHotelbyDate date:searchVM.hotelbyDate){
				if(date.roomType != null){
				for(SerachedRoomType roomTP:date.roomType){
					for(SerachedRoomRateDetail rateObj:roomTP.hotelRoomRateDetail){
						if(rateObj.allotmentmarket.allocation == 3){
						if(rateObj.availableRoom < Integer.parseInt(searchVM.hotelBookingDetails.getNoOfroom())){
							hBookingDetails.setRoom_status("on request");
						}else{
							hBookingDetails.setRoom_status("available");
						}
						}else{
							hBookingDetails.setRoom_status("available");
						}
					}
				}
			}
			}
			
		}
		
		
		hBookingDetails.save();
		
		int applyforroom = 0;
		
		for(SerachedHotelbyDate date:searchVM.hotelbyDate){
			HotelBookingDates hBookingDates=new HotelBookingDates();
			try {
				hBookingDates.setBookingDate(format.parse(date.getDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(date.roomType != null){
			for(SerachedRoomType roomTP:date.roomType){
				for(SerachedRoomRateDetail rateObj:roomTP.hotelRoomRateDetail){
					
					for(SearchRateDetailsVM detailsVM:rateObj.rateDetails){
						hBookingDates.setBookDateRate(detailsVM.rateValue);
						hBookingDates.setMealtypeName(detailsVM.mealTypeName);
					}
					
				}
			}
		}
			hBookingDates.setBookingId(hBookingDetails.findBookingId());
			hBookingDates.save();
			
			
			//if(flag == 1){
			if(date.roomType != null){
				for(SerachedRoomType roomTP:date.roomType){
					for(SerachedRoomRateDetail rateObj:roomTP.hotelRoomRateDetail){
						if(rateObj.allotmentmarket.allocation == 3){
							//if(rateObj.availableRoom >= Integer.parseInt(searchVM.hotelBookingDetails.getNoOfroom())){
							if(rateObj.availableRoom < Integer.parseInt(searchVM.hotelBookingDetails.getNoOfroom())){
								applyforroom = 1;
						}	
					 }
				 }
			 }
		  }
		}
		
		if(applyforroom != 1){
			RoomAllotedRateWise rAllotedRateWise = null;
			for(SerachedHotelbyDate date:searchVM.hotelbyDate){
				if(date.roomType != null){
					for(SerachedRoomType roomTP:date.roomType){
						for(SerachedRoomRateDetail rateObj:roomTP.hotelRoomRateDetail){
							if(rateObj.allotmentmarket.allocation == 3){
								try {
									rAllotedRateWise= RoomAllotedRateWise.findByRateIdandDate(rateObj.id, format.parse(date.getDate()));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								if(rAllotedRateWise == null){
									RoomAllotedRateWise rWise=new RoomAllotedRateWise();
									try {
										rWise.setAllowedRateDate(format.parse(date.getDate()));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									rWise.setRate(RateMeta.findById(rateObj.id));
									rWise.setRoomCount(Integer.parseInt(searchVM.hotelBookingDetails.getNoOfroom()));
									rWise.save();
									
									
								}else{
									int rcount =  rAllotedRateWise.getRoomCount() + Integer.parseInt(searchVM.hotelBookingDetails.noOfroom);
									rAllotedRateWise.setRoomCount(rcount);
									rAllotedRateWise.merge();
								}
							}
						}	
					}
				}
			}
		}
		
		return ok();
		
	}
	 
}
