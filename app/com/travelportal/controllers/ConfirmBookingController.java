package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.travelportal.domain.HotelBookingDates;
import com.travelportal.domain.HotelBookingDetails;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.vm.AgentRegisVM;
import com.travelportal.vm.AgentRegistrationVM;
import com.travelportal.vm.BookingDatesVM;
import com.travelportal.vm.HotelBookDetailsVM;
import com.travelportal.vm.HotelBookingDetailsVM;
import com.travelportal.vm.HotelSearch;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class ConfirmBookingController extends Controller {

	@Transactional(readOnly=true)
	public static Result getbookingInfo(long supplierCode,int currentPage,String fromDate,String toDate,String agentcompanyNm) {
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    	 List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
    		long totalPages = 0;
    		String status = "available";
    		List<HotelBookingDetails> hoteDetails = null;
    		
    		if(fromDate.equals("1") && toDate.equals("1") && agentcompanyNm.equals("1")){
    		
    			totalPages = HotelBookingDetails.getAllBookingTotal(5, supplierCode,status);
    			hoteDetails = HotelBookingDetails.getfindBysupplier(supplierCode, currentPage, 5, totalPages, status);
    		}else if(!fromDate.equals("1") && !toDate.equals("1")){
    			try {
    				totalPages = HotelBookingDetails.getAllBookingTotalDateWise(5 , supplierCode , format.parse(fromDate) , format.parse(toDate) , agentcompanyNm, status);
    			} catch (ParseException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		
    		try {
    			hoteDetails = HotelBookingDetails.getfindBysupplierDateWise(supplierCode, format.parse(fromDate) , format.parse(toDate), currentPage, 5, totalPages ,agentcompanyNm, status);
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		}else if(fromDate.equals("1") && toDate.equals("1") && !agentcompanyNm.equals("1")){
    			
    				totalPages = HotelBookingDetails.getAllBookingTotalDateWiseAgentWise(5 , supplierCode , agentcompanyNm, status);
    		
    			hoteDetails = HotelBookingDetails.getfindBysupplierDateWiseAgentWise(supplierCode, currentPage, 5, totalPages ,agentcompanyNm, status);
    		
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
	
	
	@Transactional(readOnly=true)
	public static Result getonrequestInfo(long supplierCode,int currentPage,String fromDate,String toDate,String agentNm) {

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	 List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
	 long totalPages = 0;
	 String status = "on request";
		List<HotelBookingDetails> hoteDetails = null;
		
		if(fromDate.equals("1") && toDate.equals("1") && agentNm.equals("1")){
	 
			totalPages = HotelBookingDetails.getAllBookingTotal(5, supplierCode,status);
			hoteDetails =  HotelBookingDetails.getfindBysupplier(supplierCode, currentPage, 5, totalPages, status);
		}else if(!fromDate.equals("1") && !toDate.equals("1")){
			try {
				totalPages = HotelBookingDetails.getAllBookingTotalDateWise(5 , supplierCode , format.parse(fromDate) , format.parse(toDate) , agentNm, status);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			hoteDetails = HotelBookingDetails.getfindBysupplierDateWise(supplierCode, format.parse(fromDate) , format.parse(toDate), currentPage, 5, totalPages ,agentNm, status);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else if(fromDate.equals("1") && toDate.equals("1") && !agentNm.equals("1")){
			
				totalPages = HotelBookingDetails.getAllBookingTotalDateWiseAgentWise(5 , supplierCode , agentNm, status);
		
			hoteDetails = HotelBookingDetails.getfindBysupplierDateWiseAgentWise(supplierCode, currentPage, 5, totalPages ,agentNm, status);
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		map.put("results", aDetailsVMs);
		return ok(Json.toJson(map));
	}	


	@Transactional(readOnly=true)
	public static Result getrejectInfo(long supplierCode,int currentPage,String fromDate,String toDate,String agentNm) {
		

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	 List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
	 long totalPages = 0;
		List<HotelBookingDetails> hoteDetails = null;
		String status = "rejected";
		if(fromDate.equals("1") && toDate.equals("1") && agentNm.equals("1")){
	 
			totalPages = HotelBookingDetails.getAllBookingTotal(5, supplierCode,status);
			hoteDetails =  HotelBookingDetails.getfindBysupplier(supplierCode, currentPage, 5, totalPages,status);
		}else if(!fromDate.equals("1") && !toDate.equals("1")){
			try {
				totalPages = HotelBookingDetails.getAllBookingTotalDateWise(5 , supplierCode , format.parse(fromDate) , format.parse(toDate) , agentNm, status);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			hoteDetails = HotelBookingDetails.getfindBysupplierDateWise(supplierCode, format.parse(fromDate) , format.parse(toDate), currentPage, 5, totalPages ,agentNm , status);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else if(fromDate.equals("1") && toDate.equals("1") && !agentNm.equals("1")){
			
				totalPages = HotelBookingDetails.getAllBookingTotalDateWiseAgentWise(5 , supplierCode , agentNm , status);
		
			hoteDetails = HotelBookingDetails.getfindBysupplierDateWiseAgentWise(supplierCode, currentPage, 5, totalPages ,agentNm, status);
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		map.put("results", aDetailsVMs);
		return ok(Json.toJson(map));
		
	}
	
	
	@Transactional(readOnly=true)
	public static Result getcancelInfo(long supplierCode,int currentPage,String fromDate,String toDate,String agentNm) {
		

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	 List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
	 long totalPages = 0;
		List<HotelBookingDetails> hoteDetails = null;
		String status = "cancel";
		if(fromDate.equals("1") && toDate.equals("1") && agentNm.equals("1")){
	 
			totalPages = HotelBookingDetails.getAllBookingTotal(5, supplierCode,status);
			hoteDetails =  HotelBookingDetails.getfindBysupplier(supplierCode, currentPage, 5, totalPages,status);
		}else if(!fromDate.equals("1") && !toDate.equals("1")){
			try {
				totalPages = HotelBookingDetails.getAllBookingTotalDateWise(5 , supplierCode , format.parse(fromDate) , format.parse(toDate) , agentNm, status);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			hoteDetails = HotelBookingDetails.getfindBysupplierDateWise(supplierCode, format.parse(fromDate) , format.parse(toDate), currentPage, 5, totalPages ,agentNm , status);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else if(fromDate.equals("1") && toDate.equals("1") && !agentNm.equals("1")){
			
				totalPages = HotelBookingDetails.getAllBookingTotalDateWiseAgentWise(5 , supplierCode , agentNm , status);
		
			hoteDetails = HotelBookingDetails.getfindBysupplierDateWiseAgentWise(supplierCode, currentPage, 5, totalPages ,agentNm, status);
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		map.put("results", aDetailsVMs);
		return ok(Json.toJson(map));
		
	}
	
	
	
	@Transactional(readOnly=true)
	public static Result getbookDateWise(long id) {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<BookingDatesVM> aList = new ArrayList<>();
		
		List<HotelBookingDates> hBookingDates =  HotelBookingDates.getDateBybookingId(id);
		for(HotelBookingDates bookingDates:hBookingDates){
			BookingDatesVM hBookingDatesVM = new BookingDatesVM();
			hBookingDatesVM.setId(bookingDates.getId());
			if(bookingDates.getBookDateRate() != null){
			hBookingDatesVM.setRate(bookingDates.getBookDateRate());
			}
			hBookingDatesVM.setCdate(format.format(bookingDates.getBookingDate()));
			if(bookingDates.getMealtypeName() != null){
			hBookingDatesVM.setMealtype(bookingDates.getMealtypeName());
			}
			aList.add(hBookingDatesVM);
			
		}
		return ok(Json.toJson(aList));
		
	}
	
	@Transactional
	public static Result getRejectBooking(long id){
		
		HotelBookingDetails hBookingDetails = HotelBookingDetails.findBookingById(id);
		hBookingDetails.setRoom_status("rejected");
		hBookingDetails.merge();
		return ok();
		
	}
	@Transactional
	public static Result getconfBooking(long id){
		HotelBookingDetails hBookingDetails = HotelBookingDetails.findBookingById(id);
		hBookingDetails.setRoom_status("available");
		hBookingDetails.merge();
		return ok();
		
	}
	
	
}
