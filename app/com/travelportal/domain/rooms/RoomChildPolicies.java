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
	@Column(name="extra_Child_Rate")
	private Double extraChildRate;
	
	/*@Column(name="years")
	private int years; 
	@Column(name="net_rate")*/
	//private String netRate;
	/*@Column(name="supplierCode")
	private long supplierCode;*/
	
	
	
		
	/*public long getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}*/

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

	
	
	
	/*public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}*/

	public Double getExtraChildRate() {
		return extraChildRate;
	}

	public void setExtraChildRate(Double extraChildRate) {
		this.extraChildRate = extraChildRate;
	}

	/*public String getNetRate() {
		return netRate;
	}

	public void setNetRate(String netRate) {
		this.netRate = netRate;
	}*/
	public static RoomChildPolicies findById(int id) {
		try
		{
    	Query query = JPA.em().createQuery("Select a from RoomChildPolicies a where a.roomchildPolicyId = ?1");
		query.setParameter(1, id);
    	return (RoomChildPolicies) query.getSingleResult();
		} catch(NoResultException e){
			 
			RoomChildPolicies roomchildPolicies = new RoomChildPolicies();
			roomchildPolicies.save();
			return roomchildPolicies;
		 }
    }
	
	 public static int deletefindById(int id) {
	    	Query query = JPA.em().createQuery("delete from RoomChildPolicies a where a.roomchildPolicyId = ?1");
			query.setParameter(1, id);
			return query.executeUpdate();
	    }
	 
	 public static int deletechildRel(int code) {
			Query q = JPA.em().createNativeQuery("delete from hotel_room_types_room_child_policies where roomchildPolicies_room_child_policy_id = '"+code+"'");
			return q.executeUpdate();
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
