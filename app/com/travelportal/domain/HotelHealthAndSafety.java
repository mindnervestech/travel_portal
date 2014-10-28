package com.travelportal.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="Hotel_health_and_safety")
public class HotelHealthAndSafety {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="public_liability")
	private String publicLiability;
	@Column(name="fire_risk")
	private String fireRisk;
	@Column(name="local_tourist")
	private String localTourist;
	@Column(name="internal_fire")
	private String internalFire;
	@Column(name="haccp_certify")
	private String haccpCertify;
	@Column(name="records_for_fire")
	private String recordsForFire;
	@Column(name="records_for_health")
	private String recordsForHealth;
	@Column(name="expiry_date")
	private Date expiryDate;
	@Column(name="expiry_date1")
	private Date expiryDate1;
	@Column(name="supplier_code")
	private long supplierCode;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPublicLiability() {
		return publicLiability;
	}
	public void setPublicLiability(String publicLiability) {
		this.publicLiability = publicLiability;
	}
	public String getFireRisk() {
		return fireRisk;
	}
	public void setFireRisk(String fireRisk) {
		this.fireRisk = fireRisk;
	}
	public String getLocalTourist() {
		return localTourist;
	}
	public void setLocalTourist(String localTourist) {
		this.localTourist = localTourist;
	}
	public String getInternalFire() {
		return internalFire;
	}
	public void setInternalFire(String internalFire) {
		this.internalFire = internalFire;
	}
	public String getHaccpCertify() {
		return haccpCertify;
	}
	public void setHaccpCertify(String haccpCertify) {
		this.haccpCertify = haccpCertify;
	}
	public String getRecordsForFire() {
		return recordsForFire;
	}
	public void setRecordsForFire(String recordsForFire) {
		this.recordsForFire = recordsForFire;
	}
	public String getRecordsForHealth() {
		return recordsForHealth;
	}
	public void setRecordsForHealth(String recordsForHealth) {
		this.recordsForHealth = recordsForHealth;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Date getExpiryDate1() {
		return expiryDate1;
	}
	public void setExpiryDate1(Date expiryDate1) {
		this.expiryDate1 = expiryDate1;
	}
	public long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public static HotelHealthAndSafety findById(Long supplierCode) {
		try
		{
		return (HotelHealthAndSafety) JPA.em().createQuery("select c from HotelHealthAndSafety c where c.supplierCode = ?1").setParameter(1, supplierCode).getSingleResult();
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
	
	
	
	
	
	/*public static HotelHealthAndSafety getHotelBrandsbyCode(int code) {
		
		return (HotelHealthAndSafety) JPA.em().createQuery("select c from HotelHealthAndSafety c where c.brandsCode = ?1").setParameter(1, code).getSingleResult();
	}*/
	
}
