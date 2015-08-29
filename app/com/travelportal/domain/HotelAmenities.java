package com.travelportal.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.JPA;


@Entity
@Table(name="hotel_amenities")
public class HotelAmenities {
	
	@Column(name="amenities_code")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int amenitiesCode;
	@Column(name="amenities_nm")
	private String amenitiesNm;
	@ManyToOne
	private AmenitiesType amenitiesType;
	@Column(name="amenities_icon")
	private String amenitiesicon;
	@Column(name="priority_no")
	private String priorityNo;
	
	
	/**
	 * @return the amenitiesCode
	 */
	public int getAmenitiesCode() {
		return amenitiesCode;
	}
	/**
	 * @param amenitiesCode the amenitiesCode to set
	 */
	public void setAmenitiesCode(int amenitiesCode) {
		this.amenitiesCode = amenitiesCode;
	}
	/**
	 * @return the amenitiesNm
	 */
	public String getAmenitiesNm() {
		return amenitiesNm;
	}
	/**
	 * @param amenitiesNm the amenitiesNm to set
	 */
	public void setAmenitiesNm(String amenitiesNm) {
		this.amenitiesNm = amenitiesNm;
	}
	/**
	 * @return the amenitiesType
	 */
	public AmenitiesType getAmenitiesType() {
		return amenitiesType;
	}
	/**
	 * @param amenitiesType the amenitiesType to set
	 */
	public void setAmenitiesType(AmenitiesType amenitiesType) {
		this.amenitiesType = amenitiesType;
	}
	
	public String getAmenitiesicon() {
		return amenitiesicon;
	}
	public void setAmenitiesicon(String amenitiesicon) {
		this.amenitiesicon = amenitiesicon;
	}
	
	public String getPriorityNo() {
		return priorityNo;
	}
	public void setPriorityNo(String priorityNo) {
		this.priorityNo = priorityNo;
	}
	public static List<HotelAmenities> getamenities(AmenitiesType amenitiescode) {
		
		return JPA.em().createQuery("select c from HotelAmenities c where amenitiesType = ?1"). setParameter(1,amenitiescode).getResultList();
	}
	
public static List<HotelAmenities> getamenities1() {
		
		return JPA.em().createQuery("select c from HotelAmenities c").getResultList();
	}
	
	
	public static Set<HotelAmenities> getallhotelamenities(List<Integer> list) {
		List<HotelAmenities> hotelcontactinformation =  JPA.em().createQuery("select c from HotelAmenities c where amenitiesCode IN ?1").setParameter(1, list).getResultList();
		return new HashSet<>(hotelcontactinformation);
	}
	
	 public static List<Map> getamenitiesCount(List<Long> supplierCode) {  
		 int priorityno = 1;
		List<Object[]> list;
		 list =JPA.em().createNativeQuery("select count(*) total,am.amenities_amenities_code,ha.amenities_nm from hotel_profile_hotel_amenities am,hotel_amenities ha where am.amenities_amenities_code = ha.amenities_code and am.hotel_profile_id IN ?1 group by  am.amenities_amenities_code").setParameter(1,supplierCode).getResultList();
		 
		 List<Map> list1 = new ArrayList<>();
		 
		 for(Object[] o :list) {
			 Map m = new HashMap<>();
			 m.put("countHotelByamenities", o[0].toString());
			 m.put("amenitiesCode", o[1].toString());
			 m.put("amenitiesNm", o[2].toString());
			 list1.add(m);
		 }
			
			return list1;
			
		
	}
	
	
}
