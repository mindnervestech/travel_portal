package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

public class HotelSearch {
	
	public Long supplierCode;
	public String hotelNm;
	public String supplierNm;
	public String hotelAddr;
	public String hoteldescription;
	public String imgPaths;
	public String imgDescription;
	public String checkIn;
	public String checkOut;
	public int nationality;
	public int countryCode;
	public int cityCode;
	public int startRating;
	public int stars;
	public int currencyId;
	public String currencyName;
	public String currencyShort;
	public Double minRate;
	/*public Set<HotelamenitiesVM> amenities;*/
	public List<Integer> services1;
	public List<ServicesVM> services;
	public HotelBookingDetailsVM hotelBookingDetails;
	public long datediff;
	
	public List<SerachHotelRoomType> hotelbyRoom = new ArrayList<>();
	public List<SerachedHotelbyDate> hotelbyDate; 
	//public String flag;
	
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
	/*public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}*/
	public List<Integer> getServices1() {
		return services1;
	}
	public void setServices1(List<Integer> services1) {
		this.services1 = services1;
	}
	public List<ServicesVM> getServices() {
		return services;
	}
	public void setServices(List<ServicesVM> services) {
		this.services = services;
	}
	public String getHoteldescription() {
		return hoteldescription;
	}
	public void setHoteldescription(String hoteldescription) {
		this.hoteldescription = hoteldescription;
	}
	/*public Set<HotelamenitiesVM> getAmenities() {
		return amenities;
	}
	public void setAmenities(Set<HotelamenitiesVM> amenities) {
		this.amenities = amenities;
	}*/
	public HotelBookingDetailsVM getHotelBookingDetails() {
		return hotelBookingDetails;
	}
	public void setHotelBookingDetails(HotelBookingDetailsVM hotelBookingDetails) {
		this.hotelBookingDetails = hotelBookingDetails;
	}

	public long getDatediff() {
		return datediff;
	}
	public void setDatediff(long datediff) {
		this.datediff = datediff;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	
	
}
