package com.travelportal.domain.admin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import com.travelportal.domain.HotelProfile;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="breakfastMarkup")
public class BreakfastMarkup {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String breakfastRate;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBreakfastRate() {
		return breakfastRate;
	}

	public void setBreakfastRate(String breakfastRate) {
		this.breakfastRate = breakfastRate;
	}

	public static BreakfastMarkup findById(Long id) {
		try
		{
    	Query query = JPA.em().createQuery("Select a from BreakfastMarkup a where a.id = ?1");
		query.setParameter(1, id);
    	return (BreakfastMarkup) query.getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
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
