package com.travelportal.domain.rooms;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.JPA;

@Entity
@Table(name="free_stay")
public class FreeStay {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="type_free_stay")
	private String typeFreeStay;
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getTypeFreeStay() {
		return typeFreeStay;
	}
	public void setTypeFreeStay(String typeFreeStay) {
		this.typeFreeStay = typeFreeStay;
	}
	
	public static List<FreeStay> getFreeStay() {
		return JPA.em().createQuery("select c.typeFreeStay from FreeStay c ").getResultList();
	}
	public static FreeStay getFreeStayByName(String name) {

		return (FreeStay) JPA.em()
				.createQuery("select c from FreeStay c where c.typeFreeStay = ?1")
				.setParameter(1, name).getSingleResult();
	}
		
}
