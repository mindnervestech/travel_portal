package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hotel_attractions")
public class HotelAttractions {
	@Column(name="attraction_code")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int attractionCode;
	@Column(name="attraction_nm")
	private String attractionNm;
	@Column(name="distance")
	private double distance;
	@Column(name="distance_type")
	private String distanceType; //either km / miles etc..
	@Column(name="time_require_in_minutes")
	private int timeRequireInMinutes;  //time require to reach the destination from hotel in minutes
	@Column(name="display_order")
	private int displayOrder;
	
	
	/**
	 * @return the attractionCode
	 */
	public int getAttractionCode() {
		return attractionCode;
	}
	/**
	 * @param attractionCode the attractionCode to set
	 */
	public void setAttractionCode(int attractionCode) {
		this.attractionCode = attractionCode;
	}
	/**
	 * @return the attractionNm
	 */
	public String getAttractionNm() {
		return attractionNm;
	}
	/**
	 * @param attractionNm the attractionNm to set
	 */
	public void setAttractionNm(String attractionNm) {
		this.attractionNm = attractionNm;
	}
	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	/**
	 * @return the timeRequireInMinutes
	 */
	public int getTimeRequireInMinutes() {
		return timeRequireInMinutes;
	}
	/**
	 * @param timeRequireInMinutes the timeRequireInMinutes to set
	 */
	public void setTimeRequireInMinutes(int timeRequireInMinutes) {
		this.timeRequireInMinutes = timeRequireInMinutes;
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
	/**
	 * @return the distanceType
	 */
	public String getDistanceType() {
		return distanceType;
	}
	/**
	 * @param distanceType the distanceType to set
	 */
	public void setDistanceType(String distanceType) {
		this.distanceType = distanceType;
	}
	
}
