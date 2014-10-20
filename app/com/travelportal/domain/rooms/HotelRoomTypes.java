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

import com.travelportal.vm.RoomType;

@Entity
@Table(name="hotel_room_types")
public class HotelRoomTypes {
	@Column(name="room_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long roomId;
	@Column(name="supplier_code")
	private Long supplierCode;
	@Column(name="room_type", unique=true)
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
	private boolean chargesForChildren;
	@Column(name="extra_bed_allowed")
	private boolean extraBedAllowed;
	@Column(name="extra_bed_chargable")
	private boolean extraBedChargable;
	@Column(name="extra_bed_charge_value")
	private Double extraBedChargeValue;
	@Column(name="extra_bed_charge_type")
	private String extraBedChargeType;
	@Column(name="room_suite_type")
	private boolean roomSuiteType;
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
	 * @return the chargesForChildren
	 */
	public boolean isChargesForChildren() {
		return chargesForChildren;
	}
	/**
	 * @param chargesForChildren the chargesForChildren to set
	 */
	public void setChargesForChildren(boolean chargesForChildren) {
		this.chargesForChildren = chargesForChildren;
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
	 * @return the extraBedAllowed
	 */
	public boolean isExtraBedAllowed() {
		return extraBedAllowed;
	}
	/**
	 * @param extraBedAllowed the extraBedAllowed to set
	 */
	public void setExtraBedAllowed(boolean extraBedAllowed) {
		this.extraBedAllowed = extraBedAllowed;
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
	 * @return the roomSuiteType
	 */
	public boolean isRoomSuiteType() {
		return roomSuiteType;
	}
	/**
	 * @param roomSuiteType the roomSuiteType to set
	 */
	public void setRoomSuiteType(boolean roomSuiteType) {
		this.roomSuiteType = roomSuiteType;
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
	
	public static HotelRoomTypes getHotelRoomDetails(final Long roomId) {
		Query q = JPA.em().createQuery("select r from HotelRoomTypes r where roomId = :roomId ");
		q.setParameter("roomId", roomId);
		return (HotelRoomTypes) q.getSingleResult();
		
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
