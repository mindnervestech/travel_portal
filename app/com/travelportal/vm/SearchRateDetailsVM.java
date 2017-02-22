package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

import com.travelportal.domain.rooms.PersonRate;

public class SearchRateDetailsVM {
	

	public double rateValue;
	public double rateAvg;
	public Long rateId;
	public int showFirstTime;
	public long mealTypeId;
	public boolean isNonRefund;
	public String mealTypeName;
	public String adult;
	public List<CancellationPolicyVM> cancellation = new ArrayList<CancellationPolicyVM>();
	
	
	public List<CancellationPolicyVM> getCancellation() {
		return cancellation;
	}

	public void setCancellation(List<CancellationPolicyVM> cancellation) {
		this.cancellation = cancellation;
	}

	public SearchRateDetailsVM() {
		
	}
	
	public String getAdult() {
		return adult;
	}

	public void setAdult(String adult) {
		this.adult = adult;
	}

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}
	
	public double getRateValue() {
		return rateValue;
	}

	public void setRateValue(double rateValue) {
		this.rateValue = rateValue;
	}

	public double getRateAvg() {
		return rateAvg;
	}

	public void setRateAvg(double rateAvg) {
		this.rateAvg = rateAvg;
	}

	public SearchRateDetailsVM(PersonRate person) {
		
		this.rateValue = person.getRateValue();
	}

	public long getMealTypeId() {
		return mealTypeId;
	}

	public void setMealTypeId(long mealTypeId) {
		this.mealTypeId = mealTypeId;
	}

	public String getMealTypeName() {
		return mealTypeName;
	}

	public void setMealTypeName(String mealTypeName) {
		this.mealTypeName = mealTypeName;
	}

	public boolean isNonRefund() {
		return isNonRefund;
	}

	public void setNonRefund(boolean isNonRefund) {
		this.isNonRefund = isNonRefund;
	}

	public int getShowFirstTime() {
		return showFirstTime;
	}

	public void setShowFirstTime(int showFirstTime) {
		this.showFirstTime = showFirstTime;
	}

	

	
	
	
}
