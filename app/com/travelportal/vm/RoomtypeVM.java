package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class RoomtypeVM {
	
	
	private Long supplierCode;
	private String roomId;
	private String roomType;
	private String roomname; //normal, deluxe etc..
	private int maxOccupancy;
	private int maxAdultOccupancy;
	private int maxAdultOccSharingWithChildren;
	private int childAllowedFreeWithAdults;
	private String chargesForChildren;
	private String extraBedAllowed;
	//private boolean extraBedChargable;
	//private Double extraBedChargeValue;
	//private String extraBedChargeType;
	private String roomSuiteType;
	//private boolean twinBeds;
	private List<Integer> roomamenities;
	private List<RoomChildpoliciVM> roomchildPolicies;
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
	
	
	
	
	
	
}
