package com.travelportal.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;

import play.Play;
import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelportal.domain.HotelHealthAndSafety;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.HotelServices;
import com.travelportal.domain.ImgPath;
import com.travelportal.domain.InfoWiseImagesPath;
import com.travelportal.domain.MealType;
import com.travelportal.domain.rooms.ChildPolicies;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RoomAmenities;
import com.travelportal.domain.rooms.RoomChildPolicies;
import com.travelportal.vm.ChildpoliciVM;
import com.travelportal.vm.HotelmealVM;
import com.travelportal.vm.RoomChildpoliciVM;
import com.travelportal.vm.RoomType;
import com.travelportal.vm.RoomtypeVM;

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
			System.out.println("hii");
		}
		hotelroomtypes.setRoomchildPolicies(null);
		hotelroomtypes.merge();

		return ok();
	}
	

	//savegeneralImg
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
                            .size(124, 124)
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
	
	
}
