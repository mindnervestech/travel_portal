package com.travelportal.domain;

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
@Table(name="room_regiter_by")
public class RoomRegiterBy {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="room_index")
	private int roomIndex;
	@Column(name="adult")
	private String adult;
	@Column(name="no_of_child")
	private int noOfchild;
	@Column(name="regiter_by")
	private String regiterBy;
	@OneToOne
	private HotelBookingDetails hotelBookingDetails;
	
	
	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public int getRoomIndex() {
		return roomIndex;
	}





	public void setRoomIndex(int roomIndex) {
		this.roomIndex = roomIndex;
	}





	public String getAdult() {
		return adult;
	}





	public void setAdult(String adult) {
		this.adult = adult;
	}





	public int getNoOfchild() {
		return noOfchild;
	}





	public void setNoOfchild(int noOfchild) {
		this.noOfchild = noOfchild;
	}





	public String getRegiterBy() {
		return regiterBy;
	}





	public void setRegiterBy(String regiterBy) {
		this.regiterBy = regiterBy;
	}



	public HotelBookingDetails getHotelBookingDetails() {
		return hotelBookingDetails;
	}





	public void setHotelBookingDetails(HotelBookingDetails hotelBookingDetails) {
		this.hotelBookingDetails = hotelBookingDetails;
	}





	public static RoomRegiterBy getRoomInfoById(int code) {
		return (RoomRegiterBy) JPA.em().createQuery("select c from RoomRegiterBy c where c.id = ?1").setParameter(1, code).getSingleResult();
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
