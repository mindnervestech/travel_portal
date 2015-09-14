package controllers.travelbusiness;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.travelportal.domain.HotelBookingDetails;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.vm.HotelBookDetailsVM;

public class BookingConfirmRejectController extends Controller {

	final static String rootDir = Play.application().configuration().getString("mail.storage.path");
	
 
	 @Transactional(readOnly=false)
	 public static Result getConfirmationInfo(String bookingId,String confirmationId,String nameConfirm,String status){

		 HotelBookingDetails hBookingDetails = HotelBookingDetails.findBookingIdDetail(bookingId);
		hBookingDetails.setRoom_status(status);
		hBookingDetails.setConfirmationId(confirmationId);
		hBookingDetails.setConfirmProcessUser(nameConfirm);
		hBookingDetails.merge();
		
		 return ok();
	 }
	 
	 @Transactional(readOnly=false)
	 public static Result getBookingInfo(String uuId){
		 HotelBookingDetails hBookingDetails = HotelBookingDetails.finduuIdDetail(uuId);
		 DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		 
		 HotelBookDetailsVM hDetailsVM= new HotelBookDetailsVM();
			hDetailsVM.setId(hBookingDetails.getId());
			hDetailsVM.setBookingId(hBookingDetails.getBookingId());
			hDetailsVM.setUuId(hBookingDetails.getUuId());
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
			hDetailsVM.setLatestCancellationDate(hBookingDetails.getLatestCancellationDate());
			hDetailsVM.setCancellationNightsCharge(hBookingDetails.getCancellationNightsCharge());
			
		 return ok(Json.toJson(hDetailsVM));
	 }
}
