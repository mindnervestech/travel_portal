package com.travelportal.vm;

import javax.persistence.OneToOne;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.agent.AgentRegistration;

public class AgentRegistrationVM {

	public long id;
	public long agentCode;
	public String title;
	public String firstName;
	public String lastName;
	public String country;
	public String business;
	public String hear;
	public String Position;
	public String companyName;
	public String companyAddress;
	public String city;
	public String postalCode;
	public String paymentMethod;
	public String financeEmailAddr;
	public String receiveNet;
	public String commission;
	public String currency;
	public String EmailAddr;	
	public String loginId;
	public String directCode;
	public String directTelNo;
	public String faxCode;
	public String faxTelNo;
	public String webSite;
	public String agree;
	public String status;
	public String password;
	
	
	
	
	public AgentRegistrationVM(AgentRegistration reg) {
		this.id = reg.getId();
		this.agentCode = reg.getAgentCode();
		this.title = reg.getTitle().getSalutationValue();
		this.firstName = reg.getFirstName();
		this.lastName = reg.getLastName();
		this.country =reg.getCountry().getCountryName();
		this.business =reg.getBusiness().getNatureofbusiness();
		this.hear = reg.getHear().getHearAboutUs();
		this.Position =reg.getPosition();
		this.companyName =reg.getCompanyName();
		this.companyAddress =reg.getCompanyAddress();
		this.city =reg.getCity().getCityName();
		this.postalCode =reg.getPostalCode();
		this.paymentMethod =reg.getPaymentMethod();
		this.financeEmailAddr =reg.getFinanceEmailAddr();
		this.receiveNet = reg.getReceiveNet();
		this.commission = reg.getCommission();
		this.currency =reg.getCurrency().getCurrencyName();
		this.EmailAddr =reg.getEmailAddr();
		this.loginId =reg.getLoginId();
		this.directCode =reg.getDirectCode();
		this.directTelNo =reg.getDirectTelNo();
		this.faxCode = reg.getFaxCode();
		this.faxTelNo = reg.getFaxTelNo();
		this.webSite = reg.getWebSite();
		this.status =reg.getStatus();	
		this.password =reg.getPassword();
	}
	
	
	
}
