package com.travelportal.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import com.travelportal.domain.rooms.RateMeta;

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
	private int noOfchild;
	private Double total;
	@OneToOne
	private Salutation travellersalutation;
	private String travellerfirstname;
	private String travellermiddlename;
	private String travellerlastname;
	private String travelleraddress;
	private String travelleremail;
	private String travellerpassportNo;
	@ManyToOne
	private Country travellercountry;
	private String travellerphnaumber;
	
	private String nonSmokingRoom;
	private String twinBeds;
	private String lateCheckout;
	private String largeBed;
	private String highFloor;
	private String earlyCheckin;
	private String airportTransfer;
	private String airportTransferInfo;
	private String enterComments;
	private String smokingRoom;
	private String wheelchair;
	private String handicappedRoom;
	
	
	
	public String payment;
	private Long roomId;
	private String roomName;
	private String promotionname;
	private int stayDays_inpromotion;
	private int payDays_inpromotion;
	private String typeOfStay_inpromotion;
	private String room_status;
	private Long agentId;
	private String agentNm;
	private String agentCompanyNm;
	private long totalNightStay;
	@OneToOne
	private RateMeta rate;
	public String nonRefund;
	private Date latestCancellationDate;
	
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
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	
	
	public long getTotalNightStay() {
		return totalNightStay;
	}
	public void setTotalNightStay(long totalNightStay) {
		this.totalNightStay = totalNightStay;
	}
	
	public String getAgentCompanyNm() {
		return agentCompanyNm;
	}
	public void setAgentCompanyNm(String agentCompanyNm) {
		this.agentCompanyNm = agentCompanyNm;
	}
	public String getAgentNm() {
		return agentNm;
	}
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}
	
	public int getNoOfchild() {
		return noOfchild;
	}
	public void setNoOfchild(int noOfchild) {
		this.noOfchild = noOfchild;
	}
	public RateMeta getRate() {
		return rate;
	}
	public void setRate(RateMeta rate) {
		this.rate = rate;
	}
	
	public String getNonRefund() {
		return nonRefund;
	}
	public void setNonRefund(String nonRefund) {
		this.nonRefund = nonRefund;
	}
	
	public String getTravellermiddlename() {
		return travellermiddlename;
	}
	public void setTravellermiddlename(String travellermiddlename) {
		this.travellermiddlename = travellermiddlename;
	}
	public String getTravellerpassportNo() {
		return travellerpassportNo;
	}
	public void setTravellerpassportNo(String travellerpassportNo) {
		this.travellerpassportNo = travellerpassportNo;
	}
	
	public Salutation getTravellersalutation() {
		return travellersalutation;
	}
	public void setTravellersalutation(Salutation travellersalutation) {
		this.travellersalutation = travellersalutation;
	}
	
	
	
	
	public String getNonSmokingRoom() {
		return nonSmokingRoom;
	}
	public void setNonSmokingRoom(String nonSmokingRoom) {
		this.nonSmokingRoom = nonSmokingRoom;
	}
	public String getTwinBeds() {
		return twinBeds;
	}
	public void setTwinBeds(String twinBeds) {
		this.twinBeds = twinBeds;
	}
	
	public String getLargeBed() {
		return largeBed;
	}
	public void setLargeBed(String largeBed) {
		this.largeBed = largeBed;
	}
	public String getHighFloor() {
		return highFloor;
	}
	public void setHighFloor(String highFloor) {
		this.highFloor = highFloor;
	}

	public String getLateCheckout() {
		return lateCheckout;
	}
	public void setLateCheckout(String lateCheckout) {
		this.lateCheckout = lateCheckout;
	}
	public String getEarlyCheckin() {
		return earlyCheckin;
	}
	public void setEarlyCheckin(String earlyCheckin) {
		this.earlyCheckin = earlyCheckin;
	}
	public String getSmokingRoom() {
		return smokingRoom;
	}
	public void setSmokingRoom(String smokingRoom) {
		this.smokingRoom = smokingRoom;
	}
	public String getWheelchair() {
		return wheelchair;
	}
	public void setWheelchair(String wheelchair) {
		this.wheelchair = wheelchair;
	}
	public String getHandicappedRoom() {
		return handicappedRoom;
	}
	public void setHandicappedRoom(String handicappedRoom) {
		this.handicappedRoom = handicappedRoom;
	}
	public String getAirportTransfer() {
		return airportTransfer;
	}
	public void setAirportTransfer(String airportTransfer) {
		this.airportTransfer = airportTransfer;
	}
	public String getAirportTransferInfo() {
		return airportTransferInfo;
	}
	public void setAirportTransferInfo(String airportTransferInfo) {
		this.airportTransferInfo = airportTransferInfo;
	}
	public String getEnterComments() {
		return enterComments;
	}
	public void setEnterComments(String enterComments) {
		this.enterComments = enterComments;
	}
	
	public Date getLatestCancellationDate() {
		return latestCancellationDate;
	}
	public void setLatestCancellationDate(Date latestCancellationDate) {
		this.latestCancellationDate = latestCancellationDate;
	}
	
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	public static List<HotelBookingDetails> allBookingData() {
    	return (List<HotelBookingDetails>) JPA.em().createQuery("select c from HotelBookingDetails c").getResultList();
    }
	
	public static HotelBookingDetails findBookingId() {
	    	return (HotelBookingDetails) JPA.em().createQuery("select c from HotelBookingDetails c where c.id = (select max(a.id) from HotelBookingDetails a)").getSingleResult();
	    }
	
	public static HotelBookingDetails findBookingById(long id) {
    	return (HotelBookingDetails) JPA.em().createQuery("select c from HotelBookingDetails c where c.id = ?1").setParameter(1, id).getSingleResult();
    }
	
	
	public static List<HotelBookingDetails> getfindBysupplierDateWise(long supplierCode,Date fromDate,Date toDate,int currentPage, int rowsPerPage,long totalPages,String agentNm,String status) {
		int  start=0;
    	
    	String sql="";
    	if(!agentNm.equals("1")){
    		sql = "Select a from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.agentCompanyNm = ?5 and a.supplierCode = ?1 and a.room_status = ?4 ORDER BY a.checkIn DESC";
    	}else{
    		sql = "Select a from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.supplierCode = ?1 and a.room_status = ?4 ORDER BY a.checkIn DESC";
    	}
    	if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, supplierCode);
   		q.setParameter(2, fromDate);
   		q.setParameter(3, toDate);
   		q.setParameter(4, status);
   		if(!agentNm.equals("1")){
   		q.setParameter(5, agentNm);
   		}
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	public static List<HotelBookingDetails> getfindByagentDateWise(long agentId,Date fromDate,Date toDate,int currentPage, int rowsPerPage,long totalPages,String status) {
		int  start=0;
    	
    	String sql="";
    	if(!status.equals("1")){
    		sql = "Select a from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.agentId = ?1 and a.room_status = ?4 ORDER BY a.checkIn DESC";
    	}else{
    		sql = "Select a from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.agentId = ?1 ORDER BY a.checkIn DESC";
    	}
    	if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, agentId);
   		q.setParameter(2, fromDate);
   		q.setParameter(3, toDate);
   		if(!status.equals("1")){
   		q.setParameter(4, status);
   		}
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	
	
	//00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
	
	public static List<HotelBookingDetails> getfindByagentDateWise1(long agentId,Date fromDate,Date toDate,int currentPage, int rowsPerPage,long totalPages,String status,String guest) {
		int  start=0;
    	
    	String sql="";
    	if(!guest.equals("undefined")){
    		sql = "Select a from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.agentId = ?1 and a.room_status = ?4 and travellerfirstname LIKE CONCAT('%', :someSymbol, '%') ORDER BY a.checkIn DESC";
    	}else{
    		sql = "Select a from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.agentId = ?1 and a.room_status = ?4 and travellerfirstname LIKE CONCAT('%', :someSymbol, '%') ORDER BY a.checkIn DESC";
    	}
    	if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, agentId);
   		q.setParameter(2, fromDate);
   		q.setParameter(3, toDate);
   		q.setParameter("someSymbol", guest);
   		if(!status.equals("1")){
   		q.setParameter(4, status);
   		}
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static List<HotelBookingDetails> getfindByDateWiseAgentWise(long agentId,int currentPage, int rowsPerPage,long totalPages, String status) {
		int  start=0;
    	
    	String sql="";
    
    		sql = "Select a from HotelBookingDetails a where a.agentId = ?1 and a.room_status = ?2 ORDER BY a.checkIn DESC";
    	
    	if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, agentId);
   		q.setParameter(2, status);
   		
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	
	//000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
	
	public static List<HotelBookingDetails> getfindByDateWiseAgentWise11(long agentId,int currentPage, int rowsPerPage,long totalPages,String status,Date fromDate,Date toDate) {
		int  start=0;
    	
    	String sql="";
    
    		sql = "Select a from HotelBookingDetails a where a.agentId = ?1 and a.room_status = ?2 and a.checkIn BETWEEN ?3 and ?4 and a.checkOut BETWEEN ?3 and ?4 ORDER BY a.checkIn DESC";
    	
    	if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, agentId);
   		q.setParameter(2, status);
   		q.setParameter(3, fromDate);
   		q.setParameter(4, toDate);
   		
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	
	
	//00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
	
	public static List<HotelBookingDetails> getfindByDateWiseAgentWise1(long agentId,int currentPage, int rowsPerPage,long totalPages, String status,String guest) {
		int  start=0;
    
    	String sql="";
    
    		sql = "Select a from HotelBookingDetails a where a.agentId = ?1 and a.room_status = ?2 and travellerfirstname LIKE CONCAT('%', :someSymbol, '%') ORDER BY a.checkIn DESC";
    	
    	if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, agentId);
   		q.setParameter(2, status);
   		q.setParameter("someSymbol", guest);
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	public static List<HotelBookingDetails> getfindByBookingId(long agentId,int currentPage, int rowsPerPage,long totalPages, String status,Long bookingId) {
		int  start=0;
    	
    	String sql="";
    	
    	
    	if(status.equals("undefined")){
    		sql = "Select a from HotelBookingDetails a where a.agentId = ?1 and a.id = ?3";
    	}else{
    		sql = "Select a from HotelBookingDetails a where a.agentId = ?1 and a.room_status = ?2 and a.id = ?3";
    	}
    	
    	if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, agentId);
    	if(!status.equals("undefined")){
    		q.setParameter(2, status);
    	}
   		q.setParameter(3, bookingId);
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	
	
	public static List<HotelBookingDetails> getfindBysupplierDateWiseAgentWise(long supplierCode,int currentPage, int rowsPerPage,long totalPages,String agentNm, String status) {
		int  start=0;
    	
    	String sql="";
    
    		sql = "Select a from HotelBookingDetails a where a.agentCompanyNm = ?2 and a.supplierCode = ?1 and a.room_status = ?3 ORDER BY a.checkIn DESC";
    	
    	if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, supplierCode);
   		q.setParameter(2, agentNm);
   		q.setParameter(3, status);
   		
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	public static List<HotelBookingDetails> getfindByagent(long agentId,int currentPage, int rowsPerPage,long totalPages) {
		int  start=0;
    	
    	String sql="";
    		sql = "Select a from HotelBookingDetails a where a.agentId = ?1 ORDER BY a.checkIn DESC";

    		if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, agentId);
    		
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	public static List<HotelBookingDetails> getfindBysupplier(long supplierCode,int currentPage, int rowsPerPage,long totalPages, String status) {
		int  start=0;
    	
    	String sql="";
    		sql = "Select a from HotelBookingDetails a where a.supplierCode = ?1 and a.room_status = ?2 ORDER BY a.checkIn DESC";

    		if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, supplierCode);
    	q.setParameter(2, status);
    		
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	@Transactional
    public static long getAllagentTotalDateWise(int rowsPerPage,long agentId,Date fromDate,Date toDate , String status) {
		long totalPages = 0, size;
	
		if(!status.equals("1")){
			size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.agentId = ?1 and a.room_status = ?4").setParameter(1, agentId).setParameter(2, fromDate).setParameter(3, toDate).setParameter(4, status).getSingleResult();  
		}else{
			size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.agentId = ?1").setParameter(1, agentId).setParameter(2, fromDate).setParameter(3, toDate).getSingleResult();   
		}
    	
    	totalPages = size/rowsPerPage;
		
    	if(size % rowsPerPage > 0) {
			totalPages++;
		}
    	return totalPages;
    }
	
	//oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
	
    public static long getAllagentTotalDateWise1(int rowsPerPage,long agentId,Date fromDate,Date toDate , String status,String guest) {
		long totalPages = 0, size;
	
		if(!guest.equals("undefined")){
			size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.agentId = ?1 and a.room_status = ?4 and a.travellerfirstname LIKE CONCAT('%', :someSymbol, '%')").setParameter(1, agentId).setParameter(2, fromDate).setParameter(3, toDate).setParameter(4, status).setParameter("someSymbol", guest).getSingleResult();  
		}else{
			size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.agentId = ?1 and a.room_status = ?4 and a.travellerfirstname LIKE CONCAT('%', :someSymbol, '%')").setParameter(1, agentId).setParameter(2, fromDate).setParameter(3, toDate).setParameter(4, status).setParameter("someSymbol", guest).getSingleResult();
		}
    	
    	totalPages = size/rowsPerPage;
		
    	if(size % rowsPerPage > 0) {
			totalPages++;
		}
    	return totalPages;
    }
	
	//--------------------------------------------------------------------------------------------------
	public static List<HotelBookingDetails> getinfobyname(String guest,String status,long agentid1,String checkIn,String checkOut)
	{
		Query q = null;
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		if(!guest.equals("undefined") && !checkIn.equals("undefined") && !checkOut.equals("undefined") )
		{
			 q = JPA.em().createQuery("select c from HotelBookingDetails c where travellerfirstname LIKE CONCAT('%', :someSymbol, '%') AND room_status=:s1 AND agentId=:id1 and checkIn BETWEEN :todate and :fromdate and checkOut BETWEEN :todate and :fromdate");
			 q.setParameter("someSymbol", guest);
			q.setParameter("s1",status);
			q.setParameter("id1",agentid1);
		
				try {
					q.setParameter("todate",format.parse(checkIn));
					q.setParameter("fromdate",format.parse(checkOut));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
		}
		else if(guest.equals("undefined")&&!checkIn.equals("undefined")&&!checkOut.equals("undefined"))
		{
			 q = JPA.em().createQuery("select c from HotelBookingDetails c where  room_status=:s1 AND agentId=:id1 and checkIn BETWEEN :todate and :fromdate and checkOut BETWEEN :todate and :fromdate");
			q.setParameter("s1",status);
			q.setParameter("id1",agentid1);
		
				try {
					q.setParameter("todate",format.parse(checkIn));
					q.setParameter("fromdate",format.parse(checkOut));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			
		}
		else if(!guest.equals("undefined")&&checkIn.equals("undefined")&&checkOut.equals("undefined"))
		{
			 q = JPA.em().createQuery("select c from HotelBookingDetails c where travellerfirstname LIKE CONCAT('%', :someSymbol, '%')AND room_status=:s1 AND agentId=:id1");
			 q.setParameter("someSymbol", guest);	
			q.setParameter("s1",status);
			q.setParameter("id1",agentid1);
		}
		return  q.getResultList();
	
	
	}
	
	@Transactional
    public static long getAllBookingTotalDateWise(int rowsPerPage,long supplierCode,Date fromDate,Date toDate ,String agentNm, String status) {
		long totalPages = 0, size;
	
		if(!agentNm.equals("1")){
			size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.agentCompanyNm = ?4 and a.supplierCode = ?1 and a.room_status = ?5").setParameter(1, supplierCode).setParameter(2, fromDate).setParameter(3, toDate).setParameter(4, agentNm).setParameter(5, status).getSingleResult();  
		}else{
			size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.checkIn BETWEEN ?2 and ?3 and a.checkOut BETWEEN ?2 and ?3 and a.supplierCode = ?1 and a.room_status = ?4").setParameter(1, supplierCode).setParameter(2, fromDate).setParameter(3, toDate).setParameter(4, status).getSingleResult();   
		}
    	
    	totalPages = size/rowsPerPage;
		
    	if(size % rowsPerPage > 0) {
			totalPages++;
		}
    	return totalPages;
    }
	
	@Transactional
    public static long getTotalDateWiseAgentWise(int rowsPerPage,long agentId, String status) {
		long totalPages = 0, size;
	
		
			size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.agentId = ?1 and a.room_status = ?2").setParameter(1, agentId).setParameter(2, status).getSingleResult();  
		
    	
    	totalPages = size/rowsPerPage;
		
    	if(size % rowsPerPage > 0) {
			totalPages++;
		}
    	return totalPages;
    }
	
	
	//000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
	
	   public static long getTotalDateWiseAgentWise1(int rowsPerPage,long agentId, String status,String guest) {
			long totalPages = 0, size;
		
			
				size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.agentId = ?1 and a.room_status = ?2 and travellerfirstname LIKE CONCAT('%', :someSymbol, '%')").setParameter(1, agentId).setParameter(2, status).setParameter("someSymbol", guest).getSingleResult();  
			
	    	
	    	totalPages = size/rowsPerPage;
			
	    	if(size % rowsPerPage > 0) {
				totalPages++;
			}
	    	return totalPages;
	    }
	   
	   //00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
	   
	   public static long getTotalBookingIdWise(int rowsPerPage,long agentId, String status,Long bookingId) {
			long totalPages = 0, size;
		
			
			if(status.equals("undefined")){
				size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.agentId = ?1 and a.id = ?3").setParameter(1, agentId).setParameter(3, bookingId).getSingleResult();
			}else{
				size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.agentId = ?1 and a.room_status = ?2 and a.id = ?3").setParameter(1, agentId).setParameter(2, status).setParameter(3, bookingId).getSingleResult();
			}
	    	
	    	totalPages = size/rowsPerPage;
			
	    	if(size % rowsPerPage > 0) {
				totalPages++;
			}
	    	return totalPages;
	    }
	   
	   
	   public static long getTotalDateWiseAgentWise11(int rowsPerPage,long agentId, String status,Date fromDate,Date toDate ) {
			long totalPages = 0, size;
		
			
				size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.agentId = ?1 and a.room_status = ?2 and a.checkIn BETWEEN ?3 and ?4 and a.checkOut BETWEEN ?3 and ?4").setParameter(1, agentId).setParameter(2, status).setParameter(3, fromDate).setParameter(4, toDate).getSingleResult();  
			
	    	
	    	totalPages = size/rowsPerPage;
			
	    	if(size % rowsPerPage > 0) {
				totalPages++;
			}
	    	return totalPages;
	    }
	   
	   
	
	
	@Transactional
    public static long getAllBookingTotalDateWiseAgentWise(int rowsPerPage,long supplierCode,String agentNm, String status) {
		long totalPages = 0, size;
	
		
			size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.agentCompanyNm = ?2 and a.supplierCode = ?1 and a.room_status = ?3").setParameter(1, supplierCode).setParameter(2, agentNm).setParameter(3, status).getSingleResult();  
		
    	
    	totalPages = size/rowsPerPage;
		
    	if(size % rowsPerPage > 0) {
			totalPages++;
		}
    	return totalPages;
    }
	
	@Transactional
    public static long getAgentBookingTotal(int rowsPerPage,long agentId) {
		long totalPages = 0, size;
    		size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.agentId = ?1").setParameter(1, agentId).getSingleResult();
    	
    	totalPages = size/rowsPerPage;
		
    	if(size % rowsPerPage > 0) {
			totalPages++;
		}
    	return totalPages;
    }
	
	@Transactional
    public static long getAdminAgentBookingTotal(int rowsPerPage,long agentId, String status) {
		long totalPages = 0, size;
    		size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.agentId = ?1 and a.room_status = ?2").setParameter(1, agentId).setParameter(2, status).getSingleResult();
    	
    	totalPages = size/rowsPerPage;
		
    	if(size % rowsPerPage > 0) {
			totalPages++;
		}
    	return totalPages;
    }
	
	@Transactional
    public static long getAdminPaymentStatusBookingTotal(int rowsPerPage,long agentId, String paymentStatus, String status) {
		long totalPages = 0, size;
    		size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.agentId = ?1 and a.payment = ?2 and a.room_status = ?3").setParameter(1, agentId).setParameter(2, paymentStatus).setParameter(3, status).getSingleResult();
    	
    	totalPages = size/rowsPerPage;
		
    	if(size % rowsPerPage > 0) {
			totalPages++;
		}
    	return totalPages;
    }
	
	public static List<HotelBookingDetails> getfindByAgent(long agentId,int currentPage, int rowsPerPage,long totalPages) {
		int  start=0;
    	
    	String sql="";
    		sql = "Select a from HotelBookingDetails a where a.agentId = ?1 ORDER BY a.checkIn DESC";

    		if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, agentId);
    		
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	public static List<HotelBookingDetails> getAdminfindByAgent(long agentId,int currentPage, int rowsPerPage,long totalPages, String status) {
		int  start=0;
    	
    	String sql="";
    		sql = "Select a from HotelBookingDetails a where a.agentId = ?1 and a.room_status = ?2 ORDER BY a.checkIn DESC";

    		if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, agentId);
    	q.setParameter(2, status);
    		
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	public static List<HotelBookingDetails> getAdminfindpaymentStatusByAgent(long agentId,int currentPage, int rowsPerPage,long totalPages, String paymentStatus, String status) {
		int  start=0;
    	
    	String sql="";
    		sql = "Select a from HotelBookingDetails a where a.agentId = ?1 and a.payment = ?2 and a.room_status = ?3 ORDER BY a.checkIn DESC";

    		if(currentPage >= 1 && currentPage <= totalPages) {
			start = (currentPage*rowsPerPage)-rowsPerPage;
		}
		if(currentPage>totalPages && totalPages!=0) {
			currentPage--;
			start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
		}
    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
		
    	q.setParameter(1, agentId);
    	q.setParameter(2, paymentStatus);
    	q.setParameter(3, status);
    		
		return (List<HotelBookingDetails>)q.getResultList();
		
	}
	
	
	@Transactional
    public static long getAllagentBookingTotal(int rowsPerPage,long agentId) {
		long totalPages = 0, size;
    		size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.agentId = ?1").setParameter(1, agentId).getSingleResult();
    	
    	totalPages = size/rowsPerPage;
		
    	if(size % rowsPerPage > 0) {
			totalPages++;
		}
    	return totalPages;
    }
	@Transactional
    public static long getAllBookingTotal(int rowsPerPage,long supplierCode,String status) {
		long totalPages = 0, size;
    		size = (long) JPA.em().createQuery("Select count(*) from HotelBookingDetails a where a.supplierCode = ?1 and a.room_status = ?2").setParameter(1, supplierCode).setParameter(2, status).getSingleResult();
    	
    	totalPages = size/rowsPerPage;
		
    	if(size % rowsPerPage > 0) {
			totalPages++;
		}
    	return totalPages;
    }
	
	 @Transactional
	    public static List<HotelBookingDetails> getAllAnnouncements(int currentPage, int rowsPerPage, long totalPages) {
	    	int  start=0;
	    	
	    	String sql="";
	    		sql = "Select a from HotelBookingDetails a";
			
	    	if(currentPage >= 1 && currentPage <= totalPages) {
				start = (currentPage*rowsPerPage)-rowsPerPage;
			}
			if(currentPage>totalPages && totalPages!=0) {
				currentPage--;
				start = (int) ((totalPages*rowsPerPage)-rowsPerPage); 
			}
	    	Query q = JPA.em().createQuery(sql).setFirstResult(start).setMaxResults(rowsPerPage);
			
			/*if(!title.trim().equals("")) {
				q.setParameter(1, "%"+title+"%");
			}*/
		
			return (List<HotelBookingDetails>)q.getResultList();
	    }
	    
	
	public static List<HotelBookingDetails> getfindBysupplierDatewise(Date fromDate,Date toDate,long supplierCode) {
		return JPA.em().createQuery("select c from HotelBookingDetails c where c.checkIn BETWEEN ?2 and ?3 and c.checkOut BETWEEN ?2 and ?3 and c.supplierCode = ?1 and c.room_status = 'available'").setParameter(1, supplierCode).setParameter(2, fromDate).setParameter(3, toDate).getResultList();
	}
	
	public static List<HotelBookingDetails> getfindBysupplierOnRequest(long supplierCode) {
		return JPA.em().createQuery("select c from HotelBookingDetails c where c.supplierCode = ?1 and c.room_status = 'on request' ORDER BY c.checkIn DESC").setParameter(1, supplierCode).setMaxResults(10).getResultList();
	}
	
	public static List<HotelBookingDetails> getfindBysupplierDatewiseOnRequest(Date fromDate,Date toDate,long supplierCode) {
		return JPA.em().createQuery("select c from HotelBookingDetails c where c.checkIn BETWEEN ?2 and ?3 and c.checkOut BETWEEN ?2 and ?3 and c.supplierCode = ?1 and c.room_status = 'on request'").setParameter(1, supplierCode).setParameter(2, fromDate).setParameter(3, toDate).getResultList();
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
