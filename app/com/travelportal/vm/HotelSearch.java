package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

import com.travelportal.domain.HotelMealPlan;

public class HotelSearch {
	
	public Long supplierCode;
	public String hotelNm;
	public String supplierNm;
	public String hotelAddr;
	public String hoteldescription;
	public String hotel_email;
	public String imgPaths;
	public String imgDescription;
	public String checkIn;
	public String checkOut;
	public int nationality;
	public String nationalityName;
	public int countryCode;
	public String countryName;
	public int cityCode;
	public String cityName;
	public int startRating;
	public Double stars;
	public Double currencyExchangeRate;
	public String hotelBuiltYear;
	public String checkInTime;
	public String checkOutTime;
	public String roomVoltage;
	public int cancellation_date_diff;
	public String bookingId;
	public int currencyId;
	public String currencyName;
	public String currencyShort;
	public String agentCurrency;
	public Double minRate;
	/*public Set<HotelamenitiesVM> amenities;*/
	public List<Integer> services1;
	public List<ServicesVM> services;
	public HotelBookingDetailsVM hotelBookingDetails;
	public long datediff;
	public String breakfackRate;
	public String perferhotel;
	public BatchMarkupInfoVM batchMarkup;
	public List<SerachHotelRoomType> hotelbyRoom = new ArrayList<>();
	public List<SerachedHotelbyDate> hotelbyDate; 
	public List<HotelMealPlan> mealPlan;
	public SpecificMarkupInfoVM markup;
	public Double mealCompulsory;
	public Double availableLimit;
	public String paymentType;
	public String agentName;
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
	
	public Double getStars() {
		return stars;
	}
	public void setStars(Double stars) {
		this.stars = stars;
	}
	public String getPerferhotel() {
		return perferhotel;
	}
	public void setPerferhotel(String perferhotel) {
		this.perferhotel = perferhotel;
	}
	public SpecificMarkupInfoVM getMarkup() {
		return markup;
	}
	public void setMarkup(SpecificMarkupInfoVM markup) {
		this.markup = markup;
	}
	public BatchMarkupInfoVM getBatchMarkup() {
		return batchMarkup;
	}
	public void setBatchMarkup(BatchMarkupInfoVM batchMarkup) {
		this.batchMarkup = batchMarkup;
	}
	public List<HotelMealPlan> getMealPlan() {
		return mealPlan;
	}
	public void setMealPlan(List<HotelMealPlan> mealPlan) {
		this.mealPlan = mealPlan;
	}
	public String getNationalityName() {
		return nationalityName;
	}
	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getBreakfackRate() {
		return breakfackRate;
	}
	public void setBreakfackRate(String breakfackRate) {
		this.breakfackRate = breakfackRate;
	}
	public String getHotelBuiltYear() {
		return hotelBuiltYear;
	}
	public void setHotelBuiltYear(String hotelBuiltYear) {
		this.hotelBuiltYear = hotelBuiltYear;
	}
	public String getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}
	public String getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	public String getRoomVoltage() {
		return roomVoltage;
	}
	public void setRoomVoltage(String roomVoltage) {
		this.roomVoltage = roomVoltage;
	}
	public int getCancellation_date_diff() {
		return cancellation_date_diff;
	}
	public void setCancellation_date_diff(int cancellation_date_diff) {
		this.cancellation_date_diff = cancellation_date_diff;
	}
	public Double getCurrencyExchangeRate() {
		return currencyExchangeRate;
	}
	public void setCurrencyExchangeRate(Double currencyExchangeRate) {
		this.currencyExchangeRate = currencyExchangeRate;
	}
	public String getAgentCurrency() {
		return agentCurrency;
	}
	public void setAgentCurrency(String agentCurrency) {
		this.agentCurrency = agentCurrency;
	}
	public Double getMealCompulsory() {
		return mealCompulsory;
	}
	public void setMealCompulsory(Double mealCompulsory) {
		this.mealCompulsory = mealCompulsory;
	}
	public String getHotel_email() {
		return hotel_email;
	}
	public void setHotel_email(String hotel_email) {
		this.hotel_email = hotel_email;
	}
	public Double getAvailableLimit() {
		return availableLimit;
	}
	public void setAvailableLimit(Double availableLimit) {
		this.availableLimit = availableLimit;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	
	
	
}
