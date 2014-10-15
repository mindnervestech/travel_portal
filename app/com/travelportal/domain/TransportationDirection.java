package com.travelportal.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="hotel_transportation_direction")
public class TransportationDirection {
	@Column(name="transport_code")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int transportCode;
	@Column(name="location_Name")
	private String locationName;
	@Column(name="location_sAddr")
	private String locationAddr;
	
	
	
	public int getTransportCode() {
		return transportCode;
	}

	public void setTransportCode(int transportCode) {
		this.transportCode = transportCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationAddr() {
		return locationAddr;
	}

	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

	@Transactional
    public void save() {
		JPA.em().persist(this);
        JPA.em().flush();     
    }
      
    @Transactional
    public void delete() {
        JPA.em().remove(this);
    }
    
    @Transactional
    public void merge() {
        JPA.em().merge(this);
    }
    
    @Transactional
    public void refresh() {
        JPA.em().refresh(this);
    }
	
}
