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
import com.travelportal.domain.MealType;
import com.travelportal.domain.rooms.CancellationPolicy;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.PersonRate;
import com.travelportal.domain.rooms.RateDetails;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RateWrapper;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.domain.rooms.RoomChildPolicies;
import com.travelportal.vm.AllocatedCitiesVM;
import com.travelportal.vm.CancellationPolicyVM;
import com.travelportal.vm.NormalRateVM;
import com.travelportal.vm.RateDetailsVM;
import com.travelportal.vm.RateVM;
import com.travelportal.vm.RoomChildpoliciVM;
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
		List<Object[]> types = HotelRoomTypes.getRoomTypes(code);
		return ok(Json.toJson(types));
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
    public static Result getRateObject(String roomType) {
		
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
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Form<RateWrapper> rateWrapperForm = Form.form(RateWrapper.class).bindFromRequest();
		List<RateVM> list = rateWrapperForm.get().rateObject;
		
		for(RateVM rate : list) {
			System.out.println("RATE ........"+rate.currency+rate.fromDate+rate.toDate+rate.rateName+rate.isSpecialRate());

				RateMeta rateMeta = new RateMeta();
				rateMeta.setCurrency(rate.currency);
				rateMeta.setRateName(rate.rateName);
				rateMeta.setFromDate(format.parse(rate.fromDate));
				rateMeta.setToDate(format.parse(rate.toDate));
				rateMeta.setRoomType(HotelRoomTypes.findByName(rate.roomType));
				rateMeta.save();
				
				RateMeta rateObject = RateMeta.findRateMeta(rate.rateName,rate.currency,format.parse(rate.fromDate),format.parse(rate.toDate),HotelRoomTypes.findByName(rate.roomType));
				List<SelectedCityVM> selectedCityVM = new ArrayList<>(); 	
				for(AllocatedCitiesVM vm: rate.allocatedCities) {
					if(vm.multiSelectGroup == false && vm.name != null){
						SelectedCityVM cityVM = new SelectedCityVM();
						cityVM.name = vm.name;
						cityVM.ticked = vm.ticked;
						selectedCityVM.add(cityVM);
					}
					
					System.out.println(vm.multiSelectGroup);
				}
				List<City> listCity = new ArrayList<>();
				for(SelectedCityVM cityvm : selectedCityVM){
					City _city = City.getCitiByName(cityvm.name);
					
					if(cityvm.ticked){
						listCity.add(_city);
					}
				}
				rateObject.setCities(listCity);
				
				RateDetails rateDetails = new RateDetails();
				if(rate.isSpecialRate == true) {
					rateDetails.setSpecialRate(rate.isSpecialRate);
					rateDetails.setSpecialDays(rate.special.weekDays.toString());
				} else {
					rateDetails.setSpecialRate(rate.isSpecialRate);
				}
				rateDetails.setApplyToMarket(rate.applyToMarket);
				rateDetails.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,format.parse(rate.fromDate),format.parse(rate.toDate),HotelRoomTypes.findByName(rate.roomType)));
				rateDetails.save();
				
				
				for(RateDetailsVM rateDetailsVM : rate.normalRate.rateDetails) {
					PersonRate personRate = new PersonRate();
					personRate.setNumberOfPersons(rateDetailsVM.name);
					personRate.setRateValue(rateDetailsVM.rateValue);
						if(rateDetailsVM.includeMeals == true) {
							personRate.setMeal(rateDetailsVM.includeMeals);
							personRate.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
						} else {
							personRate.setMeal(rateDetailsVM.includeMeals);
						}
						
					personRate.setNormal(true);
					personRate.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,format.parse(rate.fromDate),format.parse(rate.toDate),HotelRoomTypes.findByName(rate.roomType)));
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
						personRate2.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,format.parse(rate.fromDate),format.parse(rate.toDate),HotelRoomTypes.findByName(rate.roomType)));
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
						cancellation.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,format.parse(rate.fromDate),format.parse(rate.toDate),HotelRoomTypes.findByName(rate.roomType)));
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
							cancellation.setRate(RateMeta.findRateMeta(rate.rateName,rate.currency,format.parse(rate.fromDate),format.parse(rate.toDate),HotelRoomTypes.findByName(rate.roomType)));
							cancellation.save();
						}
					}
				}
				
		}
		
		return ok();
	}
	
	@Transactional(readOnly=false)
    public static Result deleteRateMeta(long id) {
		RateMeta rateMeta = RateMeta.findById(id);
		rateMeta.delete();
		RateDetails detail = RateDetails.findByRateMetaId(id);
		detail.delete();
		PersonRate.deleteByRateMetaId(id);
		CancellationPolicy.deleteByRateMetaId(id);
		return ok();
	}
	
	
	@Transactional(readOnly=false)
    public static Result updateRateMeta() throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Form<RateWrapper> rateWrapperForm = Form.form(RateWrapper.class).bindFromRequest();
		List<RateVM> list = rateWrapperForm.get().rateObject;
		
		for(RateVM rate : list) {

				RateMeta rateMeta = RateMeta.findById(rate.getId());
				rateMeta.setRateName(rate.rateName);
				rateMeta.merge();
				
				RateDetails rateDetails = RateDetails.findByRateMetaId(rate.getId());
				if(rate.isSpecialRate() == true) {
					rateDetails.setSpecialRate(rate.isSpecialRate);
					rateDetails.setSpecialDays(rate.special.weekDays.toString());
				} else {
					rateDetails.setSpecialRate(rate.isSpecialRate);
				}
				rateDetails.merge();
				
				
				for(RateDetailsVM rateDetailsVM : rate.normalRate.rateDetails) {
					PersonRate personRate = PersonRate.findByRateMetaIdAndNormal(rate.getId(),true,rateDetailsVM.name);
					personRate.setNumberOfPersons(rateDetailsVM.name);
					personRate.setRateValue(rateDetailsVM.rateValue);
							if(rateDetailsVM.includeMeals == true) {
								personRate.setMeal(rateDetailsVM.includeMeals);
								personRate.setMealType(MealType.getmealTypeByName(rateDetailsVM.meals));
							} else {
								personRate.setMeal(rateDetailsVM.includeMeals);
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
    public static Result getRateData(String room,String fromDate,String toDate,String currencyType) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<RateVM> list = new ArrayList<>();
		List<RateMeta> rateMeta = RateMeta.searchRateMeta(currencyType, format.parse(fromDate), format.parse(toDate), HotelRoomTypes.findByName(room));
		for(RateMeta rate:rateMeta) {
			RateDetails rateDetails = RateDetails.findByRateMetaId(rate.getId());
			List<PersonRate> personRate = PersonRate.findByRateMetaId(rate.getId());
			List<CancellationPolicy> cancellation = CancellationPolicy.findByRateMetaId(rate.getId());
			
			RateVM rateVM = new RateVM();
			rateVM.setCurrency(rate.getCurrency());
			rateVM.setFromDate(format.format(rate.getFromDate()));
			rateVM.setToDate(format.format(rate.getToDate()));
			rateVM.setRoomType(rate.getRoomType().getRoomType());
			rateVM.setSpecialRate(rateDetails.isSpecialRate());
			rateVM.setRateName(rate.getRateName());
			rateVM.setId(rate.getId());
			rateVM.applyToMarket = rateDetails.isApplyToMarket();
			
			NormalRateVM normalRateVM = new NormalRateVM();
			SpecialRateVM specialRateVM = new SpecialRateVM();
			
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
							System.out.println(sb.toString());
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
		}
		
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
		System.out.println(roomtypeVM.getRoomId());
		System.out.println("&^&&^&&^&^&^&^&&&^");
		
		if(roomtypeVM.getRoomId()== null || roomtypeVM.getRoomId()=="")
		{
			
			HotelRoomTypes hotelroomTypes = new HotelRoomTypes();
			hotelroomTypes.setRoomType(roomtypeVM.getRoomname());
			hotelroomTypes.setExtraBedAllowed(roomtypeVM.getExtraBedAllowed());
			hotelroomTypes.setChargesForChildren(roomtypeVM.getChargesForChildren());
			hotelroomTypes.setRoomSuiteType(roomtypeVM.getRoomSuiteType());
			hotelroomTypes.setChildAllowedFreeWithAdults(roomtypeVM.getChildAllowedFreeWithAdults());
			hotelroomTypes.setMaxAdultOccupancy(roomtypeVM.getMaxAdultOccupancy());
			hotelroomTypes.setMaxOccupancy(roomtypeVM.getMaxOccupancy());
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
	
	@Transactional(readOnly=true)
	public static Result getAvailableRoomAmenities() {
		System.out.println("getAvailableRoomAmenities");
		return ok(Json.toJson(RoomAmenities.getRoomAmenities()));
	}
	
	@Transactional(readOnly=false)
	public static Result deleteRoomchild(int id) {

		HotelRoomTypes hotelroomtypes = HotelRoomTypes.findById(id);
		
		for(RoomChildPolicies childPolicies: hotelroomtypes.getRoomchildPolicies()){
			childPolicies.delete();
			System.out.println("hii111111");
		}
		hotelroomtypes.setRoomchildPolicies(null);
		hotelroomtypes.merge();

		return ok();
	}
	
	@Transactional(readOnly=false)
	public static Result savegeneralImg() {
		

		DynamicForm form = DynamicForm.form().bindFromRequest();
		
		FilePart picture = request().body().asMultipartFormData().getFile("generalImg");
		
		System.out.println(form.get("supplierCode"));
		
		createDir(rootDir,Long.parseLong(form.get("supplierCode")));
		 String fileName = picture.getFilename();
		
		 String ThumbnailImage = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator+"GeneralPic"+ File.separator+"Logo_thumbnail."+FilenameUtils.getExtension(fileName);
         String originalFileName = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator +"GeneralPic"+File.separator+"Original_image."+FilenameUtils.getExtension(fileName);
		 
		 
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
 		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(Long.parseLong(form.get("supplierCode")));
 		 if(infowiseimagesPath == null)
 		 {
 		infowiseimagesPath = new InfoWiseImagesPath();
 		infowiseimagesPath.setSupplierCode(Long.parseLong(form.get("supplierCode")));
 		infowiseimagesPath.setGeneralPicture(ThumbnailImage);
 		infowiseimagesPath.save();
 		 }
 		 else
 		 {
 			infowiseimagesPath.setGeneralPicture(ThumbnailImage);
 	 		infowiseimagesPath.merge();
 		 }
 		
		return ok(Json.toJson(infowiseimagesPath));
 		//return ok();
		
	}
	public static void createDir(String rootDir, long supplierCode) {
        File file3 = new File(rootDir + File.separator+supplierCode +File.separator+ "ManageHotelImages"+File.separator+"GeneralPic");
        if (!file3.exists()) {
                file3.mkdirs();
        }
	}
	@Transactional(readOnly=false)
	public static Result getImagePath(long supplierCode) {
		
		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(supplierCode);
		File f = new File( infowiseimagesPath.getGeneralPicture());
        return ok(f);
		
		
	}
	
	///////
	@Transactional(readOnly = true)
	public static Result getMarketGroup(long id) {
		List<Country> country = Country.getCountries();
		List<MarketVM> group = new ArrayList<MarketVM>();
		for (Country c : country) {
			MarketVM marketvm = new MarketVM();
			CountryVM countryvm = new CountryVM();
			countryvm.countryCode = c.getCountryCode();
			countryvm.countryName = c.getCountryName();

			List<CityVM> cityvm = new ArrayList<CityVM>();
			List<City> city = City.getCities(c.getCountryCode());
			for (City _city : city) {
				
				CityVM _cityvm = new CityVM();
				_cityvm.id = _city.getCityCode();
				_cityvm.cityCountryCode = _city.getCountry().getCountryCode();
				_cityvm.cityName = _city.getCityName();
				if(id != 0) {
					RateMeta rates = RateMeta.getRatesById(id);
					for(City cty : rates.getCities()){
						if(cty.getCityCode() == _city.getCityCode()){
							_cityvm.tick = true;
							break;
						}else{
							_cityvm.tick = false;
						}
							
					}
				} else {
					_cityvm.tick = false;
				}
				
				cityvm.add(_cityvm);
			}
			countryvm.cityvm = cityvm;
			marketvm.country = countryvm;
			group.add(marketvm);
		}
		return ok(Json.toJson(group));
	}
	
	public static class MarketVM {
		public CountryVM country;

	}

	public static class CountryVM {
		public int countryCode;
		public String countryName;
		public List<CityVM> cityvm;
	}

	public static class CityVM {
		public int id;
		public int cityCountryCode;
		public String cityName;
		public boolean tick;

	}
	
	public static class SelectedCityVM {

		public String name;
		public int countryCode;
		public boolean ticked;

	}
	public static class VM {
		public int id;
		public List<SelectedCityVM> city = new ArrayList<SelectedCityVM>();
	}
	

	@Transactional(readOnly = false)
	public static Result setCitySelection() {
		//JsonNode json = request().body().asJson().get("id");
		JsonNode jn = request().body().asJson();
		//System.out.println(json.asInt());
		//jn.as
		
		VM c = Json.fromJson(jn, VM.class);
		int ratesId  = c.id;
		List<SelectedCityVM> city = c.city;
		RateMeta rates = RateMeta.getRatesById(ratesId);
		
		if(rates != null && rates.getCities() != null && !rates.getCities().isEmpty())
		{
			rates.getCities().removeAll(rates.getCities());
			//JPA.em().merge(rates);
		}
		
		List<City> listCity = new ArrayList<>();
		for(SelectedCityVM cityvm : city){
			City _city = City.getCitiByName(cityvm.name);
			
			if(cityvm.ticked){
				listCity.add(_city);
			}
		}
		rates.setCities(listCity);
		return ok();

	}

	
	
}


