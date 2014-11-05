package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class AllotmentVM {
	
	
	private Long supplierCode;
	private long roomId;
	private int datePeriodId;
	private int currencyId;
	private List<Integer> rate;
	private List<AllotmentMarketVM> allotmentmarket;
	public Long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public long getRoomId() {
		return roomId;
	}
	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}
	
	public int getDatePeriodId() {
		return datePeriodId;
	}
	public void setDatePeriodId(int datePeriodId) {
		this.datePeriodId = datePeriodId;
	}
	public int getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}
	
	public List<Integer> getRate() {
		return rate;
	}
	public void setRate(List<Integer> rate) {
		this.rate = rate;
	}
	public List<AllotmentMarketVM> getAllotmentmarket() {
		return allotmentmarket;
	}
	public void setAllotmentmarket(List<AllotmentMarketVM> allotmentmarket) {
		this.allotmentmarket = allotmentmarket;
	}
	
	
	
}
