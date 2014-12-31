package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class RateVM {

	public long id;
	public String name;
	public String rateName;
	public Long roomId;
	public String roomName;
	public String fromDate;
	public String toDate;
	public String currency;
	public Long supplierCode;
	public NormalRateVM normalRate;
	public boolean isSpecialRate;
	public boolean applyToMarket;
	public SpecialRateVM special;
	public List<CancellationPolicyVM> cancellation = new ArrayList<CancellationPolicyVM>();
	public List<AllocatedCitiesVM> allocatedCities = new ArrayList<AllocatedCitiesVM>();
	//public List<HotelProfileVM> hotelprofile = new ArrayList<HotelProfileVM>();
	public AllotmentMarketVM allotmentmarket;
	
	
	
	
	/*public List<HotelProfileVM> getHotelprofile() {
		return hotelprofile;
	}
	public void setHotelprofile(List<HotelProfileVM> hotelprofile) {
		this.hotelprofile = hotelprofile;
	}*/
	public Long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getRateName() {
		return rateName;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	
		
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public NormalRateVM getNormalRate() {
		return normalRate;
	}
	public void setNormalRate(NormalRateVM normalRate) {
		this.normalRate = normalRate;
	}
	public boolean getIsSpecialRate() {
		return isSpecialRate;
	}
	public void setIsSpecialRate(boolean isSpecialRate) {
		this.isSpecialRate = isSpecialRate;
	}
	public SpecialRateVM getSpecial() {
		return special;
	}
	public void setSpecial(SpecialRateVM special) {
		this.special = special;
	}
	public List<CancellationPolicyVM> getCancellation() {
		return cancellation;
	}
	public void setCancellation(List<CancellationPolicyVM> cancellation) {
		this.cancellation = cancellation;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public AllotmentMarketVM getAllotmentmarket() {
		return allotmentmarket;
	}
	public void setAllotmentmarket(AllotmentMarketVM allotmentmarket) {
		this.allotmentmarket = allotmentmarket;
	}
	
	
	/*public boolean isApplyToMarket() {
		return applyToMarket;
	}
	public void setApplyToMarket(boolean applyToMarket) {
		this.applyToMarket = applyToMarket;
	}*/
	
}
