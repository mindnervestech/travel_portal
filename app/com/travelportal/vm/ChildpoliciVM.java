package com.travelportal.vm;


public class ChildpoliciVM {
	private int childPolicyId;
	private int allowedChildAgeFrom;
	private int allowedChildAgeTo;
	private Long charge;
	private String chargeType;
	private int meal_plan_id;
	private Double childtaxvalue;
	private String childtaxtype;
	
	public int getMeal_plan_id() {
		return meal_plan_id;
	}

	public void setMeal_plan_id(int meal_plan_id) {
		this.meal_plan_id = meal_plan_id;
	}
	
	public int getChildPolicyId() {
		return childPolicyId;
	}

	public void setChildPolicyId(int childPolicyId) {
		this.childPolicyId = childPolicyId;
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
	public Double getChildtaxvalue() {
		return childtaxvalue;
	}

	public void setChildtaxvalue(Double childtaxvalue) {
		this.childtaxvalue = childtaxvalue;
	}

	public String getChildtaxtype() {
		return childtaxtype;
	}

	public void setChildtaxtype(String childtaxtype) {
		this.childtaxtype = childtaxtype;
	}
	
	
}
