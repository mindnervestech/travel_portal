package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.JPA;

@Entity
@Table(name = "cancellation_date_diff")
public class CancellationDateDiff {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "date_diff")
	private int dateDiff;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDateDiff() {
		return dateDiff;
	}
	public void setDateDiff(int dateDiff) {
		this.dateDiff = dateDiff;
	}
	
	public static CancellationDateDiff getById(int code) {
		return (CancellationDateDiff) JPA.em()
				.createQuery("select c from CancellationDateDiff c where id = ?1")
				.setParameter(1, code).getSingleResult();
	}
	
	
}
