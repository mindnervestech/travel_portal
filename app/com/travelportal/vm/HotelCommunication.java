package com.travelportal.vm;

public class HotelCommunication {
	private Long supplierCode;
	private String primaryEmailAddr;
	private String ccEmailAddr;
	private String booking;
	
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
	 * @return the primaryEmailAddr
	 */
	public String getPrimaryEmailAddr() {
		return primaryEmailAddr;
	}
	/**
	 * @param primaryEmailAddr the primaryEmailAddr to set
	 */
	public void setPrimaryEmailAddr(String primaryEmailAddr) {
		this.primaryEmailAddr = primaryEmailAddr;
	}
	/**
	 * @return the ccEmailAddr
	 */
	public String getCcEmailAddr() {
		return ccEmailAddr;
	}
	/**
	 * @param ccEmailAddr the ccEmailAddr to set
	 */
	public void setCcEmailAddr(String ccEmailAddr) {
		this.ccEmailAddr = ccEmailAddr;
	}
	public String getBooking() {
		return booking;
	}
	public void setBooking(String booking) {
		this.booking = booking;
	}
	
	
	
	
	
}
