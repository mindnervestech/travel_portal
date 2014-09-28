package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
}
