package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

public class SearchSpecialsVM {

	public long id;
	public String fromDate;
	public String toDate;
	public String promotionName;
	public Long supplierCode;
	public List<String> roomTypes = new ArrayList<>();
	public List<SpecialsMarketVM> markets = new ArrayList<SpecialsMarketVM>();
}
