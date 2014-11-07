package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

public class NormalRateVM {

	public List<RateDetailsVM> rateDetails = new ArrayList<RateDetailsVM>();

	public List<RateDetailsVM> getRateDetails() {
		return rateDetails;
	}

	public void setRateDetails(List<RateDetailsVM> rateDetails) {
		this.rateDetails = rateDetails;
	}
	
}
