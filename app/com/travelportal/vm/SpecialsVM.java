package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

public class SpecialsVM {

	public long id;
	public String fromDate;
	public String toDate;
	public String promotionName;
	public String promotionType;
	public Long supplierCode;
	public List<Long> roomTypes = new ArrayList<>();
	public List<RoomType> roomallInfo = new ArrayList<>();
	public List<SpecialsMarketVM> markets = new ArrayList<SpecialsMarketVM>();
}
