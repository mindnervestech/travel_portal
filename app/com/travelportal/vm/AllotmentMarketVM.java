package com.travelportal.vm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class AllotmentMarketVM {
	
	
	public int allotmentMarketId;
	public String period;
	public String specifyAllot;
	public int allocation;
	public int choose;
	public int stopAllocation;
	public int stopChoose;
	public String stopPeriod;
	public String fromDate;
	public String toDate;
	public List<Long> rate;
	public String applyMarket;
	public List<AllocatedCitiesVM> allocatedCities = new ArrayList<AllocatedCitiesVM>();
	
	public int getAllotmentMarketId() {
		return allotmentMarketId;
	}
	public void setAllotmentMarketId(int allotmentMarketId) {
		this.allotmentMarketId = allotmentMarketId;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getSpecifyAllot() {
		return specifyAllot;
	}
	public void setSpecifyAllot(String specifyAllot) {
		this.specifyAllot = specifyAllot;
	}
	
	public int getAllocation() {
		return allocation;
	}
	public void setAllocation(int allocation) {
		this.allocation = allocation;
	}
	public int getChoose() {
		return choose;
	}
	public void setChoose(int choose) {
		this.choose = choose;
	}
	public List<Long> getRate() {
		return rate;
	}
	public void setRate(List<Long> rate) {
		this.rate = rate;
	}
	
	public int getStopAllocation() {
		return stopAllocation;
	}
	public void setStopAllocation(int stopAllocation) {
		this.stopAllocation = stopAllocation;
	}
	public int getStopChoose() {
		return stopChoose;
	}
	public void setStopChoose(int stopChoose) {
		this.stopChoose = stopChoose;
	}
	public String getStopPeriod() {
		return stopPeriod;
	}
	public void setStopPeriod(String stopPeriod) {
		this.stopPeriod = stopPeriod;
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
	public List<AllocatedCitiesVM> getAllocatedCities() {
		return allocatedCities;
	}
	public void setAllocatedCities(List<AllocatedCitiesVM> allocatedCities) {
		this.allocatedCities = allocatedCities;
	}
	public String getApplyMarket() {
		return applyMarket;
	}
	public void setApplyMarket(String applyMarket) {
		this.applyMarket = applyMarket;
	}
	
	
	
	
	
}
