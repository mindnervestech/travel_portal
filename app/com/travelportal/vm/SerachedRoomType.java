package com.travelportal.vm;

import java.util.List;

public class SerachedRoomType {
	
	public Long roomId;
	public String roomName;
	public String description;
	public int childAllowedFreeWithAdults;
	public int maxAdultsWithchild;
	public String extraBedAllowed;
	public String roomSuiteType;
	public String roomSize;
	public String extraBedRate;
	public List<SerachedRoomRateDetail> hotelRoomRateDetail;
	public List<RoomAmenitiesVm> amenities;
	public List<RoomChildpoliciVM> roomchildPolicies;
	public List<SpecialsVM> specials;
	public int pcount;
	
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
	public String getExtraBedRate() {
		return extraBedRate;
	}
	public void setExtraBedRate(String extraBedRate) {
		this.extraBedRate = extraBedRate;
	}
	public int getMaxAdultsWithchild() {
		return maxAdultsWithchild;
	}
	public void setMaxAdultsWithchild(int maxAdultsWithchild) {
		this.maxAdultsWithchild = maxAdultsWithchild;
	}
	
	 
	
	
	
}
