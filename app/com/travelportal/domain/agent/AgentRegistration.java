package com.travelportal.domain.agent;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import com.travelportal.domain.City;
import com.travelportal.domain.Country;
import com.travelportal.domain.Currency;
import com.travelportal.domain.HearAboutUs;
import com.travelportal.domain.HotelRegistration;
import com.travelportal.domain.NatureOfBusiness;
import com.travelportal.domain.Salutation;
import com.travelportal.domain.allotment.AllotmentMarket;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name = "agent_registration")
public class AgentRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long agentCode;
	private String firstName;
	private String lastName;
	@OneToOne
	private Salutation title;
	@OneToOne
	private Country country;
	@OneToOne
	private NatureOfBusiness business;
	@OneToOne
	private HearAboutUs hear;
	private String Position;
	private String companyName;
	private String companyAddress;
	
	@OneToOne
	private City city;	
	private String postalCode;
	private String paymentMethod;
	private String financeEmailAddr;
	private String receiveNet;
	private String commission;
	@OneToOne
	private Currency currency;	
	private String EmailAddr;
	private String loginId;
	private String directCode;
	private String directTelNo;
	private String faxCode;
	private String faxTelNo;
	private String webSite;
	private String agree;
	private String status;
	private String password;
		
	
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public long getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(long agentCode) {
		this.agentCode = agentCode;
	}

	public Salutation getTitle() {
		return title;
	}

	public void setTitle(Salutation title) {
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public NatureOfBusiness getBusiness() {
		return business;
	}

	public void setBusiness(NatureOfBusiness business) {
		this.business = business;
	}

	public HearAboutUs getHear() {
		return hear;
	}

	public void setHear(HearAboutUs hear) {
		this.hear = hear;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getFinanceEmailAddr() {
		return financeEmailAddr;
	}

	public void setFinanceEmailAddr(String financeEmailAddr) {
		this.financeEmailAddr = financeEmailAddr;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getEmailAddr() {
		return EmailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		EmailAddr = emailAddr;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getDirectCode() {
		return directCode;
	}

	public void setDirectCode(String directCode) {
		this.directCode = directCode;
	}

	public String getDirectTelNo() {
		return directTelNo;
	}

	public void setDirectTelNo(String directTelNo) {
		this.directTelNo = directTelNo;
	}

	public String getFaxCode() {
		return faxCode;
	}

	public void setFaxCode(String faxCode) {
		this.faxCode = faxCode;
	}

	public String getFaxTelNo() {
		return faxTelNo;
	}

	public void setFaxTelNo(String faxTelNo) {
		this.faxTelNo = faxTelNo;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	
	

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
		
	public String getReceiveNet() {
		return receiveNet;
	}

	public void setReceiveNet(String receiveNet) {
		this.receiveNet = receiveNet;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public static AgentRegistration doLogin(String code, String loginid, String password) {
		Query query = JPA.em().createQuery("select h from AgentRegistration h where h.id = ?1 and h.password = ?2 and h.loginId = ?3 and h.status = 'APPROVED'").setParameter(1, code).setParameter(2, password).setParameter(3, loginid);
		return (AgentRegistration) query.getSingleResult();
	}
	
	public static AgentRegistration getallAgentCode(long code) {
		return (AgentRegistration) JPA.em().createQuery("select c from AgentRegistration c where c.agentCode = ?1").setParameter(1, code).getSingleResult();
	}
	
	public static List<AgentRegistration> getAllPendingAgent() {
		Query query = JPA.em().createQuery("select h from AgentRegistration h where h.status = 'PENDING'");
		return (List<AgentRegistration>) query.getResultList();
	}
	//Query q = JPA.em().createQuery("select c.fromDate,c.toDate from RateMeta c where c.roomType.roomId = :roomid and c.currency = :currencyName GROUP BY c.fromDate , c.toDate");
	public static List getAllApprovedAgent() {
		Query query = JPA.em().createQuery("select DISTINCT h.country from AgentRegistration h where h.status = 'APPROVED'");
		return query.getResultList();
	}
	
	public static List getApprovedAgent() {
		Query query = JPA.em().createQuery("select h from AgentRegistration h where h.status = 'APPROVED'");
		return query.getResultList();
	}
	
public static List<AgentRegistration> getAgentData(int code) {/*List<Integer> rateid*/
			Query q = JPA.em().createQuery("select c from AgentRegistration c where c.country.countryCode = ?1");
			q.setParameter(1, code);
			return (List<AgentRegistration>) q.getResultList();
	
   }
	
	public static List<AgentRegistration> getAllRejectedAgent() {
		Query query = JPA.em().createQuery("select h from AgentRegistration h where h.status = 'REJECTED'");
		return (List<AgentRegistration>) query.getResultList();
	}
	public static AgentRegistration findById(long id) {
		try{
			System.out.println(id);
		Query query = JPA.em().createQuery("select h from AgentRegistration h where h.id = ?1").setParameter(1, id);
		return (AgentRegistration) query.getSingleResult();
	}
	catch(Exception ex){
		return null;
	}
	}
	

	@Transactional
    public void save() {
		JPA.em().persist(this);
        JPA.em().flush();     
    }
      
    @Transactional
    public void delete() {
        JPA.em().remove(this);
    }
    
    @Transactional
    public void merge() {
        JPA.em().merge(this);
    }
    
    @Transactional
    public void refresh() {
        JPA.em().refresh(this);
    }
    
    
	
}
