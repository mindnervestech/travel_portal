package com.travelportal.vm;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class AllImagesVM {
	
	
	public long picId;
	public long supplierCode;
	public String picturePath;
	public File file;
	public String pictureName;
	public String pictureDescription;
	public long indexValue;
	public long getPicId() {
		return picId;
	}
	public void setPicId(long picId) {
		this.picId = picId;
	}
	public long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	public String getPictureDescription() {
		return pictureDescription;
	}
	public void setPictureDescription(String pictureDescription) {
		this.pictureDescription = pictureDescription;
	}
	public long getIndexValue() {
		return indexValue;
	}
	public void setIndexValue(long indexValue) {
		this.indexValue = indexValue;
	}
	
	
	
	
	
	
}
