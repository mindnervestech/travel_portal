package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class AllotmentVM {
	
	public int allotmentId;
	public Long supplierCode;
	public long roomId;
	public String datePeriodId;
	public String formPeriod;
	public String toPeriod;
	public String currencyName;
	//public List<Integer> rate;
	public List<AllotmentMarketVM> allotmentmarket;
	
	
	
	
	public int getAllotmentId() {
		return allotmentId;
	}
	public void setAllotmentId(int allotmentId) {
		this.allotmentId = allotmentId;
	}
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
	
	
	
	public String getDatePeriodId() {
		return datePeriodId;
	}
	public void setDatePeriodId(String datePeriodId) {
		this.datePeriodId = datePeriodId;
	}
	public String getFormPeriod() {
		return formPeriod;
	}
	public void setFormPeriod(String formPeriod) {
		this.formPeriod = formPeriod;
	}
	public String getToPeriod() {
		return toPeriod;
	}
	public void setToPeriod(String toPeriod) {
		this.toPeriod = toPeriod;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	/*public List<Integer> getRate() {
		return rate;
	}
	public void setRate(List<Integer> rate) {
		this.rate = rate;
	}*/
	public List<AllotmentMarketVM> getAllotmentmarket() {
		return allotmentmarket;
	}
	public void setAllotmentmarket(List<AllotmentMarketVM> allotmentmarket) {
		this.allotmentmarket = allotmentmarket;
	}
	
	
	
}
