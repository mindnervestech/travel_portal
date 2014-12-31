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
@Table(name = "markets")
public class Markets {

	@Column(name = "market_code")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int marketCode;
	@Column(name = "market_name")
	private String marketName;

	
	

	public int getMarketCode() {
		return marketCode;
	}

	public void setMarketCode(int marketCode) {
		this.marketCode = marketCode;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public static List<Markets> getMarkets() {
		return JPA.em().createQuery("select c from Markets c").getResultList();
	}

	public static Markets getCountryByCode(int code) {
		return (Markets) JPA.em()
				.createQuery("select c from Markets c where marketCode = ?1")
				.setParameter(1, code).getSingleResult();
	}

	
	
}
