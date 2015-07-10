package controllers.travelbusiness;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.travelbusiness.agentBookingInfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.travelportal.domain.HotelBookingDates;
import com.travelportal.domain.HotelBookingDetails;
import com.travelportal.domain.RoomRegiterBy;
import com.travelportal.domain.RoomRegiterByChild;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RoomAllotedRateWise;
import com.travelportal.vm.AgentRegisVM;
import com.travelportal.vm.ChildselectedVM;
import com.travelportal.vm.HotelBookDetailsVM;
import com.travelportal.vm.PassengerBookingInfoVM;

public class AgentInfoController extends Controller {


	@Transactional(readOnly=true)
	public static Result AgentBookingInfo(){ 

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
		long totalPages = 0;
		int currentPage = 1;

		List<HotelBookingDetails> hoteDetails = null;

		totalPages = HotelBookingDetails.getAgentBookingTotal(10,Long.parseLong(session().get("agent")));
		hoteDetails = HotelBookingDetails.getfindByAgent(Long.parseLong(session().get("agent")), currentPage, 10, totalPages);

		fullBookingInfo(hoteDetails, aDetailsVMs);

		//return ok(Json.toJson(aDetailsVMs));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		map.put("results", aDetailsVMs);

		JsonNode onehotelJson = Json.toJson(map);
		return ok(agentBookingInfo.render(onehotelJson));
		//return ok(agentBookingInfo.render());

	}


	public static void fullBookingInfo(List<HotelBookingDetails> hoteDetails, List<HotelBookDetailsVM> aDetailsVMs){
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
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
			
			List<PassengerBookingInfoVM> pList = new ArrayList<>();
			List<RoomRegiterBy> roBy = RoomRegiterBy.getRoomInfoByBookingId(hBookingDetails.getId());
			if(roBy != null){
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
					pList.add(paInfoVM);
				}
			
			hDetailsVM.setPassengerInfo(pList);
		}
			
