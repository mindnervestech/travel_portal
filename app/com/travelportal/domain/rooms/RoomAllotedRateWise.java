package com.travelportal.domain.rooms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import com.travelportal.domain.Country;
import com.travelportal.domain.allotment.AllotmentMarket;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="room_alloted_rate_wise")
public class RoomAllotedRateWise {
	
	@Column(name="id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="alloted_rate_date")
	private Date allowedRateDate;
	@Column(name="room_count")
	private int roomCount;
	@OneToOne
	private RateMeta rate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getAllowedRateDate() {
		return allowedRateDate;
	}

	public void setAllowedRateDate(Date allowedRateDate) {
		this.allowedRateDate = allowedRateDate;
	}

	public int getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(int roomCount) {
		this.roomCount = roomCount;
	}
	

	public RateMeta getRate() {
		return rate;
	}

	public void setRate(RateMeta rate) {
		this.rate = rate;
	}
	

	/*public static List<RoomAllotedRateWise> findAllDate() {
		Query query = JPA.em().createQuery("Select c from RoomAllotedRateWise");
    	return (List<RoomAllotedRateWise>) query.getResultList();
	}*/
	
	public static List<RoomAllotedRateWise> findAllDate() {
		return JPA.em().createQuery("select c from RoomAllotedRateWise c").getResultList();
	}
	
	public static RoomAllotedRateWise findByRateId(Long id) {
		Query query = JPA.em().createQuery("Select c from RoomAllotedRateWise c where c.rate.id = ?1");
		query.setParameter(1, id);
    	return (RoomAllotedRateWise) query.getSingleResult();
	}
	
	public static RoomAllotedRateWise findByRateIdandDate(Long id,Date date) {
		
		try
		{
		Query query = JPA.em().createQuery("Select c from RoomAllotedRateWise c where c.rate.id = ?1 and c.allowedRateDate = ?2");
		query.setParameter(1, id);
		query.setParameter(2, date);
    	return (RoomAllotedRateWise) query.getSingleResult();
	}
	catch(Exception ex){
		return null;
	}
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
