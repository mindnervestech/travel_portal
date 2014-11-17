package com.travelportal.vm;


public class AreaAttractionsVM {
	public int attraction_code;
	public String name;
	public double distance;
	public String km;
	public int minutes;
	
	
	public int getAttraction_code() {
		return attraction_code;
	}
	public void setAttraction_code(int attraction_code) {
		this.attraction_code = attraction_code;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
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
