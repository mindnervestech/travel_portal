package com.travelportal.domain.rooms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.allotment.AllotmentMarket;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="specialsmarket")
public class SpecialsMarket {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String stayDays;
	private String payDays;
	private String typeOfStay;
	public String earlyBird;
	public String earlyBirdDisount;
	public String earlyBirdRateCalculat;
	public Double flatRate;
	private boolean multiple;
	private boolean combined;
	public boolean breakfast;
	public String adultRate;
	public String childRate;
	private String applyToMarket;
	@OneToOne
	private Specials special;
	/*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<City> cities;*/
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Country> country;
	
	
	
	
	public List<Country> getCountry() {
		return country;
	}
	public void setCountry(List<Country> country) {
		this.country = country;
	}
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
	
	
	
		
	public boolean isBreakfast() {
		return breakfast;
	}
	public void setBreakfast(boolean breakfast) {
		this.breakfast = breakfast;
	}
	public String getAdultRate() {
		return adultRate;
	}
	public void setAdultRate(String adultRate) {
		this.adultRate = adultRate;
	}
	public String getChildRate() {
		return childRate;
	}
	public void setChildRate(String childRate) {
		this.childRate = childRate;
	}
	
	public String getEarlyBird() {
		return earlyBird;
	}
	public void setEarlyBird(String earlyBird) {
		this.earlyBird = earlyBird;
	}
	public String getEarlyBirdDisount() {
		return earlyBirdDisount;
	}
	public void setEarlyBirdDisount(String earlyBirdDisount) {
		this.earlyBirdDisount = earlyBirdDisount;
	}
	public String getEarlyBirdRateCalculat() {
		return earlyBirdRateCalculat;
	}
	public void setEarlyBirdRateCalculat(String earlyBirdRateCalculat) {
		this.earlyBirdRateCalculat = earlyBirdRateCalculat;
	}
	
	
	
	
	public Double getFlatRate() {
		return flatRate;
	}
	public void setFlatRate(Double flatRate) {
		this.flatRate = flatRate;
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
	
	
	
	public static List<SpecialsMarket> findBySpecialsIdnationality(Long specialId,int nation) { 
		
		 List<Object[]> list;
		
		list =JPA.em().createNativeQuery("select * from specialsmarket spm,specialsmarket_country spmc where spm.id = spmc.SpecialsMarket_id and spm.special_id ='"+specialId+"' and spmc.country_country_code = '"+nation+"'").getResultList();   
		
	 List<SpecialsMarket> list1 = new ArrayList<>();
	
		for(Object[] o :list) {
			
			SpecialsMarket spm = new SpecialsMarket();
			
			spm.setId(Long.parseLong(o[0].toString()));
			spm.setCombined(Boolean.parseBoolean(o[1].toString()));
			spm.setMultiple(Boolean.parseBoolean(o[2].toString()));
			if(o[3] != null){	
				spm.setPayDays(o[3].toString());
			}	
			if(o[4] != null){
				spm.setStayDays(o[4].toString());
			}	
			if(o[5] != null){
				spm.setTypeOfStay(o[5].toString());
			}	
			spm.setApplyToMarket(o[7].toString());
			if(o[8] != null){
				spm.setAdultRate(o[8].toString());
			}
			spm.setBreakfast(Boolean.parseBoolean(o[9].toString()));
			if(o[10] != null){
				spm.setChildRate(o[10].toString());
			}	
			if(o[11] != null){	
				spm.setEarlyBird(o[11].toString());
			}	
			if(o[12] != null){
				spm.setEarlyBirdDisount(o[12].toString());
			}	
			if(o[13] != null){	
				spm.setEarlyBirdRateCalculat(o[13].toString());
			}	
			if(o[14] != null){
				spm.setFlatRate(Double.valueOf(o[14].toString()));
			}	
			list1.add(spm);
		}
		
		return list1;
		
	 }
	
	
	public static SpecialsMarket findById(Long id) {
    	Query query = JPA.em().createQuery("Select s from SpecialsMarket s where s.id = ?1");
		query.setParameter(1, id);
    	return (SpecialsMarket) query.getSingleResult();
    }
	
	public static int deletespecialCountry(long code) {
		Query q = JPA.em().createNativeQuery("delete from specialsmarket_country where SpecialsMarket_id = '"+code+"'");
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
