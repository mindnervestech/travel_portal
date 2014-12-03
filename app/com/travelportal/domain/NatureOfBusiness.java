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
@Table(name="nature_of_business")
public class NatureOfBusiness {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="nature_of_business")
	private String natureofbusiness;
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public String getNatureofbusiness() {
		return natureofbusiness;
	}
	public void setNatureofbusiness(String natureofbusiness) {
		this.natureofbusiness = natureofbusiness;
	}
	
	public static List<NatureOfBusiness> getNatureOfBusiness() {
		return JPA.em().createQuery("select c from NatureOfBusiness c ").getResultList();
	}
	public static NatureOfBusiness getNatureOfBusinessByName(String name) {

		return (NatureOfBusiness) JPA.em()
				.createQuery("select c from NatureOfBusiness c where c.natureofbusiness = ?1")
				.setParameter(1, name).getSingleResult();
	}
		
}
