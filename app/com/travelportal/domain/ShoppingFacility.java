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
@Table(name="shopping_facility")
public class ShoppingFacility {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="shopping_area")
	private String shoppingArea;
	
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
	public String getshoppingArea() {
		return shoppingArea;
	}
	

	public static List<ShoppingFacility> gethotelStarratings() {
		return JPA.em().createQuery("select c from ShoppingFacility c ").getResultList();
	}
	
	
	
}
