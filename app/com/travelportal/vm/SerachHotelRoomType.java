package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.rooms.RateMeta;

public class SerachHotelRoomType {
	
	public Long roomId;
	public String roomName;
	public String description;
	public int childAllowedFreeWithAdults;
	public int maxAdultsWithchild;
	public String extraBedAllowed;
	public String roomSuiteType;
	public List<SerachedRoomRateDetail> hotelRoomRateDetail;
	public List<RoomAmenitiesVm> amenities;
	public List<SpecialsVM> specials;
	public List<RoomChildpoliciVM> roomchildPolicies;
	public int pcount;
	public int applyPromotion;
	public String roomSize;
	//public String extraBedRate;
	public int applyFlatPromotion;
	public int applybirdPromotion;
	public String breakfastInclude;
	public int breakfastRate;
	public int childAge;
	public String roomBed;
	public boolean nonRefund;
	
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public List<SerachedRoomRateDetail> getHotelRoomRateDetail() {
		return hotelRoomRateDetail;
	}
	public void setHotelRoomRateDetail(
			List<SerachedRoomRateDetail> hotelRoomRateDetail) {
		this.hotelRoomRateDetail = hotelRoomRateDetail;
	}
	public List<RoomAmenitiesVm> getAmenities() {
		return amenities;
	}
	public void setAmenities(List<RoomAmenitiesVm> amenities) {
		this.amenities = amenities;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getChildAllowedFreeWithAdults() {
		return childAllowedFreeWithAdults;
	}
	public void setChildAllowedFreeWithAdults(int childAllowedFreeWithAdults) {
		this.childAllowedFreeWithAdults = childAllowedFreeWithAdults;
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
	public List<SpecialsVM> getSpecials() {
		return specials;
	}
	public void setSpecials(List<SpecialsVM> specials) {
		this.specials = specials;
	}
	public int getPcount() {
		return pcount;
	}
	public void setPcount(int pcount) {
		this.pcount = pcount;
	}
	public int getApplyPromotion() {
		return applyPromotion;
	}
	public void setApplyPromotion(int applyPromotion) {
		this.applyPromotion = applyPromotion;
	}
	public String getRoomSize() {
		return roomSize;
	}
	public void setRoomSize(String roomSize) {
		this.roomSize = roomSize;
	}
	public List<RoomChildpoliciVM> getRoomchildPolicies() {
		return roomchildPolicies;
	}
	public void setRoomchildPolicies(List<RoomChildpoliciVM> roomchildPolicies) {
		this.roomchildPolicies = roomchildPolicies;
	}
	/*public String getExtraBedRate() {
		return extraBedRate;
	}
	public void setExtraBedRate(String extraBedRate) {
		this.extraBedRate = extraBedRate;
	}*/
	public int getMaxAdultsWithchild() {
		return maxAdultsWithchild;
	}
	public void setMaxAdultsWithchild(int maxAdultsWithchild) {
		this.maxAdultsWithchild = maxAdultsWithchild;
	}
	public String getBreakfastInclude() {
		return breakfastInclude;
	}
	public void setBreakfastInclude(String breakfastInclude) {
		this.breakfastInclude = breakfastInclude;
	}
	
	public int getBreakfastRate() {
		return breakfastRate;
	}
	public void setBreakfastRate(int breakfastRate) {
		this.breakfastRate = breakfastRate;
	}
	public int getChildAge() {
		return childAge;
	}
	public void setChildAge(int childAge) {
		this.childAge = childAge;
	}
	public String getRoomBed() {
		return roomBed;
	}
	public void setRoomBed(String roomBed) {
		this.roomBed = roomBed;
	}
	public int getApplyFlatPromotion() {
		return applyFlatPromotion;
	}
	public void setApplyFlatPromotion(int applyFlatPromotion) {
		this.applyFlatPromotion = applyFlatPromotion;
	}
	public int getApplybirdPromotion() {
		return applybirdPromotion;
	}
	public void setApplybirdPromotion(int applybirdPromotion) {
		this.applybirdPromotion = applybirdPromotion;
	}
	
	
	
	
}
