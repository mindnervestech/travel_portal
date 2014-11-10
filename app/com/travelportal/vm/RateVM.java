package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class RateVM {

	private long id;
	private String name;
	public String rateName;
	public String roomType;
	public String fromDate;
	public String toDate;
	public String currency;
	public NormalRateVM normalRate;
	public boolean isSpecialRate;
	public SpecialRateVM special;
	public List<CancellationPolicyVM> cancellation = new ArrayList<CancellationPolicyVM>();
	
	public String getRateName() {
		return rateName;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
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
	public boolean isSpecialRate() {
		return isSpecialRate;
	}
	public void setSpecialRate(boolean isSpecialRate) {
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
}
