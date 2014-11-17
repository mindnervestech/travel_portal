package com.travelportal.vm;

import java.util.List;

public class HotelamenitiesVM {
	public Long supplierCode;
	
	public List<Integer> amenities;
	
	
	
	/**
	 * @return the supplierCode
	 */
	public Long getSupplierCode() {
		return supplierCode;
	}
	/**
	 * @param supplierCode the supplierCode to set
	 */
	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
	}
	public List<Integer> getAmenities() {
		return amenities;
	}
	public void setAmenities(List<Integer> amenities) {
		this.amenities = amenities;
	}
	
	
	
	
	
}
