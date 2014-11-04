package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.travelportal.domain.Currency;
import com.travelportal.domain.Rate;
import com.travelportal.domain.rooms.HotelRoomTypes;

public class RatePeriodVM {

	private int id;
	private Date fromPeriod;
	private Date toPeriod;
	private CurrencyVM currency;
	private RoomtypeVM roomtype;
	private List<Rate> rate;
	
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
	public List<Rate> getRate() {
		return rate;
	}
	public void setRate(List<Rate> rate) {
		this.rate = rate;
	}
	
		
	
}
