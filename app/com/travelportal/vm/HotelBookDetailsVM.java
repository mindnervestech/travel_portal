package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelStarRatings;

public class HotelBookDetailsVM {
	
	public long id;
	public long supplierCode;
	public String hotelNm;
	public String supplierNm;
	public String hotelAddr;
	public String checkIn;
	public String checkOut;
	public int nationality;
	public String nationalityNm;
	public int countryId;
	public String countryNm;
	public int cityCode;
	public String  cityNm;
	public int startRating;
	public String startRatingNm;
	public int currencyId;
	public String currencyNm;
	public Double currencyExchangeRate;
	public String adult;
	public int noOfroom;
	public double total;
	public String travellerfirstname;
	public String travellerlastname;
	public String travelleraddress;
	public String travelleremail;
	public int travellercountry;
	public String travellercountryNm;
	public String travellerphnaumber;
	public long roomId;
	public String roomNm;
	public String promotionname;
	public int stayDays_inpromotion;
	public int payDays_inpromotion;
	public String typeOfStay_inpromotion;
	public String curr; 
	public String room_status;
	public Long totalNightStay;
	public List<AgentRegisVM> agent;
	public String nonRefund;
	public String payment;
	public List<PassengerBookingInfoVM> passengerInfo;
	
	
	public String nonSmokingRoom;
	public String twinBeds;
	public String lateCheckout;
	public String largeBed;
	public String highFloor;
	public String earlyCheckin;
	public String airportTransfer;
	public String airportTransferInfo;
	public String enterComments;
	public String smokingRoom;
	public String wheelchair;
	public String handicappedRoom;
	
	
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
	
		
	public String getAdult() {
		return adult;
	}
	public void setAdult(String adult) {
		this.adult = adult;
	}
	
	public int getNoOfroom() {
		return noOfroom;
	}
	public void setNoOfroom(int noOfroom) {
		this.noOfroom = noOfroom;
	}
	
	public String getTravellerfirstname() {
		return travellerfirstname;
	}
	public void setTravellerfirstname(String travellerfirstname) {
		this.travellerfirstname = travellerfirstname;
	}
	public String getTravellerlastname() {
		return travellerlastname;
	}
	public void setTravellerlastname(String travellerlastname) {
		this.travellerlastname = travellerlastname;
	}
	public String getTravelleraddress() {
		return travelleraddress;
	}
	public void setTravelleraddress(String travelleraddress) {
		this.travelleraddress = travelleraddress;
	}
	public String getTravelleremail() {
		return travelleremail;
	}
	public void setTravelleremail(String travelleremail) {
		this.travelleremail = travelleremail;
	}
	
	public String getTravellerphnaumber() {
		return travellerphnaumber;
	}
	public void setTravellerphnaumber(String travellerphnaumber) {
		this.travellerphnaumber = travellerphnaumber;
	}
	
	public String getPromotionname() {
		return promotionname;
	}
	public void setPromotionname(String promotionname) {
		this.promotionname = promotionname;
	}
	
