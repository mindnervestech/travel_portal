package com.travelportal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NoResultException;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="internal_contacts")
public class InternalContacts {
	/*@JoinColumn(name="supplier_code")
	@OneToOne
	private SupplierCode supplier_code;*/
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int guestTelCityCode;
	private int guestTelValue;
	private int guestFaxCityCode;
	private int guestFaxValue;
	private int directTelCityCode;
	private int directTelValue;
	private int directFaxCityCode;
	private int directFaxValue;
	private long supplier_code;
	
	public long getSupplierCode() {
		return supplier_code;
	}
	
	public void setSupplierCode(long supplier_code) {
		this.supplier_code = supplier_code;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the guestTelCityCode
	 */
	public int getGuestTelCityCode() {
		return guestTelCityCode;
	}
	/**
	 * @param guestTelCityCode the guestTelCityCode to set
	 */
	public void setGuestTelCityCode(int guestTelCityCode) {
		this.guestTelCityCode = guestTelCityCode;
	}
	/**
	 * @return the guestTelValue
	 */
	public int getGuestTelValue() {
		return guestTelValue;
	}
	/**
	 * @param guestTelValue the guestTelValue to set
	 */
	public void setGuestTelValue(int guestTelValue) {
		this.guestTelValue = guestTelValue;
	}
	/**
	 * @return the guestFaxCityCode
	 */
	public int getGuestFaxCityCode() {
		return guestFaxCityCode;
	}
	/**
	 * @param guestFaxCityCode the guestFaxCityCode to set
	 */
	public void setGuestFaxCityCode(int guestFaxCityCode) {
		this.guestFaxCityCode = guestFaxCityCode;
	}
	/**
	 * @return the guestFaxValue
	 */
	public int getGuestFaxValue() {
		return guestFaxValue;
	}
	/**
	 * @param guestFaxValue the guestFaxValue to set
	 */
	public void setGuestFaxValue(int guestFaxValue) {
		this.guestFaxValue = guestFaxValue;
	}
	/**
	 * @return the directTelCityCode
	 */
	public int getDirectTelCityCode() {
		return directTelCityCode;
	}
	/**
	 * @param directTelCityCode the directTelCityCode to set
	 */
	public void setDirectTelCityCode(int directTelCityCode) {
		this.directTelCityCode = directTelCityCode;
	}
	/**
	 * @return the directTelValue
	 */
	public int getDirectTelValue() {
		return directTelValue;
	}
	/**
	 * @param directTelValue the directTelValue to set
	 */
	public void setDirectTelValue(int directTelValue) {
		this.directTelValue = directTelValue;
	}
	/**
	 * @return the directFaxCityCode
	 */
	public int getDirectFaxCityCode() {
		return directFaxCityCode;
	}
	/**
	 * @param directFaxCityCode the directFaxCityCode to set
	 */
	public void setDirectFaxCityCode(int directFaxCityCode) {
		this.directFaxCityCode = directFaxCityCode;
	}
	/**
	 * @return the directFaxValue
	 */
	public int getDirectFaxValue() {
		return directFaxValue;
	}
	/**
	 * @param directFaxValue the directFaxValue to set
	 */
	public void setDirectFaxValue(int directFaxValue) {
		this.directFaxValue = directFaxValue;
	}
	
	/*public static InternalContacts findById(Long id) {
    	
    	try
		{
		return (InternalContacts) JPA.em().createQuery("select c from InternalContacts c where id = ?1").setParameter(1, id).getSingleResult();
		}
		catch(NoResultException ex){
			return null;
		}
    }*/
	
		
	
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
