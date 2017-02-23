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
@Table(name="room_regiter_by_child")
public class RoomRegiterByChild {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="age")
	private int age;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="breakfast")
	private String breakfast;
	@Column(name="child_rate")
	private double child_rate;
	@Column(name="free_child")
	private String free_child;
	@OneToOne
	private RoomRegiterBy roomRegiterBy;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public double getChild_rate() {
		return child_rate;
	}

	public void setChild_rate(double child_rate) {
		this.child_rate = child_rate;
	}

	public String getFree_child() {
		return free_child;
	}

	public void setFree_child(String free_child) {
		this.free_child = free_child;
	}

	public RoomRegiterBy getRoomRegiterBy() {
		return roomRegiterBy;
	}

	public void setRoomRegiterBy(RoomRegiterBy roomRegiterBy) {
		this.roomRegiterBy = roomRegiterBy;
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

	public static RoomRegiterByChild getRoomInfoById(int code) {
		return (RoomRegiterByChild) JPA.em().createQuery("select c from RoomRegiterByChild c where c.id = ?1").setParameter(1, code).getSingleResult();
	}
	
	public static List<RoomRegiterByChild> getRoomChildInfoByRoomId(int code) {
		try{
		return (List<RoomRegiterByChild>) JPA.em().createQuery("select c from RoomRegiterByChild c where c.roomRegiterBy.id = ?1").setParameter(1, code).getResultList();
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
