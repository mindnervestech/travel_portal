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
@Table(name="amenities_type")
public class AmenitiesType {
	
	@Column(name="amenities_type_code")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int amenitiesTypeCode;
	@Column(name="amenities_type_nm")
	private String amenitiesTypeNm;
	@Column(name="display_order")
	private int displayOrder;
	
	/**
	 * @return the amenitiesTypeCode
	 */
	public int getAmenitiesTypeCode() {
		return amenitiesTypeCode;
	}
	/**
	 * @param amenitiesTypeCode the amenitiesTypeCode to set
	 */
	public void setAmenitiesTypeCode(int amenitiesTypeCode) {
		this.amenitiesTypeCode = amenitiesTypeCode;
	}
	/**
	 * @return the amenitiesTypeNm
	 */
	public String getAmenitiesTypeNm() {
		return amenitiesTypeNm;
	}
	/**
	 * @param amenitiesTypeNm the amenitiesTypeNm to set
	 */
	public void setAmenitiesTypeNm(String amenitiesTypeNm) {
		this.amenitiesTypeNm = amenitiesTypeNm;
	}
	/**
	 * @return the displayOrder
	 */
	public int getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public static List<AmenitiesType> getamenities() {
		return JPA.em().createQuery("select c from AmenitiesType c").getResultList();
	}
}
