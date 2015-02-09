package com.travelportal.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;

import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.domain.rooms.HotelRoomTypes;
import com.travelportal.domain.rooms.RateMeta;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name = "hotel_booking_details")
public class HotelBookingDetails {

	@Column(name="id", unique=true)
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long supplierCode;
	private String hotelNm;
	private String supplierNm;
	private String hotelAddr;
	private Date checkIn;
	private Date checkOut;
	@ManyToOne
	private Country nationality;
	@ManyToOne
	private Country country;
	@ManyToOne
	private City cityCode;
	@ManyToOne
	private HotelStarRatings startRating;
	@ManyToOne
	private Currency currencyId;
	private String adult;
	private int noOfroom;
	private Double total;
	private String travellerfirstname;
	private String travellerlastname;
	private String travelleraddress;
	private String travelleremail;
	@ManyToOne
	private Country travellercountry;
	private String travellerphnaumber;
	private Long roomId;
	private String promotionname;
	private int stayDays_inpromotion;
	private int payDays_inpromotion;
	private String typeOfStay_inpromotion;
	private String room_status;
	
	public Long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getHotelNm() {
		return hotelNm;
	}	
	public void setHotelNm(String hotelNm) {
		this.hotelNm = hotelNm;
	}
	public String getSupplierNm() {
		return supplierNm;
	}
	public void setSupplierNm(String supplierNm) {
		this.supplierNm = supplierNm;
	}
	public Date getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	public Date getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
	
	public String getAdult() {
		return adult;
	}
	public void setAdult(String adult) {
		this.adult = adult;
	}
	public int getNoOfroom() {
		return noOfroom;
	}
	public void setNoOfroom(int noOfroom) {
		this.noOfroom = noOfroom;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Country getNationality() {
		return nationality;
	}
	public void setNationality(Country nationality) {
		this.nationality = nationality;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public City getCityCode() {
		return cityCode;
	}
	public void setCityCode(City cityCode) {
		this.cityCode = cityCode;
	}
	public HotelStarRatings getStartRating() {
		return startRating;
	}
	public void setStartRating(HotelStarRatings startRating) {
		this.startRating = startRating;
	}
	public Currency getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Currency currencyId) {
		this.currencyId = currencyId;
	}
	public String getTravellerfirstname() {
		return travellerfirstname;
	}
	public void setTravellerfirstname(String travellerfirstname) {
		this.travellerfirstname = travellerfirstname;
	}
	public String getTravellerlastname() {
		return travellerlastname;
	}
	public void setTravellerlastname(String travellerlastname) {
		this.travellerlastname = travellerlastname;
	}
	public String getTravelleraddress() {
		return travelleraddress;
	}
	public void setTravelleraddress(String travelleraddress) {
		this.travelleraddress = travelleraddress;
	}
	public String getTravelleremail() {
		return travelleremail;
	}
	public void setTravelleremail(String travelleremail) {
		this.travelleremail = travelleremail;
	}
	public Country getTravellercountry() {
		return travellercountry;
	}
	public void setTravellercountry(Country travellercountry) {
		this.travellercountry = travellercountry;
	}
	public String getTravellerphnaumber() {
		return travellerphnaumber;
	}
	public void setTravellerphnaumber(String travellerphnaumber) {
		this.travellerphnaumber = travellerphnaumber;
	}
	 public String getPromotionname() {
		return promotionname;
	}
	public void setPromotionname(String promotionname) {
		this.promotionname = promotionname;
	}
	public int getStayDays_inpromotion() {
		return stayDays_inpromotion;
	}
	public void setStayDays_inpromotion(int stayDays_inpromotion) {
		this.stayDays_inpromotion = stayDays_inpromotion;
	}
	public int getPayDays_inpromotion() {
		return payDays_inpromotion;
	}
	public void setPayDays_inpromotion(int payDays_inpromotion) {
		this.payDays_inpromotion = payDays_inpromotion;
	}
	public String getTypeOfStay_inpromotion() {
		return typeOfStay_inpromotion;
	}
	public void setTypeOfStay_inpromotion(String typeOfStay_inpromotion) {
		this.typeOfStay_inpromotion = typeOfStay_inpromotion;
	}
	
	public String getHotelAddr() {
		return hotelAddr;
	}
	public void setHotelAddr(String hotelAddr) {
		this.hotelAddr = hotelAddr;
	}
	public static HotelBookingDetails findBookingId() {
	    	return (HotelBookingDetails) JPA.em().createQuery("select c from HotelBookingDetails c where c.id = (select max(a.id) from HotelBookingDetails a)").getSingleResult();
	    }
	
	
	
	public String getRoom_status() {
		return room_status;
	}
	public void setRoom_status(String room_status) {
		this.room_status = room_status;
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