package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="market_policy_types")
public class MarketPolicyTypes { //Seed table.
	@Column(name="market_policy_type_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int marketPolicyTypeId;
	@Column(name="market_policy_type_nm")
	private String marketPolicyTypeNm;
	@Column(name="display_order")
	private int displayOrder;
	
	/**
	 * @return the marketPolicyTypeId
	 */
	public int getMarketPolicyTypeId() {
		return marketPolicyTypeId;
	}
	/**
	 * @param marketPolicyTypeId the marketPolicyTypeId to set
	 */
	public void setMarketPolicyTypeId(int marketPolicyTypeId) {
		this.marketPolicyTypeId = marketPolicyTypeId;
	}
	/**
	 * @return the marketPolicyTypeNm
	 */
	public String getMarketPolicyTypeNm() {
		return marketPolicyTypeNm;
	}
	/**
	 * @param marketPolicyTypeNm the marketPolicyTypeNm to set
	 */
	public void setMarketPolicyTypeNm(String marketPolicyTypeNm) {
		this.marketPolicyTypeNm = marketPolicyTypeNm;
	}
	/**
	 * @return the displayOrder
	 */
	public int getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	
}
