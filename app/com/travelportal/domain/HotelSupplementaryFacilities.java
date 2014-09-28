package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Hotel_supplementary_facilities")
public class HotelSupplementaryFacilities {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="supplement_facility")
	private boolean supplementFacility;
	@Column(name="mandatory_supp_facility")
	private boolean mandatorySupplementFacility;
	/**
	 * @return the id
	 */
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
	 * @return the supplementFacility
	 */
	public boolean isSupplementFacility() {
		return supplementFacility;
	}
	/**
	 * @param supplementFacility the supplementFacility to set
	 */
	public void setSupplementFacility(boolean supplementFacility) {
		this.supplementFacility = supplementFacility;
	}
	/**
	 * @return the mandatorySupplementFacility
	 */
	public boolean isMandatorySupplementFacility() {
		return mandatorySupplementFacility;
	}
	/**
	 * @param mandatorySupplementFacility the mandatorySupplementFacility to set
	 */
	public void setMandatorySupplementFacility(boolean mandatorySupplementFacility) {
		this.mandatorySupplementFacility = mandatorySupplementFacility;
	}
	
}
