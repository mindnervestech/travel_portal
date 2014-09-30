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
@Table(name="currency")
public class Currency {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="currency_code", unique=true)
	private int currencyCode;
	@Column(name="currency_nm")
	private String currencyName;
	/**
	 * @return the currencyCode
	 */
	public int getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(int currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * @return the currencyName
	 */
	public String getCurrencyName() {
		return currencyName;
	}
	/**
	 * @param currencyName the currencyName to set
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public static List<Currency> getCurrency() {
		return JPA.em().createQuery("select c from Currency c ").getResultList();
	}
	
	
	
}
