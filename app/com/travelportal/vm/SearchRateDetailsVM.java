package com.travelportal.vm;

import java.util.List;

import com.travelportal.domain.MealType;
import com.travelportal.domain.rooms.PersonRate;

public class SearchRateDetailsVM {
	
	
	public double rateValue;
	public double rateAvg;
	public long mealTypeId;
	public String mealTypeName;
	public String adult;
	
	
	public SearchRateDetailsVM() {
		
	}
	
	public String getAdult() {
		return adult;
	}

	public void setAdult(String adult) {
		this.adult = adult;
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

	
	
	
}
