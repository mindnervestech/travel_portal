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
@Table(name="billing_information")
public class BillingInformation {
	
	@Column(name="id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="invoice_to_hotel")
	private String invoiceToHotel;
	
	@Column(name="Bankservice")
	private String bankservice;
	
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="title")
	private String title;
	@Column(name="email_addr")
	private String emailAddr;
	@Column(name="tel_no")
	private int telNo;
	@Column(name="tel_no_code")
	private int telNoCode;
	@Column(name="fax_no")
	private int faxNo;
	@Column(name="fax_no_code")
	private int faxNoCode;
	@Column(name="ext")
	private int ext;
	
	private long supplier_code;
	/**
	 * @return the invoiceToHotel
	 */
	
	public long getSupplierCode() {
		return supplier_code;
	}
	
	public void setSupplierCode(long supplier_code) {
		this.supplier_code = supplier_code;
	}
	

	public String getInvoiceToHotel() {
		return invoiceToHotel;
	}

	public void setInvoiceToHotel(String invoiceToHotel) {
		this.invoiceToHotel = invoiceToHotel;
	}

	public String getBankservice() {
		return bankservice;
	}

	public void setBankservice(String bankservice) {
		this.bankservice = bankservice;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the emailAddr
	 */
	public String getEmailAddr() {
		return emailAddr;
	}
	/**
	 * @param emailAddr the emailAddr to set
	 */
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	/**
	 * @return the telNo
	 */
	public int getTelNo() {
		return telNo;
	}
	/**
	 * @param telNo the telNo to set
	 */
	public void setTelNo(int telNo) {
		this.telNo = telNo;
	}
	/**
	 * @return the faxNo
	 */
	public int getFaxNo() {
		return faxNo;
	}
	/**
	 * @param faxNo the faxNo to set
	 */
	public void setFaxNo(int faxNo) {
		this.faxNo = faxNo;
	}
	
	
	public int getTelNoCode() {
		return telNoCode;
	}

	public void setTelNoCode(int telNoCode) {
		this.telNoCode = telNoCode;
	}

	public int getFaxNoCode() {
		return faxNoCode;
	}

	public void setFaxNoCode(int faxNoCode) {
		this.faxNoCode = faxNoCode;
	}

	/**
	 * @return the ext
	 */
	
	public int getExt() {
		return ext;
	}
	/**
	 * @param ext the ext to set
	 */
	public void setExt(int ext) {
		this.ext = ext;
	}
	
    public static BillingInformation findById(long id) {
    	
    	try
		{
    		System.out.println(id);
		return (BillingInformation) JPA.em().createQuery("select c from BillingInformation c where c.supplier_code = ?1").setParameter(1, id).getSingleResult();
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
