package com.travelportal.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.JPA;

@Entity
@Table(name="location")
public class Location {
	@Column(name="location_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int locationId;
	@Column(name="location_nm")
	private String locationNm;
	@ManyToOne
	private City city;
	/**
	 * @return the locationId
	 */
	public int getLocationId() {
		return locationId;
	}
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	/**
	 * @return the locationNm
	 */
	public String getLocationNm() {
		return locationNm;
	}
	/**
	 * @param locationNm the locationNm to set
	 */
	public void setLocationNm(String locationNm) {
		this.locationNm = locationNm;
	}
	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(City city) {
		this.city = city;
	}
	
	public static List<Location> getLocation(int cityId) {
		return JPA.em().createQuery("select c from Location c where c.city.cityCode = ?1").setParameter(1, cityId).getResultList();
	}
	
	public static Location getlocationIdByCode(int code) {
		return (Location) JPA.em().createQuery("select c from Location c where locationId = ?1").setParameter(1, code).getSingleResult();
	}
	
}
