package com.travelportal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="shopping_facility")
public class ShoppingFacility {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
}
