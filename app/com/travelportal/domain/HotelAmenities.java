package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="hotel_amenities")
public class HotelAmenities {
	@Column(name="amenities_code")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int amenitiesCode;
	@Column(name="amenities_nm")
	private String amenitiesNm;
	@ManyToOne
	private AmenitiesType amenitiesType;
	/**
	 * @return the amenitiesCode
	 */
	public int getAmenitiesCode() {
		return amenitiesCode;
	}
	/**
	 * @param amenitiesCode the amenitiesCode to set
	 */
	public void setAmenitiesCode(int amenitiesCode) {
		this.amenitiesCode = amenitiesCode;
	}
	/**
	 * @return the amenitiesNm
	 */
	public String getAmenitiesNm() {
		return amenitiesNm;
	}
	/**
	 * @param amenitiesNm the amenitiesNm to set
	 */
	public void setAmenitiesNm(String amenitiesNm) {
		this.amenitiesNm = amenitiesNm;
	}
	/**
	 * @return the amenitiesType
	 */
	public AmenitiesType getAmenitiesType() {
		return amenitiesType;
	}
	/**
	 * @param amenitiesType the amenitiesType to set
	 */
	public void setAmenitiesType(AmenitiesType amenitiesType) {
		this.amenitiesType = amenitiesType;
	}
	
	
}
