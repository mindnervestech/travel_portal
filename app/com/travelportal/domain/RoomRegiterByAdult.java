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
@Table(name="room_regiter_by_adult")
public class RoomRegiterByAdult {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@OneToOne
	private RoomRegiterBy roomRegiterBy;
	


	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public RoomRegiterBy getRoomRegiterBy() {
		return roomRegiterBy;
	}

	public void setRoomRegiterBy(RoomRegiterBy roomRegiterBy) {
		this.roomRegiterBy = roomRegiterBy;
	}
	
	public static List<RoomRegiterByAdult> getRoomAdultInfoByRoomId(int code) {
		try{
		return (List<RoomRegiterByAdult>) JPA.em().createQuery("select c from RoomRegiterByAdult c where c.roomRegiterBy.id = ?1").setParameter(1, code).getResultList();
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
