package com.travelportal.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name = "hotel_booking_dates")
public class HotelBookingDates {

	
	@Column(name="id", unique=true)
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date bookingDate;
	private Double bookDateRate;

	@OneToOne
	private HotelBookingDetails bookingId;

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Double getBookDateRate() {
		return bookDateRate;
	}

	public void setBookDateRate(Double bookDateRate) {
		this.bookDateRate = bookDateRate;
	}

	public HotelBookingDetails getBookingId() {
		return bookingId;
	}

	public void setBookingId(HotelBookingDetails bookingId) {
		this.bookingId = bookingId;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transactional
	public void save() {
		JPA.em().persist(this);
		JPA.em().flush();
	}

	@Transactional
	public void delete() {
		JPA.em().remove(this);
	}

	@Transactional
	public void merge() {
		JPA.em().merge(this);
	}

	@Transactional
	public void refresh() {
		JPA.em().refresh(this);
	}

}
