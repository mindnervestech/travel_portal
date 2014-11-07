package com.travelportal.domain.rooms;

import java.util.Date;

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

@Entity
@Table(name="rate_meta")
public class RateMeta {

	@Column(name="rate_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="rate_name")
	private String rateName;
	@OneToOne
	private HotelRoomTypes roomType;
	@Column(name="from_date")
	private Date fromDate;
	@Column(name="to_date")
	private Date toDate;
	@Column(name="currency")
	private String currency;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRateName() {
		return rateName;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	public HotelRoomTypes getRoomType() {
		return roomType;
	}
	public void setRoomType(HotelRoomTypes roomType) {
		this.roomType = roomType;
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
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	 public static RateMeta findRateMeta(String rateName, String currency, Date fromDate,Date toDate, HotelRoomTypes roomType) {
	    	Query query = JPA.em().createQuery("Select r from RateMeta r where r.rateName = ?1 and r.currency = ?2 and r.fromDate = ?3 and r.toDate = ?4 and r.roomType = ?5");
			query.setParameter(1, rateName);
			query.setParameter(2, currency);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(5, roomType);
	    	return (RateMeta) query.getSingleResult();
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
