package com.travelportal.domain.rooms;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import scala.math.BigInt;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;
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
    public List<Country> country;
	
	
	
	public List<Country> getCountry() {
		return country;
	}
	public void setCountry(List<Country> country) {
		this.country = country;
	}
	/*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<City> cities;
	
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}*/
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
	
	 public static RateMeta findRateMeta(String rateName, String currency, HotelRoomTypes roomType) {
	    	Query query = JPA.em().createQuery("Select r from RateMeta r where r.rateName = ?1 and r.currency = ?2 and r.roomType = ?5");
			query.setParameter(1, rateName);
			query.setParameter(2, currency);
			//query.setParameter(3, fromDate);
			//query.setParameter(4, toDate);
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
	 public static List<RateMeta> getcountryByRate(long code) {
			
	    	Query query = JPA.em().createQuery("Select r from RateMeta r where r.id = ?1");
			query.setParameter(1, code);
			
	    	return query.getResultList();
	    }
	 public static List<RateMeta> getRateSupplier1(Long supplierCode,Long roomId) {
			
	    	Query query = JPA.em().createQuery("Select r from RateMeta r where r.supplierCode = ?1 and r.roomType.roomId = ?2");
			query.setParameter(1, supplierCode);
			query.setParameter(2, roomId);
			
	    	return query.getResultList();
	    }
	 
	 public static List<BigInteger> getOneSupplierId(long supplierCode,int countryId) {  
		// List<Long> list1 = new ArrayList<>();
		 List<BigInteger> list;
		list =JPA.em().createNativeQuery("select DISTINCT am.supplierCode from rate_meta am,hotel_profile hpp,rate_meta_country rmcountry where am.rate_id = rmcountry.rate_meta_rate_id and am.supplierCode = hpp.supplier_code and rmcountry.country_country_code = '"+countryId+"' and am.supplierCode= '"+supplierCode+"'").getResultList();  //and am.roomType_room_id = '"+roomId+"'
	
	
	 /*List<RateMeta> list1 = new ArrayList<>();
		
		for(Object[] o :list) {
		
			RateMeta am = new RateMeta();
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
		}*/
		
		return list;
		
	 }
	 
	 
	 public static List<BigInteger> getsupplierIdwiseRoom(long supplierCode,long roomcode,int countryId) {  //Long roomId,
		 List<BigInteger> list;
	
		list =JPA.em().createNativeQuery("select DISTINCT am.supplierCode from rate_meta am,hotel_profile hpp,rate_meta_country rmcountry where am.rate_id = rmcountry.rate_meta_rate_id and am.supplierCode = hpp.supplier_code and rmcountry.country_country_code = '"+countryId+"' and am.supplierCode= '"+supplierCode+"' and am.roomType_room_id='"+roomcode+"'").getResultList();  //and am.roomType_room_id = '"+roomId+"'
	
	
	/* List<RateMeta> list1 = new ArrayList<>();
		
		for(Object[] o :list) {
		
			RateMeta am = new RateMeta();
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
		}*/
		
		return list;
		
	 }
	 /*public static List<RateMeta> getdatecheckRoom(Long roomId,int countryId,Date date,long supplier) {  //
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		 String SDate = null;
		
			SDate = format.format(date);
		 
		 List<Object[]> list;
	
		list =JPA.em().createNativeQuery("select * from rate_meta am,hotel_profile hpp,rate_meta_country rmcountry, applicable_dateonrate adr where am.rate_id = adr.rate_rate_id and am.rate_id = rmcountry.rate_meta_rate_id and am.supplierCode = hpp.supplier_code and am.supplierCode = '"+supplier+"' and rmcountry.country_country_code = '"+countryId+"' and am.roomType_room_id = '"+roomId+"' and adr.Date_selected = '"+SDate+"'").getResultList();  // 
	
	
	 List<RateMeta> list1 = new ArrayList<>();
		
		for(Object[] o :list) {
		
			RateMeta am = new RateMeta();
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
		
	 }*/
	
	 
	 
	 public static List<BigInteger> getsupplierId(int cityId,int countryId) {  //Long roomId,  int sId
		// DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
		// List<Long> list1 = new ArrayList<>();
		 List<BigInteger> list;
	/*if(sId != 0){
		 list =JPA.em().createNativeQuery("select * from rate_meta am,hotel_profile hpp,rate_meta_country rmcountry where am.rate_id = rmcountry.rate_meta_rate_id and am.supplierCode = hpp.supplier_code and rmcountry.country_country_code = '"+countryId+"' and hpp.city_city_code = '"+cityId+"' and hpp.startRatings_id = '"+sId+"' ").getResultList();  // and am.roomType_room_id = '"+roomId+"' 
		
	}else{*/
		 list = JPA.em().createNativeQuery("select DISTINCT am.supplierCode from rate_meta am, hotel_profile hpp, rate_meta_country rmcountry where am.rate_id = rmcountry.rate_meta_rate_id and am.supplierCode = hpp.supplier_code and rmcountry.country_country_code = '"+countryId+"' and hpp.city_city_code = '"+cityId+"'").getResultList();  //and am.roomType_room_id = '"+roomId+"'
		 
	//}
		 
		 
		 /*for(BigInt o :list) {
			 list1.add(Long.parseLong(o.toString()));
		 }*/
	 /*List<RateMeta> list1 = new ArrayList<>();
		
		for(Object[] o :list) {
		
			RateMeta am = new RateMeta();
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
		*/
		return list;
		
	 }
	 public static List<RateMeta> getdatecheck(Long roomId, int countryId, Date date, long supplier) {  
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		 String SDate = null;
		
			SDate = format.format(date);
		 
		 List<Object[]> list;
	/*if(sId != 0){
		 list =JPA.em().createNativeQuery("select * from rate_meta am,hotel_profile hpp,rate_meta_country rmcountry where am.rate_id = rmcountry.rate_meta_rate_id and am.supplierCode = hpp.supplier_code and am.supplierCode = '"+supplier+"' and rmcountry.country_country_code = '"+countryId+"' and am.roomType_room_id = '"+roomId+"' and hpp.city_city_code = '"+cityId+"' and hpp.startRatings_id = '"+sId+"' and am.from_date <= '"+SDate+"' and am.to_date >= '"+SDate+"' ").getResultList();
			}else{*/
		list =JPA.em().createNativeQuery("select * from rate_meta am,hotel_profile hpp,rate_meta_country rmcountry,applicable_dateonrate adr where am.rate_id = adr.rate_rate_id and am.rate_id = rmcountry.rate_meta_rate_id and am.supplierCode = hpp.supplier_code and am.supplierCode = '"+supplier+"' and rmcountry.country_country_code = '"+countryId+"' and am.roomType_room_id = '"+roomId+"' and adr.Date_selected = '"+SDate+"'").getResultList();  // 
		
	//}
	
	 List<RateMeta> list1 = new ArrayList<>();
		
		for(Object[] o :list) {
		
			RateMeta am = new RateMeta();
			am.setId(Long.parseLong(o[0].toString()));
			am.setCurrency(o[1].toString());
			/*try {
				am.setFromDate(format.parse(o[2].toString()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			am.setRateName(o[3].toString());
			/*try {
				am.setToDate(format.parse(o[4].toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/			
			am.setRoomType(HotelRoomTypes.findById(Long.parseLong(o[5].toString())));
			if(o[6] != null){
			am.setSupplierCode(Long.parseLong(o[6].toString()));
			}
			list1.add(am);
		}
		
		return list1;
		
	 }
	 
	 
	 public static List<RateMeta> getRatecheckdateWise(String currency,Long roomId, Date date) {  //,int sId
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		 String SDate = null;
		
			SDate = format.format(date);
		 
		 List<Object[]> list;
	
		list =JPA.em().createNativeQuery("select * from rate_meta am,applicable_dateonrate ad where am.rate_id = ad.rate_rate_id and am.roomType_room_id = '"+roomId+"' and am.currency= '"+currency+"' and ad.Date_selected = '"+SDate+"'").getResultList();   
		
	 List<RateMeta> list1 = new ArrayList<>();
		
		for(Object[] o :list) {
		
			RateMeta am = new RateMeta();
			am.setId(Long.parseLong(o[0].toString()));
			am.setCurrency(o[1].toString());
			am.setRateName(o[3].toString());
			
			
			
			/*try {
				am.setFromDate(format.parse(o[2].toString()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				am.setToDate(format.parse(o[4].toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		*/	
			am.setRoomType(HotelRoomTypes.findById(Long.parseLong(o[5].toString())));
			if(o[6] != null){
			am.setSupplierCode(Long.parseLong(o[6].toString()));
			}
			list1.add(am);
		}
		
		return list1;
		
	 }
	 

	 
	 public static List<RateMeta> getDates(long roomid,String currencyName,Long supplierCode) {
		
		
			Query q = JPA.em().createQuery("select c.fromDate,c.toDate from RateMeta c where c.roomType.roomId = :roomid and c.currency = :currencyName and c.supplierCode = :supplierCode GROUP BY c.fromDate , c.toDate");
			q.setParameter("roomid", roomid);
			q.setParameter("currencyName", currencyName);
			q.setParameter("supplierCode", supplierCode);
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
		 
		 
		/* public static List<RateMeta> getRateAndHotel(Long roomId,int cityId,int sId,Date fromDate,Date toDate) {
			 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String fDate = null;
				String tDate = null;
			
				fDate = format.format(fromDate);
				tDate = format.format(toDate);
			
			
			
			
		List<Object[]> list =JPA.em().createNativeQuery("select * from rate_meta am,hotel_profile hpp where am.supplierCode = hpp.supplier_code and hpp.city_city_code = '"+cityId+"' and am.roomType_room_id = '"+roomId+"' and hpp.startRatings_id = '"+sId+"' and am.from_date <= '"+fDate+"' and am.to_date >= '"+fDate+"' and  am.from_date <= '"+tDate+"' and am.to_date >= '"+tDate+"'  ").getResultList();

		List<RateMeta> list1 = new ArrayList<>();
			
			for(Object[] o :list) {
			
				RateMeta am = new RateMeta();
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
		 */
		 
		 
	 
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



