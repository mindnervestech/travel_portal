package com.travelportal.vm;

import java.util.List;

public class AreaAttractionsVM {
	private int attraction_code;
	private String name;
	private double distance;
	private String km;
	private int minutes;
	
	
	public int getAttraction_code() {
		return attraction_code;
	}
	public void setAttraction_code(int attraction_code) {
		this.attraction_code = attraction_code;
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getKm() {
		return km;
	}
	public void setKm(String km) {
		this.km = km;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	
	
}
