package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

public class SpecialDaysRateVM {

	//public List<String> weekDays = new ArrayList<String>();
	public List<RateDetailsVM> rateDetails = new ArrayList<RateDetailsVM>();
	public List<CancellationPolicyVM> cancellation = new ArrayList<CancellationPolicyVM>();
	public boolean non_refund;
	public String fromspecial;
	public String tospecial;
	public double isspecialdaysrate;
	public String name;
	
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
	public String getFromspecial() {
		return fromspecial;
	}
	public void setFromspecial(String fromspecial) {
		this.fromspecial = fromspecial;
	}
	public String getTospecial() {
		return tospecial;
	}
	public void setTospecial(String tospecial) {
		this.tospecial = tospecial;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getIsspecialdaysrate() {
		return isspecialdaysrate;
	}
	public void setIsspecialdaysrate(double isspecialdaysrate) {
		this.isspecialdaysrate = isspecialdaysrate;
	}
	/*public boolean isNon_refund() {
		return non_refund;
	}
	public void setNon_refund(boolean non_refund) {
		this.non_refund = non_refund;
	}*/
	
	
	
	
	
	
}
