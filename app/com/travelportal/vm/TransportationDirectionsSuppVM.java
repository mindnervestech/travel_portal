package com.travelportal.vm;

import java.util.Date;
import java.util.List;

public class TransportationDirectionsSuppVM {
	
	
	private long supplierCode;
	private List<TransportationDirectionsVM> transportInfo;
	
	
	
	public long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}
	public List<TransportationDirectionsVM> getTransportInfo() {
		return transportInfo;
	}
	public void setTransportInfo(List<TransportationDirectionsVM> transportInfo) {
		this.transportInfo = transportInfo;
	}
	
	
	
	
}
