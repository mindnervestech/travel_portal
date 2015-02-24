package com.travelportal.domain.rooms;

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

@Entity
@Table(name="rate_details")
public class RateDetails {

	@Column(name="rate_detail_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne
	private RateMeta rate;
	@Column(name="is_special_rate")
	private double isSpecialRate;
	@Column(name="special_days")
	private String specialDays;
	private boolean applyToMarket;
	
	public boolean isApplyToMarket() {
		return applyToMarket;
	}
	public void setApplyToMarket(boolean applyToMarket) {
		this.applyToMarket = applyToMarket;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RateMeta getRate() {
		return rate;
	}
	public void setRate(RateMeta rate) {
		this.rate = rate;
	}
	
	public double getIsSpecialRate() {
		return isSpecialRate;
	}
	public void setIsSpecialRate(double isSpecialRate) {
		this.isSpecialRate = isSpecialRate;
	}
	public String getSpecialDays() {
		return specialDays;
	}
	public void setSpecialDays(String specialDays) {
		this.specialDays = specialDays;
	}
	
	public static RateDetails findByRateMetaId(Long id) {
		
    	Query query = JPA.em().createQuery("Select a from RateDetails a where a.rate.id = ?1");
		query.setParameter(1, id);
    	return (RateDetails) query.getSingleResult();
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