	public int getStartRating() {
		return startRating;
	}
	public void setStartRating(int startRating) {
		this.startRating = startRating;
	}
	public int getStayDays_inpromotion() {
		return stayDays_inpromotion;
	}
	public void setStayDays_inpromotion(int stayDays_inpromotion) {
		this.stayDays_inpromotion = stayDays_inpromotion;
	}
	public int getPayDays_inpromotion() {
		return payDays_inpromotion;
	}
	public void setPayDays_inpromotion(int payDays_inpromotion) {
		this.payDays_inpromotion = payDays_inpromotion;
	}
	public String getTypeOfStay_inpromotion() {
		return typeOfStay_inpromotion;
	}
	public void setTypeOfStay_inpromotion(String typeOfStay_inpromotion) {
		this.typeOfStay_inpromotion = typeOfStay_inpromotion;
	}
	public String getRoom_status() {
		return room_status;
	}
	public void setRoom_status(String room_status) {
		this.room_status = room_status;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	public int getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}
	public int getNationality() {
		return nationality;
	}
	public void setNationality(int nationality) {
		this.nationality = nationality;
	}
	public long getRoomId() {
		return roomId;
	}
	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getTravellercountry() {
		return travellercountry;
	}
	public void setTravellercountry(int travellercountry) {
		this.travellercountry = travellercountry;
	}
	public String getNationalityNm() {
		return nationalityNm;
	}
	public void setNationalityNm(String nationalityNm) {
		this.nationalityNm = nationalityNm;
	}
	public String getCountryNm() {
		return countryNm;
	}
	public void setCountryNm(String countryNm) {
		this.countryNm = countryNm;
	}
	public String getCityNm() {
		return cityNm;
	}
	public void setCityNm(String cityNm) {
		this.cityNm = cityNm;
	}
	public String getStartRatingNm() {
		return startRatingNm;
	}
	public void setStartRatingNm(String startRatingNm) {
		this.startRatingNm = startRatingNm;
	}
	public String getCurrencyNm() {
		return currencyNm;
	}
	public void setCurrencyNm(String currencyNm) {
		this.currencyNm = currencyNm;
	 String currency[]=currencyNm.split("-");
	 curr=currency[0];
	}
	public String getTravellercountryNm() {
		return travellercountryNm;
	}
	public void setTravellercountryNm(String travellercountryNm) {
		this.travellercountryNm = travellercountryNm;
	}
	public String getRoomNm() {
		return roomNm;
	}
	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}
	
	
	public Long getTotalNightStay() {
		return totalNightStay;
	}
	public void setTotalNightStay(Long totalNightStay) {
		this.totalNightStay = totalNightStay;
	}
	public List<AgentRegisVM> getAgent() {
		return agent;
	}
	public void setAgent(List<AgentRegisVM> agent) {
		this.agent = agent;
	}
	public String getNonRefund() {
		return nonRefund;
	}
	public void setNonRefund(String nonRefund) {
		this.nonRefund = nonRefund;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public String getNonSmokingRoom() {
		return nonSmokingRoom;
	}
	public void setNonSmokingRoom(String nonSmokingRoom) {
		this.nonSmokingRoom = nonSmokingRoom;
	}
	public String getTwinBeds() {
		return twinBeds;
	}
	public void setTwinBeds(String twinBeds) {
		this.twinBeds = twinBeds;
	}
	
	public String getLateCheckout() {
		return lateCheckout;
	}
	public void setLateCheckout(String lateCheckout) {
		this.lateCheckout = lateCheckout;
	}
	public String getSmokingRoom() {
		return smokingRoom;
	}
	public void setSmokingRoom(String smokingRoom) {
		this.smokingRoom = smokingRoom;
	}
	public String getWheelchair() {
		return wheelchair;
	}
	public void setWheelchair(String wheelchair) {
		this.wheelchair = wheelchair;
	}
	public String getHandicappedRoom() {
		return handicappedRoom;
	}
	public void setHandicappedRoom(String handicappedRoom) {
		this.handicappedRoom = handicappedRoom;
	}
	public String getLargeBed() {
		return largeBed;
	}
	public void setLargeBed(String largeBed) {
		this.largeBed = largeBed;
	}
	public String getHighFloor() {
		return highFloor;
	}
	public void setHighFloor(String highFloor) {
		this.highFloor = highFloor;
	}
	public String getEarlyCheckin() {
		return earlyCheckin;
	}
	public void setEarlyCheckin(String earlyCheckin) {
		this.earlyCheckin = earlyCheckin;
	}
	public String getAirportTransfer() {
		return airportTransfer;
	}
	public void setAirportTransfer(String airportTransfer) {
		this.airportTransfer = airportTransfer;
	}
	public String getAirportTransferInfo() {
		return airportTransferInfo;
	}
	public void setAirportTransferInfo(String airportTransferInfo) {
		this.airportTransferInfo = airportTransferInfo;
	}
	public String getEnterComments() {
		return enterComments;
	}
	public void setEnterComments(String enterComments) {
		this.enterComments = enterComments;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public List<PassengerBookingInfoVM> getPassengerInfo() {
		return passengerInfo;
	}
	public void setPassengerInfo(List<PassengerBookingInfoVM> passengerInfo) {
		this.passengerInfo = passengerInfo;
	}
	public Double getCurrencyExchangeRate() {
		return currencyExchangeRate;
	}
	public void setCurrencyExchangeRate(Double currencyExchangeRate) {
		this.currencyExchangeRate = currencyExchangeRate;
	}
	
	
	
}
