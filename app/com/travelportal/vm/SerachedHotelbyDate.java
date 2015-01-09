package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.rooms.RateMeta;

public class SerachedHotelbyDate {
	
	public String date;
	public List<SerachedRoomType>  roomType;
	public String flag;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<SerachedRoomType> getRoomType() {
		return roomType;
	}
	public void setRoomType(List<SerachedRoomType> roomType) {
		this.roomType = roomType;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}  
	
	
	
	
}
