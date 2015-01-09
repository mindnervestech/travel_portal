package com.travelportal.vm;

import java.util.ArrayList;
import java.util.List;

import com.travelportal.domain.HotelStarRatings;
import com.travelportal.domain.rooms.RateMeta;

public class SerachHotelRoomType {
	
	public Long roomId;
	public String roomName;
	public List<SerachedRoomRateDetail> hotelRoomRateDetail;
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public List<SerachedRoomRateDetail> getHotelRoomRateDetail() {
		return hotelRoomRateDetail;
	}
	public void setHotelRoomRateDetail(
			List<SerachedRoomRateDetail> hotelRoomRateDetail) {
		this.hotelRoomRateDetail = hotelRoomRateDetail;
	}  
	
	
	
}
