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
@Table(name="meal_type")
public class MealType {
	@Column(name="meal_type_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int mealTypeId;
	@Column(name="meal_type_nm")
	private String mealTypeNm; // lunch..dinner..
	
	/**
	 * @return the mealTypeId
	 */
	public int getMealTypeId() {
		return mealTypeId;
	}
	/**
	 * @param mealTypeId the mealTypeId to set
	 */
	public void setMealTypeId(int mealTypeId) {
		this.mealTypeId = mealTypeId;
	}
	/**
	 * @return the mealTypeNm
	 */
	public String getMealTypeNm() {
		return mealTypeNm;
	}
	/**
	 * @param mealTypeNm the mealTypeNm to set
	 */
	public void setMealTypeNm(String mealTypeNm) {
		this.mealTypeNm = mealTypeNm;
	}
	
	public static List<MealType> getmealtypes() {
		return JPA.em().createQuery("select c from MealType c").getResultList();
	}
	
	public static MealType getMealTypeIdByCode(int code) {
		return (MealType) JPA.em().createQuery("select c from MealType c where mealTypeId = ?1").setParameter(1, code).getSingleResult();
	}
	
}
