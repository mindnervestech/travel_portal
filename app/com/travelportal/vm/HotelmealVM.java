package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class HotelmealVM {
	
	
	private int mealType;
	private String mealPlanNm;
	private Date fromPeriod;
	private Date toPeriod;
	private Double rate;
	private long supplierCode;
	private Double taxvalue;
	private String taxtype;
	private boolean taxIncluded;
	private List<ChildpoliciVM> child;
	
	
	
	
	/*@Column(name="guest_type")
	private String guestType; //adult or child...
*/
	 //flag to show whether the taz is included or not in rate given.
	/*@Column(name="age_criteria")
	private String ageCriteria;*/
	
/*	
	private int allowedChildAgeFrom;
	private int allowedChildAgeTo;
	private Long charge;
	private String chargeType;
	*/
	
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
	
	public boolean gettaxIncluded() {
		return taxIncluded;
	}
	/**
	 * @param fromPeriod the fromPeriod to set
	 */
	public void settaxIncluded(boolean taxIncluded) {
		this.taxIncluded = taxIncluded;
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
	
	public long getsupplierCode() {
		return supplierCode;
	}
	
	public void setsupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public Double getTaxvalue() {
		return taxvalue;
	}
	public void setTaxvalue(Double taxvalue) {
		this.taxvalue = taxvalue;
	}
	public String getTaxtype() {
		return taxtype;
	}
	public void setTaxtype(String taxtype) {
		this.taxtype = taxtype;
	}
	public List<ChildpoliciVM> getchild() {
		return child;
	}
	
	public void setchild(List<ChildpoliciVM> child) {
		this.child = child;
	}
	/**
	 * @return the guestType
	 */
	/*public String getGuestType() {
		return guestType;
	}
	*//**
	 * @param guestType the guestType to set
	 *//*
	public void setGuestType(String guestType) {
		this.guestType = guestType;
	}*/
	/**
	 * @return the ageCriteria
	 */
	/*public String getAgeCriteria() {
		return ageCriteria;
	}
	*//**
	 * @param ageCriteria the ageCriteria to set
	 *//*
	public void setAgeCriteria(String ageCriteria) {
		this.ageCriteria = ageCriteria;
	}*/
	/**
	 * @return the mealType
	 */
	public int getMealType() {
		return mealType;
	}
	/**
	 * @param mealType the mealType to set
	 */
	public void setMealType(int mealType) {
		this.mealType = mealType;
	}
	
	/*public int getAllowedChildAgeFrom() {
		return allowedChildAgeFrom;
	}
	
	public void setAllowedChildAgeFrom(int allowedChildAgeFrom) {
		this.allowedChildAgeFrom = allowedChildAgeFrom;
	}
	
	public int getAllowedChildAgeTo() {
		return allowedChildAgeTo;
	}
	
	public void setAllowedChildAgeTo(int allowedChildAgeTo) {
		this.allowedChildAgeTo = allowedChildAgeTo;
	}
	
	public Long getCharge() {
		return charge;
	}
	
	public void setCharge(Long charge) {
		this.charge = charge;
	}
	
	public String getChargeType() {
		return chargeType;
	}
	
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}*/
	
	/*public int getChildPolicyId() {
		return childPolicyId;
	}
	
	public void setChildPolicyId(int childPolicyId) {
		this.childPolicyId = childPolicyId;
	}*/
	
	
	
}
