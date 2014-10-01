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
@Table(name="country")
public class Country {
	
	@Column(name="country_code")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int countryCode;
	@Column(name="country_nm")
	private String countryName;
	/**
	 * @return the countryCode
	 */
	public int getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}
	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	} 

	public static List<Country> getCountries() {
		return JPA.em().createQuery("select c from Country c").getResultList();
	}
	
	public static Country getCountryByCode(int code) {
		return (Country) JPA.em().createQuery("select c from Country c where countryCode = ?1").setParameter(1, code).getSingleResult();
	}
}
