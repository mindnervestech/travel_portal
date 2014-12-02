package com.travelportal.domain.rooms;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Query;

import com.travelportal.domain.City;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
public class Specials {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date fromDate;
	private Date toDate;
	private String promotionName;
	private Long supplierCode;
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<HotelRoomTypes> roomTypes;
	/*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<City> cities;*/
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	public List<HotelRoomTypes> getRoomTypes() {
		return roomTypes;
	}
	public void setRoomTypes(List<HotelRoomTypes> roomTypes) {
		this.roomTypes = roomTypes;
	}
		
	public Long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public static Specials findBySpecialsID(Long id) {
    	Query query = JPA.em().createQuery("Select a from Specials a where a.id = ?1");
		query.setParameter(1, id);
    	return (Specials) query.getSingleResult();
    }
	
	public static List<Specials> getperiod(Long code) {
		Query q = JPA.em().createQuery("select r from Specials r where r.supplierCode = ?1");
				q.setParameter(1, code);
		return (List<Specials>) q.getResultList();
	}
	
	public static Specials findSpecial(String promotionName, Date fromDate,Date toDate) {
    	Query query = JPA.em().createQuery("Select s from Specials s where s.promotionName = ?1 and s.fromDate = ?2 and s.toDate = ?3");
		query.setParameter(1, promotionName);
		query.setParameter(2, fromDate);
		query.setParameter(3, toDate);
    	return (Specials) query.getSingleResult();
    }
	
	public static List<Specials> findSpecialByDate(Date fromDate,Date toDate,String promotionName) {
    	Query query = JPA.em().createQuery("Select s from Specials s where s.fromDate = ?1 and s.toDate = ?2 and s.promotionName = ?3");
		query.setParameter(1, fromDate);
		query.setParameter(2, toDate);
		query.setParameter(3,promotionName);
    	return (List<Specials>) query.getResultList();
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
