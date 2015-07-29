package com.travelportal.vm;

import com.travelportal.domain.Currency;


public class CurrencyExchangeVM {
	
	public Integer currencySelect;
	public Double curr_THB;
	public Double curr_SGD;
	public Double curr_MYR;
	public Double curr_INR;
	
	public Integer getCurrencySelect() {
		return currencySelect;
	}
	public void setCurrencySelect(Integer currencySelect) {
		this.currencySelect = currencySelect;
	}
	public Double getCurr_THB() {
		return curr_THB;
	}
	public void setCurr_THB(Double curr_THB) {
		this.curr_THB = curr_THB;
	}
	public Double getCurr_SGD() {
		return curr_SGD;
	}
	public void setCurr_SGD(Double curr_SGD) {
		this.curr_SGD = curr_SGD;
	}
	public Double getCurr_MYR() {
		return curr_MYR;
	}
	public void setCurr_MYR(Double curr_MYR) {
		this.curr_MYR = curr_MYR;
	}
	public Double getCurr_INR() {
		return curr_INR;
	}
	public void setCurr_INR(Double curr_INR) {
		this.curr_INR = curr_INR;
	}
	
	
	
}
