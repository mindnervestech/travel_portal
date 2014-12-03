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
@Table(name="salutation")
public class Salutation {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="salutation_id")
	private int salutationId;
	@Column(name="salutation_value")
	private String salutationValue;
	/**
	 * @return the salutationId
	 */
	public int getSalutationId() {
		return salutationId;
	}
	/**
	 * @param salutationId the salutationId to set
	 */
	public void setSalutationId(int salutationId) {
		this.salutationId = salutationId;
	}
	/**
	 * @return the salutationValue
	 */
	public String getSalutationValue() {
		return salutationValue;
	}
	/**
	 * @param salutationValue the salutationValue to set
	 */
	public void setSalutationValue(String salutationValue) {
		this.salutationValue = salutationValue;
	}
	
	
	public static List<Salutation> getsalutation() {
		return JPA.em().createQuery("select c from Salutation c").getResultList();
	}
	
	public static Salutation getsalutationIdIdByCode(int code) {
		return (Salutation) JPA.em().createQuery("select c from Salutation c where salutationId = ?1").setParameter(1, code).getSingleResult();
	}
	
	public static Salutation getSalutationByName(String name) {

		return (Salutation) JPA.em()
				.createQuery("select c from Salutation c where c.salutationValue = ?1")
				.setParameter(1, name).getSingleResult();
	}
	
}
