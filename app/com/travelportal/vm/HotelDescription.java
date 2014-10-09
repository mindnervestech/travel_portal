package com.travelportal.vm;

import java.util.List;

public class HotelDescription {
	private Long supplierCode;
	private String description;
	private List<Integer> services;
	private int shoppingFacilityCode;
	private int hotelLocation;
	private int nightLifeCode;
	private String location1;
	private String location2;
	private String location3;
	
	
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the services
	 */
	public List<Integer> getServices() {
		return services;
	}
	/**
	 * @param services the services to set
	 */
	public void setServices(List<Integer> services) {
		this.services = services;
	}
	/**
	 * @return the shoppingFacilityCode
	 */
	public int getShoppingFacilityCode() {
		return shoppingFacilityCode;
	}
	/**
	 * @param shoppingFacilityCode the shoppingFacilityCode to set
	 */
	public void setShoppingFacilityCode(int shoppingFacilityCode) {
		this.shoppingFacilityCode = shoppingFacilityCode;
	}
	/**
	 * @return the hotelLocation
	 */
	public int getHotelLocation() {
		return hotelLocation;
	}
	/**
	 * @param hotelLocation the hotelLocation to set
	 */
	public void setHotelLocation(int hotelLocation) {
		this.hotelLocation = hotelLocation;
	}
	/**
	 * @return the nightLifeCode
	 */
	public int getNightLifeCode() {
		return nightLifeCode;
	}
	/**
	 * @param nightLifeCode the nightLifeCode to set
	 */
	public void setNightLifeCode(int nightLifeCode) {
		this.nightLifeCode = nightLifeCode;
	}
	
	public String getlocation1() {
		return location1;
	}
	
	public void setlocation1(String location1) {
		this.location1 = location1;
	}
	
	public String getlocation2() {
		return location2;
	}
	
	public void setlocation2(String location2) {
		this.location2 = location2;
	}
	
	public String getlocation3() {
		return location3;
	}
	
	public void setlocation3(String location3) {
		this.location3 = location3;
	}
	
	
}
