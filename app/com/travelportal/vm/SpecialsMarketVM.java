package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

public class SpecialsMarketVM {

	public long id;
	public String stayDays;
	public String payDays;
	public String typeOfStay;
	public boolean multiple;
	public boolean combined;
	public boolean breakfast;
	public String adultRate;
	public String childRate;
	public String applyToMarket;
	public List<AllocatedCitiesVM> allocatedCities = new ArrayList<AllocatedCitiesVM>();
	
}
