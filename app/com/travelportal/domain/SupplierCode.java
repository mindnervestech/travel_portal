package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.JPA;

@Entity
@Table(name="supplier_code")
public class SupplierCode {

	@Id
	public Long id;
	@Column(name="supplier_code", unique=true)
	public Long supplierCode;
	
	/*
	public static SupplierCode getsupplierIdByCode(Long code) {
		return (SupplierCode) JPA.em().createQuery("select c from SupplierCode c where supplierCode = ?1").setParameter(1, code).getSingleResult();
	}*/
}
