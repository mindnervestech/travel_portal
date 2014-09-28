package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hotel_start_ratings")
public class HotelStarRatings {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="start_rating_txt")
	private String starRatingTxt;
	
}
