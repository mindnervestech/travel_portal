package com.travelportal.vm;

import java.util.List;

import com.travelportal.domain.MealType;
import com.travelportal.domain.rooms.PersonRate;

public class RateDetailsVM {
	
	public String name;
	public double rateValue;
	public boolean includeMeals;
	public String meals;
	public double onlineRateValue;
	public boolean onlineIncludeMeals;
	public String onlineMeals;
	
	public RateDetailsVM() {
		
	}
	public RateDetailsVM(PersonRate person) {
		this.name = person.getNumberOfPersons();
		this.rateValue = person.getRateValue();
		this.includeMeals = person.isMeal();
		this.onlineRateValue = person.getOnlineRateValue();
		this.onlineIncludeMeals = person.isOnlineIsMeal();
		
		if(person.getOnlineMealType() != null){
			this.onlineMeals = person.getOnlineMealType().getMealTypeNm();
		}else{
			this.onlineMeals = "Breakfast";
		}
		
		if(person.getMealType() != null) {
			this.meals = person.getMealType().getMealTypeNm();
		}else{
			this.meals = "Breakfast";
		}
		
	}
}
