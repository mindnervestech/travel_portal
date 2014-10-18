package com.travelportal.domain.rooms;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;

import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.Location;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="room_child_policies")
public class RoomChildPolicies {
	
	@Column(name="room_child_policy_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int roomchildPolicyId;
	@Column(name="child_age_allowed_from")
	private int allowedChildAgeFrom;
	@Column(name="child_age_allowed_to")
	private int allowedChildAgeTo;
	@Column(name="years")
	private int years; 
	@Column(name="net_rate")
	private String netRate;
	
		
	public int getRoomchildPolicyId() {
		return roomchildPolicyId;
	}

	public void setRoomchildPolicyId(int roomchildPolicyId) {
		this.roomchildPolicyId = roomchildPolicyId;
	}

	public int getAllowedChildAgeFrom() {
		return allowedChildAgeFrom;
	}

	public void setAllowedChildAgeFrom(int allowedChildAgeFrom) {
		this.allowedChildAgeFrom = allowedChildAgeFrom;
	}

	public int getAllowedChildAgeTo() {
		return allowedChildAgeTo;
	}

	public void setAllowedChildAgeTo(int allowedChildAgeTo) {
		this.allowedChildAgeTo = allowedChildAgeTo;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public String getNetRate() {
		return netRate;
	}

	public void setNetRate(String netRate) {
		this.netRate = netRate;
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
