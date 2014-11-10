package com.travelportal.vm;

import java.util.List;

import com.travelportal.domain.MealType;
import com.travelportal.domain.rooms.PersonRate;

public class RateDetailsVM {
	
	public String name;
	public double rateValue;
	public boolean includeMeals;
	public String meals;
	
	public RateDetailsVM() {
		
	}
	public RateDetailsVM(PersonRate person) {
		this.name = person.getNumberOfPersons();
		this.rateValue = person.getRateValue();
		this.includeMeals = person.isMeal();
		
		if(person.getMealType() != null) {
			this.meals = person.getMealType().getMealTypeNm();
		}
		
	}
}
