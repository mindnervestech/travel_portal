package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="connection_type")
public class ConnectionType {
	@Column(name="connection_type_code")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int connectionTypeCode;
	@Column(name="connection_type_nm")
	private String connectionTypeNm; //either email, tel, fax etc..
	@Column(name="display_order")
	private int displayOrder;
	
	/**
	 * @return the connectionTypeNm
	 */
	public String getConnectionTypeNm() {
		return connectionTypeNm;
	}
	/**
	 * @param connectionTypeNm the connectionTypeNm to set
	 */
	public void setConnectionTypeNm(String connectionTypeNm) {
		this.connectionTypeNm = connectionTypeNm;
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
	/**
	 * @return the connectionTypeCode
	 */
	public int getConnectionTypeCode() {
		return connectionTypeCode;
	}
	/**
	 * @param connectionTypeCode the connectionTypeCode to set
	 */
	public void setConnectionTypeCode(int connectionTypeCode) {
		this.connectionTypeCode = connectionTypeCode;
	}
	
	
}
