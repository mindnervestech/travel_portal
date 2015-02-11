package com.travelportal.domain.rooms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
//import com.travelportal.domain.RatePeriod;

@Entity
@Table(name="applicable_dateonrate")
public class ApplicableDateOnRate {

	@Column(name="Date_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="Date_selected")
	private Date date;
	@OneToOne
	private RateMeta rate;
		 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public RateMeta getRate() {
		return rate;
	}

	public void setRate(RateMeta rate) {
		this.rate = rate;
	}

	
	public static List<String> getMinDate(Long rateId) {  
		 List<String> list;
	
		list = JPA.em().createNativeQuery("select min(Date_selected) as fromDate from applicable_dateonrate ad where ad.rate_rate_id = '"+rateId+"'").getResultList();   
			
		return list;
		
	 }
	
	public static List<String> getMaxDate(Long rateId) {  
		 List<String> list;
	
		list = JPA.em().createNativeQuery("select max(Date_selected) as toDate from applicable_dateonrate ad where ad.rate_rate_id = '"+rateId+"'").getResultList();   
			
		return list;
		
	 }
	public static int deleteByRateDates(Long id) {
    	Query query = JPA.em().createQuery("delete from ApplicableDateOnRate p where p.rate.id = ?1");
		query.setParameter(1, id);
    	return query.executeUpdate();
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



