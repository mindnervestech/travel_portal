package com.travelportal.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Entity
@Table(name="hotel_profile")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE) 

public class HotelProfile {
	
	@Column(name="supplier_code")
	private Long supplier_code;
	
	@Column(name="id", unique=true)
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="supplier_nm")
	private String supplierName;
	
	@Column(name="hotel_nm")
	private String hotelName;
	
	@Column(name="address")
	private String address;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Country country; //countryCode value should be in supplier profile table.
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private City city;
	
	@Column(name="zip_code")
	private String zipCode;
	
	@Column(name="hotel_part_of_chain")
	private boolean partOfChain;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private HotelBrands hoteBrands;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private HotelChain chainHotel;
	
	@Column(name="safetyMeasuresCompliance")
	private boolean safetyMeasuresCompliance;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Currency currency;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private MarketPolicyTypes marketPolicyType;
	
	@Column(name="star_ratings_value")
	private int startRatings;  //1,2 ...
	
	@Column(name="password")
	private String password;
	
	@Column(name="verify_password")
	private String verifyPassword;
	
	@Column(name="hotel_profile_desc")
	private String hotelProfileDesc;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<HotelServices> services;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Location location; 
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private ShoppingFacility shoppingFacility; //drop down of different values
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private NightLife nightLife;
	
	
	@Column(name="hotel_built_year")
	private int hotelBuiltYear;
	
	@Column(name="hotel_renovation_year")
	private int hotelRenovationYear;
	
	@Column(name="fire_safety_compliance")
	private String fireSafetyCompliance;
	
	@Column(name="no_of_floors")
	private int noOfFloors;
	
	@Column(name="no_of_rooms")
	private int noOfRooms;
	
	@Column(name="hotel_web_site")
	private String hotelWebSite;
	
	@Column(name="hotel_email_addr")
	private String hotelEmailAddr;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private InternalContacts internalContacts;
	
	@Column(name="general_mgr")
	private String hotelGeneralManager;
	
	@Column(name="general_mgr_email")
	private String generalMgrEmail;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private HotelPrivateContacts privateContacts;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private BusinessCommunication communication;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private BillingInformation billingInfo;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private HotelTaxes hotelTaxes;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private HotelTariffDetails tariffNotes;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private HotelSupplementaryFacilities supplimentaryFacilities;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<HotelMealPlan> meanPlans;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<HotelAmenities> amenities;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<HotelAttractions> hotelareaattraction;
	
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<TransportationDirection> transportCode;
	
	
	private String location1;
	private String location2;
	private String location3;
	
	
	
	
	public List<TransportationDirection> getTransportCode() {
		return transportCode;
	}

	public void setTransportCode(List<TransportationDirection> transportCode) {
		this.transportCode = transportCode;
	}
	public void addTransportCode(TransportationDirection transportCode) {
		if(this.transportCode == null){
			this.transportCode = new ArrayList<>();
		}
		this.transportCode.add(transportCode);
	}
	
	

	public List<HotelAttractions> getHotelareaattraction() {
		return hotelareaattraction;
	}
	
	public void setHotelareaattraction(List<HotelAttractions> hotelareaattraction) {
		this.hotelareaattraction = hotelareaattraction;
	}

	public void addHotelareaattraction(HotelAttractions hotelareaattraction) {
		if(this.hotelareaattraction == null){
			this.hotelareaattraction = new ArrayList<>();
		}
		this.hotelareaattraction.add(hotelareaattraction);
	}
	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}
	/**
	 * @param supplierName the supplierName to set
	 */	
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	
	/**
	 * @return the hotelName
	 */
	public String getHotelName() {
		return hotelName;
	}
	/**
	 * @param hotelName the hotelName to set
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(City city) {
		this.city = city;
	}
	/**
	 * @return the hoteBrands
	 */
	public HotelBrands getHoteBrands() {
		return hoteBrands;
	}
	/**
	 * @param hoteBrands the hoteBrands to set
	 */
	public void setHoteBrands(HotelBrands hoteBrands) {
		this.hoteBrands = hoteBrands;
	}
	/**
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the verifyPassword
	 */
	public String getVerifyPassword() {
		return verifyPassword;
	}
	/**
	 * @param verifyPassword the verifyPassword to set
	 */
	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}
	
	public static HotelProfile findById(final Integer profileId) {
		return JPA.em().find(HotelProfile.class, profileId);
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * @return the partOfChain
	 */
	public boolean isPartOfChain() {
		return partOfChain;
	}
	/**
	 * @param partOfChain the partOfChain to set
	 */
	public void setPartOfChain(boolean partOfChain) {
		this.partOfChain = partOfChain;
	}
	/**
	 * @return the chainHotel
	 */
	public HotelChain getChainHotel() {
		return chainHotel;
	}
	/**
	 * @param chainHotel the chainHotel to set
	 */
	public void setChainHotel(HotelChain chainHotel) {
		this.chainHotel = chainHotel;
	}
	/**
	 * @return the safetyMeasuresCompliance
	 */
	public boolean isSafetyMeasuresCompliance() {
		return safetyMeasuresCompliance;
	}
	/**
	 * @param safetyMeasuresCompliance the safetyMeasuresCompliance to set
	 */
	public void setSafetyMeasuresCompliance(boolean safetyMeasuresCompliance) {
		this.safetyMeasuresCompliance = safetyMeasuresCompliance;
	}
	/**
	 * @return the marketPolicyType
	 */
	public MarketPolicyTypes getMarketPolicyType() {
		return marketPolicyType;
	}
	/**
	 * @param marketPolicyType the marketPolicyType to set
	 */
	public void setMarketPolicyType(MarketPolicyTypes marketPolicyType) {
		this.marketPolicyType = marketPolicyType;
	}
	/**
	 * @return the startRatings
	 */
	public int getStartRatings() {
		return startRatings;
	}
	/**
	 * @param startRatings the startRatings to set
	 */
	public void setStartRatings(int startRatings) {
		this.startRatings = startRatings;
	}
	/**
	 * @return the services
	 */
	public List<HotelServices> getServices() {
		return services;
	}
	
	public List<Integer> getIntListServices() {
		List<Integer> list = new ArrayList<>();
		for(HotelServices service : this.services){
			list.add(service.getServiceId());
		}
		return list;
	}
	/**
	 * @param services the services to set
	 */
	public void setServices(List<HotelServices> services) {
		this.services = services;
	}
	
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	/**
	 * @return the nightLife
	 */
	public NightLife getNightLife() {
		return nightLife;
	}
	/**
	 * @param nightLife the nightLife to set
	 */
	public void setNightLife(NightLife nightLife) {
		this.nightLife = nightLife;
	}
	/**
	 * @return the hotelBuiltYear
	 */
	public int getHotelBuiltYear() {
		return hotelBuiltYear;
	}
	/**
	 * @param hotelBuiltYear the hotelBuiltYear to set
	 */
	public void setHotelBuiltYear(int hotelBuiltYear) {
		this.hotelBuiltYear = hotelBuiltYear;
	}
	/**
	 * @return the hotelRenovationYear
	 */
	public int getHotelRenovationYear() {
		return hotelRenovationYear;
	}
	/**
	 * @param hotelRenovationYear the hotelRenovationYear to set
	 */
	public void setHotelRenovationYear(int hotelRenovationYear) {
		this.hotelRenovationYear = hotelRenovationYear;
	}
	/**
	 * @return the fireSafetyCompliance
	 */
	
	/**
	 * @return the noOfFloors
	 */
	public int getNoOfFloors() {
		return noOfFloors;
	}
	public String getFireSafetyCompliance() {
		return fireSafetyCompliance;
	}

	public void setFireSafetyCompliance(String fireSafetyCompliance) {
		this.fireSafetyCompliance = fireSafetyCompliance;
	}

	/**
	 * @param noOfFloors the noOfFloors to set
	 */
	public void setNoOfFloors(int noOfFloors) {
		this.noOfFloors = noOfFloors;
	}
	/**
	 * @return the noOfRooms
	 */
	public int getNoOfRooms() {
		return noOfRooms;
	}
	/**
	 * @param noOfRooms the noOfRooms to set
	 */
	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}
	/**
	 * @return the hotelWebSite
	 */
	public String getHotelWebSite() {
		return hotelWebSite;
	}
	/**
	 * @param hotelWebSite the hotelWebSite to set
	 */
	public void setHotelWebSite(String hotelWebSite) {
		this.hotelWebSite = hotelWebSite;
	}
	/**
	 * @return the hotelEmailAddr
	 */
	public String getHotelEmailAddr() {
		return hotelEmailAddr;
	}
	/**
	 * @param hotelEmailAddr the hotelEmailAddr to set
	 */
	public void setHotelEmailAddr(String hotelEmailAddr) {
		this.hotelEmailAddr = hotelEmailAddr;
	}
	
	/**
	 * @return the hotelGeneralManager
	 */
	public String getHotelGeneralManager() {
		return hotelGeneralManager;
	}
	/**
	 * @param hotelGeneralManager the hotelGeneralManager to set
	 */
	public void setHotelGeneralManager(String hotelGeneralManager) {
		this.hotelGeneralManager = hotelGeneralManager;
	}
	/**
	 * @return the generalMgrEmail
	 */
	public String getGeneralMgrEmail() {
		return generalMgrEmail;
	}
	/**
	 * @param generalMgrEmail the generalMgrEmail to set
	 */
	public void setGeneralMgrEmail(String generalMgrEmail) {
		this.generalMgrEmail = generalMgrEmail;
	}
	/**
	 * @return the communication
	 */
	public BusinessCommunication getCommunication() {
		return communication;
	}
	/**
	 * @param communication the communication to set
	 */
	public void setCommunication(BusinessCommunication communication) {
		this.communication = communication;
	}
	/**
	 * @return the billingInfo
	 */
	public BillingInformation getBillingInfo() {
		return billingInfo;
	}
	/**
	 * @param billingInfo the billingInfo to set
	 */
	public void setBillingInfo(BillingInformation billingInfo) {
		this.billingInfo = billingInfo;
	}
	/**
	 * @return the meanPlans
	 */
	public List<HotelMealPlan> getMeanPlans() {
		return meanPlans;
	}
	/**
	 * @param meanPlans the meanPlans to set
	 */
	public void setMeanPlans(List<HotelMealPlan> meanPlans) {
		this.meanPlans = meanPlans;
	}
	
	/**
	 * @return the hotelProfileDesc
	 */
	public String getHotelProfileDesc() {
		return hotelProfileDesc;
	}
	/**
	 * @param hotelProfileDesc the hotelProfileDesc to set
	 */
	public void setHotelProfileDesc(String hotelProfileDesc) {
		this.hotelProfileDesc = hotelProfileDesc;
	}
	
	/**
	 * @return the internalContacts
	 */
	public InternalContacts getInternalContacts() {
		return internalContacts;
	}
	/**
	 * @param internalContacts the internalContacts to set
	 */
	public void setInternalContacts(InternalContacts internalContacts) {
		this.internalContacts = internalContacts;
	}
	/**
	 * @return the shoppingFacility
	 */
	public ShoppingFacility getShoppingFacility() {
		return shoppingFacility;
	}
	/**
	 * @param shoppingFacility the shoppingFacility to set
	 */
	public void setShoppingFacility(ShoppingFacility shoppingFacility) {
		this.shoppingFacility = shoppingFacility;
	}
	/**
	 * @return the privateContacts
	 */
	public HotelPrivateContacts getPrivateContacts() {
		return privateContacts;
	}
	/**
	 * @param privateContacts the privateContacts to set
	 */
	public void setPrivateContacts(HotelPrivateContacts privateContacts) {
		this.privateContacts = privateContacts;
	}
	
	
	public void addPrivateContacts(HotelPrivateContacts privateContacts) {
		/*if(this.privateContacts == null){
			this.privateContacts = new ArrayList<>();
		}*/
		//this.privateContacts.(privateContacts);
	}
	
	/**
	 * @return the hotelTaxes
	 */
	public HotelTaxes getHotelTaxes() {
		return hotelTaxes;
	}
	/**
	 * @param hotelTaxes the hotelTaxes to set
	 */
	public void setHotelTaxes(HotelTaxes hotelTaxes) {
		this.hotelTaxes = hotelTaxes;
	}
	/**
	 * @return the tariffNotes
	 */
	public HotelTariffDetails getTariffNotes() {
		return tariffNotes;
	}
	/**
	 * @param tariffNotes the tariffNotes to set
	 */
	public void setTariffNotes(HotelTariffDetails tariffNotes) {
		this.tariffNotes = tariffNotes;
	}
	/**
	 * @return the supplimentaryFacilities
	 */
	public HotelSupplementaryFacilities getSupplimentaryFacilities() {
		return supplimentaryFacilities;
	}
	/**
	 * @param supplimentaryFacilities the supplimentaryFacilities to set
	 */
	public void setSupplimentaryFacilities(
			HotelSupplementaryFacilities supplimentaryFacilities) {
		this.supplimentaryFacilities = supplimentaryFacilities;
	}
	/**
	 * @return the amenities
	 */
	public Set<HotelAmenities> getAmenities() {
		return amenities;
	}
	/**
	 * @param amenities the amenities to set
	 */
	public void setAmenities(Set<HotelAmenities> amenities) {
		this.amenities.addAll(amenities);
	}
	
	
	public void addAmenities(Set<HotelAmenities> amenities) {
		this.amenities.addAll(amenities);
	}
	/**
	 * @return the supplier_code
	 */
	public Long getSupplier_code() {
		return supplier_code;
	}
	/**
	 * @param supplier_code the supplier_code to set
	 */
	public void setSupplier_code(Long supplier_code) {
		this.supplier_code = supplier_code;
	}
	
	public static HotelProfile findById(Long id) {
    	Query query = JPA.em().createQuery("Select a from HotelProfile a where a.id = ?1");
		query.setParameter(1, id);
    	return (HotelProfile) query.getSingleResult();
    }
	
	/*findAllData*/
	public static HotelProfile findAllData(Long supplierCode) {
		return (HotelProfile) JPA.em().createQuery("select c from HotelProfile c where c.id = ?1").setParameter(1,supplierCode).getSingleResult();
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the location1
	 */
	public String getLocation1() {
		return location1;
	}

	/**
	 * @param location1 the location1 to set
	 */
	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	/**
	 * @return the location2
	 */
	public String getLocation2() {
		return location2;
	}

	/**
	 * @param location2 the location2 to set
	 */
	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	/**
	 * @return the location3
	 */
	public String getLocation3() {
		return location3;
	}

	/**
	 * @param location3 the location3 to set
	 */
	public void setLocation3(String location3) {
		this.location3 = location3;
	}
	
	
}
