package com.travelportal.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.travelportal.domain.HotelBookingDetails;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.vm.AgentRegisVM;
import com.travelportal.vm.HotelBookDetailsVM;

public class ManageBookingController extends Controller {

	@Transactional(readOnly=true)
	public static Result getAllAgentInfo(long agentCode){ 

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
		long totalPages = 0;
		int currentPage = 1;
		String status = "available";

		List<HotelBookingDetails> hoteDetails = null;

		totalPages = HotelBookingDetails.getAdminAgentBookingTotal(15, agentCode, status);
		hoteDetails = HotelBookingDetails.getAdminfindByAgent(agentCode, currentPage, 15, totalPages, status);


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
			hDetailsVM.setPayment(hBookingDetails.getPayment());
			hDetailsVM.setTravelleraddress(hBookingDetails.getTravelleraddress());
			hDetailsVM.setTravelleremail(hBookingDetails.getTravelleremail());
			hDetailsVM.setCurrencyId(hBookingDetails.getCurrencyId().getCurrencyCode());
			hDetailsVM.setCurrencyNm(hBookingDetails.getCurrencyId().getCurrencyName());
			hDetailsVM.setTravellerfirstname(hBookingDetails.getTravellerfirstname());
			hDetailsVM.setTravellerlastname(hBookingDetails.getTravellerlastname());
			hDetailsVM.setTravellerphnaumber(hBookingDetails.getTravellerphnaumber());
			hDetailsVM.setTravellercountry(hBookingDetails.getTravellercountry().getCountryCode());
			hDetailsVM.setTypeOfStay_inpromotion(hBookingDetails.getTypeOfStay_inpromotion());
			hDetailsVM.setNonRefund(hBookingDetails.getNonRefund());

			hDetailsVM.setNonSmokingRoom(hBookingDetails.getNonSmokingRoom());
			hDetailsVM.setTwinBeds(hBookingDetails.getTwinBeds());
			hDetailsVM.setLateCheckout(hBookingDetails.getLateCheckout());
			hDetailsVM.setLargeBed(hBookingDetails.getLargeBed());
			hDetailsVM.setHighFloor(hBookingDetails.getHighFloor());
			hDetailsVM.setEarlyCheckin(hBookingDetails.getEarlyCheckin());
			hDetailsVM.setAirportTransfer(hBookingDetails.getAirportTransfer());
			hDetailsVM.setAirportTransferInfo(hBookingDetails.getAirportTransferInfo());
			hDetailsVM.setEnterComments(hBookingDetails.getEnterComments());
			hDetailsVM.setSmokingRoom(hBookingDetails.getSmokingRoom());
			hDetailsVM.setHandicappedRoom(hBookingDetails.getHandicappedRoom());
			hDetailsVM.setWheelchair(hBookingDetails.getWheelchair());
			
			aDetailsVMs.add(hDetailsVM);
		}
		//return ok(Json.toJson(aDetailsVMs));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		map.put("results", aDetailsVMs);

		//JsonNode onehotelJson = Json.toJson(map);
		return ok(Json.toJson(map));

	}

	
	@Transactional(readOnly=true)
	public static Result getagentInfobyAll(int currentPage,String checkIn,String checkOut,String guest,String status,Long bookingId,Long agentCode) {

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
		System.out.println("check in="+checkIn+"CheckOut="+bookingId);


		long totalPages = 0;
		List<HotelBookingDetails> hoteDetails = null;

		if(!checkIn.equals("undefined") && !checkOut.equals("undefined")&&!guest.equals("undefined") && bookingId == 0){
			try {
				totalPages = HotelBookingDetails.getAllagentTotalDateWise1(15 , agentCode, format.parse(checkIn) , format.parse(checkOut) , status,guest);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				hoteDetails = HotelBookingDetails.getfindByagentDateWise1(agentCode, format.parse(checkIn) , format.parse(checkOut), currentPage, 15, totalPages , status,guest);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(checkIn.equals("undefined") && checkOut.equals("undefined") && !guest.equals("undefined") && bookingId == 0){

			totalPages = HotelBookingDetails.getTotalDateWiseAgentWise1(10 ,agentCode, status,guest);

			hoteDetails = HotelBookingDetails.getfindByDateWiseAgentWise1(agentCode, currentPage, 15, totalPages , status,guest);

		}
		else if(guest.equals("undefined")&&!checkIn.equals("undefined") && !checkOut.equals("undefined") && bookingId == 0)
		{

			try {
				totalPages = HotelBookingDetails.getTotalDateWiseAgentWise11(15 , agentCode, status,format.parse(checkIn),format.parse(checkOut));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				hoteDetails = HotelBookingDetails.getfindByDateWiseAgentWise11(agentCode, currentPage, 15, totalPages , status,format.parse(checkIn) , format.parse(checkOut));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else if(bookingId != 0){
			totalPages = HotelBookingDetails.getTotalBookingIdWise(15 , agentCode, status, bookingId);
			hoteDetails = HotelBookingDetails.getfindByBookingId(agentCode, currentPage, 15, totalPages , status,bookingId);
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
			hDetailsVM.setPayment(hBookingDetails.getPayment());
			hDetailsVM.setCurrencyId(hBookingDetails.getCurrencyId().getCurrencyCode());
			hDetailsVM.setCurrencyNm(hBookingDetails.getCurrencyId().getCurrencyName());
			hDetailsVM.setTravelleraddress(hBookingDetails.getTravelleraddress());
			hDetailsVM.setTravelleremail(hBookingDetails.getTravelleremail());
			System.out.println("currency="+hBookingDetails.getCurrencyId().getCurrencyCode());
			hDetailsVM.setTravellerfirstname(hBookingDetails.getTravellerfirstname());
			hDetailsVM.setTravellerlastname(hBookingDetails.getTravellerlastname());
			hDetailsVM.setTravellerphnaumber(hBookingDetails.getTravellerphnaumber());
			hDetailsVM.setTravellercountry(hBookingDetails.getTravellercountry().getCountryCode());
			hDetailsVM.setTypeOfStay_inpromotion(hBookingDetails.getTypeOfStay_inpromotion());
			hDetailsVM.setNonRefund(hBookingDetails.getNonRefund());
			hDetailsVM.setNonSmokingRoom(hBookingDetails.getNonSmokingRoom());
			hDetailsVM.setTwinBeds(hBookingDetails.getTwinBeds());
			hDetailsVM.setLateCheckout(hBookingDetails.getLateCheckout());
			hDetailsVM.setLargeBed(hBookingDetails.getLargeBed());
			hDetailsVM.setHighFloor(hBookingDetails.getHighFloor());
			hDetailsVM.setEarlyCheckin(hBookingDetails.getEarlyCheckin());
			hDetailsVM.setAirportTransfer(hBookingDetails.getAirportTransfer());
			hDetailsVM.setAirportTransferInfo(hBookingDetails.getAirportTransferInfo());
			hDetailsVM.setEnterComments(hBookingDetails.getEnterComments());
			hDetailsVM.setSmokingRoom(hBookingDetails.getSmokingRoom());
			hDetailsVM.setHandicappedRoom(hBookingDetails.getHandicappedRoom());
			hDetailsVM.setWheelchair(hBookingDetails.getWheelchair());
			
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
	public static Result getBookingPaymentInfo(long bookingId,String payment) {
		HotelBookingDetails hBookingDetails = HotelBookingDetails.findBookingById(bookingId);
		if(payment.equals("true")){
			hBookingDetails.setPayment("yes");
		}else{
			hBookingDetails.setPayment("no");
		}
		
		hBookingDetails.merge();
		
		return ok();
		
	}

}


