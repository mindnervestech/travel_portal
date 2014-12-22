package com.travelportal.domain.rooms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import com.travelportal.domain.City;
//import com.travelportal.domain.RatePeriod;
import com.travelportal.domain.agent.AgentRegistration;
import com.travelportal.domain.allotment.AllotmentMarket;
import com.travelportal.vm.RateVM;

@Entity
@Table(name="rate_meta")
public class RateMeta {

	@Column(name="rate_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="rate_name")
	private String rateName;
	@OneToOne
	private HotelRoomTypes roomType;
	@Column(name="from_date")
	private Date fromDate;
	@Column(name="to_date")
	private Date toDate;
	@Column(name="currency")
	private String currency;
	@Column(name="supplierCode")
	private Long supplierCode;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<City> cities;
	
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getRateName() {
		return rateName;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	public HotelRoomTypes getRoomType() {
		return roomType;
	}
	public void setRoomType(HotelRoomTypes roomType) {
		this.roomType = roomType;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	 public static RateMeta findRateMeta(String rateName, String currency, Date fromDate,Date toDate, HotelRoomTypes roomType) {
	    	Query query = JPA.em().createQuery("Select r from RateMeta r where r.rateName = ?1 and r.currency = ?2 and r.fromDate = ?3 and r.toDate = ?4 and r.roomType = ?5");
			query.setParameter(1, rateName);
			query.setParameter(2, currency);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(5, roomType);
	    	return (RateMeta) query.getSingleResult();
	    }
	 public static List<RateMeta> getRateMeta(String currency, Date fromDate,Date toDate, Long roomType) {
		
	    	Query query = JPA.em().createQuery("Select r from RateMeta r where r.currency = ?2 and fromDate = ?3 and toDate = ?4 and r.roomType.roomId = ?5");
			query.setParameter(2, currency);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(5, roomType);
	    	return query.getResultList();
	    }
	 
	
	 public static List<RateMeta> getRateSupplier(Long supplierCode) {
			
	    	Query query = JPA.em().createQuery("Select r from RateMeta r where r.supplierCode = ?1");
			query.setParameter(1, supplierCode);
			
	    	return query.getResultList();
	    }
	 
	 public static List<RateMeta> getRateByRoom(long code) {
			
	    	Query query = JPA.em().createQuery("Select r from RateMeta r where r.roomType.roomId = ?1");
			query.setParameter(1, code);
			
	    	return query.getResultList();
	    }
	 public static List<RateMeta> getRateSupplier1(Long supplierCode,Long roomId) {
			
	    	Query query = JPA.em().createQuery("Select r from RateMeta r where r.supplierCode = ?1 and r.roomType.roomId = ?2");
			query.setParameter(1, supplierCode);
			query.setParameter(2, roomId);
			
	    	return query.getResultList();
	    }
	 
	
	 public static List<RateMeta> getRateByCountry(Long supplierCode,Long roomId,int cityId,int sId) {
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	 List<Object[]> list =JPA.em().createNativeQuery("select * from rate_meta am,rate_meta_city arm,hotel_profile hpp where arm.rate_meta_rate_id = am.rate_id and am.supplierCode = hpp.supplier_code and arm.cities_city_code = '"+cityId+"' and am.roomType_room_id = '"+roomId+"' and am.supplierCode = '"+supplierCode+"' and hpp.startRatings_id = '"+sId+"'").getResultList();
	
	 List<RateMeta> list1 = new ArrayList<>();
		
		for(Object[] o :list) {
		
			RateMeta am = new RateMeta();
			System.out.println(o);
			am.setId(Long.parseLong(o[0].toString()));
			am.setCurrency(o[1].toString());
			try {
				am.setFromDate(format.parse(o[2].toString()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			am.setRateName(o[3].toString());
			try {
				am.setToDate(format.parse(o[4].toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			am.setRoomType(HotelRoomTypes.findById(Long.parseLong(o[5].toString())));
			if(o[6] != null){
			am.setSupplierCode(Long.parseLong(o[6].toString()));
			}
			list1.add(am);
		}
		
		return list1;
		
	 }
	 
	 public static List<RateMeta> getDates(long roomid,String currencyName) {
		
		
			Query q = JPA.em().createQuery("select c.fromDate,c.toDate from RateMeta c where c.roomType.roomId = :roomid and c.currency = :currencyName GROUP BY c.fromDate , c.toDate");
			q.setParameter("roomid", roomid);
			q.setParameter("currencyName", currencyName);
			return q.getResultList();
		}
	
	 public static List<RateMeta> searchRateMeta(String currency, Date fromDate,Date toDate, Long roomType) {
	    	Query query = JPA.em().createQuery("Select r from RateMeta r where r.currency = ?1 and r.fromDate = ?2 and r.toDate = ?3 and r.roomType.roomId = ?4");
			query.setParameter(1, currency);
			query.setParameter(2, fromDate);
			query.setParameter(3, toDate);
			query.setParameter(4, roomType);
	    	return (List<RateMeta>) query.getResultList();
	    }
	 
	 
	 public static List<RateMeta> getAllDate() {
	    	Query query = JPA.em().createQuery("Select r from RateMeta r");
	    	return (List<RateMeta>) query.getResultList();
	    }
	 
	 
	 public static RateMeta findById(Long id) {
	    	Query query = JPA.em().createQuery("Select r from RateMeta r where r.id = ?1");
			query.setParameter(1, id);
	    	return (RateMeta) query.getSingleResult();
	    }
	 
	 public static List<Integer> getAllRatesId() {
		 return JPA.em().createQuery("Select ratesId from RateMeta r").getResultList();
		 }

		 public static RateMeta getRatesById(long ratesId) {
		 return (RateMeta) JPA.em()
		 .createQuery("select r from RateMeta r where r.id = ?1")
		 .setParameter(1, ratesId).getSingleResult();
		 }

		 public static List<RateMeta> getAllRates() {
		 return JPA.em().createQuery("Select r from RateMeta r").getResultList();
		 }
	 
		 
		 public static List<RateMeta> getrateId(List<Long> rateid) {
				return JPA.em().createQuery("select c from RateMeta c where id IN ?1").setParameter(1, rateid).getResultList();
			}
		 
		 public static RateMeta getallRateCode(long code) {
				return (RateMeta) JPA.em().createQuery("select c from RateMeta c where id = ?1").setParameter(1, code).getSingleResult();
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
