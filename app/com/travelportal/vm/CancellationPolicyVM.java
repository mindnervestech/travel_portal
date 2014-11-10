package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

import com.travelportal.domain.rooms.CancellationPolicy;

public class CancellationPolicyVM {
	public long id;
	public String days;
	public boolean penaltyCharge;
	public String nights;
	public String percentage;
	
	public CancellationPolicyVM() {
		
	}
	
	public CancellationPolicyVM(CancellationPolicy policy) {
		this.id = policy.getId();
		this.days = policy.getCancellationDays();
		this.penaltyCharge = policy.isPenalty();
		if(policy.getNights() != null) {
			this.nights = policy.getNights();
		}
		if(policy.getPercentage() != null) {
			this.percentage = policy.getPercentage();
		}
		
	}
}
