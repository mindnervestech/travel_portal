package controllers.travelbusiness;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.container.ElementValueChange;
import org.javers.core.diff.changetype.container.ListChange;
import org.javers.core.diff.changetype.container.ValueAdded;

import play.Play;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.travelportal.domain.BookingHistory;
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelBookingDates;
import com.travelportal.domain.HotelBookingDetails;
import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.RoomAndDateWiseRate;
import com.travelportal.domain.RoomRegiterBy;
import com.travelportal.domain.RoomRegiterByChild;
import com.travelportal.domain.Salutation;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RoomAllotedRateWise;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.ChildselectedVM;
import com.travelportal.vm.HotelSearch;
import com.travelportal.vm.PassengerBookingInfoVM;
import com.travelportal.vm.RateDatedetailVM;
import com.travelportal.vm.SearchRateDetailsVM;
import com.travelportal.vm.SerachHotelRoomType;
import com.travelportal.vm.SerachedHotelbyDate;
import com.travelportal.vm.SerachedRoomRateDetail;
import com.travelportal.vm.SerachedRoomType;
import com.travelportal.vm.SpecialsMarketVM;
import com.travelportal.vm.SpecialsVM;

public class HotelBookingController extends Controller {

	@Transactional(readOnly=true)
	public static Result getSalutation() {
		final List<Salutation> salutation = Salutation.getsalutation();
		return ok(Json.toJson(salutation));
	}
	
	
	@Transactional(readOnly=false)
	public static Result saveHotelBookingInfo() {
	
		
		String flagAppro = "";
		
		Form<HotelSearch> HotelForm = Form.form(HotelSearch.class).bindFromRequest();
		HotelSearch searchVM = HotelForm.get();
		
		
		AgentRegistration aRegistration = AgentRegistration.findByIdOnCode(session().get("agent"));
		if(aRegistration.getPaymentMethod().equals("Credit") || aRegistration.getPaymentMethod().equals("Pre-Payment")){
			
			if(aRegistration.getAvailableLimit() != null){
			
				if(aRegistration.getAvailableLimit() > Double.parseDouble(searchVM.hotelBookingDetails.getTotal())){
				
					Double availableLimit = aRegistration.getAvailableLimit() - Double.parseDouble(searchVM.hotelBookingDetails.getTotal());
					aRegistration.setAvailableLimit(availableLimit);
					aRegistration.merge();
					saveBookingData(searchVM);
		
		
				}else{
					flagAppro = "NocreditLimit";
				}
			}else{
				flagAppro = "NullcreditLimit";
			}
		}else{
			saveBookingData(searchVM);
		}

		return ok(Json.toJson(flagAppro));
		
	}
	
