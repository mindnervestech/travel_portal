package com.travelportal.vm;

import java.util.List;

public class ChildpoliciVM {
	private int allowedChildAgeFrom;
	private int allowedChildAgeTo;
	private Long charge;
	private String chargeType;
	private int meal_plan_id;
	
	public int getMeal_plan_id() {
		return meal_plan_id;
	}

	public void setMeal_plan_id(int meal_plan_id) {
		this.meal_plan_id = meal_plan_id;
	}

	public int getAllowedChildAgeFrom() {
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
	}
	
	
	
}
