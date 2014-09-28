package com.travelportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="hotel_connecting_routes")
public class HotelConnectivityRoutes {
	@ManyToOne
	private ConnectionType connectionType; //airport, railway, subway etc..
	@Column(name="direction_nm")
	private String directionNm;
	@Column(name="direction_id")
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int directionId;
	@Column(name="distance")
	private Double distance;
	@Column(name="distance_type")
	private String distanceType;
	@Column(name="time_require_in_mins")
	private int timeRequireInMinutes;
	
	/**
	 * @return the connectionType
	 */
	public ConnectionType getConnectionType() {
		return connectionType;
	}
	/**
	 * @param connectionType the connectionType to set
	 */
	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}
	
	/**
	 * @return the directionNm
	 */
	public String getDirectionNm() {
		return directionNm;
	}
	/**
	 * @param directionNm the directionNm to set
	 */
	public void setDirectionNm(String directionNm) {
		this.directionNm = directionNm;
	}
	/**
	 * @return the directionId
	 */
	public int getDirectionId() {
		return directionId;
	}
	/**
	 * @param directionId the directionId to set
	 */
	public void setDirectionId(int directionId) {
		this.directionId = directionId;
	}
	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	/**
	 * @return the distanceType
	 */
	public String getDistanceType() {
		return distanceType;
	}
	/**
	 * @param distanceType the distanceType to set
	 */
	public void setDistanceType(String distanceType) {
		this.distanceType = distanceType;
	}
	/**
	 * @return the timeRequireInMinutes
	 */
	public int getTimeRequireInMinutes() {
		return timeRequireInMinutes;
	}
	/**
	 * @param timeRequireInMinutes the timeRequireInMinutes to set
	 */
	public void setTimeRequireInMinutes(int timeRequireInMinutes) {
		this.timeRequireInMinutes = timeRequireInMinutes;
	}
	
}
