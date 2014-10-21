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
	private int mainContactTelNo;
	private int mainContactTelCode;
	private int mainContactFaxNo;
	private int mainContactFaxCode;
	private int mainContactExt;
	private String mainContactEmailAddr;
	private String tollFreeNo;
	private boolean reservationSameAsMainContact; //this flag is for whether the main contact and reservation contact are same
	
	private String reservationContactPersonName;
	private String reservationContactPersonTitle;
	private int reservationContactTelNo;
	private int reservationContactTelCode;
	private int deptTelNo;
	private int deptTelCode; 
	private int deptFaxNo;
	private int deptFaxCode;//no. will be stored in format of cityCode-no, at client side while displaying split by '-'
	private int deptExtNo;
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
	
	
		
	public int getReservationContactTelCode() {
		return reservationContactTelCode;
	}

	public void setReservationContactTelCode(int reservationContactTelCode) {
		this.reservationContactTelCode = reservationContactTelCode;
	}

	public int getMainContactTelCode() {
		return mainContactTelCode;
	}

	public void setMainContactTelCode(int mainContactTelCode) {
		this.mainContactTelCode = mainContactTelCode;
	}

	public int getMainContactFaxCode() {
		return mainContactFaxCode;
	}

	public void setMainContactFaxCode(int mainContactFaxCode) {
		this.mainContactFaxCode = mainContactFaxCode;
	}

	public int getDeptTelCode() {
		return deptTelCode;
	}

	public void setDeptTelCode(int deptTelCode) {
		this.deptTelCode = deptTelCode;
	}

	public int getDeptFaxCode() {
		return deptFaxCode;
	}

	public void setDeptFaxCode(int deptFaxCode) {
		this.deptFaxCode = deptFaxCode;
	}

	/**
	 * @return the mainContactTelNo
	 */
	public int getMainContactTelNo() {
		return mainContactTelNo;
	}
	/**
	 * @param mainContactTelNo the mainContactTelNo to set
	 */
	public void setMainContactTelNo(int mainContactTelNo) {
		this.mainContactTelNo = mainContactTelNo;
	}
	/**
	 * @return the mainContactFaxNo
	 */
	public int getMainContactFaxNo() {
		return mainContactFaxNo;
	}
	/**
	 * @param mainContactFaxNo the mainContactFaxNo to set
	 */
	public void setMainContactFaxNo(int mainContactFaxNo) {
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
	 * @param reservationSameAsMainContact the 	 to set
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
	public int getReservationContactTelNo() {
		return reservationContactTelNo;
	}
	/**
	 * @param reservationContactTelNo the reservationContactTelNo to set
	 */
	public void setReservationContactTelNo(int reservationContactTelNo) {
		this.reservationContactTelNo = reservationContactTelNo;
	}
	/**
	 * @return the deptTelNo
	 */
	public int getDeptTelNo() {
		return deptTelNo;
	}
	/**
	 * @param deptTelNo the deptTelNo to set
	 */
	public void setDeptTelNo(int deptTelNo) {
		this.deptTelNo = deptTelNo;
	}
	/**
	 * @return the deptFaxNo
	 */
	public int getDeptFaxNo() {
		return deptFaxNo;
	}
	/**
	 * @param deptFaxNo the deptFaxNo to set
	 */
	public void setDeptFaxNo(int deptFaxNo) {
		this.deptFaxNo = deptFaxNo;
	}
	/**
	 * @return the deptExtNo
	 */
	public int getDeptExtNo() {
		return deptExtNo;
	}
	/**
	 * @param deptExtNo the deptExtNo to set
	 */
	public void setDeptExtNo(int deptExtNo) {
		this.deptExtNo = deptExtNo;
	}
	/**
	 * @return the reservationContactExt
	 */
	public int getReservationConstactExt() {
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
	
public static HotelPrivateContacts findById(long id) {
    	
    	try
		{
		return (HotelPrivateContacts) JPA.em().createQuery("select c from HotelPrivateContacts c where c.supplier_code = ?1").setParameter(1, id).getSingleResult();
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