	public static void saveBookingData(HotelSearch searchVM){
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		HotelBookingDetails hDetailsOld = null;
		changeBookingValueVM cVmOld = null;
		List<HotelBookingDates> hBookingDatesOld = null; 
		
		String appComp = null;
		
		if(searchVM.bookingId != ""){
			hBookingDatesOld = HotelBookingDates.getDateBybookingId(Long.parseLong(searchVM.bookingId));
			
			hDetailsOld = HotelBookingDetails.findBookingById(Long.parseLong(searchVM.bookingId));
			cVmOld = new changeBookingValueVM();
			cVmOld.checkIn = format.format(hDetailsOld.getCheckIn());
			cVmOld.checkOut = format.format(hDetailsOld.getCheckOut());
			cVmOld.roomId = hDetailsOld.getRoomId();
			cVmOld.roomName = hDetailsOld.getRoomName();
			cVmOld.roomCount = hDetailsOld.getNoOfroom();
		}
		
		
		HotelBookingDetails hBookingDetails;
		if(searchVM.bookingId != ""){
			hBookingDetails = HotelBookingDetails.findBookingById(Long.parseLong(searchVM.bookingId));
		}else{
			hBookingDetails=new HotelBookingDetails();
		}
		
		
		AgentRegistration agRegistration = AgentRegistration.getAgentCode(session().get("agent"));
		
		hBookingDetails.setAgentId(Long.parseLong(session().get("agent")));
		hBookingDetails.setHotelNm(searchVM.getHotelNm());
		hBookingDetails.setHotelAddr(searchVM.getHotelAddr());
		hBookingDetails.setSupplierCode(searchVM.getSupplierCode());
		hBookingDetails.setAgentNm(agRegistration.getFirstName());
		hBookingDetails.setAgentCompanyNm(agRegistration.getCompanyName());
		hBookingDetails.setSupplierNm(searchVM.getSupplierNm());
		hBookingDetails.setTotalNightStay(searchVM.getDatediff());
		for(SerachHotelRoomType byRoom:searchVM.hotelbyRoom){
			if(byRoom.nonRefund == false){
			for(SerachedRoomRateDetail searchByRoom:byRoom.hotelRoomRateDetail){
					int canellfirst = 0;
					for(CancellationPolicyVM canellationP:searchByRoom.cancellation){
						if(canellfirst == 0){
						 hBookingDetails.setCancellationNightsCharge(canellationP.nights);
						 
						 Calendar c = Calendar.getInstance();
							try {
								c.setTime(format.parse(searchVM.getCheckIn()));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							c.add(Calendar.DATE, - Integer.parseInt(canellationP.days));  // number of days to add
							String cancellDate = format.format(c.getTime());
							try {
								hBookingDetails.setLatestCancellationDate(format.parse(cancellDate));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						 
						 canellfirst++;
						} 
					}
			}
		}
		}
		//hBookingDetails.setCancellationNightsCharge(searchVM.getHotelbyRoom());
		
		//CancellationDateDiff canDateDiff = CancellationDateDiff.getById(1);
		
		
		/*Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(searchVM.getCheckIn()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		c.add(Calendar.DATE, - canDateDiff.getDateDiff());  // number of days to add
		String cancellDate = format.format(c.getTime());
		try {
			hBookingDetails.setLatestCancellationDate(format.parse(cancellDate));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
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
		if(searchVM.hotelBookingDetails.getNoOfchild().equals("NoChild")){
			hBookingDetails.setNoOfchild(0);
		}else if(searchVM.hotelBookingDetails.getNoOfchild() == null){
			hBookingDetails.setNoOfchild(0);
		}else if(searchVM.hotelBookingDetails.getNoOfchild() == ""){
			hBookingDetails.setNoOfchild(1);
		}else{
			hBookingDetails.setNoOfchild(Integer.parseInt(searchVM.hotelBookingDetails.getNoOfchild()));
		}
		
		hBookingDetails.setTotal(Double.parseDouble(searchVM.hotelBookingDetails.getTotal()));
		hBookingDetails.setTravellersalutation(Salutation.getsalutationIdIdByCode(Integer.parseInt(searchVM.hotelBookingDetails.getTravellersalutation())));
		hBookingDetails.setTravellerfirstname(searchVM.hotelBookingDetails.getTravellerfirstname());
		hBookingDetails.setTravellermiddlename(searchVM.hotelBookingDetails.getTravellermiddlename());
		hBookingDetails.setTravellerpassportNo(searchVM.hotelBookingDetails.getTravellerpassportNo());
		hBookingDetails.setTravelleremail(searchVM.hotelBookingDetails.getTravelleremail());
		hBookingDetails.setTravellerlastname(searchVM.hotelBookingDetails.getTravellerlastname());
		hBookingDetails.setTravelleraddress(searchVM.hotelBookingDetails.getTravelleraddress());
		hBookingDetails.setTravellercountry(Country.getCountryByCode(searchVM.countryCode));
		hBookingDetails.setTravellerphnaumber(searchVM.hotelBookingDetails.getTravellerphnaumber());
		hBookingDetails.setNonSmokingRoom(searchVM.hotelBookingDetails.getNonSmokingRoom());
		hBookingDetails.setTwinBeds(searchVM.hotelBookingDetails.getTwinBeds());
		hBookingDetails.setLateCheckout(searchVM.hotelBookingDetails.getLateCheckout());
		hBookingDetails.setLargeBed(searchVM.hotelBookingDetails.getLargeBed());
		hBookingDetails.setHighFloor(searchVM.hotelBookingDetails.getHighFloor());
		hBookingDetails.setEarlyCheckin(searchVM.hotelBookingDetails.getEarlyCheckin());
		hBookingDetails.setAirportTransfer(searchVM.hotelBookingDetails.getAirportTransfer());
		hBookingDetails.setAirportTransferInfo(searchVM.hotelBookingDetails.getAirportTransferInfo());
		hBookingDetails.setEnterComments(searchVM.hotelBookingDetails.getEnterComments());
		hBookingDetails.setSmokingRoom(searchVM.hotelBookingDetails.getSmokingRoom());
		hBookingDetails.setHandicappedRoom(searchVM.hotelBookingDetails.getHandicappedRoom());
		hBookingDetails.setWheelchair(searchVM.hotelBookingDetails.getWheelchair());
		hBookingDetails.setPayment("Outstanding");
		
		
		for(SerachHotelRoomType byRoom:searchVM.hotelbyRoom){
			if(byRoom.nonRefund == true){
			hBookingDetails.setNonRefund("true");
			}else{
				hBookingDetails.setNonRefund("false");
			}
			hBookingDetails.setRoomId(byRoom.roomId);
			hBookingDetails.setRoomName(byRoom.roomName);
			if(byRoom.specials != null){
				hBookingDetails.setApplyPromotion(searchVM.hotelBookingDetails.applyPromo);
			for(SpecialsVM speci:byRoom.specials){
				if(speci.promotionType.equals("nightPromotion")){
					hBookingDetails.setPromotionname(speci.promotionName);
					for(SpecialsMarketVM smarket:speci.markets){
						hBookingDetails.setPayDays_inpromotion(Integer.parseInt(smarket.payDays));
						hBookingDetails.setStayDays_inpromotion(Integer.parseInt(smarket.stayDays));
						hBookingDetails.setTypeOfStay_inpromotion(smarket.typeOfStay);
					}
				}
				if(speci.promotionType.equals("flatPromotion")){
					hBookingDetails.setPromotionname(speci.promotionName);
					for(SpecialsMarketVM smarket:speci.markets){
						hBookingDetails.setFlatRateInPro(smarket.flatRate);
					}
				}
				if(speci.promotionType.equals("birdPromotion")){
					hBookingDetails.setPromotionname(speci.promotionName);
					for(SpecialsMarketVM smarket:speci.markets){
						hBookingDetails.setEarlyBirdPro(Integer.parseInt(smarket.earlyBird));
						hBookingDetails.setEarlyBirdDiscountPro(Double.parseDouble(smarket.earlyBirdDisount));
						hBookingDetails.setApplicableBirdPro(smarket.earlyBirdRateCalculat);
					}
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
							hBookingDetails.setRate(RateMeta.findById(rateObj.id));
						if(rateObj.availableRoom < Integer.parseInt(searchVM.hotelBookingDetails.getNoOfroom())){
							hBookingDetails.setRoom_status("on request");
						}else{
							hBookingDetails.setRoom_status("Confirm pending");
						}
						}else{
							hBookingDetails.setRoom_status("Confirm pending");
						}
					}
				}
			}
			}
			
		}
		
		if(searchVM.bookingId != ""){
			hBookingDetails.merge();
		}else{
			AgentRegistration aRegistration = AgentRegistration.findByIdOnCode(session().get("agent"));
			String companyTwoChar = aRegistration.getCompanyName().substring(0, 2);
			
			String[] dateEdit;
			dateEdit = searchVM.getCheckIn().split("-");
			
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(10000);
			
			String newBookingId = companyTwoChar +"-"+dateEdit[0]+dateEdit[1]+dateEdit[2].substring(2, 4)+"-"+randomInt;
			hBookingDetails.setBookingId(newBookingId);
			hBookingDetails.setUuId(UUID.randomUUID().toString());
			hBookingDetails.save();
		}
		
		
		if(hBookingDetails.getRoom_status().equals("on request")){
			onRequestMail(hBookingDetails.getTravelleremail());
		}
		if(hBookingDetails.getRoom_status().equals("Confirm pending")){
			generatePDF1(searchVM,hBookingDetails.getId());
		}
		
		
		if(searchVM.bookingId != ""){
			changeBookingValueVM cVmNew = new changeBookingValueVM();
			cVmNew.checkIn = format.format(hBookingDetails.getCheckIn());
			cVmNew.checkOut = format.format(hBookingDetails.getCheckOut());
			cVmNew.roomId = hBookingDetails.getRoomId();
			cVmNew.roomName = hBookingDetails.getRoomName();
			cVmNew.roomCount = hBookingDetails.getNoOfroom();
			
			List<RoomRegiterBy> regiterByOldObj = RoomRegiterBy.getRoomInfoByBookingId(Long.parseLong(searchVM.bookingId));
			List<RoomRegiterBy> regiterByOldObjDelete = RoomRegiterBy.getRoomInfoByBookingId(Long.parseLong(searchVM.bookingId));
			for(RoomRegiterBy roomRegiterBy:regiterByOldObjDelete){
				List<RoomAndDateWiseRate> rDateWiseRate = RoomAndDateWiseRate.getRoomRateInfoByRoomId(roomRegiterBy.getId());
				for(RoomAndDateWiseRate rdr:rDateWiseRate){
					rdr.delete();
				}
				List<RoomRegiterByChild> rByChilds = RoomRegiterByChild.getRoomChildInfoByRoomId(roomRegiterBy.getId());
				for(RoomRegiterByChild rrBC:rByChilds){
					rrBC.delete();
				}
				roomRegiterBy.delete();
			}
			
			roomRegiFunction(searchVM, Long.parseLong(searchVM.bookingId));
		/*	for(PassengerBookingInfoVM passBookingInfoVM:searchVM.hotelBookingDetails.passengerInfo){
				RoomRegiterBy regiterBy = new RoomRegiterBy();
				regiterBy.setAdult(passBookingInfoVM.adult);
				regiterBy.setRegiterBy(passBookingInfoVM.regiterBy);
				regiterBy.setTotal(passBookingInfoVM.total);
				if(passBookingInfoVM.noOfchild != null){
					regiterBy.setNoOfchild(Integer.parseInt(passBookingInfoVM.noOfchild));
				}
				regiterBy.setHotelBookingDetails(HotelBookingDetails.findBookingById(Long.parseLong(searchVM.bookingId)));
				regiterBy.setRoomIndex(i);
				i++;
				regiterBy.save();
			}*/	
			
			List<RoomRegiterBy> regiterByNewObjDelete = RoomRegiterBy.getRoomInfoByBookingId(Long.parseLong(searchVM.bookingId));
			changeValueInData cInData = new changeValueInData();  
			appComp = "0";
			compare(cVmOld, cVmNew, cInData, appComp);
			appComp = "1";
			compare(regiterByOldObj, regiterByNewObjDelete, cInData, appComp);
			 Gson gson = new Gson(); 
			 String json = gson.toJson(cInData);
			 
			 BookingHistory bookingH = new BookingHistory();
			 bookingH.setJsonData(json);
			 bookingH.setHotelBookingDetails(HotelBookingDetails.findBookingById(Long.parseLong(searchVM.bookingId)));
			 bookingH.save();
			
		}else{
			roomRegiFunction(searchVM, hBookingDetails.getId());
		}
		/*for(PassengerBookingInfoVM passBookingInfoVM:searchVM.hotelBookingDetails.passengerInfo){
			RoomRegiterBy regiterBy = new RoomRegiterBy();
			regiterBy.setAdult(passBookingInfoVM.adult);
			regiterBy.setRegiterBy(passBookingInfoVM.regiterBy);
			regiterBy.setTotal(passBookingInfoVM.total);
			if(passBookingInfoVM.noOfchild != null){
				regiterBy.setNoOfchild(Integer.parseInt(passBookingInfoVM.noOfchild));
			}
			regiterBy.setHotelBookingDetails(HotelBookingDetails.findBookingById(hBookingDetails.getId()));
			regiterBy.setRoomIndex(i);
			i++;
			regiterBy.save();
			for(ChildselectedVM chVm:passBookingInfoVM.childselected){
				RoomRegiterByChild regiterByChild = new RoomRegiterByChild();
				if(chVm.age != null){
				 regiterByChild.setAge(Integer.parseInt(chVm.age));
				}
				regiterByChild.setBreakfast(chVm.breakfast);
				if(!chVm.childRate.equals("")){
				regiterByChild.setChild_rate(Double.parseDouble(chVm.childRate));
				}
				regiterByChild.setFree_child(chVm.freeChild);
				regiterByChild.setRoomRegiterBy(RoomRegiterBy.getRoomInfoById(regiterBy.getId()));
				regiterByChild.save();
				
			}
			
			for(RateDatedetailVM rDatedetailVM:passBookingInfoVM.rateDatedetail){
				RoomAndDateWiseRate rWiseRate = new RoomAndDateWiseRate();
				rWiseRate.setCurrency(rDatedetailVM.currency);
				rWiseRate.setDate(rDatedetailVM.date);
				rWiseRate.setDay(rDatedetailVM.day);
				rWiseRate.setFulldate(rDatedetailVM.fulldate);
				rWiseRate.setMeal(rDatedetailVM.meal);
				rWiseRate.setMonth(rDatedetailVM.month);
				if(rDatedetailVM.rate != null){
				  rWiseRate.setRate(Double.parseDouble(rDatedetailVM.rate));
				}
				rWiseRate.setRoomRegiterBy(RoomRegiterBy.getRoomInfoById(regiterBy.getId()));
				rWiseRate.save();
			}
			
		}
		*/
		
		if(searchVM.bookingId != ""){
			List<HotelBookingDates> hDates = HotelBookingDates.getDateBybookingId(Long.parseLong(searchVM.bookingId));
			for(HotelBookingDates dates:hDates){
				dates.delete();
			}
		}
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
						hBookingDates.setAllotmentmarket(rateObj.allotmentmarket.allocation);
					}
					
				}
			}
		}
			hBookingDates.setBookingId(HotelBookingDetails.findBookingById(hBookingDetails.getId()));
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
		
		int roomCountFlag = 0;
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
									if(searchVM.bookingId != "" && roomCountFlag == 0){
										//hDetailsOld.getNoOfroom();
										List<RoomAllotedRateWise> rWise = RoomAllotedRateWise.findAllDate();
									for(RoomAllotedRateWise rateWise:rWise){
										int rcount1 = 0;
										for(HotelBookingDates hBookingDates:hBookingDatesOld){
											
												if(hBookingDates.getBookingDate().compareTo(rateWise.getAllowedRateDate())==0){
													rcount1 =  rateWise.getRoomCount() - cVmOld.roomCount;
													rateWise.setRoomCount(rcount1);
													rateWise.merge();
												}
											
										}
									}
										try {
											rAllotedRateWise= RoomAllotedRateWise.findByRateIdandDate(rateObj.id, format.parse(date.getDate()));
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										roomCountFlag  = 1;
									}
									
									//rAllotedRateWise= RoomAllotedRateWise.findByRateIdandDate(rateObj.id, format.parse(date.getDate()));
									int rcount =  rAllotedRateWise.getRoomCount() + Integer.parseInt(searchVM.hotelBookingDetails.noOfroom);
									rAllotedRateWise.setRoomCount(rcount);
									rAllotedRateWise.merge();
								}
							}else if(searchVM.bookingId != "" && roomCountFlag == 0){
								
								
								List<RoomAllotedRateWise> rWise = RoomAllotedRateWise.findAllDate();
								for(RoomAllotedRateWise rateWise:rWise){
									int rcount1 = 0;
									for(HotelBookingDates hBookingDates:hBookingDatesOld){
										if(hBookingDates.getAllotmentmarket() == 3){
											if(hBookingDates.getBookingDate().compareTo(rateWise.getAllowedRateDate())==0){
												rcount1 =  rateWise.getRoomCount() - cVmOld.roomCount;
												rateWise.setRoomCount(rcount1);
												rateWise.merge();
											}
										}
									}
								}
								
								roomCountFlag  = 1;
							}
						}	
					}
				}
			}
		}
	}
	
	private static void roomRegiFunction(HotelSearch searchVM,long bookingId) {
		
		int i=1;
		
		for(PassengerBookingInfoVM passBookingInfoVM:searchVM.hotelBookingDetails.passengerInfo){
			RoomRegiterBy regiterBy = new RoomRegiterBy();
			regiterBy.setAdult(passBookingInfoVM.adult);
			regiterBy.setRegiterBy(passBookingInfoVM.regiterBy);
			regiterBy.setTotal(passBookingInfoVM.total);
			if(passBookingInfoVM.noOfchild != null){
				regiterBy.setNoOfchild(Integer.parseInt(passBookingInfoVM.noOfchild));
			}
			regiterBy.setHotelBookingDetails(HotelBookingDetails.findBookingById(bookingId));
			regiterBy.setRoomIndex(i);
			i++;
			regiterBy.save();
			if(passBookingInfoVM.childselected != null){
			
				for(ChildselectedVM chVm:passBookingInfoVM.childselected){
					RoomRegiterByChild regiterByChild = new RoomRegiterByChild();
					if(chVm.age != null && chVm.age != ""){
						regiterByChild.setAge(Integer.parseInt(chVm.age));
					}
					regiterByChild.setBreakfast(chVm.breakfast);
					if(!chVm.childRate.equals("")){
						regiterByChild.setChild_rate(Double.parseDouble(chVm.childRate));
					}
					regiterByChild.setFree_child(chVm.freeChild);
					regiterByChild.setRoomRegiterBy(RoomRegiterBy.getRoomInfoById(regiterBy.getId()));
					regiterByChild.save();
				
				}
			}
			for(RateDatedetailVM rDatedetailVM:passBookingInfoVM.rateDatedetail){
				RoomAndDateWiseRate rWiseRate = new RoomAndDateWiseRate();
				rWiseRate.setCurrency(rDatedetailVM.currency);
				rWiseRate.setDate(rDatedetailVM.date);
				rWiseRate.setDay(rDatedetailVM.day);
				rWiseRate.setFulldate(rDatedetailVM.fulldate);
				rWiseRate.setMeal(rDatedetailVM.meal);
				rWiseRate.setMonth(rDatedetailVM.month);
				if(rDatedetailVM.rate != null){
				  rWiseRate.setRate(Double.parseDouble(rDatedetailVM.rate));
				}
				rWiseRate.setRoomRegiterBy(RoomRegiterBy.getRoomInfoById(regiterBy.getId()));
				rWiseRate.save();
			}
			
		}
		
	}
	
	private static void compare(Object o, Object n, changeValueInData cInData, String appComp) {
		Javers javers = JaversBuilder.javers().build();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		Diff diff = javers.compare(o, n);

		System.out.println("===" + diff.getChanges().size());
		System.out.println("diff= " + diff);
	   List<changeValueVM> list = new ArrayList<changeValueVM>();
		int i=0;
		  
		List<changeValueInData> listJosnOld = new ArrayList<>();
	   List<changeValueInData> listJosnNew = new ArrayList<>();
	   //Map<String, String> mapNewJosn = new HashMap<String, String>();
	   if(appComp.equals("1")){
		    
		List<changeValueVM> valueOld = new ArrayList<>();
		List<changeValueVM> valueNew = new ArrayList<>();
		List<ListChange> changes = diff.getChangesByType(ListChange.class);
		 for(ListChange c:changes){
			
			 for(i=0;i< c.getChanges().size();i++){
				 RoomRegiterBy rRbOld = null;
				 RoomRegiterBy rRbNew = null;
				 try {
					 ElementValueChange change = (ElementValueChange)c.getChanges().get(i);
					 rRbOld = (RoomRegiterBy)change.getLeftValue();
					 rRbNew = (RoomRegiterBy)change.getRightValue();
				 } catch(ClassCastException e) {
									 
					/* if(c.getChanges().get(i).getClass().getSimpleName().equals("ValueRemoved")){
						 ValueRemoved change = (ValueRemoved)c.getChanges().get(i);
						 rRbOld = (RoomRegiterBy)change.getValue();
					 }*/
					 
					 if(c.getChanges().get(i).getClass().getSimpleName().equals("ValueAdded")){
						 ValueAdded change = (ValueAdded)c.getChanges().get(i);
						 rRbNew = (RoomRegiterBy)change.getValue();
					 }
				 }
				 changeValueVM chVm = new changeValueVM();
				 if(rRbOld !=null) {
					 chVm.room = i+1;
					 chVm.adult = rRbOld.getAdult();
					 chVm.noOfchild = rRbOld.getNoOfchild();
					 chVm.total = rRbOld.getTotal();
					 valueOld.add(chVm);
				 }
				 
				 changeValueVM chVm1 = new changeValueVM();
				 if(rRbNew !=null) {
					
					 chVm1.room = i+1;
					 chVm1.adult = rRbNew.getAdult();
					 chVm1.noOfchild = rRbNew.getNoOfchild();
					 chVm1.total = rRbNew.getTotal();
					 valueNew.add(chVm1);
				 }
				 
			 }
			 cInData.oldValue = valueOld;
			 cInData.NewValue = valueNew;
			// cData.add(cInData);
			 System.out.println(valueOld.toString());
    	 }
	   }else if(appComp.equals("0")){
		   List<changeSigleValueVM> chValueVMs = new ArrayList<>();
		   List<ValueChange> changes = diff.getChangesByType(ValueChange.class);
			 for(ValueChange c:changes){
				 changeSigleValueVM chVm = new changeSigleValueVM();
				 if(c.getProperty().getName() != "beanLoaderIndex"){
					 chVm.property = c.getProperty().getName();
					 chVm.oldVal = c.getLeft().toString(); 
					 chVm.newVal = c.getRight().toString();
					 chValueVMs.add(chVm);
				 }	
	    	 }
			 cInData.fildChanges = chValueVMs;
	   }
	   System.out.println(cInData);
		/* Gson gson = new Gson(); 
		 String json = gson.toJson(list);*/
		 
	
	}
	
	public static class changeValueVM {
		public String property;
		public String adult;
		public int room;
		public int noOfchild;
		public String total;
		
	}
	
	public static class changeValueInData {
		public List<changeSigleValueVM> fildChanges;
		public List<changeValueVM> oldValue;
		public List<changeValueVM> NewValue;
		
	}
	
	public static class changeBookingValueVM {
		public Long roomId;
		public String checkIn;
		public String checkOut;
		public String roomName;
		public int roomCount;
	}
	
	
	public static class changeSigleValueVM {
		public String property;
		public String oldVal;
		public String newVal;
		
	}
	
	
	public static void onRequestMail(String email){
		/*final String username=Play.application().configuration().getString("username");
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
 		   
  			Message feedback = new MimeMessage(session);
  			feedback.setFrom(new InternetAddress(username));
  			feedback.setRecipients(Message.RecipientType.TO,
  			InternetAddress.parse(email));
  			feedback.setSubject("On Request Booking");	  			
  			 BodyPart messageBodyPart = new MimeBodyPart();	  	       
  			messageBodyPart.setText("Thank you for booking with theexpeditionthailand.  Your booking is being processed and our team will send you your hotel voucher within the next hour.  Please present it to the hotel when you check in. \n\n We wish you a pleasant stay. \n\n The theexpeditionthailand.com team.");
  			//messageBodyPart.setText("You Your Agent Code : "+aRegistration.getAgentCode() +"Password :"+aRegistration.getPassword());	  	    
  	         Multipart multipart = new MimeMultipart();	  	    
  	         multipart.addBodyPart(messageBodyPart);	            
  	         feedback.setContent(multipart);
  		     Transport.send(feedback);
       		} catch (MessagingException e) {
  			  throw new RuntimeException(e);
  		}*/
	}
	
	
	@Transactional(readOnly=false)
	public static Result generatePDF() {
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		final String rootDir = Play.application().configuration().getString("mail.storage.path");
		
		File f = new File(rootDir);
		if(!f.exists()){
			f.mkdir();
		}
		
		HotelBookingDetails hBookingDetails = HotelBookingDetails.findBookingById(2);
		
		/*Rectangle layout = new Rectangle(PageSize.A4);
	    layout.setBorderColor(BaseColor.BLACK);  //Border color
	    layout.setBorderWidth(6);      //Border width  
	    layout.setBorder(Rectangle.BOX);  //Border on 4 sides
*/		
		
		Document document = new Document();
		try {
			
			String fileName = "C://hotelVoucher"+".pdf"; // rootDir+"/hotelVoucher"+".pdf"
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
	
	public static void createDir(String rootDir, long supplierCode, String bookingID) {
        File file3 = new File(rootDir + File.separator+"BookingVoucherDocuments"+File.separator+bookingID);
        if (!file3.exists()) {
                file3.mkdirs();
        }
	}
  public static void generatePDF1(HotelSearch searchVM,Long bookingId) {
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		final String rootDir = Play.application().configuration().getString("mail.storage.path");
		
		File f = new File(rootDir);
		if(!f.exists()){
			f.mkdir();
		}
		
		HotelBookingDetails hBookingDetails = HotelBookingDetails.findBookingById(bookingId);
		
		createDir(rootDir,hBookingDetails.getSupplierCode(),hBookingDetails.getBookingId());
		
		
		Document document = new Document();
		try {
			
			String fileName = "HotelVoucher"+".pdf";
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
			
			final String RESOURCE = rootDir+"/"	+"TagEXP.jpg";
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
			
			PdfPCell note = new PdfPCell(new Phrase("Nots",font2));
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
			
			response().setContentType("application/pdf");
			response().setHeader("Content-Disposition",
					"inline; filename=");
			document.close();
			System.out.println("Pdf created successfully..");
			File file = new File(fileName);
			
			
			//FilePart picture = request().body().asMultipartFormData().getFile(file);
			
		
			 String pdfVoucherPath = rootDir + File.separator+"BookingVoucherDocuments"+File.separator+hBookingDetails.getBookingId()+ File.separator+"HotelVoucher.pdf";
			
	         
	         OutputStream out = null;
	        // BufferedImage image = null;
	         File fsave = new File(pdfVoucherPath);
	         try {
	        	 Files.copy(file.toPath(),fsave.toPath(),java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	        	
	         } catch (FileNotFoundException e) {
	                 e.printStackTrace();
	         } catch (IOException e) {
	                 e.printStackTrace();
	         } finally {
	                 try {
	                         if(out != null) out.close();
	                 } catch (IOException e) {
	                         e.printStackTrace();
	                 }
	         }
			
			
			
			final String username=Play.application().configuration().getString("username");
	        final String password=Play.application().configuration().getString("password");
	           
	        Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("yogeshpatil424@gmail.com", "337@yogesh");
				}
			});
			try
			{
				/*MimeBodyPart attachPart = new MimeBodyPart();*/
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("yogeshpatil424@gmail.com","CheckInRooms"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(searchVM.getHotel_email()));
				message.setSubject("Confirmation Of Booking");
				Multipart multipart = new MimeMultipart();
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart = new MimeBodyPart();
				
				VelocityEngine ve = new VelocityEngine();
				ve.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.Log4JLogChute" );
				ve.setProperty("runtime.log.logsystem.log4j.logger","clientService");
				ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
				ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
				ve.init();
			
				
		        Template t = ve.getTemplate("/public/emailTemplet/beforeConfirmationSupplier.vm"); 
		        VelocityContext context = new VelocityContext();
		        context.put("uuId", hBookingDetails.getUuId());
		        context.put("agentName",hBookingDetails.getAgentNm());
		        context.put("searchVM",searchVM);
		        
		        String[] countAdult;
		        int adult = 0;
		        int child = 0;
		        for(PassengerBookingInfoVM pInfoVM:searchVM.hotelBookingDetails.passengerInfo){
		        	countAdult = pInfoVM.adult.split(" ");
		        	adult = adult + Integer.parseInt(countAdult[0]);
		        	if(pInfoVM.noOfchild != null){
		        	
		        		child = child + Integer.parseInt(pInfoVM.noOfchild);
		        	}
		        }
		        
		        context.put("adultCount",adult);
		        context.put("childCount",child);
		       
		        if (searchVM.hotelBookingDetails.nonSmokingRoom != null){
		        	if (searchVM.hotelBookingDetails.nonSmokingRoom.equals("true")) {
		        		context.put("nonSmokingRoom","Yes");
		        	}else{
		        		context.put("nonSmokingRoom","No");
		        	}
		        }else{
		        	context.put("nonSmokingRoom","No");
		        }
				
				
				if(searchVM.hotelBookingDetails.earlyCheckin != null){
					if (searchVM.hotelBookingDetails.earlyCheckin.equals("true")) {
						context.put("earlyCheckin","Yes");
					}else{
						context.put("earlyCheckin","No");
					}
				}else{
		        	context.put("earlyCheckin","No");
		        }
				
				if(searchVM.hotelBookingDetails.handicappedRoom != null){
					if (searchVM.hotelBookingDetails.handicappedRoom.equals("true")) {
						context.put("handicappedRoom","Yes");
					}else{
						context.put("handicappedRoom","No");
					}
				}else{
		        	context.put("handicappedRoom","No");
		        }
				
				if (searchVM.hotelBookingDetails.highFloor != null) {
					if (searchVM.hotelBookingDetails.highFloor.equals(true)) {
						context.put("highFloor","Yes");
					}else{
						context.put("highFloor","No");
					}
				}else{
		        	context.put("highFloor","No");
		        }
				
				if(searchVM.hotelBookingDetails.largeBed != null){
					if (searchVM.hotelBookingDetails.largeBed.equals("true")) {
						context.put("largeBed","Yes");
					}else{
						context.put("largeBed","No");	
					}
				}else{
					context.put("largeBed","No");
				}
				
				
				
				if(searchVM.hotelBookingDetails.lateCheckout != null){
					if (searchVM.hotelBookingDetails.lateCheckout.equals("true")) {
						context.put("lateCheckout","Yes");
					}else{
						context.put("lateCheckout","No");
					}
				}else{
					context.put("lateCheckout","No");
				}
				
				if (searchVM.hotelBookingDetails.smokingRoom != null){
					if (searchVM.hotelBookingDetails.smokingRoom.equals("true")) {
						context.put("smokingRoom","Yes");
					}else{
						context.put("smokingRoom","No");
					}
				}else{
					context.put("smokingRoom","No");
				}
				
				
				if (searchVM.hotelBookingDetails.twinBeds != null){
					if (searchVM.hotelBookingDetails.twinBeds.equals("true")) {
						context.put("twinBeds","Yes");
					}else{
						context.put("twinBeds","No");
					}
				}else{
					context.put("twinBeds","No");
				}
				
				if(searchVM.hotelBookingDetails.wheelchair != null){
					if (searchVM.hotelBookingDetails.wheelchair.equals("true")) {
						context.put("wheelchair","Yes");
					}else{
						context.put("wheelchair","No");
					}
				}else{
					context.put("wheelchair","No");
				}
				
				if (searchVM.hotelbyRoom.get(0).extraBedAllowed != null){
					if (searchVM.hotelbyRoom.get(0).extraBedAllowed.equals("true")) {
						context.put("extraBedAllowed","Yes");
					}else{
						context.put("extraBedAllowed","No");
					}
				}else{
					context.put("extraBedAllowed","No");
				}
				
				
			
		        
		        
		        StringWriter writer = new StringWriter();
		        t.merge( context, writer );
		        String content = writer.toString(); 
				
				messageBodyPart.setContent(content, "text/html");
				multipart.addBodyPart(messageBodyPart);
				 /* try {
						attachPart.attachFile(file);
			  	      } catch (IOException e) {
			  	       	// TODO Auto-generated catch block
			  	       		e.printStackTrace();
			  	    }
				 multipart.addBodyPart(attachPart);*/
				message.setContent(multipart);
				Transport.send(message);
				System.out.println("Sent test message successfully....");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			} 
			
			/*-----------------Agent Mail----------------------*/
			AgentRegistration aRegistration = AgentRegistration.findByIdOnCode(session().get("agent"));
			
			try
			{
				//MimeBodyPart attachPart = new MimeBodyPart();
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("yogeshpatil424@gmail.com","337@yogesh"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(aRegistration.getEmailAddr()));
				message.setSubject("Confirmation Of Booking Agent");
				Multipart multipart = new MimeMultipart();
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart = new MimeBodyPart();
				
				VelocityEngine ve = new VelocityEngine();
				ve.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.Log4JLogChute" );
				ve.setProperty("runtime.log.logsystem.log4j.logger","clientService");
				ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
				ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
				ve.init();
			
				
		        Template t = ve.getTemplate("/public/emailTemplet/beforeConfirmationAgent.vm"); 
		        VelocityContext context = new VelocityContext();
		        context.put("uuId", hBookingDetails.getUuId());
		        context.put("agentName",hBookingDetails.getAgentNm());
		        
		        
		        StringWriter writer = new StringWriter();
		        t.merge( context, writer );
		        String content = writer.toString(); 
				
				messageBodyPart.setContent(content, "text/html");
				multipart.addBodyPart(messageBodyPart);
				  /*try {
						attachPart.attachFile(file);
			  	      } catch (IOException e) {
			  	       	// TODO Auto-generated catch block
			  	       		e.printStackTrace();
			  	    }
				 multipart.addBodyPart(attachPart);*/
				message.setContent(multipart);
				Transport.send(message);
				System.out.println("Sent test message successfully....");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			} 
	        
	        
		  
			//return ok(file);
		} catch (Exception e) {
			e.printStackTrace();
			//return ok();
		}
		
	}
	 
}

