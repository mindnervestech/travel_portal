package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class TransportationDirectionsVM {
	
		
	private int transportCode;
	private String locationName;
	private String locationAddr;
	public int getTransportCode() {
		return transportCode;
	}
	public void setTransportCode(int transportCode) {
		this.transportCode = transportCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLocationAddr() {
		return locationAddr;
	}
	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}
	
	
	
	
	
	
}
