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
@Table(name="rate_special_days")
public class RateSpecialDays {

	@Column(name="rate_special_days_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="special_days_name")
	private String specialdaysName;
	@OneToOne
	private RateMeta rate;
	@Column(name="is_special_days_rate")
	private double  isSpecialdaysRate;
	@Column(name="fromspecial_days")
	private String fromspecialDate;
	@Column(name="tospecial_days")
	private String tospecialDate;
	
	
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

	public double getIsSpecialdaysRate() {
		return isSpecialdaysRate;
	}

	public void setIsSpecialdaysRate(double isSpecialdaysRate) {
		this.isSpecialdaysRate = isSpecialdaysRate;
	}

	public String getFromspecialDate() {
		return fromspecialDate;
	}

	public void setFromspecialDate(String fromspecialDate) {
		this.fromspecialDate = fromspecialDate;
	}

	public String getTospecialDate() {
		return tospecialDate;
	}

	public void setTospecialDate(String tospecialDate) {
		this.tospecialDate = tospecialDate;
	}

	public String getSpecialdaysName() {
		return specialdaysName;
	}

	public void setSpecialdaysName(String specialdaysName) {
		this.specialdaysName = specialdaysName;
	}

	public static List<RateSpecialDays> findByRateMetaId(Long id) {
		
    	Query query = JPA.em().createQuery("Select a from RateSpecialDays a where a.rate.id = ?1");
		query.setParameter(1, id);
    	return (List<RateSpecialDays>) query.getResultList();
    }
	
public static RateSpecialDays findByRateMetaIdandIsSpecial(Long id,double isspecial,String name) {
		try
		{
    	Query query = JPA.em().createQuery("Select a from RateSpecialDays a where a.rate.id = ?1 and a.isSpecialdaysRate = ?2 and a.specialdaysName = ?3");
		query.setParameter(1, id);
		query.setParameter(2, isspecial);
		query.setParameter(3, name);
    	return (RateSpecialDays) query.getSingleResult();
		}
		catch(Exception ex){
			return null;
		}	
    }
	public static int deleteByRateMetaId(Long id,double value) {
	Query query = JPA.em().createQuery("delete from RateSpecialDays p where p.rate.id = ?1 and p.isSpecialdaysRate = ?2");
	query.setParameter(1, id);
	query.setParameter(2, value);
	return query.executeUpdate();
	}
	
	public static int deleteByRateId(Long id) {
		Query query = JPA.em().createQuery("delete from RateSpecialDays p where p.rate.id = ?1");
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
