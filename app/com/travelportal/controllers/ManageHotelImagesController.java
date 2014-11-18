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
import com.travelportal.domain.ImgPath;
import com.travelportal.domain.InfoWiseImagesPath;



public class ManageHotelImagesController extends Controller {
	
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
	
	

	//savegeneralImg
	@Transactional(readOnly=false)
	public static Result savegeneralImg() throws IOException {
		

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
      //   try {
        	   
                  BufferedImage originalImage = ImageIO.read(src);
                        Thumbnails.of(originalImage)
                            .size(220, 220)
                            .toFile(f);
                            File _f = new File(originalFileName);
                            Thumbnails.of(originalImage).scale(1.0).
                            toFile(_f);
           
        	 
        // } catch (FileNotFoundException e) {
             //    e.printStackTrace();
        // } catch (IOException e) {
           //      e.printStackTrace();
       //  } finally {
                 try {
                         if(out != null) out.close();
                 } catch (IOException e) {
                         e.printStackTrace();
                 }
        // }
           
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
	
	@Transactional(readOnly=false)
	public static Result saveLobbyImg() {
		

		DynamicForm form = DynamicForm.form().bindFromRequest();
		
		FilePart picture = request().body().asMultipartFormData().getFile("LobbyImage");
		
		System.out.println(form.get("supplierCode"));
		
		createDirLobby(rootDir,Long.parseLong(form.get("supplierCode")));
		 String fileName = picture.getFilename();
		
		 String ThumbnailImage = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator+"LobbyImage"+ File.separator+"Logo_thumbnail."+FilenameUtils.getExtension(fileName);
         String originalFileName = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator +"LobbyImage"+File.separator+"Original_image."+FilenameUtils.getExtension(fileName);
		 
		 
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
 		infowiseimagesPath.setHotel_Lobby(ThumbnailImage);
 		infowiseimagesPath.save();
 		 }
 		 else
 		 {
 			infowiseimagesPath.setHotel_Lobby(ThumbnailImage);
 	 		infowiseimagesPath.merge();
 		 }
 		
		return ok(Json.toJson(infowiseimagesPath));
 			
	}
	public static void createDirLobby(String rootDir, long supplierCode) {
        File file3 = new File(rootDir + File.separator+supplierCode +File.separator+ "ManageHotelImages"+File.separator+"LobbyImage");
        if (!file3.exists()) {
                file3.mkdirs();
        }
	}
	@Transactional(readOnly=false)
	public static Result getLobbyImagePath(long supplierCode) {
		
		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(supplierCode);
		File f = new File( infowiseimagesPath.getHotel_Lobby());
        return ok(f);		
		
	}
	
	
	@Transactional(readOnly=false)
	public static Result saveRoomImg() {
		

		DynamicForm form = DynamicForm.form().bindFromRequest();
		
		FilePart picture = request().body().asMultipartFormData().getFile("RoomImage");
		
		System.out.println(form.get("supplierCode"));
		
		createDirRoom(rootDir,Long.parseLong(form.get("supplierCode")));
		 String fileName = picture.getFilename();
		
		 String ThumbnailImage = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator+"RoomImage"+ File.separator+"Logo_thumbnail."+FilenameUtils.getExtension(fileName);
         String originalFileName = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator +"RoomImage"+File.separator+"Original_image."+FilenameUtils.getExtension(fileName);
		 
		 
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
 		infowiseimagesPath.setHotelRoom(ThumbnailImage);
 		infowiseimagesPath.save();
 		 }
 		 else
 		 {
 			infowiseimagesPath.setHotelRoom(ThumbnailImage);
 	 		infowiseimagesPath.merge();
 		 }
 		
