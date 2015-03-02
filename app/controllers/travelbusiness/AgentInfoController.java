package controllers.travelbusiness;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.domain.HotelBookingDates;
import com.travelportal.domain.HotelBookingDetails;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.rooms.RoomAllotedRateWise;
import com.travelportal.vm.AgentRegisVM;
import com.travelportal.vm.BookingDatesVM;
import com.travelportal.vm.HotelBookDetailsVM;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.travelbusiness.agentBookingInfo;

public class AgentInfoController extends Controller {

	
	  @Transactional(readOnly=true)
    public static Result AgentBookingInfo(){ 
		  			
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	    	 List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
	    		long totalPages = 0;
	    		int currentPage = 1;
	    		
	    		List<HotelBookingDetails> hoteDetails = null;
	    		
	    			totalPages = HotelBookingDetails.getAgentBookingTotal(5,Long.parseLong(session().get("agent")));
	    			hoteDetails = HotelBookingDetails.getfindByAgent(Long.parseLong(session().get("agent")), currentPage, 5, totalPages);
	    		
				
			for(HotelBookingDetails hBookingDetails:hoteDetails){
				
				HotelBookDetailsVM hDetailsVM= new HotelBookDetailsVM();
				hDetailsVM.setId(hBookingDetails.getId());
				hDetailsVM.setAdult(hBookingDetails.getAdult());
				hDetailsVM.setCheckIn(format.format(hBookingDetails.getCheckIn()));
				hDetailsVM.setCheckOut(format.format(hBookingDetails.getCheckOut()));
				if(hBookingDetails.getCityCode() != null){
				hDetailsVM.setCityCode(hBookingDetails.getCityCode().getCityCode());
				hDetailsVM.setCityNm(hBookingDetails.getCityCode().getCityName());
				}
				hDetailsVM.setHotelNm(hBookingDetails.getHotelNm());
				hDetailsVM.setHotelAddr(hBookingDetails.getHotelAddr());
				hDetailsVM.setNoOfroom(hBookingDetails.getNoOfroom());
				hDetailsVM.setTotalNightStay(hBookingDetails.getTotalNightStay());
				hDetailsVM.setRoom_status(hBookingDetails.getRoom_status());
				
				List<AgentRegisVM>aList = new ArrayList<>();
				if(hBookingDetails.getAgentId() != null){
				AgentRegistration agent = AgentRegistration.getAgentCode(hBookingDetails.getAgentId().toString());
				AgentRegisVM agRegisVM=new AgentRegisVM();
				agRegisVM.setAgentCode(agent.getAgentCode());
				agRegisVM.setFirstName(agent.getFirstName());
				agRegisVM.setLastName(agent.getLastName());
				agRegisVM.setCompanyName(agent.getCompanyName());
				
				aList.add(agRegisVM);
				hDetailsVM.setAgent(aList);
				}
					
				if(hBookingDetails.getCountry()!=null){
				hDetailsVM.setCountryId(hBookingDetails.getCountry().getCountryCode());
				hDetailsVM.setCountryNm(hBookingDetails.getCountry().getCountryName());
				}
				hDetailsVM.setRoomId(hBookingDetails.getRoomId());
				hDetailsVM.setRoomNm(hBookingDetails.getRoomName());
				if(hBookingDetails.getNationality()!=null){
				hDetailsVM.setNationality(hBookingDetails.getNationality().getCountryCode());
				hDetailsVM.setNationalityNm(hBookingDetails.getNationality().getNationality());
				}
				hDetailsVM.setPayDays_inpromotion(hBookingDetails.getPayDays_inpromotion());
				hDetailsVM.setPromotionname(hBookingDetails.getPromotionname());
				if(hBookingDetails.getStartRating() != null){
				hDetailsVM.setStartRating(hBookingDetails.getStartRating().getId());
				hDetailsVM.setStartRatingNm(hBookingDetails.getStartRating().getstarRatingTxt());
				}
				hDetailsVM.setSupplierCode(hBookingDetails.getSupplierCode());
				hDetailsVM.setSupplierNm(hBookingDetails.getSupplierNm());
				hDetailsVM.setTotal(hBookingDetails.getTotal());
				hDetailsVM.setTravelleraddress(hBookingDetails.getTravelleraddress());
				hDetailsVM.setTravelleremail(hBookingDetails.getTravelleremail());
				hDetailsVM.setTravellerfirstname(hBookingDetails.getTravellerfirstname());
				hDetailsVM.setTravellerlastname(hBookingDetails.getTravellerlastname());
				hDetailsVM.setTravellerphnaumber(hBookingDetails.getTravellerphnaumber());
				hDetailsVM.setTravellercountry(hBookingDetails.getTravellercountry().getCountryCode());
				hDetailsVM.setTypeOfStay_inpromotion(hBookingDetails.getTypeOfStay_inpromotion());
				aDetailsVMs.add(hDetailsVM);
			}
			//return ok(Json.toJson(aDetailsVMs));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("totalPages", totalPages);
			map.put("currentPage", currentPage);
			map.put("results", aDetailsVMs);
			
			JsonNode onehotelJson = Json.toJson(map);
			return ok(agentBookingInfo.render(onehotelJson));
			//return ok(agentBookingInfo.render());
		  
	  }
		
	  
	 
