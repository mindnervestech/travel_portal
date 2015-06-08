package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

public class SpecialRateVM {

	public List<String> weekDays = new ArrayList<String>();
	public List<RateDetailsVM> rateDetails = new ArrayList<RateDetailsVM>();
	public List<CancellationPolicyVM> cancellation = new ArrayList<CancellationPolicyVM>();
	public boolean non_refund;
	public boolean rateDay0;
	public boolean rateDay1;
	public boolean rateDay2;
	public boolean rateDay3;
	public boolean rateDay4;
	public boolean rateDay5;
	public boolean rateDay6;
	
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
	/*public boolean isNon_refund() {
		return non_refund;
	}
	public void setNon_refund(boolean non_refund) {
		this.non_refund = non_refund;
	}*/
	
	
}
