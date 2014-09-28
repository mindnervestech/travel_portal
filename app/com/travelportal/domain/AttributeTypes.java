package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="attribute_types")
public class AttributeTypes {
	@Column(name="attribute_type_code")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int attributeTypeCode;
	@Column(name="attribute_type_nm")
	private String attributeTypeNm;
	@Column(name="display_order")
	private int displayOrder; //this is require to indicate in which order data will be rendered on screen.
	
	/**
	 * @return the attributeTypeCode
	 */
	public int getAttributeTypeCode() {
		return attributeTypeCode;
	}
	/**
	 * @param attributeTypeCode the attributeTypeCode to set
	 */
	public void setAttributeTypeCode(int attributeTypeCode) {
		this.attributeTypeCode = attributeTypeCode;
	}
	/**
	 * @return the attributeTypeNm
	 */
	public String getAttributeTypeNm() {
		return attributeTypeNm;
	}
	/**
	 * @param attributeTypeNm the attributeTypeNm to set
	 */
	public void setAttributeTypeNm(String attributeTypeNm) {
		this.attributeTypeNm = attributeTypeNm;
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
