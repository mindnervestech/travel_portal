package com.travelportal.vm;

import java.util.List;

import javax.persistence.OneToOne;

import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.rooms.RateMeta;


public class SpecificMarkupVM {
	
	
	public long code;
	public List<Integer> agentSpecific;
	public List<Integer> rateSelected;
	public String specificSelected;
	public Double specificFlat;
	public Double specificPercent;
	
	
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	public List<Integer> getAgentSpecific() {
		return agentSpecific;
	}
	public void setAgentSpecific(List<Integer> agentSpecific) {
		this.agentSpecific = agentSpecific;
	}
	public List<Integer> getRateSelected() {
		return rateSelected;
	}
	public void setRateSelected(List<Integer> rateSelected) {
		this.rateSelected = rateSelected;
	}
	public String getSpecificSelected() {
		return specificSelected;
	}
	public void setSpecificSelected(String specificSelected) {
		this.specificSelected = specificSelected;
	}
	public Double getSpecificFlat() {
		return specificFlat;
	}
	public void setSpecificFlat(Double specificFlat) {
		this.specificFlat = specificFlat;
	}
	public Double getSpecificPercent() {
		return specificPercent;
	}
	public void setSpecificPercent(Double specificPercent) {
		this.specificPercent = specificPercent;
	}
	
	
	
	
	
	
}
