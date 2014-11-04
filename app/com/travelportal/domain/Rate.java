package com.travelportal.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import com.travelportal.domain.rooms.ChildPolicies;
import com.travelportal.domain.rooms.HotelRoomTypes;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="rate")
public class Rate { //supplier specific records...
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="name")
	private String name;
	
	
	
	
	
	
	
	
	/*public static List<HotelMealPlan> getmealtype(long supplierCode) {
		return JPA.em().createQuery("select c from HotelMealPlan c where c.SupplierCode = ?1").setParameter(1, supplierCode).getResultList();
	}
	
	//findById
	 public static HotelMealPlan findById(int id) {
	    	Query query = JPA.em().createQuery("Select a from HotelMealPlan a where a.id = ?1");
			query.setParameter(1, id);
	    	return (HotelMealPlan) query.getSingleResult();
	    }
	 
	 
	
	public static HotelMealPlan getHotelMealPlanIdByCode(int code) {
		return (HotelMealPlan) JPA.em().createQuery("select c from HotelMealPlan c where id = ?1").setParameter(1, code).getSingleResult();
	}
		
	*/
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
