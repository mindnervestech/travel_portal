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
	
	
	public static List<NightLife> getNightLife() {
		return JPA.em().createQuery("select c from NightLife c ").getResultList();
	}
	
	public static NightLife getNightLifeByCode(int code) {
		return (NightLife) JPA.em().createQuery("select c from NightLife c where nightLifeCode = ?1").setParameter(1, code).getSingleResult();
	}
	
}
