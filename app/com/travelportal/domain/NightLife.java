package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="night_life")
public class NightLife {
	@Column(name="night_life_code")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int nightLifeCode;
	@Column(name="night_life_nm")
	private String nightLifeNm;
	/**
	 * @return the nightLifeCode
	 */
	public int getNightLifeCode() {
		return nightLifeCode;
	}
	/**
	 * @param nightLifeCode the nightLifeCode to set
	 */
	public void setNightLifeCode(int nightLifeCode) {
		this.nightLifeCode = nightLifeCode;
	}
	/**
	 * @return the nightLifeNm
	 */
	public String getNightLifeNm() {
		return nightLifeNm;
	}
	/**
	 * @param nightLifeNm the nightLifeNm to set
	 */
	public void setNightLifeNm(String nightLifeNm) {
		this.nightLifeNm = nightLifeNm;
	}
	
	
}
