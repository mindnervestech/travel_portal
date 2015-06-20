package com.travelportal.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="hotel_images")
public class HotelImagesPath { 
	@Column(name="id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long picId;
	@Column(name="supplier_code")
	private long supplierCode;
	@Column(name="picture_path")
	private String picturePath;
	@Column(name="picture_name")
	private String pictureName;
	@Column(name="picture_description")
	private String pictureDescription;
	@Column(name="index_value")
	private long indexValue;
	

	

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

	
	public static HotelImagesPath findById(Long supplierCode) {
		try
		{
			return (HotelImagesPath) JPA.em().createQuery("select c from HotelImagesPath c where c.supplierCode = ?1").setParameter(1, supplierCode).getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
    }
	
	public static HotelImagesPath findByIdAndIndex(Long supplierCode,Long indexValue) {
		try
		{
			return (HotelImagesPath) JPA.em().createQuery("select c from HotelImagesPath c where c.supplierCode = ?1 AND c.indexValue = ?2").setParameter(1, supplierCode).setParameter(2, indexValue).getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
    }
	
	public static List<HotelImagesPath> findBySupplier(Long supplierCode) {
		try
		{
			return (List<HotelImagesPath>) JPA.em().createQuery("select c from HotelImagesPath c where c.supplierCode = ?1").setParameter(1, supplierCode).getResultList();
		}
		catch(Exception ex){
			return null;
		}
    }
	
	@Transactional
    public void save() {
		JPA.em().persist(this);
        JPA.em().flush();     
    }
      
    @Transactional
    public void delete() {
        JPA.em().remove(this);
    }
    
    @Transactional
    public void merge() {
        JPA.em().merge(this);
    }
    
    @Transactional
    public void refresh() {
        JPA.em().refresh(this);
    }

   
	
	
}
