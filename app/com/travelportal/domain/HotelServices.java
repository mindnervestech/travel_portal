package com.travelportal.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;

@Entity
@Table(name="services")
public class HotelServices {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="service_id", unique=true)
	private int serviceId;
	@Column(name="service_type")
	private String serviceType;
	@Column(name="service_nm")
	private String serviceName;
	@Column(name="additional_info")
	private String additionalInfo;
	@Column(name="priority_no")
	private String priorityNo;
	
	/**
	 * @return the serviceId
	 */
	public int getServiceId() {
		return serviceId;
	}
	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/**
	 * @return the additionalInfo
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	/**
	 * @param additionalInfo the additionalInfo to set
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
		
	public String getPriorityNo() {
		return priorityNo;
	}
	public void setPriorityNo(String priorityNo) {
		this.priorityNo = priorityNo;
	}
	
	public static List<Map> getservicesCount(List<Long> supplierCode) {  
		 int priorityno = 1;
		List<Object[]> list;
		 list =JPA.em().createNativeQuery("select count(*) as total,ha.id,ha.service_nm from hotel_profile_services am,services ha where am.services_id = ha.id and am.hotel_profile_id IN ?1 group by  am.services_id").setParameter(1,supplierCode).getResultList();
		 
		 List<Map> list1 = new ArrayList<>();
		 
		 for(Object[] o :list) {
			 Map m = new HashMap<>();
			 m.put("countHotelByService", o[0].toString());
			 m.put("servicesid", o[1].toString());
			 m.put("servicesNm", o[2].toString());
			 list1.add(m);
		 }
			
			return list1;
	}
	
	public static HotelServices findById(int id) {
    	Query query = JPA.em().createQuery("Select a from HotelServices a where a.serviceId = ?1");
		query.setParameter(1, id);
    	return (HotelServices) query.getSingleResult();
    }
	public static List<HotelServices> gethotelservice() {
		return JPA.em().createQuery("select c from HotelServices c").getResultList();
	}
	
	public static List<HotelServices> getallhotelservice(List<Integer> list) {
		return JPA.em().createQuery("select c from HotelServices c where serviceId IN ?1").setParameter(1, list).getResultList();
	}
	
}
