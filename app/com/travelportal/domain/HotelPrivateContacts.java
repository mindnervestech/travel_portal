package com.travelportal.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="hotel_private_contacts")
public class HotelPrivateContacts {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int contactId;
	private String mainContactPersonName;
	private String mainContactPersonTitle;
	@ManyToOne
	private Salutation salutation;	
	private String mainContactTelNo;
	private String mainContactFaxNo; //no. will be stored in format of cityCode-no, at client side while displaying split by '-'
	private int mainContactExt;
	private String mainContactEmailAddr;
	private String tollFreeNo;
	private boolean reservationSameAsMainContact; //this flag is for whether the main contact and reservation contact are same
	
	private String reservationContactPersonName;
	private String reservationContactPersonTitle;
	private String reservationContactTelNo;
	private String deptTelNo; 
	private String deptFaxNo; //no. will be stored in format of cityCode-no, at client side while displaying split by '-'
	private String deptExtNo;
	private int reservationContactExt;
	private String reservationContactEmailAddr;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Salutation salutation_salutation_id;
    private long supplier_code;
    
    
	
	public long getSupplierCode() {
		return supplier_code;
	}
	
	public void setSupplierCode(long supplier_code) {
		this.supplier_code = supplier_code;
	}
	
	
	/**
	 * @return the contactId
	 */
	public int getContactId() {
		return contactId;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	/**
	 * @return the mainContactPersonName
	 */
	public String getMainContactPersonName() {
		return mainContactPersonName;
	}
	/**
	 * @param mainContactPersonName the mainContactPersonName to set
	 */
	public void setMainContactPersonName(String mainContactPersonName) {
		this.mainContactPersonName = mainContactPersonName;
	}
	/**
	 * @return the mainContactPersonTitle
	 */
	public String getMainContactPersonTitle() {
		return mainContactPersonTitle;
	}
	/**
	 * @param mainContactPersonTitle the mainContactPersonTitle to set
	 */
	public void setMainContactPersonTitle(String mainContactPersonTitle) {
		this.mainContactPersonTitle = mainContactPersonTitle;
	}
	/**
	 * @return the salutation
	 */
	public Salutation getSalutation() {
		return salutation;
	}
	/**
	 * @param salutation the salutation to set
	 */
	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
	}
	/**
	 * @return the mainContactTelNo
	 */
	public String getMainContactTelNo() {
		return mainContactTelNo;
	}
	/**
	 * @param mainContactTelNo the mainContactTelNo to set
	 */
	public void setMainContactTelNo(String mainContactTelNo) {
		this.mainContactTelNo = mainContactTelNo;
	}
	/**
	 * @return the mainContactFaxNo
	 */
	public String getMainContactFaxNo() {
		return mainContactFaxNo;
	}
	/**
	 * @param mainContactFaxNo the mainContactFaxNo to set
	 */
	public void setMainContactFaxNo(String mainContactFaxNo) {
		this.mainContactFaxNo = mainContactFaxNo;
	}
	/**
	 * @return the mainContactExt
	 */
	public int getMainContactExt() {
		return mainContactExt;
	}
	/**
	 * @param mainContactExt the mainContactExt to set
	 */
	public void setMainContactExt(int mainContactExt) {
		this.mainContactExt = mainContactExt;
	}
	/**
	 * @return the mainContactEmailAddr
	 */
	public String getMainContactEmailAddr() {
		return mainContactEmailAddr;
	}
	/**
	 * @param mainContactEmailAddr the mainContactEmailAddr to set
	 */
	public void setMainContactEmailAddr(String mainContactEmailAddr) {
		this.mainContactEmailAddr = mainContactEmailAddr;
	}
	/**
	 * @return the tollFreeNo
	 */
	public String getTollFreeNo() {
		return tollFreeNo;
	}
	/**
	 * @param tollFreeNo the tollFreeNo to set
	 */
	public void setTollFreeNo(String tollFreeNo) {
		this.tollFreeNo = tollFreeNo;
	}
	/**
	 * @return the reservationSameAsMainContact
	 */
	public boolean isReservationSameAsMainContact() {
		return reservationSameAsMainContact;
	}
	/**
	 * @param reservationSameAsMainContact the reservationSameAsMainContact to set
	 */
	public void setReservationSameAsMainContact(boolean reservationSameAsMainContact) {
		this.reservationSameAsMainContact = reservationSameAsMainContact;
	}
	/**
	 * @return the reservationContactPersonName
	 */
	public String getReservationContactPersonName() {
		return reservationContactPersonName;
	}
	/**
	 * @param reservationContactPersonName the reservationContactPersonName to set
	 */
	public void setReservationContactPersonName(String reservationContactPersonName) {
		this.reservationContactPersonName = reservationContactPersonName;
	}
	/**
	 * @return the reservationContactPersonTitle
	 */
	public String getReservationContactPersonTitle() {
		return reservationContactPersonTitle;
	}
	/**
	 * @param reservationContactPersonTitle the reservationContactPersonTitle to set
	 */
	public void setReservationContactPersonTitle(
			String reservationContactPersonTitle) {
		this.reservationContactPersonTitle = reservationContactPersonTitle;
	}
	/**
	 * @return the reservationContactTelNo
	 */
	public String getReservationContactTelNo() {
		return reservationContactTelNo;
	}
	/**
	 * @param reservationContactTelNo the reservationContactTelNo to set
	 */
	public void setReservationContactTelNo(String reservationContactTelNo) {
		this.reservationContactTelNo = reservationContactTelNo;
	}
	/**
	 * @return the deptTelNo
	 */
	public String getDeptTelNo() {
		return deptTelNo;
	}
	/**
	 * @param deptTelNo the deptTelNo to set
	 */
	public void setDeptTelNo(String deptTelNo) {
		this.deptTelNo = deptTelNo;
	}
	/**
	 * @return the deptFaxNo
	 */
	public String getDeptFaxNo() {
		return deptFaxNo;
	}
	/**
	 * @param deptFaxNo the deptFaxNo to set
	 */
	public void setDeptFaxNo(String deptFaxNo) {
		this.deptFaxNo = deptFaxNo;
	}
	/**
	 * @return the deptExtNo
	 */
	public String getDeptExtNo() {
		return deptExtNo;
	}
	/**
	 * @param deptExtNo the deptExtNo to set
	 */
	public void setDeptExtNo(String deptExtNo) {
		this.deptExtNo = deptExtNo;
	}
	/**
	 * @return the reservationContactExt
	 */
	public int getReservationContactExt() {
		return reservationContactExt;
	}
	/**
	 * @param reservationContactExt the reservationContactExt to set
	 */
	public void setReservationContactExt(int reservationContactExt) {
		this.reservationContactExt = reservationContactExt;
	}
	/**
	 * @return the reservationContactEmailAddr
	 */
	public String getReservationContactEmailAddr() {
		return reservationContactEmailAddr;
	}
	/**
	 * @param reservationContactEmailAddr the reservationContactEmailAddr to set
	 */
	public void setReservationContactEmailAddr(String reservationContactEmailAddr) {
		this.reservationContactEmailAddr = reservationContactEmailAddr;
	}
	
	public Salutation getSalutation_salutation_id() {
		return salutation_salutation_id;
	}
	
	public void setSalutation_salutation_id(Salutation salutation_salutation_id) {
		this.salutation_salutation_id = salutation_salutation_id;
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
