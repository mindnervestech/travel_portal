package com.travelportal.domain.rooms;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.travelportal.domain.HotelAmenities;

import play.db.jpa.JPA;

@Entity
@Table(name="room_amenities")
public class RoomAmenities {
	
	@Column(name="amenity_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int amenityId;
	
	@Column(name="amenity_nm")
	private String amenityNm;
	
	@Column(name="amenities_icon")
	private String amenitiesicon;
	
	/**
	 * @return the amenityId
	 */
	public int getAmenityId() {
		return amenityId;
	}
	/**
	 * @param amenityId the amenityId to set
	 */
	public void setAmenityId(int amenityId) {
		this.amenityId = amenityId;
	}
	/**
	 * @return the amenityNm
	 */
	public String getAmenityNm() {
		return amenityNm;
	}
	/**
	 * @param amenityNm the amenityNm to set
	 */
	
	
	
	
	public void setAmenityNm(String amenityNm) {
		this.amenityNm = amenityNm;
	}
	
	public String getAmenitiesicon() {
		return amenitiesicon;
	}
	public void setAmenitiesicon(String amenitiesicon) {
		this.amenitiesicon = amenitiesicon;
	}
	public static List<RoomAmenities> getRoomAmenities() {
		return JPA.em().createQuery("select ra from RoomAmenities ra").getResultList();
	}
	
	public static List<RoomAmenities> getroomamenities(List<Integer> list) {
		return JPA.em().createQuery("select c from RoomAmenities c where amenityId IN ?1").setParameter(1, list).getResultList();
	}
}
