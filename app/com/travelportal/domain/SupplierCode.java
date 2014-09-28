package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="supplier_code")
public class SupplierCode {

	@Id
	public Long id;
	@Column(name="supplier_code", unique=true)
	public Long supplierCode;
}
