package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class RoomtypeVM {
	
	
	public Long supplierCode;
	public String roomId;
	public String roomType;
	public String roomname; //normal, deluxe etc..
	public int maxOccupancy;
	public int maxAdultOccupancy;
	public int maxAdultOccSharingWithChildren;
	public int childAllowedFreeWithAdults;
	public String chargesForChildren;
	public String extraBedAllowed;
	public String roomSuiteType;
	public String description;
	public String roomPicture;
	public String roomSize;
	public String roomSizeType;
	//public String extraBedRate;
	
	public int childAge;
	public String breakfastInclude;
	public Double breakfastRate;
	
	public List<Integer> roomamenities;
	public List<RoomChildpoliciVM> roomchildPolicies;
	public Long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	
	
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	
	/*public String getExtraBedRate() {
		return extraBedRate;
	}
	public void setExtraBedRate(String extraBedRate) {
		this.extraBedRate = extraBedRate;
	}*/
	public int getMaxAdultOccupancy() {
		return maxAdultOccupancy;
	}
	public void setMaxAdultOccupancy(int maxAdultOccupancy) {
		this.maxAdultOccupancy = maxAdultOccupancy;
	}
	public int getMaxAdultOccSharingWithChildren() {
		return maxAdultOccSharingWithChildren;
	}
	public void setMaxAdultOccSharingWithChildren(int maxAdultOccSharingWithChildren) {
		this.maxAdultOccSharingWithChildren = maxAdultOccSharingWithChildren;
	}
	public int getChildAllowedFreeWithAdults() {
		return childAllowedFreeWithAdults;
	}
	public void setChildAllowedFreeWithAdults(int childAllowedFreeWithAdults) {
		this.childAllowedFreeWithAdults = childAllowedFreeWithAdults;
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
	public List<Integer> getRoomamenities() {
		return roomamenities;
	}
	public void setRoomamenities(List<Integer> roomamenities) {
		this.roomamenities = roomamenities;
	}
	public List<RoomChildpoliciVM> getRoomchildPolicies() {
		return roomchildPolicies;
	}
	public void setRoomchildPolicies(List<RoomChildpoliciVM> roomchildPolicies) {
		this.roomchildPolicies = roomchildPolicies;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRoomPicture() {
		return roomPicture;
	}
	public void setRoomPicture(String roomPicture) {
		this.roomPicture = roomPicture;
	}
	public String getRoomSize() {
		return roomSize;
	}
	public void setRoomSize(String roomSize) {
		this.roomSize = roomSize;
	}
	public int getChildAge() {
		return childAge;
	}
	public void setChildAge(int childAge) {
		this.childAge = childAge;
	}
	public String getBreakfastInclude() {
		return breakfastInclude;
	}
	public void setBreakfastInclude(String breakfastInclude) {
		this.breakfastInclude = breakfastInclude;
	}
	public Double getBreakfastRate() {
		return breakfastRate;
	}
	public void setBreakfastRate(Double breakfastRate) {
		this.breakfastRate = breakfastRate;
	}
	public String getRoomSizeType() {
		return roomSizeType;
	}
	public void setRoomSizeType(String roomSizeType) {
		this.roomSizeType = roomSizeType;
	}

	
	
	
}
