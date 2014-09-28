package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tax_type")
public class TaxType {
	@Column(name="tax_type_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int taxTypeId;
	@Column(name="tax_type_nm")
	private String taxTypeName; //% or fixed etc..
	
	/**
	 * @return the taxTypeId
	 */
	public int getTaxTypeId() {
		return taxTypeId;
	}
	/**
	 * @param taxTypeId the taxTypeId to set
	 */
	public void setTaxTypeId(int taxTypeId) {
		this.taxTypeId = taxTypeId;
	}
	/**
	 * @return the taxTypeName
	 */
	public String getTaxTypeName() {
		return taxTypeName;
	}
	/**
	 * @param taxTypeName the taxTypeName to set
	 */
	public void setTaxTypeName(String taxTypeName) {
		this.taxTypeName = taxTypeName;
	}
	
}
