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

import com.travelportal.domain.HotelHealthAndSafety;
import com.travelportal.domain.ImgPath;
import com.travelportal.domain.InfoWiseImagesPath;

import play.Play;
import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
//import org.junit.experimental.theories.internal.AllMembersSupplier;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData.FilePart;




public class SupplierAgreementController extends Controller {
	
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


@Transactional(readOnly=false)
public static Result savepdf(){
	DynamicForm form = DynamicForm.form().bindFromRequest();

	//HotelHealthAndSafety hAndSafety=HotelHealthAndSafety.findById(Long.parseLong(form.get("supplierCode")));
	
	FilePart picture = request().body().asMultipartFormData().getFile("file1");
	
	createDir(rootDir,Long.parseLong(form.get("supplierCode")));
	 String fileName = picture.getFilename();
	 String imgPath = rootDir + File.separator +Long.parseLong(form.get("supplierCode"))+File.separator+ "SupplierAgreement"+ File.separator+"SupplierAgreement."+FilenameUtils.getExtension(fileName);
	
     File src = picture.getFile();
     OutputStream out = null;
     BufferedImage image = null;
     File f = new File(imgPath);
     System.out.println(imgPath);
     try {
    	 Files.copy(src.toPath(),f.toPath(),java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    	
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
  
		
	return ok();
	
}

@Transactional(readOnly=false)
public static Result getshowpdf(){
	

	DynamicForm form = DynamicForm.form().bindFromRequest();
	//FilePart picture = request().body().asMultipartFormData().getFile("generalImg");
	System.out.println(form.get("supplierCode"));
		createDir(rootDir,Long.parseLong(form.get("supplierCode")));
	
	String PdfFile = rootDir + File.separator + Long.parseLong(form.get("supplierCode")) +File.separator+ "SupplierAgreement"+File.separator+"SupplierAgreement.pdf";
	
	return ok();
	
}
	
@Transactional(readOnly=false)
public static Result getPdfPath(long supplierCode) {
	System.out.println("###yogesh######");
	System.out.println(supplierCode);
	createDir(rootDir,supplierCode);
	response().setContentType("application/pdf");
	response().setHeader("Content-Disposition", "inline; filename="+"SupplierAgreement.pdf");
	
	String PdfFile = rootDir + File.separator + supplierCode +File.separator+ "SupplierAgreement"+File.separator+"SupplierAgreement.pdf";
	File f = new File(PdfFile);
	response().setHeader("Content-Length", ((int)f.length())+"");
    return ok(f);	
	
	
}

public static void createDir(String rootDir, long supplierCode) {
    File file3 = new File(rootDir + File.separator+supplierCode +File.separator+ "SupplierAgreement");
    if (!file3.exists()) {
            file3.mkdirs();
    }
}


/*
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
	File f = new File(infowiseimagesPath.getGeneralPicture());
    return ok(f);		
	
}*/

	
	

	
}
