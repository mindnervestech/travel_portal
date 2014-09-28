package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
