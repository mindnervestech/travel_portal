package com.travelportal.domain.rooms;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelMealPlan;
import com.travelportal.domain.HotelProfile;
import com.travelportal.vm.RoomType;

@Entity
@Table(name="hotel_room_types")
public class HotelRoomTypes {
	@Column(name="room_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long roomId;
	@Column(name="supplier_code")
	private Long supplierCode;
	@Column(name="room_type")  //, unique=true
	private String roomType; //normal, deluxe etc..
	@Column(name="max_occupancy")
	private int maxOccupancy;
	@Column(name="max_adult_occupancy")
	private int maxAdultOccupancy;
	@Column(name="max_adult_occ_sharing_with_children")
	private int maxAdultOccSharingWithChildren;
	@Column(name="child_allowed_with_adults")
	private int childAllowedFreeWithAdults;
	@Column(name="children_charges")
	private String chargesForChildren;
	@Column(name="extra_bed_allowed")
	private String extraBedAllowed;
	@Column(name="extra_bed_chargable")
	private boolean extraBedChargable;
	@Column(name="extra_bed_charge_value")
	private Double extraBedChargeValue;
	@Column(name="extra_bed_charge_type")
	private String extraBedChargeType;
	@Column(name="room_suite_type")
	private String roomSuiteType;
	@Column(name="twin_beds_flag")
	private boolean twinBeds;
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<RoomAmenities> amenities;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<RoomChildPolicies> roomchildPolicies;
	
	/**
	 * @return the roomId
	 */
	public Long getRoomId() {
		return roomId;
	}
	/**
	 * @param roomId the roomId to set
	 */
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	/**
	 * @return the supplierCode
	 */
	public Long getSupplierCode() {
		return supplierCode;
	}
	/**
	 * @param supplierCode the supplierCode to set
	 */
	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
	}
	/**
	 * @return the roomType
	 */
	public String getRoomType() {
		return roomType;
	}
	/**
	 * @param roomType the roomType to set
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	/**
	 * @return the maxOccupancy
	 */
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	/**
	 * @param maxOccupancy the maxOccupancy to set
	 */
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	/**
	 * @return the maxAdultOccupancy
	 */
	public int getMaxAdultOccupancy() {
		return maxAdultOccupancy;
	}
	/**
	 * @param maxAdultOccupancy the maxAdultOccupancy to set
	 */
	public void setMaxAdultOccupancy(int maxAdultOccupancy) {
		this.maxAdultOccupancy = maxAdultOccupancy;
	}
	/**
	 * @return the maxAdultOccSharingWithChildren
	 */
	public int getMaxAdultOccSharingWithChildren() {
		return maxAdultOccSharingWithChildren;
	}
	/**
	 * @param maxAdultOccSharingWithChildren the maxAdultOccSharingWithChildren to set
	 */
	public void setMaxAdultOccSharingWithChildren(int maxAdultOccSharingWithChildren) {
		this.maxAdultOccSharingWithChildren = maxAdultOccSharingWithChildren;
	}
	/**
	 * @return the childAllowedFreeWithAdults
	 */
	public int getChildAllowedFreeWithAdults() {
		return childAllowedFreeWithAdults;
	}
	/**
	 * @param childAllowedFreeWithAdults the childAllowedFreeWithAdults to set
	 */
	public void setChildAllowedFreeWithAdults(int childAllowedFreeWithAdults) {
		this.childAllowedFreeWithAdults = childAllowedFreeWithAdults;
	}
	
	/**
	 * @return the amenities
	 */
	public List<RoomAmenities> getAmenities() {
		return amenities;
	}
	/**
	 * @param amenities the amenities to set
	 */
	public void setAmenities(List<RoomAmenities> amenities) {
		this.amenities = amenities;
	}

	
	public void saveOrUpdateHotelRoomDetails() {
		JPA.em().persist(this);
	}
	
	/**
	 * @return the extraBedChargable
	 */
	public boolean isExtraBedChargable() {
		return extraBedChargable;
	}
	/**
	 * @param extraBedChargable the extraBedChargable to set
	 */
	public void setExtraBedChargable(boolean extraBedChargable) {
		this.extraBedChargable = extraBedChargable;
	}
	/**
	 * @return the extraBedChargeValue
	 */
	public Double getExtraBedChargeValue() {
		return extraBedChargeValue;
	}
	/**
	 * @param extraBedChargeValue the extraBedChargeValue to set
	 */
	public void setExtraBedChargeValue(Double extraBedChargeValue) {
		this.extraBedChargeValue = extraBedChargeValue;
	}
	/**
	 * @return the extraBedChargeType
	 */
	public String getExtraBedChargeType() {
		return extraBedChargeType;
	}
	/**
	 * @param extraBedChargeType the extraBedChargeType to set
	 */
	public void setExtraBedChargeType(String extraBedChargeType) {
		this.extraBedChargeType = extraBedChargeType;
	}
	
