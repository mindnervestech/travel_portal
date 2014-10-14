package com.travelportal.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class TransportationDirectionsVM {
	
		
	private int transportCode;
	private String airportName;
	private String airportdirections;
	private double airportdistance;
	private String airportkm;//either km / miles etc..
	private int airportminutes;  
	
	private String railName;
	private String raildirections;
	private double raildistance;
	private String railkm;//either km / miles etc..
	private int railminutes; 
	
    private String subwayName;
	private String subwaydirections;
	private double subwaydistance;
	private String subwaykm;//either km / miles etc..
	private int subwayminutes; 
	
	private String cruiseName;
	private String cruisedirections;
	private double cruisedistance;
	private String cruisekm;//either km / miles etc..
	private int cruiseminutes;
	
	
	
	
	public int getTransportCode() {
		return transportCode;
	}
	public void setTransportCode(int transportCode) {
		this.transportCode = transportCode;
	}
	public String getAirportName() {
		return airportName;
	}
	public void setAirportName(String airportName) {
		this.airportName = airportName;
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
	public String getAirportkm() {
		return airportkm;
	}
	public void setAirportkm(String airportkm) {
		this.airportkm = airportkm;
	}
	public int getAirportminutes() {
		return airportminutes;
	}
	public void setAirportminutes(int airportminutes) {
		this.airportminutes = airportminutes;
	}
	public String getRailName() {
		return railName;
	}
	public void setRailName(String railName) {
		this.railName = railName;
	}
	public String getRaildirections() {
		return raildirections;
	}
	public void setRaildirections(String raildirections) {
		this.raildirections = raildirections;
	}
	public double getRaildistance() {
		return raildistance;
	}
	public void setRaildistance(double raildistance) {
		this.raildistance = raildistance;
	}
	public String getRailkm() {
		return railkm;
	}
	public void setRailkm(String railkm) {
		this.railkm = railkm;
	}
	public int getRailminutes() {
		return railminutes;
	}
	public void setRailminutes(int railminutes) {
		this.railminutes = railminutes;
	}
	public String getSubwayName() {
		return subwayName;
	}
	public void setSubwayName(String subwayName) {
		this.subwayName = subwayName;
	}
	public String getSubwaydirections() {
		return subwaydirections;
	}
	public void setSubwaydirections(String subwaydirections) {
		this.subwaydirections = subwaydirections;
	}
	public double getSubwaydistance() {
		return subwaydistance;
	}
	public void setSubwaydistance(double subwaydistance) {
		this.subwaydistance = subwaydistance;
	}
	public String getSubwaykm() {
		return subwaykm;
	}
	public void setSubwaykm(String subwaykm) {
		this.subwaykm = subwaykm;
	}
	public int getSubwayminutes() {
		return subwayminutes;
	}
	public void setSubwayminutes(int subwayminutes) {
		this.subwayminutes = subwayminutes;
	}
	public String getCruiseName() {
		return cruiseName;
	}
	public void setCruiseName(String cruiseName) {
		this.cruiseName = cruiseName;
	}
	public String getCruisedirections() {
		return cruisedirections;
	}
	public void setCruisedirections(String cruisedirections) {
		this.cruisedirections = cruisedirections;
	}
	public double getCruisedistance() {
		return cruisedistance;
	}
	public void setCruisedistance(double cruisedistance) {
		this.cruisedistance = cruisedistance;
	}
	public String getCruisekm() {
		return cruisekm;
	}
	public void setCruisekm(String cruisekm) {
		this.cruisekm = cruisekm;
	}
	public int getCruiseminutes() {
		return cruiseminutes;
	}
	public void setCruiseminutes(int cruiseminutes) {
		this.cruiseminutes = cruiseminutes;
	}
	
	
	
	
		
	
	
}
