package com.travelportal.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name = "hotel_registration")
public class HotelRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String hotelName;
	private String supplierName;
	private String hotelAddress;
	@OneToOne
	private Country country;
	@OneToOne
	private City city;
	private String zipcode;
	private boolean partOfChain;
	private String chainHotel;
	private String hotelBrand;
	private boolean laws;
	@OneToOne
	private Currency currency;
	private String policy;
	private String starRating;
	private String password;
	private String supplierCode;
	private String status;
	private String supplierType;
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSupplierType() {
		return supplierType;
	}
	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public boolean isPartOfChain() {
		return partOfChain;
	}
	public void setPartOfChain(boolean partOfChain) {
		this.partOfChain = partOfChain;
	}
	public String getChainHotel() {
		return chainHotel;
	}
	public void setChainHotel(String chainHotel) {
		this.chainHotel = chainHotel;
	}
	public String getHotelBrand() {
		return hotelBrand;
	}
	public void setHotelBrand(String hotelBrand) {
		this.hotelBrand = hotelBrand;
	}
	public boolean isLaws() {
		return laws;
	}
	public void setLaws(boolean laws) {
		this.laws = laws;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public String getStarRating() {
		return starRating;
	}
	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public static List<HotelRegistration> getAllPendingUsers() {
		Query query = JPA.em().createQuery("select h from HotelRegistration h where h.status = 'PENDING'");
		return (List<HotelRegistration>) query.getResultList();
	}
	
	public static List<HotelRegistration> getAllApprovedUsers() {
		Query query = JPA.em().createQuery("select h from HotelRegistration h where h.status = 'APPROVED'");
		return (List<HotelRegistration>) query.getResultList();
	}
	
	public static List<HotelRegistration> getAllRejectedUsers() {
		Query query = JPA.em().createQuery("select h from HotelRegistration h where h.status = 'REJECTED'");
		return (List<HotelRegistration>) query.getResultList();
	}
	
	public static HotelRegistration findById(long id) {
		Query query = JPA.em().createQuery("select h from HotelRegistration h where h.id = ?1").setParameter(1, id);
		return (HotelRegistration) query.getSingleResult();
	}
	
	public static HotelRegistration doLogin(String email, String code, String password, String type) {
		Query query = JPA.em().createQuery("select h from HotelRegistration h where h.supplierCode = ?1 and h.password = ?2 and h.supplierType = ?3 and h.email = ?4 and h.status = 'APPROVED'").setParameter(1, code).setParameter(2, password).setParameter(3, type).setParameter(4, email);
		return (HotelRegistration) query.getSingleResult();
	}
	
	public static HotelRegistration findSupplier(String code,String name) {
		try
		{    		 				
			Query query = JPA.em().createQuery("select h from HotelRegistration h where h.supplierCode = ?1 or h.hotelName = ?2 and h.status = 'APPROVED'").setParameter(1, code).setParameter(2, name);
		return (HotelRegistration) query.getSingleResult();
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
