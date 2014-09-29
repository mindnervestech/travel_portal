package com.travelportal.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;

@Entity
@Table(name="city")
public class City {
	@Column(name="city_nm")
	private String cityName;
	@Column(name="city_code", unique=true)
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int cityCode;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Country country;
	/**
	 * @return the cityCode
	 */
	public int getCityCode() {
		return cityCode;
	}
	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public static List<City> getCities(final int countryCode) {
		Query q = JPA.em().createQuery("select c from City c where c.country.countryCode = :cCode");
		q.setParameter("cCode", countryCode);
		return q.getResultList();
	}
}
