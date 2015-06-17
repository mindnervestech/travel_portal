package com.travelportal.vm;

import java.util.List;

import javax.persistence.Column;

public class SearchHotelValueVM {
	
	public String supplierCode;
	public String countryCode;
	public String cityCode;
	public String checkIn;
	public String checkOut;
	public String id;
	public String nationalityCode;
	public String sortData;
	public String noSort;
	public String sortByRating;
	public List<Integer> servicesCheck;
	public List<Integer> locationCheck;
	public List<Integer> amenitiesCheck;
	public List<Integer> starCheck;
	public String roomId;
	public String total;
	public String adult;
	public String noOfroom;
	public String noOfchild;
	public String totalParPerson;
	public String loginID;
	public String password;
	public String agentId;
	public String hotelname;
	
	
	
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
	
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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
	
	public List<Integer> getStarCheck() {
		return starCheck;
	}
	public void setStarCheck(List<Integer> starCheck) {
		this.starCheck = starCheck;
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
	public String getLoginID() {
		return loginID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getSortByRating() {
		return sortByRating;
	}
	public void setSortByRating(String sortByRating) {
		this.sortByRating = sortByRating;
	}
	public String getNoSort() {
		return noSort;
	}
	public void setNoSort(String noSort) {
		this.noSort = noSort;
	}
	public String getHotelname() {
		return hotelname;
	}
	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}
	public String getTotalParPerson() {
		return totalParPerson;
	}
	public void setTotalParPerson(String totalParPerson) {
		this.totalParPerson = totalParPerson;
	}
	public String getNoOfchild() {
		return noOfchild;
	}
	public void setNoOfchild(String noOfchild) {
		this.noOfchild = noOfchild;
	}
		
	
	
}
