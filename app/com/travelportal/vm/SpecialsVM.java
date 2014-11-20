package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

public class SpecialsVM {

	public long id;
	public String fromDate;
	public String toDate;
	public String promotionName;
	public List<String> roomTypes = new ArrayList<>();
	public List<SpecialsMarketVM> markets = new ArrayList<SpecialsMarketVM>();
}
