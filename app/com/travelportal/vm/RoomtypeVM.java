package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class RoomtypeVM {
	
	
	private Long supplierCode;
	private String roomname; //normal, deluxe etc..
	private int maxOccupancy;
	private int maxAdultsOccupancy;
	private int maxAdultsWithChildren;
	private int freeChildOccuWithAdults;
	private boolean chargesforChild;
	private boolean extraBed;
	//private boolean extraBedChargable;
	//private Double extraBedChargeValue;
	//private String extraBedChargeType;
	private boolean roomSuite;
	//private boolean twinBeds;
	private List<Integer> roomamenities;
	private List<RoomChildpoliciVM> roomchildPolicies;
	public Long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
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
	public int getMaxAdultsOccupancy() {
		return maxAdultsOccupancy;
	}
	public void setMaxAdultsOccupancy(int maxAdultsOccupancy) {
		this.maxAdultsOccupancy = maxAdultsOccupancy;
	}
	public int getMaxAdultsWithChildren() {
		return maxAdultsWithChildren;
	}
	public void setMaxAdultsWithChildren(int maxAdultsWithChildren) {
		this.maxAdultsWithChildren = maxAdultsWithChildren;
	}
	public int getFreeChildOccuWithAdults() {
		return freeChildOccuWithAdults;
	}
	public void setFreeChildOccuWithAdults(int freeChildOccuWithAdults) {
		this.freeChildOccuWithAdults = freeChildOccuWithAdults;
	}
	public boolean isChargesforChild() {
		return chargesforChild;
	}
	public void setChargesforChild(boolean chargesforChild) {
		this.chargesforChild = chargesforChild;
	}
	
	
	public boolean isExtraBed() {
		return extraBed;
	}
	public void setExtraBed(boolean extraBed) {
		this.extraBed = extraBed;
	}
	public boolean isRoomSuite() {
		return roomSuite;
	}
	public void setRoomSuite(boolean roomSuite) {
		this.roomSuite = roomSuite;
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
