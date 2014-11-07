package com.travelportal.domain.rooms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
