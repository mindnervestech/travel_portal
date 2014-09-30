package com.travelportal.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.JPA;

@Entity
@Table(name="hotel_start_ratings")
public class HotelStarRatings {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="start_rating_txt")
	private String starRatingTxt;
	
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
	/**
	 * @return the chainHotelName
	 */
	public String getstarRatingTxt() {
		return starRatingTxt;
	}
	
	
	public static List<HotelStarRatings> gethotelStarratings() {
		return JPA.em().createQuery("select c from HotelStarRatings c ").getResultList();
	}
	
}
