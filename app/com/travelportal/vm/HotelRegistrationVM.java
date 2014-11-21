package com.travelportal.vm;

import javax.persistence.OneToOne;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelRegistration;

public class HotelRegistrationVM {

	public long id;
	public String supplierName;
	public String hotelName;
	public String policy;
	public String starRating;
	public String code;
	public String hotelAddress;
	public String country;
	public String city;
	public String zipcode;
	public boolean partOfChain;
	public String chainHotel;
	public String hotelBrand;
	public boolean laws;
	public String currency;
	public String password;
	public String status;
	public String supplierType;
	public String email;
	
	
	
	
	public HotelRegistrationVM(HotelRegistration reg) {
		this.id = reg.getId();
		this.supplierName = reg.getSupplierName();
		this.hotelName = reg.getHotelName();
		this.policy = reg.getPolicy();
		this.starRating = reg.getStarRating();
		this.code = reg.getSupplierCode();
		this.hotelAddress =reg.getHotelAddress();
		this.country =reg.getCountry().getCountryName();
		this.city =reg.getCity().getCityName();
		this.zipcode =reg.getZipcode();
		this.partOfChain =reg.isPartOfChain();
		this.chainHotel =reg.getChainHotel();
		this.hotelBrand =reg.getHotelBrand();
		this.currency =reg.getCurrency().getCurrencyName();
		this.email =reg.getEmail();
		this.password =reg.getPassword();
	}
	
	
	
}
