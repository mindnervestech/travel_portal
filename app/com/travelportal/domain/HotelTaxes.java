package com.travelportal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="supplier_hotel_taxes")
public class HotelTaxes {
	/*@JoinColumn(name="supplier_code")
	@OneToOne
	private SupplierCode supplier_code;*/
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String taxStartPeriod;
	private String taxEndPeriod;
	private String taxName;
	@ManyToOne
	private TaxType taxType;
	private Double taxAmount;
	private boolean surchargeFlag; //yes / no
	private Double addiChargeValuePerNight;
	private Double percentageCharge;
	@ManyToOne
	private Currency currency;
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
	 * @return the taxStartPeriod
	 */
	public String getTaxStartPeriod() {
		return taxStartPeriod;
	}
	/**
	 * @param taxStartPeriod the taxStartPeriod to set
	 */
	public void setTaxStartPeriod(String taxStartPeriod) {
		this.taxStartPeriod = taxStartPeriod;
	}
	/**
	 * @return the taxEndPeriod
	 */
	public String getTaxEndPeriod() {
		return taxEndPeriod;
	}
	/**
	 * @param taxEndPeriod the taxEndPeriod to set
	 */
	public void setTaxEndPeriod(String taxEndPeriod) {
		this.taxEndPeriod = taxEndPeriod;
	}
	/**
	 * @return the taxName
	 */
	public String getTaxName() {
		return taxName;
	}
	/**
	 * @param taxName the taxName to set
	 */
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	/**
	 * @return the taxType
	 */
	public TaxType getTaxType() {
		return taxType;
	}
	/**
	 * @param taxType the taxType to set
	 */
	public void setTaxType(TaxType taxType) {
		this.taxType = taxType;
	}
	/**
	 * @return the taxAmount
	 */
	public Double getTaxAmount() {
		return taxAmount;
	}
	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}
	
	/**
	 * @return the addiChargeValuePerNight
	 */
	public Double getAddiChargeValuePerNight() {
		return addiChargeValuePerNight;
	}
	/**
	 * @param addiChargeValuePerNight the addiChargeValuePerNight to set
	 */
	public void setAddiChargeValuePerNight(Double addiChargeValuePerNight) {
		this.addiChargeValuePerNight = addiChargeValuePerNight;
	}
	/**
	 * @return the percentageCharge
	 */
	public Double getPercentageCharge() {
		return percentageCharge;
	}
	/**
	 * @param percentageCharge the percentageCharge to set
	 */
	public void setPercentageCharge(Double percentageCharge) {
		this.percentageCharge = percentageCharge;
	}
	/**
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	/**
	 * @return the surchargeFlag
	 */
	public boolean isSurchargeFlag() {
		return surchargeFlag;
	}
	/**
	 * @param surchargeFlag the surchargeFlag to set
	 */
	public void setSurchargeFlag(boolean surchargeFlag) {
		this.surchargeFlag = surchargeFlag;
	}
	
	
}
