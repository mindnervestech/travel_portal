package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hotel_chain")
public class HotelChain {
	@Column(name="id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="chain_hotel_code", unique=true)
	private int chainHotelCode;
	@Column(name="chain_hotel_nm")
	private String chainHotelName;
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
	 * @return the chainHotelCode
	 */
	public int getChainHotelCode() {
		return chainHotelCode;
	}
	/**
	 * @param chainHotelCode the chainHotelCode to set
	 */
	public void setChainHotelCode(int chainHotelCode) {
		this.chainHotelCode = chainHotelCode;
	}
	/**
	 * @return the chainHotelName
	 */
	public String getChainHotelName() {
		return chainHotelName;
	}
	/**
	 * @param chainHotelName the chainHotelName to set
	 */
	public void setChainHotelName(String chainHotelName) {
		this.chainHotelName = chainHotelName;
	}

}
