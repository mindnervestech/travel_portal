package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.travelportal.domain.Currency;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RateMeta;

public class RatePeriodVM {

	public int id;
	public Date fromPeriod;
	public Date toPeriod;
	public CurrencyVM currency;
	public RoomtypeVM roomtype;
	public List<RateMeta> rate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFromPeriod() {
		return fromPeriod;
	}
	public void setFromPeriod(Date fromPeriod) {
		this.fromPeriod = fromPeriod;
	}
	public Date getToPeriod() {
		return toPeriod;
	}
	public void setToPeriod(Date toPeriod) {
		this.toPeriod = toPeriod;
	}
	public CurrencyVM getCurrency() {
		return currency;
	}
	public void setCurrencyVM(CurrencyVM currency) {
		this.currency = currency;
	}
	public RoomtypeVM getRoomtype() {
		return roomtype;
	}
	public void setRoomtype(RoomtypeVM roomtype) {
		this.roomtype = roomtype;
	}
	public List<RateMeta> getRate() {
		return rate;
	}
	public void setRate(List<RateMeta> rate) {
		this.rate = rate;
	}
	public void setCurrency(CurrencyVM currency) {
		this.currency = currency;
	}
	
	
	
		
	
}
