package com.travelportal.vm;

import java.util.List;

import com.travelportal.domain.agent.AgentRegistration;


public class BatchMarkupInfoVM {
	
	public int batchMarkupId;
	public Long supplier;
	public String agentCode;
	public String agentfirstName;
	public String companyName;
	public int countryCode;
	public String CountryName;
	public int currencyCode;
	public String currencyName;
	public String selected;
	public Double flat;
	public Double percent;
	
	
	public int getBatchMarkupId() {
		return batchMarkupId;
	}
	public void setBatchMarkupId(int batchMarkupId) {
		this.batchMarkupId = batchMarkupId;
	}
	public Long getSupplier() {
		return supplier;
	}
	public void setSupplier(Long supplier) {
		this.supplier = supplier;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public int getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return CountryName;
	}
	public void setCountryName(String countryName) {
		CountryName = countryName;
	}
	public int getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(int currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public Double getFlat() {
		return flat;
	}
	public void setFlat(Double flat) {
		this.flat = flat;
	}
	public Double getPercent() {
		return percent;
	}
	public void setPercent(Double percent) {
		this.percent = percent;
	}
	public String getAgentfirstName() {
		return agentfirstName;
	}
	public void setAgentfirstName(String agentfirstName) {
		this.agentfirstName = agentfirstName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
	
	
	
	
	
	
}
