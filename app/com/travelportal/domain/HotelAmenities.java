package com.travelportal.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.JPA;


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
	
	/*public static Location getlocationIdByCode(int code) {
		return (Location) JPA.em().createQuery("select c from Location c where locationId = ?1").setParameter(1, code).getSingleResult();
	}*/
	
	public static List<HotelAmenities> getamenities(AmenitiesType amenitiescode) {
		
		return JPA.em().createQuery("select c from HotelAmenities c where amenitiesType = ?1"). setParameter(1,amenitiescode).getResultList();
	}
	
	public static Set<HotelAmenities> getallhotelamenities(List<Integer> list) {
		List<HotelAmenities> hotelcontactinformation =  JPA.em().createQuery("select c from HotelAmenities c where amenitiesCode IN ?1").setParameter(1, list).getResultList();
		return new HashSet<>(hotelcontactinformation);
	}
	
}
