package com.travelportal.vm;

import java.util.List;

public class TransportationDirectionsSuppVM {
	
	
	public long supplierCode;
	public List<TransportationDirectionsVM> findLocation;
	
	
	
	public long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}
	public List<TransportationDirectionsVM> getFindLocation() {
		return findLocation;
	}
	public void setFindLocation(List<TransportationDirectionsVM> findLocation) {
		this.findLocation = findLocation;
	}

	
	
	
	
	
}
