package com.travelportal.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.JPA;

@Entity
@Table(name="hotel_brands")
public class HotelBrands {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="brands_code")
	private int brandsCode;
	@Column(name="brands_nm")
	private String brandName;
	/**
	 * @return the brandsCode
	 */
	public int getBrandsCode() {
		return brandsCode;
	}
	/**
	 * @param brandsCode the brandsCode to set
	 */
	public void setBrandsCode(int brandsCode) {
		this.brandsCode = brandsCode;
	}
	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	
	public static List<HotelBrands> gethotelbrand() {
		return JPA.em().createQuery("select c from HotelBrands c ").getResultList();
	}
	
}
