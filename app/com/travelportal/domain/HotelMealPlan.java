package com.travelportal.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="hotel_meal_plan")
public class HotelMealPlan { //supplier specific records...
	@JoinColumn(name="supplier_code")
	@OneToOne
	private SupplierCode supplier_code;
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne
	private MealType mealType;
	@Column(name="meal_plan_nm")
	private String mealPlanNm;
	@Column(name="from_period")
	private Date fromPeriod;
	@Column(name="to_period")
	private Date toPeriod;
	@Column(name="rate")
	private Double rate;
	@Column(name="guest_type")
	private String guestType; //adult or child...
	@Column(name="tax_included")
	private boolean taxIncluded; //flag to show whether the taz is included or not in rate given.
	@Column(name="age_criteria")
	private String ageCriteria;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the mealPlanNm
	 */
	public String getMealPlanNm() {
		return mealPlanNm;
	}
	/**
	 * @param mealPlanNm the mealPlanNm to set
	 */
	public void setMealPlanNm(String mealPlanNm) {
		this.mealPlanNm = mealPlanNm;
	}
	/**
	 * @return the fromPeriod
	 */
	public Date getFromPeriod() {
		return fromPeriod;
	}
	/**
	 * @param fromPeriod the fromPeriod to set
	 */
	public void setFromPeriod(Date fromPeriod) {
		this.fromPeriod = fromPeriod;
	}
	/**
	 * @return the toPeriod
	 */
	public Date getToPeriod() {
		return toPeriod;
	}
	/**
	 * @param toPeriod the toPeriod to set
	 */
	public void setToPeriod(Date toPeriod) {
		this.toPeriod = toPeriod;
	}
	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}
	/**
	 * @return the guestType
	 */
	public String getGuestType() {
		return guestType;
	}
	/**
	 * @param guestType the guestType to set
	 */
	public void setGuestType(String guestType) {
		this.guestType = guestType;
	}
	/**
	 * @return the ageCriteria
	 */
	public String getAgeCriteria() {
		return ageCriteria;
	}
	/**
	 * @param ageCriteria the ageCriteria to set
	 */
	public void setAgeCriteria(String ageCriteria) {
		this.ageCriteria = ageCriteria;
	}
	/**
	 * @return the mealType
	 */
	public MealType getMealType() {
		return mealType;
	}
	/**
	 * @param mealType the mealType to set
	 */
	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}
	
}
