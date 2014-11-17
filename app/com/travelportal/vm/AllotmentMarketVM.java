package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class AllotmentMarketVM {
	
	
	public int allotmentMarketId;
	public String period;
	public String specifyAllot;
	public int allocation;
	public int choose;
	public List<Long> rate;
	
	
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
	
	
	
	
	
	
}
