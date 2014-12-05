package com.travelportal.domain;

import java.util.Date;
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
@Table(name="InfoWise_ImagesPath")
public class InfoWiseImagesPath { //Seed table.
	@Column(name="img_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int imgId;
	@Column(name="supplierCode")
	private long supplierCode;
	@Column(name="general_picture")
	private String generalPicture;
	@Column(name="general_Description")
	private String generalDescription;
	@Column(name=" hotel_lobby")
	private String  hotel_Lobby;
	@Column(name="lobby_Description")
	private String lobbyDescription;
	@Column(name="hotel_room")
	private String hotelRoom;
	@Column(name="room_Description")
	private String roomDescription;
	@Column(name="amenities_services")
	private String amenitiesServices;
	@Column(name="amenities_Description")
	private String amenitiesDescription;
	@Column(name="leisure_sports")
	private String leisureSports;
	@Column(name="leisure_Description")
	private String leisureDescription;
	@Column(name=" map_image")
	private String  map_image;
	@Column(name="map_Description")
	private String mapDescription;
	
	

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	
	public long getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(long supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getGeneralPicture() {
		return generalPicture;
	}

	public void setGeneralPicture(String generalPicture) {
		this.generalPicture = generalPicture;
	}

	public String getHotel_Lobby() {
		return hotel_Lobby;
	}

	public void setHotel_Lobby(String hotel_Lobby) {
		this.hotel_Lobby = hotel_Lobby;
	}

	public String getHotelRoom() {
		return hotelRoom;
	}

	public void setHotelRoom(String hotelRoom) {
		this.hotelRoom = hotelRoom;
	}

	public String getAmenitiesServices() {
		return amenitiesServices;
	}

	public void setAmenitiesServices(String amenitiesServices) {
		this.amenitiesServices = amenitiesServices;
	}

	public String getLeisureSports() {
		return leisureSports;
	}

	public void setLeisureSports(String leisureSports) {
		this.leisureSports = leisureSports;
	}

	public String getMap_image() {
		return map_image;
	}

	public void setMap_image(String map_image) {
		this.map_image = map_image;
	}
	
	public String getGeneralDescription() {
		return generalDescription;
	}

	public void setGeneralDescription(String generalDescription) {
		this.generalDescription = generalDescription;
	}
	
	public String getLobbyDescription() {
		return lobbyDescription;
	}

	public void setLobbyDescription(String lobbyDescription) {
		this.lobbyDescription = lobbyDescription;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public String getAmenitiesDescription() {
		return amenitiesDescription;
	}

	public void setAmenitiesDescription(String amenitiesDescription) {
		this.amenitiesDescription = amenitiesDescription;
	}

	public String getLeisureDescription() {
		return leisureDescription;
	}

	public void setLeisureDescription(String leisureDescription) {
		this.leisureDescription = leisureDescription;
	}

	public String getMapDescription() {
		return mapDescription;
	}

	public void setMapDescription(String mapDescription) {
		this.mapDescription = mapDescription;
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

    public static InfoWiseImagesPath findById(Long supplierCode) {
		try
		{
		return (InfoWiseImagesPath) JPA.em().createQuery("select c from InfoWiseImagesPath c where c.supplierCode = ?1").setParameter(1, supplierCode).getSingleResult();
		}
		catch(Exception ex){
			return null;
		}
    }
	
	
}