	/**
	 * @return the twinBeds
	 */
	public boolean isTwinBeds() {
		return twinBeds;
	}
	/**
	 * @param twinBeds the twinBeds to set
	 */
	public void setTwinBeds(boolean twinBeds) {
		this.twinBeds = twinBeds;
	}
	
	
		
	public String getChargesForChildren() {
		return chargesForChildren;
	}
	public void setChargesForChildren(String chargesForChildren) {
		this.chargesForChildren = chargesForChildren;
	}
	public String getExtraBedAllowed() {
		return extraBedAllowed;
	}
	public void setExtraBedAllowed(String extraBedAllowed) {
		this.extraBedAllowed = extraBedAllowed;
	}
	public String getRoomSuiteType() {
		return roomSuiteType;
	}
	public void setRoomSuiteType(String roomSuiteType) {
		this.roomSuiteType = roomSuiteType;
	}
	public List<RoomChildPolicies> getRoomchildPolicies() {
		return roomchildPolicies;
	}
	public void setRoomchildPolicies(List<RoomChildPolicies> roomchildPolicies) {
		this.roomchildPolicies = roomchildPolicies;
	}
	
	public void addchildPolicies(RoomChildPolicies roomchildPolicies) {
		if(this.roomchildPolicies == null){
			this.roomchildPolicies = new ArrayList<>();
		}
		if(!this.roomchildPolicies.contains(roomchildPolicies))
		this.roomchildPolicies.add(roomchildPolicies);
	}
	
	
	
	
	public static List<RoomType> getAllRoomTypes(final Long supplierCode) {
		Query q = JPA.em().createQuery("select r.roomId, r.roomType from HotelRoomTypes r where r.supplierCode = :supplierCode ");
		q.setParameter("supplierCode", supplierCode);
		List<Object[]> resultSet = q.getResultList();
		List<RoomType> roomTypeList = new ArrayList<>();
		
		for (Object[] resultElement : resultSet) {
			RoomType roomType = new RoomType();
			roomType.setRoomId((Long) resultElement[0]);
			roomType.setRoomType((String) resultElement[1]);
			roomTypeList.add(roomType);
		}
		return roomTypeList;
	}
	
//	public static List<Object[]> getRoomTypes(long code) {
//		Query q = JPA.em().createNativeQuery("select hotel_room_types.room_type from hotel_room_types  where hotel_room_types.supplier_code = '"+code+"'");
//		return (List<Object[]>) q.getResultList();
//	}
	
	public static List<HotelRoomTypes> getRoomTypesByCode(Long code) {
		Query q = JPA.em().createQuery("select r from HotelRoomTypes r where r.supplierCode = ?1");
				q.setParameter(1, code);
		return (List<HotelRoomTypes>) q.getResultList();
	}
	
	public static HotelRoomTypes getHotelRoomDetailsInfo(Long RoomId) {
		Query q = JPA.em().createQuery("select r from HotelRoomTypes r where r.roomId = ?1");
		q.setParameter(1, RoomId);
		return (HotelRoomTypes) q.getSingleResult();
		
	}
	

	 public static HotelRoomTypes findById(long id) {
	    	Query query = JPA.em().createQuery("Select a from HotelRoomTypes a where a.roomId = ?1");
			query.setParameter(1, id);
	    	return (HotelRoomTypes) query.getSingleResult();
	    }
	 
	 public static HotelRoomTypes findByIdAndName(String name,Long code) {
			try
			{
		 System.out.println(name);
	    	Query query = JPA.em().createQuery("Select a from HotelRoomTypes a where a.supplierCode = ?1 and a.roomType = ?2");
			query.setParameter(1, code);
			query.setParameter(2, name);
	    	return (HotelRoomTypes) query.getSingleResult();
			}
			catch(Exception ex){
				return null;
			}
	    }
	
	 public static HotelRoomTypes findByName(String type) {
	    	Query query = JPA.em().createQuery("Select a from HotelRoomTypes a where a.roomType = ?1");
			query.setParameter(1, type);
	    	return (HotelRoomTypes) query.getSingleResult();
	    }
	 
	 public static List<HotelRoomTypes> findByListName(List<String> names) {
	    Query query = JPA.em().createQuery("Select a from HotelRoomTypes a where a.roomType IN ?1");
		query.setParameter(1, names);
	    return ( List<HotelRoomTypes>) query.getResultList();
	 }

	public static List<HotelRoomTypes> getHotelRoomDetails(long supplierCode) {
		return JPA.em().createQuery("select r from HotelRoomTypes r where r.supplierCode = ?1").setParameter(1, supplierCode).getResultList();
	}
	
	public static List<HotelRoomTypes> getHotelRoomDetailsByIds(long supplierCode,long roomId) {
		return JPA.em().createQuery("select r from HotelRoomTypes r where r.supplierCode = ?1 and r.roomId = ?2").setParameter(1, supplierCode).setParameter(2, roomId).getResultList();
	}
	
	public static int getHotelRoomMaxAdultOccupancy(Long roomType) {
		return (int) JPA.em().createQuery("select r.maxAdultOccupancy from HotelRoomTypes r where r.roomId = ?1").setParameter(1, roomType).getSingleResult();
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
