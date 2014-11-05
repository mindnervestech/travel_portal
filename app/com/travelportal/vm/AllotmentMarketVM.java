package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class AllotmentMarketVM {
	
	
	
	private String period;
	private String specifyAllot;
	private int allocation;
	private int choose;
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
	
	
	
	
	
	
}
