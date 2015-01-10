package com.travelportal.vm;

import java.util.List;

public class SerachedRoomType {
	
	public Long roomId;
	public String roomName;
	public List<SerachedRoomRateDetail> hotelRoomRateDetail;
	private List<RoomAmenitiesVm> amenities;
	
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
	public List<RoomAmenitiesVm> getAmenities() {
		return amenities;
	}
	public void setAmenities(List<RoomAmenitiesVm> amenities) {
		this.amenities = amenities;
	}  
	
	
	
}
