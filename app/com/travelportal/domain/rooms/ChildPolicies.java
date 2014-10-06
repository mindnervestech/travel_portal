package com.travelportal.domain.rooms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="child_policies")
public class ChildPolicies {
	
	@Column(name="child_policy_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int childPolicyId;
	@Column(name="child_age_allowed_from")
	private int allowedChildAgeFrom;
	@Column(name="child_age_allowed_to")
	private int allowedChildAgeTo;
	@Column(name="charge")
	private Long charge; //if chargeType is % then consider this as % of net else fixed amount set to net on per day basis.
	@Column(name="charge_type")
	private String chargeType;  //in % or fixed charge.
	
	/**
	 * @return the allowedChildAgeFrom
	 */
	public int getAllowedChildAgeFrom() {
		return allowedChildAgeFrom;
	}
	/**
	 * @param allowedChildAgeFrom the allowedChildAgeFrom to set
	 */
	public void setAllowedChildAgeFrom(int allowedChildAgeFrom) {
		this.allowedChildAgeFrom = allowedChildAgeFrom;
	}
	/**
	 * @return the allowedChildAgeTo
	 */
	public int getAllowedChildAgeTo() {
		return allowedChildAgeTo;
	}
	/**
	 * @param allowedChildAgeTo the allowedChildAgeTo to set
	 */
	public void setAllowedChildAgeTo(int allowedChildAgeTo) {
		this.allowedChildAgeTo = allowedChildAgeTo;
	}
	/**
	 * @return the charge
	 */
	public Long getCharge() {
		return charge;
	}
	/**
	 * @param charge the charge to set
	 */
	public void setCharge(Long charge) {
		this.charge = charge;
	}
	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}
	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	/**
	 * @return the childPolicyId
	 */
	public int getChildPolicyId() {
		return childPolicyId;
	}
	/**
	 * @param childPolicyId the childPolicyId to set
	 */
	public void setChildPolicyId(int childPolicyId) {
		this.childPolicyId = childPolicyId;
	}
	
}
