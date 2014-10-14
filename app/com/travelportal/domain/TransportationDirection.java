package com.travelportal.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
@Table(name="hotel_transportation_direction")
public class TransportationDirection {
	@Column(name="transport_code")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int transportCode;
	@Column(name="airport_nm")
	private String airportNm;
	@Column(name="airport_directions")
	private String airportdirections;
	@Column(name="airportdistance")
	private double airportdistance;
	@Column(name="airportdistance_type")
	private String airportdistanceType; //either km / miles etc..
	@Column(name="airporttime_require_in_minutes")
	private int airporttimeRequireInMinutes;  
	
	@Column(name="RailStation_nm")
	private String RailStationNm;
		@Column(name="RailStation_directions")
	private String RailStationdirections;
	@Column(name="RailStationdistance")
	private double RailStationdistance;
	@Column(name="RailStationdistance_type")
	private String RailStationdistanceType; //either km / miles etc..
	@Column(name="RailStationtime_require_in_minutes")
	private int RailStationtimeRequireInMinutes;  
	
	@Column(name="Subway_nm")
	private String SubwayNm;
	@Column(name="Subway_directions")
	private String Subwaydirections;
	@Column(name="Subwaydistance")
	private double Subwaydistance;
	@Column(name="Subwaydistance_type")
	private String SubwaydistanceType; //either km / miles etc..
	@Column(name="Subwaytime_require_in_minutes")
	private int SubwaytimeRequireInMinutes;  
	
	@Column(name="Cruise_nm")
	private String CruiseNm;
	@Column(name="Cruise_directions")
	private String Cruisedirections;
	@Column(name="Cruisedistance")
	private double Cruisedistance;
	@Column(name="Cruisedistance_type")
	private String CruisedistanceType; //either km / miles etc..
	@Column(name="Cruisetime_require_in_minutes")
	private int CruisetimeRequireInMinutes;  
	
	
	
	public int getTransportCode() {
		return transportCode;
	}

	public void setTransportCode(int transportCode) {
		this.transportCode = transportCode;
	}

	public String getAirportNm() {
		return airportNm;
	}

	public void setAirportNm(String airportNm) {
		this.airportNm = airportNm;
	}

	public String getAirportdirections() {
		return airportdirections;
	}

	public void setAirportdirections(String airportdirections) {
		this.airportdirections = airportdirections;
	}

	public double getAirportdistance() {
		return airportdistance;
	}

	public void setAirportdistance(double airportdistance) {
		this.airportdistance = airportdistance;
	}

	public String getAirportdistanceType() {
		return airportdistanceType;
	}

	public void setAirportdistanceType(String airportdistanceType) {
		this.airportdistanceType = airportdistanceType;
	}

	public int getAirporttimeRequireInMinutes() {
		return airporttimeRequireInMinutes;
	}

	public void setAirporttimeRequireInMinutes(int airporttimeRequireInMinutes) {
		this.airporttimeRequireInMinutes = airporttimeRequireInMinutes;
	}

	public String getRailStationNm() {
		return RailStationNm;
	}

	public void setRailStationNm(String railStationNm) {
		RailStationNm = railStationNm;
	}

	public String getRailStationdirections() {
		return RailStationdirections;
	}

	public void setRailStationdirections(String railStationdirections) {
		RailStationdirections = railStationdirections;
	}

	public double getRailStationdistance() {
		return RailStationdistance;
	}

	public void setRailStationdistance(double railStationdistance) {
		RailStationdistance = railStationdistance;
	}

	public String getRailStationdistanceType() {
		return RailStationdistanceType;
	}

	public void setRailStationdistanceType(String railStationdistanceType) {
		RailStationdistanceType = railStationdistanceType;
	}

	public int getRailStationtimeRequireInMinutes() {
		return RailStationtimeRequireInMinutes;
	}

	public void setRailStationtimeRequireInMinutes(
			int railStationtimeRequireInMinutes) {
		RailStationtimeRequireInMinutes = railStationtimeRequireInMinutes;
	}

	public String getSubwayNm() {
		return SubwayNm;
	}

	public void setSubwayNm(String subwayNm) {
		SubwayNm = subwayNm;
	}

	public String getSubwaydirections() {
		return Subwaydirections;
	}

	public void setSubwaydirections(String subwaydirections) {
		Subwaydirections = subwaydirections;
	}

	public double getSubwaydistance() {
		return Subwaydistance;
	}

	public void setSubwaydistance(double subwaydistance) {
		Subwaydistance = subwaydistance;
	}

	public String getSubwaydistanceType() {
		return SubwaydistanceType;
	}

	public void setSubwaydistanceType(String subwaydistanceType) {
		SubwaydistanceType = subwaydistanceType;
	}

	public int getSubwaytimeRequireInMinutes() {
		return SubwaytimeRequireInMinutes;
	}

	public void setSubwaytimeRequireInMinutes(int subwaytimeRequireInMinutes) {
		SubwaytimeRequireInMinutes = subwaytimeRequireInMinutes;
	}

	public String getCruiseNm() {
		return CruiseNm;
	}

	public void setCruiseNm(String cruiseNm) {
		CruiseNm = cruiseNm;
	}

	public String getCruisedirections() {
		return Cruisedirections;
	}

	public void setCruisedirections(String cruisedirections) {
		Cruisedirections = cruisedirections;
	}

	public double getCruisedistance() {
		return Cruisedistance;
	}

	public void setCruisedistance(double cruisedistance) {
		Cruisedistance = cruisedistance;
	}

	public String getCruisedistanceType() {
		return CruisedistanceType;
	}

	public void setCruisedistanceType(String cruisedistanceType) {
		CruisedistanceType = cruisedistanceType;
	}

	public int getCruisetimeRequireInMinutes() {
		return CruisetimeRequireInMinutes;
	}

	public void setCruisetimeRequireInMinutes(int cruisetimeRequireInMinutes) {
		CruisetimeRequireInMinutes = cruisetimeRequireInMinutes;
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
