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
import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.InfoWiseImagesPath;
import com.travelportal.domain.Markets;
import com.travelportal.domain.MealType;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.ApplicableDateOnRate;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RateWrapper;
import com.travelportal.domain.rooms.RoomAllotedRateWise;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.domain.rooms.RoomChildPolicies;
import com.travelportal.domain.rooms.SpecialsMarket;
import com.travelportal.vm.AllocatedCitiesVM;
import com.travelportal.vm.AllotmentMarketVM;
import com.travelportal.vm.AllotmentVM;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.CountrysVM;
import com.travelportal.vm.HotelHealthAndSafetyVM;
import com.travelportal.vm.NormalRateVM;
import com.travelportal.vm.RateDetailsVM;
import com.travelportal.vm.RateVM;
import com.travelportal.vm.RoomChildpoliciVM;
import com.travelportal.vm.RoomType;
import com.travelportal.vm.RoomtypeVM;
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
    public static Result getRateObject(Long roomType) {
		
		int maxAdults = HotelRoomTypes.getHotelRoomMaxAdultOccupancy(roomType);
		
		NormalRateVM normal = new NormalRateVM();
		SpecialRateVM special = new SpecialRateVM();
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
			special.rateDetails.add(rateDetail);
		}
		
		
		CancellationPolicyVM cancel = new CancellationPolicyVM();
		
		cancel.penaltyCharge = true;
		
		special.cancellation.add(cancel);
		
		RateVM rateVM = new RateVM();
		rateVM.normalRate = normal;
		rateVM.isSpecialRate = false;
		rateVM.special = special;
		rateVM.cancellation.add(cancel);
		rateVM.applyToMarket = true;
		return ok(Json.toJson(rateVM));
	}
	
	
	@Transactional(readOnly=false)
    public static Result saveRate() throws ParseException {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					
		Form<RateWrapper> rateWrapperForm = Form.form(RateWrapper.class).bindFromRequest();
		List<RateVM> list = rateWrapperForm.get().rateObject;
		
		
		for(RateVM rate : list) {
			//rate.isSpecialRate = true;
			//System.out.println("RATE ........"+rate.currency+rate.fromDate+rate.toDate+rate.rateName+rate.getIsSpecialRate());

				RateMeta rateMeta = new RateMeta();
				rateMeta.setSupplierCode(rate.supplierCode);
				rateMeta.setCurrency(rate.currency);
				rateMeta.setRateName(rate.rateName);
				//rateMeta.setFromDate(format.parse(rate.fromDate));
				//rateMeta.setToDate(format.parse(rate.toDate));
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
    				
    				/*if(rate.allotmentmarket.allocation == 3){
    					RoomAllotedRateWise rAllotedRateWise = new RoomAllotedRateWise();
    					rAllotedRateWise.setAllowedRateDate(c.getTime());
    					rAllotedRateWise.setRoomCount(rate.allotmentmarket.choose);
    					rAllotedRateWise.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
    					rAllotedRateWise.save();
    				}*/
    				
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
					
					//System.out.println(vm.multiSelectGroup);
				}
				List<Country> listCity = new ArrayList<>();
				for(SelectedCountryVM cityvm : selectedCityVM){
					Country _city = Country.getCountryByName(cityvm.name);
					
					if(cityvm.ticked){
						listCity.add(_city);
					}
				}
				rateObject.setCountry(listCity);
				
			
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
				
				
				RateDetails rateDetails = new RateDetails();
				if(rate.isSpecialRate == true) {
					rateDetails.setSpecialRate(rate.isSpecialRate);
					rateDetails.setSpecialDays(rate.special.weekDays.toString());
				} else {
					rateDetails.setSpecialRate(rate.isSpecialRate);
				}
				rateDetails.setApplyToMarket(rate.applyToMarket);
				rateDetails.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
				rateDetails.save();
				
				
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
						
					personRate.setNormal(true);
					personRate.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
					personRate.save();
					
				}
				
				if(rate.isSpecialRate == true) {
					for(RateDetailsVM rateDetailsVM : rate.special.rateDetails) {
						PersonRate personRate2 = new PersonRate();
						personRate2.setNumberOfPersons(rateDetailsVM.name);
						personRate2.setRateValue(rateDetailsVM.rateValue);
							if(rateDetailsVM.includeMeals == true) {
								personRate2.setMeal(rateDetailsVM.includeMeals);
								personRate2.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
							} else {
								personRate2.setMeal(rateDetailsVM.includeMeals);
							}
							
						personRate2.setNormal(false);	
						personRate2.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
						personRate2.save();
						
					}
				}
				
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
						cancellation.setNormal(true);	
						cancellation.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
						cancellation.save();
					}
				}
				
				if(rate.isSpecialRate == true) {
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
							cancellation.setNormal(false);	
							cancellation.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
							cancellation.save();
						}
					}
				}
				
		}
		
		return ok();
	}
	
	@Transactional(readOnly=false)
    public static Result deleteRateMeta(long id) {
				
		RateDetails detail = RateDetails.findByRateMetaId(id);
		detail.delete();
		
		ApplicableDateOnRate.deleteByRateDates(id);
		
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
				
			    RoomAllotedRateWise rAllotedRateWise = RoomAllotedRateWise.findByRateId(rate.getId()); 
			    
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
				System.out.println("&&&&&&&&&&&&&&&&&&");
				System.out.println(rate.rateName);
				allotmentmarket.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,HotelRoomTypes.findById(rate.roomId)));
				
				allotmentmarket.merge();

				RateDetails rateDetails = RateDetails.findByRateMetaId(rate.getId());
				if(rate.getIsSpecialRate() == true) {   //	if(rate.isSpecialRate() == true) {
					rateDetails.setSpecialRate(rate.isSpecialRate);
					rateDetails.setSpecialDays(rate.special.weekDays.toString());
				} else {
					rateDetails.setSpecialRate(rate.isSpecialRate);
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
					
					//System.out.println(vm.multiSelectGroup);
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
					PersonRate personRate = PersonRate.findByRateMetaIdAndNormal(rate.getId(),true,rateDetailsVM.name);
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
							
							personRate.setNormal(true);
							personRate.merge();
					
				}
				
				if(rate.isSpecialRate == true) {
					for(RateDetailsVM rateDetailsVM : rate.special.rateDetails) {
						PersonRate personRate = PersonRate.findByRateMetaIdAndNormal(rate.getId(),false,rateDetailsVM.name);
						personRate.setNumberOfPersons(rateDetailsVM.name);
						personRate.setRateValue(rateDetailsVM.rateValue);
								if(rateDetailsVM.includeMeals == true) {
									personRate.setMeal(rateDetailsVM.includeMeals);
									personRate.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
								} else {
									personRate.setMeal(rateDetailsVM.includeMeals);
								}
								
								personRate.setNormal(false);	
								personRate.merge();
						
					}
				}
				
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
								cancellation.setNormal(true);	
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
								cancellation.setNormal(true);	
								cancellation.merge();
							}
						}	
				}
				
				if(rate.isSpecialRate == true) {
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
								cancellation.setNormal(false);	
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
								cancellation.setNormal(false);	
								cancellation.merge();
							}
						}
					}
				}
				
		}
		
		return ok();
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
					RateDetails rateDetails = RateDetails.findByRateMetaId(rate.getId());
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
					rateVM.setIsSpecialRate(rateDetails.isSpecialRate());
					rateVM.setId(rate.getId());
					rateVM.applyToMarket = rateDetails.isApplyToMarket();
					List<String> clist = new ArrayList<>();
					List<RateMeta> rList = RateMeta.getcountryByRate(rate.getId());
					for(RateMeta rMeta:rList){				
						
						for(Country country:rMeta.getCountry()){
						
							System.out.println(country.getCountryName());
							clist.add(country.getCountryName());
						}
						
					}
					
					rateVM.setAllocatedCountry(clist);
					
					
					NormalRateVM normalRateVM = new NormalRateVM();
					SpecialRateVM specialRateVM = new SpecialRateVM();
					AllotmentMarketVM aMarketVM = new AllotmentMarketVM();
					
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
			
					
					for(PersonRate person:personRate) {
						
						if(person.isNormal() == true){
							RateDetailsVM vm = new RateDetailsVM(person);
							normalRateVM.rateDetails.add(vm);
						}
						
						if(person.isNormal() == false) {
							RateDetailsVM vm = new RateDetailsVM(person);
							specialRateVM.rateDetails.add(vm);
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
								if(cancel.isNormal() == true){
									CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
									rateVM.cancellation.add(vm);
								}
								if(cancel.isNormal() == false) {
									CancellationPolicyVM vm = new CancellationPolicyVM(cancel);
									specialRateVM.cancellation.add(vm);
								}
							}
							
						rateVM.setNormalRate(normalRateVM);
						rateVM.setSpecial(specialRateVM);
					
						list.add(rateVM);
					
				map.put(rate.getId(), Long.parseLong("1"));	
			}
				
			}
			
		c.add(Calendar.DATE, 1);
			
		}
		
		//List<RateVM> list = new ArrayList<>();
	//	List<RateMeta> rateMeta = RateMeta.getRatecheckdateWise(currencyType,room,c.getTime());
	//	List<RateMeta> rateMeta = RateMeta.searchRateMeta(currencyType, format.parse(fromDate), format.parse(toDate), room);
		
		/*for(RateMeta rate:rateMeta) {
			
		}*/
		
		return ok(Json.toJson(list));
	}
	
	@Transactional(readOnly=true)
    public static Result fetchToEditHotelDetails(long supplierCode) {
		//this method is used to fetch the room details for edit. this method will return json data..
		//System.out.println(supplierCode);
		//HotelRoomTypes roomType = null;
		/*if (roomId != -1) {
			roomType = new HotelRoomTypes();
			return ok(Json.toJson(roomType));
		} else {*/
			System.out.println(supplierCode);
			List<HotelRoomTypes> roomTypeInfo = HotelRoomTypes.getHotelRoomDetails(supplierCode);
		//}
		return ok(Json.toJson(roomTypeInfo));
	}
	
	
	@Transactional(readOnly=true)
    public static Result getroomtypesInfo(long RoomId) {
		
			System.out.println(RoomId);
			HotelRoomTypes roomType = HotelRoomTypes.getHotelRoomDetailsInfo(RoomId);
		
		return ok(Json.toJson(roomType));
	}
	
	
	@Transactional(readOnly=false)
    public static Result saveOrUpdateHotelRoom() {
			
		JsonNode json = request().body().asJson();
		DynamicForm form = DynamicForm.form().bindFromRequest();
		Json.fromJson(json, RoomtypeVM.class);
		RoomtypeVM roomtypeVM = Json.fromJson(json, RoomtypeVM.class);
		
		System.out.println("&^&&^&&^&^&^&^&&&^");
		System.out.println(roomtypeVM.getRoomname());
		System.out.println(roomtypeVM.getSupplierCode());
		System.out.println("&^&&^&&^&^&^&^&&&^");
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
			
			hotelroomTypes.setAmenities(RoomAmenities.getroomamenities(roomtypeVM.getRoomamenities()));
					
			for(RoomChildpoliciVM childVM : roomtypeVM.getRoomchildPolicies())
			{
				RoomChildPolicies roomchildPolicies = new RoomChildPolicies();
				roomchildPolicies.setAllowedChildAgeFrom(childVM.getAllowedChildAgeFrom());
				roomchildPolicies.setAllowedChildAgeTo(childVM.getAllowedChildAgeTo());
				roomchildPolicies.setYears(childVM.getYears());
				roomchildPolicies.setNetRate(childVM.getNetRate());
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
					
			for(RoomChildpoliciVM childVM : roomtypeVM.getRoomchildPolicies())
			{
							
				RoomChildPolicies roomchildPolicies = RoomChildPolicies.findById(childVM.getRoomchildPolicyId());
				roomchildPolicies.setAllowedChildAgeFrom(childVM.getAllowedChildAgeFrom());
				roomchildPolicies.setAllowedChildAgeTo(childVM.getAllowedChildAgeTo());
				roomchildPolicies.setYears(childVM.getYears());
				roomchildPolicies.setNetRate(childVM.getNetRate());
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
         System.out.println(originalFileName);
         try {
        	   
                  BufferedImage originalImage = ImageIO.read(src);
                        Thumbnails.of(originalImage)
                            .size(220, 220)
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
           
 		 System.out.println(fileName);
 		
 		HotelRoomTypes hotelroomTypes =HotelRoomTypes.findById(Long.parseLong(form.get("roomId")));
 		 if(hotelroomTypes != null)
 		 {
 			hotelroomTypes.setRoomPic(originalFileName); 
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
		System.out.println("getAvailableRoomAmenities");
		return ok(Json.toJson(RoomAmenities.getRoomAmenities()));
	}
	
	@Transactional(readOnly=false)
	public static Result deleteRoomchild(int id) {

		RoomChildPolicies.deletechildRel(id);
		RoomChildPolicies.deletefindById(id);
		
		/*for(RoomChildPolicies childPolicies: hotelroomtypes.getRoomchildPolicies()){
			childPolicies.delete();
			System.out.println("hii111111");
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
		//System.out.println(json.asInt());
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
		//System.out.println(json.asInt());
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