		return ok(Json.toJson(infowiseimagesPath));
 			
	}
	public static void createDirRoom(String rootDir, long supplierCode) {
        File file3 = new File(rootDir + File.separator+supplierCode +File.separator+ "ManageHotelImages"+File.separator+"RoomImage");
        if (!file3.exists()) {
                file3.mkdirs();
        }
	}
	@Transactional(readOnly=false)
	public static Result getRoomImagePath(long supplierCode) {
		
		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(supplierCode);
		File f = new File( infowiseimagesPath.getHotelRoom());
        return ok(f);		
		
	}
	
	@Transactional(readOnly=false)
	public static Result saveAmenitiesServicesImg() {
		

		DynamicForm form = DynamicForm.form().bindFromRequest();
		
		FilePart picture = request().body().asMultipartFormData().getFile("AmenitiesServicesImage");
		
		System.out.println(form.get("supplierCode"));
		
		createDirAmenitiesServices(rootDir,Long.parseLong(form.get("supplierCode")));
		 String fileName = picture.getFilename();
		
		 String ThumbnailImage = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator+"AmenitiesServicesImage"+ File.separator+"Logo_thumbnail."+FilenameUtils.getExtension(fileName);
         String originalFileName = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator +"AmenitiesServicesImage"+File.separator+"Original_image."+FilenameUtils.getExtension(fileName);
		 
		 
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
 		infowiseimagesPath.setAmenitiesServices(ThumbnailImage);
 		infowiseimagesPath.save();
 		 }
 		 else
 		 {
 			infowiseimagesPath.setAmenitiesServices(ThumbnailImage);
 	 		infowiseimagesPath.merge();
 		 }
 		
		return ok(Json.toJson(infowiseimagesPath));
 			
	}
	public static void createDirAmenitiesServices(String rootDir, long supplierCode) {
        File file3 = new File(rootDir + File.separator+supplierCode +File.separator+ "ManageHotelImages"+File.separator+"AmenitiesServicesImage");
        if (!file3.exists()) {
                file3.mkdirs();
        }
	}
	@Transactional(readOnly=false)
	public static Result getAmenitiesServicesImagePath(long supplierCode) {
		
		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(supplierCode);
		File f = new File( infowiseimagesPath.getAmenitiesServices());
        return ok(f);		
		
	}
	
	//
	//
	
	@Transactional(readOnly=false)
	public static Result saveLeisureorSportsImg() {
		

		DynamicForm form = DynamicForm.form().bindFromRequest();
		
		FilePart picture = request().body().asMultipartFormData().getFile("LeisureorSportsImage");
		
		System.out.println(form.get("supplierCode"));
		
		createDirLeisureorSports(rootDir,Long.parseLong(form.get("supplierCode")));
		 String fileName = picture.getFilename();
		
		 String ThumbnailImage = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator+"LeisureorSportsImage"+ File.separator+"Logo_thumbnail."+FilenameUtils.getExtension(fileName);
         String originalFileName = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator +"LeisureorSportsImage"+File.separator+"Original_image."+FilenameUtils.getExtension(fileName);
		 
		 
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
 		infowiseimagesPath.setLeisureSports(ThumbnailImage);
 		infowiseimagesPath.save();
 		 }
 		 else
 		 {
 			infowiseimagesPath.setLeisureSports(ThumbnailImage);
 	 		infowiseimagesPath.merge();
 		 }
 		
		return ok(Json.toJson(infowiseimagesPath));
 			
	}
	public static void createDirLeisureorSports(String rootDir, long supplierCode) {
        File file3 = new File(rootDir + File.separator+supplierCode +File.separator+ "ManageHotelImages"+File.separator+"LeisureorSportsImage");
        if (!file3.exists()) {
                file3.mkdirs();
        }
	}
	@Transactional(readOnly=false)
	public static Result getLeisureorSportsImagePath(long supplierCode) {
		
		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(supplierCode);
		File f = new File( infowiseimagesPath.getLeisureSports());
        return ok(f);		
		
	}
	
	//
	//
	@Transactional(readOnly=false)
	public static Result saveMapImg() {
		

		DynamicForm form = DynamicForm.form().bindFromRequest();
		
		FilePart picture = request().body().asMultipartFormData().getFile("MapImage");
		
		System.out.println(form.get("supplierCode"));
		
		createDirMap(rootDir,Long.parseLong(form.get("supplierCode")));
		 String fileName = picture.getFilename();
		
		 String ThumbnailImage = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator+"MapImage"+ File.separator+"Logo_thumbnail."+FilenameUtils.getExtension(fileName);
         String originalFileName = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator +"MapImage"+File.separator+"Original_image."+FilenameUtils.getExtension(fileName);
		 
		 
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
 		infowiseimagesPath.setMap_image(ThumbnailImage);
 		infowiseimagesPath.save();
 		 }
 		 else
 		 {
 			infowiseimagesPath.setMap_image(ThumbnailImage);
 	 		infowiseimagesPath.merge();
 		 }
 		
		return ok(Json.toJson(infowiseimagesPath));
 			
	}
	public static void createDirMap(String rootDir, long supplierCode) {
        File file3 = new File(rootDir + File.separator+supplierCode +File.separator+ "ManageHotelImages"+File.separator+"MapImage");
        if (!file3.exists()) {
                file3.mkdirs();
        }
	}
	@Transactional(readOnly=false)
	public static Result getMapImagePath(long supplierCode) {
		
		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(supplierCode);
		File f = new File( infowiseimagesPath.getMap_image());
        return ok(f);		
		
	}
	
}
