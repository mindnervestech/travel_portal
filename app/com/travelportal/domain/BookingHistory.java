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
@Table(name="Booking_history")
public class BookingHistory {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="json_data")
	private String jsonData;
	@Column(name="change_date")
	private String changeDate;
	@OneToOne
	private HotelBookingDetails hotelBookingDetails;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public HotelBookingDetails getHotelBookingDetails() {
		return hotelBookingDetails;
	}

	public void setHotelBookingDetails(HotelBookingDetails hotelBookingDetails) {
		this.hotelBookingDetails = hotelBookingDetails;
	}

	public static BookingHistory gethistoryById(int code) {
		return (BookingHistory) JPA.em().createQuery("select c from BookingHistory c where c.id = ?1").setParameter(1, code).getSingleResult();
	}
	
	public static List<BookingHistory> getbookinghistory(int code) {
		try{
		return (List<BookingHistory>) JPA.em().createQuery("select c from BookingHistory c where c.hotelBookingDetails.id = ?1").setParameter(1, code).getResultList();
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
