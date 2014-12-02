package com.travelportal.domain.rooms;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;

import com.travelportal.domain.City;
import com.travelportal.domain.allotment.AllotmentMarket;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
public class SpecialsMarket {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String stayDays;
	private String payDays;
	private String typeOfStay;
	private boolean multiple;
	private boolean combined;
	private String applyToMarket;
	@OneToOne
	private Specials special;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<City> cities;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStayDays() {
		return stayDays;
	}
	public void setStayDays(String stayDays) {
		this.stayDays = stayDays;
	}
	public String getPayDays() {
		return payDays;
	}
	public void setPayDays(String payDays) {
		this.payDays = payDays;
	}
	public String getTypeOfStay() {
		return typeOfStay;
	}
	public void setTypeOfStay(String typeOfStay) {
		this.typeOfStay = typeOfStay;
	}
	public boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	public boolean isCombined() {
		return combined;
	}
	public void setCombined(boolean combined) {
		this.combined = combined;
	}
	public Specials getSpecial() {
		return special;
	}
	public void setSpecial(Specials special) {
		this.special = special;
	}
	
	public String getApplyToMarket() {
		return applyToMarket;
	}
	public void setApplyToMarket(String applyToMarket) {
		this.applyToMarket = applyToMarket;
	}
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	public static SpecialsMarket findByIdCity(long Code) {
		try
		{
			Query query = JPA.em().createQuery("Select s from SpecialsMarket s where s.id = ?1");
			query.setParameter(1, Code);
	    	return (SpecialsMarket) query.getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
    }
	
	public static SpecialsMarket findByTopid() {
		try
		{
		return (SpecialsMarket) JPA.em().createQuery("select c from SpecialsMarket c where c.id = (select max(a.id) from SpecialsMarket a)").getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
    }
	
	public static int deleteMarketSp(Long id) {
    	Query query = JPA.em().createQuery("delete from SpecialsMarket p where p.id = ?1");
		query.setParameter(1, id);
    	return query.executeUpdate();
    }
	
	public static int deleteSp(Long id) {
    	Query query = JPA.em().createQuery("delete from SpecialsMarket p where p.special.id = ?1");
		query.setParameter(1, id);
    	return query.executeUpdate();
    }
	
	public static List<SpecialsMarket> findBySpecialsId(Long id) {
    	Query query = JPA.em().createQuery("Select s from SpecialsMarket s where s.special.id = ?1");
		query.setParameter(1, id);
    	return (List<SpecialsMarket>) query.getResultList();
    }
	
	public static SpecialsMarket findById(Long id) {
    	Query query = JPA.em().createQuery("Select s from SpecialsMarket s where s.id = ?1");
		query.setParameter(1, id);
    	return (SpecialsMarket) query.getSingleResult();
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
