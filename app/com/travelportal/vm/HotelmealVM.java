package com.travelportal.vm;

import java.util.Date;
import java.util.List;

public class HotelmealVM {
	
	public int id;
	public int mealType;
	public String mealPlanNm;
	public String fromPeriod;
	public String toPeriod;
	public String guestType;
	public Double rate;
	public long supplierCode;
	public Double taxvalue;
	public String taxtype;
	public String taxIncluded;
	public String ageCriteria;
	public List<ChildpoliciVM> child;
	
	
	
	
	
	public int getId() {
		return id;
	}
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
	
	
	
	
	
	public String getGuestType() {
		return guestType;
	}
	public String getFromPeriod() {
		return fromPeriod;
	}
	public void setFromPeriod(String fromPeriod) {
		this.fromPeriod = fromPeriod;
	}
	public void setToPeriod(String toPeriod) {
		this.toPeriod = toPeriod;
	}
	public String getToPeriod() {
		return toPeriod;
	}
	public void setGuestType(String guestType) {
		this.guestType = guestType;
	}
	/**
	 * @return the toPeriod
	 */
	
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
	
	
	
	public long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(long supplierCode) {
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
	
	public String getAgeCriteria() {
		return ageCriteria;
	}
	public void setAgeCriteria(String ageCriteria) {
		this.ageCriteria = ageCriteria;
	}
	
	
	
	public String getTaxIncluded() {
		return taxIncluded;
	}
	public void setTaxIncluded(String taxIncluded) {
		this.taxIncluded = taxIncluded;
	}
	public List<ChildpoliciVM> getChild() {
		return child;
	}
	public void setChild(List<ChildpoliciVM> child) {
		this.child = child;
	}
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
	
	
	
}
