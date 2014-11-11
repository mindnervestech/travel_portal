package com.travelportal.domain.rooms;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import com.travelportal.domain.MealType;

@Entity
@Table(name="person_rate")
public class PersonRate {

	@Column(name="person_rate_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="number_of_persons")
	private String numberOfPersons;
	@Column(name="rate_value")
	private double rateValue;
	@Column(name="is_meal")
	private boolean isMeal;
	@OneToOne
	private MealType mealType;
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
	public String getNumberOfPersons() {
		return numberOfPersons;
	}
	public void setNumberOfPersons(String numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}
	public double getRateValue() {
		return rateValue;
	}
	public void setRateValue(double rateValue) {
		this.rateValue = rateValue;
	}
	public boolean isMeal() {
		return isMeal;
	}
	public void setMeal(boolean isMeal) {
		this.isMeal = isMeal;
	}
	public MealType getMealType() {
		return mealType;
	}
	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}
	public RateMeta getRate() {
		return rate;
	}
	public void setRate(RateMeta rate) {
		this.rate = rate;
	}
	
	public static List<PersonRate> findByRateMetaId(Long id) {
    	Query query = JPA.em().createQuery("Select p from PersonRate p where p.rate.id = ?1");
		query.setParameter(1, id);
    	return (List<PersonRate>) query.getResultList();
    }
	
	public static PersonRate findByRateMetaIdAndNormal(Long id,boolean isNormal,String numberOfPersons) {
    	Query query = JPA.em().createQuery("Select p from PersonRate p where p.rate.id = ?1 and p.isNormal = ?2 and p.numberOfPersons = ?3");
		query.setParameter(1, id);
		query.setParameter(2, isNormal);
		query.setParameter(3, numberOfPersons);
    	return (PersonRate) query.getSingleResult();
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