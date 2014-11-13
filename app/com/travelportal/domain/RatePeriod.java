package com.travelportal.domain;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import com.travelportal.domain.rooms.ChildPolicies;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RateMeta;
import com.travelportal.domain.rooms.RoomChildPolicies;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="rate_period")
public class RatePeriod { //supplier specific records...
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	private Date fromPeriod;
	@Column(name="to_period")
	private Date toPeriod;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Currency currency;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private HotelRoomTypes roomtype;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<RateMeta> rate;
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFromPeriod() {
		return fromPeriod;
	}

	public void setFromPeriod(Date fromPeriod) {
		this.fromPeriod = fromPeriod;
	}

	public Date getToPeriod() {
		return toPeriod;
	}

	public void setToPeriod(Date toPeriod) {
		this.toPeriod = toPeriod;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public HotelRoomTypes getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(HotelRoomTypes roomtype) {
		this.roomtype = roomtype;
	}
	
	public List<RateMeta> getRate() {
		return rate;
	}

	public void setRate(List<RateMeta> rate) {
		this.rate = rate;
	}

	public static List<RatePeriod> getDates(long roomid,int currencyid) {
		Query q = JPA.em().createQuery("select c from RatePeriod c where c.roomtype.roomId = :roomid and c.currency.id = :currencyid");
		q.setParameter("roomid", roomid);
		q.setParameter("currencyid", currencyid);
		return q.getResultList();
	}

	public static List<RatePeriod> getRates(int Id) {
		return JPA.em().createQuery("select c from RatePeriod c where c.id = ?1").setParameter(1, Id).getResultList();
	}
	
	 public static RatePeriod findById(int id) {
	    	Query query = JPA.em().createQuery("Select a from RatePeriod a where a.id = ?1");
			query.setParameter(1, id);
	    	return (RatePeriod) query.getSingleResult();
	    }
	
	
	
	
	
	
	
	/*public static List<HotelMealPlan> getmealtype(long supplierCode) {
		return JPA.em().createQuery("select c from HotelMealPlan c where c.SupplierCode = ?1").setParameter(1, supplierCode).getResultList();
	}
	
	//findById
	 public static HotelMealPlan findById(int id) {
	    	Query query = JPA.em().createQuery("Select a from HotelMealPlan a where a.id = ?1");
			query.setParameter(1, id);
	    	return (HotelMealPlan) query.getSingleResult();
	    }
	 
	 
	
	public static HotelMealPlan getHotelMealPlanIdByCode(int code) {
		return (HotelMealPlan) JPA.em().createQuery("select c from HotelMealPlan c where id = ?1").setParameter(1, code).getSingleResult();
	}
		
	*/
	

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
