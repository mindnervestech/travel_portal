package com.travelportal.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FilenameUtils;

import play.Play;
import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import scala.Array;

import com.travelportal.domain.HotelImagesPath;
import com.travelportal.domain.InfoWiseImagesPath;
import com.travelportal.vm.AllImagesVM;



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
	
/*List<FilePart> picture = request().body().asMultipartFormData().getFiles();

System.out.println(form.get("supplierCode"));
System.out.println(form.get("description"));

createDir(rootDir,Long.parseLong(form.get("supplierCode")));
for(int i=0; i< picture.size()-1 ;i++){
	String fileName = picture.get(i).getFilename();
	System.out.println("-------------------");
	System.out.println(fileName);
}*/

	//savegeneralImg
	@Transactional(readOnly=false)
	public static Result savegeneralImg() throws IOException {
		

		DynamicForm form = DynamicForm.form().bindFromRequest();
		
		FilePart picture = request().body().asMultipartFormData().getFile("generalImg");
		
		System.out.println(form.get("supplierCode"));
		System.out.println(form.get("description"));
	
	
		createDir(rootDir,Long.parseLong(form.get("supplierCode")),form.get("index"));
		 String fileName = picture.getFilename();
		
		 String ThumbnailImage = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator+form.get("index")+ File.separator+"Logo_thumbnail."+FilenameUtils.getExtension(fileName);
         String originalFileName = rootDir + File.separator + +Long.parseLong(form.get("supplierCode"))+File.separator+ "ManageHotelImages"+ File.separator +form.get("index")+File.separator+"Original_image."+FilenameUtils.getExtension(fileName);
		 
         File src = picture.getFile();
         OutputStream out = null;
         BufferedImage image = null;
         File f = new File(ThumbnailImage);
         System.out.println(originalFileName);
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
           
 		 System.out.println(fileName);
 		 
		HotelImagesPath hotelImagesPath = HotelImagesPath.findByIdAndIndex(
				Long.parseLong(form.get("supplierCode")),
				Long.parseLong(form.get("index")));
		if (hotelImagesPath == null) {
			hotelImagesPath = new HotelImagesPath();
			hotelImagesPath.setIndexValue(Long.parseLong(form.get("index")));
			hotelImagesPath.setSupplierCode(Long.parseLong(form
					.get("supplierCode")));
			hotelImagesPath.setPictureName(form.get("pictureName"));
			hotelImagesPath.setPicturePath(ThumbnailImage);
			hotelImagesPath.setPictureDescription(form.get("description"));
			hotelImagesPath.save();
		} else {
			hotelImagesPath.setSupplierCode(Long.parseLong(form
					.get("supplierCode")));
			hotelImagesPath.setPictureName(form.get("pictureName"));
			hotelImagesPath.setPicturePath(ThumbnailImage);
			hotelImagesPath.setPictureDescription(form.get("description"));
			hotelImagesPath.merge();
		}
 		
 		/*InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath.findById(Long.parseLong(form.get("supplierCode")));
 		 if(infowiseimagesPath == null)
 		 {
 		infowiseimagesPath = new InfoWiseImagesPath();
 		infowiseimagesPath.setSupplierCode(Long.parseLong(form.get("supplierCode")));
 		infowiseimagesPath.setGeneralPicture(ThumbnailImage);
 		infowiseimagesPath.setGeneralDescription(form.get("description"));
 		infowiseimagesPath.save();
 		 }
 		 else
 		 {
 			infowiseimagesPath.setGeneralPicture(ThumbnailImage);
 			infowiseimagesPath.setGeneralDescription(form.get("description"));
 	 		infowiseimagesPath.merge();
 		 }*/
 		
		return ok(Json.toJson(hotelImagesPath));
		
	}
	public static void createDir(String rootDir, long supplierCode,String index) {
        File file3 = new File(rootDir + File.separator+supplierCode +File.separator+ "ManageHotelImages"+File.separator+index);
        if (!file3.exists()) {
                file3.mkdirs();
        }
	}
	@Transactional(readOnly=false)
	public static Result getImagePath(long supplierCode,long indexValue) {
		
		HotelImagesPath hImagesPath = HotelImagesPath.findByIdAndIndex(supplierCode,indexValue);
		File f;
		if(hImagesPath != null){
		 
			if(hImagesPath.getPicturePath() != null){
				 f = new File(hImagesPath.getPicturePath());
			}else{
				f = new File("C:\\mypath\\default\\logo.jpg");
			}
		}
		else{
			f = new File("C:\\mypath\\default\\logo.jpg");
		}
				
        return ok(f);		
    		
	}
	
	
	@Transactional(readOnly=false)
	public static Result finddescrip(long supplierCode) {
		InfoWiseImagesPath infoImagesPath= InfoWiseImagesPath.findById(supplierCode);
		return ok(Json.toJson(infoImagesPath));
	
	}
	
	@Transactional(readOnly=false)
	public static Result getAllImgs(long supplierCode){
		List<AllImagesVM> aImagesVMs = new ArrayList<>();
		List<HotelImagesPath> hImagesPath = HotelImagesPath.findBySupplier(supplierCode);
		/*for(HotelImagesPath hPath:hImagesPath){
			AllImagesVM aVm = new AllImagesVM();
			aVm.indexValue = hPath.getIndexValue();
			aVm.file =  new File(hPath.getPicturePath());
			aVm.pictureName = hPath.getPictureName();
			
			aImagesVMs.add(aVm);
			
		}*/
		return ok(Json.toJson(hImagesPath));
	}
	
	
	
	
	
}
