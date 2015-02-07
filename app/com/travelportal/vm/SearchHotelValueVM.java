package com.travelportal.vm;

import java.util.List;

import javax.persistence.Column;

public class SearchHotelValueVM {
	
	public String supplierCode;
	public String countryCode;
	public String city;
	public String checkIn;
	public String checkOut;
	public String id;
	public String nationalityCode;
	public String sortData;
	public List<Integer> servicesCheck;
	public List<Integer> locationCheck;
	public List<Integer> amenitiesCheck;
	public String roomId;
	public String total;
	public String adult;
	public String noOfroom;
	
	
	
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNationalityCode() {
		return nationalityCode;
	}
	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}
	public List<Integer> getServicesCheck() {
		return servicesCheck;
	}
	public void setServicesCheck(List<Integer> servicesCheck) {
		this.servicesCheck = servicesCheck;
	}
	public List<Integer> getLocationCheck() {
		return locationCheck;
	}
	public void setLocationCheck(List<Integer> locationCheck) {
		this.locationCheck = locationCheck;
	}
	public List<Integer> getAmenitiesCheck() {
		return amenitiesCheck;
	}
	public void setAmenitiesCheck(List<Integer> amenitiesCheck) {
		this.amenitiesCheck = amenitiesCheck;
	}
	public String getSortData() {
		return sortData;
	}
	public void setSortData(String sortData) {
		this.sortData = sortData;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getAdult() {
		return adult;
	}
	public void setAdult(String adult) {
		this.adult = adult;
	}
	public String getNoOfroom() {
		return noOfroom;
	}
	public void setNoOfroom(String noOfroom) {
		this.noOfroom = noOfroom;
	}
		
	
	
}
