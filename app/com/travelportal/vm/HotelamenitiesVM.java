package com.travelportal.vm;

import java.util.List;

public class HotelamenitiesVM {
	private Long supplierCode;
	
	private List<Integer> amenities;
	
	
	
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
	
	/**
	 * @return the services
	 */
	public List<Integer> getamenities() {
		return amenities;
	}
	/**
	 * @param services the services to set
	 */
	public void setServices(List<Integer> amenities) {
		this.amenities = amenities;
	}
	
	
}
