package com.travelportal.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FilenameUtils;

import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.Markets;
import com.travelportal.domain.MealType;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.ApplicableDateOnRate;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RateSpecialDays;
import com.travelportal.domain.rooms.RateWrapper;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.domain.rooms.RoomChildPolicies;
import com.travelportal.vm.AllocatedCitiesVM;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.NormalRateVM;
import com.travelportal.vm.RateDetailsVM;
import com.travelportal.vm.RateVM;
import com.travelportal.vm.RoomChildpoliciVM;
import com.travelportal.vm.RoomType;
import com.travelportal.vm.RoomtypeVM;
import com.travelportal.vm.SpecialDaysRateVM;
import com.travelportal.vm.SpecialRateVM;

public class HotelRoomController extends Controller {
	
	final static String rootDir = Play.application().configuration().getString("mail.storage.path");
	
	  static {
        createRootDir();
  }

public static void createRootDir() {
        File file = new File(rootDir);
        if (!file.exists()) {
                file.mkdir();
        }
        
}
	
	@Transactional(readOnly=true)
    public static Result hotelRoomHome() {
        return ok(index.render("Your new application is ready."));
    }
	
	@Transactional(readOnly=true)
    public static Result fetchAllRoomTypesForEdit() {
		//this is used when user clicks on edit room type link.
		//get the supplier code from Query param
		/*Long supplierCode = Long.parseLong(request().getQueryString("supplierCode"));
		List<RoomType> roomTypes = HotelRoomTypes.getAllRoomTypes(supplierCode);
		return ok(Json.toJson(roomTypes));*/
		return ok();
	}
	
	@Transactional(readOnly=true)
    public static Result getAllRoomTypes() {
		long code = Long.parseLong(session().get("SUPPLIER"));
		//List<Object[]> types = HotelRoomTypes.getRoomTypes(code);
		List<HotelRoomTypes> types = HotelRoomTypes.getRoomTypesByCode(code);
		return ok(Json.toJson(types));
	}
	
	@Transactional(readOnly=true)
    public static Result getAllRoomTypesByCode() {
		long code = Long.parseLong(session().get("SUPPLIER"));
		List<HotelRoomTypes> types = HotelRoomTypes.getRoomTypesByCode(code);
		List<RoomType> roomsList = new ArrayList<>();
		for(HotelRoomTypes room : types) {
			RoomType type = new RoomType();
			type.roomType = room.getRoomType();
			type.roomId = room.getRoomId();
			roomsList.add(type);
		}
		
		return ok(Json.toJson(roomsList));
	}
	
	@Transactional(readOnly=true)
    public static Result getCurrency() {
		List<Currency> list = Currency.getCurrency();
		return ok(Json.toJson(list));
	}
	
	@Transactional(readOnly=true)
    public static Result getMealTypes() {
		List<MealType> list = MealType.getmealtypes();
		return ok(Json.toJson(list));
	}
	
	
	@Transactional(readOnly=true)
    public static Result getSpecialObject(Long roomType) {
		int maxAdults = HotelRoomTypes.getHotelRoomMaxAdultOccupancy(roomType);
		SpecialDaysRateVM specialDaysRateVM = new SpecialDaysRateVM();
		for(int i=1;i<=maxAdults;i++) {
			RateDetailsVM rateDetail = new RateDetailsVM();
			if(i != 1) {
				rateDetail.name = i+" Adults";
			} else {
				rateDetail.name = "1 Adult";
			}
			
			rateDetail.includeMeals = false;
			rateDetail.meals ="Lunch";
			rateDetail.onlineMeals = "Lunch";
			specialDaysRateVM.rateDetails.add(rateDetail);
		}
		
		CancellationPolicyVM cancel = new CancellationPolicyVM();
		
		cancel.penaltyCharge = true;
		specialDaysRateVM.cancellation.add(cancel);
		
		return ok(Json.toJson(specialDaysRateVM));
		
	}
	
