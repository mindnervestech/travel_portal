package com.travelportal.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="room_and_date_wise_rate")
public class RoomAndDateWiseRate {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="currency")
	private String currency;
	@Column(name="date")
	private String date;
	@Column(name="day")
	private String day;
	@Column(name="fulldate")
	private String fulldate;
	@Column(name="meal")
	private String meal;
	@Column(name="month")
	private String month;
	@Column(name="rate")
	private double rate;
	@OneToOne
	private RoomRegiterBy roomRegiterBy;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getFulldate() {
		return fulldate;
	}

	public void setFulldate(String fulldate) {
		this.fulldate = fulldate;
	}

	public String getMeal() {
		return meal;
	}

	public void setMeal(String meal) {
		this.meal = meal;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public RoomRegiterBy getRoomRegiterBy() {
		return roomRegiterBy;
	}

	public void setRoomRegiterBy(RoomRegiterBy roomRegiterBy) {
		this.roomRegiterBy = roomRegiterBy;
	}

	public static RoomAndDateWiseRate getRoomRateInfoById(int code) {
		return (RoomAndDateWiseRate) JPA.em().createQuery("select c from RoomAndDateWiseRate c where c.id = ?1").setParameter(1, code).getSingleResult();
	}
	
	public static List<RoomAndDateWiseRate> getRoomRateInfoByRoomId(int code) {
		try{
		return (List<RoomAndDateWiseRate>) JPA.em().createQuery("select c from RoomAndDateWiseRate c where c.roomRegiterBy.id = ?1").setParameter(1, code).getResultList();
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
