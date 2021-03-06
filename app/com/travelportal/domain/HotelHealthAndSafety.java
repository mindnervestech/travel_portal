package com.travelportal.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="hotel_health_and_safety")
public class HotelHealthAndSafety {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="public_liability")
	private String publicLiability;
	@Column(name="publicLiability_expiry_date")
	private Date publicLiabilityexpiryDate;
	@Column(name="fire_risk")
	private String fireRisk;
	@Column(name="fireRisk_expiry_date")
	private Date fireRiskexpiryDate;
	@Column(name="local_tourist")
	private String localTourist;
	@Column(name="localTourist_expiry_date")
	private Date localTouristexpiryDate;
	@Column(name="internal_fire")
	private String internalFire;
	@Column(name="internalFire_expiry_date")
	private Date internalFireexpiryDate;
	@Column(name="haccp_certify")
	private String haccpCertify;
	@Column(name="haccpCertify_expiry_date")
	private Date haccpCertifyexpiryDate;
	@Column(name="records_for_fire")
	private String recordsForFire;
	@Column(name="recordsForFire_expiry_date")
	private Date recordsForFireexpiryDate;
	@Column(name="records_for_health")
	private String recordsForHealth;
	@Column(name="recordsForHealth_expiry_date")
	private Date recordsForHealthexpiryDate;
	/*@Column(name="expiry_date")
	private Date expiryDate;
	@Column(name="expiry_date1")
	private Date expiryDate1;*/
	@Column(name="supplier_code")
	private long supplierCode;
	
	/*----------------------1.FirePrecaution-----------------*/
	@Column(name="working_Fire_Alarm")
	private String workingFireAlarm;
	@Column(name="smoke_Detectors_In_PublicArea")
	private String smokeDetectorsInPublicArea;
	@Column(name="smoke_Detectors_In_GuestBedroom")
	private String smokeDetectorsInGuestBedroom;
	@Column(name="smoke_Detectors_In_Apartment")
	private String smokeDetectorsInApartment;
	@Column(name="system_At_LeastAnnually")
	private String systemAtLeastAnnually;
	@Column(name="internal_Fire_AlarmTest")
	private String internalFireAlarmTest;
	@Column(name="extinguishers_In_AllArea")
	private String extinguishersInAllArea;
	@Column(name="emergency_Lighting_Install")
	private String emergencyLightingInstall;
	@Column(name="limited_Walking_Abilities")
	private String limitedWalkingAbilities;
	/*-----------------------2.Exits and Corridors-----------------------*/
	@Column(name="how_many_exits")
	private String howManyExits;
	@Column(name="unlocked_At_AllTime")
	private String unlockedAtAllTime;
	@Column(name="exits_ClearlySigned")
	private String exitsClearlySigned;
	@Column(name="routes_Illuminated")
	private String routesIlluminated;
	@Column(name="usable_Staircase_From_AllFloors")
	private String usableStaircaseFromAllFloors;
	/*-----------------------3.Air Conditioning------------------------*/
	@Column(name="central")
	private String central;
	@Column(name="independentUnits")
	private String independentUnits;
	/*-------------------------4.Lifts---------------------------------*/
	@Column(name="floors_Accessible")
	private String floorsAccessible;
	@Column(name="internal_ClosingDoor")
	private String internalClosingDoor;
	@Column(name="relevant_SignageDisplay")
	private String relevantSignageDisplay;
	@Column(name="noSmoking")
	private String noSmoking;
	@Column(name="noUnaccompanied_Children")
	private String noUnaccompaniedChildren;
	@Column(name="event_Of_Fire")
	private String eventOfFire;
	/*-----------------------5.Bedrooms and Balconies--------------------------*/
	@Column(name="fireSafety_InstructionsPosted")
	private String fireSafetyInstructionsPosted;
	@Column(name="electrics_AutomaticallyDisconnect")
	private String electricsAutomaticallyDisconnect;
	@Column(name="rooms_Have_Balconies")
	private String roomsHaveBalconies;
	@Column(name="balconies_AtLeast1m")
	private String balconiesAtLeast1m;
	@Column(name="gaps_GreaterThan10cm")
	private String gapsGreaterThan10cm;
	@Column(name="anyAdjoiningRooms")
	private String anyAdjoiningRooms;
	@Column(name="how_Many")
	private String howMany;	
	/*-------------------------6.Kitchen's and Hygiene---------------------------*/
	@Column(name="self_Catering_Accommodation")
	private String selfCateringAccommodation;
	@Column(name="all_Kitchen_AppliancesRegularly")
	private String allKitchenAppliancesRegularly;
	@Column(name="self_CateringAccommodation_HaveFull")
	private String selfCateringAccommodationHaveFull;
	@Column(name="main_Kitchen")
	private String mainKitchen;
	@Column(name="stages_Of_FoodPreparation")
	private String stagesOfFoodPreparation;
	@Column(name="premises_Adequately_Proofed")
	private String premisesAdequatelyProofed;
	/*--------------------------7.Children's Facilities---------------------------------*/
	@Column(name="internal_ChildrenPlayArea")
	private String internalChildrenPlayArea;
	@Column(name="external_ChildrenPlayArea")
	private String externalChildrenPlayArea;
	@Column(name="supervise_TheKidClub")
	private String superviseTheKidClub;
	@Column(name="quality_ChecksOf_EquipmentAndFurniture")
	private String qualityChecksOfEquipmentAndFurniture;
	@Column(name="month_kid")
	public String monthkid;
	
	/*-------------------------8.Swimming Pool -------------------------------------------*/
	@Column(name="pool_RulesAndRegulation")
	private String poolRulesAndRegulation;
	@Column(name="openingClosing_Times")
	private String openingClosingTimes;
	@Column(name="depth_Information")
	private String depthInformation;
	@Column(name="noDiving")
	private String noDiving;
	@Column(name="no_Unaccompanied_ChildrenPool")
	private String noUnaccompaniedChildrenPool;
	@Column(name="life_savingEquipment")
	private String lifesavingEquipment;
	@Column(name="glass_AroundThePoolArea")
	private String glassAroundThePoolArea;
	@Column(name="life_guardOrQualifiedPerson")
	private String lifeguardOrQualifiedPerson;
	@Column(name="there_ChildrenPool")
	private String thereChildrenPool;
	@Column(name="separated_FromTheAdultPool")
	private String separatedFromTheAdultPool;
	@Column(name="sufficient_DepthMarkings")
	private String sufficientDepthMarkings;
	@Column(name="thePool_CleanedDaily")
	private String thePoolCleanedDaily;
	@Column(name="records_Kept")
	private String recordsKept;
	@Column(name="heated_PoolInTheProperty")
	private String heatedPoolInTheProperty;
	@Column(name="month_Operational")
	public String monthOperational;
	/*--------------------------9.Gas Water Heaters---------------------------------*/
	@Column(name="gas_WaterHeaters")
	private String gasWaterHeaters;
	@Column(name="internal")
	private String internal;
	@Column(name="external")
	private String external;
	@Column(name="servicing")
	private String servicing;
	@Column(name="maintenance")
	private String maintenance;
	
	/*---------------------------10.CCTV Status----------------------------------------*/
	@Column(name="cctv_Area")
	public String cctvArea;	
	/*--------------------------11.Additional Info---------------------------------*/
	@Column(name="additional_InformationOrComments")
	private String additionalInformationOrComments;
	@Column(name="name")
	private String name;
	@Column(name="designation")
	private String designation;
	/*------------------------------12.doc-------------------------------*/
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<ImgPath> imgpath;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPublicLiability() {
		return publicLiability;
	}
	public void setPublicLiability(String publicLiability) {
		this.publicLiability = publicLiability;
	}
	public String getFireRisk() {
		return fireRisk;
	}
	public void setFireRisk(String fireRisk) {
		this.fireRisk = fireRisk;
	}
	public String getLocalTourist() {
		return localTourist;
	}
	public void setLocalTourist(String localTourist) {
		this.localTourist = localTourist;
	}
	public String getInternalFire() {
		return internalFire;
	}
	public void setInternalFire(String internalFire) {
		this.internalFire = internalFire;
	}
	public String getHaccpCertify() {
		return haccpCertify;
	}
	public void setHaccpCertify(String haccpCertify) {
		this.haccpCertify = haccpCertify;
	}
	public String getRecordsForFire() {
		return recordsForFire;
	}
	public void setRecordsForFire(String recordsForFire) {
		this.recordsForFire = recordsForFire;
	}
	public String getRecordsForHealth() {
		return recordsForHealth;
	}
	public void setRecordsForHealth(String recordsForHealth) {
		this.recordsForHealth = recordsForHealth;
	}
	public Date getPublicLiabilityexpiryDate() {
		return publicLiabilityexpiryDate;
	}
	public void setPublicLiabilityexpiryDate(Date publicLiabilityexpiryDate) {
		this.publicLiabilityexpiryDate = publicLiabilityexpiryDate;
	}
	public Date getFireRiskexpiryDate() {
		return fireRiskexpiryDate;
	}
	public void setFireRiskexpiryDate(Date fireRiskexpiryDate) {
		this.fireRiskexpiryDate = fireRiskexpiryDate;
	}
	public Date getLocalTouristexpiryDate() {
		return localTouristexpiryDate;
	}
	public void setLocalTouristexpiryDate(Date localTouristexpiryDate) {
		this.localTouristexpiryDate = localTouristexpiryDate;
	}
	public Date getInternalFireexpiryDate() {
		return internalFireexpiryDate;
	}
	public void setInternalFireexpiryDate(Date internalFireexpiryDate) {
		this.internalFireexpiryDate = internalFireexpiryDate;
	}
	public Date getHaccpCertifyexpiryDate() {
		return haccpCertifyexpiryDate;
	}
	public void setHaccpCertifyexpiryDate(Date haccpCertifyexpiryDate) {
		this.haccpCertifyexpiryDate = haccpCertifyexpiryDate;
	}
	public Date getRecordsForFireexpiryDate() {
		return recordsForFireexpiryDate;
	}
	public void setRecordsForFireexpiryDate(Date recordsForFireexpiryDate) {
		this.recordsForFireexpiryDate = recordsForFireexpiryDate;
	}
	public Date getRecordsForHealthexpiryDate() {
		return recordsForHealthexpiryDate;
	}
	public void setRecordsForHealthexpiryDate(Date recordsForHealthexpiryDate) {
		this.recordsForHealthexpiryDate = recordsForHealthexpiryDate;
	}
	/*public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Date getExpiryDate1() {
		return expiryDate1;
	}
	public void setExpiryDate1(Date expiryDate1) {
		this.expiryDate1 = expiryDate1;
	}*/
	public long getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getWorkingFireAlarm() {
		return workingFireAlarm;
	}
	public void setWorkingFireAlarm(String workingFireAlarm) {
		this.workingFireAlarm = workingFireAlarm;
	}
	public String getSmokeDetectorsInPublicArea() {
		return smokeDetectorsInPublicArea;
	}
	public void setSmokeDetectorsInPublicArea(String smokeDetectorsInPublicArea) {
		this.smokeDetectorsInPublicArea = smokeDetectorsInPublicArea;
	}
	public String getSmokeDetectorsInGuestBedroom() {
		return smokeDetectorsInGuestBedroom;
	}
	public void setSmokeDetectorsInGuestBedroom(String smokeDetectorsInGuestBedroom) {
		this.smokeDetectorsInGuestBedroom = smokeDetectorsInGuestBedroom;
	}
	public String getSmokeDetectorsInApartment() {
		return smokeDetectorsInApartment;
	}
	public void setSmokeDetectorsInApartment(String smokeDetectorsInApartment) {
		this.smokeDetectorsInApartment = smokeDetectorsInApartment;
	}
	public String getSystemAtLeastAnnually() {
		return systemAtLeastAnnually;
	}
	public void setSystemAtLeastAnnually(String systemAtLeastAnnually) {
		this.systemAtLeastAnnually = systemAtLeastAnnually;
	}
	public String getInternalFireAlarmTest() {
		return internalFireAlarmTest;
	}
	public void setInternalFireAlarmTest(String internalFireAlarmTest) {
		this.internalFireAlarmTest = internalFireAlarmTest;
	}
	public String getExtinguishersInAllArea() {
		return extinguishersInAllArea;
	}
	public void setExtinguishersInAllArea(String extinguishersInAllArea) {
		this.extinguishersInAllArea = extinguishersInAllArea;
	}
	public String getEmergencyLightingInstall() {
		return emergencyLightingInstall;
	}
	public void setEmergencyLightingInstall(String emergencyLightingInstall) {
		this.emergencyLightingInstall = emergencyLightingInstall;
	}
	public String getLimitedWalkingAbilities() {
		return limitedWalkingAbilities;
	}
	public void setLimitedWalkingAbilities(String limitedWalkingAbilities) {
		this.limitedWalkingAbilities = limitedWalkingAbilities;
	}
		
		
	public String getHowManyExits() {
		return howManyExits;
	}
	public void setHowManyExits(String howManyExits) {
		this.howManyExits = howManyExits;
	}
	public String getUnlockedAtAllTime() {
		return unlockedAtAllTime;
	}
	public void setUnlockedAtAllTime(String unlockedAtAllTime) {
		this.unlockedAtAllTime = unlockedAtAllTime;
	}
	public String getExitsClearlySigned() {
		return exitsClearlySigned;
	}
	public void setExitsClearlySigned(String exitsClearlySigned) {
		this.exitsClearlySigned = exitsClearlySigned;
	}
	public String getRoutesIlluminated() {
		return routesIlluminated;
	}
	public void setRoutesIlluminated(String routesIlluminated) {
		this.routesIlluminated = routesIlluminated;
	}
	public String getUsableStaircaseFromAllFloors() {
		return usableStaircaseFromAllFloors;
	}
	public void setUsableStaircaseFromAllFloors(String usableStaircaseFromAllFloors) {
		this.usableStaircaseFromAllFloors = usableStaircaseFromAllFloors;
	}
	
	
		
	public String getCentral() {
		return central;
	}
	public void setCentral(String central) {
		this.central = central;
	}
	public String getIndependentUnits() {
		return independentUnits;
	}
	public void setIndependentUnits(String independentUnits) {
		this.independentUnits = independentUnits;
	}
	
	
	
	
	public String getFloorsAccessible() {
		return floorsAccessible;
	}
	public void setFloorsAccessible(String floorsAccessible) {
		this.floorsAccessible = floorsAccessible;
	}
	public String getInternalClosingDoor() {
		return internalClosingDoor;
	}
	public void setInternalClosingDoor(String internalClosingDoor) {
		this.internalClosingDoor = internalClosingDoor;
	}
	public String getRelevantSignageDisplay() {
		return relevantSignageDisplay;
	}
	public void setRelevantSignageDisplay(String relevantSignageDisplay) {
		this.relevantSignageDisplay = relevantSignageDisplay;
	}
	public String getNoSmoking() {
		return noSmoking;
	}
	public void setNoSmoking(String noSmoking) {
		this.noSmoking = noSmoking;
	}
	public String getNoUnaccompaniedChildren() {
		return noUnaccompaniedChildren;
	}
	public void setNoUnaccompaniedChildren(String noUnaccompaniedChildren) {
		this.noUnaccompaniedChildren = noUnaccompaniedChildren;
	}
	public String getEventOfFire() {
		return eventOfFire;
	}
	public void setEventOfFire(String eventOfFire) {
		this.eventOfFire = eventOfFire;
	}
	
	
	public String getFireSafetyInstructionsPosted() {
		return fireSafetyInstructionsPosted;
	}
	public void setFireSafetyInstructionsPosted(String fireSafetyInstructionsPosted) {
		this.fireSafetyInstructionsPosted = fireSafetyInstructionsPosted;
	}
	public String getElectricsAutomaticallyDisconnect() {
		return electricsAutomaticallyDisconnect;
	}
	public void setElectricsAutomaticallyDisconnect(
			String electricsAutomaticallyDisconnect) {
		this.electricsAutomaticallyDisconnect = electricsAutomaticallyDisconnect;
	}
	public String getRoomsHaveBalconies() {
		return roomsHaveBalconies;
	}
	public void setRoomsHaveBalconies(String roomsHaveBalconies) {
		this.roomsHaveBalconies = roomsHaveBalconies;
	}
	public String getBalconiesAtLeast1m() {
		return balconiesAtLeast1m;
	}
	public void setBalconiesAtLeast1m(String balconiesAtLeast1m) {
		this.balconiesAtLeast1m = balconiesAtLeast1m;
	}
	public String getGapsGreaterThan10cm() {
		return gapsGreaterThan10cm;
	}
	public void setGapsGreaterThan10cm(String gapsGreaterThan10cm) {
		this.gapsGreaterThan10cm = gapsGreaterThan10cm;
	}
	public String getAnyAdjoiningRooms() {
		return anyAdjoiningRooms;
	}
	public void setAnyAdjoiningRooms(String anyAdjoiningRooms) {
		this.anyAdjoiningRooms = anyAdjoiningRooms;
	}
	public String getHowMany() {
		return howMany;
	}
	public void setHowMany(String howMany) {
		this.howMany = howMany;
	}
	
		
	
	public String getSelfCateringAccommodation() {
		return selfCateringAccommodation;
	}
	public void setSelfCateringAccommodation(String selfCateringAccommodation) {
		this.selfCateringAccommodation = selfCateringAccommodation;
	}
	public String getAllKitchenAppliancesRegularly() {
		return allKitchenAppliancesRegularly;
	}
	public void setAllKitchenAppliancesRegularly(
			String allKitchenAppliancesRegularly) {
		this.allKitchenAppliancesRegularly = allKitchenAppliancesRegularly;
	}
	public String getSelfCateringAccommodationHaveFull() {
		return selfCateringAccommodationHaveFull;
	}
	public void setSelfCateringAccommodationHaveFull(
			String selfCateringAccommodationHaveFull) {
		this.selfCateringAccommodationHaveFull = selfCateringAccommodationHaveFull;
	}
	public String getMainKitchen() {
		return mainKitchen;
	}
	public void setMainKitchen(String mainKitchen) {
		this.mainKitchen = mainKitchen;
	}
	public String getStagesOfFoodPreparation() {
		return stagesOfFoodPreparation;
	}
	public void setStagesOfFoodPreparation(String stagesOfFoodPreparation) {
		this.stagesOfFoodPreparation = stagesOfFoodPreparation;
	}
	public String getPremisesAdequatelyProofed() {
		return premisesAdequatelyProofed;
	}
	public void setPremisesAdequatelyProofed(String premisesAdequatelyProofed) {
		this.premisesAdequatelyProofed = premisesAdequatelyProofed;
	}
	
	
	public String getInternalChildrenPlayArea() {
		return internalChildrenPlayArea;
	}
	public void setInternalChildrenPlayArea(String internalChildrenPlayArea) {
		this.internalChildrenPlayArea = internalChildrenPlayArea;
	}
	public String getExternalChildrenPlayArea() {
		return externalChildrenPlayArea;
	}
	public void setExternalChildrenPlayArea(String externalChildrenPlayArea) {
		this.externalChildrenPlayArea = externalChildrenPlayArea;
	}
	public String getSuperviseTheKidClub() {
		return superviseTheKidClub;
	}
	public void setSuperviseTheKidClub(String superviseTheKidClub) {
		this.superviseTheKidClub = superviseTheKidClub;
	}
	public String getQualityChecksOfEquipmentAndFurniture() {
		return qualityChecksOfEquipmentAndFurniture;
	}
	public void setQualityChecksOfEquipmentAndFurniture(String qualityChecksOfEquipmentAndFurniture) 
	{
		this.qualityChecksOfEquipmentAndFurniture = qualityChecksOfEquipmentAndFurniture;
	}
	
	public String getMonthkid() {
		return monthkid;
	}
	public void setMonthkid(String monthkid) {
		this.monthkid = monthkid;
	}
	
		
	public String getPoolRulesAndRegulation() {
		return poolRulesAndRegulation;
	}
	public void setPoolRulesAndRegulation(String poolRulesAndRegulation) {
		this.poolRulesAndRegulation = poolRulesAndRegulation;
	}
	public String getOpeningClosingTimes() {
		return openingClosingTimes;
	}
	public void setOpeningClosingTimes(String openingClosingTimes) {
		this.openingClosingTimes = openingClosingTimes;
	}
	public String getDepthInformation() {
		return depthInformation;
	}
	public void setDepthInformation(String depthInformation) {
		this.depthInformation = depthInformation;
	}
	public String getNoDiving() {
		return noDiving;
	}
	public void setNoDiving(String noDiving) {
		this.noDiving = noDiving;
	}
	public String getNoUnaccompaniedChildrenPool() {
		return noUnaccompaniedChildrenPool;
	}
	public void setNoUnaccompaniedChildrenPool(String noUnaccompaniedChildrenPool) {
		this.noUnaccompaniedChildrenPool = noUnaccompaniedChildrenPool;
	}
	public String getLifesavingEquipment() {
		return lifesavingEquipment;
	}
	public void setLifesavingEquipment(String lifesavingEquipment) {
		this.lifesavingEquipment = lifesavingEquipment;
	}
	public String getGlassAroundThePoolArea() {
		return glassAroundThePoolArea;
	}
	public void setGlassAroundThePoolArea(String glassAroundThePoolArea) {
		this.glassAroundThePoolArea = glassAroundThePoolArea;
	}
	public String getLifeguardOrQualifiedPerson() {
		return lifeguardOrQualifiedPerson;
	}
	public void setLifeguardOrQualifiedPerson(String lifeguardOrQualifiedPerson) {
		this.lifeguardOrQualifiedPerson = lifeguardOrQualifiedPerson;
	}
	public String getThereChildrenPool() {
		return thereChildrenPool;
	}
	public void setThereChildrenPool(String thereChildrenPool) {
		this.thereChildrenPool = thereChildrenPool;
	}
	public String getSeparatedFromTheAdultPool() {
		return separatedFromTheAdultPool;
	}
	public void setSeparatedFromTheAdultPool(String separatedFromTheAdultPool) {
		this.separatedFromTheAdultPool = separatedFromTheAdultPool;
	}
	public String getSufficientDepthMarkings() {
		return sufficientDepthMarkings;
	}
	public void setSufficientDepthMarkings(String sufficientDepthMarkings) {
		this.sufficientDepthMarkings = sufficientDepthMarkings;
	}
	public String getThePoolCleanedDaily() {
		return thePoolCleanedDaily;
	}
	public void setThePoolCleanedDaily(String thePoolCleanedDaily) {
		this.thePoolCleanedDaily = thePoolCleanedDaily;
	}
	public String getRecordsKept() {
		return recordsKept;
	}
	public void setRecordsKept(String recordsKept) {
		this.recordsKept = recordsKept;
	}
	public String getHeatedPoolInTheProperty() {
		return heatedPoolInTheProperty;
	}
	public void setHeatedPoolInTheProperty(String heatedPoolInTheProperty) {
		this.heatedPoolInTheProperty = heatedPoolInTheProperty;
	}
	public String getMonthOperational() {
		return monthOperational;
	}
	public void setMonthOperational(String monthOperational) {
		this.monthOperational = monthOperational;
	}
	public String getGasWaterHeaters() {
		return gasWaterHeaters;
	}
	public void setGasWaterHeaters(String gasWaterHeaters) {
		this.gasWaterHeaters = gasWaterHeaters;
	}
	public String getInternal() {
		return internal;
	}
	public void setInternal(String internal) {
		this.internal = internal;
	}
	
	
	public String getExternal() {
		return external;
	}
	public void setExternal(String external) {
		this.external = external;
	}
	public String getServicing() {
		return servicing;
	}
	public void setServicing(String servicing) {
		this.servicing = servicing;
	}
	public String getMaintenance() {
		return maintenance;
	}
	public void setMaintenance(String maintenance) {
		this.maintenance = maintenance;
	}
	
		
	public String getCctvArea() {
		return cctvArea;
	}
	public void setCctvArea(String cctvArea) {
		this.cctvArea = cctvArea;
	}
	public String getAdditionalInformationOrComments() {
		return additionalInformationOrComments;
	}
	public void setAdditionalInformationOrComments(
			String additionalInformationOrComments) {
		this.additionalInformationOrComments = additionalInformationOrComments;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
	public List<ImgPath> getImgpath() {
		return imgpath;
	}
	public void setImgpath(List<ImgPath> imgpath) {
		this.imgpath = imgpath;
	}
	
	public void addImgpath(ImgPath imgpath) {
		this.imgpath.add(imgpath);
	}
	
	public static List<HotelHealthAndSafety> getdocument(int id) {
		return JPA.em().createQuery("select c from HotelHealthAndSafety c where c.id = ?1").setParameter(1, id).getResultList();
	}
	
	public static List<HotelHealthAndSafety> getdocumentList(long supplierCode) {
		return JPA.em().createQuery("select c from HotelHealthAndSafety c where c.supplierCode = ?1").setParameter(1, supplierCode).getResultList();
	}
	
	public static HotelHealthAndSafety findById(Long supplierCode) {
		try
		{
		return (HotelHealthAndSafety) JPA.em().createQuery("select c from HotelHealthAndSafety c where c.supplierCode = ?1").setParameter(1, supplierCode).getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
    }
	
	 public static HotelHealthAndSafety HealthSafetyfindById(int id) {
	    	Query query = JPA.em().createQuery("Select a from HotelHealthAndSafety a where a.id = ?1");
			query.setParameter(1, id);
	    	return (HotelHealthAndSafety) query.getSingleResult();
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
	
	
	
	
	
	/*public static HotelHealthAndSafety getHotelBrandsbyCode(int code) {
		
		return (HotelHealthAndSafety) JPA.em().createQuery("select c from HotelHealthAndSafety c where c.brandsCode = ?1").setParameter(1, code).getSingleResult();
	}*/
	
}
