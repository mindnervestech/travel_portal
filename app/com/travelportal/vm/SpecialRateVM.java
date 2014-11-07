package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

public class SpecialRateVM {

	public List<String> weekDays = new ArrayList<String>();
	public List<RateDetailsVM> rateDetails = new ArrayList<RateDetailsVM>();
	public List<CancellationPolicyVM> cancellation = new ArrayList<CancellationPolicyVM>();
	
	public List<String> getWeekDays() {
		return weekDays;
	}
	public void setWeekDays(List<String> weekDays) {
		this.weekDays = weekDays;
	}
	public List<RateDetailsVM> getRateDetails() {
		return rateDetails;
	}
	public void setRateDetails(List<RateDetailsVM> rateDetails) {
		this.rateDetails = rateDetails;
	}
	public List<CancellationPolicyVM> getCancellation() {
		return cancellation;
	}
	public void setCancellation(List<CancellationPolicyVM> cancellation) {
		this.cancellation = cancellation;
	}
	
}
