package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class SerachedRoomRateDetail {

	public long id;
	//public String rateName;
	public List<SearchRateDetailsVM> rateDetailsNormal = new ArrayList<SearchRateDetailsVM>();
	//public boolean isSpecialRate;
	
	public List<CancellationPolicyVM> cancellation = new ArrayList<CancellationPolicyVM>();
	public List<SearchRateDetailsVM> rateDetails = new ArrayList<SearchRateDetailsVM>();
	public SearchAllotmentMarketVM allotmentmarket;
	//public List<AllocatedCitiesVM> allocatedCities = new ArrayList<AllocatedCitiesVM>();
	public int flag;
	public int availableRoom;
	
	
	public int adult_occupancy;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	/*public String getRateName() {
		return rateName;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}*/

	
	/*public boolean isSpecialRate() {
		return isSpecialRate;
	}
	public void setSpecialRate(boolean isSpecialRate) {
		this.isSpecialRate = isSpecialRate;
	}*/
	
	/*public SearchSpecialRateVM getSpecial() {
		return special;
	}
	public void setSpecial(SearchSpecialRateVM special) {
		this.special = special;
	}*/
	
	
	/*public SeaachNormalRateVM getNormalRate() {
		return normalRate;
	}
	public void setNormalRate(SeaachNormalRateVM normalRate) {
		this.normalRate = normalRate;
	}*/
	
	
	
	public int getAdult_occupancy() {
		return adult_occupancy;
	}
	public List<CancellationPolicyVM> getCancellation() {
		return cancellation;
	}
	public void setCancellation(List<CancellationPolicyVM> cancellation) {
		this.cancellation = cancellation;
	}
	public List<SearchRateDetailsVM> getRateDetailsNormal() {
		return rateDetailsNormal;
	}
	public void setRateDetailsNormal(List<SearchRateDetailsVM> rateDetailsNormal) {
		this.rateDetailsNormal = rateDetailsNormal;
	}
	public void setAdult_occupancy(int adult_occupancy) {
		this.adult_occupancy = adult_occupancy;
	}
	public SearchAllotmentMarketVM getAllotmentmarket() {
		return allotmentmarket;
	}
	public void setAllotmentmarket(SearchAllotmentMarketVM allotmentmarket) {
		this.allotmentmarket = allotmentmarket;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getAvailableRoom() {
		return availableRoom;
	}
	public void setAvailableRoom(int availableRoom) {
		this.availableRoom = availableRoom;
	}
	
	
	
	
	
}