			aDetailsVMs.add(hDetailsVM);
		}
	}

	@Transactional(readOnly=true)
	public static Result getagentInfo(int currentPage,String fromDate,String toDate,String status) {

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
		long totalPages = 0;
		//String status = "available";
		List<HotelBookingDetails> hoteDetails = null;

		if(fromDate.equals("1") && toDate.equals("1") && status.equals("1")){

			totalPages = HotelBookingDetails.getAllagentBookingTotal(10, Long.parseLong(session().get("agent")));
			hoteDetails = HotelBookingDetails.getfindByagent(Long.parseLong(session().get("agent")), currentPage, 10, totalPages);
		}else if(!fromDate.equals("1") && !toDate.equals("1")){
			try {
				totalPages = HotelBookingDetails.getAllagentTotalDateWise(10 , Long.parseLong(session().get("agent")) , format.parse(fromDate) , format.parse(toDate) , status);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				hoteDetails = HotelBookingDetails.getfindByagentDateWise(Long.parseLong(session().get("agent")), format.parse(fromDate) , format.parse(toDate), currentPage, 10, totalPages , status);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(fromDate.equals("1") && toDate.equals("1") && !status.equals("1")){

			totalPages = HotelBookingDetails.getTotalDateWiseAgentWise(10 , Long.parseLong(session().get("agent")), status);

			hoteDetails = HotelBookingDetails.getfindByDateWiseAgentWise(Long.parseLong(session().get("agent")), currentPage, 10, totalPages , status);

		}

		fullBookingInfo(hoteDetails, aDetailsVMs);
		/*for(HotelBookingDetails hBookingDetails:hoteDetails){

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
		}*/
		//return ok(Json.toJson(aDetailsVMs));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		map.put("results", aDetailsVMs);
		return ok(Json.toJson(map));
	}


	/*----------------------------------------------------------------------------------------------------------------------------------------*/

	@Transactional(readOnly=true)
	public static Result getagentInfobynm(int currentPage,String checkIn,String checkOut,String guest,String status,Long bookingId) {

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		//HotelBookDetailsVM hDetailsVM= new HotelBookDetailsVM();       
		List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
		System.out.println("check in="+checkIn+"CheckOut="+checkOut);


		long totalPages = 0;
		List<HotelBookingDetails> hoteDetails = null;

		if(!checkIn.equals("undefined") && !checkOut.equals("undefined")&&!guest.equals("undefined") && bookingId == 0){
			try {
				totalPages = HotelBookingDetails.getAllagentTotalDateWise1(10 , Long.parseLong(session().get("agent")) , format.parse(checkIn) , format.parse(checkOut) , status,guest);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				hoteDetails = HotelBookingDetails.getfindByagentDateWise1(Long.parseLong(session().get("agent")), format.parse(checkIn) , format.parse(checkOut), currentPage, 10, totalPages , status,guest);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(checkIn.equals("undefined") && checkOut.equals("undefined") && !guest.equals("undefined") && bookingId == 0){

			totalPages = HotelBookingDetails.getTotalDateWiseAgentWise1(10 , Long.parseLong(session().get("agent")), status,guest);

			hoteDetails = HotelBookingDetails.getfindByDateWiseAgentWise1(Long.parseLong(session().get("agent")), currentPage, 10, totalPages , status,guest);

		}
		else if(guest.equals("undefined")&&!checkIn.equals("undefined") && !checkOut.equals("undefined") && bookingId == 0)
		{

			try {
				totalPages = HotelBookingDetails.getTotalDateWiseAgentWise11(10 , Long.parseLong(session().get("agent")), status,format.parse(checkIn),format.parse(checkOut));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				hoteDetails = HotelBookingDetails.getfindByDateWiseAgentWise11(Long.parseLong(session().get("agent")), currentPage, 10, totalPages , status,format.parse(checkIn) , format.parse(checkOut));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else if(bookingId != 0){
			totalPages = HotelBookingDetails.getTotalBookingIdWise(10 , Long.parseLong(session().get("agent")), status, bookingId);
			hoteDetails = HotelBookingDetails.getfindByBookingId(Long.parseLong(session().get("agent")), currentPage, 10, totalPages , status,bookingId);
		}

		fullBookingInfo(hoteDetails, aDetailsVMs);
	/*	for(HotelBookingDetails hBookingDetails:hoteDetails){

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
		}*/
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
		hBookingDetails.setRoom_status("cancel");
		hBookingDetails.merge();

		cancelMail(hBookingDetails.getTravelleremail(),hBookingDetails);

		List<HotelBookingDates> hotelBookingDates = HotelBookingDates.getDateBybookingId(id);
		//RoomAllotedRateWise rateWise = RoomAllotedRateWise.findByRateId(hBookingDetails.getRate().getId());
		for(HotelBookingDates hDates:hotelBookingDates){

			if(hBookingDetails.getRate() != null){

				RoomAllotedRateWise rateWise = RoomAllotedRateWise.findByRateIdandDate(hBookingDetails.getRate().getId(), hDates.getBookingDate());
				if(rateWise != null){
					count = rateWise.getRoomCount() - hBookingDetails.getNoOfroom();
					rateWise.setRoomCount(count);
					rateWise.merge();

				}
			}

		}

		return ok();

	}

	
	
	
	@Transactional(readOnly=false)
	public static Result getVoucherOnRequest(long bookingId){
		return ok();
	}
	
	@Transactional(readOnly=false)
	public static Result getVoucherCancel(long bookingId){
		return ok();
		
	}
	
	@Transactional(readOnly=false)
	public static Result getVoucherConfirm(long bookingId){
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		final String rootDir = Play.application().configuration().getString("mail.storage.path");
		
		File f = new File(rootDir);
		if(!f.exists()){
			f.mkdir();
		}
		
		HotelBookingDetails hBookingDetails = HotelBookingDetails.findBookingById(bookingId);
		
		Document document = new Document();
		try {
			
			String fileName = "C://hotelVoucher"+".pdf";
			PdfWriter.getInstance(document, new FileOutputStream(fileName));
			
		
			Font font1 = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
					BaseColor.BLACK);
			Font font2 = new Font(FontFamily.HELVETICA, 8, Font.BOLD,
					BaseColor.BLACK);
			Font voucherFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL,
					BaseColor.BLACK);
			Font voucherFont1 = new Font(FontFamily.HELVETICA, 10, Font.NORMAL,
					BaseColor.RED);
			Font voucherPageMsgFont = new Font(FontFamily.HELVETICA, 8, Font.NORMAL,
					BaseColor.BLUE);
			
			
			
		/*-----------------Table--------------------*/		
			PdfPTable BookingAddImg = new PdfPTable(1);
			BookingAddImg.setWidthPercentage(100);
			float[] bookingAddImgWidth = {2f};
			BookingAddImg.setWidths(bookingAddImgWidth);
			
			final String RESOURCE = rootDir+"/"	+"Tag EXP.jpg";
			Image logoimg = Image.getInstance(RESOURCE);
			logoimg.scaleAbsolute(500f, 70f);
			
			PdfPCell hotelImg = new PdfPCell(logoimg);
			hotelImg.setBorder(Rectangle.NO_BORDER);
			hotelImg.setPaddingBottom(5);
			BookingAddImg.addCell(hotelImg);
			
			/*PdfPTable BookingAddImg = new PdfPTable(2);
			BookingAddImg.setWidthPercentage(100);
			float[] bookingAddImgWidth = {1.5f,2.5f};
			BookingAddImg.setWidths(bookingAddImgWidth);
			
			final String RESOURCE = rootDir+"/"	+"logo.png";
			Image logoimg = Image.getInstance(RESOURCE);
			logoimg.scaleAbsolute(130f, 25f);
			PdfPCell hotelImg = new PdfPCell(logoimg);
			hotelImg.setBorder(Rectangle.NO_BORDER);
			hotelImg.setPaddingTop(7);
			BookingAddImg.addCell(hotelImg);
			
			final String RESOURCE1 = rootDir+"/"+"hotelVoucher.png";
			Image voucherimg = Image.getInstance(RESOURCE1);
			voucherimg.scaleAbsolute(340f, 60f);
			PdfPCell hotelVoucherImg = new PdfPCell(voucherimg);
			hotelVoucherImg.setBorder(Rectangle.NO_BORDER);
			hotelVoucherImg.setPaddingBottom(6);
			BookingAddImg.addCell(hotelVoucherImg);*/
			
		/*-----------------Table--------------------*/			
			PdfPTable hotelVoucherTitle = new PdfPTable(2);
			hotelVoucherTitle.setWidthPercentage(100);
			float[] hotelVoucherTitleWidth = {2f,2f};
			hotelVoucherTitle.setWidths(hotelVoucherTitleWidth);
			
			PdfPCell voucherTitle = new PdfPCell(new Paragraph("HOTEL",voucherFont));
			voucherTitle.setBackgroundColor(new BaseColor(224, 224, 224));
			voucherTitle.setHorizontalAlignment(Element.ALIGN_RIGHT);
			voucherTitle.setBorder(Rectangle.NO_BORDER);
			voucherTitle.setPaddingBottom(4);
			hotelVoucherTitle.addCell(voucherTitle);
			
			PdfPCell voucherTitle1 = new PdfPCell(new Paragraph("VOUCHER",voucherFont1));
			voucherTitle1.setBackgroundColor(new BaseColor(224, 224, 224));
			voucherTitle1.setHorizontalAlignment(Element.ALIGN_LEFT);
			voucherTitle1.setBorder(Rectangle.NO_BORDER);
			voucherTitle1.setPaddingBottom(4);
			hotelVoucherTitle.addCell(voucherTitle1);
			
			PdfPTable hotelVoucherTitlemain = new PdfPTable(1);
			hotelVoucherTitlemain.setWidthPercentage(100);
			float[] hotelVoucherTitlemainWidth = {2f};
			hotelVoucherTitlemain.setWidths(hotelVoucherTitlemainWidth);
			
			PdfPCell voucherTitlemain = new PdfPCell(hotelVoucherTitle);
			voucherTitlemain.setBorder(Rectangle.NO_BORDER);
			hotelVoucherTitlemain.addCell(voucherTitlemain);
			
			
		/*-----------------Table--------------------*/		
			PdfPTable hotelVoucherMsg = new PdfPTable(1);
			hotelVoucherMsg.setWidthPercentage(100);
			float[] hotelVoucherMsgWidth = {2f};
			hotelVoucherMsg.setWidths(hotelVoucherMsgWidth);
			
			PdfPCell hotelVoucherPageMsg = new PdfPCell(new Paragraph("Please present either an electronic or paper copy of your hotel voucher upon check-in",voucherPageMsgFont));
			hotelVoucherPageMsg.setBackgroundColor(new BaseColor(255, 255, 255));
			hotelVoucherPageMsg.setHorizontalAlignment(Element.ALIGN_CENTER);
			hotelVoucherPageMsg.setBorder(Rectangle.NO_BORDER);
			hotelVoucherPageMsg.setPaddingTop(10);
			hotelVoucherMsg.addCell(hotelVoucherPageMsg);
			
		/*-----------------Table--------------------*/	
			
			PdfPTable todayDateTable = new PdfPTable(4);
			todayDateTable.setWidthPercentage(100);
			float[] todayDateWidth = {2f,2f,2f,2f};
			todayDateTable.setWidths(todayDateWidth);
			
			PdfPCell blank = new PdfPCell();
			blank.setBorderColor(BaseColor.WHITE);
			todayDateTable.addCell(blank);
			
			PdfPCell blank1 = new PdfPCell();
			blank1.setBorderColor(BaseColor.WHITE);
			todayDateTable.addCell(blank1);
			
			PdfPCell voucherdateLabel = new PdfPCell(new Paragraph("Voucher Issue Date :",font1));
			voucherdateLabel.setBorder(Rectangle.NO_BORDER);
			todayDateTable.addCell(voucherdateLabel);
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String date = sdf.format(new Date());
			Paragraph date1 = new Paragraph(date,font2);
			
			PdfPCell datevalue = new PdfPCell(new Paragraph(date1));
			datevalue.setBackgroundColor(new BaseColor(224, 224, 224));
			datevalue.setHorizontalAlignment(Element.ALIGN_CENTER);
			datevalue.setBorderColor(BaseColor.WHITE);
			todayDateTable.addCell(datevalue);
			
			/*PdfPTable voucherDateTable = new PdfPTable(2);
			voucherDateTable.setWidthPercentage(100);
			float[] voucherDateWidth = {2f,2f};
			voucherDateTable.setWidths(voucherDateWidth);*/
			
	/*-----------------Table--------------------*/	
			
			PdfPTable FacilityNumbarOfRoomTable = new PdfPTable(2);
			FacilityNumbarOfRoomTable.setWidthPercentage(100);
			float[] FacilityNumbarOfRoomTableWidth = {2f,2f};
			FacilityNumbarOfRoomTable.setWidths(FacilityNumbarOfRoomTableWidth);	
			
			PdfPCell FacilityNumbarOfRoom = new PdfPCell(new Paragraph("Number of Rooms :",font1));
			FacilityNumbarOfRoom.setBorder(Rectangle.NO_BORDER);
			FacilityNumbarOfRoom.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityNumbarOfRoomTable.addCell(FacilityNumbarOfRoom);
		
			PdfPCell FacilityNumbarOfRoom1 = new PdfPCell(new Paragraph(String.valueOf(hBookingDetails.getNoOfroom()),font2));
			FacilityNumbarOfRoom1.setBorderColor(BaseColor.WHITE);
			FacilityNumbarOfRoom1.setBorderWidth(1f);
			FacilityNumbarOfRoom1.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityNumbarOfRoom1.setHorizontalAlignment(Element.ALIGN_CENTER);
			FacilityNumbarOfRoomTable.addCell(FacilityNumbarOfRoom1);
		
		/*-----------------Table--------------------*/	
			
			PdfPTable FacilityNumberofExtraBedsTable = new PdfPTable(2);
			FacilityNumberofExtraBedsTable.setWidthPercentage(100);
			float[] FacilityNumberofExtraBedsTableWidth = {2f,2f};
			FacilityNumberofExtraBedsTable.setWidths(FacilityNumberofExtraBedsTableWidth);	
			
			PdfPCell  FacilityNumberofExtraBeds = new PdfPCell(new Paragraph("Number of Extra Beds :",font1));
			FacilityNumberofExtraBeds.setBorder(Rectangle.NO_BORDER);
			FacilityNumberofExtraBeds.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityNumberofExtraBedsTable.addCell(FacilityNumberofExtraBeds);
			
			PdfPCell FacilityNumberofExtraBeds1 = new PdfPCell(new Paragraph("8",font2));
			FacilityNumberofExtraBeds1.setBorderColor(BaseColor.WHITE);
			FacilityNumberofExtraBeds1.setBorderWidth(1f);
			FacilityNumberofExtraBeds1.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityNumberofExtraBeds1.setHorizontalAlignment(Element.ALIGN_CENTER);
			FacilityNumberofExtraBedsTable.addCell(FacilityNumberofExtraBeds1);
			
/*-----------------Table--------------------*/	
			
			PdfPTable FacilityBreakfastTable = new PdfPTable(2);
			FacilityBreakfastTable.setWidthPercentage(100);
			float[] FacilityBreakfastTableWidth = {2f,2f};
			FacilityBreakfastTable.setWidths(FacilityBreakfastTableWidth);	
			
			PdfPCell  FacilityBreakfast = new PdfPCell(new Paragraph("Breakfast :",font1));
			FacilityBreakfast.setBorder(Rectangle.NO_BORDER);
			FacilityBreakfast.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityBreakfastTable.addCell(FacilityBreakfast);
			
			
			PdfPCell FacilityBreakfast1 = new PdfPCell(new Paragraph(hBookingDetails.getHotelNm(),font2));
			FacilityBreakfast1.setBorderColor(BaseColor.WHITE);
			FacilityBreakfast1.setBorderWidth(1f);
			FacilityBreakfast1.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityBreakfast1.setHorizontalAlignment(Element.ALIGN_CENTER);
			FacilityBreakfastTable.addCell(FacilityBreakfast1);
			
/*-----------------Table--------------------*/	
			
			PdfPTable FacilityRoomTypeTable = new PdfPTable(2);
			FacilityRoomTypeTable.setWidthPercentage(100);
			float[] FFacilityRoomTypeTableWidth = {2f,2f};
			FacilityRoomTypeTable.setWidths(FFacilityRoomTypeTableWidth);	
			
			PdfPCell  FacilityRoomType = new PdfPCell(new Paragraph("Room Category :",font1));
			FacilityRoomType.setBorder(Rectangle.NO_BORDER);
			FacilityRoomType.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityRoomTypeTable.addCell(FacilityRoomType);
			
			PdfPCell FacilityRoomType1 = new PdfPCell(new Paragraph(hBookingDetails.getRoomName(),font2));
			FacilityRoomType1.setBorderColor(BaseColor.WHITE);
			FacilityRoomType1.setBorderWidth(1f);
			FacilityRoomType1.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityRoomType1.setHorizontalAlignment(Element.ALIGN_CENTER);
			FacilityRoomTypeTable.addCell(FacilityRoomType1);
			
			
/*-----------------Table--------------------*/	
			
			PdfPTable bookigConfimNoTable = new PdfPTable(2);
			bookigConfimNoTable.setWidthPercentage(100);
			float[] bookigConfimNoTableWidth = {2f,2f};
			bookigConfimNoTable.setWidths(bookigConfimNoTableWidth);	
			
			PdfPCell  bookigConfimNo = new PdfPCell(new Paragraph("Confirmation No / By :",font1));
			bookigConfimNo.setBorder(Rectangle.NO_BORDER);
			bookigConfimNo.setBackgroundColor(new BaseColor(224, 224, 224));
			bookigConfimNoTable.addCell(bookigConfimNo);
			
			PdfPCell bookigConfimNo1 = new PdfPCell(new Paragraph("1234",font2));
			bookigConfimNo1.setBorderColor(BaseColor.WHITE);
			bookigConfimNo1.setBorderWidth(1f);
			bookigConfimNo1.setBackgroundColor(new BaseColor(224, 224, 224));
			bookigConfimNo1.setHorizontalAlignment(Element.ALIGN_CENTER);
			bookigConfimNoTable.addCell(bookigConfimNo1);
					
		/*-----------------Table--------------------*/	
			
			PdfPTable bookingFacilityInfoTable = new PdfPTable(1);
			bookingFacilityInfoTable.setWidthPercentage(100);
			float[] bookingFacilityInfoWidth = {2f};
			bookingFacilityInfoTable.setWidths(bookingFacilityInfoWidth);
			
			PdfPCell FacilityNumbarOfRoomTable1 = new PdfPCell(FacilityNumbarOfRoomTable);
			FacilityNumbarOfRoomTable1.setBorder(Rectangle.NO_BORDER);
			FacilityNumbarOfRoomTable1.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityNumbarOfRoomTable1.setPaddingBottom(4);
			bookingFacilityInfoTable.addCell(FacilityNumbarOfRoomTable1);
		
			PdfPCell  FacilityNumberofExtraBedsTable1 = new PdfPCell(FacilityNumberofExtraBedsTable);
			FacilityNumberofExtraBedsTable1.setBorder(Rectangle.NO_BORDER);
			FacilityNumberofExtraBedsTable1.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityNumberofExtraBedsTable1.setPaddingBottom(4);
			bookingFacilityInfoTable.addCell(FacilityNumberofExtraBedsTable1);
			
			PdfPCell  FacilityBreakfastTable1 = new PdfPCell(FacilityBreakfastTable);
			FacilityBreakfastTable1.setBorder(Rectangle.NO_BORDER);
			FacilityBreakfastTable1.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityBreakfastTable1.setPaddingBottom(4);
			bookingFacilityInfoTable.addCell(FacilityBreakfastTable1);
			
			PdfPCell  FacilityRoomTypeTable1 = new PdfPCell(FacilityRoomTypeTable);
			FacilityRoomTypeTable1.setBorder(Rectangle.NO_BORDER);
			FacilityRoomTypeTable1.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityRoomTypeTable1.setPaddingBottom(4);
			bookingFacilityInfoTable.addCell(FacilityRoomTypeTable1);
			
			PdfPCell FacilityMaxOccupancyTable1 = new PdfPCell(bookigConfimNoTable);
			FacilityMaxOccupancyTable1.setBorder(Rectangle.NO_BORDER);
			FacilityMaxOccupancyTable1.setBackgroundColor(new BaseColor(224, 224, 224));
			FacilityMaxOccupancyTable1.setPaddingBottom(4);
			bookingFacilityInfoTable.addCell(FacilityMaxOccupancyTable1);
		
			
		/*-----------------Table--------------------*/				
			PdfPTable bookingInfoRefNoTable = new PdfPTable(2);
			bookingInfoRefNoTable.setWidthPercentage(100);
			float[] bookingInfoRefNoWidth = {2f,2f};
			bookingInfoRefNoTable.setWidths(bookingInfoRefNoWidth);
			
			PdfPCell  bookingInfoRefNo = new PdfPCell(new Paragraph("Booking Reference No :",font1));
			bookingInfoRefNo.setBorder(Rectangle.NO_BORDER);
			bookingInfoRefNo.setBackgroundColor(new BaseColor(255, 255, 255));
			bookingInfoRefNoTable.addCell(bookingInfoRefNo);
			
			PdfPCell bookingInfoRefNo1 = new PdfPCell(new Paragraph(hBookingDetails.getId().toString(),font2));
			bookingInfoRefNo1.setBorderColor(BaseColor.WHITE);
			bookingInfoRefNo1.setBackgroundColor(new BaseColor(224, 224, 224));
			bookingInfoRefNoTable.addCell(bookingInfoRefNo1);
			
			/*-----------------Table--------------------*/				
			PdfPTable bookingInfoHotelNameTable = new PdfPTable(2);
			bookingInfoHotelNameTable.setWidthPercentage(100);
			float[] bookingInfoHotelNameWidth = {2f,2f};
			bookingInfoHotelNameTable.setWidths(bookingInfoHotelNameWidth);	
			
			PdfPCell  bookingInfoHotelName = new PdfPCell(new Paragraph("Hotel :",font1));
			bookingInfoHotelName.setBorder(Rectangle.NO_BORDER);
			bookingInfoHotelName.setBackgroundColor(new BaseColor(255, 255, 255));
			bookingInfoHotelNameTable.addCell(bookingInfoHotelName);
			
			PdfPCell bookingInfoHotelName1 = new PdfPCell(new Paragraph(hBookingDetails.getHotelNm().toString(),font2));
			bookingInfoHotelName1.setBorderColor(BaseColor.WHITE);
			bookingInfoHotelName1.setBackgroundColor(new BaseColor(224, 224, 224));
			bookingInfoHotelNameTable.addCell(bookingInfoHotelName1);
		
			/*-----------------Table--------------------*/				
			PdfPTable hotelAddressTable = new PdfPTable(2);
			hotelAddressTable.setWidthPercentage(100);
			float[] hotelAddressTableWidth = {2f,2f};
			hotelAddressTable.setWidths(hotelAddressTableWidth);	
				
			PdfPCell hotelAddress = new PdfPCell(new Paragraph("Hotel Address :",font1));
			hotelAddress.setBorder(Rectangle.NO_BORDER);
			hotelAddress.setBackgroundColor(new BaseColor(255, 255, 255));
			hotelAddressTable.addCell(hotelAddress);
			
			PdfPCell  hotelAddress1= new PdfPCell(new Paragraph(hBookingDetails.getHotelAddr(),font2));
			hotelAddress1.setBorderColor(BaseColor.WHITE);
			hotelAddress1.setBackgroundColor(new BaseColor(224, 224, 224));
			hotelAddressTable.addCell(hotelAddress1);
			
			/*-----------------Table--------------------*/				
			PdfPTable hotelContactNoTable = new PdfPTable(2);
			hotelContactNoTable.setWidthPercentage(100);
			float[] hotelContactNoTableWidth = {2f,2f};
			hotelContactNoTable.setWidths(hotelContactNoTableWidth);	
			
			PdfPCell hotelContactNo = new PdfPCell(new Paragraph("Hotel Contact Numbar :",font1));
			hotelContactNo.setBorder(Rectangle.NO_BORDER);
			hotelContactNo.setBackgroundColor(new BaseColor(255, 255, 255));
			hotelContactNoTable.addCell(hotelContactNo);
			
			PdfPCell  hotelContactNo1= new PdfPCell(new Paragraph("",font2));
			hotelContactNo1.setBorderColor(BaseColor.WHITE);
			hotelContactNo1.setBackgroundColor(new BaseColor(224, 224, 224));
			hotelContactNoTable.addCell(hotelContactNo1);
			
		/*-----------------Table--------------------*/				
			PdfPTable bookingInfoTable = new PdfPTable(1);
			bookingInfoTable.setWidthPercentage(100);
			float[] bookingInfoWidth = {2f};
			bookingInfoTable.setWidths(bookingInfoWidth);
			
					
			PdfPCell bookingInfoRefNoTable1 = new PdfPCell(bookingInfoRefNoTable);
			bookingInfoRefNoTable1.setBorderColor(BaseColor.WHITE);
			bookingInfoRefNoTable1.setBackgroundColor(new BaseColor(255, 255, 255));
			bookingInfoRefNoTable1.setPaddingBottom(4);
			bookingInfoTable.addCell(bookingInfoRefNoTable1);
			
		
			
			PdfPCell  bookingInfoHotelNameTable1 = new PdfPCell(bookingInfoHotelNameTable);
			bookingInfoHotelNameTable1.setBorder(Rectangle.NO_BORDER);
			bookingInfoHotelNameTable1.setBackgroundColor(new BaseColor(255, 255, 255));
			bookingInfoHotelNameTable1.setPaddingBottom(4);
			bookingInfoTable.addCell(bookingInfoHotelNameTable1);
			
			
			PdfPCell hotelAddressTable1 = new PdfPCell(hotelAddressTable);
			hotelAddressTable1.setBorder(Rectangle.NO_BORDER);
			hotelAddressTable1.setBackgroundColor(new BaseColor(255, 255, 255));
			hotelAddressTable1.setPaddingBottom(4);
			bookingInfoTable.addCell(hotelAddressTable1);
			
			PdfPCell hotelContactNoTable1 = new PdfPCell(hotelContactNoTable);
			hotelContactNoTable1.setBorder(Rectangle.NO_BORDER);
			hotelContactNoTable1.setBackgroundColor(new BaseColor(255, 255, 255));
			hotelContactNoTable1.setPaddingBottom(4);
			bookingInfoTable.addCell(hotelContactNoTable1);
		
		
		/*-----------------Table--------------------*/	
				
			
			PdfPTable bookingFacilityInfoTable2 = new PdfPTable(2);
			bookingFacilityInfoTable2.setWidthPercentage(100);
			float[] bookingFacilityInfoWidth2 = {2f,2f};
			bookingFacilityInfoTable2.setSpacingBefore(5f);
			bookingFacilityInfoTable2.setSpacingAfter(10f);
			
			bookingFacilityInfoTable2.setWidths(bookingFacilityInfoWidth2);
			
			PdfPCell otherTable1 = new PdfPCell(bookingInfoTable);
			otherTable1.setBackgroundColor(new BaseColor(255, 255, 255));
			otherTable1.setPadding(10);
			otherTable1.setBorder(Rectangle.NO_BORDER);
			bookingFacilityInfoTable2.addCell(otherTable1);
			
			PdfPCell otherTable = new PdfPCell(bookingFacilityInfoTable);
			otherTable.setBackgroundColor(new BaseColor(224, 224, 224));
			otherTable.setPadding(10);
			otherTable.setBorder(Rectangle.NO_BORDER);
			bookingFacilityInfoTable2.addCell(otherTable);
			
		/*------ Table ---------*/	
			PdfPTable bookingCancellation = new PdfPTable(1);
			bookingCancellation.setWidthPercentage(100);
			float[] bookingCancellationWidth = {2f};
			bookingCancellation.setSpacingBefore(5f);
			bookingCancellation.setSpacingAfter(10f);
			bookingCancellation.setWidths(bookingCancellationWidth);	
			
			PdfPCell cancellationMsg = new PdfPCell(new Paragraph("Any Cancellation received within 2 days prior to arrival date will incur the first night charge. Failure to arrival at your hotel will be treated as No-Show and will incur the first night charge(Hotel policy). ",font1));
			cancellationMsg.setBorderColor(BaseColor.WHITE);
			otherTable1.setPaddingBottom(8f);
			cancellationMsg.setBackgroundColor(new BaseColor(255, 255, 255));
			bookingCancellation.addCell(cancellationMsg);
			
		/*------ Table ---------*/	
			PdfPTable bookingPassengerName = new PdfPTable(2);
			bookingPassengerName.setWidthPercentage(100);
			float[] passengerNameWidth = {0.8f,2.5f};
			bookingPassengerName.setWidths(passengerNameWidth);	
			
			PdfPCell passengerNameLabel = new PdfPCell(new Paragraph("Passenger Name :",font2));
			passengerNameLabel.setBorderColor(BaseColor.WHITE);
			passengerNameLabel.setBorderWidth(1f);
			passengerNameLabel.setBackgroundColor(new BaseColor(255, 255, 255));
			bookingPassengerName.addCell(passengerNameLabel);
			
			PdfPCell passengerName = new PdfPCell(new Paragraph(hBookingDetails.getTravellerfirstname(),font2));
			passengerName.setBorderColor(BaseColor.WHITE);
			passengerName.setBorderWidth(1f);
			passengerName.setBackgroundColor(new BaseColor(224, 224, 224));
			bookingPassengerName.addCell(passengerName);
			
		/*------ Table ---------*/	
			PdfPTable bookingArrivalDeparture = new PdfPTable(6);
			bookingArrivalDeparture.setWidthPercentage(100);
			float[] bookinghotelDetailsWidth = {2.2f,1.8f,2.4f,1.6f,2.7f,1.3f};
			bookingArrivalDeparture.setWidths(bookinghotelDetailsWidth);
			
			PdfPCell bookingArrival = new PdfPCell(new Paragraph("Arrival Date:",font2));
			bookingArrival.setBorderColor(BaseColor.WHITE);
			bookingArrival.setBorderWidth(1f);
			bookingArrival.setBackgroundColor(new BaseColor(255, 255, 255));
			bookingArrivalDeparture.addCell(bookingArrival);
			
			PdfPCell bookingArrival1 = new PdfPCell(new Paragraph(format.format(hBookingDetails.getCheckIn()).toString(),font2));
			bookingArrival1.setBorderColor(BaseColor.WHITE);
			bookingArrival1.setBorderWidth(1f);
			bookingArrival1.setBackgroundColor(new BaseColor(224, 224, 224));
			bookingArrivalDeparture.addCell(bookingArrival1);
			
			PdfPCell bookingDeparture = new PdfPCell(new Paragraph("Departure Date:",font2));
			bookingDeparture.setBorderColor(BaseColor.WHITE);
			bookingDeparture.setBorderWidth(1f);
			bookingDeparture.setBackgroundColor(new BaseColor(255, 255, 255));
			bookingArrivalDeparture.addCell(bookingDeparture);
			
			PdfPCell bookingDeparture1 = new PdfPCell(new Paragraph(format.format(hBookingDetails.getCheckOut()).toString(),font2));
			bookingDeparture1.setBorderColor(BaseColor.WHITE);
			bookingDeparture1.setBorderWidth(1f);
			bookingDeparture1.setBackgroundColor(new BaseColor(224, 224, 224));
			bookingArrivalDeparture.addCell(bookingDeparture1);
			
			PdfPCell bookingNumberOfNight = new PdfPCell(new Paragraph("Number Of Night(s) :",font2));
			bookingNumberOfNight.setBorderColor(BaseColor.WHITE);
			bookingNumberOfNight.setBorderWidth(1f);
			bookingNumberOfNight.setBackgroundColor(new BaseColor(255, 255, 255));
			bookingArrivalDeparture.addCell(bookingNumberOfNight);
			
			PdfPCell bookingNumberOfNight1 = new PdfPCell(new Paragraph(String.valueOf(hBookingDetails.getTotalNightStay()),font2));
			bookingNumberOfNight1.setBorderColor(BaseColor.WHITE);
			bookingNumberOfNight1.setBorderWidth(1f);
			bookingNumberOfNight1.setBackgroundColor(new BaseColor(224, 224, 224));
			bookingArrivalDeparture.addCell(bookingNumberOfNight1);
			
					
		/*------ Table ---------*/	
			PdfPTable bookingPayDetailsTable = new PdfPTable(6);
			bookingArrivalDeparture.setWidthPercentage(100);
			float[] bookingPayDetailsTableWidth = {3f,1f,2f,1f,2f,1f};
			bookingPayDetailsTable.setWidths(bookingPayDetailsTableWidth);
			
			PdfPCell toatlPassengerAdult = new PdfPCell(new Paragraph("Total Passenger Adult :",font2));
			toatlPassengerAdult.setBackgroundColor(new BaseColor(224, 224, 224));
			toatlPassengerAdult.setBorderColor(BaseColor.WHITE);
			toatlPassengerAdult.setBorderWidth(1f);
			bookingPayDetailsTable.addCell(toatlPassengerAdult);
			
			String[] noAdults;
  			noAdults = hBookingDetails.getAdult().split(" ");
			
			PdfPCell toatlPassengerAdult1 = new PdfPCell(new Paragraph(noAdults[0],font2));
			toatlPassengerAdult1.setBackgroundColor(new BaseColor(224, 224, 224));
			toatlPassengerAdult1.setBorderColor(BaseColor.WHITE);
			toatlPassengerAdult1.setBorderWidth(1f);
			bookingPayDetailsTable.addCell(toatlPassengerAdult1);
			
			PdfPCell noOfChild = new PdfPCell(new Paragraph("Child :",font2));
			noOfChild.setBackgroundColor(new BaseColor(224, 224, 224));
			noOfChild.setBorderColor(BaseColor.WHITE);
			noOfChild.setBorderWidth(1f);
			bookingPayDetailsTable.addCell(noOfChild);
			
			PdfPCell noOfChild1 = new PdfPCell(new Paragraph(String.valueOf(hBookingDetails.getNoOfchild()),font2));
			noOfChild1.setBackgroundColor(new BaseColor(224, 224, 224));
			noOfChild1.setBorderColor(BaseColor.WHITE);
			noOfChild1.setBorderWidth(1f);
			bookingPayDetailsTable.addCell(noOfChild1);	
			
			PdfPCell infact = new PdfPCell(new Paragraph("Infant :",font2));
			infact.setBackgroundColor(new BaseColor(224, 224, 224));
			infact.setBorderColor(BaseColor.WHITE);
			infact.setBorderWidth(1f);
			bookingPayDetailsTable.addCell(infact);
			
			PdfPCell infact1 = new PdfPCell(new Paragraph(hBookingDetails.getRoom_status().toString(),font2));
			infact1.setBackgroundColor(new BaseColor(224, 224, 224));
			infact1.setBorderColor(BaseColor.WHITE);
			infact1.setBorderWidth(1f);
			bookingPayDetailsTable.addCell(infact1);	
			
		/*------ Table ---------*/	
			
			PdfPTable bookedbyPayableTitle = new PdfPTable(1);
			bookedbyPayableTitle.setWidthPercentage(100);
			float[] bookedbyPayableTitleeWidth = {1f};
			bookedbyPayableTitle.setWidths(bookedbyPayableTitleeWidth);
			
			PdfPCell bookedbyPayableTitlerowtitle = new PdfPCell(new Paragraph("booked & Payable By:",font2));
			bookedbyPayableTitlerowtitle.setBackgroundColor(new BaseColor(255, 255, 255));
			bookedbyPayableTitlerowtitle.setBorderColor(BaseColor.WHITE);
			bookedbyPayableTitle.addCell(bookedbyPayableTitlerowtitle);	
			
		/*------ Table ---------*/	
			
			PdfPTable bookedbyPayable = new PdfPTable(1);
			bookedbyPayable.setWidthPercentage(100);
			float[] bookedbyPayableWidth = {1f};
			bookedbyPayable.setWidths(bookedbyPayableWidth);
			
			PdfPCell bookedbyPayableInfo = new PdfPCell(new Paragraph("THE EXPEDITION CO., LTD. \n 700/86, Srinakarin Road, Suanluang, Suanluang, Bangkok - 10250. \n Thailand \n Emergency Contact No : 9877654444",font2));
			bookedbyPayableInfo.setBackgroundColor(new BaseColor(224, 224, 224));
			bookedbyPayableInfo.setBorderColor(BaseColor.WHITE);
			bookedbyPayable.addCell(bookedbyPayableInfo);	
			
		/*------ Table ---------*/	
			PdfPTable bookinghotelDetails = new PdfPTable(1);
			bookinghotelDetails.setWidthPercentage(100);
			float[] bookingArrivalDepartureWidth = {2f};
			bookinghotelDetails.setWidths(bookingArrivalDepartureWidth);
			
			PdfPCell bookingPassenger = new PdfPCell(bookingPassengerName);
			bookingPassenger.setBackgroundColor(new BaseColor(255, 255, 255));
			bookingPassenger.setPaddingBottom(6);
			bookingPassenger.setBorderColor(BaseColor.WHITE);
			bookinghotelDetails.addCell(bookingPassenger);
			
			PdfPCell arrivalDeparture = new PdfPCell(bookingArrivalDeparture);
			arrivalDeparture.setBackgroundColor(new BaseColor(255, 255, 255));
			arrivalDeparture.setBorderColor(BaseColor.WHITE);
			arrivalDeparture.setPaddingBottom(6);
			bookinghotelDetails.addCell(arrivalDeparture);
						
			PdfPCell payDetails = new PdfPCell(bookingPayDetailsTable);
			payDetails.setBackgroundColor(new BaseColor(255, 255, 255));
			payDetails.setBorderColor(BaseColor.WHITE);
			payDetails.setPaddingBottom(6);
			bookinghotelDetails.addCell(payDetails);
			
			PdfPCell bookedbyPayabletitleRow = new PdfPCell(bookedbyPayableTitle);
			bookedbyPayabletitleRow.setBackgroundColor(new BaseColor(255, 255, 255));
			bookedbyPayabletitleRow.setBorderColor(BaseColor.WHITE);
			bookinghotelDetails.addCell(bookedbyPayabletitleRow);
			
			PdfPCell bookedbyInfo = new PdfPCell(bookedbyPayable);
			bookedbyInfo.setBackgroundColor(new BaseColor(255, 255, 255));
			
			bookinghotelDetails.addCell(bookedbyInfo);
			
		/*------ Table ---------*/	
			PdfPTable hotelStampSig = new PdfPTable(1);
			hotelStampSig.setWidthPercentage(100);
			float[] hotelStampSigWidth = {2f};
			hotelStampSig.setWidths(hotelStampSigWidth);
					
			PdfPCell blankforpersonSig = new PdfPCell(new Paragraph(".",font1));
			blankforpersonSig.setBorderColor(BaseColor.WHITE);
			hotelStampSig.addCell(blankforpersonSig);
			
			PdfPCell sigPersonName = new PdfPCell(new Paragraph("THUNYASORN SWETPRASAT (Mr.Puk)",font1));
			sigPersonName.setHorizontalAlignment(Element.ALIGN_CENTER);
			sigPersonName.setBorderColor(BaseColor.WHITE);
			sigPersonName.setPaddingTop(6);
			hotelStampSig.addCell(sigPersonName);
			
			PdfPCell blankLine = new PdfPCell(new Paragraph("_________"));
			blankLine.setHorizontalAlignment(Element.ALIGN_CENTER);
			blankLine.setBorderColor(BaseColor.WHITE);
			hotelStampSig.addCell(blankLine);
			
			PdfPCell blankForStamp = new PdfPCell(new Paragraph(".",font1));
			blankForStamp.setBorderColor(BaseColor.WHITE);
			hotelStampSig.addCell(blankForStamp);
			
			PdfPCell companyStamp = new PdfPCell(new Paragraph("FOR\nTHE EXPEDITION CO., LTD. ",font1));
			companyStamp.setHorizontalAlignment(Element.ALIGN_CENTER);
			companyStamp.setBorderColor(BaseColor.WHITE);
			companyStamp.setPaddingTop(6);
			hotelStampSig.addCell(companyStamp);
			
		/*------ Table ---------*/	
			
			PdfPTable hotelStampSig1 = new PdfPTable(1);
			hotelStampSig1.setWidthPercentage(100);
			float[] hotelStampSig1Width = {2f};
			hotelStampSig1.setWidths(hotelStampSig1Width);
			
			PdfPCell StampSig1 = new PdfPCell(hotelStampSig);
			StampSig1.setBorderColor(BaseColor.RED);
			hotelStampSig1.addCell(StampSig1);
			
		/*------ Table ---------*/	
			PdfPTable bookingPaymentDetails = new PdfPTable(2);
			bookingPaymentDetails.setWidthPercentage(100);
			float[] bookingPaymentDetailsWidth = {2f,0.6f};
			bookingPaymentDetails.setWidths(bookingPaymentDetailsWidth);
			
			PdfPCell hotelPaymentDatails = new PdfPCell(bookinghotelDetails);
			hotelPaymentDatails.setBackgroundColor(new BaseColor(255, 255, 255));
			bookingPaymentDetails.addCell(hotelPaymentDatails);
			
			PdfPCell stampSig = new PdfPCell(hotelStampSig1);
			stampSig.setBorderColor(BaseColor.GRAY);
			stampSig.setBackgroundColor(new BaseColor(255, 255, 255));
			stampSig.setPaddingLeft(8);
			stampSig.setPaddingBottom(8);
			stampSig.setPaddingTop(8);
			stampSig.setPaddingRight(8);
			bookingPaymentDetails.addCell(stampSig);
		
		/*------ Table ---------*/	
			PdfPTable hotelPaymentMainTable = new PdfPTable(1);
			hotelPaymentMainTable.setWidthPercentage(100);
			float[] hotelPaymentMainTableWidth = {2f};
			hotelPaymentMainTable.setSpacingBefore(5f);
			hotelPaymentMainTable.setSpacingAfter(10f);
			hotelPaymentMainTable.setWidths(hotelPaymentMainTableWidth);
			
			PdfPCell mainDetails = new PdfPCell(bookingPaymentDetails);
			mainDetails.setBorderColor(BaseColor.GRAY);
			mainDetails.setPadding(8);
			mainDetails.setBorderWidth(1f);
			hotelPaymentMainTable.addCell(mainDetails);
			
		/*------ Table ---------*/		
			
			PdfPTable remarkTable = new PdfPTable(1);
			remarkTable.setWidthPercentage(100);
			float[] remarkTableWidth = {2f};
			remarkTable.setWidths(remarkTableWidth);
			
			PdfPCell remarks = new PdfPCell(new Phrase("Remarks :",font2));
			remarks.setBorderColor(BaseColor.WHITE);
			remarks.setBackgroundColor(new BaseColor(255, 255, 255));
			remarkTable.addCell(remarks);

			
			
     /*------ Table ---------*/		
			
			PdfPTable customerServiceTable = new PdfPTable(1);
			customerServiceTable.setWidthPercentage(100);
			float[] customerServiceWidth = {2f};
			bookingCancellation.setSpacingBefore(5f);
			bookingCancellation.setSpacingAfter(10f);
			customerServiceTable.setWidths(customerServiceWidth);
			
			PdfPCell callOur = new PdfPCell(new Phrase("* All special requests are subject to availability upon arrival. ",font2));
			callOur.setBorderColor(BaseColor.WHITE);
			callOur.setBackgroundColor(new BaseColor(255, 255, 255));
			customerServiceTable.addCell(callOur);

		
	   /*------ Table ---------*/	
			
			PdfPTable notesFieldTable = new PdfPTable(1);
			notesFieldTable.setWidthPercentage(100);
			float[] notesFieldWidth = {2f};
			notesFieldTable.setWidths(notesFieldWidth);
			
			PdfPCell note = new PdfPCell(new Phrase("Note",font2));
			note.setBorderColor(BaseColor.WHITE);
			note.setBackgroundColor(new BaseColor(255, 255, 255));
			notesFieldTable.addCell(note);
			
			PdfPCell note1 = new PdfPCell(new Phrase("* IMPORTANT : All rooms are guaranteed on the day of arrival. In the case of a no-show, your room(s) will be released and you will be subject to the terms and condition of the cancellation policy specified at the time of booking, informed by your travel agent. ",font1));
			note1.setBorderColor(BaseColor.WHITE);
			note1.setBackgroundColor(new BaseColor(255, 255, 255));
			notesFieldTable.addCell(note1);
			
			PdfPCell note2 = new PdfPCell(new Phrase("  The price of the booking does not include mini-bar items, telephone usage, laundry service etc. The hotel will bill you directly. ",font1));
			note2.setBorderColor(BaseColor.WHITE);
			note2.setBackgroundColor(new BaseColor(255, 255, 255));
			notesFieldTable.addCell(note2);
			
			PdfPCell note3 = new PdfPCell(new Phrase("  In case where breakfast is included with the room rate, please note that certain hotels may charge extra for children travelling with their parents. ",font1));
			note3.setBorderColor(BaseColor.WHITE);
			note3.setBackgroundColor(new BaseColor(255, 255, 255));
			notesFieldTable.addCell(note3);
			
			PdfPCell note4 = new PdfPCell(new Phrase("  If not booked through us, and if applicable the hotel will bill you directly. Upon arrival, if you have any questions, please verify with the hotel.  ",font1));
			note4.setBorderColor(BaseColor.WHITE);
			note4.setBackgroundColor(new BaseColor(255, 255, 255));
			notesFieldTable.addCell(note4);
			
			PdfPCell note5 = new PdfPCell(new Phrase("  Early Check-In & Late Check-out, is subject to availability at the discretion of the hotel.  ",font1));
			note5.setBorderColor(BaseColor.WHITE);
			note5.setBackgroundColor(new BaseColor(255, 255, 255));
			notesFieldTable.addCell(note5);
			
			PdfPCell note6 = new PdfPCell(new Phrase("  For any change in the booking please contact our office at the above mentioned phone number.  ",font1));
			note6.setBorderColor(BaseColor.WHITE);
			note6.setBackgroundColor(new BaseColor(255, 255, 255));
			notesFieldTable.addCell(note6);
			
			PdfPCell note7 = new PdfPCell(new Phrase("  The Expedition Co., Ltd. Does not hold any responsibility for any of your belongings in the hotel.   ",font1));
			note7.setBorderColor(BaseColor.WHITE);
			note7.setBackgroundColor(new BaseColor(255, 255, 255));
			notesFieldTable.addCell(note7);
	 
	 /*------ Table ---------*/		
			
			PdfPTable notesTable = new PdfPTable(1);
			notesTable.setWidthPercentage(100);
			float[] notesWidth = {2f};
			notesTable.setSpacingBefore(5f);
			notesTable.setSpacingAfter(10f);
			notesTable.setWidths(notesWidth);
			
			PdfPCell noteShow = new PdfPCell(notesFieldTable);
			noteShow.setBorderColor(BaseColor.GRAY);
			noteShow.setBorderWidth(1f);
			noteShow.setBackgroundColor(new BaseColor(255, 255, 255));
			notesTable.addCell(noteShow);
			
		/*------ Table ---------*/		
			
			PdfPTable AddAllTableInMainTable = new PdfPTable(1);
			AddAllTableInMainTable.setWidthPercentage(100);
			float[] AddAllTableInMainTableWidth = {2f};
			AddAllTableInMainTable.setWidths(AddAllTableInMainTableWidth);
		
			PdfPCell BookingAddImg1 = new PdfPCell(BookingAddImg);
			BookingAddImg1.setBorder(Rectangle.NO_BORDER);
			AddAllTableInMainTable.addCell(BookingAddImg1);
			
			PdfPCell hotelVoucherTitlemain1 = new PdfPCell(hotelVoucherTitlemain);
			hotelVoucherTitlemain1.setBorder(Rectangle.NO_BORDER);
			AddAllTableInMainTable.addCell(hotelVoucherTitlemain1);
			
			PdfPCell hotelVoucherMsg1 = new PdfPCell(hotelVoucherMsg);
			hotelVoucherMsg1.setBorder(Rectangle.NO_BORDER);
			AddAllTableInMainTable.addCell(hotelVoucherMsg1);
			
			PdfPCell todayDateTable1 = new PdfPCell(todayDateTable);
			todayDateTable1.setBorder(Rectangle.NO_BORDER);
			AddAllTableInMainTable.addCell(todayDateTable1);
			
			PdfPCell bookingFacilityInfoTable21 = new PdfPCell(bookingFacilityInfoTable2);
			bookingFacilityInfoTable21.setBorder(Rectangle.NO_BORDER);
			AddAllTableInMainTable.addCell(bookingFacilityInfoTable21);
			
			PdfPCell bookingCancellation1 = new PdfPCell(bookingCancellation);
			bookingCancellation1.setBorder(Rectangle.NO_BORDER);
			AddAllTableInMainTable.addCell(bookingCancellation1);
			
			PdfPCell hotelPaymentMainTable1 = new PdfPCell(hotelPaymentMainTable);
			hotelPaymentMainTable1.setBorder(Rectangle.NO_BORDER);
			AddAllTableInMainTable.addCell(hotelPaymentMainTable1);
			
			PdfPCell remarkTable1 = new PdfPCell(remarkTable);
			remarkTable1.setBorder(Rectangle.NO_BORDER);
			AddAllTableInMainTable.addCell(remarkTable1);
			
			PdfPCell customerServiceTable1 = new PdfPCell(customerServiceTable);
			customerServiceTable1.setBorder(Rectangle.NO_BORDER);
			AddAllTableInMainTable.addCell(customerServiceTable1);
			
			PdfPCell notesTable1 = new PdfPCell(notesTable);
			notesTable1.setBorder(Rectangle.NO_BORDER);
			AddAllTableInMainTable.addCell(notesTable1);
			
	/*------ Table ---------*/		
			
			PdfPTable AddMainTable = new PdfPTable(1);
			AddMainTable.setWidthPercentage(100);
			float[] AddMainTableWidth = {2f};
			AddMainTable.setWidths(AddMainTableWidth);
		
			PdfPCell AddAllTableInMainTable1 = new PdfPCell(AddAllTableInMainTable);
			AddAllTableInMainTable1.setPadding(10);
			AddAllTableInMainTable1.setBorderWidth(1f);
			AddMainTable.addCell(AddAllTableInMainTable1);
			
			
			document.open();// PDF document opened........
				        
			
			document.add(AddMainTable); 
			//document.add(Chunk.NEWLINE);
			
			
			response().setContentType("application/pdf");
			response().setHeader("Content-Disposition",
					"inline; filename=");
			document.close();
			System.out.println("Pdf created successfully..");
			File file = new File(fileName);
			return ok(file);
		} catch (Exception e) {
			e.printStackTrace();
			return ok();
		}
		
	}
	
	public static void cancelMail(String email1,HotelBookingDetails hBookingDetails){
		System.out.println("Delete");
	}
	
	@Transactional(readOnly=true)
	public static Result getRoomType(Long supplierCode){
		
		List<HotelRoomTypes> hRoomTypes = HotelRoomTypes.getHotelRoomDetails(supplierCode);
		return ok(Json.toJson(hRoomTypes));
		
	}
	

}
