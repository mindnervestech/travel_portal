package controllers.travelbusiness;

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
import com.travelportal.domain.HotelBookingDates;
import com.travelportal.domain.HotelBookingDetails;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.rooms.HotelRoomTypes;
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
			hDetailsVM.setCurrencyId(hBookingDetails.getCurrencyId().getCurrencyCode());
			hDetailsVM.setCurrencyNm(hBookingDetails.getCurrencyId().getCurrencyName());
			hDetailsVM.setTravellerfirstname(hBookingDetails.getTravellerfirstname());
			hDetailsVM.setTravellerlastname(hBookingDetails.getTravellerlastname());
			hDetailsVM.setTravellerphnaumber(hBookingDetails.getTravellerphnaumber());
			hDetailsVM.setTravellercountry(hBookingDetails.getTravellercountry().getCountryCode());
			hDetailsVM.setTypeOfStay_inpromotion(hBookingDetails.getTypeOfStay_inpromotion());
			hDetailsVM.setNonRefund(hBookingDetails.getNonRefund());
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
			aDetailsVMs.add(hDetailsVM);
		}
		//return ok(Json.toJson(aDetailsVMs));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		map.put("results", aDetailsVMs);
		return ok(Json.toJson(map));
	}


	/*----------------------------------------------------------------------------------------------------------------------------------------*/

	@Transactional(readOnly=true)
	public static Result getagentInfobynm(int currentPage,String checkIn,String checkOut,String guest,String status) {

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		//HotelBookDetailsVM hDetailsVM= new HotelBookDetailsVM();       
		List<HotelBookDetailsVM> aDetailsVMs =  new ArrayList<>();
		System.out.println("check in="+checkIn+"CheckOut="+checkOut);


		long totalPages = 0;
		//String status = "available";
		List<HotelBookingDetails> hoteDetails = null;

		if(!checkIn.equals("undefined") && !checkOut.equals("undefined")&&!guest.equals("undefined")){
			try {
				totalPages = HotelBookingDetails.getAllagentTotalDateWise1(5 , Long.parseLong(session().get("agent")) , format.parse(checkIn) , format.parse(checkOut) , status,guest);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				hoteDetails = HotelBookingDetails.getfindByagentDateWise1(Long.parseLong(session().get("agent")), format.parse(checkIn) , format.parse(checkOut), currentPage, 5, totalPages , status,guest);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(checkIn.equals("undefined") && checkOut.equals("undefined") && !guest.equals("undefined")){

			totalPages = HotelBookingDetails.getTotalDateWiseAgentWise1(5 , Long.parseLong(session().get("agent")), status,guest);

			hoteDetails = HotelBookingDetails.getfindByDateWiseAgentWise1(Long.parseLong(session().get("agent")), currentPage, 5, totalPages , status,guest);

		}
		else if(guest.equals("undefined")&&!checkIn.equals("undefined") && !checkOut.equals("undefined"))
		{

			try {
				totalPages = HotelBookingDetails.getTotalDateWiseAgentWise11(5 , Long.parseLong(session().get("agent")), status,format.parse(checkIn),format.parse(checkOut));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				hoteDetails = HotelBookingDetails.getfindByDateWiseAgentWise11(Long.parseLong(session().get("agent")), currentPage, 5, totalPages , status,format.parse(checkIn) , format.parse(checkOut));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
			aDetailsVMs.add(hDetailsVM);
		}
		//return ok(Json.toJson(aDetailsVMs));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		map.put("results", aDetailsVMs);
		return ok(Json.toJson(map));	  
	}













	/*----------------------------------------------------------------------------------------------------------------------------------------*/  






















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

	public static void cancelMail(String email,HotelBookingDetails hBookingDetails){
/*		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		final String username=Play.application().configuration().getString("username");
		final String password=Play.application().configuration().getString("password");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(username, password);
			}
		});

		try{

			Date todayD = new Date();

			Message feedback = new MimeMessage(session);
			feedback.setFrom(new InternetAddress(username));
			feedback.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			feedback.setSubject("On Cancel Booking");	  			
			BodyPart messageBodyPart = new MimeBodyPart();	
			HotelRoomTypes hRoomTypes =HotelRoomTypes.findById(hBookingDetails.getRoomId());
			String[] noAdults;
			noAdults = hBookingDetails.getAdult().split(" ");

			messageBodyPart.setText("Dear "+hBookingDetails.getTravellerfirstname()+" "+hBookingDetails.getTravellerlastname()+",\n\n Please be informed that your booking has been cancelled \n\n -------------------------------------- \n Original Booking Details\n -------------------------------------- \n Booking ID      : "+hBookingDetails.getId()+" \n Cancelled on    : "+df.format(todayD)+" \n Customer First Name : "+hBookingDetails.getTravellerfirstname()+" \n Customer Last Name :"+hBookingDetails.getTravellerlastname()+" \n Hotel        : "+hBookingDetails.getHotelNm()+"  \n Arrival        : "+df.format(hBookingDetails.getCheckIn())+" \n Departure        :"+df.format(hBookingDetails.getCheckOut())+" \n Room Type      : "+hRoomTypes.getRoomType()+" \n Number of Adults   : "+noAdults[0]+"   \n\n -------------------------------------- \n Cancellation Details \n --------------------------------------\n Any cancellation received within 2 days prior to arrival date will incur the first night charge. Failure to arrive at your hotel will be treated as a No-Show and will incur the first night charge (Hotel policy). \n Promotion : Limited Time Offer. Rate includes 12% discount!" +
					"\n\n Refund to credit card : \n\n Any applicable refund should be converted to your local currency by your credit card company. Banks generally take up to 10 business days to process the refund payment and transfer the funds into customers account. Please note, some banks can take up to 15-30 days, or until the next billing cycle. Your statement will ALWAYS show  theexpedition somewhere in the subject line. \n If you want to make a new booking, please visit our website: www.theexpeditionthailand.com \n\n We’re looking forward to seeing you again." +
					"\n\n  theexpedition Customer Support \n ************************************ \n Go Smarter, Go  theexpedition \n  theexpedition Company Pte Ltd \n Tel: +44 (0)20 3027 7900 \n ************************************ \n\n  Internet : http://www.theexpeditionthailand.com\n  theexpedition Customer Support 24/7 : http:// theexpeditionthailand.com/info/contact_theexpedition.html \n ************************************ \n  theexpedition handles bookings for thousands of travel brands and websites  through our global partners network.");
			//messageBodyPart.setText("You Your Agent Code : "+aRegistration.getAgentCode() +"Password :"+aRegistration.getPassword());	  	    
			Multipart multipart = new MimeMultipart();	  	    
			multipart.addBodyPart(messageBodyPart);	            
			feedback.setContent(multipart);
			Transport.send(feedback);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
*/
	}

}
