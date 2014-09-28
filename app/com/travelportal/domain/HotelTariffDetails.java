package com.travelportal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hotel_tariff_details")
public class HotelTariffDetails {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String tariffNotes;
	private boolean transferMandatory; //whether the transfer to be booked along with reservation.
	private int allowedAge;
	private String checkInTime; //only time in format of 11:00
	private String checkOutTime;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the tariffNotes
	 */
	public String getTariffNotes() {
		return tariffNotes;
	}
	/**
	 * @param tariffNotes the tariffNotes to set
	 */
	public void setTariffNotes(String tariffNotes) {
		this.tariffNotes = tariffNotes;
	}
	/**
	 * @return the transferMandatory
	 */
	public boolean isTransferMandatory() {
		return transferMandatory;
	}
	/**
	 * @param transferMandatory the transferMandatory to set
	 */
	public void setTransferMandatory(boolean transferMandatory) {
		this.transferMandatory = transferMandatory;
	}
	/**
	 * @return the allowedAge
	 */
	public int getAllowedAge() {
		return allowedAge;
	}
	/**
	 * @param allowedAge the allowedAge to set
	 */
	public void setAllowedAge(int allowedAge) {
		this.allowedAge = allowedAge;
	}
	/**
	 * @return the checkInTime
	 */
	public String getCheckInTime() {
		return checkInTime;
	}
	/**
	 * @param checkInTime the checkInTime to set
	 */
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}
	/**
	 * @return the checkOutTime
	 */
	public String getCheckOutTime() {
		return checkOutTime;
	}
	/**
	 * @param checkOutTime the checkOutTime to set
	 */
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	
	
}
