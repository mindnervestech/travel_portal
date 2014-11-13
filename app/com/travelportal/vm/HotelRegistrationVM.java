package com.travelportal.vm;

import com.travelportal.domain.HotelRegistration;

public class HotelRegistrationVM {

	public long id;
	public String supplierName;
	public String hotelName;
	public String policy;
	public String starRating;
	
	public HotelRegistrationVM(HotelRegistration reg) {
		this.id = reg.getId();
		this.supplierName = reg.getSupplierName();
		this.hotelName = reg.getHotelName();
		this.policy = reg.getPolicy();
		this.starRating = reg.getStarRating();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public String getStarRating() {
		return starRating;
	}
	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}
	
}