	  @Transactional(readOnly=true)
		public static Result getagentInfo(int currentPage,String fromDate,String toDate,String status) {
			
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	    	 List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
	    		long totalPages = 0;
	    		//String status = "available";
	    		List<HotelBookingDetails> hoteDetails = null;
	    		
	    		if(fromDate.equals("1") && toDate.equals("1") && status.equals("1")){
	    		
	    			totalPages = HotelBookingDetails.getAllagentBookingTotal(5, Long.parseLong(session().get("agent")));
	    			hoteDetails = HotelBookingDetails.getfindByagent(Long.parseLong(session().get("agent")), currentPage, 5, totalPages);
	    		}else if(!fromDate.equals("1") && !toDate.equals("1")){
	    			try {
	    				totalPages = HotelBookingDetails.getAllagentTotalDateWise(5 , Long.parseLong(session().get("agent")) , format.parse(fromDate) , format.parse(toDate) , status);
	    			} catch (ParseException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    		
	    		try {
	    			hoteDetails = HotelBookingDetails.getfindByagentDateWise(Long.parseLong(session().get("agent")), format.parse(fromDate) , format.parse(toDate), currentPage, 5, totalPages , status);
	    		} catch (ParseException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		}else if(fromDate.equals("1") && toDate.equals("1") && !status.equals("1")){
	    			
	    				totalPages = HotelBookingDetails.getTotalDateWiseAgentWise(5 , Long.parseLong(session().get("agent")), status);
	    		
	    			hoteDetails = HotelBookingDetails.getfindByDateWiseAgentWise(Long.parseLong(session().get("agent")), currentPage, 5, totalPages , status);
	    		
	    		}
				
			for(HotelBookingDetails hBookingDetails:hoteDetails){
				
				HotelBookDetailsVM hDetailsVM= new HotelBookDetailsVM();
				hDetailsVM.setId(hBookingDetails.getId());
				hDetailsVM.setAdult(hBookingDetails.getAdult());
				hDetailsVM.setCheckIn(format.format(hBookingDetails.getCheckIn()));
				hDetailsVM.setCheckOut(format.format(hBookingDetails.getCheckOut()));
				if(hBookingDetails.getCityCode() != null){
				hDetailsVM.setCityCode(hBookingDetails.getCityCode().getCityCode());
				hDetailsVM.setCityNm(hBookingDetails.getCityCode().getCityName());
				}
				hDetailsVM.setHotelNm(hBookingDetails.getHotelNm());
				hDetailsVM.setHotelAddr(hBookingDetails.getHotelAddr());
				hDetailsVM.setNoOfroom(hBookingDetails.getNoOfroom());
				hDetailsVM.setTotalNightStay(hBookingDetails.getTotalNightStay());
				hDetailsVM.setRoom_status(hBookingDetails.getRoom_status());
				
				List<AgentRegisVM>aList = new ArrayList<>();
				if(hBookingDetails.getAgentId() != null){
				AgentRegistration agent = AgentRegistration.getAgentCode(hBookingDetails.getAgentId().toString());
				AgentRegisVM agRegisVM=new AgentRegisVM();
				agRegisVM.setAgentCode(agent.getAgentCode());
				agRegisVM.setFirstName(agent.getFirstName());
				agRegisVM.setLastName(agent.getLastName());
				agRegisVM.setCompanyName(agent.getCompanyName());
				
				aList.add(agRegisVM);
				hDetailsVM.setAgent(aList);
				}
					
				if(hBookingDetails.getCountry()!=null){
				hDetailsVM.setCountryId(hBookingDetails.getCountry().getCountryCode());
				hDetailsVM.setCountryNm(hBookingDetails.getCountry().getCountryName());
				}
				hDetailsVM.setRoomId(hBookingDetails.getRoomId());
				hDetailsVM.setRoomNm(hBookingDetails.getRoomName());
				if(hBookingDetails.getNationality()!=null){
				hDetailsVM.setNationality(hBookingDetails.getNationality().getCountryCode());
				hDetailsVM.setNationalityNm(hBookingDetails.getNationality().getNationality());
				}
				hDetailsVM.setPayDays_inpromotion(hBookingDetails.getPayDays_inpromotion());
				hDetailsVM.setPromotionname(hBookingDetails.getPromotionname());
				if(hBookingDetails.getStartRating() != null){
				hDetailsVM.setStartRating(hBookingDetails.getStartRating().getId());
				hDetailsVM.setStartRatingNm(hBookingDetails.getStartRating().getstarRatingTxt());
				}
				hDetailsVM.setSupplierCode(hBookingDetails.getSupplierCode());
				hDetailsVM.setSupplierNm(hBookingDetails.getSupplierNm());
				hDetailsVM.setTotal(hBookingDetails.getTotal());
				hDetailsVM.setTravelleraddress(hBookingDetails.getTravelleraddress());
				hDetailsVM.setTravelleremail(hBookingDetails.getTravelleremail());
				hDetailsVM.setTravellerfirstname(hBookingDetails.getTravellerfirstname());
				hDetailsVM.setTravellerlastname(hBookingDetails.getTravellerlastname());
				hDetailsVM.setTravellerphnaumber(hBookingDetails.getTravellerphnaumber());
				hDetailsVM.setTravellercountry(hBookingDetails.getTravellercountry().getCountryCode());
				hDetailsVM.setTypeOfStay_inpromotion(hBookingDetails.getTypeOfStay_inpromotion());
				aDetailsVMs.add(hDetailsVM);
			}
			//return ok(Json.toJson(aDetailsVMs));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("totalPages", totalPages);
			map.put("currentPage", currentPage);
			map.put("results", aDetailsVMs);
			return ok(Json.toJson(map));
		}
		
	  
	  @Transactional
		public static Result getbookingcancel(long id){
		  int count= 0;
			HotelBookingDetails hBookingDetails = HotelBookingDetails.findBookingById(id);
			/*hBookingDetails.setRoom_status("cancel");
			hBookingDetails.merge();*/
			
			List<HotelBookingDates> hotelBookingDates = HotelBookingDates.getDateBybookingId(id);
			//RoomAllotedRateWise rateWise = RoomAllotedRateWise.findByRateId(hBookingDetails.getRate().getId());
			for(HotelBookingDates hDates:hotelBookingDates){
				RoomAllotedRateWise rateWise = RoomAllotedRateWise.findByRateIdandDate(hBookingDetails.getRate().getId(), hDates.getBookingDate());
				if(rateWise != null){
					count = rateWise.getRoomCount() - hBookingDetails.getNoOfroom();
					rateWise.setRoomCount(count);
					rateWise.merge();
					
				}
				
			}
			
			
			return ok();
			
		}
	  
}
