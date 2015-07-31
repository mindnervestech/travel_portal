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
@Table(name="hear_about_us")
public class HearAboutUs {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="hear_aboutus")
	private String hearAboutUs;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getHearAboutUs() {
		return hearAboutUs;
	}
	public void setHearAboutUs(String hearAboutUs) {
		this.hearAboutUs = hearAboutUs;
	}
	
	public static List<HearAboutUs> gethearAboutUs() {
		return JPA.em().createQuery("select c from HearAboutUs c ").getResultList();
	}
	
	public static HearAboutUs getHearAboutUsByName(String name) {

		return (HearAboutUs) JPA.em()
				.createQuery("select c from HearAboutUs c where c.hearAboutUs = ?1")
				.setParameter(1, name).getSingleResult();
	}

		
}
