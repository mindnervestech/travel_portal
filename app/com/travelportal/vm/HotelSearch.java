package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.rooms.RateMeta;

public class HotelSearch {
	
	public Long supplierCode;
	public String hotelNm;
	public String supplierNm;
	public String hotelAddr;
	public String imgPaths;
	public String imgDescription;
	public String checkIn;
	public String checkOut;
	public int nationality;
	public int countryCode;
	public int cityCode;
	public int startRating;
	public List<Integer> services;
	public List<SerachHotelRoomType> hotelbyRoom = new ArrayList<>();
	public List<SerachedHotelbyDate> hotelbyDate; 
	public String flag;
	
	public Long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getHotelNm() {
		return hotelNm;
	}
	public void setHotelNm(String hotelNm) {
		this.hotelNm = hotelNm;
	}
	public String getSupplierNm() {
		return supplierNm;
	}
	public void setSupplierNm(String supplierNm) {
		this.supplierNm = supplierNm;
	}
	public String getHotelAddr() {
		return hotelAddr;
	}
	public void setHotelAddr(String hotelAddr) {
		this.hotelAddr = hotelAddr;
	}
	public int getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	public int getStartRating() {
		return startRating;
	}
	public void setStartRating(int startRating) {
		this.startRating = startRating;
	}
	
	public List<SerachedHotelbyDate> getHotelbyDate() {
		return hotelbyDate;
	}
	public void setHotelbyDate(List<SerachedHotelbyDate> hotelbyDate) {
		this.hotelbyDate = hotelbyDate;
	}
	public List<SerachHotelRoomType> getHotelbyRoom() {
		return hotelbyRoom;
	}
	public void setHotelbyRoom(List<SerachHotelRoomType> hotelbyRoom) {
		this.hotelbyRoom = hotelbyRoom;
	}
	public String getImgPaths() {
		return imgPaths;
	}
	public void setImgPaths(String imgPaths) {
		this.imgPaths = imgPaths;
	}
	public String getImgDescription() {
		return imgDescription;
	}
	public void setImgDescription(String imgDescription) {
		this.imgDescription = imgDescription;
	}
	public List<Integer> getServices() {
		return services;
	}
	public void setServices(List<Integer> services) {
		this.services = services;
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
	public int getNationality() {
		return nationality;
	}
	public void setNationality(int nationality) {
		this.nationality = nationality;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
	
}
