package com.travelportal.domain.rooms;

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

@Entity
@Table(name="cancellation_policy")
public class CancellationPolicy {

	@Column(name="cancellation_policy_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="cancellation_days")
	private String cancellationDays;
	@Column(name="is_penalty")
	private boolean isPenalty;
	@Column(name="nights")
	private String nights;
	@Column(name="percentage")
	private String percentage;
	@OneToOne
	private RateMeta rate;
	private boolean isNormal;
	
	public boolean isNormal() {
		return isNormal;
	}
	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCancellationDays() {
		return cancellationDays;
	}
	public void setCancellationDays(String cancellationDays) {
		this.cancellationDays = cancellationDays;
	}
	public boolean isPenalty() {
		return isPenalty;
	}
	public void setPenalty(boolean isPenalty) {
		this.isPenalty = isPenalty;
	}
	public String getNights() {
		return nights;
	}
	public void setNights(String nights) {
		this.nights = nights;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public RateMeta getRate() {
		return rate;
	}
	public void setRate(RateMeta rate) {
		this.rate = rate;
	}
	
	public static List<CancellationPolicy> findByRateMetaId(long id) {
    	Query query = JPA.em().createQuery("Select c from CancellationPolicy c where c.rate.id = ?1");
		query.setParameter(1, id);
    	return (List<CancellationPolicy>) query.getResultList();
    }
	
	public static List<CancellationPolicy> findByRateMetaIdAndNormal(Long id,boolean isNormal) {
    	Query query = JPA.em().createQuery("Select c from CancellationPolicy c where c.rate.id = ?1 and c.isNormal = ?2");
		query.setParameter(1, id);
		query.setParameter(2, isNormal);
    	return (List<CancellationPolicy>) query.getResultList();
    }
	
	public static CancellationPolicy findById(Long id) {
    	Query query = JPA.em().createQuery("Select c from CancellationPolicy c where c.id = ?1");
		query.setParameter(1, id);
    	return (CancellationPolicy) query.getSingleResult();
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