	@Transactional(readOnly=true)
    public static Result getRateObject(Long roomType) {
		
		int maxAdults = HotelRoomTypes.getHotelRoomMaxAdultOccupancy(roomType);
		
		NormalRateVM normal = new NormalRateVM();
		SpecialRateVM special = new SpecialRateVM();
		SpecialDaysRateVM specialDaysRateVM = new SpecialDaysRateVM();
		for(int i=1;i<=maxAdults;i++) {
			RateDetailsVM rateDetail = new RateDetailsVM();
			if(i != 1) {
				rateDetail.name = i+" Adults";
			} else {
				rateDetail.name = "1 Adult";
			}
			
			rateDetail.includeMeals = false;
			rateDetail.meals ="Lunch";
			rateDetail.onlineMeals = "Lunch";
			normal.rateDetails.add(rateDetail);
			special.non_refund = true;
			special.rateDetails.add(rateDetail);
			
			specialDaysRateVM.rateDetails.add(rateDetail);
		}
		
		
		CancellationPolicyVM cancel = new CancellationPolicyVM();
		
		cancel.penaltyCharge = true;
		specialDaysRateVM.non_refund = true;
		specialDaysRateVM.cancellation.add(cancel);
		
		special.cancellation.add(cancel);
		
		RateVM rateVM = new RateVM();
		rateVM.normalRate = normal;
		rateVM.isSpecialRate = 0;
		rateVM.isSpecialDaysRate = 0;
		rateVM.specialDaysRate.add(specialDaysRateVM);
		rateVM.special = special;
		rateVM.cancellation.add(cancel);
		rateVM.applyToMarket = true;
		rateVM.non_refund = true;
		return ok(Json.toJson(rateVM));
	}
	
	
	@Transactional(readOnly=false)
    public static Result saveRate() throws ParseException {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		Form<RateWrapper> rateWrapperForm = Form.form(RateWrapper.class).bindFromRequest();
		List<RateVM> list = rateWrapperForm.get().rateObject;
		
		
		for(RateVM rate : list) {

			RateMeta rMeta = RateMeta.getRateName(rate.rateName, rate.supplierCode);
			if(rMeta == null){
				RateMeta rateMeta = new RateMeta();
				rateMeta.setSupplierCode(rate.supplierCode);
				rateMeta.setCurrency(rate.currency);
				rateMeta.setRateName(rate.rateName);
				rateMeta.setStatus("pending");
				rateMeta.setRoomType(HotelRoomTypes.findById(rate.roomId));
				
				rateMeta.save();
				
				
				Date formDate = null;
    			Date toDates = null;
    			try {
    				formDate = format.parse(rate.fromDate);
    				toDates = format.parse(rate.toDate);
    			} catch (ParseException e) { // TODO Auto-generated catch block
    				e.printStackTrace();
    			}

    			Calendar c = Calendar.getInstance();
    			c.setTime(formDate);
    			c.set(Calendar.MILLISECOND, 0);
    			
    			long dayDiff;
    			if(toDates.getTime() == formDate.getTime()){
    				dayDiff = 1;
    			}else{
    				long diff = toDates.getTime() - formDate.getTime();

    				dayDiff = diff / (1000 * 60 * 60 * 24);
    			}
    			
    			for (int i = 0; i <= dayDiff; i++) {
    			
    				ApplicableDateOnRate aDateOnRate = new ApplicableDateOnRate();
    				
    				aDateOnRate.setDate(c.getTime());
    				aDateOnRate.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
    				aDateOnRate.save();
    				    				
    			c.add(Calendar.DATE, 1);
    				
    			}
    			

				
				RateMeta rateObject = RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId));
				
				List<SelectedCountryVM> selectedCityVM = new ArrayList<>(); 	
				for(AllocatedCitiesVM vm: rate.allocatedCities) {
					if(vm.multiSelectGroup == false && vm.name != null){
						SelectedCountryVM cityVM = new SelectedCountryVM();
						cityVM.name = vm.name;
						cityVM.ticked = vm.ticked;
						selectedCityVM.add(cityVM);
					}
				}
				List<Country> listCity = new ArrayList<>();
				for(SelectedCountryVM cityvm : selectedCityVM){
					Country _city = Country.getCountryByName(cityvm.name);
					
					if(cityvm.ticked){
						listCity.add(_city);
					}
				}
				rateObject.setCountry(listCity);
				
				if(rate.allotmentmarket != null){
				
				AllotmentMarket allotmentmarket = new AllotmentMarket();
				
				allotmentmarket.setPeriod(rate.allotmentmarket.period);
				allotmentmarket.setSpecifyAllot(rate.allotmentmarket.specifyAllot);
				allotmentmarket.setAllocation(rate.allotmentmarket.allocation);
				allotmentmarket.setChoose(rate.allotmentmarket.choose);
				allotmentmarket.setStopAllocation(rate.allotmentmarket.stopAllocation);
				allotmentmarket.setStopChoose(rate.allotmentmarket.stopChoose);
				allotmentmarket.setStopPeriod(rate.allotmentmarket.stopPeriod);
				if(rate.allotmentmarket.fromDate != null ){
				allotmentmarket.setFromDate(format.parse(rate.allotmentmarket.fromDate));
				}
				if(rate.allotmentmarket.fromDate != null){
				allotmentmarket.setToDate(format.parse(rate.allotmentmarket.toDate));
				}
				allotmentmarket.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
				
				allotmentmarket.save();
				}
				
				RateDetails rateDetails = new RateDetails();
				if(rate.isSpecialRate == 1) {
					rateDetails.setIsSpecialRate(rate.isSpecialRate);
					rateDetails.setSpecialDays(rate.special.weekDays.toString());
				} else {
					rateDetails.setIsSpecialRate(rate.isSpecialRate);
				}
				rateDetails.setApplyToMarket(rate.applyToMarket);
				rateDetails.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
				rateDetails.save();
				
				if(rate.isSpecialDaysRate == 2) {
					double value = 2.1;
				for(SpecialDaysRateVM sDaysRateVM:rate.specialDaysRate){
					RateSpecialDays rateSpecialDays = new RateSpecialDays();
					rateSpecialDays.setIsSpecialdaysRate(value);
					rateSpecialDays.setSpecialdaysName(sDaysRateVM.getName());
					rateSpecialDays.setFromspecialDate(sDaysRateVM.getFromspecial());
					rateSpecialDays.setTospecialDate(sDaysRateVM.getTospecial());
					rateSpecialDays.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
					rateSpecialDays.save();
					
					if(sDaysRateVM.non_refund == true){
						CancellationPolicy cancellation = new CancellationPolicy();
						cancellation.setIsNormal(value);	
						cancellation.setNon_refund(sDaysRateVM.non_refund);
						cancellation.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
						cancellation.save();
					}else{
					for(CancellationPolicyVM vm : sDaysRateVM.cancellation) {
						CancellationPolicy cancellation = new CancellationPolicy();
						if(vm.days != null) {
							cancellation.setCancellationDays(vm.days);
								if(vm.penaltyCharge == true) {
									cancellation.setPenalty(vm.penaltyCharge);
									cancellation.setNights(vm.nights);
								} else {
									cancellation.setPenalty(vm.penaltyCharge);
									cancellation.setPercentage(vm.percentage);
								}
								
							cancellation.setIsNormal(value);	
							cancellation.setNon_refund(sDaysRateVM.non_refund);
							cancellation.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
							cancellation.save();
						}
					}
				}
					for(RateDetailsVM rateDetailsVM : sDaysRateVM.rateDetails) {
						PersonRate personRate = new PersonRate();
						personRate.setNumberOfPersons(rateDetailsVM.name);
						personRate.setRateValue(rateDetailsVM.rateValue);
							if(rateDetailsVM.includeMeals == true) {
								personRate.setMeal(rateDetailsVM.includeMeals);
								personRate.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
							} else {
								personRate.setMeal(rateDetailsVM.includeMeals);
							}
							
						personRate.setIsNormal(value);	
						personRate.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
						personRate.save();
						
					}
					value = value + 0.1;
				}
				}else{
					RateSpecialDays rateSpecialDays = new RateSpecialDays();
					rateSpecialDays.setIsSpecialdaysRate(rate.isSpecialDaysRate);
					rateSpecialDays.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
					rateSpecialDays.save();
				}
				
				for(RateDetailsVM rateDetailsVM : rate.normalRate.rateDetails) {
					PersonRate personRate = new PersonRate();
					personRate.setNumberOfPersons(rateDetailsVM.name);
					personRate.setRateValue(rateDetailsVM.rateValue);
					personRate.setOnlineRateValue(rateDetailsVM.onlineRateValue);
					
					if(rateDetailsVM.onlineIncludeMeals == true) {
						personRate.setOnlineIsMeal(rateDetailsVM.onlineIncludeMeals);
						personRate.setOnlineMealType(MealType.getmealTypeByName(rateDetailsVM.onlineMeals));
					} else {
						personRate.setOnlineIsMeal(rateDetailsVM.onlineIncludeMeals);
					}
					
						if(rateDetailsVM.includeMeals == true) {
							personRate.setMeal(rateDetailsVM.includeMeals);
							personRate.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
						} else {
							personRate.setMeal(rateDetailsVM.includeMeals);
						}
						
					personRate.setIsNormal(0);
					personRate.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
					personRate.save();
					
				}
								
				if(rate.isSpecialRate == 1) {
					for(RateDetailsVM rateDetailsVM : rate.special.rateDetails) {
						PersonRate personRate = new PersonRate();
						personRate.setNumberOfPersons(rateDetailsVM.name);
						personRate.setRateValue(rateDetailsVM.rateValue);
							if(rateDetailsVM.includeMeals == true) {
								personRate.setMeal(rateDetailsVM.includeMeals);
								personRate.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
 						} else {
								personRate.setMeal(rateDetailsVM.includeMeals);
							}
							
						personRate.setIsNormal(1);	
						personRate.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
						personRate.save();
						
					}
				}
				
				if(rate.non_refund == true){
					CancellationPolicy cancellation = new CancellationPolicy();
					cancellation.setIsNormal(0);	
					cancellation.setNon_refund(rate.non_refund);
					cancellation.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
					cancellation.save();
				}else{

				for(CancellationPolicyVM vm : rate.cancellation) {
					CancellationPolicy cancellation = new CancellationPolicy();
					if(vm.days != null) {
						cancellation.setCancellationDays(vm.days);
							if(vm.penaltyCharge == true) {
								cancellation.setPenalty(vm.penaltyCharge);
								cancellation.setNights(vm.nights);
							} else {
								cancellation.setPenalty(vm.penaltyCharge);
								cancellation.setPercentage(vm.percentage);
							}
						cancellation.setIsNormal(0);	
						cancellation.setNon_refund(rate.non_refund);
						cancellation.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
						cancellation.save();
					}
				}
		}
				if(rate.isSpecialRate == 1) {
					
					if(rate.special.non_refund == true){
						CancellationPolicy cancellation = new CancellationPolicy();
						cancellation.setIsNormal(1);	
						cancellation.setNon_refund(rate.special.non_refund);
						cancellation.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
						cancellation.save();
					}else{

					for(CancellationPolicyVM vm : rate.special.cancellation) {
						CancellationPolicy cancellation = new CancellationPolicy();
						if(vm.days != null) {
							cancellation.setCancellationDays(vm.days);
								if(vm.penaltyCharge == true) {
									cancellation.setPenalty(vm.penaltyCharge);
									cancellation.setNights(vm.nights);
								} else {
									cancellation.setPenalty(vm.penaltyCharge);
									cancellation.setPercentage(vm.percentage);
								}
							cancellation.setIsNormal(1);	
							cancellation.setNon_refund(rate.special.non_refund);
							cancellation.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
							cancellation.save();
						}
					}
				}
				}
				
		}else{
			return ok("rateNameSame");
		}
			}
			
		return ok();
	}
	
	@Transactional(readOnly=false)
    public static Result deleteRateMeta(long id) {
				
		RateDetails detail = RateDetails.findByRateMetaId(id);
		detail.delete();
		
		ApplicableDateOnRate.deleteByRateDates(id);
		RateSpecialDays.deleteByRateId(id);
		PersonRate.deleteByRateMetaId(id);
		CancellationPolicy.deleteByRateMetaId(id);
		//PersonRate.deleteAllotment(id);
		AllotmentMarket.deleteAllotmentM(id);
		
		RateMeta rateMeta = RateMeta.findById(id);
		rateMeta.getCountry().removeAll(rateMeta.getCountry());
		
		//rateMeta.setCities(null);
		
		rateMeta.delete();
	
		
		return ok();
	}
	
	
	@Transactional(readOnly=false)
    public static Result updateRateMeta() throws ParseException {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Form<RateWrapper> rateWrapperForm = Form.form(RateWrapper.class).bindFromRequest();
		List<RateVM> list = rateWrapperForm.get().rateObject;
		
		for(RateVM rate : list) {

				RateMeta rateMeta = RateMeta.findById(rate.getId());
				rateMeta.setRateName(rate.rateName);
				rateMeta.merge();
				
				
				AllotmentMarket allotmentmarket = AllotmentMarket.findByRateId(rate.getId());
				allotmentmarket.setPeriod(rate.allotmentmarket.period);
				allotmentmarket.setSpecifyAllot(rate.allotmentmarket.specifyAllot);
				allotmentmarket.setAllocation(rate.allotmentmarket.allocation);
				allotmentmarket.setChoose(rate.allotmentmarket.choose);
				allotmentmarket.setStopAllocation(rate.allotmentmarket.stopAllocation);
				allotmentmarket.setStopChoose(rate.allotmentmarket.stopChoose);
				allotmentmarket.setStopPeriod(rate.allotmentmarket.stopPeriod);
				
			   // RoomAllotedRateWise rAllotedRateWise = RoomAllotedRateWise.findByRateId(rate.getId()); 
			    
				/*if(rate.allotmentmarket.fromDate != null ){
				allotmentmarket.setFromDate(format.parse(rate.allotmentmarket.fromDate));
				}else{
					allotmentmarket.setFromDate(null);
				}
				if(rate.allotmentmarket.fromDate != null){
				allotmentmarket.setToDate(format.parse(rate.allotmentmarket.toDate));
				}else{
					allotmentmarket.setToDate(null);
				}*/
				allotmentmarket.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
				
				allotmentmarket.merge();

				RateDetails rateDetails = RateDetails.findByRateMetaId(rate.getId());
				if(rate.getIsSpecialRate() == 1) {   //	if(rate.isSpecialRate() == true) {
					rateDetails.setIsSpecialRate(rate.isSpecialRate);
					rateDetails.setSpecialDays(rate.special.weekDays.toString());
				} else {
					rateDetails.setIsSpecialRate(rate.isSpecialRate);
				}
				rateDetails.setApplyToMarket(rate.applyToMarket);
				rateDetails.merge();
				
			
				
				
				RateMeta rateObject = RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId));
				List<SelectedCountryVM> selectedCountryVM = new ArrayList<>(); 	
				for(AllocatedCitiesVM vm: rate.allocatedCities) {
					if(vm.multiSelectGroup == false && vm.name != null){
						SelectedCountryVM countryVM = new SelectedCountryVM();
						countryVM.name = vm.name;
						countryVM.ticked = vm.ticked;
						selectedCountryVM.add(countryVM);
					}
					
				}
				List<Country> listCity = new ArrayList<>();
				for(SelectedCountryVM countryvm : selectedCountryVM){
					Country _city = Country.getCountryByName(countryvm.name);
					
					if(countryvm.ticked){
						listCity.add(_city);
					}
				}
				rateObject.setCountry(listCity);
				
				for(RateDetailsVM rateDetailsVM : rate.normalRate.rateDetails) {
					double v = 0;
					PersonRate personRate = PersonRate.findByRateMetaIdAndNormal(rate.getId(),v,rateDetailsVM.name);
					personRate.setNumberOfPersons(rateDetailsVM.name);
					personRate.setRateValue(rateDetailsVM.rateValue);
					personRate.setOnlineRateValue(rateDetailsVM.onlineRateValue);
							if(rateDetailsVM.includeMeals == true) {
								personRate.setMeal(rateDetailsVM.includeMeals);
								personRate.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
							} else {
								personRate.setMeal(rateDetailsVM.includeMeals);
							}
							

							if(rateDetailsVM.onlineIncludeMeals == true) {
								personRate.setOnlineIsMeal(rateDetailsVM.onlineIncludeMeals);
								personRate.setOnlineMealType(MealType.getmealTypeByName(rateDetailsVM.onlineMeals));
							} else {
								personRate.setOnlineIsMeal(rateDetailsVM.onlineIncludeMeals);
							}
							
							personRate.setIsNormal(0);
							personRate.merge();
					
				}
				
				
					if(rate.isSpecialDaysRate == 2) {
						double value = 2.1;
					for(SpecialDaysRateVM sDaysRateVM:rate.specialDaysRate){
						RateSpecialDays rateSpecialDays = RateSpecialDays.findByRateMetaIdandIsSpecial(rate.getId(),sDaysRateVM.isspecialdaysrate,sDaysRateVM.name);
						
						if(rateSpecialDays != null){
						
						rateSpecialDays.setFromspecialDate(sDaysRateVM.getFromspecial());
						rateSpecialDays.setTospecialDate(sDaysRateVM.getTospecial());
						rateSpecialDays.merge();
						
						if(sDaysRateVM.non_refund == true){
							
							for(CancellationPolicyVM vm : sDaysRateVM.cancellation) {
								CancellationPolicy cancellation = CancellationPolicy.findByRateMetaIdAndNormalrate(rate.getId(), sDaysRateVM.isspecialdaysrate);
								cancellation.delete();
							}
							CancellationPolicy cancellation = new CancellationPolicy();
							cancellation.setIsNormal(value);	
							cancellation.setNon_refund(sDaysRateVM.non_refund);
							cancellation.setRate(RateMeta.findById(rate.id));
							cancellation.save();
						}else{
						for(CancellationPolicyVM vm : sDaysRateVM.cancellation) {
							CancellationPolicy cancellation = CancellationPolicy.findByRateMetaIdAndNormalrate(rate.getId(), sDaysRateVM.isspecialdaysrate);
							if(vm.days != null) {
								cancellation.setCancellationDays(vm.days);
									if(vm.penaltyCharge == true) {
										cancellation.setPenalty(vm.penaltyCharge);
										cancellation.setNights(vm.nights);
									} else {
										cancellation.setPenalty(vm.penaltyCharge);
										cancellation.setPercentage(vm.percentage);
									}
									cancellation.setNon_refund(sDaysRateVM.non_refund);
									
								cancellation.merge();
							}
						}
						}
						for(RateDetailsVM rateDetailsVM : sDaysRateVM.rateDetails) {
							PersonRate personRate = PersonRate.findByRateMetaIdAndNormal(rate.getId(),sDaysRateVM.isspecialdaysrate, rateDetailsVM.name);
							personRate.setNumberOfPersons(rateDetailsVM.name);
							personRate.setRateValue(rateDetailsVM.rateValue);
								if(rateDetailsVM.includeMeals == true) {
									personRate.setMeal(rateDetailsVM.includeMeals);
									personRate.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
								} else {
									personRate.setMeal(rateDetailsVM.includeMeals);
								}
								
							personRate.merge();
							
						}
					 }else{
						 
						 RateSpecialDays.deleteByRateMetaId(rate.getId(),0.0);
						 RateSpecialDays rateSpecialDays1 = new RateSpecialDays();
						
						rateSpecialDays1.setIsSpecialdaysRate(value);
						rateSpecialDays1.setSpecialdaysName(sDaysRateVM.getName());
						rateSpecialDays1.setFromspecialDate(sDaysRateVM.getFromspecial());
						rateSpecialDays1.setTospecialDate(sDaysRateVM.getTospecial());
						rateSpecialDays1.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
						rateSpecialDays1.save();
						
						
						if(sDaysRateVM.non_refund == true){
							
							CancellationPolicy cancellation = new CancellationPolicy();
							cancellation.setIsNormal(value);	
							cancellation.setNon_refund(sDaysRateVM.non_refund);
							cancellation.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
							cancellation.save();
						}else{
						for(CancellationPolicyVM vm : sDaysRateVM.cancellation) {
							CancellationPolicy cancellation1 = new CancellationPolicy();
							if(vm.days != null) {
								cancellation1.setCancellationDays(vm.days);
									if(vm.penaltyCharge == true) {
										cancellation1.setPenalty(vm.penaltyCharge);
										cancellation1.setNights(vm.nights);
									} else {
										cancellation1.setPenalty(vm.penaltyCharge);
										cancellation1.setPercentage(vm.percentage);
									}
								cancellation1.setNon_refund(sDaysRateVM.non_refund);
								cancellation1.setIsNormal(value);	
								cancellation1.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
								cancellation1.save();
							}
						}
					 }
						for(RateDetailsVM rateDetailsVM : sDaysRateVM.rateDetails) {
							PersonRate personRate1 = new PersonRate();
							personRate1.setNumberOfPersons(rateDetailsVM.name);
							personRate1.setRateValue(rateDetailsVM.rateValue);
								if(rateDetailsVM.includeMeals == true) {
									personRate1.setMeal(rateDetailsVM.includeMeals);
									personRate1.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
								} else {
									personRate1.setMeal(rateDetailsVM.includeMeals);
								}
								
							personRate1.setIsNormal(value);	
							personRate1.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
							personRate1.save();
							
						}
						
					}
						value = value + 0.1;
					}
					}
					
				
				if(rate.isSpecialRate == 1) {
					for(RateDetailsVM rateDetailsVM : rate.special.rateDetails) {
						PersonRate personRate = PersonRate.findByRateMetaIdAndNormal(rate.getId(),rate.isSpecialRate,rateDetailsVM.name);
						if(personRate != null){
							personRate.setNumberOfPersons(rateDetailsVM.name);
							personRate.setRateValue(rateDetailsVM.rateValue);
									if(rateDetailsVM.includeMeals == true) {
										personRate.setMeal(rateDetailsVM.includeMeals);
										personRate.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
									} else {
										personRate.setMeal(rateDetailsVM.includeMeals);
									}
									
									personRate.setIsNormal(1);	
									personRate.merge();
						}else{
							PersonRate personRate1 = new PersonRate();
							personRate1.setRate(rateMeta.findById(rate.getId()));
							personRate1.setNumberOfPersons(rateDetailsVM.name);
							personRate1.setRateValue(rateDetailsVM.rateValue);
									if(rateDetailsVM.includeMeals == true) {
										personRate1.setMeal(rateDetailsVM.includeMeals);
										personRate1.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
									} else {
										personRate1.setMeal(rateDetailsVM.includeMeals);
									}
									
									personRate1.setIsNormal(1);	
									personRate1.save();
						}
						
						
					}
				}
				
				if(rate.non_refund == true){
					
					for(CancellationPolicyVM vm : rate.cancellation) {
						CancellationPolicy cancellation = CancellationPolicy.findById(vm.id);
						cancellation.delete();
					}
					CancellationPolicy cancellation = new CancellationPolicy();
					cancellation.setIsNormal(0);	
					cancellation.setNon_refund(rate.non_refund);
					cancellation.setRate(RateMeta.findById(rate.id));
					cancellation.save();
				}else{
				for(CancellationPolicyVM vm : rate.cancellation) {
						if(vm.id == 0) {
							CancellationPolicy cancellation = new CancellationPolicy();
							if(vm.days != null) {
								cancellation.setCancellationDays(vm.days);
									if(vm.penaltyCharge == true) {
										cancellation.setPenalty(vm.penaltyCharge);
										cancellation.setNights(vm.nights);
									} else {
										cancellation.setPenalty(vm.penaltyCharge);
										cancellation.setPercentage(vm.percentage);
									}
									cancellation.setNon_refund(rate.non_refund);
								cancellation.setIsNormal(0);	
								cancellation.setRate(RateMeta.findById(rate.id));
								cancellation.save();
							}
						} else {
						
							CancellationPolicy cancellation = CancellationPolicy.findById(vm.id);
							if(vm.days != null) {
								cancellation.setCancellationDays(vm.days);
									if(vm.penaltyCharge == true) {
										cancellation.setPenalty(vm.penaltyCharge);
										cancellation.setNights(vm.nights);
									} else {
										cancellation.setPenalty(vm.penaltyCharge);
										cancellation.setPercentage(vm.percentage);
									}
									cancellation.setNon_refund(rate.non_refund);
								cancellation.setIsNormal(0);	
								cancellation.merge();
							}
						}	
				}
		}
				if(rate.isSpecialRate == 1) {
					if(rate.special.non_refund == true){
						
						for(CancellationPolicyVM vm : rate.special.cancellation) {
							CancellationPolicy cancellation = CancellationPolicy.findById(vm.id);
							cancellation.delete();
						}
						CancellationPolicy cancellation = new CancellationPolicy();
						cancellation.setIsNormal(1);	
						cancellation.setNon_refund(rate.special.non_refund);
						cancellation.setRate(RateMeta.findById(rate.id));
						cancellation.save();
					}else{
					for(CancellationPolicyVM vm : rate.special.cancellation) {
						if(vm.id == 0) {
							CancellationPolicy cancellation = new CancellationPolicy();
							if(vm.days != null) {
								cancellation.setCancellationDays(vm.days);
									if(vm.penaltyCharge == true) {
										cancellation.setPenalty(vm.penaltyCharge);
										cancellation.setNights(vm.nights);
									} else {
										cancellation.setPenalty(vm.penaltyCharge);
										cancellation.setPercentage(vm.percentage);
									}
								cancellation.setIsNormal(1);	
								cancellation.setRate(RateMeta.findById(rate.id));
								cancellation.save();
							}
						} else {
							CancellationPolicy cancellation = CancellationPolicy.findById(vm.id);
							if(vm.days != null) {
								cancellation.setCancellationDays(vm.days);
									if(vm.penaltyCharge == true) {
										cancellation.setPenalty(vm.penaltyCharge);
										cancellation.setNights(vm.nights);
									} else {
										cancellation.setPenalty(vm.penaltyCharge);
										cancellation.setPercentage(vm.percentage);
									}
								cancellation.setIsNormal(1);	
								cancellation.merge();
							}
						}
					}
				}
				}
				
		}
		
		return ok();
	}
	
	@Transactional(readOnly=true)
    public static Result getRateRang(String fromDate,String toDate,Long room,Long supplierCode){
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<RateVM> list = new ArrayList<>();
		Map<Long, Long> map = new HashMap<Long, Long>();
		
		Date formDate = null;
		Date toDates = null;
		try {
			formDate = format.parse(fromDate);
			toDates = format.parse(toDate);
		} catch (ParseException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar c = Calendar.getInstance();
		c.setTime(formDate);
		c.set(Calendar.MILLISECOND, 0);

		
		long dayDiff;
		if(toDates.getTime() == formDate.getTime()){
			dayDiff = 1;
		}else{
			long diff = toDates.getTime() - formDate.getTime();

			dayDiff = diff / (1000 * 60 * 60 * 24);
		}
		
		for (int i = 0; i <= dayDiff; i++) {
			
			List<RateMeta> rateMeta = RateMeta.getRatecheckdateWiseSupplier(supplierCode,room,c.getTime());
			for(RateMeta rate:rateMeta) {
				Long object = map.get(rate.getId());
				if (object == null) {
					List<SpecialDaysRateVM>  spDaysRateVMs = new ArrayList<>();
					RateDetails rateDetails = RateDetails.findByRateMetaId(rate.getId());
					//List<RateSpecialDays> rateSpecialDays = RateSpecialDays.findByRateMetaId(rate.getId());
					//List<PersonRate> personRate = PersonRate.findByRateMetaId(rate.getId());
					List<CancellationPolicy> cancellation = CancellationPolicy.findByRateMetaId(rate.getId());
					AllotmentMarket aMarket = AllotmentMarket.findByRateId(rate.getId());
					
					RateVM rateVM = new RateVM();
					rateVM.setCurrency(rate.getCurrency());
					List<String> aMinDateOnRate = ApplicableDateOnRate.getMinDate(rate.getId());
					rateVM.setFromDate(format.format(aMinDateOnRate.get(0)));
					List<String> aMaxDateOnRate = ApplicableDateOnRate.getMaxDate(rate.getId());
					rateVM.setToDate(format.format(aMaxDateOnRate.get(0)));
					rateVM.setRoomName(rate.getRoomType().getRoomType());
					rateVM.setRoomId(rate.getRoomType().getRoomId());
					rateVM.setRateName(rate.getRateName());
					rateVM.setIsSpecialRate(rateDetails.getIsSpecialRate());
					
					rateVM.setId(rate.getId());
					rateVM.applyToMarket = rateDetails.isApplyToMarket();
					List<String> clist = new ArrayList<>();
					List<RateMeta> rList = RateMeta.getcountryByRate(rate.getId());
					for(RateMeta rMeta:rList){				
						
						for(Country country:rMeta.getCountry()){
						
							clist.add(country.getCountryName());
						}
						
					}
					
					rateVM.setAllocatedCountry(clist);
					
					
					NormalRateVM normalRateVM = new NormalRateVM();
					SpecialRateVM specialRateVM = new SpecialRateVM();
					AllotmentMarketVM aMarketVM = new AllotmentMarketVM();
					SpecialDaysRateVM sDaysRateVM = new SpecialDaysRateVM();
					List<SpecialDaysRateVM> spList = new ArrayList<>(); 
					
					if(aMarket != null){
					aMarketVM.setAllocation(aMarket.getAllocation());
					aMarketVM.setChoose(aMarket.getChoose());
					aMarketVM.setPeriod(aMarket.getPeriod());
					aMarketVM.setSpecifyAllot(aMarket.getSpecifyAllot());
					aMarketVM.setStopAllocation(aMarket.getStopAllocation());
					aMarketVM.setStopChoose(aMarket.getStopChoose());
					aMarketVM.setStopPeriod(aMarket.getStopPeriod());
					if(aMarket.getFromDate() != null){
					aMarketVM.setFromDate(format.format(aMarket.getFromDate()));
					}
					if(aMarket.getToDate() != null){
					aMarketVM.setToDate(format.format(aMarket.getToDate()));
					}
					
					rateVM.setAllotmentmarket(aMarketVM);
					}
												
							for(CancellationPolicy cancel:cancellation) {
								if(cancel.getIsNormal() == 0){
									CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
									rateVM.cancellation.add(vm);
								}
								if(cancel.getIsNormal() == 1) {
									CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
									specialRateVM.cancellation.add(vm);
								}
							}
							
						rateVM.setNormalRate(normalRateVM);
						rateVM.setSpecial(specialRateVM);
						rateVM.setSpecialDaysRate(spDaysRateVMs);
					
						list.add(rateVM);
					
				map.put(rate.getId(), Long.parseLong("1"));	
			}
				
			}
			
		c.add(Calendar.DATE, 1);
			
		}
		
			
		return ok(Json.toJson(list));
		
	}
	
	@Transactional(readOnly=true)
    public static Result getRateData(Long room,String fromDate,String toDate,String currencyType) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<RateVM> list = new ArrayList<>();
		Map<Long, Long> map = new HashMap<Long, Long>();
		
		Date formDate = null;
		Date toDates = null;
		try {
			formDate = format.parse(fromDate);
			toDates = format.parse(toDate);
		} catch (ParseException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar c = Calendar.getInstance();
		c.setTime(formDate);
		c.set(Calendar.MILLISECOND, 0);

		
		long dayDiff;
		if(toDates.getTime() == formDate.getTime()){
			dayDiff = 1;
		}else{
			long diff = toDates.getTime() - formDate.getTime();

			dayDiff = diff / (1000 * 60 * 60 * 24);
		}
		
		for (int i = 0; i <= dayDiff; i++) {
			
			List<RateMeta> rateMeta = RateMeta.getRatecheckdateWise(currencyType,room,c.getTime());
			for(RateMeta rate:rateMeta) {
				Long object = map.get(rate.getId());
				if (object == null) {
					List<SpecialDaysRateVM>  spDaysRateVMs = new ArrayList<>();
					RateDetails rateDetails = RateDetails.findByRateMetaId(rate.getId());
					List<RateSpecialDays> rateSpecialDays = RateSpecialDays.findByRateMetaId(rate.getId());
					List<PersonRate> personRate = PersonRate.findByRateMetaId(rate.getId());
					List<CancellationPolicy> cancellation = CancellationPolicy.findByRateMetaId(rate.getId());
					AllotmentMarket aMarket = AllotmentMarket.findByRateId(rate.getId());
					
					RateVM rateVM = new RateVM();
					rateVM.setCurrency(rate.getCurrency());
					List<String> aMinDateOnRate = ApplicableDateOnRate.getMinDate(rate.getId());
					rateVM.setFromDate(format.format(aMinDateOnRate.get(0)));
					List<String> aMaxDateOnRate = ApplicableDateOnRate.getMaxDate(rate.getId());
					rateVM.setToDate(format.format(aMaxDateOnRate.get(0)));
					rateVM.setRoomName(rate.getRoomType().getRoomType());
					rateVM.setRoomId(rate.getRoomType().getRoomId());
					rateVM.setRateName(rate.getRateName());
					rateVM.setIsSpecialRate(rateDetails.getIsSpecialRate());
					
					rateVM.setId(rate.getId());
					rateVM.applyToMarket = rateDetails.isApplyToMarket();
					List<String> clist = new ArrayList<>();
					List<RateMeta> rList = RateMeta.getcountryByRate(rate.getId());
					for(RateMeta rMeta:rList){				
						
						for(Country country:rMeta.getCountry()){
						
							clist.add(country.getCountryName());
						}
						
					}
					
					rateVM.setAllocatedCountry(clist);
					
					
					NormalRateVM normalRateVM = new NormalRateVM();
					SpecialRateVM specialRateVM = new SpecialRateVM();
					AllotmentMarketVM aMarketVM = new AllotmentMarketVM();
					SpecialDaysRateVM sDaysRateVM = new SpecialDaysRateVM();
					List<SpecialDaysRateVM> spList = new ArrayList<>(); 
					
					if(aMarket != null){
					aMarketVM.setAllocation(aMarket.getAllocation());
					aMarketVM.setChoose(aMarket.getChoose());
					aMarketVM.setPeriod(aMarket.getPeriod());
					aMarketVM.setSpecifyAllot(aMarket.getSpecifyAllot());
					aMarketVM.setStopAllocation(aMarket.getStopAllocation());
					aMarketVM.setStopChoose(aMarket.getStopChoose());
					aMarketVM.setStopPeriod(aMarket.getStopPeriod());
					if(aMarket.getFromDate() != null){
					aMarketVM.setFromDate(format.format(aMarket.getFromDate()));
					}
					if(aMarket.getToDate() != null){
					aMarketVM.setToDate(format.format(aMarket.getToDate()));
					}
					
					rateVM.setAllotmentmarket(aMarketVM);
					}
			
					for(PersonRate person:personRate) {
						
						if(person.getIsNormal() == 0){
							RateDetailsVM vm = new RateDetailsVM(person);
							normalRateVM.rateDetails.add(vm);
						}else if(person.getIsNormal() == 1) {
							RateDetailsVM vm = new RateDetailsVM(person);
							specialRateVM.rateDetails.add(vm);
						}						
											
						if(person.getIsNormal() > 2) {
							rateVM.setIsSpecialDaysRate(2);
							for(RateSpecialDays rDays:rateSpecialDays){
								if(rDays.getIsSpecialdaysRate() ==  person.getIsNormal()){
								SpecialDaysRateVM sRateVM= new SpecialDaysRateVM();
								sRateVM.name = rDays.getSpecialdaysName();
								sRateVM.fromspecial = rDays.getFromspecialDate();
								sRateVM.tospecial = rDays.getTospecialDate();
								sRateVM.isspecialdaysrate = rDays.getIsSpecialdaysRate();
							    
								RateDetailsVM vm = new RateDetailsVM(person);
								sRateVM.rateDetails.add(vm);
								for(CancellationPolicy cancel:cancellation) {
									if(cancel.getIsNormal() > 2){
											if(sRateVM.isspecialdaysrate == cancel.getIsNormal()){
										CancellationPolicyVM vm1 = new CancellationPolicyVM(cancel);
										sRateVM.cancellation.add(vm1);
										sRateVM.non_refund = cancel.isNon_refund();
										}
												
									}
								}	
								spDaysRateVMs.add(sRateVM);
								}
							}
							
							}
					}
						if(rateDetails.getSpecialDays() != null) {
							String week[] = rateDetails.getSpecialDays().split(",");
								for(String day:week) {
									StringBuilder sb = new StringBuilder(day);
									if(day.contains("[")) {
										sb.deleteCharAt(sb.indexOf("["));
									}
									if(day.contains("]")) {
										sb.deleteCharAt(sb.indexOf("]"));
									}
									if(day.contains(" ")) {
										sb.deleteCharAt(sb.indexOf(" "));
									}
									specialRateVM.weekDays.add(sb.toString());
									if(sb.toString().equals("Sun")) {
										specialRateVM.rateDay0 = true;
									}
									if(sb.toString().equals("Mon")) {
										specialRateVM.rateDay1 = true;
									}
									if(sb.toString().equals("Tue")) {
										specialRateVM.rateDay2 = true;
									}
									if(sb.toString().equals("Wed")) {
										specialRateVM.rateDay3 = true;
									}
									if(sb.toString().equals("Thu")) {
										specialRateVM.rateDay4 = true;
									}
									if(sb.toString().equals("Fri")) {
										specialRateVM.rateDay5 = true;
									}
									if(sb.toString().equals("Sat")) {
										specialRateVM.rateDay6 = true;
									}
								}
						}
						
							for(CancellationPolicy cancel:cancellation) {
								if(cancel.getIsNormal() == 0){
									CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
									rateVM.cancellation.add(vm);
									rateVM.non_refund = cancel.isNon_refund();
								}
								if(cancel.getIsNormal() == 1) {
									CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
									specialRateVM.cancellation.add(vm);
									specialRateVM.non_refund = cancel.isNon_refund();
								}
							}
							
						rateVM.setNormalRate(normalRateVM);
						rateVM.setSpecial(specialRateVM);
						rateVM.setSpecialDaysRate(spDaysRateVMs);
					
						list.add(rateVM);
					
				map.put(rate.getId(), Long.parseLong("1"));	
			}
				
			}
			
		c.add(Calendar.DATE, 1);
			
		}
		
			
		return ok(Json.toJson(list));
	}
	
	@Transactional(readOnly=true)
    public static Result fetchToEditHotelDetails(long supplierCode) {
		//this method is used to fetch the room details for edit. this method will return json data..
		//HotelRoomTypes roomType = null;
		/*if (roomId != -1) {
			roomType = new HotelRoomTypes();
			return ok(Json.toJson(roomType));
		} else {*/
			List<HotelRoomTypes> roomTypeInfo = HotelRoomTypes.getHotelRoomDetails(supplierCode);
		//}
		return ok(Json.toJson(roomTypeInfo));
	}
	
	
	@Transactional(readOnly=true)
    public static Result getroomtypesInfo(long RoomId) {
		
			HotelRoomTypes roomType = HotelRoomTypes.getHotelRoomDetailsInfo(RoomId);
		
		return ok(Json.toJson(roomType));
	}
	
	
	@Transactional(readOnly=false)
    public static Result saveOrUpdateHotelRoom() {
			
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, RoomtypeVM.class);
		RoomtypeVM roomtypeVM = Json.fromJson(json, RoomtypeVM.class);
		
		//HotelRoomTypes hotelroomTypes =HotelRoomTypes.findByIdAndName(roomtypeVM.getRoomname(),roomtypeVM.getSupplierCode());
		
		if(roomtypeVM.getRoomId()== null || roomtypeVM.getRoomId()=="")
		{
			HotelRoomTypes hotelroomTypes =HotelRoomTypes.findByIdAndName(roomtypeVM.getRoomname(),roomtypeVM.getSupplierCode());
			
			if(hotelroomTypes == null)
			{
			
			hotelroomTypes = new HotelRoomTypes();
			hotelroomTypes.setRoomType(roomtypeVM.getRoomname());
			hotelroomTypes.setExtraBedAllowed(roomtypeVM.getExtraBedAllowed());
			hotelroomTypes.setChargesForChildren(roomtypeVM.getChargesForChildren());
			hotelroomTypes.setRoomSuiteType(roomtypeVM.getRoomSuiteType());
			hotelroomTypes.setChildAllowedFreeWithAdults(roomtypeVM.getChildAllowedFreeWithAdults());
			hotelroomTypes.setMaxAdultOccupancy(roomtypeVM.getMaxAdultOccupancy());
			hotelroomTypes.setMaxOccupancy(roomtypeVM.getMaxOccupancy());
			hotelroomTypes.setDescription(roomtypeVM.getDescription());
			hotelroomTypes.setMaxAdultOccSharingWithChildren(roomtypeVM.getMaxAdultOccSharingWithChildren());
			hotelroomTypes.setSupplierCode(roomtypeVM.getSupplierCode());
			//String roomSize = roomtypeVM.getRoomSize() + roomtypeVM.getRoomSizeType();
			hotelroomTypes.setRoomSize(roomtypeVM.getRoomSize() + roomtypeVM.getRoomSizeType());
			hotelroomTypes.setBreakfastInclude(roomtypeVM.getBreakfastInclude());
			hotelroomTypes.setBreakfastRate(roomtypeVM.getBreakfastRate());
			hotelroomTypes.setChildAge(roomtypeVM.getChildAge());
			/*if(roomtypeVM.getExtraBedRate() != null){
			hotelroomTypes.setExtraBedRate(Double.parseDouble(roomtypeVM.getExtraBedRate()));
			}*/
			hotelroomTypes.setAmenities(RoomAmenities.getroomamenities(roomtypeVM.getRoomamenities()));
					
			for(RoomChildpoliciVM childVM : roomtypeVM.getRoomchildPolicies())
			{
				RoomChildPolicies roomchildPolicies = new RoomChildPolicies();
				roomchildPolicies.setAllowedChildAgeFrom(childVM.getAllowedChildAgeFrom());
				roomchildPolicies.setAllowedChildAgeTo(childVM.getAllowedChildAgeTo());
				roomchildPolicies.setExtraChildRate(Double.parseDouble(childVM.getExtraChildRate()));
				//roomchildPolicies.setYears(childVM.getYears());
				//roomchildPolicies.setNetRate(childVM.getNetRate());
				roomchildPolicies.save();
				hotelroomTypes.addchildPolicies(roomchildPolicies);
				
			}
			hotelroomTypes.save();
			}
		}
		else
		{
			HotelRoomTypes hotelroomTypes =HotelRoomTypes.findById(Long.parseLong(roomtypeVM.getRoomId()));
			hotelroomTypes.setRoomType(roomtypeVM.getRoomname());
			hotelroomTypes.setExtraBedAllowed(roomtypeVM.getExtraBedAllowed());
			hotelroomTypes.setChargesForChildren(roomtypeVM.getChargesForChildren());
			hotelroomTypes.setRoomSuiteType(roomtypeVM.getRoomSuiteType());
			hotelroomTypes.setChildAllowedFreeWithAdults(roomtypeVM.getChildAllowedFreeWithAdults());
			hotelroomTypes.setMaxAdultOccupancy(roomtypeVM.getMaxAdultOccupancy());
			hotelroomTypes.setMaxOccupancy(roomtypeVM.getMaxOccupancy());
			hotelroomTypes.setDescription(roomtypeVM.getDescription());
			hotelroomTypes.setMaxAdultOccSharingWithChildren(roomtypeVM.getMaxAdultOccSharingWithChildren());
			//hotelroomTypes.setSupplierCode(roomtypeVM.getSupplierCode());
			hotelroomTypes.setAmenities(RoomAmenities.getroomamenities(roomtypeVM.getRoomamenities()));
			hotelroomTypes.setBreakfastInclude(roomtypeVM.getBreakfastInclude());
			hotelroomTypes.setBreakfastRate(roomtypeVM.getBreakfastRate());
			hotelroomTypes.setChildAge(roomtypeVM.getChildAge());
			
			/*if(roomtypeVM.getExtraBedRate() != null){
			hotelroomTypes.setExtraBedRate(Double.parseDouble(roomtypeVM.getExtraBedRate()));
			}*/
			for(RoomChildpoliciVM childVM : roomtypeVM.getRoomchildPolicies())
			{
							
				RoomChildPolicies roomchildPolicies = RoomChildPolicies.findById(childVM.getRoomchildPolicyId());
				roomchildPolicies.setAllowedChildAgeFrom(childVM.getAllowedChildAgeFrom());
				roomchildPolicies.setAllowedChildAgeTo(childVM.getAllowedChildAgeTo());
				roomchildPolicies.setExtraChildRate(Double.parseDouble(childVM.getExtraChildRate()));
				//roomchildPolicies.setYears(childVM.getYears());
				//roomchildPolicies.setNetRate(childVM.getNetRate());
				roomchildPolicies.merge();
				hotelroomTypes.addchildPolicies(roomchildPolicies);
				
			}
			hotelroomTypes.merge();
		}
				
		
		return ok();

	}
	
	
	@Transactional(readOnly=false)
	public static Result saveRoomImgs() throws IOException {
		
		DynamicForm form = DynamicForm.form().bindFromRequest();
		
		FilePart picture = request().body().asMultipartFormData().getFile("roomPic");
			
		createDir(rootDir,Long.parseLong(form.get("roomId")),Long.parseLong(form.get("supplierCode")));
		 String fileName = picture.getFilename();
		
		 String ThumbnailImage = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "RoomType"+ File.separator+Long.parseLong(form.get("roomId"))+ File.separator+"Logo_thumbnail."+FilenameUtils.getExtension(fileName);
         String originalFileName = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "RoomType"+ File.separator +Long.parseLong(form.get("roomId"))+File.separator+"Original_image."+FilenameUtils.getExtension(fileName);
		 
		 
         File src = picture.getFile();
         OutputStream out = null;
         BufferedImage image = null;
         File f = new File(ThumbnailImage);
         try {
        	   
                  BufferedImage originalImage = ImageIO.read(src);
                        Thumbnails.of(originalImage)
                           .size(780, 780)
                            .toFile(f);
                            File _f = new File(originalFileName);
                            Thumbnails.of(originalImage).scale(1.0).
                            toFile(_f);
           
        	 
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
           
 		HotelRoomTypes hotelroomTypes =HotelRoomTypes.findById(Long.parseLong(form.get("roomId")));
 		 if(hotelroomTypes != null)
 		 {
 			hotelroomTypes.setRoomPic(ThumbnailImage); 
 			hotelroomTypes.merge();
 		 }
 		 
 		return ok(Json.toJson(hotelroomTypes));
		
	}
	
	public static void createDir(String rootDir, long roomId,long supplierCode) {
        File file3 = new File(rootDir + File.separator+supplierCode +File.separator+ "RoomType"+File.separator+roomId);
        if (!file3.exists()) {
                file3.mkdirs();
        }
	}
	
	@Transactional(readOnly=false)
	public static Result getRoomImagePathInroom(long roomId) {
		
		HotelRoomTypes hRoomTypes = HotelRoomTypes.findById(roomId);
		File f = new File(hRoomTypes.getRoomPic());
	    return ok(f);	
		
	}
	
	
	
	@Transactional(readOnly=true)
	public static Result getAvailableRoomAmenities() {
		return ok(Json.toJson(RoomAmenities.getRoomAmenities()));
	}
	
	@Transactional(readOnly=false)
	public static Result deleteRoomchild(int id) {

		RoomChildPolicies.deletechildRel(id);
		RoomChildPolicies.deletefindById(id);
		
		/*for(RoomChildPolicies childPolicies: hotelroomtypes.getRoomchildPolicies()){
			childPolicies.delete();
		}*/
		/*hotelroomtypes.setRoomchildPolicies(null);
		hotelroomtypes.merge();*/

		return ok();
	}
	
	
	
	@Transactional(readOnly=false)
    public static Result deletecansell(long id,boolean value){
		CancellationPolicy.deletecansellpolicy(id,value);
		return ok();
	}
	
	
	@Transactional(readOnly=false)
    public static Result deleteSpecialcansells(long id,boolean value){
		CancellationPolicy.deleteSpecicansellpolicy(id,value);
		return ok();
	}
	
	
	@Transactional(readOnly = true)
	public static Result getMarketGroup(long id) {
		List<Markets> arMarkets = Markets.getMarkets();
		List<MarketVM> group = new ArrayList<MarketVM>();
		for (Markets c : arMarkets) {
			MarketVM marketvm = new MarketVM();
			martektsVM marketsvm = new martektsVM();
			marketsvm.marketCode = c.getMarketCode();
			marketsvm.marketName = c.getMarketName();

			List<CountryVM> conutryvm = new ArrayList<CountryVM>();
			List<Country> conutry = Country.getCountry(c.getMarketCode());
			for (Country _conutry : conutry) {
				
				CountryVM _conutryvm = new CountryVM();
				_conutryvm.id = _conutry.getCountryCode();
				_conutryvm.countryMarketCode = _conutry.getMarket().getMarketCode();
				_conutryvm.countryName = _conutry.getCountryName();
				if(id != 0) {
					RateMeta rates = RateMeta.getRatesById(id);
					for(Country cty : rates.getCountry()){
						if(cty.getCountryCode() == _conutry.getCountryCode()){
							_conutryvm.tick = true;
							break;
						}else{
							_conutryvm.tick = false;
						}
							
					}
				} else {
					_conutryvm.tick = false;
				}
				
				conutryvm.add(_conutryvm);
			}
			marketsvm.conutryvm = conutryvm;
			marketvm.country = marketsvm;
			group.add(marketvm);
		}
		return ok(Json.toJson(group));
	}
	
	public static class MarketVM {
		public martektsVM country;

	}

	public static class martektsVM {
		public int marketCode;
		public String marketName;
		public List<CountryVM> conutryvm;
	}

	public static class CountryVM {
		public int id;
		public int countryMarketCode;
		public String countryName;
		public boolean tick;

	}
	
	public static class SelectedCountryVM {

		public String name;
		public int marketCode;
		public boolean ticked;

	}
	public static class VM {
		public int id;
		public List<SelectedCountryVM> city = new ArrayList<SelectedCountryVM>();
	}
	

	@Transactional(readOnly = false)
	public static Result setCitySelection() {
		//JsonNode json = request().body().asJson().get("id");
		JsonNode jn = request().body().asJson();
		//jn.as
		
		VM c = Json.fromJson(jn, VM.class);
		int ratesId  = c.id;
		List<SelectedCountryVM> city = c.city;
		RateMeta rates = RateMeta.getRatesById(ratesId);
		
		if(rates != null && rates.getCountry() != null && !rates.getCountry().isEmpty())
		{
			rates.getCountry().removeAll(rates.getCountry());
			//JPA.em().merge(rates);
		}
		
		List<Country> listCity = new ArrayList<>();
		for(SelectedCountryVM cityvm : city){
			Country _city = Country.getCountryByName(cityvm.name);
			
			if(cityvm.ticked){
				listCity.add(_city);
			}
		}
		rates.setCountry(listCity);
		return ok();

	}

	@Transactional(readOnly = false)
	public static Result setCountrySelection(long id) {
		//JsonNode json = request().body().asJson().get("id");
		JsonNode jn = request().body().asJson();
		//jn.as
		
		VM c = Json.fromJson(jn, VM.class);
		int ratesId  = c.id;
		List<SelectedCountryVM> city = c.city;
		RateMeta rates = RateMeta.getRatesById(ratesId);
		
		if(rates != null && rates.getCountry() != null && !rates.getCountry().isEmpty())
		{
			rates.getCountry().removeAll(rates.getCountry());
			//JPA.em().merge(rates);
		}
		
		List<Country> listCity = new ArrayList<>();
		for(SelectedCountryVM cityvm : city){
			Country _city = Country.getCountryByName(cityvm.name);
			
			if(cityvm.ticked){
				listCity.add(_city);
			}
		}
		rates.setCountry(listCity);
		return ok();

	}

	
	
}


