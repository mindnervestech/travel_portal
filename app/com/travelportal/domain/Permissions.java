package com.travelportal.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;

@Entity
@Table(name="permissions")
public class Permissions {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="name")
	private String name;
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static List<Permissions> getPermission() {
		return JPA.em().createQuery("select c from Permissions c ").getResultList();
	}
	public static List<Permissions> getpp(List<Integer> id){
		return JPA.em().createQuery("select c from Permissions c where c.id IN ?1").setParameter(1, id).getResultList();
	}
	
   public static Permissions getp(int id) {
		
		return (Permissions) JPA.em().createQuery("select c from Permissions c where id = ?1"). setParameter(1,id).getSingleResult();
	}
	
	
	/*Query query = JPA.em().createQuery("Select a from HotelProfile a where a.supplier_code = ?1");
	query.setParameter(1, id);*/
		
}
