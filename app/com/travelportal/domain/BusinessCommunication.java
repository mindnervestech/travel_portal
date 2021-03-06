package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="business_communication")
public class BusinessCommunication {
	
	@Column(name="id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="primary_email_addr")
	private String primaryEmailAddr;
	@Column(name="cc_email_addr")
	private String ccEmailAddr;
	private String booking;
	private long supplier_code;
	
	
	public long getSupplierCode() {
		return supplier_code;
	}
	
	public void setSupplierCode(long supplier_code) {
		this.supplier_code = supplier_code;
	}
	
	public String getbooking() {
		return booking;
	}
	/**
	 * @param primaryEmailAddr the primaryEmailAddr to set
	 */
	public void setbooking(String booking) {
		this.booking = booking;
	}
	/**
	 * @return the primaryEmailAddr
	 */
	public String getPrimaryEmailAddr() {
		return primaryEmailAddr;
	}
	/**
	 * @param primaryEmailAddr the primaryEmailAddr to set
	 */
	public void setPrimaryEmailAddr(String primaryEmailAddr) {
		this.primaryEmailAddr = primaryEmailAddr;
	}
	/**
	 * @return the ccEmailAddr
	 */
	public String getCcEmailAddr() {
		return ccEmailAddr;
	}
	/**
	 * @param ccEmailAddr the ccEmailAddr to set
	 */
	public void setCcEmailAddr(String ccEmailAddr) {
		this.ccEmailAddr = ccEmailAddr;
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
	
public static BusinessCommunication findById(long id) {
    	
    	try
		{
		return (BusinessCommunication) JPA.em().createQuery("select c from BusinessCommunication c where c.supplier_code = ?1").setParameter(1, id).getSingleResult();
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
